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
 * A place holder for the special action command. When the SpecialSnack's
 * execute command is called, it'll execute the interfaces execute method.
 *
 * @author Christoffer
 */
public class SpecialSnack extends Snack {

    private Direction dir;
    private float timer;

    public SpecialSnack(String texturePath, SpecialAction specialAction, Direction dir) {
        super(specialAction);
        this.dir = dir;
        this.timer = 0;

        // loads the animation here instead of in the setupAnimation method,
        // because of the custom texture path. This way you can load anykind
        // of snack image on the same logic
        InputStream in = getClass().getResourceAsStream(texturePath);
        SpriteSheet sheet = new SpriteSheet(TextureLoader.loadTextureLinear(in), width, width, 16, 16);
        animation = new Animation(sheet);
    }

    //=================================================================
    /*
     * OVERRIDDEN METHODS
     */
    //=================================================================
    /**
     * This method is overridden with an empty one, to disable the default
     * loading.
     */
    @Override
    public void setupAnimation() {
    }

    /**
     * Randomizes a direction for the special snack.
     *
     * @param delta
     */
    @Override
    public void update(float delta) {
        timer += delta;

        if (dir == Direction.UP) {
            y += 0.01f * delta;
        } else if (dir == Direction.DOWN) {
            y -= 0.01f * delta;
        } else if (dir == Direction.LEFT) {
            x -= 0.01f * delta;
        } else if (dir == Direction.RIGHT) {
            x += 0.01f * delta;
        }
    }

    //=================================================================
    /*
     * PUBLIC METHODS
     */
    //=================================================================
    //=================================================================
    /*
     * GETTERS
     */
    //=================================================================
    public float getTimer() {
        return timer;
    }
}
