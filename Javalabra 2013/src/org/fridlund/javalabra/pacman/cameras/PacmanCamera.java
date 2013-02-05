/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.cameras;

import org.fridlund.javalabra.game.cameras.Camera;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

/**
 * A special GluLookAt camera used in the game of Pacman.
 *
 * @author Christoffer
 */
public class PacmanCamera extends Camera {

    /*
     * Fields used when rotating the camera in 3D (swapping the X-axis)
     */
    private enum RotateDirection {

        RIGHT, LEFT, IDLE;
    }
    private RotateDirection dir = RotateDirection.IDLE;
    private float angle = 0;
    private float maxAngle = (float) Math.PI;
    private float minAngle = 0.0f;
    private float rotationSpeed = 0.005f;

    /*
     * Camera specific fields
     */
    private float aspectRatio;
    private float fov;
    private float zNear;
    private float zFar;
    private float radius;

    public PacmanCamera(float aspectRatio, float radius) {
        this(aspectRatio, radius, new Vector3f(0, 0, 0));
    }

    public PacmanCamera(float aspectRatio, float radius, Vector3f position) {
        this(aspectRatio, radius, position, new Vector3f(0, 0, 0));
    }

    public PacmanCamera(float aspectRatio, float radius, Vector3f position, Vector3f rotation) {
        super(position, rotation);

        this.aspectRatio = aspectRatio;
        this.fov = 90;
        this.zNear = 0.3f;
        this.zFar = 1000f;
        this.radius = radius;
    }

    //=================================================================
    /*
     * OVERRIDDEN METHODS
     */
    //=================================================================
    /**
     * Rotates the camera, when the rotateLeft or rotateRight methods have been
     * called. Makes sure that the level always are shown by moving it further
     * away during the rotation.
     *
     * @param delta
     */
    @Override
    public void update(float delta) {
        super.update(delta);

        if (dir == RotateDirection.LEFT) {
            angle += rotationSpeed * delta;
            if (angle >= maxAngle) {
                dir = RotateDirection.IDLE;
                angle = maxAngle;
            }
        } else if (dir == RotateDirection.RIGHT) {
            angle -= rotationSpeed * delta;
            if (angle <= minAngle) {
                dir = RotateDirection.IDLE;
                angle = minAngle;
            }
        }

        position.x = (float) ((radius * Math.abs(Math.sin(angle)) + radius) * Math.sin(angle)) + rotation.x;
        position.z = (float) ((radius * Math.abs(Math.sin(angle)) + radius) * Math.cos(angle)) + rotation.z;

        // nice bouncing effect when only applying rotation to z-component
        // position.z = (float) ((500 * Math.sin(angleRad) + 300) * Math.cos(angleRad)) + rotation.z;
    }

    /**
     * Overrides the default apply model view matrix, with a Glu.gluLookAt call.
     * This makes it possible to rotate around the position the camera is
     * looking at, without the need of hard trigonometry.
     *
     * @param resetMatrix
     */
    @Override
    public void applyModelViewMatrix(boolean resetMatrix) {
        glMatrixMode(GL_MODELVIEW);
        if (resetMatrix) {
            glLoadIdentity();
        }
        GLU.gluLookAt(position.x, position.y, position.z, rotation.x, rotation.y, rotation.z, 0, 1, 0);
    }

    /**
     * Makes the camera have a GluPerspective projection matrix, that enables
     * the depth vision. By default is the game using an Orthographic projection
     * matrix.
     */
    @Override
    public void applyProjectionMatrix() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(fov, aspectRatio, zNear, zFar);
        glMatrixMode(GL_MODELVIEW);
    }

    //=================================================================
    /*
     * PUBLIC METHODS
     */
    //=================================================================
    public void rotateLeft() {
        this.dir = RotateDirection.LEFT;
    }

    public void rotateRight() {
        this.dir = RotateDirection.RIGHT;
    }

    //=================================================================
    /*
     * GETTERS
     */
    //=================================================================
    public float getAngle() {
        return angle;
    }
}
