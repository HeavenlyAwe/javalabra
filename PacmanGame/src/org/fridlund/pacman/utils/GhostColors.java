/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.pacman.utils;

import org.lwjgl.util.vector.Vector4f;

/**
 * Static class that holds a given set of colors, that the ghosts are drawn
 * with. The color is defined by the ghostColorIndex each ghost has been given
 * when it's created.
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

    /**
     * Get a predefined color with the index of the ghost. If the index is
     * higher than the amount of colors, the modulo of the index and the color
     * amount is calculated to return a color from the color array.
     *
     * @param index
     * @return
     */
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
