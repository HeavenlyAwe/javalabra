/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.backman.entities;

import java.util.HashMap;
import java.util.Map;
import org.fridlund.javalabra.game.sprites.Animation;
import org.fridlund.javalabra.game.sprites.SpriteSheet;
import org.fridlund.javalabra.game.utils.TextureLoader;
import org.fridlund.backman.utils.GhostColors;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Vector4f;

/**
 * Contains the animation for the Ghost entity. This way you can assign the same
 * graphics to several different Ghost AI.
 *
 * @author Christoffer
 */
public class GhostGraphics {

    private static String texturePath = "/res/images/ghost_silhuette.png";
    /**
     *
     */
    public static int spriteWidth = 32;
    /**
     *
     */
    public static int spriteHeight = 32;
    private Map<String, Animation> eyeAnimations;       // Map containing all eye animations.
    private Animation bodyAnimation;                    // Current body animation
    private Animation eyeAnimation;                     // Current eye animation
    private int ghostColorIndex;
    /*
     * Color is chosen by ghostColorIndex
     */
    private Vector4f color;

    /**
     *
     * @param ghostColorIndex
     */
    public GhostGraphics(int ghostColorIndex) {
        this.ghostColorIndex = ghostColorIndex;
        setRegularColor();
        createAnimations();
    }

    //=================================================================
    /*
     * PRIVATE METHODS
     */
    //=================================================================
    /**
     * This method creates all the body and eye animations used from the ghost
     * AI. This also creates a death animation.
     */
    private void createAnimations() {

        SpriteSheet sheet = new SpriteSheet(TextureLoader.loadTextureLinear(getClass().getResourceAsStream(texturePath)), spriteWidth, spriteHeight, 256, 64);
        eyeAnimations = new HashMap<>();

        Animation downAnimation = new Animation(sheet);
        downAnimation.addFrame(0, 1, 25);

        Animation leftAnimation = new Animation(sheet);
        leftAnimation.addFrame(1, 1, 25);

        Animation rightAnimation = new Animation(sheet);
        rightAnimation.addFrame(2, 1, 25);

        Animation upAnimation = new Animation(sheet);
        upAnimation.addFrame(3, 1, 1000);

        eyeAnimations.put("down", downAnimation);
        eyeAnimations.put("left", leftAnimation);
        eyeAnimations.put("right", rightAnimation);
        eyeAnimations.put("up", upAnimation);

        Animation killableAnimation = new Animation(sheet);
        killableAnimation.addFrame(4, 1, 250);
        killableAnimation.addFrame(5, 1, 250);
        eyeAnimations.put("killable", killableAnimation);

        Animation killed = new Animation(sheet);
        killed.addFrame(6, 1, 1000);
        killed.addFrame(7, 1, 1000);
        eyeAnimations.put("killed", killed);


        bodyAnimation = new Animation(sheet);
        for (int i = 0; i < 4; i++) {
            bodyAnimation.addFrame(i, 0, 250);
        }
    }

    //=================================================================
    /*
     * PUBLIC METHODS
     */
    //=================================================================
    /**
     *
     */
    public void cleanUp() {
        for (String key : eyeAnimations.keySet()) {
            eyeAnimations.get(key).cleanUp();
        }
        bodyAnimation.cleanUp();
    }

    /**
     *
     * @param delta
     */
    public void update(float delta) {
        bodyAnimation.update(delta);
        eyeAnimation.update(delta);
    }

    /**
     *
     * @param x
     * @param y
     */
    public void render(float x, float y) {
        glColor4f(color.x, color.y, color.z, color.w);
        bodyAnimation.render(x, y);
        glColor4f(1, 1, 1, 1);
        eyeAnimation.render(x, y);
    }
    //=================================================================
    /*
     * SETTERS
     */
    //=================================================================

    /**
     *
     * @param color
     */
    public void setColor(Vector4f color) {
        this.color = color;
    }

    /**
     *
     */
    public void setRegularColor() {
        this.color = GhostColors.getColor(ghostColorIndex);
    }

    /**
     *
     */
    public void setKillableColor() {
        this.color = new Vector4f(0, 0, 0.5f, 1);
    }

    /**
     *
     * @param key
     */
    public void setAnimation(String key) {
        eyeAnimation = eyeAnimations.get(key);
    }

    //=================================================================
    /*
     * GETTERS
     */
    //=================================================================
    /**
     *
     * @return
     */
    public int getGhostColorIndex() {
        return ghostColorIndex;
    }
}
