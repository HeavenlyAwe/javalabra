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

    /**
     * Overriding this method to get rid of the original loading method.
     */
    @Override
    public void setupAnimation() {
    }

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

    public float getTimer() {
        return timer;
    }
}
