/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import org.fridlund.javalabra.game.entities.MovableEntityAbstract;
import org.fridlund.javalabra.game.sprites.Animation;
import org.fridlund.javalabra.game.sprites.SpriteSheet;
import org.fridlund.javalabra.game.utils.TextureLoader;
import org.fridlund.javalabra.pacman.levels.Level;
import org.fridlund.javalabra.pacman.scenes.GameplayScene;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author Christoffer
 */
public class Pacman extends MovableEntityAbstract {

    private static String texturePath = "res/pacman/images/pacman.png";
    private Map<String, Animation> animations;
    private Animation animation;
    private Controller controller = null;
    private Level level;
    // movement
    private ArrayList<Integer> allowedTiles;
    private float speed = 0.1f;
    private int points;
    private int lives;
    private float dx;
    private float dy;
    private int up = Keyboard.KEY_W;
    private int down = Keyboard.KEY_S;
    private int left = Keyboard.KEY_A;
    private int right = Keyboard.KEY_D;

    public Pacman(Level level) {
        this.level = level;
        this.lives = 3;
        this.points = 0;

        this.setCollisionOffset(5.0f);

        allowedTiles = new ArrayList<>();
        allowedTiles.add(Level.WALKABLE);

        spawn();
    }

    public void spawn() {
        this.spawn(17, 1);
    }

    public void spawn(int tileX, int tileY) {
        setPosition(tileX * level.getTileWidth(), tileY * level.getTileHeight());
    }

    @Override
    public void setup() {

        animations = new HashMap<>();
        for (Controller c : ControllerEnvironment.getDefaultEnvironment().getControllers()) {
            if (c.getType() == Controller.Type.GAMEPAD) {
                controller = c;
                System.out.println(controller.getName());
            }
        }

        SpriteSheet spriteSheet = new SpriteSheet(TextureLoader.loadTextureLinear(texturePath), 32, 32, 256, 128);

        setWidth(32);
        setHeight(32);

        Animation rightAnimation = new Animation(spriteSheet);
        rightAnimation.addFrame(0, 0, 50);
        rightAnimation.addFrame(1, 0, 50);
        rightAnimation.addFrame(2, 0, 50);
        rightAnimation.addFrame(3, 0, 50);
        rightAnimation.addFrame(4, 0, 50);
        rightAnimation.addFrame(5, 0, 50);
        rightAnimation.addFrame(6, 0, 50);
        rightAnimation.addFrame(7, 0, 50);

        Animation leftAnimation = new Animation(spriteSheet);
        leftAnimation.addFrame(0, 1, 50);
        leftAnimation.addFrame(1, 1, 50);
        leftAnimation.addFrame(2, 1, 50);
        leftAnimation.addFrame(3, 1, 50);
        leftAnimation.addFrame(4, 1, 50);
        leftAnimation.addFrame(5, 1, 50);
        leftAnimation.addFrame(6, 1, 50);
        leftAnimation.addFrame(7, 1, 50);

        animation = rightAnimation;

        animations.put("right", rightAnimation);
        animations.put("left", leftAnimation);
    }

    @Override
    public void cleanUp() {
        for (String key : animations.keySet()) {
            animations.get(key).cleanUp();
        }
    }

    @Override
    public void update(float delta) {

        dx = 0;
        dy = 0;

        handleControllerInput(delta);
        handleKeyboardInput(delta);

        chooseAnimationFromDirection();

        resetDyWhenMovingOutsideBoard();

        tryToMove();



        teleportWhenMovingOutsideBoard();

        animation.update(delta);
    }

    private void handleControllerInput(float delta) {
        if (controller != null) {
            if (controller.poll()) {
                for (Component c : controller.getComponents()) {
                    if (c.getName().equals("Y Axis")) {
                        if (c.getPollData() > 0.1f) {
                            dy = speed * c.getPollData() * delta;
                        } else if (c.getPollData() < -0.1f) {
                            dy = speed * c.getPollData() * delta;
                        }
                    } else if (c.getName().equals("X Axis")) {
                        if (c.getPollData() > 0.5f) {
                            dx = speed * delta;
                        } else if (c.getPollData() < -0.5f) {
                            dx = speed * delta;
                        }
                    }
                }
            }
        }
    }

    private void handleKeyboardInput(float delta) {

        // allows for movement in x and y direction at the same time
        if (!keyDown(left) && keyDown(right)) {
            dx = speed * delta;
        } else if (keyDown(left) && !keyDown(right)) {
            dx = -speed * delta;
        }

        if (keyDown(up) && !keyDown(down)) {
            dy = speed * delta;
        } else if (!keyDown(up) && keyDown(down)) {
            dy = -speed * delta;
        }

    }

    private void chooseAnimationFromDirection() {
        if (dx < 0) {
            animation = animations.get("left");
        } else if (dx > 0) {
            animation = animations.get("right");
        }
    }

    private void resetDyWhenMovingOutsideBoard() {
        if (x < 0 || x + width > level.getWidth()) {
            dy = 0;
        }
    }

    private void tryToMove() {
        if (level.walkableTile(this, dx, 0, allowedTiles)) {
            move(dx, 0);
        }
        if (level.walkableTile(this, 0, dy, allowedTiles)) {
            move(0, dy);
        }
    }

    private void teleportWhenMovingOutsideBoard() {
        if (level.outsideOnTheRight(this)) {
            setX(-width);
            setY(level.getHeight() / 2);
        }
        if (level.outsideOnTheLeft(this)) {
            setX(level.getWidth());
            setY(level.getHeight() / 2);
        }
    }

    private boolean keyDown(int key) {
        return Keyboard.isKeyDown(key);
    }

    @Override
    public void render() {
        animation.render(x, y);
    }

    public void kill() {
        lives--;
    }

    public int getLives() {
        return lives;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void removePoints(int points) {
        this.points -= points;
    }

    public int getPoints() {
        return points;
    }
}
