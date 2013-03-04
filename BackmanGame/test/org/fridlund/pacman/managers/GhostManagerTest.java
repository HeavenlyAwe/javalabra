/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.pacman.managers;

import org.fridlund.backman.managers.GhostManager;
import org.fridlund.javalabra.game.Screen;
import org.fridlund.backman.entities.Backman;
import org.fridlund.backman.highscores.HighScoreManager;
import org.fridlund.backman.level.Level;
import org.fridlund.backman.scenes.GameplayScene;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import sun.misc.Launcher;

/**
 *
 * @author Christoffer
 */
public class GhostManagerTest {

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
    GhostManager gm;

    /**
     *
     */
    @Before
    public void setUp() {
        GameplayScene game = new GameplayScene(0, new HighScoreManager());
        Level level = new Level();
        Backman pacman = new Backman(level);

        gm = new GhostManager(game, pacman, level, 4);
    }

    /**
     *
     */
    @Test
    public void noGhostsAtBeginning() {
        assertEquals("Too many ghosts.", 4, gm.getGhosts().size());
    }

    /**
     *
     */
    @Test
    public void spawnFirstGhostTest() {
        // the first ghost should spawn after 1 second,
        // while the rest are spawning on intervals of 5 seconds
        gm.update(1000);
        assertEquals("Wrong amount of ghosts.", 4, gm.getGhosts().size());
    }

    /**
     *
     */
    @Test
    public void spawnSecondGhostTest() {
        gm.update(1000);
        gm.update(5000);
        assertEquals("Wrong amount of ghosts.", 4, gm.getGhosts().size());
    }

    /**
     *
     */
    @Test
    public void spawnThreeGhostsTest() {
        for (int i = 1; i < 4; i++) {
            gm.update(5000);
            assertEquals("Wrong amount of ghosts.", 4, gm.getGhosts().size());
        }
    }

    /**
     *
     */
    @Test
    public void spawnFourGhostsTest() {
        for (int i = 1; i < 5; i++) {
            gm.update(5000);
            assertEquals("Wrong amount of ghosts.", 4, gm.getGhosts().size());
        }
    }

    /**
     *
     */
    @Test
    public void spawnFiveGhostsTest() {
        for (int i = 1; i < 5; i++) {
            gm.update(5000);
        }
        gm.update(5000);
        assertEquals("Wrong amount of ghosts.", 4, gm.getGhosts().size());
    }

    /**
     *
     */
    @Test
    public void spawnSixGhostsTest() {
        for (int i = 1; i < 6; i++) {
            gm.update(5000);
        }
        gm.update(5000);
        assertEquals("Wrong amount of ghosts.", 4, gm.getGhosts().size());
    }
}
