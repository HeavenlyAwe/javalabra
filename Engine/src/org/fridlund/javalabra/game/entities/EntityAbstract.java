/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.entities;

/**
 * Simple abstract implementation of the Entity interface.
 *
 * @author Christoffer
 */
public abstract class EntityAbstract implements Entity {

    /**
     *
     */
    protected float x;
    /**
     *
     */
    protected float y;
    /**
     *
     */
    protected float width;
    /**
     *
     */
    protected float height;
    /**
     *
     */
    protected float collisionOffset;

    /**
     *
     */
    public EntityAbstract() {
        this(0, 0);
    }

    /**
     *
     * @param x
     * @param y
     */
    public EntityAbstract(float x, float y) {
        this.setPosition(x, y);
        this.width = 1;
        this.height = 1;
        this.collisionOffset = 0.0f;

        setup();
    }

    //=================================================================
    /*
     * OVERRIDDEN METHODS
     */
    //=================================================================
    /**
     * Checks if another entity is colliding with this one.
     *
     * @param entity
     * @return
     */
    @Override
    public boolean collision(Entity entity) {

        if (this.x + collisionOffset < entity.getX() + entity.getWidth() - entity.getCollisionOffset()
                && this.x + this.width - collisionOffset > entity.getX() + entity.getCollisionOffset()
                && this.y + collisionOffset < entity.getY() + entity.getHeight() - entity.getCollisionOffset()
                && this.y + this.height - collisionOffset > entity.getY() + entity.getCollisionOffset()) {
            return true;
        } else {
            return false;
        }
    }

    //=================================================================
    /*
     * SETTERS
     */
    //=================================================================
    /**
     *
     * @param x
     * @param y
     */
    @Override
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @param x
     */
    @Override
    public void setX(float x) {
        this.x = x;
    }

    /**
     *
     * @param y
     */
    @Override
    public void setY(float y) {
        this.y = y;
    }

    /**
     *
     * @param width
     */
    @Override
    public void setWidth(float width) {
        this.width = width;
    }

    /**
     *
     * @param height
     */
    @Override
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     *
     * @param offset
     */
    @Override
    public void setCollisionOffset(float offset) {
        this.collisionOffset = offset;
    }

    //=================================================================
    /*
     * GETTERS
     */
    //=================================================================
    /**
     *
     * @return
     */
    @Override
    public float getX() {
        return x;
    }

    /**
     *
     * @return
     */
    @Override
    public float getY() {
        return y;
    }

    /**
     *
     * @return
     */
    @Override
    public float getWidth() {
        return width;
    }

    /**
     *
     * @return
     */
    @Override
    public float getHeight() {
        return height;
    }

    /**
     *
     * @return
     */
    @Override
    public float getCollisionOffset() {
        return collisionOffset;
    }
}
