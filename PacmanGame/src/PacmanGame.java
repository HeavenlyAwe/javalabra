/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import org.fridlund.javalabra.game.GameLWJGL;
import org.fridlund.javalabra.game.scenes.Scene;
import org.fridlund.javalabra.game.utils.FontLoader;
import org.fridlund.pacman.scenes.MainMenuScene;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

/**
 *
 * @author Christoffer
 */
public class PacmanGame extends GameLWJGL {
    
    private Scene menuScene;
    
    public PacmanGame() {
        super("Pacman");
    }
    
    @Override
    public void setup() {
        FontLoader.loadFont("Times New Roman", FontLoader.FontStyle.PLAIN, 30);
        menuScene = new MainMenuScene();
    }
    
    @Override
    public void cleanUp() {
        menuScene.cleanUp();
    }
    
    @Override
    public void update(float delta) {
        super.update(delta);
        menuScene.update(delta);
    }
    
    @Override
    public boolean gameLoopRestrictions() {
        return !Display.isActive() || !Mouse.isInsideWindow();
    }
    
    @Override
    public void render() {
        super.render();
        menuScene.render();
    }
}
