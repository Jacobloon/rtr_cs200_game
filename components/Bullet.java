package components;
import java.awt.*;

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
    
    private int xVel = 0;
    private int yVel = 0;
    private int bulletVelocity = 10; 

    /**
     * Reference to the player object to check for collision
     */
    private Player player;
    
    
    /**
     * Constructor for the bullet
     */
    public Bullet(Player player, int direction) {
    	this.player = player;
    	this.x = this.player.getX() + (this.player.getWidth()/2);
    	this.y = this.player.getY() + (this.player.getHeight()/2);

    	switch (direction) {
            case 0:
                this.xVel = -1 * this.bulletVelocity;
                break;
            case 1:
                this.yVel = -1 * this.bulletVelocity;
                break;
            case 2:
                this.xVel = this.bulletVelocity;
                break;
            case 3:
                this.yVel = this.bulletVelocity;
                break;
        }
    }
    
    /**
     * This draws the revolver on the screen, and is the method that gets called every tick
     * @param g Graphics panel
     */
    public void draw(Graphics g) {
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