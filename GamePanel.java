import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import components.*;
import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;

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
     * Revolver object if it is added to the scene
     */
    Revolver revolver;
    
    /**
     * List of shot bullets
     */
    ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    
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

    /**
     * Creates the panels on which the game is played, changed per location
     * @param layout layout information
     * @param manager GameManager
     * @param player Current Player
     * @param name Name of the location
     * @param edges Edges leading out of the location
     */
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
        addMouseListener( new MouseListener() {
            // Shoots the bullets
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
            	if (player.getRevolver()) {
            		PointerInfo a = MouseInfo.getPointerInfo();
            		Point b = a.getLocation();
            		int mouseX = (int) b.getX();
            		int mouseY = (int) b.getY();
            		Bullet shot = new Bullet(player, mouseX, mouseY); 
            		bullets.add(shot);
            	}
            }
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}
        });
        
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setBackground(new Color(16, 16, 16));
        t = new Timer(15, this);
        t.start();
    }
    
    /**
     * Checks for collisions between Bullets and other objects
     */
    public void checkCollision(Bullet shot) {
        if (shot.getY() <= 0 || shot.getY() >= 600 || shot.getX() <= 0 || shot.getX() >= 600) {
            bullets.remove(shot);
        }
    }

    /**
     * Paints the given components on the screen
     * @param g Graphics pane
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g);
        if (this.revolver != null) {
        	revolver.draw(g);
        }
        for (int i=0;i<bullets.size();i++) {
        	Bullet shot = bullets.get(i);
        	shot.draw(g);
        	checkCollision(shot);
        }
    }
    
    public void addRevolver(Revolver rev) {
    	this.revolver = rev;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (edgeMap.containsKey("0") && player.getX() < 10) {
            t.stop();
            player.setX(600 - 60 - player.getWidth());
            manager.changeLocation(edgeMap.get("0"));
            
        }
        else if (edgeMap.containsKey("1") && player.getY() < 10) {
            t.stop();
            player.setY(600 - 70 - player.getHeight());
            manager.changeLocation(edgeMap.get("1"));
            
        }
        else if (edgeMap.containsKey("2") && player.getX() > 500) {
            t.stop();
            player.setX(20);
            manager.changeLocation(edgeMap.get("2"));
            //t.stop();   // IMPORTANT LINE: Stops the old timer so game doesn't lag out 
        }
        else if (edgeMap.containsKey("3") && player.getY() > 470) {
            t.stop();
            player.setY(20);
            manager.changeLocation(edgeMap.get("3"));
            //t.stop();
        }
        repaint();
    }
}