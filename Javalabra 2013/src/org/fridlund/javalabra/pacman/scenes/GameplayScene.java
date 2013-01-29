/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.scenes;

import org.fridlund.javalabra.game.scenes.Scene;
import org.fridlund.javalabra.game.utils.FontLoader;
import org.fridlund.javalabra.pacman.entities.Pacman;
import org.fridlund.javalabra.pacman.levels.Level;
import org.fridlund.javalabra.pacman.managers.GhostManager;
import org.fridlund.javalabra.pacman.managers.Manager;
import org.fridlund.javalabra.pacman.managers.SnackManager;
import org.lwjgl.opengl.Display;

/**
 *
 * @author Christoffer
 */
public class GameplayScene extends Scene {

    private Manager snackManager;
    private GhostManager ghostManager;
    private Level level;
    private Pacman pacman;
    public static float offsetDrawX;
    public static float offsetDrawY;
    private String gameOverMessage = "";
    private boolean gameOver = false;

    @Override
    public void setup() {
        level = new Level();

        offsetDrawX = (Display.getWidth() - level.getWidth()) / 2;
        offsetDrawY = (Display.getHeight() - level.getHeight()) / 2;

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

        level.render();
        pacman.render();

        snackManager.render();
        ghostManager.render();

        renderPacmanStats();

        // fix this to show correct message when game is over
        int w = FontLoader.getFont("times new roman").getWidth(gameOverMessage);
        int h = FontLoader.getFont("times new roman").getHeight(gameOverMessage);

        FontLoader.renderString(gameOverMessage, (Display.getWidth() - w) / 2, (Display.getHeight() - h) / 2, "times new roman");
    }

    private void renderPacmanStats() {
        FontLoader.renderString("Points: " + pacman.getPoints(), 10, 10, "times new roman");
        FontLoader.renderString("Lives: " + pacman.getLives(), 10, 35, "times new roman");
    }

    public void setGameOver(String message) {
        this.gameOver = true;
        this.gameOverMessage = message;
    }
}
