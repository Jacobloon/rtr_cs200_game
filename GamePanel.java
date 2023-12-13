import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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
     * Enemy object if present
     */
    Enemy enemy;

    /**
     * List of shot bullets
     */
    ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    
    /**
     * Timer to allow characters to move
     */
    Timer t;

    /**
     * Game panel's background image
     */
    BufferedImage bg;
    BufferedImage map;
    BufferedImage health;
    /**
     * Boolean to show if current location is visible
     */
     private boolean curLocationIsVisible = false;
    /**
     * If player is on a shot pause or not. Prevents extreme rapid fire
     */
    boolean shotPause;

    /**
     * Game manager class for our panel
     */
    GameManager manager;

    /**
     * Names and positions of edges connected to this location
     */
    HashMap<String, String> edgeMap = new HashMap<String, String>();

    private String name;

    /**
     * Boolean to show if map is visible
     */
    private boolean mapIsVisible = false;
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
        this.name = name;
        try {
            if (name.equals("graveyard")) {
                this.bg = ImageIO.read(new File("sprites/graveyard.png"));
            }
            else {
                this.bg = ImageIO.read(new File("sprites/sand.png"));
            }
            this.map = ImageIO.read(new File("sprites/map.png"));
            this.health = ImageIO.read(new File("sprites/health.png"));
        }
        catch (IOException e) {
            System.out.println("Error loading sand");
        }
        this.manager = manager;
        for (String edge : edges) {
            String[] data = edge.split(":");
            this.edgeMap.put(data[1], data[0]);
        }
        this.player = player;
        this.shotPause = false;

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
                        manager.startMenu(true);
                        t.stop();
                        break;
                    case KeyEvent.VK_M:
                        if (!mapIsVisible) {
                            mapIsVisible = true;
                        }
                        break;
                    case KeyEvent.VK_N:
                        if (!curLocationIsVisible) {
                            curLocationIsVisible = true;
                        }
                        break;
                }
                // Bullet direction
                if (!shotPause && player.getRevolver()) {
                    switch (KeyCode) {
                        case KeyEvent.VK_LEFT:
                            Bullet shot = new Bullet(player, 0); 
                            bullets.add(shot);
                            shotPause = true;
                            break;
                        case KeyEvent.VK_UP:
                            shot = new Bullet(player, 1); 
                            bullets.add(shot);
                            shotPause = true;
                            break;
                        case KeyEvent.VK_RIGHT:
                            shot = new Bullet(player, 2); 
                            bullets.add(shot);
                            shotPause = true;
                            break;
                        case KeyEvent.VK_DOWN:
                            shot = new Bullet(player, 3); 
                            bullets.add(shot);
                            shotPause = true;
                            break;            
                    }
                }
            }
            // Event when a key is released
            @Override
            public void keyReleased(KeyEvent e) {
                // Allows movement again
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
                    case KeyEvent.VK_M:
                        mapIsVisible = false;
                        repaint();
                        break;
                    case KeyEvent.VK_N:
                        curLocationIsVisible = false;
                        repaint();
                        break;  
                }
                // Allows another shot in a direction
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        shotPause = false;
                        break;
                    case KeyEvent.VK_UP:
                        shotPause = false;
                        break;
                    case KeyEvent.VK_RIGHT:
                        shotPause = false;
                        break;
                    case KeyEvent.VK_DOWN:
                        shotPause = false;
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
     * Method to find where to place Location Label using given position
     * @param int position holds the direction of the connected area
     * @return int[] returns pixel locatios x,y
     */
    public int[] findLabelLocation(int position){
        int[] returnLocations = new int[2];
        switch (position) {
          case 0: //area left
            returnLocations[0] = 50;
            returnLocations[1] = 250;
            break;
          case 1: //area up
            returnLocations[0] = 275;
            returnLocations[1] = 100;
            break;
          case 2: //area right
            returnLocations[0] = 500;
            returnLocations[1] = 250;
            break;
          case 3: //area down
            returnLocations[0] = 275;
            returnLocations[1] = 500;
            break;
        }
        return returnLocations;
      }

    /**
     * Checks for collisions between Bullets and other objects
     */
    public void checkCollision(Bullet shot) {
        int sX = shot.getX();
        int sY = shot.getY();
        if (sY <= 0 || sY >= 600 || sX <= 0 || sX >= 600) {
            bullets.remove(shot);
        }
        if (this.enemy != null) {
            int eX = enemy.getX();
            int eY = enemy.getY();
            if (sY >= eY && sY <= eY + enemy.getHeight() && sX >= eX && sX <= eX + enemy.getWidth()) {
                this.enemy.getShot();
                LocationDescription loc = manager.layout.getDescriptions().get(name); // TODO: Initialize location description above in constructor
                loc.setProperty("NONE");
                this.enemy = null;
                bullets.remove(shot);
            }
        }
    }

    /**
     * Checks for collisions between EnemyBullets and other objects
     */
    public void checkCollisionEnemy(EnemyBullet shot) {
        // Checks for wall hit
        int sX = shot.getX();
        int sY = shot.getY();
        if (sY <= 0 || sY >= 600 || sX <= 0 || sX >= 600) {
            enemy.getBullets().remove(shot);
        }
        // Checks for Player hit
        int pX = player.getX();
        int pY = player.getY();
        if (sY >= pY && sY <= pY + enemy.getHeight() && sX >= pX && sX <= pX + enemy.getWidth()) {
            player.loseLife();
            enemy.getBullets().remove(shot);
        }
    }

    /**
     * Paints the given components on the screen
     * @param g Graphics pane
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.bg, 0, 0, this);
        player.draw(g);
        if (this.revolver != null) {
        	revolver.draw(g);
        }
        if (this.enemy != null) {
        	enemy.draw(g);
        }
        // Draws the player's bullets
        for (int i=0;i<bullets.size();i++) {
        	Bullet shot = bullets.get(i);
        	shot.draw(g);
        	checkCollision(shot);
        }
        // Draws the enemy's bullets
        if (enemy != null) {
            for (int i=0;i<enemy.getBullets().size();i++) {
                EnemyBullet eShot = enemy.getBullets().get(i);
                eShot.draw(g);
                checkCollisionEnemy(eShot);
            }
        }
        // Map goes on top
        if (mapIsVisible){
          g.drawImage(this.map, 90, 90, this);
        }
        if (curLocationIsVisible){ //Triggers Location to display if n key is pushed
            g.drawString(manager.layout.getCurLocation(), 275, 300);
            for (String neighbor : manager.layout.getConnections().get(name)){
              String [] neighborsAndLocations = neighbor.split(":");
              int[] panelLocation = findLabelLocation(Integer.parseInt(neighborsAndLocations[1]));
              g.drawString(neighborsAndLocations[0], panelLocation[0], panelLocation[1]);
            }
          }
        // Has the player died?
        if (player.getLife() == 0) {
            t.stop();
            manager.loseGame();
        } 
        else {
            for (int i=1;i<=player.getLife();i++) {
                g.drawImage(this.health, 590 - (i * 64), 10, this);
            }
        }
        // All enemies defeated?
        if (manager.layout.getNumEnemies() == 0) {
            manager.winGame();
        }
    }
    
    /**
     * Adds a revolver object to the panel
     * @param rev
     */
    public void addRevolver(Revolver rev) {
    	this.revolver = rev;
    }
    
    /**
     * Adds an enemy to the panel
     * @param enemy
     */
    public void addEnemy(Enemy enemy) {
        this.enemy = enemy;
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
        }
        else if (edgeMap.containsKey("3") && player.getY() > 470) {
            t.stop();
            player.setY(20);
            manager.changeLocation(edgeMap.get("3"));
        }
        repaint();
    }

}
