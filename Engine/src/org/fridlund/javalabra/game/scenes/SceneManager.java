/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.scenes;

import java.util.HashMap;

/**
 * Handles all the scenes loaded in the game. Use the setCurrentScene method to
 * activate the update and render on that scene.
 *
 * @author Christoffer
 */
public class SceneManager {

    private HashMap<Integer, Scene> scenes;
    private int currentSceneID;

    public SceneManager() {
        scenes = new HashMap<>();
    }

    //=================================================================
    /*
     * PUBLIC METHODS
     */
    //=================================================================
    /**
     * Clears the Scene map.
     */
    public void cleanUp() {
        scenes.clear();
    }

    /**
     * Adds a new Scene to the map. You can't use same ID in two scenes. Check
     * the SceneIDs class for currently usable IDs.
     *
     * @param scene
     */
    public void addScene(Scene scene) {
        if (scenes.containsKey(scene.getID())) {
            System.out.println("ID already in use!");
            return;
        }
        currentSceneID = scene.getID();
        scene.addSceneManager(this);
        scenes.put(scene.getID(), scene);
    }

    /**
     * Removes the Scene attached to the id.
     *
     * @param id
     */
    public void removeScene(int id) {
        scenes.get(id).cleanUp();
        scenes.remove(id);
    }

    public void update(float delta) {
        scenes.get(currentSceneID).update(delta);
    }

    public void render() {
        scenes.get(currentSceneID).render();
    }

    //=================================================================
    /*
     * SETTERS
     */
    //=================================================================
    /**
     * Calls the previous scenes disable method and activates the new scene
     * connected to that specific id and calls the new scene's show method.
     *
     * @param id
     */
    public void setCurrentScene(int id) {
        scenes.get(currentSceneID).disable();
        this.currentSceneID = id;
        scenes.get(id).show();
    }
}
