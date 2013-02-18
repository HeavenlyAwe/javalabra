/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fridlund.javalabra.game.Game;

/**
 * Shows a list of all games in the game folder. Choose one of the games through
 * the Gui object and start playing it.
 *
 * @author Christoffer
 */
public class GameHub {

    private static Map<String, Game> games;

    public GameHub() {
        games = new HashMap<>();
        loadGames();
    }

    private void loadGames() {

        File gameFolder = new File("games");
        if (gameFolder == null) {
            System.out.println("Couldn't find folder games");
            return;
        }
        File[] subfolders = gameFolder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });

        System.out.println("Loading games from: " + gameFolder.getAbsolutePath());

        for (File dir : subfolders) {

            try {
                File file = new File(dir.getAbsolutePath() + "/" + dir.getName() + ".jar");

                if (file == null) {
                    System.out.println("Couldn't find file: " + dir.getName());
                    return;
                }

                URL url = file.toURI().toURL();
                URL[] urls = new URL[]{url};
                ClassLoader cl = new URLClassLoader(urls);

                Class cls = cl.loadClass(dir.getName());

                Game game = (Game) cls.newInstance();
                games.put(game.getTitle(), game);

            } catch (InstantiationException ex) {
                Logger.getLogger(GameHub.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(GameHub.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GameHub.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(GameHub.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Returns a list of Strings containing the names of all games.
     *
     * @return
     */
    public ArrayList<String> getListOfGames() {
        ArrayList<String> listOfGames = new ArrayList<>();
        for (String name : games.keySet()) {
            listOfGames.add(name);
        }
        return listOfGames;
    }

    /**
     * Starts a new thread where the game is run.
     *
     * @param gameName
     */
    public void startGame(final String gameName) {
        new Thread() {
            @Override
            public void run() {
                games.get(gameName).start();
            }
        }.start();
    }
}
