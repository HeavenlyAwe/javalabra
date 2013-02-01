/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.hud;

import org.fridlund.javalabra.game.Screen;
import org.fridlund.javalabra.game.cameras.Camera;
import org.fridlund.javalabra.game.utils.FontLoader;
import org.fridlund.javalabra.pacman.entities.Pacman;
import org.fridlund.javalabra.pacman.levels.Level;
import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Christoffer
 */
public class Hud {

    private Camera camera;
    private Level level;
    private Pacman pacman;

    public Hud(Camera camera, Level level, Pacman pacman) {
        this.camera = camera;
        this.level = level;
        this.pacman = pacman;
    }

    public void update(float delta) {
    }

    public void render() {



        
        
        renderLives();
        
        
        FontLoader.renderString("Points: " + pacman.getPoints(), 10, 10, "times new roman");
        FontLoader.renderString("Lives: " + pacman.getLives(), 10, 35, "times new roman");
//        glBegin(GL_QUADS);
//        {
//            glColor4f(0, 0, 0, 0.7f);
//            glTexCoord2f(0, 0);
//            glVertex2f(0, 0);
//            glTexCoord2f(32, 0);
//            glVertex2f(Display.getWidth(), 0);
//            glTexCoord2f(32, 32);
//            glVertex2f(Display.getWidth(), Display.getHeight());
//            glTexCoord2f(0, 32);
//            glVertex2f(0, Display.getHeight());
//        }
//        glEnd();

    }

    private void renderLives() {


        float x0 = (Display.getWidth() - level.getWidth()) / 2;
        float y0 = Display.getHeight() - 2 * pacman.getHeight();
        float x1 = x0 + pacman.getWidth();
        float y1 = y0 + pacman.getHeight();

//        x0 = 0;
//        y0 = 0;
//        x1 = 32;
//        y1 = 32;

        glBegin(GL_QUADS);
        {
            glColor3f(1, 0, 0);
            glVertex2f(x0, y0);
            glVertex2f(x1, y0);
            glVertex2f(x1, y1);
            glVertex2f(x0, y1);
        }
        glEnd();

    }
}
