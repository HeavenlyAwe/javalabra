/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.pacman.highscores;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Christoffer
 */
public class HighScoreManager {

    private List<HighScore> highScoreList;

    public HighScoreManager() {
        loadHighScore();
        if (highScoreList == null) {
            highScoreList = new LinkedList<>();
        }
    }

    public void addHighScore(HighScore highScore) {
        highScoreList.add(highScore);
    }

    public void saveHighScore() {
        try {
            File highScoreFolder = new File("high_scores");
            if (!highScoreFolder.exists()) {
                highScoreFolder.mkdir();
            }

            FileOutputStream fileOut = new FileOutputStream("high_scores/pacman_high_score");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(highScoreList);
            out.close();
            fileOut.close();

        } catch (IOException ex) {
            Logger.getLogger(HighScoreManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadHighScore() {
        try {
            File highScoreFolder = new File("high_scores/pacman_high_score");
            if (highScoreFolder == null || !highScoreFolder.exists()) {
                System.out.println("Haven't saved any high scores yet.");
                return;
            }

            FileInputStream fileIn = new FileInputStream("high_scores/pacman_high_score");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            highScoreList = (LinkedList) in.readObject();
            
            Collections.sort(highScoreList, new HighScoreComparator());
            
            in.close();
            fileIn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HighScoreManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HighScoreManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Collection getHighScoreList() {
        return highScoreList;
    }
}
