package components;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import components.*;

/**
 * Class used to draw and define the player within our game
 * @author Jacob Odenthal, Owen Talberg
 * @version 12/1/2023
 */
public class Player {
    public enum PlayerDirections {
      UP, DOWN, LEFT, RIGHT
    }
    
    private int x = 28;
    private int y = 190;
    
    // Determines how fast the player moves
    private int speed = 10; 
    private int velX = 0;
    private int velY = 0;

    // Player Size variables
    private int pWidth = 80;
    private int pHeight = 80;

    /**
     * Constructor for Player class that will store data that needs to be
     * saved and reloaded.
     */
    public Player() {
        // TODO: Add info
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
        g.fillRect(x, y, pWidth, pHeight); 
    }

    /**
     * Checks if the player is colliding with a wall or object
     */
    // TODO: Put in GamePanel??
    public void checkCollision() {
        if (getY() <= 0) {
            setY(0);
        }
        else if (getY() >= 600 - pHeight - (pHeight / 2)) { // TODO: Panel Height
            setY(600 - pHeight - (pHeight / 2));            // TODO: Fix half of player going off bottom screen
        }
        if (getX() <= 0) {
            setX(0);
        }
        else if (getX() >= 600 - pWidth) { // TODO: Panel Height
            setX(600 - pWidth);           
        }
    }

    /**
     * Changes position based on keyboard input
     * @param direction PlayerDirections keyboard options
     */
    // TODO: Change to KeyBindings for smoother movement?
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
}
