/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Christoffer
 */
public class PacmanGameTest {

    PacmanGame game;

    public PacmanGameTest() {
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
