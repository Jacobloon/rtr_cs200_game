package components;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import components.*;

/**
 * Class used to draw and define the revolver sprite
 * @author Jacob Odenthal, Owen Talberg
 * @version 12/1/2023
 */
public class Revolver{
	
	private int x = 250;
    private int y = 250;
    
    // Width and height based on sprite image size
    private int rWidth = 60;
    private int rHeight = 35;
    
    private BufferedImage revolverSprite;
    
    /**
     * Reference to the player object to check for collision
     */
    private Player player;
    
    private boolean visible = true;
    
    /**
     * Constructor for the revolver object
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