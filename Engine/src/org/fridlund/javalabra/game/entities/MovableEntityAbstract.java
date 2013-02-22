/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.entities;

/**
 * Simple abstract implementation of the MovableEntity interface and the
 * abstract implementation of the Entity.
 *
 * @author Christoffer
 */
public abstract class MovableEntityAbstract extends EntityAbstract implements MovableEntityInterface {

    protected float dx;
    protected float dy;

    public MovableEntityAbstract() {
        dx = 0.0f;
        dy = 0.0f;
    }

    public MovableEntityAbstract(float x, float y) {
        super(x, y);

        dx = 0.0f;
        dy = 0.0f;
    }

    //=================================================================
    /*
     * OVERRIDDE METHODS
     */
    //=================================================================
    @Override
    public void move(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;

        this.setX(x + dx);
        this.setY(y + dy);
    }

    //=================================================================
    /*
     * SETTERS
     */
    //=================================================================
    @Override
    public void setDX(float dx) {
        this.dx = dx;
    }

    @Override
    public void setDY(float dy) {
        this.dy = dy;
    }

    //=================================================================
    /*
     * GETTERS
     */
    //=================================================================
    @Override
    public float getDX() {
        return dx;
    }

    @Override
    public float getDY() {
        return dy;
    }
}
