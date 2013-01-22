/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.fridlund.javalabra.game.entities.MovableEntityAbstract;
import org.fridlund.javalabra.game.sprites.Animation;
import org.fridlund.javalabra.game.sprites.SpriteSheet;
import org.fridlund.javalabra.game.utils.TextureLoader;
import org.fridlund.javalabra.pacman.levels.Level;
import org.fridlund.javalabra.pacman.scenes.GameplayScene;

/**
 *
 * @author Christoffer
 */
public class Ghost extends MovableEntityAbstract {

    private enum Direction {

        UP, DOWN, LEFT, RIGHT;
    }
    private static String texturePath = "res/pacman/images/ghost.png";
    private static int spriteWidth = 32;
    private static int spriteHeight = 32;
    private Level level;
    private Animation animation;
    private Map<String, Animation> animations;
    private int ghostColorIndex;
    private Direction direction;

    public Ghost(Level level, int ghostColorIndex) {
        this.level = level;
        this.ghostColorIndex = ghostColorIndex;
        spawn();
    }

    private void spawn() {

        float x = (level.getWidth() - spriteWidth) / 2;
        float y = (level.getHeight() - spriteHeight) / 2;
        setPosition(x, y);

        createAnimations();
    }

    /**
     * This should have been called from setup, but since that method is
     * overloaded, it is called before the constructor
     */
    private void createAnimations() {

        SpriteSheet sheet = new SpriteSheet(TextureLoader.loadTextureLinear(texturePath), spriteWidth, spriteHeight, 128, 128);
        animations = new HashMap<>();

        Animation upAnimation = new Animation(sheet);
        upAnimation.addFrame(3, ghostColorIndex, 1000);
        animations.put("up", upAnimation);

        Animation downAnimation = new Animation(sheet);
        downAnimation.addFrame(0, ghostColorIndex, 1000);
        animations.put("down", downAnimation);

        Animation leftAnimation = new Animation(sheet);
        leftAnimation.addFrame(1, ghostColorIndex, 1000);
        animations.put("left", leftAnimation);

        Animation rightAnimation = new Animation(sheet);
        rightAnimation.addFrame(2, ghostColorIndex, 1000);
        animations.put("right", rightAnimation);

        animation = animations.get("up");
        direction = Direction.UP;
    }

    @Override
    public void setup() {

        setWidth(spriteWidth);
        setHeight(spriteHeight);

    }

    @Override
    public void cleanUp() {
        animation.cleanUp();
    }
    private float dx;
    private float dy;
    private float speed = 0.08f;
    private boolean hasLeftTheNest = false;
    private String message;

    @Override
    public void update(float delta) {
        message = "";

        dx = 0.0f;
        dy = 0.0f;

        switch (direction) {
            case UP:
                dy = -speed * delta;
                dx = 0;
                animation = animations.get("up");
                break;
            case DOWN:
                dy = speed * delta;
                dx = 0;
                animation = animations.get("down");
                break;
            case LEFT:
                dy = 0;
                dx = -speed * delta;
                animation = animations.get("left");
                break;
            case RIGHT:
                dy = 0;
                dx = speed * delta;
                animation = animations.get("right");
                break;
        }

//        boolean canGoUp = false;
//        boolean canGoDown = false;
//        boolean canGoLeft = false;
//        boolean canGoRight = false;
//
//        if (direction == Direction.UP) {
//            if (level.walkableTile(this, width, 0)) {
//                message += "Can go right.";
//                canGoRight = true;
//            }
//            if (level.walkableTile(this, -width, 0)) {
//                message += "Can go left.";
//                canGoLeft = true;
//            }
//            if (level.walkableTile(this, 0, -height)) {
//                message += "Can go up.";
//                canGoUp = true;
//            }
//            randomizeDirection(canGoUp, false, canGoLeft, canGoRight, delta);
//        } else if (direction == Direction.DOWN) {
//            if (level.walkableTile(this, width, 0)) {
//                message += "Can go right.";
//                canGoRight = true;
//            }
//            if (level.walkableTile(this, -width, 0)) {
//                message += "Can go left.";
//                canGoLeft = true;
//            }
//            if (level.walkableTile(this, 0, height)) {
//                message += "Can go down.";
//                canGoDown = true;
//            }
//            randomizeDirection(false, canGoDown, canGoLeft, canGoRight, delta);
//        } else if (direction == Direction.LEFT) {
//            if (level.walkableTile(this, 0, height)) {
//                message += "Can go down.";
//                canGoDown = true;
//            }
//            if (level.walkableTile(this, 0, -height)) {
//                message += "Can go up.";
//                canGoUp = true;
//            }
//            if (level.walkableTile(this, -width, 0)) {
//                message += "Can go left.";
//                canGoLeft = true;
//            }
//            randomizeDirection(canGoUp, canGoDown, canGoLeft, false, delta);
//        } else if (direction == Direction.RIGHT) {
//            if (level.walkableTile(this, 0, height)) {
//                message += "Can go down.";
//                canGoDown = true;
//            }
//            if (level.walkableTile(this, 0, -height)) {
//                message += "Can go up.";
//                canGoUp = true;
//            }
//            if (level.walkableTile(this, width, 0)) {
//                message += "Can go right.";
//                canGoRight = true;
//            }
//            randomizeDirection(canGoUp, canGoDown, false, canGoRight, delta);
//        }

        if (!level.walkableTile(this, dx, dy)) {
            randomizeDirection();
        }

        if (level.walkableTile(this, dx, dy)) {
            move(dx, dy);
        }

        animation.update(delta);
    }
    private Random random = new Random();

