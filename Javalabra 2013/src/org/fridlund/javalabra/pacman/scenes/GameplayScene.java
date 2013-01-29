/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.scenes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.fridlund.javalabra.game.entities.Entity;
import org.fridlund.javalabra.game.scenes.Scene;
import org.fridlund.javalabra.game.utils.FontLoader;
import org.fridlund.javalabra.pacman.entities.Ghost;
import org.fridlund.javalabra.pacman.entities.Pacman;
import org.fridlund.javalabra.pacman.entities.Snack;
import org.fridlund.javalabra.pacman.entities.SuperSnack;
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
    private Manager ghostManager;
    private Map<Integer, Entity> ghosts;
    private Level level;
    private Pacman pacman;
    public static float offsetDrawX;
    public static float offsetDrawY;
    private String gameOverMessage = "";
    // fields used for spawning new ghosts
    private float ghostTimer = 0.0f;
    private float killingGhostTimer = -1.0f;
    private float killingGhostTimerMax = 10000; // 10 seconds
    private boolean warningActivated = false; // used for activating the blinking effect on the ghosts
    private boolean[] killedGhosts = new boolean[4];
    private boolean gameOver = false;

    @Override
    public void setup() {
        level = new Level();

        offsetDrawX = (Display.getWidth() - level.getWidth()) / 2;
        offsetDrawY = (Display.getHeight() - level.getHeight()) / 2;

        pacman = new Pacman(level);

        snackManager = new SnackManager(this, pacman, level);
        ghostManager = new GhostManager(this, pacman, level);

        ghosts = new HashMap<>();
//        spawnGhost();
    }

    /**
     * This method chooses what color the spawned ghost shall have
     */
    private void spawnGhost() {
        for (int i = 0; i < 4; i++) {
            if (!ghosts.containsKey(i)) {
                ghosts.put(i, new Ghost(level, i));
                break;
            }
        }
    }

    /**
     * OpenGL cleanup code. If you load resources that Java garbage handler
     * doesn't normally handle, then you'll have to release those resources
     * manually.
     */
    @Override
    public void cleanUp() {
        pacman.cleanUp();
        for (int key : ghosts.keySet()) {
            ghosts.get(key).cleanUp();
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (!gameOver) {

            pacman.update(delta);
            snackManager.update(delta);
            ghostManager.update(delta);


//            checkIfGhostIsKillable(delta);
//            pacman.update(delta);
//            Arrays.fill(killedGhosts, false);
//            updateGhosts(delta);
//            deadGhostHandling(delta);
        }
    }

    private void checkIfGhostIsKillable(float delta) {
        if (killingGhostTimer >= 0 && killingGhostTimer <= killingGhostTimerMax) {
            killingGhostTimer += delta;
            if (killingGhostTimer >= killingGhostTimerMax - 2000 && warningActivated == false) {
                warningActivated = true;
                for (int ghostColor : ghosts.keySet()) {
                    ((Ghost) ghosts.get(ghostColor)).setWarningAnimation();
                }
            }
        } else {
            killingGhostTimer = -1.0f;
            warningActivated = false;
            for (int ghostColor : ghosts.keySet()) {
                ((Ghost) ghosts.get(ghostColor)).setInvincible();
            }
        }
    }

    private void updateGhosts(float delta) {
        for (int key : ghosts.keySet()) {
            ghosts.get(key).update(delta);

            if (pacman.collision(ghosts.get(key))) {
                // a collision has occured with a ghost

                // If pacman is in invincible mode after he has eaten a SuperSnack, he is able to kill the ghosts
                // else they are able to kill him on touch
                if (!((Ghost) ghosts.get(key)).isInvincible()) {
                    killedGhosts[key] = true;
                } else {

                    // remove one life from pacman
                    // remove a given amount of points from the current score
                    pacman.kill();
                    pacman.removePoints(100);

                    // check if packman has anymore lives, and if he does, respawn him and the ghosts
                    if (pacman.getLives() != 0) {
                        pacman.spawn();
                        Arrays.fill(killedGhosts, true);
                        // force the ghostTimer to its maximum to make a ghost respawn directly
                        ghostTimer = 5000;
                    } else {
                        gameOver = true;
                        gameOverMessage = "Game Over!";
                    }
                }
            }
        }
    }

    private void deadGhostHandling(float delta) {
        // Remove all ghosts that are killed during this update
        for (int i = 0; i < killedGhosts.length; i++) {
            if (killedGhosts[i]) {
                ghosts.remove(i);
            }
        }

        // Respawn a new ghost every 5 seconds. When there are 4 ghosts it wont spawn anymore
        ghostTimer += delta;
        if (ghostTimer >= 5000) {
            if (ghosts.size() < 4) {
                spawnGhost();
            }
            ghostTimer = 0;
        }
    }

    @Override
    public void render() {
        super.render();

        level.render();
        pacman.render();


        snackManager.render();
        ghostManager.render();

//        renderSnacks();
//        renderGhosts();
        renderPacmanStats();

        // fix this to show correct message when game is over
        int w = FontLoader.getFont("times new roman").getWidth(gameOverMessage);
        int h = FontLoader.getFont("times new roman").getHeight(gameOverMessage);

        FontLoader.renderString(gameOverMessage, (Display.getWidth() - w) / 2, (Display.getHeight() - h) / 2, "times new roman");
    }

    private void renderGhosts() {
        for (int key : ghosts.keySet()) {
            ghosts.get(key).render();
        }
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
