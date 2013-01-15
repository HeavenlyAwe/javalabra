/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Christoffer
 */
public abstract class Game extends Canvas implements Runnable {
    
    private boolean running;
    private JFrame frame;
    
    public Game(String title) {
        Dimension dim = new Dimension(800, 600);
        this.setMinimumSize(dim);
        this.setMaximumSize(dim);
        this.setPreferredSize(dim);
        
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this, BorderLayout.CENTER);
        frame.pack();
        
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void start() {
        running = true;
        new Thread(this).start();
    }
    
    public void stop() {
        running = false;
    }
    
    @Override
    public void run() {    
        int delta = 16;    
        while (running) {
            update(delta);
            render();
        }
    }
    
    public abstract void update(double delta);
    
    public abstract void render();
}
