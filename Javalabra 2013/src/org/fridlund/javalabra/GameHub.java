/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.fridlund.javalabra.game.Game;
import org.fridlund.javalabra.pacman.PacmanGame;

/**
 *
 * @author Christoffer
 */
public class GameHub {

    private JFrame frame;
    private Map<String, Game> games;

    public GameHub(JFrame parent) {
        this.frame = parent;
        this.games = new HashMap<>();
        loadGames();
    }

    private void loadGames() {
        loadDefaultGames();

        File gameFolder = new File("games");
        File[] subfolders = gameFolder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });

        for (File dir : subfolders) {

            File[] folderFiles = dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.isFile();
                }
            });

            URL[] classUrls;

            try {
                URL url = dir.toURI().toURL();
                classUrls = new URL[]{url};

                URLClassLoader ucl = new URLClassLoader(classUrls);
                String s = dir.getName().toLowerCase() + "." + dir.getName();
                Class c = ucl.loadClass(s);
                System.out.println(c.getName());
                for (Field field : c.getDeclaredFields()) {
                    System.out.println("Field name" + field.getName());
                }

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GameHub.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(GameHub.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private void loadDefaultGames() {
        games.put("Pacman", new PacmanGame(frame));
    }

    public ArrayList<String> getListOfGames() {
        ArrayList<String> listOfGames = new ArrayList<>();
        for (String name : games.keySet()) {
            listOfGames.add(name);
        }
        return listOfGames;
    }

    public void startGame(String gameName) {
        games.get(gameName).start();
    }
}
