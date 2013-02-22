/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.scenes.menus;

import java.awt.Point;
import java.awt.Rectangle;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

/**
 * Simple button used by the Menu system.
 *
 * @author Christoffer
 */
public class Button extends TextMenuItem {

    /**
     *
     */
    protected float padding = 10f;
    private Rectangle rect;
    private float w;
    private float h;
    private Action action;
    private float red = 0.2f;

    /**
     *
     * @param text
     * @param x
     * @param y
     * @param w
     * @param h
     * @param action
     * @param fontName
     */
    public Button(String text, float x, float y, float w, float h, Action action, String fontName) {
        super(text, fontName, x, y);

        this.w = w;
        this.h = h;
        this.action = action;

        this.rect = new Rectangle((int) (x - padding), (int) (y - padding), (int) (w + 2 * padding), (int) (h + 2 * padding));
    }

    //=================================================================
    /*
     * OVERRIDDEN METHODS
     */
    //=================================================================
    /**
     *
     * @param delta
     */
    @Override
    public void update(float delta) {
    }

    /**
     *
     */
    @Override
    public void render() {
        glBegin(GL_QUADS);
        {
            glColor4f(red, 0.2f, 0.2f, 1.0f);
            glVertex2f(x - padding, y - padding);
            glVertex2f(x - padding, y + h + padding);
            glVertex2f(x + w + padding, y + h + padding);
            glVertex2f(x + w + padding, y - padding);
        }
        glEnd();
        super.render();
    }

    //=================================================================
    /*
     * PUBLIC METHODS
     */
    //=================================================================
    /**
     *
     * @param action
     */
    public void addAction(Action action) {
        this.action = action;
    }

    /**
     * Checks if the mouse cursor is above the button or not.
     *
     * @param mouseX
     * @param mouseY
     * @return
     */
    public boolean isUnderMouse(int mouseX, int mouseY) {
        Point mouseCursor = new Point(mouseX, Display.getHeight() - mouseY);
        boolean inside = rect.contains(mouseCursor);
        if (inside) {
            red = 1.0f;
        } else {
            red = 0.2f;
        }
        return inside;
    }

    /**
     * If the button has an action assigned, it'll get executed here. Assign an
     * action using the addAction() method.
     *
     * @addAction()
     */
    public void executeAction() {
        if (action != null) {
            action.execute();
        }
    }
}
