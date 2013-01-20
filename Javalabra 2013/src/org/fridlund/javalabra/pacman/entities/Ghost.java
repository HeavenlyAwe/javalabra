/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.entities;

import org.fridlund.javalabra.game.entities.MovableEntityAbstract;
import org.fridlund.javalabra.game.sprites.Animation;
import org.fridlund.javalabra.game.sprites.SpriteSheet;
import org.fridlund.javalabra.game.utils.TextureLoader;
import org.fridlund.javalabra.pacman.PacmanGame;
import org.fridlund.javalabra.pacman.levels.Level;

/**
 *
 * @author Christoffer
 */
public class Ghost extends MovableEntityAbstract {

    private static String texturePath = "res/pacman/images/pacman.png";
    private Animation animation;
    private static int spriteWidth = 32;
    private static int spriteHeight = 32;
    private Level level;

    public Ghost(Level level) {
        this.level = level;
        setPosition((level.getWidth() - spriteWidth) / 2, (level.getHeight() - spriteHeight) / 2);
    }

    @Override
    public void setup() {
        SpriteSheet sheet = new SpriteSheet(TextureLoader.loadTextureLinear(texturePath), spriteWidth, spriteHeight, 256, 128);

        setWidth(spriteWidth);
        setHeight(spriteHeight);

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
        animation.render(PacmanGame.offsetDrawX + x, PacmanGame.offsetDrawY + y);
    }
}
