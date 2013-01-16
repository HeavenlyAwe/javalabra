/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra;

import java.io.File;
import javax.swing.SwingUtilities;
import org.lwjgl.LWJGLUtil;

/**
 *
 * @author Christoffer
 */
public class Launcher {

    /**
     * Loads the natives for LWJGL, from the natives folder inside the lib
     * directory. By using this method you don't have to explicitly have to
     * choose natives (because they are different for different platforms).
     */
    private static void setupNativesLWJGL() {
        String lwjglPath = "org.lwjgl.librarypath";
        String userDir = System.getProperty("user.dir");
        String nativePath = "lib/natives";

        System.setProperty(lwjglPath, new File(new File(userDir, nativePath),
                LWJGLUtil.getPlatformName()).getAbsolutePath());

        String inputPath = "net.java.games.input.librarypath";
        String lwjglProperty = System.getProperty(lwjglPath);

        System.setProperty(inputPath, lwjglProperty);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        setupNativesLWJGL();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameHubFrame().setVisible(true);
            }
        });
    }
}
