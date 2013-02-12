/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.pacman.entities;

import java.util.ArrayList;
import java.util.Random;
import org.fridlund.javalabra.game.entities.MovableEntityAbstract;
import org.fridlund.pacman.level.Level;
import org.lwjgl.util.vector.Vector4f;

/**
 * Simple ghost AI
 *
 * @author Christoffer
 */
public class Ghost extends MovableEntityAbstract {

    private GhostGraphics ghost;
    private Level level;
    private Direction direction;
    private Random random;
    private float speed;
    private boolean outOfNest;
    private int moveUpDownDirection;
    protected ArrayList<Integer> allowedTiles;
    private boolean isInvincible;
    private boolean isWarning;
    private float warningTimer;
    private boolean dead;
    private boolean releaseable;

    public Ghost(GhostGraphics ghost, Level level) {
        this.ghost = ghost;
        this.level = level;

        this.speed = 0.08f;
        this.random = new Random();

        this.ghost.setAnimation("up");
        this.direction = Direction.UP;
        this.outOfNest = false;
        // randomizes the direction the ghost'll bounce when in nest
        this.moveUpDownDirection = (random.nextBoolean()) ? 1 : -1;

        this.setCollisionOffset(5.0f);
        this.isWarning = false;
        this.isInvincible = true;
        this.dead = false;

        spawn(ghost.getGhostColorIndex());
    }

    //=================================================================
    /*
     * OVERRIDDEN METHODS
     */
    //=================================================================
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
            if (releaseable) {
                getOutOfNest(delta);
            } else {
                moveUpDown(delta);
            }
        } else {
            updateDirection(delta);
        }

        ghost.update(delta);
    }

    //=================================================================
    /*
     * PRIVATE METHODS
     */
    //=================================================================
    private void spawn(int ghostColorIndex) {
        // spawning the ghosts inside the "ghost area" offseted by two tiles to the right for each color.
        float spawnX = 14 * level.getTileWidth() + (ghostColorIndex % 4) * 2 * level.getTileWidth();
        float spawnY = 16 * level.getTileHeight();
        setPosition(spawnX, spawnY);
    }

    private void moveUpDown(float delta) {
        dy = moveUpDownDirection * speed * delta;
        if (level.walkableTile(this, 0, dy, allowedTiles)) {
            move(0, dy);
        } else {
            moveUpDownDirection *= -1;
        }
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

        dx = 0;
        dy = 0;

        switch (direction) {
            case UP:
                dy = speed * delta;
                ghost.setAnimation("up");
                break;
            case DOWN:
                dy = -speed * delta;
                ghost.setAnimation("down");
                break;
            case LEFT:
                dx = -speed * delta;
                ghost.setAnimation("left");
                break;
            case RIGHT:
                dx = speed * delta;
                ghost.setAnimation("right");
                break;
        }

        ghost.setRegularColor();

        if (!this.isInvincible()) {
            ghost.setAnimation("killable");
            ghost.setKillableColor();
        }
        warningEffect(delta);
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

    private void warningEffect(float delta) {
        if (this.isWarning()) {
            warningTimer += delta;
            if (warningTimer >= 150) {
                ghost.setColor(new Vector4f(1, 1, 1, 1));
            } else {
                ghost.setKillableColor();
            }

            if (warningTimer >= 300) {
                warningTimer = 0;
            }
            // ghost.setWarning(true);
        } else {
            // ghost.setWarning(false);
        }
    }

    private boolean checkDead(float delta) {
        if (this.isDead()) {
            ghost.setAnimation("killed");

            //ghost.setWarning(false);

            // using % 4 to get it at the four first ghost's locations. Prevents them from spawning outside the nest to the right.
            dx = (int) Math.round(16 * level.getTileWidth() + (ghost.getGhostColorIndex() % 4) * level.getTileWidth() - this.getX());
            dy = (int) Math.round(17 * level.getTileHeight() - this.getY());
            if (Math.abs(dx) < level.getTileWidth() && Math.abs(dy) < level.getTileHeight()) {
                setInNest();
                ghost.setAnimation("down");
                ghost.setRegularColor();
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

    //=================================================================
    /*
     * PUBLIC METHODS
     */
    //=================================================================
    public void kill() {
        this.dead = true;
        this.releaseable = false;
    }

    //=================================================================
    /*
     * SETTERS
     */
    //=================================================================
    public void setKillable() {
        this.isWarning = false;
        this.isInvincible = false;
    }

    public void setWarningAnimation() {
        if (!isInvincible) {
            this.isWarning = true;
        }
    }

    public void setInvincible() {
        this.isInvincible = true;
        this.isWarning = false;
    }

    public void setReleaseable(boolean releaseable) {
        this.releaseable = releaseable;
    }

    //=================================================================
    /*
     * GETTERS
     */
    //=================================================================
    public boolean isWarning() {
        return isWarning;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isReleaseable() {
        return releaseable;
    }

    public GhostGraphics getGhost() {
        return ghost;
    }
}
