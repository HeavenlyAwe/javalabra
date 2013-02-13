/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.scenes;

import java.util.ArrayList;
import java.util.List;
import org.fridlund.javalabra.game.scenes.menus.MenuItem;

/**
 *
 * @author Christoffer
 */
public abstract class MenuScene extends Scene {

    protected List<MenuItem> menuItems;

    public MenuScene(int id) {
        super(id);
    }

    @Override
    public void setup() {
        menuItems = new ArrayList<>();
    }
}
