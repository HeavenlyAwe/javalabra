/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman;

import java.util.LinkedList;
import java.util.List;
import org.fridlund.javalabra.game.GameLWJGL;
import org.fridlund.javalabra.game.entities.Entity;
import org.fridlund.javalabra.pacman.entities.Ghost;
import org.fridlund.javalabra.pacman.entities.Pacman;

/**
 *
 * @author Christoffer
 */
public class PacmanGame extends GameLWJGL {

    private List<Entity> entities;
    private Pacman pacman;
    
    private String message = "";

    public PacmanGame() {
        super("Pacman");
    }

    @Override
    public void setup() {
        pacman = new Pacman();
        pacman.setup();

        entities = new LinkedList<>();

        Ghost ghost = new Ghost();
        ghost.setup();
        entities.add(ghost);
    }

    @Override
    public void cleanUp() {
        pacman.cleanUp();
        for (Entity entity : entities) {
            entity.cleanUp();
        }
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        pacman.update(delta);
        for (Entity entity : entities) {
            entity.update(delta);
            if (pacman.collision(entity)) {
                message = "inside entity";
            }
        }
    }

    @Override
    public void render() {
        super.render();

        pacman.render();
        for (Entity entity : entities) {
            entity.render();
        }
        
        
    }
}
