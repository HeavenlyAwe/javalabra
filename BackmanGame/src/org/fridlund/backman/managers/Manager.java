/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.backman.managers;

import org.fridlund.backman.entities.Backman;
import org.fridlund.backman.level.Level;
import org.fridlund.backman.scenes.GameplayScene;

/**
 * Abstract class containing the base methods used by the managers. Also
 * contains protected fields that are likely to be used by every manager in this
 * game.
 *
 * @author Christoffer
 */
public abstract class Manager {

    /**
     *
     */
    protected GameplayScene game;
    /**
     *
     */
    protected Backman pacman;
    /**
     *
     */
    protected Level level;

    /**
     *
     * @param game
     * @param pacman
     * @param level
     */
    public Manager(GameplayScene game, Backman pacman, Level level) {
        this.game = game;
        this.pacman = pacman;
        this.level = level;
    }

    /**
     *
     */
    public abstract void cleanUp();

    /**
     *
     * @param delta
     */
    public abstract void update(float delta);

    /**
     *
     */
    public abstract void render();
}
