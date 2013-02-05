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
import org.fridlund.javalabra.pacman.scenes.GameplayScene;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/**
 * Handles all the input in the Pacman game. Have fields of the different
 * objects that has to be modified by input.
 *
 * @author Christoffer
 */
public class PacmanInputProfile extends InputProfile {

    private GameplayScene game;
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

    public PacmanInputProfile(GameplayScene game, Pacman pacman, Level level) {
        this.game = game;
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
    /**
     * Checks for an Controller (this game has support for XBox360 hand
     * controller).
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

    /**
     * Update method that checks for input from user. By calling the
     * super.update(delta) method, it is able to use the different handling
     * methods (controller, mouse and keyboard).
     *
     * @param delta
     */
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

    /**
     * Method that checks for mouse input changes.
     *
     * @param delta
     */
    @Override
    public void handleMouseInput(float delta) {
        if (Mouse.isButtonDown(0)) {
            game.rotateCameraLeft();
        } else if (Mouse.isButtonDown(1)) {
            game.rotateCameraRight();
        }
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

        while (Keyboard.next()) {
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                game.superSnackEaten();
            }
        }

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
    /**
     * Based upon the current DX and DY, the animation on Pacman is chosen here.
     */
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

    /**
     * Makes sure that Pacman can't go in y-direction when he has left the level
     * for teleportation.
     */
    private void resetDyWhenMovingOutsideBoard() {
        if (pacman.getX() < 0 || pacman.getX() + pacman.getWidth() > level.getWidth()) {
            dy = 0;
        }
    }

    /**
     * Teleports Pacman from one side to the other, when he has left the level
     * through one of the corridors.
     */
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

    /**
     * Helper method to specify what action to use, when buttons are pressed on
     * the hand controller.
     *
     * @param delta
     */
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
