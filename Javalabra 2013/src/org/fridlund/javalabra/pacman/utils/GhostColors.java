/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman.utils;

import org.lwjgl.util.vector.Vector4f;

/**
 *
 * @author Christoffer
 */
public class GhostColors {

    private static Vector4f[] colors = new Vector4f[10];

    private static void setup() {
        int index = 0;

        colors[index++] = new Vector4f(1, 0, 0, 1);
        colors[index++] = new Vector4f(0, 1, 0, 1);
        colors[index++] = new Vector4f(0, 0, 1, 1);

        colors[index++] = new Vector4f(1, 0.5f, 0, 1);
        colors[index++] = new Vector4f(0.5f, 1, 0, 1);
        colors[index++] = new Vector4f(0, 0.5f, 1, 1);
        
        colors[index++] = new Vector4f(0.5f, 0, 0.5f, 1);
        colors[index++] = new Vector4f(0.5f, 0, 1, 1);
        colors[index++] = new Vector4f(0.5f, 0, 1, 1);
        
        colors[index++] = new Vector4f(1, 0, 0.5f, 1);

    }

    public static Vector4f getColor(int index) {
        if (colors[0] == null) {
            setup();
        }

        if (index < 0) {
            index = -index;
        }
        if (index >= colors.length) {
            index = index % colors.length;
        }
        return colors[index];
    }
}
