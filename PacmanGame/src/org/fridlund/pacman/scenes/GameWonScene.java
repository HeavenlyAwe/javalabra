/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.pacman.scenes;

import org.fridlund.javalabra.game.scenes.MenuScene;
import org.fridlund.javalabra.game.scenes.menus.Action;
import org.fridlund.javalabra.game.scenes.menus.Button;
import org.fridlund.pacman.input.MenuInputProfile;

/**
 *
 * @author Christoffer
 */
public class GameWonScene extends MenuScene {
    
    private MenuInputProfile inputProfile;
    private String fontName = "times30";
    
    public GameWonScene(int id, MenuInputProfile inputProfile) {
        super(id, "Victory", "times50");
        this.inputProfile = inputProfile;
    }
    
    @Override
    public void cleanUp() {
    }
    
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
    
    @Override
    public void update(float delta) {
        inputProfile.setMenuItems(menuItems);
        inputProfile.update(delta);
    }
}
