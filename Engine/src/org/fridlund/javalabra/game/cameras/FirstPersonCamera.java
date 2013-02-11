/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.cameras;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

/**
 * This is only an example on how to make a camera.
 *
 * @author Christoffer
 */
public class FirstPersonCamera extends Camera {

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

    public FirstPersonCamera(float aspectRatio) {
        this(aspectRatio, new Vector3f(0, 0, 0));
    }

    public FirstPersonCamera(float aspectRatio, Vector3f position) {
        this(aspectRatio, position, new Vector3f(0, 0, 0));
    }

    public FirstPersonCamera(float aspectRatio, Vector3f position, Vector3f rotation) {
        super(position, rotation);

        this.aspectRatio = aspectRatio;
    }

    @Override
    public void applyProjectionMatrix() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(fov, aspectRatio, zNear, zFar);
        glMatrixMode(GL_MODELVIEW);
    }

    @Override
    public void update(float delta) {
        handleMouseInput(delta);
        handleKeyboardInput(delta);
    }

    public void handleMouseInput(float delta) {
        if (!Mouse.isGrabbed()) {
            return;
        }

        float mouseDX = Mouse.getDX() * mouseSpeed * delta;
        float mouseDY = Mouse.getDY() * mouseSpeed * delta;

        float yaw = rotation.y;
        float pitch = rotation.x;
        float roll = rotation.z;

        if (yaw + mouseDX >= 360) {
            yaw = yaw + mouseDX - 360;
        } else if (yaw + mouseDX < 0) {
            yaw = 360 - yaw + mouseDX;
        } else {
            yaw += mouseDX;
        }

        if (pitch - mouseDY >= maxLookDown && pitch - mouseDY <= maxLookUp) {
            pitch += -mouseDY;
        } else if (pitch - mouseDY < maxLookDown) {
            pitch = maxLookDown;
        } else if (pitch - mouseDY > maxLookUp) {
            pitch = maxLookUp;
        }

        rotation = new Vector3f(pitch, yaw, roll);
    }

    public void handleKeyboardInput(float delta) {
        boolean keyUp = Keyboard.isKeyDown(Keyboard.KEY_UP);
        boolean keyDown = Keyboard.isKeyDown(Keyboard.KEY_DOWN);
        boolean keyLeft = Keyboard.isKeyDown(Keyboard.KEY_LEFT);
        boolean keyRight = Keyboard.isKeyDown(Keyboard.KEY_RIGHT);
        boolean flyUp = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
        boolean flyDown = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);


        if (keyUp && keyRight && !keyLeft && !keyDown) {
            moveFromLook(speedX * delta, 0, -speedZ * delta);
        }
        if (keyUp && keyLeft && !keyRight && !keyDown) {
            moveFromLook(-speedX * delta, 0, -speedZ * delta);
        }
        if (keyUp && !keyLeft && !keyRight && !keyDown) {
            moveFromLook(0, 0, -speedZ * delta);
        }
        if (keyDown && keyLeft && !keyRight && !keyUp) {
            moveFromLook(-speedX * delta, 0, speedZ * delta);
        }
        if (keyDown && keyRight && !keyLeft && !keyUp) {
            moveFromLook(speedX * delta, 0, speedZ * delta);
        }
        if (keyDown && !keyUp && !keyLeft && !keyRight) {
            moveFromLook(0, 0, speedZ * delta);
        }
        if (keyLeft && !keyRight && !keyUp && !keyDown) {
            moveFromLook(-speedX * delta, 0, 0);
        }
        if (keyRight && !keyLeft && !keyUp && !keyDown) {
            moveFromLook(speedX * delta, 0, 0);
        }

        float dy = 0;
        if (flyUp && !flyDown) {
            position.y += speedY * delta;
        }
        if (flyDown && !flyUp) {
            position.y -= speedY * delta;
        }
    }

    public void moveFromLook(float dx, float dy, float dz) {
        float nX = this.position.x;
        float nY = this.position.y;
        float nZ = this.position.z;

        float hypotenuseX = dx;
        float adjacentX = hypotenuseX
                * (float) Math.cos(Math.toRadians(rotation.y - 90));
        float oppositeX = (float) Math.sin(Math.toRadians(rotation.y - 90))
                * hypotenuseX;
        nZ += adjacentX;
        nX -= oppositeX;

        nY += dy;

        float hypotenuseZ = dz;
        float adjacentZ = hypotenuseZ * (float) Math.cos(Math.toRadians(rotation.y));
        float oppositeZ = (float) Math.sin(Math.toRadians(rotation.y)) * hypotenuseZ;
        nZ += adjacentZ;
        nX -= oppositeZ;

        this.position = new Vector3f(nX, nY, nZ);
    }
}
