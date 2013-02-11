/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.pacman.level;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.fridlund.javalabra.game.entities.Entity;
import org.fridlund.javalabra.game.sprites.Sprite;
import org.fridlund.javalabra.game.sprites.SpriteSheet;
import org.fridlund.javalabra.game.utils.TextureLoader;
import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.*;

/**
 * The level and it's loading, update and render method.
 *
 * @author Christoffer
 */
public class Level {

    private String texturePath = "/res/levels/level_tile_set.png";
    private String levelPath = "/res/levels/level1.png";
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
    /*
     * Tile indices, used when assigning allowed tiles to the different entities used in the game.
     */
    public final static int VOID = 0;
    public final static int WALL = 1;
    public final static int WALKABLE = 2;
    public final static int GHOST_TILE = 3;

    public Level() {
        setup();
    }

    //=================================================================
    /*
     * PRIVATE METHODS
     */
    //=================================================================
    /**
     * Reads the sprite sheet containing all level sprites and generates the
     * level from a image file.
     */
    private void setup() {
        sheet = new SpriteSheet(TextureLoader.loadTextureLinear(getClass().getResourceAsStream(texturePath)), tileWidth, tileHeight, 128, 128);
        generateLevelFromImage();
//        printLevelInAscii();
    }

    /**
     * Generates the level from an image file. Based on the colors used in the
     * image, different tiles are chosen from the sprite sheet. The different
     * tiles are chosen from the static indices in this class.
     */
    private void generateLevelFromImage() {
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream(levelPath));

            int levelSizeX = image.getWidth();
            int levelSizeY = image.getHeight();

            levelWidth = levelSizeX * tileWidth;
            levelHeight = levelSizeY * tileHeight;

            tiles = new int[levelSizeX][levelSizeY];
            for (int y = 0; y < levelSizeY; y++) {

                int tileY = levelSizeY - y - 1;

                for (int x = 0; x < levelSizeX; x++) {

                    int tileX = x;

                    Color color = new Color(image.getRGB(x, y));
                    // VOID tile
                    if (color.getRed() == 255
                            && color.getGreen() == 255
                            && color.getBlue() == 255
                            && color.getAlpha() == 0) {
                        tiles[tileX][tileY] = VOID;
                    } // WALL tile
                    else if (color.getRed() == 0
                            && color.getGreen() == 0
                            && color.getBlue() == 0
                            && color.getAlpha() == 255) {
                        tiles[tileX][tileY] = WALL;
                    } // WALKABLE tile
                    else if (color.getRed() == 255
                            && color.getGreen() == 255
                            && color.getBlue() == 255
                            && color.getAlpha() == 255) {
                        tiles[tileX][tileY] = WALKABLE;
                    } // GHOST tile
                    else if (color.getRed() == 255
                            && color.getGreen() == 0
                            && color.getBlue() == 255
                            && color.getAlpha() == 255) {
                        tiles[tileX][tileY] = GHOST_TILE;
                    } else {
                        tiles[tileX][tileY] = VOID;
                    }
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    /**
     * Helper method to print the loaded level in ASCII characters.
     */
    private void printLevelInAscii() {
        for (int y = 0; y < tiles[0].length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                System.out.print(tiles[x][y]);
            }
            System.out.println("");
        }
    }

    /**
     * Helper method to render the different tiles, based on the value they have
     * been assigned in the loadLevel method. The different tiles are then
     * rendered by accessing parts of the sprite sheet and drawing the specified
     * part.
     *
     * @param x
     * @param y
     * @param tile
     */
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

    //=================================================================
    /*
     * PUBLIC METHODS
     */
    //=================================================================
    /**
     * Updates the tiles.
     *
     * Currently no animated tiles, so this method is not really needed.
     *
     * @param delta
     */
    public void update(float delta) {
//        for (int y = 0; y < tiles[0].length; y++) {
//            for (int x = 0; x < tiles.length; x++) {
//            }
//        }
    }

    /**
     * Renders all tiles in their position. Calculates the tile position based
     * on the tile width and tile height.
     */
    public void render() {
        for (int y = 0; y < tiles[0].length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                renderTile(x * tileWidth, y * tileHeight, tiles[x][y]);
            }
        }
    }

    /**
     * Checks if the entity is trying to access a legible tile, by checking
     * against the entity's allowed tiles list. Ghosts can move on the ghost
     * tiles in the center of the screen, but Pacman can't.
     *
     * @param entity
     * @param dx
     * @param dy
     * @param allowedTiles
     * @return
     */
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

    /**
     * Checks if entity has left the world on the Right. Used when Pacman is
     * teleporting from one side to the next.
     *
     * @param entity
     * @return
     */
    public boolean outsideOnTheRight(Entity entity) {
        if (entity.getX() > levelWidth) {
            return true;
        }
        return false;
    }

    /**
     * Checks if entity has left the world on the Left. Used when Pacman is
     * teleporting from one side to the next.
     *
     * @param entity
     * @return
     */
    public boolean outsideOnTheLeft(Entity entity) {
        if (entity.getX() < -entity.getWidth()) {
            return true;
        }
        return false;
    }

    //=================================================================
    /*
     * GETTERS
     */
    //=================================================================
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
