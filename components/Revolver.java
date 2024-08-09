package components;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * Class used to draw and define the revolver sprite
 * @author Jacob Odenthal, Owen Talberg
 * @version 8/9/24
 */
public class Revolver{
	/**
     * Starting coordinate positions
     */
	private int x = 250;
    private int y = 250;
    
    /**
     * Sprite of the revolver on the screen
     */
    private BufferedImage revolverSprite;
    /**
     * Reference to the player object to check for collision
     */
    private Player player;
    /**
     * True if not yet collected, false if picked up
     */
    private boolean visible = true;
    
    /**
     * Constructor for the revolver object
     * @param player Player to attach the revolver to
     */
    public Revolver(Player player) {
    	this.player = player;
	   	try {
			revolverSprite = ImageIO.read(new File("sprites/rev_temp.png"));
		}
		catch (Exception e) {
			System.out.println("Failure loading revolver sprite.");
		}
    }
    
    /**
     * This draws the revolver on the screen, and is the method that gets called every tick
     * @param g Graphics panel
     */
    public void draw(Graphics g) {
    	if (visible) {
    		g.drawImage(revolverSprite, x, y, null);
            if (x <= player.getX() + player.getWidth() && x >= player.getX() && y <= player.getY() + player.getHeight() && y >= player.getY()) {
            	visible = false;
            	player.setRevolver(true);
            }
    	}
        
    }
    
}