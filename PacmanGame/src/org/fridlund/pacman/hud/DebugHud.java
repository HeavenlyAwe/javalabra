/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.pacman.hud;

import org.fridlund.javalabra.game.utils.FontLoader;

/**
 * Displays the controls, when activating the debug menu. You could call them
 * cheats.
 *
 * @author Christoffer
 */
public class DebugHud {

    private String fontName;

    /**
     *
     */
    public DebugHud() {
        fontName = "dialog18";
    }

    //=================================================================
    /*
     * PUBLIC METHODS
     */
    //=================================================================
    /**
     *
     */
    public void render() {

        FontLoader.renderString("Victory Screen: F11", 10, 50, fontName);
        FontLoader.renderString("Game Over Screen: F12", 10, 70, fontName);
        FontLoader.renderString("Rotate Level Left: G", 10, 90, fontName);
        FontLoader.renderString("Rotate Level Right: H", 10, 110, fontName);
        FontLoader.renderString("Eat Super Snack: Space", 10, 130, fontName);

    }
}
