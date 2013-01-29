/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.entities;

import org.fridlund.javalabra.game.sprites.Animation;
import org.fridlund.javalabra.game.sprites.SpriteSheet;
import org.fridlund.javalabra.game.utils.TextureLoader;

/**
 *
 * @author Christoffer
 */
public class SuperSnack extends Snack {

    private static String texturePath = "res/pacman/images/super_snack.png";

    @Override
    public void setupAnimation() {
        SpriteSheet sheet = new SpriteSheet(TextureLoader.loadTextureLinear(texturePath), width, width, 16, 16);
        animation = new Animation(sheet);
    }
}
