/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JFrame;
import org.fridlund.javalabra.game.Game;

/**
 *
 * @author Christoffer
 */
public class PacmanGame extends Game {
    
    private BufferedImage backBuffer = new BufferedImage(this.WIDTH, this.HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) backBuffer.getRaster().getDataBuffer()).getData();

    public PacmanGame(JFrame parent) {
        super(parent, "Pacman");
    }

    @Override
    public void update(double delta) {
    }

    @Override
    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(2);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
    }
}
