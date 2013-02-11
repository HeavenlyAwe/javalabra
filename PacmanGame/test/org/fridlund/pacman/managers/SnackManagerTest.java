/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.pacman.managers;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Christoffer
 */
public class SnackManagerTest {
    
    public SnackManagerTest() {
    }
    
    @Before
    public void setUp() {
    }

    /**
     * Test of cleanUp method, of class SnackManager.
     */
    @Test
    public void testCleanUp() {
        System.out.println("cleanUp");
        SnackManager instance = null;
        instance.cleanUp();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class SnackManager.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        float delta = 0.0F;
        SnackManager instance = null;
        instance.update(delta);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of render method, of class SnackManager.
     */
    @Test
    public void testRender() {
        System.out.println("render");
        SnackManager instance = null;
        instance.render();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSnacks method, of class SnackManager.
     */
    @Test
    public void testGetSnacks() {
        System.out.println("getSnacks");
        SnackManager instance = null;
        List expResult = null;
        List result = instance.getSnacks();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
