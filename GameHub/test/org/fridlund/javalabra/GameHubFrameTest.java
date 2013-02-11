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
public class GameHubFrameTest {

    public GameHubFrameTest() {
    }
    GameHubFrame frame;

    @Before
    public void setUp() {
        frame = new GameHubFrame();
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

    @Test
    public void constructedTest() {
        assertNotNull("Failed to construct GameHubFrame.", frame);
    }
}
