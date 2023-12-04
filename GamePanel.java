import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import components.*;

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

   //TODO This takes in a location description to allow locations travel x and ys instead of name
    public GamePanel(LayoutManager layout, GameManager manager, Player player, String name) {
        super(layout);
        this.manager = manager;
        // TODOL Remove desc
        LocationDescription desc = new LocationDescription("Description", name, "NONE");
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
        // TODO: Move player back if hitting wall
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (player.getX() > 500) {
            player.setX(0);
            manager.changeLocation("Place");
            t.stop();   // IMPORTANT LINE: Stops the old timer so game doesn't lag out 
        }
        repaint();
    }

    // TODO: returns false when panel is to be closed
    public boolean isActive() {
        return true;
    }
}