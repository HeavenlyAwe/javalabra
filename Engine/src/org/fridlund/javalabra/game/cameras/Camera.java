/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.cameras;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Vector3f;

/**
 * Abstract Camera class containing the methods to most likely be used when
 * using a Camera. Has a position and a rotation (both in 3D).
 *
 * @author Christoffer
 */
public abstract class Camera {

    /**
     *
     */
    protected Vector3f position;
    /**
     *
     */
    protected Vector3f rotation;

    /**
     *
     */
    public Camera() {
        this(new Vector3f(0, 0, 0));
    }

    /**
     *
     * @param position
     */
    public Camera(Vector3f position) {
        this(position, new Vector3f(0, 0, 0));
    }

    /**
     *
     * @param position
     * @param rotation
     */
    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    //=================================================================
    /*
     * OVERRIDDEN METHODS
     */
    //=================================================================
    @Override
    public String toString() {
        return "x: " + position.x + "y: " + position.y + "z: " + position.z + "pitch: " + rotation.x + "yaw: " + rotation.y + "roll: " + rotation.z;
    }
    //=================================================================
    /*
     * ABSTRACT METHODS
     */
    //=================================================================

    /**
     *
     */
    public abstract void applyProjectionMatrix();

    //=================================================================
    /*
     * PUBLIC METHODS
     */
    //=================================================================
    /**
     *
     * @param delta
     */
    public void update(float delta) {
    }

    /**
     * Translates the position of the objects and rotates them according to the
     * position and rotation vectors.
     *
     * @param resetMatrix
     */
    public void applyModelViewMatrix(boolean resetMatrix) {
        if (resetMatrix) {
            glLoadIdentity();
        }

        glRotatef(rotation.x, 1, 0, 0);
        glRotatef(rotation.y, 0, 1, 0);
        glRotatef(rotation.z, 0, 0, 1);
        glTranslatef(-position.x, -position.y, -position.z);
    }

    //=================================================================
    /*
     * SETTERS
     */
    //=================================================================
    /**
     *
     * @param x
     * @param y
     * @param z
     */
    public void setPosition(float x, float y, float z) {
        this.position = new Vector3f(x, y, z);
    }

    /**
     *
     * @param position
     */
    public void setPosition(Vector3f position) {
        this.position = position;
    }

    /**
     *
     * @param pitch
     * @param yaw
     * @param roll
     */
    public void setRotation(float pitch, float yaw, float roll) {
        this.rotation = new Vector3f(pitch, yaw, roll);
    }

    /**
     *
     * @param rotation
     */
    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }
}
