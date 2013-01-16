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
public abstract class MovableEntityAbstract extends EntityAbstract implements MovableEntityInterface {

    private float dx;
    private float dy;

    public MovableEntityAbstract(float x, float y, Animation animation) {
        super(x, y, animation);
        
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
}
