/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.entities;

import org.fridlund.javalabra.game.entities.MovableEntityAbstract;
import org.fridlund.javalabra.game.sprites.Animation;
import org.fridlund.javalabra.game.sprites.SpriteSheet;
import org.fridlund.javalabra.game.utils.TextureLoader;

/**
 *
 * @author Christoffer
 */
public class Ghost extends MovableEntityAbstract {

    private static String texturePath = "res/pacman/images/pacman.png";
    private Animation animation;

    public Ghost() {
        super(100, 100);
    }

    @Override
    public void setup() {
        SpriteSheet sheet = new SpriteSheet(TextureLoader.loadTextureLinear(texturePath), 32, 32, 256, 128);

        setWidth(32);
        setHeight(32);

        animation = new Animation(sheet);
    }

    @Override
    public void cleanUp() {
        animation.cleanUp();
    }

    @Override
    public void update(float delta) {
        animation.update(delta);
    }

    @Override
    public void render() {
        animation.render(x, y);
    }
}
