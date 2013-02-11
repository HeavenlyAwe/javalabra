/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.pacman.entities;

import java.io.InputStream;
import org.fridlund.javalabra.game.sprites.Animation;
import org.fridlund.javalabra.game.sprites.SpriteSheet;
import org.fridlund.javalabra.game.utils.TextureLoader;

/**
 *
 * @author Christoffer
 */
public class SuperSnack extends Snack {

    private static String texturePath = "/res/images/super_snack.png";

    @Override
    public void setupAnimation() {
        InputStream in = getClass().getResourceAsStream(texturePath);
        SpriteSheet sheet = new SpriteSheet(TextureLoader.loadTextureLinear(in), width, width, 16, 16);
        animation = new Animation(sheet);
    }
}
