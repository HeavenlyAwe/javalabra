/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.backman.entities;

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

    /**
     *
     * @param specialAction
     */
    public SuperSnack(SpecialAction specialAction) {
        super(specialAction);
    }

    //=================================================================
    /*
     * OVERRIDDEN METHODS
     */
    //=================================================================
    /**
     * Assigns the image for the super snack. This is overridden to change the
     * default image used by the regular snack. Kind of a ugly hack, but what
     * can you do!
     */
    @Override
    public void setupAnimation() {
        InputStream in = getClass().getResourceAsStream(texturePath);
        SpriteSheet sheet = new SpriteSheet(TextureLoader.loadTextureLinear(in), width, width, 16, 16);
        animation = new Animation(sheet);
    }
}
