/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.pacman.managers;

import org.fridlund.javalabra.game.Screen;
import org.fridlund.pacman.entities.Pacman;
import org.fridlund.pacman.highscores.HighScoreManager;
import org.fridlund.pacman.level.Level;
import org.fridlund.pacman.scenes.GameplayScene;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Christoffer
 */
public class SnackManagerTest {

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
    SnackManager sm;

    /**
     *
     */
    @Before
    public void setUp() {
        GameplayScene game = new GameplayScene(0, new HighScoreManager());
        Level level = new Level();
        Pacman pacman = new Pacman(level);

        sm = new SnackManager(game, pacman, level);
    }

    /**
     *
     */
    @Test
    public void checkSnackManagerConstructedTest() {
        assertNotNull("SnackManager wasn't created.", sm);
    }
//    @Test
//    public void checkNoSnacksAtBeginningTest() {
//        assertEquals("Too many snacks.", 0, sm.getSnacks().size());
//    }
}
