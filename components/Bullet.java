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
 * Class used to draw and define the bullets
 * @author Jacob Odenthal, Owen Talberg
 * @version 12/1/2023
 */
public class Bullet{
	
	private int x;
    private int y;
    
    // Width and height based on sprite image size
    private int rWidth = 5;
    private int rHeight = 5;
    
    private int mouseX;
    private int mouseY;
    private double xVel;
    private double yVel;
    private double xDif;
    private double yDif;
    private double bulletVelocity = 10.0; 
    
    /**
     * Reference to the player object to check for collision
     */
    private Player player;
    
    
    /**
     * Constructor for the bullet
     */
    public Bullet(Player player, int mouseX, int mouseY) {
    	this.player = player;
    	this.x = player.getX();
    	this.y = player.getY();
    	this.mouseX = mouseX;
    	this.mouseY = mouseY;
    	this.xDif = (mouseX * 1.0 - x) ;
    	this.yDif = (mouseY * 1.0 - y) ;
    	
    	double max = 1.0 * Math.max(mouseX, mouseY);
    	double speedChange = bulletVelocity / max;
    	this.xVel = xDif * 3.0;
    	this.yVel = yDif * 3.0;
    }
    
    /**
     * This draws the revolver on the screen, and is the method that gets called every tick
     * @param g Graphics panel
     */
    public void draw(Graphics g) {
    	System.out.println(xVel + " " + yVel);
        x += xVel;
        y += yVel;
    	
    	g.drawRect(this.x, this.y, rWidth, rHeight);
    }
    
    
    // GETTERS AND SETTERS ================================================================
    
    public int getX() {
    	return this.x;
    }
    public void setX(int x) {
    	this.x = x;
    }
    public int getY() {
    	return this.y;
    }
    public void setY(int y) {
    	this.y = y;
    }
}