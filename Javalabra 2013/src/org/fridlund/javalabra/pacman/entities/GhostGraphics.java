/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.fridlund.javalabra.game.sprites.Animation;
import org.fridlund.javalabra.game.sprites.SpriteSheet;
import org.fridlund.javalabra.game.utils.TextureLoader;
import org.fridlund.javalabra.pacman.utils.GhostColors;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Vector4f;

/**
 *
 * @author Christoffer
 */
public class GhostGraphics {

    private static String texturePath = "res/pacman/images/ghost_silhuette.png";
    public static int spriteWidth = 32;
    public static int spriteHeight = 32;
    private Animation eyeAnimation;
    private Map<String, Animation> eyeAnimations;
    private Animation bodyAnimation;
    private int ghostColorIndex;
    /*
     * Color is chosen by ghostColorIndex
     */
    private Vector4f color;

    public GhostGraphics(int ghostColorIndex) {
        this.ghostColorIndex = ghostColorIndex;
        setRegularColor();
        createAnimations();
    }

    /**
     * This should have been called from setup, but since that method is
     * overloaded, it is called before the constructor
     */
    private void createAnimations() {

        SpriteSheet sheet = new SpriteSheet(TextureLoader.loadTextureLinear(texturePath), spriteWidth, spriteHeight, 256, 64);
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

    public void cleanUp() {
        for (String key : eyeAnimations.keySet()) {
            eyeAnimations.get(key).cleanUp();
        }
        bodyAnimation.cleanUp();
    }

    public void setColor(Vector4f color) {
        this.color = color;
    }

    public void setRegularColor() {
        this.color = GhostColors.getColor(ghostColorIndex);
    }

    public void setKillableColor() {
        this.color = new Vector4f(0, 0, 0.5f, 1);
    }

    public void setAnimation(String key) {
        eyeAnimation = eyeAnimations.get(key);
    }

    public void update(float delta) {
        bodyAnimation.update(delta);
        eyeAnimation.update(delta);
    }

    public void render(float x, float y) {
        glColor4f(color.x, color.y, color.z, color.w);
        bodyAnimation.render(x, y);
        glColor4f(1, 1, 1, 1);
        eyeAnimation.render(x, y);
    }

    public int getGhostColorIndex() {
        return ghostColorIndex;
    }
}
