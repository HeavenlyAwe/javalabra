/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.managers;

import org.fridlund.javalabra.Launcher;
import org.fridlund.javalabra.game.Screen;
import org.fridlund.javalabra.pacman.entities.Pacman;
import org.fridlund.javalabra.pacman.levels.Level;
import org.fridlund.javalabra.pacman.scenes.GameplayScene;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Christoffer
 */
public class SnackManagerTest {

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
    SnackManager sm;

    @Before
    public void setUp() {
        GameplayScene game = new GameplayScene();
        Level level = new Level();
        Pacman pacman = new Pacman(level);

        sm = new SnackManager(game, pacman, level);
    }

    @Test
    public void checkSnackManagerConstructedTest() {
        assertNotNull("SnackManager wasn't created.", sm);
    }

//    @Test
//    public void checkNoSnacksAtBeginningTest() {
//        assertEquals("Too many snacks.", 0, sm.getSnacks().size());
//    }
}
