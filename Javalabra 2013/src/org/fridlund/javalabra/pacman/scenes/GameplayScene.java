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
import org.fridlund.javalabra.game.Screen;
import org.fridlund.javalabra.game.entities.Entity;
import org.fridlund.javalabra.game.scenes.Scene;
import org.fridlund.javalabra.game.utils.FontLoader;
import org.fridlund.javalabra.pacman.entities.Ghost;
import org.fridlund.javalabra.pacman.entities.Pacman;
import org.fridlund.javalabra.pacman.entities.Snack;
import org.fridlund.javalabra.pacman.entities.SuperSnack;
import org.fridlund.javalabra.pacman.levels.Level;
import org.lwjgl.opengl.Display;

/**
 *
 * @author Christoffer
 */
public class GameplayScene extends Scene {

    private Map<Integer, Entity> ghosts;
    private List<Entity> snacks;
    private Level level;
    private Pacman pacman;
    public static float offsetDrawX;
    public static float offsetDrawY;
    private String gameOverMessage = "";

    @Override
    public void setup() {
        level = new Level();

        offsetDrawX = (Display.getWidth() - level.getWidth()) / 2;
        offsetDrawY = (Display.getHeight() - level.getHeight()) / 2;

        pacman = new Pacman(level);

        ghosts = new HashMap<>();
        snacks = new ArrayList<>();

        spawnSnacks();
        spawnGhost();
    }

    private void spawnSnacks() {

        ArrayList<Integer> allowedTiles = new ArrayList<>();
        allowedTiles.add(Level.WALKABLE);
        Snack snack;
        for (int y = 0; y < level.getHeight() / level.getTileHeight(); y++) {
            for (int x = 0; x < level.getWidth() / level.getTileWidth(); x++) {

                if (x == 2 && y == 2 || x == 2 && y == level.getHeight() / level.getTileHeight() - 2 || x == level.getWidth() / level.getTileWidth() - 2 && y == level.getHeight() / level.getTileHeight() - 2) {
                    snack = new SuperSnack();
                } else {
                    snack = new Snack();
                }
                if (level.walkableTile(snack, x * level.getTileWidth() - 0.5f * level.getTileWidth(), y * level.getTileHeight() - 0.5f * level.getTileHeight(), allowedTiles)) {
                    snack.setPosition(x * level.getTileWidth() - 0.5f * level.getTileWidth(), y * level.getTileHeight() - 0.5f * level.getTileHeight());
                    snacks.add(snack);
                }

            }
        }
    }

    private void spawnGhost() {
        for (int i = 0; i < 4; i++) {
            if (!ghosts.containsKey(i)) {
                ghosts.put(i, new Ghost(level, i));
                break;
            }
        }
    }

    @Override
    public void cleanUp() {
        pacman.cleanUp();
        for (int key : ghosts.keySet()) {
            ghosts.get(key).cleanUp();
        }
    }
    private float ghostTimer = 0.0f;
    private boolean[] killedGhosts = new boolean[4];
    private boolean gameOver = false;

    @Override
    public void update(float delta) {
        super.update(delta);

        if (!gameOver) {

            Arrays.fill(killedGhosts, false);

            pacman.update(delta);

            for (int i = 0; i < snacks.size(); i++) {
                if (pacman.collision(snacks.get(i))) {
                    snacks.remove(i);
                    i--;
                    pacman.addPoints(10);
                }
            }

            for (int key : ghosts.keySet()) {
                ghosts.get(key).update(delta);

                if (pacman.collision(ghosts.get(key))) {
                    // a collision has occured with a ghost

                    // If pacman is in invincible mode after he has eaten a SuperSnack, he is able to kill the ghosts
                    // else they are able to kill him on touch
                    if (pacman.isInvincible()) {
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
    }

    @Override
    public void render() {
        super.render();

        level.render();
        pacman.render();

        for (int i = 0; i < snacks.size(); i++) {
            snacks.get(i).render();
        }

        for (int key : ghosts.keySet()) {
            ghosts.get(key).render();
        }

        FontLoader.renderString("Points: " + pacman.getPoints(), 10, 10, "times new roman");
        FontLoader.renderString("Lives: " + pacman.getLives(), 10, 35, "times new roman");

        // fix this to show correct message when game is over
        int w = FontLoader.getFont("times new roman").getWidth(gameOverMessage);
        int h = FontLoader.getFont("times new roman").getHeight(gameOverMessage);

        FontLoader.renderString(gameOverMessage, (Display.getWidth() - w) / 2, (Display.getHeight() - h) / 2, "times new roman");
    }
}
