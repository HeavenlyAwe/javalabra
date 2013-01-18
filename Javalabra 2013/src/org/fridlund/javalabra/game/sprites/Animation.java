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

    public Animation(SpriteSheet sheet) {
        this.sheet = sheet;
        this.sprites = new ArrayList<>();
        this.durations = new ArrayList<>();
        
        setup();
    }

    public void setup() {
        currentSprite = sheet.getSprite(0, 0);
        currentSpriteIndex = 0;
        timeSinceLastSprite = 0;
    }

    public void cleanUp() {
        glDeleteTextures(sheet.getTextureID());
    }

    public void addFrame(int x, int y, int duration) {
        sprites.add(sheet.getSprite(x, y));
        durations.add(duration);
    }

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

    public void render(float x, float y, float scale) {
        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, sheet.getTextureID());

        float sx = currentSprite.getX();
        float sy = currentSprite.getY();
        float sx1 = sx + currentSprite.getWidth();
        float sy1 = sy + currentSprite.getHeight();

        glBegin(GL_QUADS);
        {
            glTexCoord2f(sx, sy);
            glVertex2f(x, y);
            glTexCoord2f(sx, sy1);
            glVertex2f(x, y + currentSprite.getHeight() * scale);
            glTexCoord2f(sx1, sy1);
            glVertex2f(x + currentSprite.getWidth() * scale, y + currentSprite.getHeight() * scale);
            glTexCoord2f(sx1, sy);
            glVertex2f(x + currentSprite.getWidth() * scale, y);
        }
        glEnd();

        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, 0);
    }

    public void render(float x, float y) {
        render(x, y, 1);
    }
}
