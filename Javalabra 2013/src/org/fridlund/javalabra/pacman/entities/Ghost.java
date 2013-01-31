/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.fridlund.javalabra.game.entities.MovableEntityAbstract;
import org.fridlund.javalabra.game.sprites.Animation;
import org.fridlund.javalabra.game.sprites.SpriteSheet;
import org.fridlund.javalabra.game.utils.TextureLoader;
import org.fridlund.javalabra.pacman.levels.Level;

/**
 *
 * @author Christoffer
 */
public class Ghost extends MovableEntityAbstract {

    private enum Direction {

        UP, DOWN, LEFT, RIGHT;
    }
    private static String texturePath = "res/pacman/images/ghost.png";
    private static int spriteWidth = 32;
    private static int spriteHeight = 32;
    private Level level;
    private Animation animation;
    private Map<String, Animation> animations;
    private int ghostColorIndex;
    protected ArrayList<Integer> allowedTiles;
    private boolean isInvincible;
    private boolean isWarning;
    private boolean dead;

    public Ghost(Level level, int ghostColorIndex) {
        this.level = level;
        this.ghostColorIndex = ghostColorIndex;
        this.setCollisionOffset(5.0f);
        this.isWarning = false;
        this.isInvincible = true;
        this.dead = false;
        spawn();
    }

    private void spawn() {

        // spawning the ghosts inside the "ghost area" offseted by two tiles to the right for each color.
        float x = 14 * level.getTileWidth() + ghostColorIndex * 2 * level.getTileWidth();
        float y = 16 * level.getTileHeight();
        setPosition(x, y);

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
        warningAnimation.addFrame(3, 4, 10);
        warningAnimation.addFrame(1, 4, 10);
        animations.put("warning", warningAnimation);
    }

    @Override
    public void setup() {

        setWidth(spriteWidth);
        setHeight(spriteHeight);

        this.allowedTiles = new ArrayList<>();
        this.allowedTiles.add(Level.GHOST_TILE);
        this.allowedTiles.add(Level.WALKABLE);
    }

    @Override
    public void cleanUp() {
        animation.cleanUp();
    }

    public void setAnimation(String key) {
        animation = animations.get(key);
    }

    @Override
    public void update(float delta) {
        animation.update(delta);
    }

    public void setKillable() {
        this.isInvincible = false;
    }

    public void setWarningAnimation() {
        if (!isInvincible) {
            this.isWarning = true;
        }
    }

    public boolean isWarning() {
        return isWarning;
    }

    public void setInvincible() {
        this.isInvincible = true;
        this.isWarning = false;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    @Override
    public void render() {
        animation.render(x, y);
    }

    public boolean isDead() {
        return dead;
    }

    public void kill() {
        this.dead = true;
    }
}
