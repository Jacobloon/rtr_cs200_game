import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import components.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

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
     * Our hero; he rode a blazing saddle, he wore a shining star
     */
    Player player;
    
    /**
     * 
     */
    GameLayout layout;
    /**
     * 
     */
    // TODO
    LocationManager locater;
    

    /**
     * Constructor that takes in the game window to manage
     * @param gameWindow JFrame
     */
    public GameManager(JFrame gameWindow) {
        this.gameWindow = gameWindow;
        this.player = new Player();
        this.layout = new GameLayout(this.player);
        this.locater = new LocationManager(this, layout);
    }

    /**
     * Method that continually determines where user travels in the game until
     * it is closed or finished
     * @param pause False if initial launch
     */
    public void startMenu(boolean pause) {
        gameWindow.getContentPane().removeAll(); // TODO: May cause issues
        gameWindow.add(loadMenu(pause));
        gameWindow.revalidate();
    }
    /**
     * Changes the location currently shown on the screen
     * @param gameWindow JFrame to change
     * @param location String location name
     */
    public void changeLocation(String location) {
        gameWindow.getContentPane().removeAll();
        GamePanel newPanel = locater.getLocation(location, player);
        gameWindow.add(newPanel);
        layout.setCurLocation(location);
        gameWindow.revalidate();
        newPanel.requestFocusInWindow();
    }



    // GAME PANELS ========================================================================================

    /**
     * Creates the main menu panel, can be called again later
     */
    public JPanel loadMenu(boolean pause) {
        // Menu panel 
        JPanel menu = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //JLabel title = new JLabel("Rootin Tootin Revenge");
        //title.setFont(new Font("Comic Sans",Font.PLAIN, 40));
        c.fill = GridBagConstraints.PAGE_START;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 0;
        // Basic Game Title: can change to an image later if we get ambitious
        BufferedImage titleImg;
        try {
            titleImg = ImageIO.read(new File("sprites/game_logo.png"));
            JLabel title = new JLabel(new ImageIcon(titleImg));
            menu.add(title, c);
        }
        catch (IOException e) {
            System.out.println("Could not load title image");
        }

        JLabel nameL = new JLabel();
        nameL.setFont(new Font("Comic Sans",Font.PLAIN, 20));
        c.fill = GridBagConstraints.PAGE_START;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 1;
        c.ipady = 10;
        menu.add(nameL, c);

        // Chooses Continue Game or New Game whether it's a pause menu or not
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 2;
        c.ipady = 0;
        if (!pause) {
            // New Game Button:
            JButton startBt = new JButton("New Game");
            // This format is how Buttons perform functions and other commands
            startBt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    menu.setVisible(false);
                    layout.newGame();
                    changeLocation("graveyard"); 
                }

            });
            menu.add(startBt, c);
        }
        else {
            // Continue Game Button:
            JButton contBt = new JButton("Continue Game");
            // Resumes the game
            contBt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    menu.setVisible(false);
                    changeLocation(layout.getCurLocation()); // Lame fix, restarts the current panel
                }

            });
            menu.add(contBt, c);

            // Save Game Button:
            JButton saveBt = new JButton("Save Game");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weighty = 1.0;
            c.gridx = 0;
            c.gridy = 3;
            c.ipady = 0;
            // This format is how Buttons perform functions and other commands
            saveBt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO : Load from file
                    String result = (String)JOptionPane.showInputDialog(
                        menu,
                        "Please enter save name", 
                        "Save Prompt",            
                        JOptionPane.PLAIN_MESSAGE,
                        null,            
                        null, 
                        "testsave"
                    );
                    layout.saveGame(result + ".txt");
                }

            });
            menu.add(saveBt, c);
        }
        
        // Load Game Button:
        JButton loadBt = new JButton("Load Game");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 4;
        c.ipady = 0;
        // This format is how Buttons perform functions and other commands
        loadBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO : Load from file
                JFileChooser chooser = new JFileChooser();
                chooser.addChoosableFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
                // invoke the showsSaveDialog function to show the save dialog
                int r = chooser.showOpenDialog(null);
                // if the user selects a file
                if (r == JFileChooser.APPROVE_OPTION) {
                    // set the label to the path of the selected file
                    File savedGame = chooser.getSelectedFile();
                    layout.loadGame(savedGame);
                    System.out.println(layout.getStart());
                    changeLocation(layout.getCurLocation());
                }
                // if the user cancelled the operation
                else
                    nameL.setText("the user cancelled the operation");
            }

        });
        menu.setBackground(Color.decode("#058AFF"));
        menu.add(loadBt, c);
        return menu;
    }
    
}
