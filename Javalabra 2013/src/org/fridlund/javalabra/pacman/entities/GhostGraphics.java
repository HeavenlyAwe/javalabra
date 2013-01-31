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

/**
 *
 * @author Christoffer
 */
public class GhostGraphics {

    private static String texturePath = "res/pacman/images/ghost.png";
    public static int spriteWidth = 32;
    public static int spriteHeight = 32;
    private Animation animation;
    private Map<String, Animation> animations;
    private int ghostColorIndex;

    public GhostGraphics(int ghostColorIndex) {
        this.ghostColorIndex = ghostColorIndex;
        createAnimations();
    }

    /**
     * This should have been called from setup, but since that method is
     * overloaded, it is called before the constructor
     */
    private void createAnimations() {

        SpriteSheet sheet = new SpriteSheet(TextureLoader.loadTextureLinear(texturePath), spriteWidth, spriteHeight, 128, 160);
        animations = new HashMap<>();

        Animation upAnimation = new Animation(sheet);
        upAnimation.addFrame(3, ghostColorIndex, 1000);
        animations.put("up", upAnimation);

        Animation downAnimation = new Animation(sheet);
        downAnimation.addFrame(0, ghostColorIndex, 1000);
        animations.put("down", downAnimation);

        Animation leftAnimation = new Animation(sheet);
        leftAnimation.addFrame(1, ghostColorIndex, 1000);
        animations.put("left", leftAnimation);

        Animation rightAnimation = new Animation(sheet);
        rightAnimation.addFrame(2, ghostColorIndex, 1000);
        animations.put("right", rightAnimation);

        Animation killableAnimation = new Animation(sheet);
        killableAnimation.addFrame(0, 4, 100);
        killableAnimation.addFrame(1, 4, 100);
        animations.put("killable", killableAnimation);

        Animation warningAnimation = new Animation(sheet);
        warningAnimation.addFrame(2, 4, 10);
        warningAnimation.addFrame(0, 4, 10);
        animations.put("warning", warningAnimation);

        Animation killed = new Animation(sheet);
        killed.addFrame(3, 4, 1000);
        animations.put("killed", killed);
    }

    public void cleanUp() {
        animation.cleanUp();
    }

    public void setAnimation(String key) {
        animation = animations.get(key);
    }

    public void update(float delta) {
        animation.update(delta);
    }

    public void render(float x, float y) {
        animation.render(x, y);
    }

    public int getGhostColorIndex() {
        return ghostColorIndex;
    }
}
