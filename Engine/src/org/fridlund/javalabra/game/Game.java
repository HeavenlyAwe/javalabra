/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game;

import org.fridlund.javalabra.game.utils.Timer;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

/**
 * The Game instance, from which all the game's main class should be
 * instantiated.
 *
 * @author Christoffer
 */
public abstract class Game {

    private String title;
    private boolean running;

    /**
     *
     * @param title
     */
    public Game(String title) {
        this.title = title;
    }

    //=================================================================
    /*
     * PRIVATE METHODS
     */
    //=================================================================
    private void setupGame() {
        Screen.setupDisplay(title);
        Screen.setupLWJGL();

        Timer.setup();

        setup();
    }

    private void cleanUpGame() {
        cleanUp();
        Screen.cleanUp();
    }

    //=================================================================
    /*
     * PUBLIC METHODS
     */
    //=================================================================
    /**
     * Override this method, if you for some reason wants to pause the render
     * and update. If the method returns true, it'll reset the timer to the
     * current instance, and render the update function useless.
     *
     * @return
     */
    public boolean gameLoopRestrictions() {
        return false;
    }

    /**
     * Sets the running flag to true, setups the game and start the main loop.
     *
     * @run()
     */
    public void start() {
        running = true;

        setupGame();
        run();
    }

    /**
     * Sets the running flag to false.
     */
    public void stop() {
        running = false;
    }

    /**
     * Runs the main game loop. Start the game by calling the start() method.
     *
     * @start()
     */
    public void run() {
        Timer.reset();

        while (running && !Display.isCloseRequested()) {

            if (gameLoopRestrictions()) {
                Timer.reset();
            }

            update(Timer.getDelta());
            render();

            Display.update();
        }

        cleanUpGame();
    }

    /**
     *
     */
    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(0, 0, 0, 0);
    }

    /**
     *
     * @param delta
     */
    public void update(float delta) {
        Display.setTitle("FPS: " + Timer.getFPS());
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
     */
    public abstract void cleanUp();

    //=================================================================
    /*
     * GETTERS
     */
    //=================================================================
    /**
     *
     * @return
     */
    public String getTitle() {
        return title;
    }
}
