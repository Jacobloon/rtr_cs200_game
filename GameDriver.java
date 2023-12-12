import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import components.*;

/**
 * Driving class for the CS200 game final project. Utilizes Java Swing and other components to create a game interface
 * 
 * @author Jacob Odenthal, Owen Talberg
 * @version 11/28/2023
 */
public class GameDriver{
    /**
     * Game manager for the experience
     */
    GameManager gameSystem;

    /**
     * Constructor class to allow the driver to be encapsulated by a Swing window
     */
    public GameDriver() {
        // Window size variables for sizing
        int width = 600;
        int height = 600;
        // Our initial game window
        JFrame gameWindow = new JFrame();
        gameWindow.setTitle("Rootin Tootin Revenge");
        gameWindow.setSize(width, height);
        gameWindow.setResizable(false);
        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Creates the main gameSystem which changes locations, menus, etc.
        this.gameSystem = new GameManager(gameWindow);

        // Window quality changes; title of window, initial size of window, and makes it visible
        this.gameSystem.startMenu(false);

        // Sets the current panel to menu to start the game
        gameWindow.setVisible(true);
    }


    /**
     * This is where the magic happens
     */
    public static void main(String[] args) {
        // Wacky looking code I found online that supposedly helps threading issues
        EventQueue.invokeLater(() -> {
            GameDriver ex = new GameDriver();
        });
    }
}