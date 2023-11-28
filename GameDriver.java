import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Driving class for the CS200 game final project. Utilizes Java Swing and other components to create a game interface
 * 
 * @author Jacob Odenthal, Owen Talberg
 * @version 11/28/2023
 */
public class GameDriver{
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
        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Menu panel 
        JPanel menu = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Basic Game Title: can change to an image later if we get ambitious
        JLabel title = new JLabel("Rootin Tootin Revenge");
        title.setFont(new Font("Comic Sans",Font.PLAIN, 40));
        c.fill = GridBagConstraints.PAGE_START;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 100;
        menu.add(title, c);

        // Start Button:
        JButton startBt = new JButton("New Game");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 1;
        c.ipady = 0;
        // This format is how Buttons perform functions and other commands
        startBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.setVisible(false);
                GameManager.startGame(gameWindow, "Graveyard");
            }

        });
        menu.add(startBt, c);

        // Sets the current panel to menu to start the game
        gameWindow.add(menu);
        gameWindow.setVisible(true);

        // Window quality changes; title of window, initial size of window, and makes it visible
        menu.setVisible(true);

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