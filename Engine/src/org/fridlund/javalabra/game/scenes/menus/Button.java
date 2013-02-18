/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.scenes.menus;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Christoffer
 */
public class Button extends MenuItem {

    protected float padding = 10f;
    private Rectangle rect;
    private float w;
    private float h;
    private Action action;

    public Button(String text, float x, float y, float w, float h, Action action, String fontName) {
        super(text, x, y, fontName);
        this.w = w;
        this.h = h;
        this.action = action;

        this.rect = new Rectangle((int) (x - padding), (int) (y - padding), (int) (w + 2 * padding), (int) (h + 2 * padding));
    }

    public void addAction(Action action) {
        this.action = action;
    }
//    @Override
//    public void update(float delta) {
//        super.update(delta);
//
//        leftMouseClicked = false;
//        rightMouseClicked = false;
//
//        Point mouseCursor = new Point(Mouse.getX(), Display.getHeight() - Mouse.getY());
//        if (rect.contains(mouseCursor)) {
//            red = 1.0f;
//            while (Mouse.next()) {
//                if (Mouse.isButtonDown(0)) {
//                    if (Mouse.getEventButtonState()) {
//                        leftMouseClicked = true;
//                    } else {
//                    }
//                } else if (Mouse.isButtonDown(1)) {
//                    if (Mouse.getEventButtonState()) {
//                        rightMouseClicked = true;
//                    } else {
//                    }
//                }
//            }
//        } else {
//            red = 0.2f;
//        }
//    }
    private float red = 0.2f;

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

    public void executeAction() {
        if (action != null) {
            action.execute();
        }
    }

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
}
