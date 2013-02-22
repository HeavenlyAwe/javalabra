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

    public Label(String text, float x, float y, String fontName) {
        super(text, fontName, x, y);
    }

    @Override
    public void update(float delta) {
    }
}
