/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.sprites;

import java.util.ArrayList;
import java.util.List;
import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.*;

/**
 * For now the easiest way of rendering images on the screen. Loads one or
 * several images from a sprite sheet and loops through them, at a speed based
 * on the current frames duration.
 *
 * @author Christoffer
 */
public class Animation {

    private int currentSpriteIndex;
    private Sprite currentSprite;
    private SpriteSheet sheet;
    private List<Sprite> sprites;
    private List<Integer> durations;
    private int timeSinceLastSprite;

    /**
     *
     * @param sheet
     */
    public Animation(SpriteSheet sheet) {
        this.sheet = sheet;
        this.sprites = new ArrayList<>();
        this.durations = new ArrayList<>();

        setup();
    }

    //=================================================================
    /*
     * PUBLIC METHODS
     */
    //=================================================================
    /**
     * Access to the first sub image in the sprite sheet. Starts the timer from
     * zero.
     */
    public void setup() {
        currentSprite = sheet.getSprite(0, 0);
        currentSpriteIndex = 0;
        timeSinceLastSprite = 0;
    }

    /**
     * Deletes the OpenGL binding for the sprite sheet texture.
     */
    public void cleanUp() {
        glDeleteTextures(sheet.getTextureID());
    }

    /**
     * Adds a new frame to the animation list.
     *
     * @param x - sub image coordinate
     * @param y - sub image coordinate
     * @param duration - milliseconds
     */
    public void addFrame(int x, int y, int duration) {
        sprites.add(sheet.getSprite(x, y));
        durations.add(duration);
    }

    /**
     * If the animations doesn't have any durations it doesn't do anything.
     * Otherwise it updates the timer and switches between the animation frames
     * according to the time used.
     *
     * @param delta
     */
    public void update(float delta) {
        if (!durations.isEmpty()) {
            timeSinceLastSprite += delta;
            if (timeSinceLastSprite >= durations.get(currentSpriteIndex)) {
                timeSinceLastSprite = 0;
                currentSpriteIndex++;
                if (currentSpriteIndex >= durations.size()) {
                    currentSpriteIndex = 0;
                }
            }
            currentSprite = sprites.get(currentSpriteIndex);
        }
    }

    /**
     * Renders the animations at the scale of 1.0
     *
     * @param x
     * @param y
     */
    public void render(float x, float y) {
        render(x, y, 1);
    }

    /**
     * Render by a arbitrary scale.
     *
     * @param x
     * @param y
     * @param scale
     */
    public void render(float x, float y, float scale) {
        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, sheet.getTextureID());

        float sx = currentSprite.getX();
        float sy = currentSprite.getY();
        float sx1 = sx + currentSprite.getWidth();
        float sy1 = sy + currentSprite.getHeight();

        glBegin(GL_QUADS);
        {
            glTexCoord2f(sx, sy1);
            glVertex2f(x, y);
            glTexCoord2f(sx, sy);
            glVertex2f(x, y + currentSprite.getHeight() * scale);
            glTexCoord2f(sx1, sy);
            glVertex2f(x + currentSprite.getWidth() * scale, y + currentSprite.getHeight() * scale);
            glTexCoord2f(sx1, sy1);
            glVertex2f(x + currentSprite.getWidth() * scale, y);
        }
        glEnd();

        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, 0);
    }
}
