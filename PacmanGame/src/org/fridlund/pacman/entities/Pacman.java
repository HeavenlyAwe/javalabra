/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.pacman.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.fridlund.javalabra.game.entities.MovableEntityAbstract;
import org.fridlund.javalabra.game.sprites.Animation;
import org.fridlund.javalabra.game.sprites.SpriteSheet;
import org.fridlund.javalabra.game.utils.TextureLoader;
import org.fridlund.pacman.level.Level;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Vector4f;

/**
 *
 * @author Christoffer
 */
public class Pacman extends MovableEntityAbstract {

    private static String texturePath = "/res/images/pacman.png";
    private Map<String, Animation> animations;
    private Map<String, Animation> eyeAnimations;
    private Animation animation;
    private Animation eyeAnimation;
    private Level level;
    private ArrayList<Integer> allowedTiles;
    private int points;
    private int maxLives;
    private int lives;
    private boolean immortal = false;
    private float immortalTimerMax = 3000;
    private float immortalTimer = 0;
    /*
     * Colors for making pacman yellow (and transparent when immortal).
     */
    private Vector4f color = new Vector4f(1, 1, 0, 1);
    private Vector4f yellow_transparent = new Vector4f(1, 1, 0, 0.2f);
    private Vector4f yellow = new Vector4f(1, 1, 0, 1);

    public Pacman(Level level) {
        super();

        this.level = level;
        this.maxLives = 3;
        this.lives = 3;
        this.points = 0;

        this.setCollisionOffset(5.0f);

        allowedTiles = new ArrayList<>();
        allowedTiles.add(Level.WALKABLE);

        spawn();
    }

    //=================================================================
    /*
     * OVERRIDDEN METHODS
     */
    //=================================================================
    @Override
    public void setup() {

        animations = new HashMap<>();
        eyeAnimations = new HashMap<>();

        SpriteSheet spriteSheet = new SpriteSheet(TextureLoader.loadTextureLinear(getClass().getResourceAsStream(texturePath)), 32, 32, 256, 128);

        setWidth(32);
        setHeight(32);

        Animation rightAnimation = createAnimation(8, 0, 25, spriteSheet);
        Animation leftAnimation = createAnimation(8, 1, 25, spriteSheet);
        Animation downAnimation = createAnimation(8, 2, 25, spriteSheet);
        Animation upAnimation = createAnimation(1, 3, 25, spriteSheet);


        animations.put("right", rightAnimation);
        animations.put("left", leftAnimation);
        animations.put("down", downAnimation);
        animations.put("up", upAnimation);

        animation = rightAnimation;


        createEyeAnimation("right", 1, 2, 3, 25, spriteSheet);
        createEyeAnimation("left", 3, 4, 3, 25, spriteSheet);
        createEyeAnimation("down", 5, 6, 3, 25, spriteSheet);
        createEyeAnimation("up", 7, 7, 3, 25, spriteSheet);

        eyeAnimation = eyeAnimations.get("right");
    }

    @Override
    public void cleanUp() {
        for (String key : animations.keySet()) {
            animations.get(key).cleanUp();
        }
        for (String key : eyeAnimations.keySet()) {
            eyeAnimations.get(key).cleanUp();
        }
    }

    @Override
    public void update(float delta) {

        if (immortal) {
            immortalTimer += delta;
            if (immortalTimer >= immortalTimerMax) {
                immortalTimer = 0;
                immortal = false;
            }

            if (immortalTimer % 300 < 150) {
                color = yellow_transparent;
            } else if (immortalTimer % 300 >= 150) {
                color = yellow;
            }
        } else {
            color = yellow;
        }

        animation.update(delta);
        eyeAnimation.update(delta);
    }

    @Override
    public void render() {
        glColor4f(color.x, color.y, color.z, color.w);
        animation.render(x, y);
        glColor4f(1, 1, 1, 1);
        eyeAnimation.render(x, y);
    }

    @Override
    public void move(float dx, float dy) {
        if (level.walkableTile(this, dx, 0, allowedTiles)) {
            super.move(dx, 0);
        }
        if (level.walkableTile(this, 0, dy, allowedTiles)) {
            super.move(0, dy);
        }
    }

    //=================================================================
    /*
     * PRIVATE METHODS
     */
    //=================================================================
    private Animation createAnimation(int spriteX, int spriteY, int dt, SpriteSheet sheet) {
        Animation anim = new Animation(sheet);

        for (int i = 0; i < spriteX; i++) {
            anim.addFrame(i, spriteY, dt);
        }
        for (int i = spriteX - 1; i > 0; i--) {
            anim.addFrame(i, spriteY, dt);
        }

        return anim;
    }

    private void createEyeAnimation(String key, int spriteX, int spriteAngryX, int spriteY, int dt, SpriteSheet sheet) {
        Animation anim = new Animation(sheet);
        anim.addFrame(spriteX, spriteY, dt);

        Animation angryAnim = new Animation(sheet);
        angryAnim.addFrame(spriteAngryX, spriteY, dt);

        eyeAnimations.put(key, anim);
        eyeAnimations.put(key + "_angry", angryAnim);
    }

    //=================================================================
    /*
     * PUBLIC METHODS
     */
    //=================================================================
    public void spawn() {
        this.spawn(17, 1);
    }

    public void spawn(int tileX, int tileY) {
        setPosition(tileX * level.getTileWidth(), tileY * level.getTileHeight());
    }

    /**
     * Removes one life from Pacman.
     */
    public void kill() {
        lives--;
    }

    /**
     * Adds the desired amount of points to the total score. Most likely to be
     * called from killing ghosts and eating stuff.
     *
     * @param points
     */
    public void addPoints(int points) {
        this.points += points;
    }

    /**
     * Remove the desired amount of points from the score. Most likely to be
     * called when Pacman is being killed.
     *
     * @param points
     */
    public void removePoints(int points) {
        this.points -= points;
    }

    //=================================================================
    /*
     * SETTERS
     */
    //=================================================================
    public void setImmortal() {
        this.immortal = true;
    }
    private boolean angry = false;

    public void setAnimation(String key) {
        animation = animations.get(key);
        if (angry || immortal) {
            eyeAnimation = eyeAnimations.get(key + "_angry");
        } else {
            eyeAnimation = eyeAnimations.get(key);
        }
    }

    public void setAngry(boolean angry) {
        this.angry = angry;
    }

    //=================================================================
    /*
     * GETTERS
     */
    //=================================================================
    public int getMaxLives() {
        return maxLives;
    }

    public int getLives() {
        return lives;
    }

    public int getPoints() {
        return points;
    }

    public boolean isImmortal() {
        return immortal;
    }

    public Animation getAnimation(String key) {
        return animations.get(key);
    }
}
