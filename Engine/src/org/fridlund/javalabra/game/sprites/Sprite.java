/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.sprites;

/**
 * Position and size for the sub image in the sprite sheet.
 *
 * @author Christoffer
 */
public class Sprite {

    private final float x;
    private final float y;
    private final float w;
    private final float h;

    public Sprite(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    //=================================================================
    /*
     * OVERRIDDEN METHODS
     */
    //=================================================================

    @Override
    public String toString() {
        return x + ", " + y + ", " + w + ", " + h;
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
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return w;
    }

    public float getHeight() {
        return h;
    }
}
