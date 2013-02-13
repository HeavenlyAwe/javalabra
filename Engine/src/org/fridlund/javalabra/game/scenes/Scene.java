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

    private final int id;
    private SceneManager sceneManager;

    public Scene(int id) {
        this.id = id;
        setup();
    }

    public void addSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public abstract void setup();

    public abstract void cleanUp();

    public void update(float delta) {
    }

    public void render() {
    }

    public int getID() {
        return id;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }
}
