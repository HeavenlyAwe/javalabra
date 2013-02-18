/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.scenes;

import java.util.ArrayList;
import java.util.List;
import org.fridlund.javalabra.game.scenes.menus.Action;
import org.fridlund.javalabra.game.scenes.menus.Button;
import org.fridlund.javalabra.game.scenes.menus.Label;
import org.fridlund.javalabra.game.scenes.menus.MenuItem;
import org.fridlund.javalabra.game.utils.FontLoader;
import org.lwjgl.opengl.Display;

/**
 *
 * @author Christoffer
 */
public abstract class MenuScene extends Scene {

    protected List<MenuItem> menuItems;
    protected String titleFont;
    private String title;
    protected float y;
    private float w;
    private float h;
    private float titleY;
    private float titleX;
    private int titleW;

    public MenuScene(int id, String title, String titleFont) {
        super(id);
        this.title = title;
        this.titleFont = titleFont;
    }

    @Override
    public void setup() {
        menuItems = new ArrayList<>();
    }

    @Override
    public void show() {
        y = 50;
        titleY = y;
        titleW = FontLoader.getFont(titleFont).getWidth(title);
        titleX = (Display.getWidth() - titleW) / 2;
    }

    @Override
    public void render() {
        super.render();

        FontLoader.renderString(title, titleX, titleY, titleFont);

        for (int i = 0; i < menuItems.size(); i++) {
            menuItems.get(i).render();
        }
    }

    /**
     * Places a button in the menuItem list, and automatically calculates the
     * next y-coordinate based on the height of this element and the previous
     * y-coordinate.
     *
     * @param text
     * @param fontKey
     */
    public void addButton(String text, Action action, String fontKey) {
        w = FontLoader.getFont(fontKey).getWidth(text);
        h = FontLoader.getFont(fontKey).getFont().getSize();

        float x = (Display.getWidth() - w) / 2;
        y += 2 * h;

        menuItems.add(new Button(text, x, y, w, h, action, fontKey));
    }

    public void addLabel(String text, String fontKey) {
        w = FontLoader.getFont(fontKey).getWidth(text);
        h = FontLoader.getFont(fontKey).getFont().getSize();

        float x = (Display.getWidth() - w) / 2;
        y += 2 * h;

        menuItems.add(new Label(text, x, y, fontKey));
    }
}
