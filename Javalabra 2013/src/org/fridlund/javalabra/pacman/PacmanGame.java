/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra.pacman;

import java.util.LinkedList;
import java.util.List;
import org.fridlund.javalabra.game.GameLWJGL;
import org.fridlund.javalabra.game.entities.Entity;
import org.fridlund.javalabra.game.utils.FontLoader;
import org.fridlund.javalabra.pacman.entities.Ghost;
import org.fridlund.javalabra.pacman.entities.Pacman;
import org.fridlund.javalabra.pacman.levels.Level;
import org.lwjgl.opengl.Display;

/**
 *
 * @author Christoffer
 */
public class PacmanGame extends GameLWJGL {

    private List<Entity> entities;
    private Level level;
    private Pacman pacman;
    private String message = "";
    public static float offsetDrawX;
    public static float offsetDrawY;

    public PacmanGame() {
        super("Pacman");
    }

    @Override
    public void setup() {
        FontLoader.loadFont("Times New Roman", FontLoader.FontStyle.PLAIN, 18);

        level = new Level();

        offsetDrawX = (Display.getWidth() - level.getWidth()) / 2;
        offsetDrawY = (Display.getHeight() - level.getHeight()) / 2;

        pacman = new Pacman(level);

        entities = new LinkedList<>();

        Ghost ghost = new Ghost(level);
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
            } else {
                message = "";
            }
        }
    }

    @Override
    public void render() {
        super.render();

        level.render();

        pacman.render();
        for (Entity entity : entities) {
            entity.render();
        }

        FontLoader.renderString(message, 10, 10, "times new roman");
    }
}
