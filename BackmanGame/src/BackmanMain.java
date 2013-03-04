
import org.fridlund.javalabra.game.Screen;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Christoffer
 */
public class BackmanMain {

    /**
     * Starts a new thread with the Pacman game. Used when not running from the GameHub
     *
     * @param args
     */
    public static void main(String[] args) {
        Screen.setupNativesLWJGL();
        new Thread() {
            @Override
            public void run() {
                new BackmanGame().start();
            }
        }.start();
    }
}
