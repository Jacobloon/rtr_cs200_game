import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class manages the current JFrame's panel. Essentially, this is the train station where all locations/menus
 * connect in the program before being sent to their destination.
 * @author Jacob Odenthal, Owen Talberg
 * @version 11/28/23
 */
public class GameManager {
    /**
     * Game window to manage
     */
    JFrame gameWindow;

    /**
     * Constructor that takes in the game window to manage
     * @param gameWindow JFrame
     */
    public GameManager(JFrame gameWindow) {
        this.gameWindow = gameWindow;
    }

    /**
     * Method that continually determines where user travels in the game until
     * it is closed or finished
     * @param gameWindow JFrame of the game
     */
    public void startGame(String location) {
        //gameWindow.add(graveyard());
        changeLocation( "menu");
    }
    /**
     * Changes the location currently shown on the screen
     * @param gameWindow JFrame to change
     * @param location String location name
     */
    public void changeLocation(String location) {
        // Can probably change this whole if/else statement to call from a HashMap
        if (location.equals("menu")) {
            gameWindow.add(loadMenu());
        }
        else if (location.equals("graveyard")) {
            gameWindow.add(loadGraveyard());
        }
    }

    // GAME PANELS ========================================================================================

    /**
     * Creates the main menu panel, can be called again later
     */
    public JPanel loadMenu() {
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
                changeLocation("graveyard"); // null for new game
            }

        });
        menu.add(startBt, c);
        return menu;
    }

    /**
     * This class represents the graveyard location in the game. 
     */
    private JPanel loadGraveyard() {
        // Menu panel 
        JPanel menu = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Basic Game Title: can change to an image later if we get ambitious
        JLabel title = new JLabel("Graveyard");
        title.setFont(new Font("Comic Sans",Font.PLAIN, 40));
        c.fill = GridBagConstraints.PAGE_START;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 100;
        menu.add(title, c);

        // Start Button:
        JButton startBt = new JButton("Return");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 1;
        c.ipady = 0;
        menu.add(startBt, c);
        // This format is how Buttons perform functions and other commands
        startBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.setVisible(false);
                changeLocation("menu");
            }
        });
        return menu;
    }
}
