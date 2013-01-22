/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.sprites;

import org.fridlund.javalabra.Launcher;
import org.fridlund.javalabra.game.Screen;
import org.fridlund.javalabra.game.utils.TextureLoader;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Christoffer
 */
public class AnimationTest {

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

    private Animation animation;
    
    @Before
    public void setUp() {
        int textureID = TextureLoader.loadTextureLinear("res/pacman/images/pacman.png");
        SpriteSheet sheet = new SpriteSheet(textureID, 32, 32, 128, 128);
        animation = new Animation(sheet);
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void checkSizeOfAnimation(){
    }
}
