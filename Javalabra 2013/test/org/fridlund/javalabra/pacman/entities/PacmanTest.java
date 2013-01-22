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
public class PacmanTest {

    Level level;
    Pacman pacman;

    @BeforeClass
    public static void init() {
        Launcher.setupNativesLWJGL();
        Screen.setupDisplay("PacmanTest");
        Screen.setupLWJGL();
    }
    
    @AfterClass
    public static void cleanUp(){
        Screen.cleanUp();
    }

    @Before
    public void setUp() {
        level = new Level();
        pacman = new Pacman(level);
    }

    @Test
    public void pacmanExistsTest() {
        assertNotNull("Level null", level);
        assertNotNull("Pacman null", pacman);
    }

    @Test
    public void positionAtStartTest() {
        assertEquals("Position X not correct", pacman.getX(), 17 * level.getTileWidth(), 3);
        assertEquals("Position Y not correct", pacman.getY(), 11 * level.getTileHeight(), 3);
    }
    
    @Test
    public void spawnPointWalkableTest(){
        assertTrue("Not walkable", level.walkableTile(pacman, 0, 0));
    }
    
    @Test
    public void moveRight(){
        assertTrue("Failed to walk right", level.walkableTile(pacman, level.getTileWidth(), 0));
    }
    
    @Test
    public void moveLeft(){
        assertTrue("Failed to walk left", level.walkableTile(pacman, -level.getTileWidth(), 0));
    }
    
    @Test
    public void moveUp(){
        assertFalse("Could walk up", level.walkableTile(pacman, 0, -level.getTileHeight()));
    }
    
    @Test
    public void moveDown(){
        assertTrue("Failed to walk down", level.walkableTile(pacman, 0, level.getTileHeight()));
    }
    
    @Test
    public void pacmanSetPositionTest(){
        pacman.setPosition(0, 0);
        assertEquals("Wrong X position.", pacman.getX(), 0, 3);
        assertEquals("Wrong Y position.", pacman.getY(), 0, 3);
    }
    
}
