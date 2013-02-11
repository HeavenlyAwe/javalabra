/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.sprites;

/**
 *
 * @author Christoffer
 */
public class SpriteSheet {

    private int textureID;
    private int spriteWidth;
    private int spriteHeight;
    private int imageWidth;
    private int imageHeight;
    private Sprite[][] sprites;

    public SpriteSheet(int textureID, int spriteWidth, int spriteHeight, int imageWidth, int imageHeight) {
        this.textureID = textureID;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;

        setup();
    }

    public void setup() {
        int spritesX = imageWidth / spriteWidth;
        int spritesY = imageHeight / spriteHeight;

        sprites = new Sprite[spritesX][spritesY];

        float tx;
        float ty;
        for (int x = 0; x < spritesX; x++) {
            for (int y = 0; y < spritesY; y++) {
                tx = x * spriteWidth;
                ty = y * spriteHeight;
                sprites[x][y] = new Sprite(tx, ty, spriteWidth, spriteHeight);
            }
        }
    }

    public Sprite getSprite(int x, int y) {
        if (x < 0 || x >= sprites.length || y < 0 || y >= sprites[0].length) {
            throw new IndexOutOfBoundsException("Can't get sprite outside Sprite Sheet!");
        }
        return sprites[x][y];
    }

    public int getTextureID() {
        return textureID;
    }
}
