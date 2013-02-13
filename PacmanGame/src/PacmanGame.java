/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.fridlund.javalabra.game.GameLWJGL;
import org.fridlund.javalabra.game.scenes.SceneManager;
import org.fridlund.javalabra.game.utils.FontLoader;
import org.fridlund.pacman.highscores.HighScoreManager;
import org.fridlund.pacman.scenes.GameOverScene;
import org.fridlund.pacman.scenes.GameplayScene;
import org.fridlund.pacman.scenes.HighScoreScene;
import org.fridlund.pacman.scenes.MainMenuScene;
import org.fridlund.pacman.scenes.SceneIDs;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

/**
 * The extended game, this starts the main menu scene
 *
 * @author Christoffer
 */
public class PacmanGame extends GameLWJGL {
    
    private HighScoreManager highScoreManager;
    private SceneManager sceneManager;
    
    public PacmanGame() {
        super("Pacman");
    }

    //=================================================================
    /*
     * OVERRIDDEN METHODS
     */
    //=================================================================
    @Override
    public void setup() {
        FontLoader.loadFont("times30", "Times New Roman", FontLoader.FontStyle.PLAIN, 30);
        FontLoader.loadFont("times50","Times New Roman", FontLoader.FontStyle.BOLD, 50);
        
        highScoreManager = new HighScoreManager();
        
        sceneManager = new SceneManager();
        sceneManager.addScene(new MainMenuScene(SceneIDs.MAIN_MENU_SCENE_ID));
        sceneManager.addScene(new GameplayScene(SceneIDs.GAMEPLAY_SCENE_ID, highScoreManager));
        sceneManager.addScene(new HighScoreScene(SceneIDs.HIGH_SCORES_SCENE_ID, highScoreManager));
        sceneManager.addScene(new GameOverScene(SceneIDs.GAME_OVER_SCENE_ID, highScoreManager));
        
        sceneManager.setCurrentScene(0);
    }
    
    @Override
    public void cleanUp() {
        sceneManager.cleanUp();
    }
    
    @Override
    public void update(float delta) {
        super.update(delta);
        sceneManager.update(delta);
    }
    
    @Override
    public boolean gameLoopRestrictions() {
        return !Display.isActive() || !Mouse.isInsideWindow();
    }
    
    @Override
    public void render() {
        super.render();
        sceneManager.render();
    }
}
