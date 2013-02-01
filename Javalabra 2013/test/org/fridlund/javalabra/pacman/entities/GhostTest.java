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
        ghost = new Ghost(new GhostGraphics(1), level);
    }

    @Test
    public void ghostCreatedTest() {
        assertNotNull("Ghost not created", ghost);
    }

    @Test
    public void ghostPositionAtStart() {
        assertEquals("X position wrong.", 14 * level.getTileWidth() + 2 * level.getTileWidth(), ghost.getX(), 3);
        assertEquals("Y position wrong.", 16 * level.getTileHeight(), ghost.getY(), 3);
    }

    @Test
    public void moveGhost10PixelsX() {
        ghost.move(10, 0);
        assertEquals("X position wrong.", 14 * level.getTileWidth() + 2 * level.getTileWidth() + 10, ghost.getX(), 3);
        assertEquals("Y position wrong.", 16 * level.getTileHeight(), ghost.getY(), 3);
    }

    @Test
    public void moveGhost20PixelsX() {
        ghost.move(20, 0);
        assertEquals("X position wrong.", 14 * level.getTileWidth() + 2 * level.getTileWidth() + 20, ghost.getX(), 3);
        assertEquals("Y position wrong.", 16 * level.getTileHeight(), ghost.getY(), 3);
    }

    @Test
    public void moveGhost10PixelsY() {
        ghost.move(0, 10);
        assertEquals("X position wrong.", 14 * level.getTileWidth() + 2 * level.getTileWidth(), ghost.getX(), 3);
        assertEquals("Y position wrong.", 16 * level.getTileHeight() + 10, ghost.getY(), 3);
    }

    @Test
    public void moveGhost20PixelsY() {
        ghost.move(0, 20);
        assertEquals("X position wrong.", 14 * level.getTileWidth() + 2 * level.getTileWidth(), ghost.getX(), 3);
        assertEquals("Y position wrong.", 16 * level.getTileHeight() + 20, ghost.getY(), 3);
    }
}
