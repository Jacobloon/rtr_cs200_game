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

    public GamePanel(LayoutManager layout, Player player) {
        super(layout);
        this.player = player;
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int KeyCode = e.getKeyCode();
                if (KeyCode == KeyEvent.VK_W) {             // W Keypress
                    player.Move(PlayerDirections.UP);
                }
                if (KeyCode == KeyEvent.VK_A) {
                    player.Move(PlayerDirections.LEFT);     // A Keypress
                }
                if (KeyCode == KeyEvent.VK_S) {
                    player.Move(PlayerDirections.DOWN);     // S Keypress
                }
                if (KeyCode == KeyEvent.VK_D) {
                    player.Move(PlayerDirections.RIGHT);    // D Keypress
                }
            }
        });
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setBackground(new Color(16, 16, 16));
        t = new Timer(5, this);
        t.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}