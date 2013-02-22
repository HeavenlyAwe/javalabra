/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Christoffer
 */
public class GameHubTest {
    
    GameHub hub;
    
    /**
     *
     */
    @Before
    public void setUp() {
        hub = new GameHub();
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

    /**
     *
     */
    @Test
    public void testOneGameInList() {
        assertEquals("More or less games than one", hub.getListOfGames().size(), 1);
    }
    
    /**
     *
     */
    @Test
    public void testPacmanGameInList() {
        assertTrue("First game in list is not an instance of pacman", hub.getListOfGames().get(0).equals("Pacman"));
    }
}
