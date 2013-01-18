/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.tiles;

import org.fridlund.javalabra.game.sprites.SpriteSheet;

/**
 *
 * @author Christoffer
 */
public class BasicTile extends Tile {

    private int tileX;
    private int tileY;

    public BasicTile(SpriteSheet sheet, int tileX, int tileY, float x, float y) {
        super(x, y);
        this.tileX = tileX;
        this.tileY = tileY;
    }

    @Override
    public void setup() {
    }

    @Override
    public void cleanUp() {
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render() {
    }
}
