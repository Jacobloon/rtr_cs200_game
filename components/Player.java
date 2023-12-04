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
    private int speed = 10; // Determines how fast the player moves
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
    
    /**
     * Returns player's x position
     * @return int x position
     */
    public int getX() {
      return this.x;
    }

    /**
     * Sets the player's x position
     */
    public void setX(int x) {
      this.x = x;
    }
    /**
     * Returns player's y position
     * @return int y position
     */
    public int getY() {
      return this.y;
    }

    /**
     * Sets the player's y position
     */
    public void setY(int y) {
      this.y = y;
    }

    // TODO: Smooth movement
    public void setVelX(int velX) {
        this.velX = velX;
    }
    public void setVelY(int velY) {
        this.velY = velY;
    }
    public int getVelX() {
        return this.velX;
    }
    public int getVelY() {
        return this.velY;
    }

    public int getSpeed() {
        return this.speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
