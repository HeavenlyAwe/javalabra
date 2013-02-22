/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.scenes;

/**
 * The abstract Scene class. This is used by the SceneManager to maintain all
 * the different scenes in the project.
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

    //=================================================================
    /*
     * PUBLIC METHODS
     */
    //=================================================================
    public void addSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void show() {
    }

    public void disable() {
    }

    public void update(float delta) {
    }

    public void render() {
    }

    //=================================================================
    /*
     * ABSTRACT METHODS
     */
    //=================================================================
    public abstract void setup();

    public abstract void cleanUp();

    //=================================================================
    /*
     * GETTERS
     */
    //=================================================================
    public int getID() {
        return id;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }
}
