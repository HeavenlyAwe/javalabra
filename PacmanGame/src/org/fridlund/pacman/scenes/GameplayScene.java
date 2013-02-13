/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.pacman.scenes;

import org.fridlund.javalabra.game.Screen;
import org.fridlund.javalabra.game.scenes.Scene;
import org.fridlund.javalabra.game.utils.FontLoader;
import org.fridlund.pacman.cameras.PacmanCamera;
import org.fridlund.pacman.entities.Pacman;
import org.fridlund.pacman.highscores.HighScore;
import org.fridlund.pacman.highscores.HighScoreManager;
import org.fridlund.pacman.hud.Hud;
import org.fridlund.pacman.input.PacmanInputProfile;
import org.fridlund.pacman.level.Level;
import org.fridlund.pacman.managers.GhostManager;
import org.fridlund.pacman.managers.Manager;
import org.fridlund.pacman.managers.SnackManager;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Vector3f;

/**
 * Contains the Pacman, GhostManager, SnackManager and a Camera for showing the
 * world in the center. Also listens to a InputProfile, so no input is handled
 * here.
 *
 * @author Christoffer
 */
public class GameplayScene extends Scene {

    private HighScoreManager highScoreManager;
    private Hud hud;
    private PacmanCamera camera;
    private Manager snackManager;
    private GhostManager ghostManager;
    private Level level;
    private PacmanInputProfile input;
    private Pacman pacman;
    private String gameOverMessage;
    private boolean gameOver;

    public GameplayScene(int id, HighScoreManager highScoreManager) {
        super(id);
        this.highScoreManager = highScoreManager;
    }

    //=================================================================
    /*
     * OVERRIDDEN METHODS
     */
    //=================================================================
    /**
     * Initializes all game play specific variables. <ul> <li>Level</li>
     * <li>Camera</li> <li>Pacman</li> <li>InputProfile</li> <li>HUD</li>
     * <li>GhostManager</li> <li>SnackManager</li> </ul>
     */
    @Override
    public void setup() {
    }

    @Override
    public void show() {
        level = new Level();

        gameOverMessage = "";
        gameOver = false;

        camera = new PacmanCamera((float) Display.getWidth() / (float) Display.getHeight(),
                300, new Vector3f(level.getWidth() / 2, level.getHeight() / 2, 300),
                new Vector3f(level.getWidth() / 2, level.getHeight() / 2, 0));

        pacman = new Pacman(level);
        input = new PacmanInputProfile(this, pacman, level);

        hud = new Hud(level, pacman);

        snackManager = new SnackManager(this, pacman, level);
        ghostManager = new GhostManager(this, pacman, level, 16);
    }

    /**
     * OpenGL cleanup code. If you load resources that Java garbage handler
     * doesn't normally handle, then you'll have to release those resources
     * manually.
     */
    @Override
    public void cleanUp() {
        pacman.cleanUp();
        snackManager.cleanUp();
        ghostManager.cleanUp();
    }

    /**
     * Updates the initialized components. Only updates the camera if the game
     * is over. This makes it possible to rotate the view even though the game
     * is over. <br> <br> Updates the components in this order: <br> Camera,
     * Input, Pacman, SnackManager, GhostManager
     *
     * @param delta
     */
    @Override
    public void update(float delta) {
        super.update(delta);

        camera.update(delta);

        if (!gameOver) {

            input.update(delta);
            pacman.update(delta);
            snackManager.update(delta);
            ghostManager.update(delta);
        }
    }

    /**
     * Applies the model view matrix to the camera, for updating all changes to
     * the graphics card and after that renders all stuff in the following
     * order: <br> Level, Pacman, SnackManager, GhostManager. <br> <br> Also
     * renders a couple of black stripes at the sides of the world, to hide
     * Pacman when he teleports from one side to the other.
     */
    @Override
    public void render() {
        super.render();

        camera.applyProjectionMatrix();
        camera.applyModelViewMatrix(true);

        level.render();
        pacman.render();

        snackManager.render();
        ghostManager.render();

        renderBorder();
        renderHud();

        Screen.applyProjectionMatrix();
    }

    //=================================================================
    /*
     * PRIVATE METHODS
     */
    //=================================================================
    /**
     * Renders one stripe on both sides of the level, to hide Pacman from
     * showing when he leaves the board.
     */
    private void renderBorder() {

        glBegin(GL_QUADS);
        {
            glColor3f(0, 0, 0);
            // left border
            glVertex2f(0, 0);
            glVertex2f(-pacman.getWidth(), 0);
            glVertex2f(-pacman.getWidth(), level.getHeight());
            glVertex2f(0, level.getHeight());
            // right border
            glVertex2f(level.getWidth(), 0);
            glVertex2f(level.getWidth(), level.getHeight());
            glVertex2f(level.getWidth() + pacman.getWidth(), level.getHeight());
            glVertex2f(level.getWidth() + pacman.getWidth(), 0);

        }
        glEnd();
    }

    /**
     * Renders the few elements showing info to the player.
     */
    private void renderHud() {
        Screen.applyProjectionMatrix();

        hud.render();

        // fix this to show correct message when game is over
        int w = FontLoader.getFont("times new roman").getWidth(gameOverMessage);
        int h = FontLoader.getFont("times new roman").getHeight(gameOverMessage);

        FontLoader.renderString(gameOverMessage, (Display.getWidth() - w) / 2, (Display.getHeight() - h) / 2, "times new roman");

        camera.applyProjectionMatrix();
    }

    //=================================================================
    /*
     * PUBLIC METHODS
     */
    //=================================================================
    /**
     * Makes all ghosts killable in the GhostManager for a certain amount of
     * time (specified in the GhostManager) and also sets Pacman angry, to
     * change his animation.
     */
    public void superSnackEaten() {
        ghostManager.setAllGhostsKillable();
        pacman.setAngry(true);
    }

    /**
     * 3D effect of Camera turning 180 degrees to the left.
     */
    public void rotateCameraLeft() {
        camera.rotateLeft();
    }

    /**
     * 3D effect of Camera turning 180 degrees to the right
     */
    public void rotateCameraRight() {
        camera.rotateRight();
    }

    //=================================================================
    /*
     * SETTERS
     */
    //=================================================================
    /**
     * Used from the SnackManager to inform the GhostManager to make the ghosts
     * blink, because of super snack effect is soon over.
     */
    public void setWarningOfSuperSnackEffectSoonGone() {
        ghostManager.setWarningOnGhosts();
    }

    /**
     * Makes all ghosts in GhostManager invincible again.
     */
    public void setGhostsInvincible() {
        ghostManager.setAllGhostsInvincible();
        pacman.setAngry(false);
    }

    /**
     * Stops the update loop and sets the message to be drawn on the screen.
     *
     * @param message
     */
    public void setGameOver(String message) {
        this.gameOver = true;
        this.gameOverMessage = message;
        getSceneManager().setCurrentScene(SceneIDs.GAME_OVER_SCENE_ID);
        this.highScoreManager.addHighScore(new HighScore(pacman.getPoints()));
        this.highScoreManager.saveHighScore();
    }

    /**
     * Makes the GhostManager aware that the ghosts are allowed to leave the
     * nest (for instance, when the super snack effect is over, all killed
     * ghosts are allowed to return to the maze).
     *
     * @param releaseable
     */
    public void setGhostsReleaseable(boolean releaseable) {
        ghostManager.setGhostReleaseable(releaseable);
    }
}
