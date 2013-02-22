/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.entities;

/**
 * Interface containing all the methods used when Moving an entity around.
 *
 * @author Christoffer
 */
public interface MovableEntityInterface {

    /**
     *
     * @param dx
     * @param dy
     */
    public void move(float dx, float dy);

    /**
     *
     * @param dx
     */
    public void setDX(float dx);

    /**
     *
     * @return
     */
    public float getDX();

    /**
     *
     * @param dy
     */
    public void setDY(float dy);

    /**
     *
     * @return
     */
    public float getDY();
}
