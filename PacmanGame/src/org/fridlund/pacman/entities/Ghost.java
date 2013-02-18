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
    /**
     * Assigns the width and the hight of the ghost based on the graphics
     * loaded. Here is also the tiles allowed for the ghosts to walk on are
     * defined.
     */
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
    /**
     * Places the ghost in the nest.
     *
     * @param ghostColorIndex
     */
    private void spawn(int ghostColorIndex) {
        // spawning the ghosts inside the "ghost area" offseted by two tiles to the right for each color.
        float spawnX = 14 * level.getTileWidth() + (ghostColorIndex % 4) * 2 * level.getTileWidth();
        float spawnY = 16 * level.getTileHeight();
        setPosition(spawnX, spawnY);
    }

    /**
     * Moves the ghost up and down inside the nest.
     *
     * @param delta
     */
    private void moveUpDown(float delta) {
        dy = moveUpDownDirection * speed * delta;
        if (level.walkableTile(this, 0, dy, allowedTiles)) {
            move(0, dy);
        } else {
            moveUpDownDirection *= -1;
        }
    }

    /**
     * This method is used to move the ghost outside the nest, when it is
     * allowed to move outside.
     *
     * @param delta
     */
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

        // Makes the ghost go to the center of the nest before it starts to
        // move out. The boundaries are checked against a padding of one pixel.
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

    /**
     * When the ghost is outside the nest, the ghosts position is updated in
     * this method.
     *
     * @param delta
     */
    private void updateDirection(float delta) {

        dx = 0;
        dy = 0;

        // choose the direction-specific-animation, based on the direction the ghost is moving.
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

        // set the color to the default for this specific ghost.
        ghost.setRegularColor();

        // reset the color based on the current animation
        if (!this.isInvincible()) {
            ghost.setAnimation("killable");
            ghost.setKillableColor();
        }
        warningEffect(delta);
        if (checkDead(delta)) {
            return;
        }

        // if the next tile isn't movable, find a new direction
        if (!level.walkableTile(this, dx, dy, this.allowedTiles)) {
            randomizeDirection();
        }

        // if the next tile is walkable, use the move method.
        if (level.walkableTile(this, dx, dy, this.allowedTiles)) {
            this.move(dx, dy);
        }
    }

    /**
     * Activates the blinking on the ghost. When the warning timer is full it
     * changes between white and the killable color.
     *
     * @param delta
     */
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
        }
    }

    /**
     * Controls the state of the ghost. If it is dead it'll transport the ghost
     * back to the nest.
     *
     * @param delta
     * @return
     */
    private boolean checkDead(float delta) {
        if (this.isDead()) {
            ghost.setAnimation("killed");

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
            dx = dx / length * 0.5f * delta;
            dy = dy / length * 0.5f * delta;
            this.move(dx, dy);
            return true;
        }
        return false;
    }

    /**
     * If it can't go right it will go left.
     */
    private void tryRightDirection() {
        if (level.walkableTile(this, this.getWidth() / 2, 0, this.allowedTiles)) {
            direction = Direction.RIGHT;
        } else {
            direction = Direction.LEFT;
        }
    }

    /**
     * If it cant go left it will go right.
     */
    private void tryLeftDirection() {
        if (level.walkableTile(this, -this.getHeight() / 2, 0, this.allowedTiles)) {
            direction = Direction.LEFT;
        } else {
            direction = Direction.RIGHT;
        }
    }

    /**
     * If it cant go up it will go down.
     */
    private void tryUpDirection() {
        if (level.walkableTile(this, 0, this.getHeight() / 2, this.allowedTiles)) {
            direction = Direction.UP;
        } else {
            direction = Direction.DOWN;
        }
    }

    /**
     * If it cant go down it will go up.
     */
    private void tryDownDirection() {
        if (level.walkableTile(this, 0, -this.getHeight() / 2, this.allowedTiles)) {
            direction = Direction.DOWN;
        } else {
            direction = Direction.UP;
        }
    }

    /**
     * Chooses one of three other directions (then it already have) randomly.
     */
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

    /**
     * Sets <br> <b>outOfNest</b> flag to <i>false</i> <br> <b>dead</b> flag to
     * <i>false</i> <br> and makes the ghost invincible again.
     */
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
