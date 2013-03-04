/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.backman.scenes;

import org.fridlund.backman.input.MenuInputProfile;
import org.fridlund.javalabra.game.scenes.MenuScene;
import org.fridlund.javalabra.game.scenes.menus.Action;

/**
 * Scene that is shown whenever a player wins the game.
 *
 * @author Christoffer
 */
public class GameWonScene extends MenuScene {

    private MenuInputProfile inputProfile;
    private String fontName = "times30";

    /**
     *
     * @param id
     * @param inputProfile
     */
    public GameWonScene(int id, MenuInputProfile inputProfile) {
        super(id, "Victory", "times50");
        this.inputProfile = inputProfile;
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
     * Replaces all the buttons on the correct position.
     */
    @Override
    public void show() {
        super.show();

        y = 100;
        addButton("Play Again", new Action() {
            @Override
            public void execute() {
                getSceneManager().setCurrentScene(SceneIDs.GAMEPLAY_SCENE_ID);
            }
        }, fontName);
        addButton("Exit", new Action() {
            @Override
            public void execute() {
                getSceneManager().setCurrentScene(SceneIDs.MAIN_MENU_SCENE_ID);
            }
        }, fontName);
    }

    /**
     * Calls the inputProfile.setMenuItems() method before updating the
     * inputProfile
     *
     * @param delta
     */
    @Override
    public void update(float delta) {
        inputProfile.setMenuItems(menuItems);
        inputProfile.update(delta);
    }
}
