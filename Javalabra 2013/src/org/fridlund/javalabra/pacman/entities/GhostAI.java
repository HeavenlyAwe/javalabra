/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.entities;

import java.util.ArrayList;
import java.util.Random;
import org.fridlund.javalabra.pacman.levels.Level;

/**
 *
 * @author Christoffer
 */
public class GhostAI {

    private Ghost ghost;
    private Level level;
    private Direction direction;
    private Random random;
    private float speed;

    public GhostAI(Ghost ghost, Level level) {
        this.ghost = ghost;
        this.level = level;

        this.speed = 0.08f;
        this.random = new Random();

        this.ghost.setAnimation("up");
        this.direction = Direction.UP;
    }
    private boolean outOfNest = false;
    private int nestDirection = 1;

    public void update(float delta) {
        float dx = 0;
        float dy = 0;

        if (!outOfNest) {

            dx = nestDirection * speed * delta;
            dy = speed * delta;

            if (level.walkableTile(ghost, 0, dy, ghost.allowedTiles)) {
                ghost.move(0, dy);
            } else if (level.walkableTile(ghost, dx, 0, ghost.allowedTiles)) {
                ghost.move(dx, 0);
                if (level.walkableTile(ghost, 0, dy, ghost.allowedTiles)) {
                    ghost.move(0, dy);
                }
                if (ghost.getY() >= level.getTileHeight() * 19) {
                    outOfNest = true;
                    System.out.println(ghost + ": out of nest");
                }
            } else {
                nestDirection = -1;
            }
        } else {
            updateDirection(delta);
        }

        ghost.update(delta);
    }

    private void updateDirection(float delta) {
        float dx = 0.0f;
        float dy = 0.0f;

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

        if (!ghost.isInvincible()) {
            ghost.setAnimation("killable");
        }
        if (ghost.isWarning()) {
            ghost.setAnimation("warning");
        }

        if (!level.walkableTile(ghost, dx, dy, ghost.allowedTiles)) {
            randomizeDirection();
        }

        if (level.walkableTile(ghost, dx, dy, ghost.allowedTiles)) {
            ghost.move(dx, dy);
        }
    }

    private void tryRightDirection() {
        if (level.walkableTile(ghost, ghost.getWidth() / 2, 0, ghost.allowedTiles)) {
            direction = Direction.RIGHT;
        } else {
            direction = Direction.LEFT;
        }
    }

    private void tryLeftDirection() {
        if (level.walkableTile(ghost, -ghost.getHeight() / 2, 0, ghost.allowedTiles)) {
            direction = Direction.LEFT;
        } else {
            direction = Direction.RIGHT;
        }
    }

    private void tryUpDirection() {
        if (level.walkableTile(ghost, 0, ghost.getHeight() / 2, ghost.allowedTiles)) {
            direction = Direction.UP;
        } else {
            direction = Direction.DOWN;
        }
    }

    private void tryDownDirection() {
        if (level.walkableTile(ghost, 0, -ghost.getHeight() / 2, ghost.allowedTiles)) {
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

    public Ghost getGhost() {
        return ghost;
    }
}
