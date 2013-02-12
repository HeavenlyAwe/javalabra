/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.pacman.entities;

import org.fridlund.javalabra.game.entities.EntityAbstract;
import org.fridlund.javalabra.game.entities.MovableEntityAbstract;
import org.fridlund.javalabra.game.sprites.Animation;
import org.fridlund.javalabra.game.sprites.SpriteSheet;
import org.fridlund.javalabra.game.utils.TextureLoader;

/**
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
        this(new SpecialAction() {
            @Override
            public void execute() {
            }
        });
    }

    public Snack(SpecialAction specialAction) {
        this.setCollisionOffset(5.0f);
        this.specialAction = specialAction;
    }

    public void setupAnimation() {
        SpriteSheet sheet = new SpriteSheet(TextureLoader.loadTextureLinear(getClass().getResourceAsStream(texturePath)), width, width, 16, 16);
        animation = new Animation(sheet);
    }

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

    public void executeAction() {
        specialAction.execute();
    }
}
