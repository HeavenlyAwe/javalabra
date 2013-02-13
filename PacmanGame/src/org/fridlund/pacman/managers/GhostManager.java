/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.pacman.managers;

import java.util.HashMap;
import java.util.Map;
import org.fridlund.pacman.entities.Ghost;
import org.fridlund.pacman.entities.GhostGraphics;
import org.fridlund.pacman.entities.Pacman;
import org.fridlund.pacman.level.Level;
import org.fridlund.pacman.scenes.GameplayScene;

/**
 * Handles when the ghosts should get out of the nest, and is responsible for
 * updating and rendering the ghosts. Also handles collision detection between
 * ghosts and Pacman.
 *
 * @author Christoffer
 */
public class GhostManager extends Manager {

    private Map<Integer, Ghost> ghosts; // contains the entities of the different ghosts used in the manager.
    private int numberOfGhosts;         // number of total ghosts in the game (for now it can't load anymore than 4 different colors).
    private float releaseInterval;      // total interval between ghost releases (default 5 seconds)
    private float releaseTimer;         // running timer to check how long before next release.
    private boolean ghostReleasable;    // controls if ghosts are allowed to be released from the nest
    private int ghostKillingPoints;     // the kill-streak-combo points (increases with 200 for each kill).

    /**
     * Creates a ghost manager that makes sure that ghosts can't randomly spawn
     * whenever they like. Ghosts can only spawn if the super-snack timer is off
     * (this means that the killed ghosts or ghosts that haven't already been
     * released won't come out of the nest before the player is killable again.
     *
     * @param game
     * @param pacman
     * @param level
     */
    public GhostManager(GameplayScene game, Pacman pacman, Level level, int numberOfGhosts) {
        super(game, pacman, level);
        this.ghosts = new HashMap<>();

        this.numberOfGhosts = numberOfGhosts;
        this.releaseInterval = 5000.0f;
        this.releaseTimer = 4000.0f;
        this.ghostReleasable = true;
        this.ghostKillingPoints = 200;

        this.spawnGhosts();
    }

    //=================================================================
    /*
     * OVERRIDDEN METHODS
     */
    //=================================================================
    /**
     * Loops over all ghosts and calls their cleanUp methods.
     */
    @Override
    public void cleanUp() {
        for (int i : ghosts.keySet()) {
            ghosts.get(i).getGhost().cleanUp();
        }
    }

    /**
     * Calculates the spawn timer (default 5 seconds in between spawns) <br>
     *
     * @param delta
     */
    @Override
    public void update(float delta) {
        releaseTimer += delta;

        if (checkIfAllGhostsAreDead()) {
            for (int key : ghosts.keySet()) {
                ghosts.get(key).setReleaseable(true);
            }
        }

        for (int key : ghosts.keySet()) {

            // updating the ghost AI
            if (!ghosts.get(key).isReleaseable() && releaseTimer >= releaseInterval && ghostReleasable) {
                releaseTimer = 0.0f;
                ghosts.get(key).setReleaseable(true);
                continue;
            }
            ghosts.get(key).update(delta);

            if (pacman.collision(ghosts.get(key))) {
                if (ghosts.get(key).isInvincible()) {
                    killPacman();
                } else {
                    killGhost(key);
                }
            }
        }
    }

    /**
     * Loops over all ghosts and draws them.
     */
    @Override
    public void render() {
        for (int key : ghosts.keySet()) {
            ghosts.get(key).render();
        }
    }
    //=================================================================
    /*
     * PRIVATE METHODS
     */
    //=================================================================

    /**
     * This method chooses what color the spawned ghost shall have and spawns
     * all ghosts in the center of the labyrinth. It also makes sure that the
     * ghosts that are spawned can't be released before the update loop desires
     * them to get loose (by default this happens every 5th second).
     */
    private void spawnGhosts() {
        for (int color = 0; color < numberOfGhosts; color++) {
            Ghost ghost = new Ghost(new GhostGraphics(color), level);
            ghost.update(0);
            ghost.setReleaseable(false);
            ghosts.put(color, ghost);
        }
    }

    /**
     * Helper method to check if all ghosts are dead. If not it'll return false.
     *
     * @return
     */
    private boolean checkIfAllGhostsAreDead() {
        for (int key : ghosts.keySet()) {
            if (!ghosts.get(key).isDead()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method that checks what to do if Pacman is killed (or at least tried to
     * be killed).
     */
    private void killPacman() {
        // remove one life from pacman
        // remove a given amount of points from the current score
        if (!pacman.isImmortal()) {
            pacman.kill();
            pacman.removePoints(100);

            // check if packman has anymore lives, and if he does, respawn him and the ghosts
            if (pacman.getLives() != 0) {
                pacman.spawn();
                pacman.setImmortal();
                // force the ghostTimer to its maximum to make a ghost respawn directly
                releaseTimer = releaseInterval;
            } else {
                game.setGameOver();
            }
        }
    }

    /**
     * Kills the ghost in the HashMap with the specific key. And gives the
     * player 200 points for the first kill, 400 for the next, 600 on the
     * following and lastly 800 points
     *
     * @param key
     */
    private void killGhost(int key) {
        if (!ghosts.get(key).isDead()) {
            ghosts.get(key).kill();
            pacman.addPoints(ghostKillingPoints);
            ghostKillingPoints += 200;
        }
    }

    //=================================================================
    /*
     * PUBLIC METHODS
     */
    //=================================================================
    //=================================================================
    /*
     * SETTERS
     */
    //=================================================================
    /**
     * Makes all ghosts invincible again, should be called a desired amount of
     * time after activating the warning on the ghosts.
     *
     * @setWarningOnGhosts().
     */
    public void setAllGhostsInvincible() {
        for (int key : ghosts.keySet()) {
            ghosts.get(key).setInvincible();
        }
    }

    /**
     * Makes the ghosts able to get out from the nest. If Pacman is invincible
     * no ghosts will be released (he can still kill all the ones in the maze).
     *
     * @param ghostReleaseable
     */
    public void setGhostReleaseable(boolean ghostReleaseable) {
        this.ghostReleasable = ghostReleaseable;
        this.ghostKillingPoints = 200;
    }

    /**
     * Makes all ghosts that are loose in the labyrinth killable by Pacman. This
     * method should only be called from GameplayScene, which is triggered by
     * the SnackManager class, when Pacman eats a SuperSnack.
     */
    public void setAllGhostsKillable() {
        for (int key : ghosts.keySet()) {
            ghosts.get(key).setKillable();
        }
    }

    /**
     * Activates the blinking effect on the ghosts, short before they get
     * invincible again.
     */
    public void setWarningOnGhosts() {
        for (int key : ghosts.keySet()) {
            ghosts.get(key).setWarningAnimation();
        }
    }
    //=================================================================
    /*
     * GETTERS
     */
    //=================================================================

    public Map<Integer, Ghost> getGhosts() {
        return ghosts;
    }

    public boolean isGhostReleaseable() {
        return ghostReleasable;
    }
}
