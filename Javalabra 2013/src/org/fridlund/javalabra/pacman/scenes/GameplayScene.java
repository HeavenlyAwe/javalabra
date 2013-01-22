/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.scenes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.fridlund.javalabra.game.entities.Entity;
import org.fridlund.javalabra.game.scenes.Scene;
import org.fridlund.javalabra.game.utils.FontLoader;
import org.fridlund.javalabra.pacman.entities.Ghost;
import org.fridlund.javalabra.pacman.entities.Pacman;
import org.fridlund.javalabra.pacman.levels.Level;
import org.lwjgl.opengl.Display;

/**
 *
 * @author Christoffer
 */
public class GameplayScene extends Scene {

    private Map<Integer, Entity> entities;
    private Level level;
    private Pacman pacman;
    private String message = "";
    public static float offsetDrawX;
    public static float offsetDrawY;

    @Override
    public void setup() {
        level = new Level();

        offsetDrawX = (Display.getWidth() - level.getWidth()) / 2;
        offsetDrawY = (Display.getHeight() - level.getHeight()) / 2;

        pacman = new Pacman(level);

        entities = new HashMap<>();
        spawnGhost();
    }

    private void spawnGhost() {
        for (int i = 0; i < 4; i++) {
            if (!entities.containsKey(i)) {
                entities.put(i, new Ghost(level, i));
                break;
            }
        }
    }

    @Override
    public void cleanUp() {
        pacman.cleanUp();
        for (int key : entities.keySet()) {
            entities.get(key).cleanUp();
        }
    }
    private float ghostTimer = 0.0f;
    private boolean[] killedGhosts = new boolean[4];

    @Override
    public void update(float delta) {
        super.update(delta);

        Arrays.fill(killedGhosts, false);

        pacman.update(delta);

        for (int key : entities.keySet()) {
            entities.get(key).update(delta);
            if (pacman.collision(entities.get(key))) {
                System.out.println("Collision: " + key);
                message = "inside entity";
                if (pacman.isInvincible()) {
                    killedGhosts[key] = true;
                } else {
                }
            } else {
                message = "";
            }
        }

        ghostTimer += delta;
        if (ghostTimer >= 5000) {
            if (entities.size() < 4) {
                spawnGhost();
            }
            ghostTimer = 0;
        }

        for (int i = 0; i < killedGhosts.length; i++) {
            if (killedGhosts[i]) {
                entities.remove(i);
            }
        }
    }

    @Override
    public void render() {
        super.render();

        level.render();

        pacman.render();
        for (int key : entities.keySet()) {
            entities.get(key).render();
        }

        FontLoader.renderString(message, 10, 10, "times new roman");
    }
}
