/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.managers;

import java.util.ArrayList;
import java.util.List;
import org.fridlund.javalabra.game.entities.Entity;
import org.fridlund.javalabra.pacman.entities.Pacman;
import org.fridlund.javalabra.pacman.entities.Snack;
import org.fridlund.javalabra.pacman.entities.SuperSnack;
import org.fridlund.javalabra.pacman.levels.Level;
import org.fridlund.javalabra.pacman.scenes.GameplayScene;

/**
 * Controls what the different edible entities do, and for how long their
 * special effect is working.
 *
 * @author Christoffer
 */
public class SnackManager extends Manager {

    private List<Entity> snacks;            // list of all snacks in the world
    private float superSnackTimerMax;       // for how long time will Pacman be invincible when eating a SuperSnack
    private float superSnackTimer;          // how long remaining of the super-ability
    private boolean superSnackEaten;        // checks if a SuperSnack has been eaten
    private boolean superSnackWarningSent;  // checks if the ghosts are soon dangerous warning has been set

    /**
     * Constructor creating a SnackManager instance. And initializing all global
     * variables
     *
     * @param game
     * @param pacman
     * @param level
     */
    public SnackManager(GameplayScene game, Pacman pacman, Level level) {
        super(game, pacman, level);
        this.snacks = new ArrayList<>();

        this.superSnackTimerMax = 10000.0f;
        this.superSnackTimer = 0.0f;
        this.superSnackEaten = false;
        this.superSnackWarningSent = false;

        this.spawnSnacks();
    }

    //=================================================================
    /*
     * OVERRIDDEN METHODS
     */
    //=================================================================
    /**
     * Calls cleanUp method in all snack entities
     */
    @Override
    public void cleanUp() {
        for (int i = 0; i < snacks.size(); i++) {
            snacks.get(i).cleanUp();
        }
    }

    /**
     * Used for checking if level is complete and if Pacman has eaten a
     * SuperSnack or just a regular snack.
     *
     * @param delta
     */
    @Override
    public void update(float delta) {
        checkVictory();
        checkIfSuperSnackIsEaten(delta);
        checkIfPacmanEatsSnack(delta);
    }

    /**
     * Renders all snacks
     */
    @Override
    public void render() {
        for (int i = 0; i < snacks.size(); i++) {
            snacks.get(i).render();
        }
    }

    //=================================================================
    /*
     * PRIVATE METHODS
     */
    //=================================================================
    /**
     * Method for spawning snacks during level setup
     */
    private void spawnSnacks() {

        ArrayList<Integer> allowedTiles = new ArrayList<>();
        allowedTiles.add(Level.WALKABLE);

        for (int y = 0; y < level.getHeight() / level.getTileHeight(); y++) {
            for (int x = 0; x < level.getWidth() / level.getTileWidth(); x++) {
                chooseSnackToSpawn(x, y, allowedTiles);
            }
        }
    }

    /**
     * This method checks on what tiles the snacks are able to spawn and what
     * kind of snack it should be (regular or super)
     *
     * @param x
     * @param y
     * @param allowedTiles
     */
    private void chooseSnackToSpawn(int x, int y, ArrayList<Integer> allowedTiles) {
        Snack snack;
        if (x == 1 && y == 1
                || x == 1 && y == level.getHeight() / level.getTileHeight() - 3
                || x == level.getWidth() / level.getTileWidth() - 3 && y == 1
                || x == level.getWidth() / level.getTileWidth() - 3 && y == level.getHeight() / level.getTileHeight() - 3) {
            snack = new SuperSnack();
        } else {
            snack = new Snack();
        }

        float posX = x * level.getTileWidth();
        float posY = y * level.getTileHeight();
        if (level.walkableTile(snack, posX, posY, allowedTiles)
                && level.walkableTile(snack, posX + level.getTileWidth(), posY, allowedTiles)
                && level.walkableTile(snack, posX, posY + level.getTileHeight(), allowedTiles)
                && level.walkableTile(snack, posX + level.getTileWidth(), posY + level.getTileHeight(), allowedTiles)
                && posX + level.getTileWidth() < level.getWidth()) {
            snack.setPosition(posX + 0.5f * level.getTileWidth(), posY + 0.5f * level.getTileHeight());
            snacks.add(snack);
        }
    }

    /**
     * Controls if there are any snacks left in the snacks list. If the list is
     * empty it calls the GameplayScene's method setGameOver with win message.
     */
    private void checkVictory() {
        if (snacks.isEmpty()) {
            game.setGameOver("You Win! Points = " + pacman.getPoints());
        }
    }

    /**
     * Controls the invincibility of Pacman, by checking if he has eaten a
     * SuperSnack and then calculating the timer to see how long the ghosts
     * should be targets and when to start the warning on them.
     *
     * @param delta
     */
    private void checkIfSuperSnackIsEaten(float delta) {
        if (superSnackEaten) {
            superSnackTimer += delta;
            if (superSnackWarningSent == false && superSnackTimer >= superSnackTimerMax - 2000) {
                superSnackWarningSent = true;
                game.setWarningOfSuperSnackEffectSoonGone();
            }
            if (superSnackTimer >= superSnackTimerMax) {
                superSnackEaten = false;
                superSnackTimer = 0.0f;
                game.setGhostsReleaseable(true);
                game.setGhostsInvincible();
            }
        }
    }

    /**
     * Controls if Pacman has eaten a snack, and what kind of snack in case he
     * eats one.
     *
     * @param delta
     */
    private void checkIfPacmanEatsSnack(float delta) {
        for (int i = 0; i < snacks.size(); i++) {
            snacks.get(i).update(delta);
            if (pacman.collision(snacks.get(i))) {
                if (snacks.get(i).getClass().getSimpleName().equals("SuperSnack")) {
                    game.superSnackEaten();
                    game.setGhostsReleaseable(false);
                    superSnackTimer = 0.0f;
                    superSnackEaten = true;
                    superSnackWarningSent = false;
                    System.out.println("SuperSnack eaten");
                }
                snacks.remove(i);
                i--;
                pacman.addPoints(10);
            }
        }
    }
    //=================================================================
    /*
     * PUBLIC METHODS
     */
    //=================================================================

    //=================================================================
    /*
     * GETTERS
     */
    //=================================================================
    public List<Entity> getSnacks() {
        return snacks;
    }
}
