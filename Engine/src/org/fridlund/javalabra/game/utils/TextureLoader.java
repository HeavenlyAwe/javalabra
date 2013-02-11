/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.opengl.PNGDecoder;

/**
 *
 * @author Christoffer
 */
public class TextureLoader {

    public static int loadTextureLinear(InputStream in) {
        int textureID = glGenTextures();
        
        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, textureID);
        try {
            PNGDecoder decoder = new PNGDecoder(in);
            ByteBuffer buffer = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            buffer.flip();
            in.close();

            glTexParameteri(GL_TEXTURE_RECTANGLE_ARB, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_RECTANGLE_ARB, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

            glTexImage2D(GL_TEXTURE_RECTANGLE_ARB, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(TextureLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TextureLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, 0);

        return textureID;
    }

    public static BufferedImage loadImage(String filepath) {
        URL url = TextureLoader.class.getClass().getResource(filepath);
        File file = new File(url.getFile());
        
        if(file.exists()){
            System.out.println("Found on disk");
        }
        else {
            System.out.println("Found in jar");
        }
        
        return null;
    }
}
