/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.pacman.entities;

import org.fridlund.javalabra.game.Screen;
import org.fridlund.pacman.level.Level;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Christoffer
 */
public class GhostTest {

    Level level;
    Ghost ghost;

    @BeforeClass
    public static void init() {
        Screen.setupNativesLWJGL();
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
    
    @Test
    public void ghostReleaseableInBeginningTest(){
        assertFalse("Ghost should not be releaseable.", ghost.isReleaseable());
    }
    
    @Test
    public void setGhostReleaseableTest(){
        ghost.setReleaseable(true);
        assertTrue("Ghsot is not releaseable.", ghost.isReleaseable());
    }

    @Test
    public void ghostNotKillableTest() {
        assertFalse("Ghost is killable.", !ghost.isInvincible());
    }

    @Test
    public void ghostKillableTest() {
        ghost.setKillable();
        assertTrue("Ghost is not killable.", !ghost.isInvincible());
    }

    @Test
    public void ghostInvincibleTest() {
        assertTrue("Ghost not invincible.", ghost.isInvincible());
    }

    @Test
    public void ghostAliveTest() {
        assertTrue("Ghost is dead.", !ghost.isDead());
    }

    @Test
    public void ghostWarningWithoutBeingKillableTest() {
        ghost.setWarningAnimation();
        assertFalse("Warning flag is active.", ghost.isWarning());
    }

    @Test
    public void ghostWarningWhenKillableTest() {
        ghost.setKillable();
        ghost.setWarningAnimation();
        assertTrue("Warning flag is active.", ghost.isWarning());
    }

    @Test
    public void killedTest() {
        ghost.kill();
        assertTrue("Ghost not dead.", ghost.isDead());
    }
}
