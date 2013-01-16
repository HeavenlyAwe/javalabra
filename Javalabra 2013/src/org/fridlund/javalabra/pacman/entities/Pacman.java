/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.entities;

import java.awt.Rectangle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import org.fridlund.javalabra.game.entities.Entity;
import org.fridlund.javalabra.game.entities.MovableEntityAbstract;
import org.fridlund.javalabra.game.sprites.Animation;
import org.fridlund.javalabra.game.sprites.SpriteSheet;
import org.fridlund.javalabra.game.utils.TextureLoader;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author Christoffer
 */
public class Pacman extends MovableEntityAbstract {

    private static String texturePath = "res/images/pacman/pacman.png";
    private Map<String, Animation> animations;
    private Animation animation;
    private Controller controller = null;

    public Pacman() {
        super(200, 10);
        animations = new HashMap<>();
        for (Controller c : ControllerEnvironment.getDefaultEnvironment().getControllers()) {
            if (c.getType() == Controller.Type.GAMEPAD) {
                controller = c;
                System.out.println(controller.getName());
            }
        }
    }

    @Override
    public void setup() {

        SpriteSheet spriteSheet = new SpriteSheet(TextureLoader.loadTextureLinear(texturePath), 32, 32, 256, 128);
        spriteSheet.setup();

        setWidth(32);
        setHeight(32);

        Animation right = new Animation(spriteSheet);
        right.addFrame(0, 0, 100);
        right.addFrame(1, 0, 100);
        right.addFrame(2, 0, 100);
        right.addFrame(3, 0, 100);
        right.addFrame(4, 0, 100);
        right.addFrame(5, 0, 100);
        right.addFrame(6, 0, 100);
        right.addFrame(7, 0, 100);
        right.setup();

        Animation left = new Animation(spriteSheet);
        left.addFrame(0, 1, 100);
        left.addFrame(1, 1, 100);
        left.addFrame(2, 1, 100);
        left.addFrame(3, 1, 100);
        left.addFrame(4, 1, 100);
        left.addFrame(5, 1, 100);
        left.addFrame(6, 1, 100);
        left.addFrame(7, 1, 100);
        left.setup();

        animation = right;

        animations.put("right", right);
        animations.put("left", left);
    }

    @Override
    public void cleanUp() {
        for (String key : animations.keySet()) {
            animations.get(key).cleanUp();
        }
    }

    @Override
    public void update(float delta) {

        float dx = 0;
        float dy = 0;


        if (controller != null) {
            if (controller.poll()) {
                for (Component c : controller.getComponents()) {
                    if (c.getName().equals("Y Axis")) {
                        if (c.getPollData() > 0.1f) {
                            dy = 0.1f * c.getPollData() * delta;
                        } else if (c.getPollData() < -0.1f) {
                            dy = 0.1f * c.getPollData() * delta;
                        }
                    } else if (c.getName().equals("X Axis")) {
                        if (c.getPollData() > 0.5f) {
                            dx = 0.1f * delta;
                        } else if (c.getPollData() < -0.5f) {
                            dx = -0.1f * delta;
                        }
                    }
                }
            }
        }



        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            dy = -0.1f * delta;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            dy = 0.1f * delta;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            dx = -0.1f * delta;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            dx = 0.1f * delta;
        }

        if (dx < 0) {
            animation = animations.get("left");
        } else if (dx > 0) {
            animation = animations.get("right");
        }

        move(dx, dy);

        animation.update(delta);
    }

    @Override
    public void render() {
        animation.render(x, y);
    }
}
