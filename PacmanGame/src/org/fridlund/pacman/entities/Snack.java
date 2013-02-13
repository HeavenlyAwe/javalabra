/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.pacman.entities;

import org.fridlund.javalabra.game.entities.MovableEntityAbstract;
import org.fridlund.javalabra.game.sprites.Animation;
import org.fridlund.javalabra.game.sprites.SpriteSheet;
import org.fridlund.javalabra.game.utils.TextureLoader;

/**
 * The yellow snacks that Pacman eats.
 *
 * @author Christoffer
 */
public class Snack extends MovableEntityAbstract {

    protected static String texturePath = "/res/images/snack.png";
    protected static int width = 16;
    protected static int height = 16;
    protected Animation animation;
    private SpecialAction specialAction;

    public Snack() {
        this(null);
    }

    public Snack(SpecialAction specialAction) {
        this.setCollisionOffset(5.0f);
        this.specialAction = specialAction;
    }

    //=================================================================
    /*
     * OVERRIDDEN METHODS
     */
    //=================================================================
    @Override
    public void setup() {
        setWidth(width);
        setHeight(height);
        setupAnimation();
    }

    @Override
    public void cleanUp() {
        animation.cleanUp();
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render() {
        animation.render(x, y);
    }

    //=================================================================
    /*
     * PUBLIC METHODS
     */
    //=================================================================
    public void setupAnimation() {
        SpriteSheet sheet = new SpriteSheet(TextureLoader.loadTextureLinear(getClass().getResourceAsStream(texturePath)), width, width, 16, 16);
        animation = new Animation(sheet);
    }

    public void executeAction() {
        if(specialAction == null){
            return;
        }
        specialAction.execute();
    }
}
