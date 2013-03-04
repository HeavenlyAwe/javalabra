/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.backman.hud;

import org.fridlund.backman.entities.Backman;
import org.fridlund.javalabra.game.sprites.Animation;
import org.fridlund.javalabra.game.sprites.SpriteSheet;
import org.fridlund.javalabra.game.utils.FontLoader;
import org.fridlund.javalabra.game.utils.TextureLoader;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

/**
 * The Heads Up Display. Containing the health-bar and the informative text at
 * the upper left corner.
 *
 * @author Christoffer
 */
public class Hud {

    private String fontName;
    private Backman pacman;
    private Animation lifeAnimation;

    /**
     *
     * @param pacman
     */
    public Hud(Backman pacman) {
        this.pacman = pacman;

        this.fontName = "times30";

        SpriteSheet spriteSheet = new SpriteSheet(TextureLoader.loadTextureLinear(getClass().getResourceAsStream("/res/images/backman.png")), 32, 32, 256, 128);

        this.lifeAnimation = new Animation(spriteSheet);
        lifeAnimation.addFrame(4, 0, 1000);
        lifeAnimation.update(0);
    }

    //=================================================================
    /*
     * PRIVATE METHODS
     */
    //=================================================================
    /**
     * Renders the life symbols down left. The amount of symbols depends on how
     * many lives Backman has.
     */
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

    //=================================================================
    /*
     * PUBLIC METHODS
     */
    //=================================================================
    /**
     *
     * @param delta
     */
    public void update(float delta) {
    }

    /**
     * Renders the health-bar and the points collected by Backman.
     */
    public void render() {
        renderLives();
        FontLoader.renderString("Points: " + pacman.getPoints(), 10, 10, fontName);
    }
}
