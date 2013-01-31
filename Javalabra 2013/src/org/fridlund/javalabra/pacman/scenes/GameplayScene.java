/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.scenes;

import org.fridlund.javalabra.game.Screen;
import org.fridlund.javalabra.game.scenes.Scene;
import org.fridlund.javalabra.game.utils.FontLoader;
import org.fridlund.javalabra.pacman.cameras.PacmanCamera;
import org.fridlund.javalabra.pacman.entities.Pacman;
import org.fridlund.javalabra.pacman.levels.Level;
import org.fridlund.javalabra.pacman.managers.GhostManager;
import org.fridlund.javalabra.pacman.managers.Manager;
import org.fridlund.javalabra.pacman.managers.SnackManager;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Christoffer
 */
public class GameplayScene extends Scene {

    private PacmanCamera fpsCamera;
    private Manager snackManager;
    private GhostManager ghostManager;
    private Level level;
    private Pacman pacman;
    private String gameOverMessage = "";
    private boolean gameOver = false;

    @Override
    public void setup() {
        level = new Level();

//        fpsCamera = new FirstPersonCamera((float) Display.getWidth() / (float) Display.getHeight(),
//                new Vector3f(Display.getWidth() / 2, Display.getHeight() / 2, -300),
//                new Vector3f(0, 180, 180));
//        fpsCamera.applyProjectionMatrix();
        fpsCamera = new PacmanCamera((float) Display.getWidth() / (float) Display.getHeight(),
                300, new Vector3f(level.getWidth() / 2, level.getHeight() / 2, 300),
                new Vector3f(level.getWidth() / 2, level.getHeight() / 2, 0));
        fpsCamera.applyProjectionMatrix();



        pacman = new Pacman(level);

        snackManager = new SnackManager(this, pacman, level);
        ghostManager = new GhostManager(this, pacman, level);
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

    @Override
    public void update(float delta) {
        super.update(delta);

        fpsCamera.update(delta);
        if (Mouse.isButtonDown(0)) {
            fpsCamera.rotateLeft();
//            Mouse.setGrabbed(true);
        } else if (Mouse.isButtonDown(1)) {
            fpsCamera.rotateRight();
//            Mouse.setGrabbed(false);
        }


        if (!gameOver) {

            pacman.update(delta);
            snackManager.update(delta);
            ghostManager.update(delta);
        }
    }

    public void superSnackEaten() {
        ghostManager.setAllGhostsKillable();
    }

    public void setWarningOfSuperSnackEffectSoonGone() {
        ghostManager.activateWarningOnGhosts();
    }

    public void setGhostsInvincible() {
        ghostManager.setAllGhostsInvincible();
    }

    @Override
    public void render() {
        super.render();

        fpsCamera.applyModelViewMatrix(true);

        level.render();
        pacman.render();

        snackManager.render();
        ghostManager.render();

        renderBorder();

        renderPacmanStats();

        // fix this to show correct message when game is over
        int w = FontLoader.getFont("times new roman").getWidth(gameOverMessage);
        int h = FontLoader.getFont("times new roman").getHeight(gameOverMessage);

        FontLoader.renderString(gameOverMessage, (Display.getWidth() - w) / 2, (Display.getHeight() - h) / 2, "times new roman");
    }

    private void renderBorder() {
        float x = -32;
        float y = -32;
        float w = level.getWidth() + 32;
        float h = level.getHeight() + 32;

        glBegin(GL_QUADS);
        {
            glColor3f(0, 0, 0);

//            // bottom border
//            glVertex2f(0, 0);
//            glVertex2f(level.getWidth(), 0);
//            glVertex2f(level.getWidth(), -32);
//            glVertex2f(0, -32);

            // left border
            glVertex2f(0, 0);
            glVertex2f(-32, 0);
            glVertex2f(-32, level.getHeight());
            glVertex2f(0, level.getHeight());

//            // top border
//            glVertex2f(0, level.getHeight());
//            glVertex2f(0, level.getHeight() + 32);
//            glVertex2f(level.getWidth(), level.getHeight() + 32);
//            glVertex2f(level.getWidth(), level.getHeight());

            // right border
            glVertex2f(level.getWidth(), 0);
            glVertex2f(level.getWidth(), level.getHeight());
            glVertex2f(level.getWidth() + 32, level.getHeight());
            glVertex2f(level.getWidth() + 32, 0);

        }
        glEnd();
    }

    private void renderPacmanStats() {
        Screen.applyProjectionMatrix();

        FontLoader.renderString("Points: " + pacman.getPoints(), 10, 10, "times new roman");
        FontLoader.renderString("Lives: " + pacman.getLives(), 10, 35, "times new roman");

        fpsCamera.applyProjectionMatrix();
    }

    public void setGameOver(String message) {
        this.gameOver = true;
        this.gameOverMessage = message;
    }
}
