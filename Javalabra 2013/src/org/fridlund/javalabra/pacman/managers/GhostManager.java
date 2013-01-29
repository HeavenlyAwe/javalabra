/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.managers;

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

    public GhostManager(GameplayScene game, Pacman pacman, Level level) {
        super(game, pacman, level);
        this.ghosts = new HashMap<>();
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

    @Override
    public void update(float delta) {
        for (int key : ghosts.keySet()) {
            ghosts.get(key).update(delta);
        }
    }

    @Override
    public void render() {
        for (int key : ghosts.keySet()) {
            ghosts.get(key).render();
        }
    }
}
