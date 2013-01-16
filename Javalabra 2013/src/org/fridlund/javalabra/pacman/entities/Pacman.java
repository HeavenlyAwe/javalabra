/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.entities;

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

    public Pacman() {
        super(10, 10, new Animation(new SpriteSheet(TextureLoader.loadTextureLinear(texturePath), 32, 32, 256, 128)));
    }

    @Override
    public void setup() {
        super.setup();

        animation.addFrame(0, 0, 100);
        animation.addFrame(1, 0, 100);
        animation.addFrame(2, 0, 100);
        animation.addFrame(3, 0, 100);
        animation.addFrame(4, 0, 100);
        animation.addFrame(5, 0, 100);
        animation.addFrame(6, 0, 100);
        animation.addFrame(7, 0, 100);

    }

    @Override
    public void update(float delta) {
        super.update(delta);

        float dx = 0;
        float dy = 0;

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

        move(dx, dy);
    }
}
