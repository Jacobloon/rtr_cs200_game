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
    
    GameManager manager = this;

    BufferedImage gameOver;
    BufferedImage gameWon;
    BufferedImage titleImg;

    /**
     * Constructor that takes in the game window to manage
     * @param gameWindow JFrame
     */
    public GameManager(JFrame gameWindow) {
        this.gameWindow = gameWindow;
        this.player = new Player();
        this.layout = new GameLayout(this.player);
        this.locater = new LocationManager(this, layout);
        try {
            this.gameOver = ImageIO.read(new File("sprites/game_over.png"));
            this.gameWon = ImageIO.read(new File("sprites/game_won.png"));
            this.titleImg = ImageIO.read(new File("sprites/title_image.png"));
        }
        catch (IOException e) {
            System.out.println("Could not load menu sprite");
        }
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


    /**
     * Hopefully you never see this. Shows the lose game menu if you lose
     */
    public void loseGame() {
        gameWindow.getContentPane().removeAll();
        JPanel ggPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel ggLabel = new JLabel(new ImageIcon(gameOver));
        c.fill = GridBagConstraints.PAGE_START;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        ggPanel.add(ggLabel,c);

        // restart Game Button:
        /* 
        JButton restartBt = new JButton("Restart?");
        c.fill = GridBagConstraints.PAGE_START;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 10;
        // This format is how Buttons perform functions and other commands
        restartBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ggPanel.setVisible(false);
                player = new Player();
                layout = new GameLayout(player);
                locater = new LocationManager(manager, layout);
                layout.newGame();
                changeLocation("graveyard"); 
                gameWindow.remove(ggPanel);
            }

        });
        restartBt.setLocation(250, 400);
        ggPanel.add(restartBt, c);
        */

        gameWindow.add(ggPanel);
        gameWindow.revalidate();
    }

    /**
     * Extremely rewarding. Win game menu
     */
    public void winGame() {
        gameWindow.getContentPane().removeAll();
        JPanel ggPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel ggLabel = new JLabel(new ImageIcon(gameWon));
        c.fill = GridBagConstraints.PAGE_START;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        ggPanel.add(ggLabel,c);

        // restart Game Button:
        /* 
        JButton restartBt = new JButton("Restart?");
        c.fill = GridBagConstraints.PAGE_START;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 1;
        c.ipady = 10;
        // This format is how Buttons perform functions and other commands
        restartBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ggPanel.setVisible(false);
                player = new Player();
                layout = new GameLayout(player);
                locater = new LocationManager(manager, layout);
                layout.newGame();
                changeLocation("graveyard"); 
                gameWindow.remove(ggPanel);
            }

        });
        ggPanel.add(restartBt, c);
        */

        gameWindow.add(ggPanel);
        gameWindow.revalidate();
    }

    // GAME PANELS ========================================================================================

    /**
     * Creates the main menu panel, can be called again later
     */
    public JPanel loadMenu(boolean pause) {
        // Menu panel 
        JPanel menu = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        menu.setLayout(null);
        // Basic Game Title: can change to an image later if we get ambitious
        BufferedImage logoImg;
        try {
            logoImg = ImageIO.read(new File("sprites/game_logo.png"));
            JLabel title = new JLabel(new ImageIcon(logoImg));
            title.setBounds(-10, 0, 600, 300);
            menu.add(title);
        }
        catch (IOException e) {
            System.out.println("Could not load title image");
        }


        // Chooses Continue Game or New Game whether it's a pause menu or not
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
            startBt.setBounds(100, 440, 400, 50);
            menu.add(startBt);
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
            contBt.setBounds(100, 440, 400, 50);
            menu.add(contBt);

            // Save Game Button:
            JButton saveBt = new JButton("Save Game");
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
            saveBt.setBounds(100, 500, 400, 50);
            menu.add(saveBt);
        }
        
        // Load Game Button:
        JButton loadBt = new JButton("Load Game");
        // This format is how Buttons perform functions and other commands
        loadBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        menu.setBackground(Color.decode("#090a6b"));
        loadBt.setBounds(100, 500, 400, 50);
        menu.add(loadBt);


        JLabel titleBg = new JLabel(new ImageIcon(titleImg));
        titleBg.setBounds(0, 0, 600, 600);
        menu.add(titleBg);

        return menu;
    }
    
}
