/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.entities;

import org.fridlund.javalabra.game.sprites.Animation;

/**
 *
 * @author Christoffer
 */
public abstract class EntityAbstract implements EntityInterface {

    protected float x;
    protected float y;

    public EntityAbstract(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public float getY() {
        return y;
    }
}
