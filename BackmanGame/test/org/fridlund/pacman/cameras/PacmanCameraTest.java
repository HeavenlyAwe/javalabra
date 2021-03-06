/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.pacman.cameras;

import org.fridlund.backman.cameras.PacmanCamera;
import org.fridlund.javalabra.game.Screen;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lwjgl.opengl.Display;

/**
 *
 * @author Christoffer
 */
public class PacmanCameraTest {

    /**
     *
     */
    @BeforeClass
    public static void init() {
        Screen.setupNativesLWJGL();
        Screen.setupDisplay("PacmanTest");
        Screen.setupLWJGL();
    }

    /**
     *
     */
    @AfterClass
    public static void cleanUp() {
        Screen.cleanUp();
    }
    PacmanCamera camera;

    /**
     *
     */
    @Before
    public void setUp() {
        camera = new PacmanCamera(Display.getWidth() / Display.getHeight(), 300);
    }

    /**
     *
     */
    @Test
    public void cameraConstructedTest() {
        assertNotNull("Camera failed to be constructed.", camera);
    }

    /**
     *
     */
    @Test
    public void rotateCameraLeftTest() {
        camera.rotateLeft();
        camera.update(1000);
        assertEquals("Wrong angle!", Math.PI, camera.getAngle(), 3);
    }

    /**
     *
     */
    @Test
    public void rotateCameraLeftAfterRightTest() {
        camera.rotateRight();
        camera.update(1000);
        camera.rotateLeft();
        camera.update(1000);
        assertEquals("Wrong angle!", Math.PI, camera.getAngle(), 3);
    }

    /**
     *
     */
    @Test
    public void rotateCameraRightTest() {
        camera.rotateRight();
        camera.update(1000);
        assertEquals("Wrong angle!", 0, camera.getAngle(), 3);
    }

    /**
     *
     */
    @Test
    public void rotateCameraRightAfterLeftTest() {
        camera.rotateLeft();
        camera.update(1000);
        camera.rotateRight();
        camera.update(1000);
        assertEquals("Wrong angle!", 0, camera.getAngle(), 3);
    }

    /**
     *
     */
    @Test
    public void rotateCameraLeftAfterLeftTest() {
        camera.rotateLeft();
        camera.update(1000);
        camera.rotateLeft();
        camera.update(1000);
        assertEquals("Wrong angle!", Math.PI, camera.getAngle(), 3);
    }

    /**
     *
     */
    @Test
    public void rotateCameraRightLeftLeftTest() {
        camera.rotateRight();
        camera.update(1000);
        camera.rotateLeft();
        camera.rotateLeft();
        camera.update(1000);
        camera.rotateLeft();
        camera.update(1000);
        assertEquals("Wrong angle!", Math.PI, camera.getAngle(), 3);
    }

    /**
     *
     */
    @Test
    public void rotateCameraLeftRightLeftTest() {
        camera.rotateLeft();
        camera.update(1000);
        camera.rotateRight();
        camera.update(1000);
        camera.rotateLeft();
        camera.update(1000);
        assertEquals("Wrong angle!", Math.PI, camera.getAngle(), 3);
    }

    /**
     *
     */
    @Test
    public void rotateCameraRightLeftRightTest() {
        camera.rotateRight();
        camera.update(1000);
        camera.rotateLeft();
        camera.update(1000);
        camera.rotateRight();
        camera.update(1000);
        assertEquals("Wrong angle!", 0, camera.getAngle(), 3);
    }

    /**
     *
     */
    @Test
    public void rotateCameraLeftRightRightTest() {
        camera.rotateLeft();
        camera.update(1000);
        camera.rotateRight();
        camera.update(1000);
        camera.rotateRight();
        camera.update(1000);
        assertEquals("Wrong angle!", 0, camera.getAngle(), 3);

    }
}
