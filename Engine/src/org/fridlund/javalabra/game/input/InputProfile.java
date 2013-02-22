/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.input;

import org.lwjgl.input.Keyboard;

/**
 * Abstract class containing the methods most likely to be used when accessing
 * input actions.
 *
 * @author Christoffer
 */
public abstract class InputProfile {

    /**
     *
     */
    public InputProfile() {
        setup();
    }
//=================================================================
    /*
     * PRIVATE METHODS
     */
    //=================================================================

    private void handleInput(float delta) {
        handleControllerInput(delta);
        handleMouseInput(delta);
        handleKeyboardInput(delta);
    }
    //=================================================================
    /*
     * PUBLIC METHODS
     */
    //=================================================================

    /**
     *
     * @param delta
     */
    public void update(float delta) {
        handleInput(delta);
    }

    /**
     *
     * @param key
     * @return
     */
    public final boolean keyDown(int key) {
        return Keyboard.isKeyDown(key);
    }
    //=================================================================
    /*
     * ABSTRACT METHODS
     */
    //=================================================================

    /**
     *
     */
    public abstract void setup();

    /**
     *
     * @param delta
     */
    public abstract void handleControllerInput(float delta);

    /**
     *
     * @param delta
     */
    public abstract void handleMouseInput(float delta);

    /**
     *
     * @param delta
     */
    public abstract void handleKeyboardInput(float delta);
}
