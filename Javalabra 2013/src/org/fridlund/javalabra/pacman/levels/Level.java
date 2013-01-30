/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.levels;

import java.util.ArrayList;
import java.util.logging.Logger;
import org.fridlund.javalabra.game.entities.Entity;
import org.fridlund.javalabra.game.sprites.Sprite;
import org.fridlund.javalabra.game.sprites.SpriteSheet;
import org.fridlund.javalabra.game.utils.TextureLoader;
import org.fridlund.javalabra.pacman.scenes.GameplayScene;
import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Christoffer
 */
public class Level {

    private String texturePath = "res/pacman/images/level_tile_set.png";
    private String levelPath = "res/pacman/levels/level1.png";
    private int[][] tiles;
    private SpriteSheet sheet;
    private static int tileWidth = 16;
    private static int tileHeight = 16;
    private float levelWidth;
    private float levelHeight;
    // makes a little offset to the sourrounding walls, to not make the entity
    // get stuck when moving in the tunnels
    private float entityOffsetToWalls = 1.0f;
    // render variables
    private Sprite sprite;
    private float scale = 1.0f;
    public final static int VOID = 0;
    public final static int WALL = 1;
    public final static int WALKABLE = 2;
    public final static int GHOST_TILE = 3;

    public Level() {
        setup();
    }

    private void setup() {
        sheet = new SpriteSheet(TextureLoader.loadTextureLinear(texturePath), tileWidth, tileHeight, 128, 128);
//        generateLevel(sheet);
        generateLevelFromImage();
        printLevelInAscii();
    }

    private void generateLevelFromImage() {
        try {
            Image image = new Image(levelPath);

            int levelSizeX = image.getWidth();
            int levelSizeY = image.getHeight();

            levelWidth = levelSizeX * tileWidth;
            levelHeight = levelSizeY * tileHeight;

            tiles = new int[levelSizeX][levelSizeY];
            for (int y = 0; y < levelSizeY; y++) {

                int tileY = levelSizeY - y - 1;

                for (int x = 0; x < levelSizeX; x++) {

                    int tileX = x;

                    // VOID tile
                    if (image.getColor(x, y).getRed() == 255
                            && image.getColor(x, y).getGreen() == 255
                            && image.getColor(x, y).getBlue() == 255
                            && image.getColor(x, y).getAlpha() == 0) {
                        tiles[tileX][tileY] = VOID;
                    } // WALL tile
                    else if (image.getColor(x, y).getRed() == 0
                            && image.getColor(x, y).getGreen() == 0
                            && image.getColor(x, y).getBlue() == 0
                            && image.getColor(x, y).getAlpha() == 255) {
                        tiles[tileX][tileY] = WALL;
                    } // WALKABLE tile
                    else if (image.getColor(x, y).getRed() == 255
                            && image.getColor(x, y).getGreen() == 255
                            && image.getColor(x, y).getBlue() == 255
                            && image.getColor(x, y).getAlpha() == 255) {
                        tiles[tileX][tileY] = WALKABLE;
                    } // GHOST tile
                    else if (image.getColor(x, y).getRed() == 255
                            && image.getColor(x, y).getGreen() == 0
                            && image.getColor(x, y).getBlue() == 255
                            && image.getColor(x, y).getAlpha() == 255) {
                        tiles[tileX][tileY] = GHOST_TILE;
                    } else {
                        tiles[tileX][tileY] = VOID;
                    }
                }
            }

            image.destroy();
        } catch (SlickException ex) {
            Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    private void printLevelInAscii() {
        for (int y = 0; y < tiles[0].length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                System.out.print(tiles[x][y]);
            }
            System.out.println("");
        }
    }

    public void update(float delta) {
        for (int y = 0; y < tiles[0].length; y++) {
            for (int x = 0; x < tiles.length; x++) {
            }
        }
    }

    public void render() {
        for (int y = 0; y < tiles[0].length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                renderTile(x * tileWidth, y * tileHeight, tiles[x][y]);
            }
        }
    }

    private void renderTile(float x, float y, int tile) {
        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, sheet.getTextureID());

        int tileX = 0;
        int tileY = 0;

        // chooses correct tile from the quadratic grid of tiles in the image, based on a unique number
        switch (tile) {
            case VOID:
                tileX = 0;
                break;
            case WALL:
                tileX = 1;
                break;
            case WALKABLE:
                tileX = 2;
                break;
            case GHOST_TILE:
                tileX = 3;
                break;
            default:
                tileX = 0;
                break;
        }

        sprite = sheet.getSprite(tileX, tileY);

        float sx = sprite.getX();
        float sy = sprite.getY();
        float sx1 = sx + sprite.getWidth();
        float sy1 = sy + sprite.getHeight();

        glBegin(GL_QUADS);
        {
            glTexCoord2f(sx, sy);
            glVertex2f(x, y);
            glTexCoord2f(sx, sy1);
            glVertex2f(x, y + sprite.getHeight() * scale);
            glTexCoord2f(sx1, sy1);
            glVertex2f(x + sprite.getWidth() * scale, y + sprite.getHeight() * scale);
            glTexCoord2f(sx1, sy);
            glVertex2f(x + sprite.getWidth() * scale, y);
        }
        glEnd();

        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, 0);
    }

    public boolean walkableTile(Entity entity, float dx, float dy, ArrayList<Integer> allowedTiles) {
        int x0 = (int) (entity.getX() + dx + entityOffsetToWalls) / tileWidth;
        int x1 = (int) (entity.getX() + entity.getWidth() + dx - entityOffsetToWalls) / tileWidth;
        int y0 = (int) (entity.getY() + dy + entityOffsetToWalls) / tileHeight;
        int y1 = (int) (entity.getY() + entity.getHeight() + dy - entityOffsetToWalls) / tileHeight;
        int xCenter = (int) (entity.getX() + entity.getWidth() / 2 + dx) / tileWidth;
        int yCenter = (int) (entity.getY() + entity.getHeight() / 2 + dy) / tileHeight;

        if (x0 < 0 || y0 < 0 || x1 >= tiles.length || y1 >= tiles[0].length) {
            return true;
        }

        return allowedTiles.contains(tiles[x0][y0])
                && allowedTiles.contains(tiles[x0][y1])
                && allowedTiles.contains(tiles[x1][y0])
                && allowedTiles.contains(tiles[x1][y1])
                && allowedTiles.contains(tiles[xCenter][y0])
                && allowedTiles.contains(tiles[xCenter][y1])
                && allowedTiles.contains(tiles[x0][yCenter])
                && allowedTiles.contains(tiles[x1][yCenter]);
    }

    public boolean outsideOnTheRight(Entity entity) {
        if (entity.getX() > levelWidth) {
            return true;
        }
        return false;
    }

    public boolean outsideOnTheLeft(Entity entity) {
        if (entity.getX() < -entity.getWidth()) {
            return true;
        }
        return false;
    }

    public float getWidth() {
        return levelWidth;
    }

    public float getHeight() {
        return levelHeight;
    }

    public float getTileWidth() {
        return tileWidth;
    }

    public float getTileHeight() {
        return tileHeight;
    }
}
