/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman;

import org.fridlund.javalabra.Launcher;
import org.fridlund.javalabra.game.Screen;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Christoffer
 */
public class PacmanGameTest {

    PacmanGame game;

    @BeforeClass
    public static void init() {
        Launcher.setupNativesLWJGL();
        Screen.setupDisplay("PacmanTest");
        Screen.setupLWJGL();
    }

    @Before
    public void setUp() {
        game = new PacmanGame();
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

    @Test
    public void setupGameTest() {
        game.setup();
    }
}
