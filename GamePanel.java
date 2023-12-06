import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import components.*;
import java.util.HashMap;
import java.util.Set;

/**
 * Extended JPanel class to add ActionListener components so Players
 * and enemies can move
 * @author Jacob Odenthal, Owen Talberg
 * @version 12/1/2023
 */
public class GamePanel extends JPanel implements ActionListener {

    /**
     * Grabs the player from the GameManager so we don't
     * accidentally make a new one
     */
    Player player;

    /**
     * Timer to allow characters to move
     */
    Timer t;

    /**
     * Game manager class for our panel
     */
    GameManager manager;

    /**
     * Names and positions of edges connected to this location
     */
    HashMap<String, String> edgeMap = new HashMap<String, String>();

   //TODO This takes in a location description to allow locations travel x and ys instead of name
    public GamePanel(LayoutManager layout, GameManager manager, Player player, String name, Set<String> edges) {
        super(layout);
        this.manager = manager;
        for (String edge : edges) {
            String[] data = edge.split(":");
            this.edgeMap.put(data[1], data[0]);
        }
        this.player = player;
        
        addKeyListener(new KeyAdapter() {
            // Event when a key is pressed
            @Override
            public void keyPressed(KeyEvent e) {
                int KeyCode = e.getKeyCode();
                switch (KeyCode) {
                    case KeyEvent.VK_W:
                        player.Move(Player.PlayerDirections.UP); // W Keypress
                        break;
                    case KeyEvent.VK_A:
                        player.Move(Player.PlayerDirections.LEFT); // A Keypress
                        break;
                    case KeyEvent.VK_S:
                        player.Move(Player.PlayerDirections.DOWN); // S Keypress
                        break;
                    case KeyEvent.VK_D:
                        player.Move(Player.PlayerDirections.RIGHT); // D Keypress
                        break;
                    case KeyEvent.VK_ESCAPE:
                        manager.startGame(null);
                        t.stop();
                        repaint();
                        break;
                }
            }
            // Event when a key is released
            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        player.setVelY(0);
                        break;
                    case KeyEvent.VK_A:
                        player.setVelX(0);
                        break;
                    case KeyEvent.VK_S:
                        player.setVelY(0);
                        break;
                    case KeyEvent.VK_D:
                        player.setVelX(0);
                        break;
                }
            }
        });
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setBackground(new Color(16, 16, 16));
        t = new Timer(15, this);
        t.start();
    }
    
    /**
     * Checks for collisions between the player and walls/objects
     */
    public void checkCollision() {
        // TODO: Move player back if hitting object
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (edgeMap.containsKey("0") && player.getX() < 10) {
            t.stop();
            player.setX(600 - 10 - player.getWidth());
            manager.changeLocation(edgeMap.get("0"));
            
        }
        else if (edgeMap.containsKey("1") && player.getY() < 10) {
            t.stop();
            player.setY(600 - 10 - player.getHeight() - player.getHeight() / 2);
            manager.changeLocation(edgeMap.get("1"));
            
        }
        else if (edgeMap.containsKey("2") && player.getX() > 500) {
            t.stop();
            player.setX(50);
            manager.changeLocation(edgeMap.get("2"));
            //t.stop();   // IMPORTANT LINE: Stops the old timer so game doesn't lag out 
        }
        else if (edgeMap.containsKey("3") && player.getY() > 470) {
            t.stop();
            player.setY(50);
            manager.changeLocation(edgeMap.get("3"));
            //t.stop();
        }
        repaint();
    }

    // TODO: returns false when panel is to be closed
    public boolean isActive() {
        return true;
    }
}