/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.entities;

import org.fridlund.javalabra.Launcher;
import org.fridlund.javalabra.game.Screen;
import org.fridlund.javalabra.pacman.levels.Level;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Christoffer
 */
public class GhostTest {

    Level level;
    Ghost ghost;

    @BeforeClass
    public static void init() {
        Launcher.setupNativesLWJGL();
        Screen.setupDisplay("PacmanTest");
        Screen.setupLWJGL();
    }

    @AfterClass
    public static void cleanUp() {
        Screen.cleanUp();
    }

    @Before
    public void setUp() {
        level = new Level();
        ghost = new Ghost(level, 1);
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

    @Test
    public void ghostCreatedTest() {
        assertNotNull("Ghost not created", ghost);
    }

    @Test
    public void ghostPositionAtStart() {
        assertEquals("X position wrong.", ghost.getX(), (level.getWidth() - ghost.getWidth()) / 2, 3);
        assertEquals("Y position wrong.", ghost.getY(), (level.getHeight() - ghost.getHeight()) / 2, 3);
    }

    @Test
    public void moveGhost10PixelsX() {
        ghost.move(10, 0);
        assertEquals("X position wrong.", ghost.getX(), (level.getWidth() - ghost.getWidth()) / 2 + 10, 3);
        assertEquals("Y position wrong.", ghost.getY(), (level.getHeight() - ghost.getHeight()) / 2, 3);
    }

    @Test
    public void moveGhost20PoxelsX() {
        ghost.move(20, 0);
        assertEquals("X position wrong.", ghost.getX(), (level.getWidth() - ghost.getWidth()) / 2 + 20, 3);
        assertEquals("Y position wrong.", ghost.getY(), (level.getHeight() - ghost.getHeight()) / 2, 3);
    }

    @Test
    public void moveGhost10PoxelsY() {
        ghost.move(0, 10);
        assertEquals("X position wrong.", ghost.getX(), (level.getWidth() - ghost.getWidth()) / 2, 3);
        assertEquals("Y position wrong.", ghost.getY(), (level.getHeight() - ghost.getHeight()) / 2 + 10, 3);
    }

    @Test
    public void moveGhost20PoxelsY() {
        ghost.move(0, 20);
        assertEquals("X position wrong.", ghost.getX(), (level.getWidth() - ghost.getWidth()) / 2, 3);
        assertEquals("Y position wrong.", ghost.getY(), (level.getHeight() - ghost.getHeight()) / 2 + 20, 3);
    }
}
