/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.pacman.hud;

import org.fridlund.javalabra.game.sprites.Animation;
import org.fridlund.javalabra.game.sprites.SpriteSheet;
import org.fridlund.javalabra.game.utils.FontLoader;
import org.fridlund.javalabra.game.utils.TextureLoader;
import org.fridlund.pacman.entities.Pacman;
import org.fridlund.pacman.level.Level;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Christoffer
 */
public class Hud {

    private Level level;
    private Pacman pacman;
    private Animation lifeAnimation;

    public Hud(Level level, Pacman pacman) {
        this.level = level;
        this.pacman = pacman;

        SpriteSheet spriteSheet = new SpriteSheet(TextureLoader.loadTextureLinear(getClass().getResourceAsStream("/res/images/pacman.png")), 32, 32, 256, 128);

        this.lifeAnimation = new Animation(spriteSheet);
        lifeAnimation.addFrame(4, 0, 1000);
        lifeAnimation.update(0);
    }

    public void update(float delta) {
    }

    public void render() {
        renderLives();

        FontLoader.renderString("Points: " + pacman.getPoints(), 10, 10, "times new roman");
//        FontLoader.renderString("Lives: " + pacman.getLives(), 10, 35, "times new roman");
    }

    private void renderLives() {

        float x0 = 10;
        float y0 = Display.getHeight() - (10 + pacman.getHeight());

        glColor4f(1, 1, 0, 1);
        for (int i = 0; i < pacman.getMaxLives(); i++) {
            if ((i + 1) > pacman.getLives()) {
                glColor4f(1, 1, 0, 0.2f);
            }
            lifeAnimation.render(x0 + i * 1.1f * pacman.getWidth(), y0);
        }
    }
}
