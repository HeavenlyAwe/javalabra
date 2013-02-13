/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.pacman.scenes;

import org.fridlund.javalabra.game.scenes.MenuScene;
import org.fridlund.javalabra.game.scenes.menus.Button;
import org.fridlund.javalabra.game.utils.FontLoader;
import org.lwjgl.opengl.Display;

/**
 * Main menu, contains buttons and eventual graphics related to the menu. It's
 * from here the game is started.
 *
 * @author Christoffer
 */
public class MainMenuScene extends MenuScene {

    public static final String fontName = "Times New Roman";
    private int w = 0;
    private int h = 0;
    private float y = 100;

    public MainMenuScene(int id) {
        super(id);
    }

    //=================================================================
    /*
     * OVERRIDDEN METHODS
     */
    //=================================================================
    /**
     * Creates all buttons on the menu.
     */
    @Override
    public void setup() {
        super.setup();

        addButton("Start Game");
        addButton("Exit");
    }

    /**
     * Doesn't do anything for now.
     */
    @Override
    public void cleanUp() {
    }

    /**
     * Updates the menuItems until the play button has been pressed. Checks for
     * actions on the specified buttons.
     *
     * @param delta
     */
    @Override
    public void update(float delta) {
        super.update(delta);

        for (int i = 0; i < menuItems.size(); i++) {
            menuItems.get(i).update(delta);
            if (menuItems.get(i) instanceof Button) {
                if (((Button) menuItems.get(i)).isLeftMouseDown() && menuItems.get(i).getText().equals("Start Game")) {
                    getSceneManager().setCurrentScene(SceneIDs.GAMEPLAY_SCENE_ID);
                }
            }
        }
    }

    /**
     * Renders the menuItems until the play button has been pressed.
     */
    @Override
    public void render() {
        super.render();

        for (int i = 0; i < menuItems.size(); i++) {
            menuItems.get(i).render();
        }

    }

    //=================================================================
    /*
     * PRIVATE METHODS
     */
    //=================================================================
    /**
     * Places a button in the menuItem list, and automatically calculates the
     * next y-coordinate based on the height of this element and the previous
     * y-coordinate.
     *
     * @param text
     */
    private void addButton(String text) {
        w = FontLoader.getFont(fontName).getWidth(text);
        h = FontLoader.getFont(fontName).getHeight(text);

        float x = (Display.getWidth() - w) / 2;
        y += 2 * h;

        menuItems.add(new Button(text, x, y, w, h, fontName));
    }
}
