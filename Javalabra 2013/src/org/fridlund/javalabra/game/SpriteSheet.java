/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Christoffer
 */
public class SpriteSheet {

    public String path;
    public int width;
    public int height;
    
    public int[] pixels;

    public SpriteSheet(String path, int tw, int th) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(SpriteSheet.class.getClass().getResource(path));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (image == null) {
            System.out.println("Image null");
            return;
        }

        this.path = path;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }
    
}
