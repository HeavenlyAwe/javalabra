/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.scenes;

import java.util.ArrayList;
import java.util.List;
import org.fridlund.javalabra.game.scenes.MenuScene;
import org.fridlund.javalabra.game.scenes.menus.Button;
import org.fridlund.javalabra.game.scenes.menus.MenuItem;
import org.fridlund.javalabra.game.utils.FontLoader;
import org.lwjgl.opengl.Display;

/**
 *
 * @author Christoffer
 */
public class MainMenuScene extends MenuScene {

    public static final String fontName = "Times New Roman";
    private GameplayScene gameplay;

    @Override
    public void setup() {
        super.setup();

        addButton("Start Game");
        addButton("Exit");
    }
    int w = 0;
    int h = 0;
    float y = 100;

    private void addButton(String text) {
        w = FontLoader.getFont(fontName).getWidth(text);
        h = FontLoader.getFont(fontName).getHeight(text);

        float x = (Display.getWidth() - w) / 2;
        y += 2 * h;

        menuItems.add(new Button(text, x, y, w, h, fontName));
    }

    @Override
    public void cleanUp() {
    }

    @Override
    public void update(float delta) {

        if (gameplay != null) {
            gameplay.update(delta);
        } else {
            for (int i = 0; i < menuItems.size(); i++) {
                menuItems.get(i).update(delta);
                if (menuItems.get(i) instanceof Button) {
                    if (((Button) menuItems.get(i)).isLeftMouseDown() && menuItems.get(i).getText().equals("Start Game")) {
                        gameplay = new GameplayScene();
                    }
                }
            }
        }
    }

    @Override
    public void render() {

        if (gameplay != null) {
            gameplay.render();
        } else {
            for (int i = 0; i < menuItems.size(); i++) {
                menuItems.get(i).render();
            }
        }

    }
}
