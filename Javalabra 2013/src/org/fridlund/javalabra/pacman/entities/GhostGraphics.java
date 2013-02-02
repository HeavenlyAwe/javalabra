/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.entities;

import java.util.HashMap;
import java.util.Map;
import org.fridlund.javalabra.game.sprites.Animation;
import org.fridlund.javalabra.game.sprites.SpriteSheet;
import org.fridlund.javalabra.game.utils.TextureLoader;
import org.lwjgl.util.vector.Vector4f;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Christoffer
 */
public class GhostGraphics {

    private static String texturePath = "res/pacman/images/ghost_silhuette.png";
    public static int spriteWidth = 32;
    public static int spriteHeight = 32;
    private Animation animation;
    private Map<String, Animation> animations;
    private Animation bodyAnimation;
    private int ghostColorIndex;
    private boolean warning;
    private boolean dead;
    /*
     * Color is chosen by ghostColorIndex
     */
    private Vector4f red = new Vector4f(1, 0, 0, 1);
    private Vector4f blue = new Vector4f(0, 1, 1, 1);
    private Vector4f pink = new Vector4f(1, 0.5f, 0.5f, 1);
    private Vector4f orange = new Vector4f(1, 0.5f, 0, 1);
    private Vector4f color;
    private Vector4f renderColor;

    public GhostGraphics(int ghostColorIndex) {
        this.ghostColorIndex = ghostColorIndex;
        this.warning = false;
        this.dead = false;

        switch (ghostColorIndex) {
            case 0:
                this.color = red;
                break;
            case 1:
                this.color = blue;
                break;
            case 2:
                this.color = pink;
                break;
            case 3:
                this.color = orange;
                break;
        }

        renderColor = color;
        createAnimations();
    }

    /**
     * This should have been called from setup, but since that method is
     * overloaded, it is called before the constructor
     */
    private void createAnimations() {

        SpriteSheet sheet = new SpriteSheet(TextureLoader.loadTextureLinear(texturePath), spriteWidth, spriteHeight, 256, 64);
        animations = new HashMap<>();

        Animation downAnimation = new Animation(sheet);
        downAnimation.addFrame(0, 1, 25);

        Animation leftAnimation = new Animation(sheet);
        leftAnimation.addFrame(1, 1, 25);

        Animation rightAnimation = new Animation(sheet);
        rightAnimation.addFrame(2, 1, 25);

        Animation upAnimation = new Animation(sheet);
        upAnimation.addFrame(3, 1, 1000);

        animations.put("down", downAnimation);
        animations.put("left", leftAnimation);
        animations.put("right", rightAnimation);
        animations.put("up", upAnimation);

        Animation killableAnimation = new Animation(sheet);
        killableAnimation.addFrame(4, 1, 100);
        killableAnimation.addFrame(5, 1, 100);
        animations.put("killable", killableAnimation);

        Animation killed = new Animation(sheet);
        killed.addFrame(6, 1, 1000);
        killed.addFrame(7, 1, 1000);
        animations.put("killed", killed);


        bodyAnimation = new Animation(sheet);
        for (int i = 0; i < 4; i++) {
            bodyAnimation.addFrame(i, 0, 25);
        }
    }

    public void cleanUp() {
        for (String key : animations.keySet()) {
            animations.get(key).cleanUp();
        }
        bodyAnimation.cleanUp();
    }

    public void setWarning(boolean warning) {
        this.warning = warning;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setAnimation(String key) {
        animation = animations.get(key);
    }
    private float timer = 0;

    public void update(float delta) {
        renderColor = color;
        if (warning) {
            timer += delta;
            if (timer % 300 >= 150) {
                renderColor.w = 0.5f;
            }
        } else {
            renderColor = color;
        }
        bodyAnimation.update(delta);
        animation.update(delta);
    }

    public void render(float x, float y) {
        if (!dead) {
            glColor4f(renderColor.x, renderColor.y, renderColor.z, renderColor.w);
            bodyAnimation.render(x, y);
            glColor4f(1, 1, 1, 1);
        }
        animation.render(x, y);
    }

    public int getGhostColorIndex() {
        return ghostColorIndex;
    }
}
