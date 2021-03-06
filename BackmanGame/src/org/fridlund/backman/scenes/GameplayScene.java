/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.backman.scenes;

import org.fridlund.javalabra.game.Screen;
import org.fridlund.javalabra.game.scenes.Scene;
import org.fridlund.backman.cameras.PacmanCamera;
import org.fridlund.backman.entities.Backman;
import org.fridlund.backman.highscores.HighScore;
import org.fridlund.backman.highscores.HighScoreManager;
import org.fridlund.backman.hud.DebugHud;
import org.fridlund.backman.hud.Hud;
import org.fridlund.backman.input.PacmanInputProfile;
import org.fridlund.backman.level.Level;
import org.fridlund.backman.managers.GhostManager;
import org.fridlund.backman.managers.Manager;
import org.fridlund.backman.managers.SnackManager;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Vector3f;

/**
 * Contains the Backman, GhostManager, SnackManager and a Camera for showing the
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
    private Backman pacman;
    private boolean paused;
    private DebugHud debugHud;
    private boolean debug;

    /**
     *
     * @param id
     * @param highScoreManager
     */
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
     * <li>Camera</li> <li>Backman</li> <li>InputProfile</li> <li>HUD</li>
     * <li>GhostManager</li> <li>SnackManager</li> </ul>
     */
    @Override
    public void setup() {
    }

    /**
     *
     */
    @Override
    public void show() {
        level = new Level();

        paused = false;

        camera = new PacmanCamera((float) Display.getWidth() / (float) Display.getHeight(),
                300, new Vector3f(level.getWidth() / 2, level.getHeight() / 2, 300),
                new Vector3f(level.getWidth() / 2, level.getHeight() / 2, 0));

        pacman = new Backman(level);
        input = new PacmanInputProfile(this, pacman, level);

        hud = new Hud(pacman);
        debugHud = new DebugHud();

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
     * Input, Backman, SnackManager, GhostManager
     *
     * @param delta
     */
    @Override
    public void update(float delta) {
        super.update(delta);

        camera.update(delta);
        input.update(delta);

        if (!paused) {
            pacman.update(delta);
            snackManager.update(delta);
            ghostManager.update(delta);
        }
    }

    /**
     * Applies the model view matrix to the camera, for updating all changes to
     * the graphics card and after that renders all stuff in the following
     * order: <br> Level, Backman, SnackManager, GhostManager. <br> <br> Also
     * renders a couple of black stripes at the sides of the world, to hide
     * Backman when he teleports from one side to the other.
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

        if (paused) {
            dimScreen();
        }

        renderHud();

        Screen.applyProjectionMatrix();
    }

    //=================================================================
    /*
     * PRIVATE METHODS
     */
    //=================================================================
    /**
     * Renders one stripe on both sides of the level, to hide Backman from
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

    private void dimScreen() {
        Screen.applyProjectionMatrix();
        glColor4f(0, 0, 0, 0.5f);
        glRectf(0, 0, Display.getWidth(), Display.getHeight());
        camera.applyProjectionMatrix();
    }

    /**
     * Renders the few elements showing info to the player.
     */
    private void renderHud() {
        Screen.applyProjectionMatrix();
        hud.render();
        if (debug) {
            debugHud.render();
        }
        camera.applyProjectionMatrix();
    }

    //=================================================================
    /*
     * PUBLIC METHODS
     */
    //=================================================================
    /**
     * Makes all ghosts killable in the GhostManager for a certain amount of
     * time (specified in the GhostManager) and also sets Backman angry, to
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

    /**
     *
     */
    public void pause() {
        paused = !paused;
    }

    /**
     *
     */
    public void toggleDebug() {
        debug = !debug;
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
     * Shows the game over menu, and your points won't get stored in the high
     * scores file.
     *
     */
    public void setGameOver() {
        this.paused = true;
        getSceneManager().setCurrentScene(SceneIDs.GAME_OVER_SCENE_ID);
    }

    /**
     * Basically the same menu as the game over, but now your points will get
     * stored in the high scores file.
     */
    public void setGameWon() {
        this.paused = true;
        this.highScoreManager.addHighScore(new HighScore(pacman.getPoints()));
        this.highScoreManager.saveHighScore();
        getSceneManager().setCurrentScene(SceneIDs.GAME_WON_SCENE_ID);
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

    //=================================================================
    /*
     * GETTERS
     */
    //=================================================================
    /**
     *
     * @return
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     *
     * @return
     */
    public boolean isDebug() {
        return debug;
    }
}
