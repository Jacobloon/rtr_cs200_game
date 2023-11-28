import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class manages the current JFrame's panel, and changes it according to which part
 * of the map the player is currently on or moving to
 * 
 * @author Jacob Odenthal, Owen Talberg
 * @version 11/28/23
 */
public class GameManager {
    /**
     * Method that continually determines where user travels in the game until
     * it is closed or finished
     * @param gameWindow JFrame of the game
     */
    public static void startGame(JFrame gameWindow, String location) {
        //gameWindow.add(graveyard());
        String nextLocation = graveyard(gameWindow);
        System.out.println(nextLocation);
    }

    /**
     * This class represents the graveyard location in the game. 
     */
    private static String graveyard(JFrame gameWindow) {
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
        // This format is how Buttons perform functions and other commands
        startBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.setVisible(false);
                return "Town";
            }

        });

        return "Town";
    }
}