    private void randomizeDirection(boolean canGoUp, boolean canGoDown, boolean canGoLeft, boolean canGoRight, float delta) {

        if (canGoUp && !canGoDown && !canGoLeft && !canGoRight) {
            direction = Direction.UP;
        } else if (!canGoUp && canGoDown && !canGoLeft && !canGoRight) {
            direction = Direction.DOWN;
        } else if (!canGoUp && !canGoDown && canGoLeft && !canGoRight) {
            direction = Direction.LEFT;
        } else if (!canGoUp && !canGoDown && !canGoLeft && canGoRight) {
            direction = Direction.RIGHT;
        } else if (canGoUp && canGoDown && !canGoLeft && !canGoRight) {
            if (random.nextBoolean()) {
                direction = Direction.UP;
            } else {
                direction = Direction.DOWN;
            }
        } else if (!canGoUp && !canGoDown && canGoLeft && canGoRight) {
            hasLeftTheNest = true;
            if (random.nextBoolean()) {
                direction = Direction.LEFT;
            } else {
                direction = Direction.RIGHT;
            }
        } else if (canGoUp && !canGoDown && canGoLeft && canGoRight) {
            if (random.nextBoolean()) {
                if (random.nextBoolean()) {
                    direction = Direction.LEFT;
                } else {
                    direction = Direction.RIGHT;
                }
            } else {
                direction = Direction.UP;
            }
        } else if (!canGoUp && canGoDown && canGoLeft && canGoRight) {
            if (random.nextBoolean()) {
                if (random.nextBoolean()) {
                    direction = Direction.LEFT;
                } else {
                    direction = Direction.RIGHT;
                }
            } else {
                direction = Direction.DOWN;
            }
        } else if (canGoUp && canGoDown && canGoLeft && !canGoRight) {
            if (random.nextBoolean()) {
                if (random.nextBoolean()) {
                    direction = Direction.UP;
                } else {
                    direction = Direction.DOWN;
                }
            } else {
                direction = Direction.LEFT;
            }
        } else if (canGoUp && canGoDown && !canGoLeft && canGoRight) {
            if (random.nextBoolean()) {
                if (random.nextBoolean()) {
                    direction = Direction.UP;
                } else {
                    direction = Direction.DOWN;
                }
            } else {
                direction = Direction.RIGHT;
            }
        }

        if (!hasLeftTheNest) {
            direction = Direction.UP;
        }
    }

    private void tryRightDirection() {
        if (level.walkableTile(this, width / 2, 0)) {
            direction = Direction.RIGHT;
        } else {
            direction = Direction.LEFT;
        }
    }

    private void tryLeftDirection() {
        if (level.walkableTile(this, -width / 2, 0)) {
            direction = Direction.LEFT;
        } else {
            direction = Direction.RIGHT;
        }
    }

    private void tryUpDirection() {
        if (level.walkableTile(this, 0, -height / 2)) {
            direction = Direction.UP;
        } else {
            direction = Direction.DOWN;
        }
    }

    private void tryDownDirection() {
        if (level.walkableTile(this, 0, height / 2)) {
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

    @Override
    public void render() {
        animation.render(GameplayScene.offsetDrawX + x, GameplayScene.offsetDrawY + y);
    }
}
