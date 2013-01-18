/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.levels;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import org.fridlund.javalabra.game.sprites.Sprite;
import org.fridlund.javalabra.game.sprites.SpriteSheet;
import org.fridlund.javalabra.game.utils.TextureLoader;
import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Christoffer
 */
public class Level {

    private String texturePath = "res/pacman/images/level_tile_set.png";
    private String levelPath = "res/pacman/levels/level1.txt";
    private int[][] tiles;
    private SpriteSheet sheet;

    public Level() {
        setup();
    }

    private void setup() {
        sheet = new SpriteSheet(TextureLoader.loadTextureLinear(texturePath), 32, 32, 256, 256);
        generateLevel(sheet);
    }

    private void generateLevel(SpriteSheet sheet) {
        try {
            Scanner scanner = new Scanner(new File(levelPath));

            List<String> lines = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line);
            }

            int levelSizeX = lines.get(0).length();
            int levelSizeY = lines.size();

            tiles = new int[levelSizeX][levelSizeY];
            for (int y = 0; y < levelSizeY; y++) {
                String line = lines.get(y);
                for (int x = 0; x < levelSizeX; x++) {
                    if (line.charAt(x) == 'X') {
                        tiles[x][y] = 0;
                    } else if (line.charAt(x) == '.') {
                        tiles[x][y] = 1;
                    } else {
                        tiles[x][y] = 1;
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                renderTile(x * 32, y * 32, tiles[x][y]);
            }
        }
    }
    private Sprite sprite;
    private float scale = 1.0f;

    private void renderTile(float x, float y, int tile) {
        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, sheet.getTextureID());

        int tileX = 0;
        int tileY = 0;
        if (tile == 1) {
            tileX = 1;
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

    public boolean walkableTile(int x, int y) {
        return tiles[x][y] == 1;
    }
}
