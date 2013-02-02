/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.entities;

/**
 *
 * @author Christoffer
 */
public interface MovableEntityInterface {

    public void move(float dx, float dy);

    public void setDX(float dx);

    public float getDX();

    public void setDY(float dy);

    public float getDY();
}
