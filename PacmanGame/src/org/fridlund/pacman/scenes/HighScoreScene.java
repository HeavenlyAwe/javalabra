/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.pacman.scenes;

import java.util.Collection;
import java.util.Iterator;
import org.fridlund.javalabra.game.scenes.MenuScene;
import org.fridlund.javalabra.game.scenes.menus.Button;
import org.fridlund.pacman.highscores.HighScore;
import org.fridlund.pacman.highscores.HighScoreManager;

/**
 *
 * @author Christoffer
 */
public class HighScoreScene extends MenuScene {

    private HighScoreManager highScoreManager;
    private String fontName = "times30";

    public HighScoreScene(int id, HighScoreManager highScoreManager) {
        super(id, "High Scores", "times50");
        this.highScoreManager = highScoreManager;
    }

    @Override
    public void setup() {
        super.setup();
    }

    @Override
    public void show() {
        super.show();

        y = 100;

        this.highScoreManager.loadHighScore();
        Collection<HighScore> highScores = highScoreManager.getHighScoreList();
        Iterator<HighScore> scores = highScores.iterator();

        menuItems.clear();

        int maxScores = 5;
        int scoreCount = 0;
        while (scores.hasNext()) {
            if (scoreCount == maxScores) {
                break;
            }
            scoreCount++;
            addLabel("" + scores.next().getPoints(), fontName);
        }

        y += 50;
        addButton("Back", fontName);
    }

    @Override
    public void cleanUp() {
    }

    @Override
    public void update(float delta) {
        for (int i = 0; i < menuItems.size(); i++) {
            menuItems.get(i).update(delta);
            if (menuItems.get(i) instanceof Button) {
                if (((Button) menuItems.get(i)).isLeftMouseDown() && menuItems.get(i).getText().equals("Back")) {
                    getSceneManager().setCurrentScene(SceneIDs.MAIN_MENU_SCENE_ID);
                }
            }
        }
    }
}
