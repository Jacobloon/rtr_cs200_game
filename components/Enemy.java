package components;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This is the class to create enemy objects on the playing field
 * @author Jacob Odenthal
 * @version 12/10/2023
 */
public class Enemy {
    /**
     * Sprite of the enemy
     */
    private BufferedImage sprite;


    /**
     * Constructor for the object
     */
    public Enemy() {
        try {
            sprite = ImageIO.read(new File("sprites/meany.png"));
        }
        catch (IOException e) {
            System.out.println("Cannot load enemy sprite");
        }
    }
}
