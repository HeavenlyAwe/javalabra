/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.scenes;

/**
 *
 * @author Christoffer
 */
public abstract class Scene {

    public Scene() {
        setup();
    }

    public abstract void setup();

    public abstract void cleanUp();

    public void update(float delta) {
    }

    public void render() {
    }
}
