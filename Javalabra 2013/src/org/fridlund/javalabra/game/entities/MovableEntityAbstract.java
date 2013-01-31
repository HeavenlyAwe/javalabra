/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.entities;

/**
 *
 * @author Christoffer
 */
public abstract class MovableEntityAbstract extends EntityAbstract implements MovableEntityInterface {

    protected float dx;
    protected float dy;
    
    public MovableEntityAbstract(){
        dx = 0.0f;
        dy = 0.0f;
    }

    public MovableEntityAbstract(float x, float y) {
        super(x, y);

        dx = 0.0f;
        dy = 0.0f;
    }

    @Override
    public void move(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;

        this.setX(x + dx);
        this.setY(y + dy);
    }
    
    @Override
    public float getDX(){
        return dx;
    }
    
    @Override
    public float getDY(){
        return dy;
    }
}
