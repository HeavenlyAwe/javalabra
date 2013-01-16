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
public abstract class EntityAbstract implements EntityInterface {

    protected float x;
    protected float y;
    protected Animation animation;

    public EntityAbstract(float x, float y, Animation animation) {
        this.x = x;
        this.y = y;
        if (animation == null) {
            throw new IllegalArgumentException("You must give an animnation");
        }
        this.animation = animation;
    }

    @Override
    public void setup() {
        animation.setup();
    }

    @Override
    public void cleanUp() {
        animation.cleanUp();
    }

    @Override
    public void update(float delta) {
        animation.update(delta);
    }

    @Override
    public void render() {
        animation.render(x, y);
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
}
