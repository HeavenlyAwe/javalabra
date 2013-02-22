/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.pacman.scenes;

import java.util.Collection;
import java.util.Iterator;
import org.fridlund.javalabra.game.scenes.MenuScene;
import org.fridlund.javalabra.game.scenes.menus.Action;
import org.fridlund.pacman.highscores.HighScore;
import org.fridlund.pacman.highscores.HighScoreManager;
import org.fridlund.pacman.input.MenuInputProfile;

/**
 * Scene showing the top five high scores and a back button.
 *
 * @author Christoffer
 */
public class HighScoreScene extends MenuScene {

    private MenuInputProfile inputProfile;
    private HighScoreManager highScoreManager;
    private String fontName = "times30";

    /**
     *
     * @param id
     * @param inputProfile
     * @param highScoreManager
     */
    public HighScoreScene(int id, MenuInputProfile inputProfile, HighScoreManager highScoreManager) {
        super(id, "High Scores", "times50");
        this.inputProfile = inputProfile;
        this.highScoreManager = highScoreManager;
    }

    //=================================================================
    /*
     * OVERRIDDEN METHODS
     */
    //=================================================================
    /**
     *
     */
    @Override
    public void cleanUp() {
    }

    /**
     * Called when the menu is showed. Replaces all the buttons (a little bit
     * inefficient, when they are recreated)
     */
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
        addButton("Back", new Action() {
            @Override
            public void execute() {
                getSceneManager().setCurrentScene(SceneIDs.MAIN_MENU_SCENE_ID);
            }
        }, fontName);
    }

    /**
     * Calls the inputProfile's setMenuItems method, before updating the
     * inputProfile.
     *
     * @param delta
     */
    @Override
    public void update(float delta) {
        inputProfile.setMenuItems(menuItems);
        inputProfile.update(delta);
    }
}
