
import org.fridlund.javalabra.game.Screen;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Christoffer
 */
public class PacmanMain {

    /**
     * Starts a new thread with the pacman game
     *
     * @param args
     */
    public static void main(String[] args) {
        Screen.setupNativesLWJGL();
        new Thread() {
            @Override
            public void run() {
                new PacmanGame().start();
            }
        }.start();
    }
}
