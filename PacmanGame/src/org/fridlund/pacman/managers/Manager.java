/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.pacman.managers;

import org.fridlund.pacman.entities.Pacman;
import org.fridlund.pacman.level.Level;
import org.fridlund.pacman.scenes.GameplayScene;

/**
 * Abstract class containing the base methods used by the managers. Also
 * contains protected fields that are likely to be used by every manager in this
 * game.
 *
 * @author Christoffer
 */
public abstract class Manager {

    protected GameplayScene game;
    protected Pacman pacman;
    protected Level level;

    public Manager(GameplayScene game, Pacman pacman, Level level) {
        this.game = game;
        this.pacman = pacman;
        this.level = level;
    }

    public abstract void cleanUp();

    public abstract void update(float delta);

    public abstract void render();
}
