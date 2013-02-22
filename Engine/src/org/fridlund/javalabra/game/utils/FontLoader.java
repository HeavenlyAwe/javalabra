/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.utils;

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import static org.lwjgl.opengl.ARBTextureRectangle.*;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

/**
 * A static class, containing all the Fonts that the game could like to use.
 *
 * @author Christoffer
 */
public class FontLoader {

    /**
     *
     */
    public static enum FontStyle {

        /**
         *
         */
        PLAIN,
        /**
         *
         */
        BOLD,
        /**
         *
         */
        ITALIC;
    }
    private static Map<String, UnicodeFont> fonts;

    private static void initFontLoader() {
        fonts = new HashMap<>();
        loadFont("dialog18", "Dialog", FontStyle.PLAIN, 18);
    }

    /**
     * Empties the font map.
     */
    public static void cleanUp() {
        fonts.clear();
        fonts = null;
    }

    /**
     * Creates a new font. If you use an already used key, then it will update
     * the old one.
     *
     * @param key - access the font with this
     * @param fontName - defines what system font to load.
     * @param style - italic, bold or plain.
     * @param size - pixel size
     */
    public static void loadFont(String key, String fontName, FontStyle style, int size) {
        if (fonts == null) {
            initFontLoader();
        }
        Font awtFont = null;
        switch (style) {
            case PLAIN:
                awtFont = new Font(fontName, Font.PLAIN, size);
                break;
            case BOLD:
                awtFont = new Font(fontName, Font.BOLD, size);
                break;
            case ITALIC:
                awtFont = new Font(fontName, Font.ITALIC, size);
                break;
        }

        UnicodeFont font = new UnicodeFont(awtFont);
        font.getEffects().add(new ColorEffect(java.awt.Color.white));
        font.addAsciiGlyphs();
        try {
            font.loadGlyphs();

            System.out.println("Loaded: " + fontName);
        } catch (SlickException ex) {
            ex.printStackTrace();
        }

        fonts.put(key, font);
    }

    /**
     * Use the key, that you gave when loading a font.
     *
     * @param key
     * @return
     */
    public static UnicodeFont getFont(String key) {
        if (fonts == null) {
            initFontLoader();
        }

        key = key.toLowerCase();

        if (fonts.get(key) == null) {
            return fonts.get("dialog18");
        }
        return fonts.get(key);
    }

    /**
     * Draws the text to the screen, at the defined coordinate with the defined
     * font.
     *
     * @param text
     * @param x
     * @param y
     * @param key
     */
    public static void renderString(String text, float x, float y, String key) {
        glDisable(GL_TEXTURE_RECTANGLE_ARB);
        FontLoader.getFont(key).drawString(x, y, text);
        glEnable(GL_TEXTURE_RECTANGLE_ARB);
    }

    /**
     * Draws the text to the screen, at the defined coordinate with the defined
     * font and color.
     *
     * @param text
     * @param x
     * @param y
     * @param key
     * @param color
     */
    public static void renderString(String text, float x, float y, String key, Color color) {
        glDisable(GL_TEXTURE_RECTANGLE_ARB);
        FontLoader.getFont(key).drawString(x, y, text, color);
        glEnable(GL_TEXTURE_RECTANGLE_ARB);
    }
}
