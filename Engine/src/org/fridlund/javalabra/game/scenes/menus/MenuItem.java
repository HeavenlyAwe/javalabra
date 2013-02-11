/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.scenes.menus;

import org.fridlund.javalabra.game.utils.FontLoader;

/**
 *
 * @author Christoffer
 */
public class MenuItem {

    protected String text;
    protected float x;
    protected float y;
    private String fontName;

    public MenuItem(String text, float x, float y, String fontName) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.fontName = fontName;
    }

    public void update(float delta) {
    }

    public void render() {
        FontLoader.renderString(text, x, y, fontName);
    }

    public String getFontName() {
        return fontName;
    }

    public String getText() {
        return text;
    }
}
