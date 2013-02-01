/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.fridlund.javalabra.game.utils.Timer;
import org.lwjgl.LWJGLException;
import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Christoffer
 */
public abstract class GameLWJGL {

    private String title;
    private boolean running;

    public GameLWJGL(String title) {
        this.title = title;
    }

    private void setupGame() {
        Screen.setupDisplay(title);
        Screen.setupLWJGL();

        Timer.setup();

        setup();
    }

//    public void setupDisplay() {
//        try {
//            Display.setDisplayMode(new DisplayMode(800, 640));
//            Display.setTitle(this.title);
//            Display.create();
//        } catch (LWJGLException ex) {
//            Logger.getLogger(GameLWJGL.class.getName()).log(Level.SEVERE, null, ex);
//            cleanUpGame();
//        }
//    }
//
//    public void setupLWJGL() {
//        glMatrixMode(GL_PROJECTION);
//        glLoadIdentity();
//        glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
//        glMatrixMode(GL_MODELVIEW);
//        glLoadIdentity();
//        glViewport(0, 0, Display.getWidth(), Display.getHeight());
//
//        glEnable(GL_TEXTURE_2D);
//        glEnable(GL_TEXTURE_RECTANGLE_ARB);
//        glEnable(GL_CULL_FACE);
//        glCullFace(GL_BACK);
//        glDisable(GL_DEPTH_TEST);
//        glEnable(GL_BLEND);
//        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//    }
    public abstract void setup();

    private void cleanUpGame() {
        cleanUp();
        Screen.cleanUp();
    }

    public abstract void cleanUp();

    public void start() {
        running = true;

        setupGame();
        run();
    }

    public void stop() {
        running = false;
        cleanUpGame();
    }

    public void run() {
        Timer.reset();

        while (running && !Display.isCloseRequested()) {

            update(Timer.getDelta());
            render();

            Display.update();
//            Display.sync(60);
        }

        cleanUpGame();
    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(0, 0, 0, 0);
    }

    public void update(float delta) {
        Display.setTitle("FPS: " + Timer.getFPS());
    }
}
