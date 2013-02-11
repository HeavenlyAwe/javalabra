/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.entities;

/**
 *
 * @author Christoffer
 */
public abstract class EntityAbstract implements Entity {

    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected float collisionOffset;

    public EntityAbstract() {
        this(0, 0);
    }

    public EntityAbstract(float x, float y) {
        this.setPosition(x, y);
        this.width = 1;
        this.height = 1;
        this.collisionOffset = 0.0f;

        setup();
    }

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
//        return ((this.x + this.width > entity.getX() || this.x < entity.getX() + entity.getWidth()) 
//                && (this.y < entity.getY() + entity.getHeight() || this.y + this.height > entity.getY()));

//            return true;
//        }
//        Rectangle spriteRect = new Rectangle((int) sprite.getX(), (int) sprite.getY(), (int) sprite.getWidth(), (int) sprite.getHeight());
//        Rectangle hitBox = new Rectangle((int) x, (int) y, (int) width, (int) height);
//        return spriteRect.contains(hitBox);
    }

    @Override
    public void setPosition(float x, float y) {
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

    @Override
    public void setWidth(float width) {
        this.width = width;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public void setCollisionOffset(float offset) {
        this.collisionOffset = offset;
    }

    @Override
    public float getCollisionOffset() {
        return collisionOffset;
    }
}
