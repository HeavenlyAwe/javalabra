/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Christoffer
 */
public class Screen {

    public static void setupDisplay(String title) {
        try {
            Display.setDisplayMode(new DisplayMode(800, 640));
            Display.setTitle(title);
            Display.create();
        } catch (LWJGLException ex) {
            Logger.getLogger(Screen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void cleanUp() {
        Display.destroy();
    }

    public static void setupLWJGL() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glViewport(0, 0, Display.getWidth(), Display.getHeight());

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_TEXTURE_RECTANGLE_ARB);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }
}
