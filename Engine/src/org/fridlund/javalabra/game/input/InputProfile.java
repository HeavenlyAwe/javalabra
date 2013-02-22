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

    public void update(float delta) {
        handleInput(delta);
    }

    public final boolean keyDown(int key) {
        return Keyboard.isKeyDown(key);
    }
    //=================================================================
    /*
     * ABSTRACT METHODS
     */
    //=================================================================

    public abstract void setup();

    public abstract void handleControllerInput(float delta);

    public abstract void handleMouseInput(float delta);

    public abstract void handleKeyboardInput(float delta);
}
