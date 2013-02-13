/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.pacman.highscores;

import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author Christoffer
 */
public class HighScoreComparator implements Comparator<HighScore>, Serializable {

    @Override
    public int compare(HighScore o1, HighScore o2) {
        return o2.getPoints() - o1.getPoints();
    }
}
