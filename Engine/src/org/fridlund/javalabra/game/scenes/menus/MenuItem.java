/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.scenes.menus;

import org.fridlund.javalabra.game.utils.FontLoader;

/**
 * Simple menu item, used in the menu system.
 *
 * @author Christoffer
 */
public abstract class MenuItem {

    protected float x;
    protected float y;

    public MenuItem(float x, float y) {
        this.x = x;
        this.y = y;
    }

    //=================================================================
    /*
     * PUBLIC METHODS
     */
    //=================================================================
    public abstract void update(float delta);

    public abstract void render();
    //=================================================================
    /*
     * GETTERS
     */
    //=================================================================
}
