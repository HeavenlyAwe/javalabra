/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.backman.scenes;

import org.fridlund.backman.input.MenuInputProfile;
import org.fridlund.javalabra.game.scenes.MenuScene;
import org.fridlund.javalabra.game.scenes.menus.Action;

/**
 * Scene that is shown, whenever the player dies.
 *
 * @author Christoffer
 */
public class GameOverScene extends MenuScene {

    private MenuInputProfile inputProfile;
    private String fontName = "times30";

    /**
     *
     * @param id
     * @param inputProfile
     */
    public GameOverScene(int id, MenuInputProfile inputProfile) {
        super(id, "Game Over", "times50");
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
     * Replaces the menuItems every time the scene is shown.
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
