/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author Christoffer
 */
public abstract class Game extends Canvas implements Runnable {

    private boolean running;
    private JDialog frame;

    public Game(JFrame parent, String title) {
        Dimension dim = new Dimension(800, 600);
        this.setMinimumSize(dim);
        this.setMaximumSize(dim);
        this.setPreferredSize(dim);

        frame = new JDialog(parent, title, true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(this, BorderLayout.CENTER);
        frame.pack();

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    public void start() {
        running = true;
        frame.setVisible(true);
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
