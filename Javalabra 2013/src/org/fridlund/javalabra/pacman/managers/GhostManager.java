/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.managers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.fridlund.javalabra.pacman.entities.Ghost;
import org.fridlund.javalabra.pacman.entities.Pacman;
import org.fridlund.javalabra.pacman.levels.Level;
import org.fridlund.javalabra.pacman.scenes.GameplayScene;

/**
 *
 * @author Christoffer
 */
public class GhostManager extends Manager {

    private Map<Integer, Ghost> ghosts;
    private float spawnInterval; // ms
    private float spawnTimer;

    public GhostManager(GameplayScene game, Pacman pacman, Level level) {
        super(game, pacman, level);
        this.ghosts = new HashMap<>();

        this.spawnInterval = 5000.0f;
        this.spawnTimer = 0.0f;

        this.spawnGhost();
    }

    @Override
    public void cleanUp() {
        for (int i : ghosts.keySet()) {
            ghosts.get(i).cleanUp();
        }
    }

    /**
     * This method chooses what color the spawned ghost shall have
     */
    private void spawnGhost() {
        for (int color = 0; color < 4; color++) {
            if (!ghosts.containsKey(color)) {
                ghosts.put(color, new Ghost(level, color));
                break;
            }
        }
    }

    public void setAllGhostsKillable() {
        for (int key : ghosts.keySet()) {
            ghosts.get(key).setKillable();
        }
    }

    public void activateWarningOnGhosts() {
        for (int key : ghosts.keySet()) {
            ghosts.get(key).setWarningAnimation();
        }
    }

    public void setAllGhostsInvincible() {
        for (int key : ghosts.keySet()) {
            ghosts.get(key).setInvincible();
        }
    }

    @Override
    public void update(float delta) {
        spawnTimer += delta;

        if (ghosts.size() < 4 && spawnTimer >= spawnInterval) {
            spawnTimer = 0.0f;
            spawnGhost();
        }

        boolean[] killed = new boolean[4];
        Arrays.fill(killed, false);

        for (int key : ghosts.keySet()) {
            ghosts.get(key).update(delta);

            if (pacman.collision(ghosts.get(key))) {
                if (ghosts.get(key).isInvincible()) {
                    // remove one life from pacman
                    // remove a given amount of points from the current score
                    pacman.kill();
                    pacman.removePoints(100);

                    // check if packman has anymore lives, and if he does, respawn him and the ghosts
                    if (pacman.getLives() != 0) {
                        pacman.spawn();
                        Arrays.fill(killed, true);
                        // force the ghostTimer to its maximum to make a ghost respawn directly
                        spawnTimer = spawnInterval;
                    } else {
                        game.setGameOver("Game Over");
                    }
                } else {
                    ghosts.get(key).kill();
                    pacman.addPoints(200);
                    killed[key] = ghosts.get(key).isDead();
                }
            }
        }

        for (int i = 0; i < killed.length; i++) {
            if (killed[i]) {
                ghosts.remove(i);
            }
        }
    }

    @Override
    public void render() {
        for (int key : ghosts.keySet()) {
            ghosts.get(key).render();
        }
    }
}
