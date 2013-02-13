/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.pacman.scenes;

import org.fridlund.javalabra.game.scenes.MenuScene;
import org.fridlund.javalabra.game.scenes.menus.Button;
import org.fridlund.pacman.highscores.HighScoreManager;

/**
 *
 *
 * @author Christoffer
 */
public class GameOverScene extends MenuScene {

    private HighScoreManager highScoreManager;
    private String fontName = "times30";

    public GameOverScene(int id, HighScoreManager highScoreManager) {
        super(id, "Game Over", "times50");
        this.highScoreManager = highScoreManager;
    }

    //=================================================================
    /*
     * OVERRIDDEN METHODS
     */
    //=================================================================
    @Override
    public void setup() {
        super.setup();
    }

    @Override
    public void cleanUp() {
    }

    @Override
    public void show() {
        super.show();

        y += 100;
        addButton("Play Again", fontName);
        addButton("Exit", fontName);
    }

    @Override
    public void update(float delta) {
        for (int i = 0; i < menuItems.size(); i++) {
            menuItems.get(i).update(delta);
            if (menuItems.get(i) instanceof Button) {
                if (((Button) menuItems.get(i)).isLeftMouseDown() && menuItems.get(i).getText().equals("Play Again")) {
                    getSceneManager().setCurrentScene(SceneIDs.GAMEPLAY_SCENE_ID);
                } else if (((Button) menuItems.get(i)).isLeftMouseDown() && menuItems.get(i).getText().equals("Exit")) {
                    System.out.println("Clicked high scores button");
                    getSceneManager().setCurrentScene(SceneIDs.MAIN_MENU_SCENE_ID);
                }
            }
        }
    }
}
