/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.entities;

import java.util.ArrayList;
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
public class PacmanTest {

    Level level;
    Pacman pacman;
    ArrayList<Integer> allowedTiles;

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
        pacman = new Pacman(level);
        allowedTiles = new ArrayList<>();
        allowedTiles.add(Level.WALKABLE);
    }

    @Test
    public void pacmanExistsTest() {
        assertNotNull("Level null", level);
        assertNotNull("Pacman null", pacman);
    }

    @Test
    public void positionAtStartTest() {
        pacman.spawn(17, 1);
        assertEquals("Position X not correct: " + pacman.getX(), pacman.getX(), 17 * level.getTileWidth(), 3);
        assertEquals("Position Y not correct: " + pacman.getY(), pacman.getY(), 1 * level.getTileHeight(), 3);
    }

    @Test
    public void spawnPointWalkableTest() {
        pacman.spawn(17, 1);
        assertTrue("Not walkable", level.walkableTile(pacman, 0, 0, allowedTiles));
    }

    @Test
    public void moveRight() {
        pacman.spawn(17, 1);
        assertTrue("Failed to walk right", level.walkableTile(pacman, level.getTileWidth(), 0, allowedTiles));
    }

    @Test
    public void moveLeft() {
        pacman.spawn(17, 1);
        assertTrue("Failed to walk left", level.walkableTile(pacman, -level.getTileWidth(), 0, allowedTiles));
    }

    @Test
    public void moveUp() {
        pacman.spawn(17, 1);
        assertFalse("Could walk up", level.walkableTile(pacman, 0, -level.getTileHeight(), allowedTiles));
    }

    @Test
    public void moveDown() {
        pacman.spawn(17, 1);
        assertFalse("Failed to walk down", level.walkableTile(pacman, 0, level.getTileHeight(), allowedTiles));
    }

    @Test
    public void pacmanSetPositionTest() {
        pacman.setPosition(0, 0);
        assertEquals("Wrong X position.", pacman.getX(), 0, 3);
        assertEquals("Wrong Y position.", pacman.getY(), 0, 3);
    }

    @Test
    public void pacmanAtOriginalSpawnTest() {
        assertEquals("Wrong X position.", pacman.getX(), level.getWidth() / 2 - level.getTileWidth(), 3);
        assertEquals("Wrong Y position.", pacman.getY(), level.getTileHeight(), 3);
    }
}
