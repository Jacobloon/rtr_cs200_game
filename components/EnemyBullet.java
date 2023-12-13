package components;
import java.awt.*;

public class EnemyBullet {
	
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
    private Enemy enemy;
    
    
    /**
     * Constructor for the bullet
     */
    public EnemyBullet(Enemy enemy, int direction) {
    	this.enemy = enemy;
    	this.x = this.enemy.getX() + (this.enemy.getWidth()/2);
    	this.y = this.enemy.getY() + (this.enemy.getHeight()/2);

    	switch (direction) {
            case 0:
                this.xVel = -1 * this.bulletVelocity;
                break;
            case 2:
                this.xVel = this.bulletVelocity;
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
    	g.setColor(Color.ORANGE);
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
