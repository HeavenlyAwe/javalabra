/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.backman.scenes;

import org.fridlund.backman.input.MenuInputProfile;
import org.fridlund.javalabra.game.Game;
import org.fridlund.javalabra.game.scenes.MenuScene;
import org.fridlund.javalabra.game.scenes.menus.Action;

/**
 * Main menu, contains buttons and eventual graphics related to the menu. It's
 * from here the game is started.
 *
 * @author Christoffer
 */
public class MainMenuScene extends MenuScene {

    private Game game;
    private MenuInputProfile inputProfile;
    /**
     *
     */
    public static final String fontName = "times30";

    /**
     *
     * @param id
     * @param game
     * @param inputProfile
     */
    public MainMenuScene(int id, Game game, MenuInputProfile inputProfile) {
        super(id, "Main Menu", "times50");
        this.game = game;
        this.inputProfile = inputProfile;
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

        y = 100;

        addButton("Start Game", new Action() {
            @Override
            public void execute() {
                getSceneManager().setCurrentScene(SceneIDs.GAMEPLAY_SCENE_ID);
            }
        }, "times30");
        addButton("High Scores", new Action() {
            @Override
            public void execute() {
                getSceneManager().setCurrentScene(SceneIDs.HIGH_SCORES_SCENE_ID);
            }
        }, "times30");
        addButton("Quit", new Action() {
            @Override
            public void execute() {
                game.stop();
            }
        }, "times30");
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

        inputProfile.setMenuItems(menuItems);
        inputProfile.update(delta);
    }

    /**
     * Renders the menuItems until the play button has been pressed.
     */
    @Override
    public void render() {
        super.render();
    }
}
