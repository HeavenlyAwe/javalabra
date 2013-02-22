/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.scenes.menus;

/**
 * Simple label, consisting of a TextMenuItem
 *
 * @author Christoffer
 */
public class Label extends TextMenuItem {

    /**
     *
     * @param text
     * @param x
     * @param y
     * @param fontName
     */
    public Label(String text, float x, float y, String fontName) {
        super(text, fontName, x, y);
    }

    /**
     *
     * @param delta
     */
    @Override
    public void update(float delta) {
    }
}
