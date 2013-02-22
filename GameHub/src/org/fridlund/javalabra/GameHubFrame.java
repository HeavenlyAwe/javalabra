/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.javalabra;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;

/**
 * The graphics for the GameHub
 *
 * @author Christoffer
 */
public class GameHubFrame extends JFrame {

    private JList<String> gameList;
    private DefaultListModel<String> gameListModel;
    private GameHub hub;

    /**
     *
     */
    public GameHubFrame() {
        this.setTitle("Game Hub");

        this.setPreferredSize(new Dimension(300, 400));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        gameListModel = new DefaultListModel<>();
        gameList = new JList<>(gameListModel);
        this.add(gameList, BorderLayout.CENTER);

        JButton startSelectedGameButton = new JButton("Start Game");
        startSelectedGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameList.getSelectedIndex() != -1) {
                    String gameName = gameList.getSelectedValue();
                    hub.startGame(gameName);
                }
            }
        });
        this.add(startSelectedGameButton, BorderLayout.SOUTH);

        this.pack();

        this.setResizable(false);
        this.setLocationRelativeTo(null);


        hub = new GameHub();

        load();
    }

    private void load() {
        ArrayList<String> listOfGames = hub.getListOfGames();
        for (int i = 0; i < listOfGames.size(); i++) {
            gameListModel.addElement(listOfGames.get(i));
        }
    }
}
