package components;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Class used to draw and define the player within our game
 * @author Jacob Odenthal, Owen Talberg
 * @version 8/9/24
 */
public class Player {
    public enum PlayerDirections {
      UP, DOWN, LEFT, RIGHT
    }
    /**
     * Location on map
     */
    private int x;
    private int y;
    /**
     * Speed, and corresponding velocities of the player
     */
    private int speed; 
    private int velX;
    private int velY;
    /**
     * Player size variables
     */
    private int pWidth;
    private int pHeight;
    /**
     * Player's hit points
     */
    private int life;
    /**
     * False until the player collects the revolver
     */
    private boolean hasRevolver = false;
    /**
     * Player sprite
     */
    private BufferedImage sprite;


    /**
     * Constructor for Player class that will store data that needs to be
     * saved and reloaded.
     * @exception IOException Player sprite image cannot be read
     */
    public Player() {
        this.x = 250;
        this.y = 250;
        this.speed = 7;
        this.velX = 0;
        this.velY = 0;
        this.pWidth = 44;
        this.pHeight = 64;
        this.life = 3;

        try {
            sprite = ImageIO.read(new File("sprites/boney.png"));
        }
        catch (IOException e) {
            System.out.println("Cannot load player sprite");
        }
    }

    /**
     * This draws the player on the screen, and is the method that gets called every tick
     * @param g Graphics panel
     */
    public void draw(Graphics g) {
        x+=velX;
        y+=velY;
        checkCollision();
        g.setColor(Color.WHITE);
        g.drawImage(sprite, x, y, null);
    }

    /**
     * Checks if the player is colliding with a wall or object
     */
    // TODO: Put in GamePanel??
    // Could be put in a parent class for all entitys: Entity()
    public void checkCollision() {
        if (getY() <= 0) {
            setY(0);
        }
        else if (getY() >= 600 - 10 - pHeight - (pHeight / 2)) { 
            setY(600 - 10 - pHeight - (pHeight / 2));            // TODO: Fix half of player going off bottom screen
        }
        if (getX() <= 0) {
            setX(0);
        }
        else if (getX() >= 600 - pWidth - (pWidth / 2)) { 
            setX(600 - pWidth - (pWidth / 2));           
        }
    }

    /**
     * Changes position based on keyboard input
     * @param direction PlayerDirections keyboard options
     */
    public void Move(PlayerDirections direction) {
        switch (direction) {
        case UP:
            setVelY(-1 * speed);
            break;
        case DOWN:
            setVelY(speed);
            break;
        case LEFT:
            setVelX(-1 * speed);
            break;
        case RIGHT:
            setVelX(speed);
            break;
        }
    }
    
    public void loseLife() {
        this.life -= 1;
    }

    // GETTER AND SETTER METHODS ===========================================================

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
    public int getVelX() {
        return this.velX;
    }
    public void setVelX(int velX) {
        this.velX = velX;
    }
    public int getVelY() {
        return this.velY;
    }
    public void setVelY(int velY) {
        this.velY = velY;
    }
    public int getSpeed() {
        return this.speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public int getWidth() {
        return this.pWidth;
    }
    public void setWidth(int pWidth) {
        this.pWidth = pWidth;
    }
    public int getHeight() {
        return this.pHeight;
    }
    public void setHeight(int pHeight) {
        this.pHeight = pHeight;
    }
    public void setRevolver(boolean has) {
    	this.hasRevolver = has;
    }
    public boolean getRevolver() {
    	return this.hasRevolver;
    }
    public int getLife() {
        return this.life;
    }
    public void setLife(int life) {
        this.life = life;
    }
    
}
