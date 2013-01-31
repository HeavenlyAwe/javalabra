/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.entities;

import java.util.ArrayList;
import java.util.Random;
import org.fridlund.javalabra.game.entities.MovableEntityAbstract;
import org.fridlund.javalabra.pacman.levels.Level;

/**
 *
 * @author Christoffer
 */
public class Ghost extends MovableEntityAbstract {

    private GhostGraphics ghost;
    private Level level;
    private Direction direction;
    private Random random;
    private float speed;
    private boolean outOfNest = false;
    private int nestDirection = 1;
    protected ArrayList<Integer> allowedTiles;
    private boolean isInvincible;
    private boolean isWarning;
    private boolean dead;

    public Ghost(GhostGraphics ghost, Level level) {
        this.ghost = ghost;
        this.level = level;

        this.speed = 0.08f;
        this.random = new Random();

        this.ghost.setAnimation("up");
        this.direction = Direction.UP;

        this.setCollisionOffset(5.0f);
        this.isWarning = false;
        this.isInvincible = true;
        this.dead = false;

        spawn(ghost.getGhostColorIndex());
    }

    private void spawn(int ghostColorIndex) {
        // spawning the ghosts inside the "ghost area" offseted by two tiles to the right for each color.
        float x = 14 * level.getTileWidth() + ghostColorIndex * 2 * level.getTileWidth();
        float y = 16 * level.getTileHeight();
        setPosition(x, y);
    }

    @Override
    public void setup() {

        setWidth(GhostGraphics.spriteWidth);
        setHeight(GhostGraphics.spriteHeight);

        this.allowedTiles = new ArrayList<>();
        this.allowedTiles.add(Level.GHOST_TILE);
        this.allowedTiles.add(Level.WALKABLE);
    }

    @Override
    public void cleanUp() {
        ghost.cleanUp();
    }

    @Override
    public void render() {
        ghost.render(x, y);
    }

    @Override
    public void update(float delta) {
        dx = 0;
        dy = 0;

        if (!outOfNest) {
            getOutOfNest(delta);
        } else {
            updateDirection(delta);
        }

        ghost.update(delta);
    }

    private void getOutOfNest(float delta) {
        setInvincible();
        switch (direction) {
            case UP:
                ghost.setAnimation("up");
                break;
            case DOWN:
                ghost.setAnimation("down");
                break;
            case LEFT:
                ghost.setAnimation("left");
                break;
            case RIGHT:
                ghost.setAnimation("right");
                break;
        }

        if (getX() != 17 * level.getTileWidth()) {
            if (Math.round(getX()) < 17 * level.getTileWidth() - 1f) {
                dx = speed * delta;
            } else if (Math.round(getX()) > 17 * level.getTileWidth() + 1f) {
                dx = -speed * delta;
            } else {
                setPosition(17 * level.getTileWidth(), y);
            }
            move(dx, 0);
        } else {
            if (Math.round(getY()) < 21 * level.getTileHeight() - 1f) {
                dy = speed * delta;
            } else {
                setPosition(x, 21 * level.getTileHeight());
                outOfNest = true;
                if (random.nextBoolean()) {
                    tryLeftDirection();
                } else {
                    tryRightDirection();
                    return;
                }
            }
            move(0, dy);
        }
    }

    private void updateDirection(float delta) {
        switch (direction) {
            case UP:
                dy = speed * delta;
                dx = 0;
                ghost.setAnimation("up");
                break;
            case DOWN:
                dy = -speed * delta;
                dx = 0;
                ghost.setAnimation("down");
                break;
            case LEFT:
                dy = 0;
                dx = -speed * delta;
                ghost.setAnimation("left");
                break;
            case RIGHT:
                dy = 0;
                dx = speed * delta;
                ghost.setAnimation("right");
                break;
        }

        if (!this.isInvincible()) {
            ghost.setAnimation("killable");
        }
        if (this.isWarning()) {
            ghost.setAnimation("warning");
        }
        if (checkDead(delta)) {
            return;
        }

        if (!level.walkableTile(this, dx, dy, this.allowedTiles)) {
            randomizeDirection();
        }

        if (level.walkableTile(this, dx, dy, this.allowedTiles)) {
            this.move(dx, dy);
        }
    }

    private boolean checkDead(float delta) {
        if (this.isDead()) {
            ghost.setAnimation("killed");
            dx = (int) Math.round(16 * level.getTileWidth() + ghost.getGhostColorIndex() * level.getTileWidth() - this.getX());
            dy = (int) Math.round(17 * level.getTileHeight() - this.getY());
            if (Math.abs(dx) < level.getTileWidth() && Math.abs(dy) < level.getTileHeight()) {
                setInNest();
                return true;
            }
            float length = (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
            dx = dx / length * delta;
            dy = dy / length * delta;
            this.move(dx, dy);
            return true;
        }
        return false;
    }

    private void tryRightDirection() {
        if (level.walkableTile(this, this.getWidth() / 2, 0, this.allowedTiles)) {
            direction = Direction.RIGHT;
        } else {
            direction = Direction.LEFT;
        }
    }

    private void tryLeftDirection() {
        if (level.walkableTile(this, -this.getHeight() / 2, 0, this.allowedTiles)) {
            direction = Direction.LEFT;
        } else {
            direction = Direction.RIGHT;
        }
    }

    private void tryUpDirection() {
        if (level.walkableTile(this, 0, this.getHeight() / 2, this.allowedTiles)) {
            direction = Direction.UP;
        } else {
            direction = Direction.DOWN;
        }
    }

    private void tryDownDirection() {
        if (level.walkableTile(this, 0, -this.getHeight() / 2, this.allowedTiles)) {
            direction = Direction.DOWN;
        } else {
            direction = Direction.UP;
        }
    }

    private void randomizeDirection() {

        if (direction == Direction.UP) {
            if (random.nextBoolean()) {
                tryRightDirection();
            } else {
                tryLeftDirection();
            }
        } else if (direction == Direction.DOWN) {
            if (random.nextBoolean()) {
                tryRightDirection();
            } else {
                tryLeftDirection();
            }
        } else if (direction == Direction.LEFT) {
            if (random.nextBoolean()) {
                tryUpDirection();
            } else {
                tryDownDirection();
            }
        } else if (direction == Direction.RIGHT) {
            if (random.nextBoolean()) {
                tryUpDirection();
            } else {
                tryDownDirection();
            }
        }

    }

    private void setInNest() {
        outOfNest = false;
        this.dead = false;
        this.setInvincible();
    }

    public void setKillable() {
        this.isInvincible = false;
    }

    public void setWarningAnimation() {
        if (!isInvincible) {
            this.isWarning = true;
        }
    }

    public boolean isWarning() {
        return isWarning;
    }

    public void setInvincible() {
        this.isInvincible = true;
        this.isWarning = false;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    public boolean isDead() {
        return dead;
    }

    public void kill() {
        this.dead = true;
    }

    public GhostGraphics getGhost() {
        return ghost;
    }
}
