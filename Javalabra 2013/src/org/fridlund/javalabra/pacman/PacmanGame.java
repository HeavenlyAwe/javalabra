/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman;

import org.fridlund.javalabra.game.GameLWJGL;
import org.fridlund.javalabra.pacman.entities.Pacman;

/**
 *
 * @author Christoffer
 */
public class PacmanGame extends GameLWJGL {

    private Pacman pacman;

    public PacmanGame() {
        super("Pacman");
    }

    @Override
    public void setup() {
        pacman = new Pacman();
        pacman.setup();
    }

    @Override
    public void cleanUp() {
        pacman.cleanUp();
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        pacman.update(delta);
    }

    @Override
    public void render() {
        super.render();

        pacman.render();
    }
}
