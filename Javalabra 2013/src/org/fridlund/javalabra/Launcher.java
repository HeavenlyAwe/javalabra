/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra;

import javax.swing.SwingUtilities;
import org.fridlund.javalabra.pacman.PacmanGame;

/**
 *
 * @author Christoffer
 */
public class Launcher {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PacmanGame().start();
            }
        });
    }
}
