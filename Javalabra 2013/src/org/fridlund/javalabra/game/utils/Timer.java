/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.utils;

import org.lwjgl.Sys;

/**
 *
 * @author Christoffer
 */
public class Timer {

    private static long previousTime;
    /**
     * These variables are used for the FPS-counter
     */
    private static long lastTimer;
    private static int fps;
    private static int currentFPS;

    /**
     * Remember to call this method before you start using this class!
     */
    public static void setup() {
        previousTime = getTime();
        lastTimer = getTime();
        fps = 0;
        currentFPS = 0;
    }

    public static void reset() {
        previousTime = getTime();
        lastTimer = getTime();
        fps = 0;
        currentFPS = 0;
    }

    /**
     * Remember to call
     *
     * @setup before usage of this method
     */
    public static long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    /**
     * Remember to call
     *
     * @setup before usage of this method
     */
    public static float getDelta() {
        long currentTime = getTime();
        fps++;
        if (currentTime - lastTimer >= 1000) {
            currentFPS = fps;
            fps = 0;
            lastTimer += 1000;
        }
        float delta = (float) (currentTime - previousTime);
        previousTime = getTime();
        return delta;
    }

    public static int getFPS() {
        return currentFPS;
    }
}
