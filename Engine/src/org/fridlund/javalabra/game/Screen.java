/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game;

import java.io.File;
import java.nio.FloatBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Christoffer
 */
public class Screen {

    private static int lastX;
    private static int lastY;

    public static void setupDisplay(String title) {
        try {
            Display.setDisplayMode(new DisplayMode(800, 640));
            Display.setTitle(title);
            Display.create();

            lastX = Display.getX();
            lastY = Display.getY();

        } catch (LWJGLException ex) {
            Logger.getLogger(Screen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void cleanUp() {
        Display.destroy();
    }

    public static void applyProjectionMatrix() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }

    public static void setupLWJGL() {
        applyProjectionMatrix();
        glViewport(0, 0, Display.getWidth(), Display.getHeight());

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_TEXTURE_RECTANGLE_ARB);
//        glEnable(GL_CULL_FACE);
//        glCullFace(GL_BACK);
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public static boolean isMoved() {
        if (lastX != Display.getX() || lastY != Display.getY()) {
            lastX = Display.getX();
            lastY = Display.getY();
            return true;
        }
        return false;
    }
    
     /**
     * Loads the natives for LWJGL, from the natives folder inside the lib
     * directory. By using this method you don't have to explicitly have to
     * choose natives (because they are different for different platforms).
     */
    public static void setupNativesLWJGL() {
        String lwjglPath = "org.lwjgl.librarypath";
        String userDir = System.getProperty("user.dir");
        String nativePath = "lib/natives";
        
        File nativeFile = new File(new File(userDir, nativePath),
                LWJGLUtil.getPlatformName());

        System.setProperty(lwjglPath, nativeFile.getAbsolutePath());

        String inputPath = "net.java.games.input.librarypath";
        String lwjglProperty = System.getProperty(lwjglPath);

        System.setProperty(inputPath, lwjglProperty);
    }
}
