/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.pacman.input;

import java.util.Collection;
import java.util.Iterator;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import org.fridlund.javalabra.game.input.InputProfile;
import org.fridlund.javalabra.game.scenes.menus.Button;
import org.fridlund.javalabra.game.scenes.menus.MenuItem;
import org.lwjgl.input.Mouse;

/**
 * Input Profile used in all the menus. The same instance should be used in all
 * menus through the setMenuItems method.
 *
 * @author Christoffer
 */
public class MenuInputProfile extends InputProfile {

    private Controller controller;
    private Collection<MenuItem> menuItems;
    private float dx;
    private float dy;
    private float deadZone;
    private boolean released;

    public MenuInputProfile() {
        dx = 0;
        dy = 0;
        deadZone = 0.2f;
        released = true;
    }

    //=================================================================
    /*
     * OVERRIDDEN METHODS
     */
    //=================================================================
    /**
     * Checks if a XBox360 Controller is connected when the profile is setup.
     */
    @Override
    public void setup() {
        for (Controller c : ControllerEnvironment.getDefaultEnvironment().getControllers()) {
            if (c.getType() == Controller.Type.GAMEPAD) {
                controller = c;
                System.out.println(controller.getName());
            }
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    /**
     * Choose action based on the controller input. Moves the mouse cursor with
     * the controller.
     *
     * @param delta
     */
    @Override
    public void handleControllerInput(float delta) {
        dx = 0;
        dy = 0;

        if (controller != null) {
            if (controller.poll()) {
                for (Component c : controller.getComponents()) {
                    if (c.getName().equalsIgnoreCase("Y Axis")) {
                        if (c.getPollData() > deadZone || c.getPollData() < -deadZone) {
                            dy = -c.getPollData() * delta;
                        }
                    } else if (c.getName().equalsIgnoreCase("X Axis")) {
                        if (c.getPollData() > deadZone || c.getPollData() < -deadZone) {
                            dx = c.getPollData() * delta;
                        }
                    } else if (c.getName().equalsIgnoreCase("Button 0")) {
                        executeButtonActionController(c);
                    }
                }
            }
        }

        if (dx != 0 || dy != 0) {
            Mouse.setCursorPosition(Mouse.getX() + (int) dx, Mouse.getY() + (int) dy);
        }
    }

    /**
     * Loops throgh all the menuItems constantly and if it's a button, it'll try
     * to click it by calling
     *
     * @executeButtonActionMouse() method.
     *
     * @param delta
     */
    @Override
    public void handleMouseInput(float delta) {
        Iterator<MenuItem> it = menuItems.iterator();
        MenuItem current;
        while (it.hasNext()) {
            current = it.next();
            if (current instanceof Button) {
                executeButtonActionMouse(current);
            }
        }
    }

    @Override
    public void handleKeyboardInput(float delta) {
    }

    //=================================================================
    /*
     * PRIVATE METHODS
     */
    //=================================================================
    /**
     * Checks if button is under mouse and Left Mouse Button is released, to
     * activate the action of the button.
     *
     * @param current
     */
    private void executeButtonActionMouse(MenuItem current) {
        if (((Button) current).isUnderMouse(Mouse.getX(), Mouse.getY())) {
            while (Mouse.next()) {
                if (Mouse.isButtonDown(0)) {
                    if (Mouse.getEventButtonState()) {
                        ((Button) current).executeAction();
                    }
                }
            }
        }
    }

    /**
     * Checks if the Mouse Cursor points on a button, and loops through all the
     * items, to see which one is clicked.
     *
     * @param c
     */
    private void executeButtonActionController(Component c) {
        Iterator<MenuItem> it = menuItems.iterator();
        MenuItem current;
        while (it.hasNext()) {
            current = it.next();
            if (current instanceof Button) {
                if (((Button) current).isUnderMouse(Mouse.getX(), Mouse.getY())) {
                    if (c.getPollData() == 1 && released) {
                        ((Button) current).executeAction();
                        released = false;
                    }
                    if (c.getPollData() == 0) {
                        released = true;
                    }
                }
            }
        }
    }

    //=================================================================
    /*
     * PUBLIC METHODS
     */
    //=================================================================
    //=================================================================
    /*
     * SETTERS
     */
    //=================================================================
    public void setMenuItems(Collection<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
}
