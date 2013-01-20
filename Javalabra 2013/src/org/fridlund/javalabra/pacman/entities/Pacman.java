/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.entities;

import java.util.HashMap;
import java.util.Map;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import org.fridlund.javalabra.game.entities.MovableEntityAbstract;
import org.fridlund.javalabra.game.sprites.Animation;
import org.fridlund.javalabra.game.sprites.SpriteSheet;
import org.fridlund.javalabra.game.utils.TextureLoader;
import org.fridlund.javalabra.pacman.PacmanGame;
import org.fridlund.javalabra.pacman.levels.Level;
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
    private float speed = 0.1f;
    private float dx;
    private float dy;
    private int up = Keyboard.KEY_W;
    private int down = Keyboard.KEY_S;
    private int left = Keyboard.KEY_A;
    private int right = Keyboard.KEY_D;

    public Pacman(Level level) {
        this.level = level;

        spawn();
    }

    private void spawn() {
        setPosition(level.getWidth() / 2 - level.getTileWidth(), level.getHeight() - this.height - level.getTileHeight());
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
        rightAnimation.addFrame(0, 0, 100);
        rightAnimation.addFrame(1, 0, 100);
        rightAnimation.addFrame(2, 0, 100);
        rightAnimation.addFrame(3, 0, 100);
        rightAnimation.addFrame(4, 0, 100);
        rightAnimation.addFrame(5, 0, 100);
        rightAnimation.addFrame(6, 0, 100);
        rightAnimation.addFrame(7, 0, 100);

        Animation leftAnimation = new Animation(spriteSheet);
        leftAnimation.addFrame(0, 1, 100);
        leftAnimation.addFrame(1, 1, 100);
        leftAnimation.addFrame(2, 1, 100);
        leftAnimation.addFrame(3, 1, 100);
        leftAnimation.addFrame(4, 1, 100);
        leftAnimation.addFrame(5, 1, 100);
        leftAnimation.addFrame(6, 1, 100);
        leftAnimation.addFrame(7, 1, 100);

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

        move();



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
            dy = -speed * delta;
        } else if (!keyDown(up) && keyDown(down)) {
            dy = speed * delta;
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

    private void move() {
        if (level.walkableTile(this, dx, 0)) {
            move(dx, 0);
        }
        if (level.walkableTile(this, 0, dy)) {
            move(0, dy);
        }
    }

    private void teleportWhenMovingOutsideBoard() {
        if (level.outsideOnTheRight(this)) {
            setX(-width);
            setY(level.getHeight() / 2 - height);
        }
        if (level.outsideOnTheLeft(this)) {
            setX(level.getWidth());
            setY(level.getHeight() / 2 - height);
        }
    }

    private boolean keyDown(int key) {
        return Keyboard.isKeyDown(key);
    }

    @Override
    public void render() {
        animation.render(PacmanGame.offsetDrawX + x, PacmanGame.offsetDrawY + y);
    }
}
