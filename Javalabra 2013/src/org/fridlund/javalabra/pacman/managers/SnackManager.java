/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.managers;

import java.util.ArrayList;
import java.util.List;
import org.fridlund.javalabra.game.entities.Entity;
import org.fridlund.javalabra.pacman.entities.Ghost;
import org.fridlund.javalabra.pacman.entities.Pacman;
import org.fridlund.javalabra.pacman.entities.Snack;
import org.fridlund.javalabra.pacman.entities.SuperSnack;
import org.fridlund.javalabra.pacman.levels.Level;
import org.fridlund.javalabra.pacman.scenes.GameplayScene;

/**
 *
 * @author Christoffer
 */
public class SnackManager extends Manager {

    private List<Entity> snacks;

    public SnackManager(GameplayScene game, Pacman pacman, Level level) {
        super(game, pacman, level);
        this.snacks = new ArrayList<>();
        this.spawnSnacks();
    }

    @Override
    public void cleanUp() {
        for (int i = 0; i < snacks.size(); i++) {
            snacks.get(i).cleanUp();
        }
    }

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
        if (x == 2 && y == 2
                || x == 2 && y == level.getHeight() / level.getTileHeight() - 2
                || x == level.getWidth() / level.getTileWidth() - 2 && y == 2
                || x == level.getWidth() / level.getTileWidth() - 2 && y == level.getHeight() / level.getTileHeight() - 2) {
            snack = new SuperSnack();
        } else {
            snack = new Snack();
        }
        if (level.walkableTile(snack, x * level.getTileWidth() - 0.5f * level.getTileWidth(), y * level.getTileHeight() - 0.5f * level.getTileHeight(), allowedTiles)) {
            snack.setPosition(x * level.getTileWidth() - 0.5f * level.getTileWidth(), y * level.getTileHeight() - 0.5f * level.getTileHeight());
            snacks.add(snack);
        }
    }

    @Override
    public void update(float delta) {
        if (snacks.isEmpty()) {
            game.setGameOver("You Win! Points = " + pacman.getPoints());
        }

        for (int i = 0; i < snacks.size(); i++) {
            snacks.get(i).update(delta);
            if (pacman.collision(snacks.get(i))) {
                if (snacks.get(i).getClass().getSimpleName().equals("SuperSnack")) {
                    System.out.println("SuperSnack eaten");
                }
                snacks.remove(i);
                i--;
                pacman.addPoints(10);
            }
        }
    }

    @Override
    public void render() {
        for (int i = 0; i < snacks.size(); i++) {
            snacks.get(i).render();
        }
    }
}
