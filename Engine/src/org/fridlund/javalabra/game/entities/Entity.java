/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.entities;

/**
 * Interface containing all the methods used in an Entity object.
 *
 * @author Christoffer
 */
public interface Entity {

    /**
     *
     */
    public void setup();

    /**
     *
     */
    public void cleanUp();

    /**
     *
     * @param delta
     */
    public void update(float delta);

    /**
     *
     */
    public void render();

    /**
     *
     * @param entity
     * @return
     */
    public boolean collision(Entity entity);

    /**
     *
     * @param x
     * @param y
     */
    public void setPosition(float x, float y);

    /**
     *
     * @param x
     */
    public void setX(float x);

    /**
     *
     * @return
     */
    public float getX();

    /**
     *
     * @param y
     */
    public void setY(float y);

    /**
     *
     * @return
     */
    public float getY();

    /**
     *
     * @param width
     */
    public void setWidth(float width);

    /**
     *
     * @return
     */
    public float getWidth();

    /**
     *
     * @param height
     */
    public void setHeight(float height);

    /**
     *
     * @return
     */
    public float getHeight();

    /**
     *
     * @param offset
     */
    public void setCollisionOffset(float offset);

    /**
     *
     * @return
     */
    public float getCollisionOffset();
}
