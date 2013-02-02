/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.input;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import org.fridlund.javalabra.game.input.InputProfile;
import org.fridlund.javalabra.pacman.entities.Pacman;
import org.fridlund.javalabra.pacman.levels.Level;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author Christoffer
 */
public class PacmanInputProfile extends InputProfile {

    private Pacman pacman;
    private Level level;
    private Controller controller;
    private float speed;
    private float dx;
    private float dy;

    /*
     * Buttons for use when moving around in the world
     */
    private int up = Keyboard.KEY_W;
    private int down = Keyboard.KEY_S;
    private int left = Keyboard.KEY_A;
    private int right = Keyboard.KEY_D;

    public PacmanInputProfile(Pacman pacman, Level level) {
        this.pacman = pacman;
        this.level = level;

        this.controller = null;
        this.speed = 0.1f;

        this.dx = 0;
        this.dy = 0;
    }

    //=================================================================
    /*
     * OVERRIDDEN METHODS
     */
    //=================================================================
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
        dx = 0;
        dy = 0;
        super.update(delta);
        pacman.setDX(dx);
        pacman.setDY(dy);

        resetDyWhenMovingOutsideBoard();
        chooseAnimationFromDirection();
        pacman.move(dx, dy);
        teleportWhenMovingOutsideBoard();
    }

    @Override
    public void handleControllerInput(float delta) {
        if (controller != null) {
            if (controller.poll()) {
                selectControllerAction(delta);
            }
        }
    }

    @Override
    public void handleKeyboardInput(float delta) {

        // allows for movement in x and y direction at the same time
        if (!keyDown(left) && keyDown(right)) {
            dx = speed * delta;
        } else if (keyDown(left) && !keyDown(right)) {
            dx = -speed * delta;
        }

        if (keyDown(up) && !keyDown(down)) {
            dy = speed * delta;
        } else if (!keyDown(up) && keyDown(down)) {
            dy = -speed * delta;
        }

    }

    //=================================================================
    /*
     * PRIVATE METHODS
     */
    //=================================================================
    private void chooseAnimationFromDirection() {
        if (dx < 0) {
            pacman.setAnimation("left");
        } else if (dx > 0) {
            pacman.setAnimation("right");
        } else if (dy > 0) {
            pacman.setAnimation("up");
        } else if (dy < 0) {
            pacman.setAnimation("down");
        }
    }

    private void resetDyWhenMovingOutsideBoard() {
        if (pacman.getX() < 0 || pacman.getX() + pacman.getWidth() > level.getWidth()) {
            dy = 0;
        }
    }

    private void teleportWhenMovingOutsideBoard() {
        if (level.outsideOnTheRight(pacman)) {
            pacman.setX(-pacman.getWidth());
            pacman.setY(level.getHeight() / 2);
        }
        if (level.outsideOnTheLeft(pacman)) {
            pacman.setX(level.getWidth());
            pacman.setY(level.getHeight() / 2);
        }
    }

    private void selectControllerAction(float delta) {
        for (Component c : controller.getComponents()) {
            switch (c.getName()) {
                case "Y Axis":
                    if (c.getPollData() > 0.1f) {
                        pacman.setDY(speed * c.getPollData() * delta);
                        dy = speed * c.getPollData() * delta;
                    } else if (c.getPollData() < -0.1f) {
                        dy = speed * c.getPollData() * delta;
                    }
                    break;
                case "X Axis":
                    if (c.getPollData() > 0.5f) {
                        dx = speed * delta;
                    } else if (c.getPollData() < -0.5f) {
                        dx = speed * delta;
                    }
                    break;
            }
        }
    }
}
