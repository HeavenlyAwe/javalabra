/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.fridlund.backman.highscores.HighScoreManager;
import org.fridlund.backman.input.MenuInputProfile;
import org.fridlund.backman.scenes.GameOverScene;
import org.fridlund.backman.scenes.GameWonScene;
import org.fridlund.backman.scenes.GameplayScene;
import org.fridlund.backman.scenes.HighScoreScene;
import org.fridlund.backman.scenes.MainMenuScene;
import org.fridlund.backman.scenes.SceneIDs;
import org.fridlund.javalabra.game.Game;
import org.fridlund.javalabra.game.scenes.SceneManager;
import org.fridlund.javalabra.game.utils.FontLoader;

/**
 * The extended game, this starts the main menu scene
 *
 * @author Christoffer
 */
public class BackmanGame extends Game {

    private HighScoreManager highScoreManager;
    private SceneManager sceneManager;

    /**
     *
     */
    public BackmanGame() {
        super("Backman");
    }

    //=================================================================
    /*
     * OVERRIDDEN METHODS
     */
    //=================================================================
    /**
     * Setups all necessary stuff for the game to be able to run.
     */
    @Override
    public void setup() {
        FontLoader.loadFont("times30", "Times New Roman", FontLoader.FontStyle.PLAIN, 30);
        FontLoader.loadFont("times50", "Times New Roman", FontLoader.FontStyle.BOLD, 50);

        highScoreManager = new HighScoreManager();

        MenuInputProfile inputProfile = new MenuInputProfile();

        sceneManager = new SceneManager();
        sceneManager.addScene(new MainMenuScene(SceneIDs.MAIN_MENU_SCENE_ID, this, inputProfile));
        sceneManager.addScene(new GameplayScene(SceneIDs.GAMEPLAY_SCENE_ID, highScoreManager));
        sceneManager.addScene(new HighScoreScene(SceneIDs.HIGH_SCORES_SCENE_ID, inputProfile, highScoreManager));
        sceneManager.addScene(new GameOverScene(SceneIDs.GAME_OVER_SCENE_ID, inputProfile));
        sceneManager.addScene(new GameWonScene(SceneIDs.GAME_WON_SCENE_ID, inputProfile));

        sceneManager.setCurrentScene(0);
    }

    /**
     *
     */
    @Override
    public void cleanUp() {
        FontLoader.cleanUp();
        sceneManager.cleanUp();
    }

    /**
     *
     * @param delta
     */
    @Override
    public void update(float delta) {
        super.update(delta);
        sceneManager.update(delta);
    }

    /**
     *
     */
    @Override
    public void render() {
        super.render();
        sceneManager.render();
    }
}
