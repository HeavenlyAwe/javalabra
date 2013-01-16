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

    public EntityAbstract(float x, float y) {
        this.x = x;
        this.y = y;
        this.width = 1;
        this.height = 1;
    }

    @Override
    public boolean collision(Entity entity) {

        if (this.x < entity.getX() + entity.getWidth()
                && this.x + this.width > entity.getX()
                && this.y < entity.getY() + entity.getHeight()
                && this.y + this.height > entity.getY()) {
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
}
