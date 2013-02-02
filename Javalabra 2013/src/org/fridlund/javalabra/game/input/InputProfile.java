/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.input;

import org.lwjgl.input.Keyboard;

/**
 *
 * @author Christoffer
 */
public abstract class InputProfile {

    public InputProfile() {
        setup();
    }

    public abstract void setup();

    public void update(float delta) {
        handleInput(delta);
    }

    private void handleInput(float delta) {
        handleControllerInput(delta);
        handleKeyboardInput(delta);
    }

    public void handleControllerInput(float delta) {
    }

    public void handleMouseInput(float delta) {
    }

    public void handleKeyboardInput(float delta) {
    }

    public final boolean keyDown(int key) {
        return Keyboard.isKeyDown(key);
    }
}
