/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.pacman.highscores;

import java.io.Serializable;

/**
 * An High Score instance containing the points. Could be expanded to contain
 * any kind of information.
 *
 * @author Christoffer
 */
public class HighScore implements Serializable {

    private int points;

    public HighScore(int points) {
        this.points = points;
    }

    //=================================================================
    /*
     * PUBLIC METHODS
     */
    //=================================================================
    //=================================================================
    /*
     * GETTERS
     */
    //=================================================================
    public int getPoints() {
        return points;
    }
}
