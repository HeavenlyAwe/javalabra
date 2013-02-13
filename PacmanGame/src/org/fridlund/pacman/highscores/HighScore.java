/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.pacman.highscores;

import java.io.Serializable;

/**
 *
 * @author Christoffer
 */
public class HighScore implements Serializable {

    private int points;

    public HighScore(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
}
