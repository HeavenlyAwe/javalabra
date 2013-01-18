/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.entities;

/**
 *
 * @author Christoffer
 */
public interface Entity {

    public void setup();

    public void cleanUp();

    public void update(float delta);

    public void render();

    public boolean collision(Entity entity);

    public void setX(float x);

    public float getX();

    public void setY(float y);

    public float getY();

    public void setWidth(float width);

    public float getWidth();

    public void setHeight(float height);

    public float getHeight();
}
