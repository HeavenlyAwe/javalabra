/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.scenes.menus;

import org.fridlund.javalabra.game.utils.FontLoader;

/**
 * Simple menu item with a textual element.
 *
 * @author Christoffer
 */
public abstract class TextMenuItem extends MenuItem {

    private String text;
    private String font;

    public TextMenuItem(String text, String font, float x, float y) {
        super(x, y);

        this.text = text;
        this.font = font;
    }

    //=================================================================
    /*
     * OVERRIDDEN METHODS
     */
    //=================================================================
    @Override
    public void render() {
        FontLoader.renderString(text, x, y, font);
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
    public String getText() {
        return text;
    }

    public String getFontName() {
        return font;
    }
}
