/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.cameras;

import org.fridlund.javalabra.game.cameras.Camera;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Christoffer
 */
public class PacmanCamera extends Camera {

    private float aspectRatio;
    private float fov = 90;
    private float zNear = 0.3f;
    private float zFar = 1000.0f;
    private float mouseSpeed = 0.01f;
    private float maxLookUp = 80;
    private float maxLookDown = -80;
    private float speedX = 0.03f;
    private float speedY = 0.03f;
    private float speedZ = 0.03f;

    public PacmanCamera(float aspectRatio) {
        this(aspectRatio, new Vector3f(0, 0, 0));
    }

    public PacmanCamera(float aspectRatio, Vector3f position) {
        this(aspectRatio, position, new Vector3f(0, 0, 0));
    }

    public PacmanCamera(float aspectRatio, Vector3f position, Vector3f rotation) {
        super(position, rotation);
        this.aspectRatio = aspectRatio;
    }

    private enum RotateDirection {

        RIGHT, LEFT, IDLE;
    }
    private RotateDirection dir = RotateDirection.IDLE;
    private float angle = 0;
    private float rotationSpeed = 0.2f;

    @Override
    public void update(float delta) {
        if (dir == RotateDirection.LEFT) {
            angle += rotationSpeed * delta;
            if (angle >= 180) {
                dir = RotateDirection.IDLE;
                angle = 180f;
            }
        } else if (dir == RotateDirection.RIGHT) {
            angle -= rotationSpeed * delta;
            if (angle <= 0) {
                dir = RotateDirection.IDLE;
                angle = 0.0f;
            }
        }

        float angleRad = (float) Math.toRadians(angle);

        position.x = (float) ((300 * Math.sin(angleRad) + 300) * Math.sin(angleRad)) + rotation.x;
        position.z = (float) ((300 * Math.sin(angleRad) + 300) * Math.cos(angleRad)) + rotation.z;

        // nice bouncing effect when only applying rotation to z-component
        // position.z = (float) ((500 * Math.sin(angleRad) + 300) * Math.cos(angleRad)) + rotation.z;
    }

    public void rotateLeft() {
        this.dir = RotateDirection.LEFT;
    }

    public void rotateRight() {
        this.dir = RotateDirection.RIGHT;
    }

    @Override
    public void applyModelViewMatrix(boolean resetMatrix) {
        glMatrixMode(GL_MODELVIEW);
        if (resetMatrix) {
            glLoadIdentity();
        }
        GLU.gluLookAt(position.x, position.y, position.z, rotation.x, rotation.y, rotation.z, 0, 1, 0);
    }

    @Override
    public void applyProjectionMatrix() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(fov, aspectRatio, zNear, zFar);
        glMatrixMode(GL_MODELVIEW);
    }

    @Override
    public void processMouse(float delta) {
    }

    @Override
    public void processKeyboard(float delta) {
    }

    public void moveFromLook(float dx, float dy, float dz) {
    }
}
