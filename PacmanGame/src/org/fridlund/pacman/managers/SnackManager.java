/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.pacman.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.fridlund.pacman.entities.Direction;
import org.fridlund.pacman.entities.Pacman;
import org.fridlund.pacman.entities.Snack;
import org.fridlund.pacman.entities.SpecialAction;
import org.fridlund.pacman.entities.SpecialSnack;
import org.fridlund.pacman.entities.SuperSnack;
import org.fridlund.pacman.level.Level;
import org.fridlund.pacman.scenes.GameplayScene;

/**
 * Controls what the different edible entities do, and for how long their
 * special effect is working.
 *
 * @author Christoffer
 */
public class SnackManager extends Manager {

    private List<Snack> snacks;                 // list of all snacks in the world
    private float superSnackTimerMax;           // for how long time will Pacman be invincible when eating a SuperSnack
    private float superSnackTimer;              // how long remaining of the super-ability
    private boolean superSnackEaten;            // checks if a SuperSnack has been eaten
    private boolean superSnackWarningSent;      // checks if the ghosts are soon dangerous warning has been set
    /*
     * Action listeners for the different SpecialSnacks used in the game
     */
    private SpecialAction eatSuperSnackAction;
    private SpecialAction eatCherryAction;
    private SpecialAction eatAppleAction;
    private ArrayList<Integer> allowedTiles;    // This is basically the same as Pacman.allowedTiles
    private Random random;

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

        this.eatSuperSnackAction = new SpecialAction() {
            @Override
            public void execute() {
                eatSuperSnack();
            }
        };
        this.eatCherryAction = new SpecialAction() {
            @Override
            public void execute() {
                eatCherry();
            }
        };
        this.eatAppleAction = new SpecialAction() {
            @Override
            public void execute() {
                eatApple();
            }
        };

        this.allowedTiles = new ArrayList<>();
        this.allowedTiles.add(Level.WALKABLE);

        this.random = new Random();

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
        trySpawnSpecialSnack();
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
        for (int y = 0; y < level.getHeight() / level.getTileHeight(); y++) {
            for (int x = 0; x < level.getWidth() / level.getTileWidth(); x++) {
                chooseSnackToSpawn(x, y);
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
    private void chooseSnackToSpawn(int x, int y) {
        Snack snack;
        if (x == 1 && y == 1
                || x == 1 && y == level.getHeight() / level.getTileHeight() - 3
                || x == level.getWidth() / level.getTileWidth() - 3 && y == 1
                || x == level.getWidth() / level.getTileWidth() - 3 && y == level.getHeight() / level.getTileHeight() - 3) {
            snack = new SuperSnack(eatSuperSnackAction);
        } else {
            snack = new Snack();
        }
        placeSnack(x, y, snack);
    }

    private void trySpawnSpecialSnack() {
        if (random.nextInt(1000) == 0) {
            int x = random.nextInt((int) (level.getWidth() / level.getTileWidth()));
            int y = random.nextInt((int) (level.getHeight() / level.getTileHeight()));


            int chosenSpecialSnack = random.nextInt(2);
            int chosenDirection = random.nextInt(4);

            Direction dir = Direction.RIGHT;
            if (chosenDirection == 0) {
                dir = Direction.DOWN;
            } else if (chosenDirection == 1) {
                dir = Direction.UP;
            } else if (chosenDirection == 2) {
                dir = Direction.LEFT;
            } else if (chosenDirection == 3) {
                dir = Direction.RIGHT;
            }

            Snack snack = null;
            if (chosenSpecialSnack == 0) {
                snack = new SpecialSnack("/res/images/cherries.png", eatCherryAction, dir);
            } else if (chosenSpecialSnack == 1) {
                snack = new SpecialSnack("/res/images/apple.png", eatAppleAction, dir);
            }

            placeSnack(x, y, snack);
        }
    }

    private void placeSnack(int x, int y, Snack snack) {

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
            game.setGameWon();
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

            // Removes the special snack if it collides with a wall or if it goes outside the level.
            if (level.outsideLevel(snacks.get(i)) || !level.walkableTile(snacks.get(i), 0, 0, allowedTiles)) {
                snacks.remove(i);
                i--;
            } else if (pacman.collision(snacks.get(i))) {
                snacks.get(i).executeAction();
                snacks.remove(i);
                i--;
                pacman.addPoints(10);
            }
        }
    }

    /**
     * Informs the game that a super snack is eaten, makes the ghosts imprisoned
     * in the nest and starts the timer for how long pacman is invincible.
     */
    private void eatSuperSnack() {
        game.superSnackEaten();
        game.setGhostsReleaseable(false);
        superSnackTimer = 0.0f;
        superSnackEaten = true;
        superSnackWarningSent = false;
        System.out.println("SuperSnack eaten");
    }

    /**
     * Makes the camera rotate left if the eatCherryAction is executed.
     */
    private void eatCherry() {
        game.rotateCameraLeft();
        System.out.println("Cherry eaten");
    }

    /**
     * Makes the camera rotate right if the eatAppleAction is executed.
     */
    private void eatApple() {
        game.rotateCameraRight();
        System.out.println("Apple eaten");
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
    /**
     *
     * @return
     */
    public List<Snack> getSnacks() {
        return snacks;
    }
}
