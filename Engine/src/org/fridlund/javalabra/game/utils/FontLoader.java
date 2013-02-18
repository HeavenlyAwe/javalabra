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
 *
 * @author Christoffer
 */
public class FontLoader {

    public static enum FontStyle {

        PLAIN, BOLD, ITALIC;
    }
    private static Map<String, UnicodeFont> fonts;

    private static void initFontLoader() {
        fonts = new HashMap<>();
    }

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

    public static void renderString(String text, float x, float y, String key) {
        glDisable(GL_TEXTURE_RECTANGLE_ARB);
        FontLoader.getFont(key).drawString(x, y, text);
        glEnable(GL_TEXTURE_RECTANGLE_ARB);
    }

    public static void renderString(String text, float x, float y, String key, Color color) {
        glDisable(GL_TEXTURE_RECTANGLE_ARB);
        FontLoader.getFont(key).drawString(x, y, text, color);
        glEnable(GL_TEXTURE_RECTANGLE_ARB);
    }
}
