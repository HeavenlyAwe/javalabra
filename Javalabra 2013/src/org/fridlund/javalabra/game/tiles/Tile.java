/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.tiles;

/**
 *
 * @author Christoffer
 */
public abstract class Tile {

    protected float x;
    protected float y;

    public Tile(float x, float y) {
        this.x = x;
        this.y = y;

        setup();
    }

    public abstract void setup();

    public abstract void cleanUp();

    public abstract void update(float delta);

    public abstract void render();
}
