/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.pacman.input;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import org.fridlund.javalabra.game.input.InputProfile;
import org.fridlund.pacman.entities.Pacman;
import org.fridlund.pacman.level.Level;
import org.fridlund.pacman.scenes.GameplayScene;
import org.lwjgl.input.Keyboard;

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
    private String controllerComponent;
    private boolean released;

    /*
     * Buttons for use when moving around in the world
     */
    private int up = Keyboard.KEY_W;
    private int down = Keyboard.KEY_S;
    private int left = Keyboard.KEY_A;
    private int right = Keyboard.KEY_D;

    public PacmanInputProfile(GameplayScene game, Pacman pacman, Level level) {
        super();

        this.game = game;
        this.pacman = pacman;
        this.level = level;
        this.released = true;

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
//        if (Mouse.isButtonDown(0)) {
//            game.rotateCameraLeft();
//        } else if (Mouse.isButtonDown(1)) {
//            game.rotateCameraRight();
//        }
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
            if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                game.pause();
            } else if (Keyboard.isKeyDown(Keyboard.KEY_F1)) {
                game.toggleDebug();
            }
            if (game.isDebug()) {
                if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                    game.superSnackEaten();
                } else if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
                    game.rotateCameraLeft();
                } else if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
                    game.rotateCameraRight();
                } else if (Keyboard.isKeyDown(Keyboard.KEY_F11)) {
                    game.setGameWon();
                } else if (Keyboard.isKeyDown(Keyboard.KEY_F12)) {
                    game.setGameOver();
                }
            }

        }

        if (!game.isPaused()) {

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

            controllerComponent = c.getName();

            if (controllerComponent.equalsIgnoreCase("Y Axis")) {
                if (c.getPollData() > 0.2f) {
                    dy = -speed * delta;
                } else if (c.getPollData() < -0.2f) {
                    dy = speed * delta;
                }
                pacman.setDY(dy);
            } else if (controllerComponent.equalsIgnoreCase("X Axis")) {
                if (c.getPollData() > 0.2f) {
                    dx = speed * delta;
                } else if (c.getPollData() < -0.2f) {
                    dx = -speed * delta;
                }
                pacman.setDX(dx);
            } else if (controllerComponent.equalsIgnoreCase("Button 5")) {
                if (c.getPollData() == 1 && released) {
                    game.toggleDebug();
                    released = false;
                }
                if (c.getPollData() == 0) {
                    released = true;
                }
            }

            if (game.isDebug()) {
                switch (c.getName()) {
                    case "Z Axis":
                        if (c.getPollData() > 0.5f) {
                            game.rotateCameraLeft();
                        } else if (c.getPollData() < -0.5f) {
                            game.rotateCameraRight();
                        }
                        break;
                    case "Button 0":
                        if (c.getPollData() == 1) {
                            game.superSnackEaten();
                        }
                        break;
                    case "Button 6":
                        if (c.getPollData() == 1) {
                            game.setGameOver();
                        }
                        break;
                    case "Button 7":
                        if (c.getPollData() == 1) {
                            game.setGameWon();
                        }
                        break;
                }
            }

        }
    }
}
