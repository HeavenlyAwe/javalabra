/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

    public static int loadTextureLinear(String filepath) {
        int textureID = glGenTextures();

        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, textureID);
        InputStream in = null;
        try {
            in = new FileInputStream(filepath);
            PNGDecoder decoder = new PNGDecoder(in);
            ByteBuffer buffer = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            buffer.flip();
            in.close();

            glTexParameteri(GL_TEXTURE_RECTANGLE_ARB, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_RECTANGLE_ARB, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

            glTexImage2D(GL_TEXTURE_RECTANGLE_ARB, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        } catch (FileNotFoundException ex) {
            System.out.println("Could not find file: " + filepath);
        } catch (IOException ex) {
            System.out.println("Couldn't load texture");
            Logger.getLogger(TextureLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, 0);

        return textureID;
    }
}
