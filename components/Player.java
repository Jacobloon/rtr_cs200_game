package components;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Class used to draw and define the player within our game
 * @author Jacob Odenthal, Owen Talberg
 * @version 12/1/2023
 */
public class Player {

    private int x = 28;
    private int y = 190;
    private int speed = 10;

    public Player() {

    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, 80, 80);
    }

    // TODO: Change to KeyBindings for smoother movement?
    public void Move(PlayerDirections direction) {
        switch (direction) {
        case UP:
            y -= speed;
            break;
        case DOWN:
            y += speed;
            break;
        case LEFT:
            x -= speed;
            break;
        case RIGHT:
            x += speed;
            break;
        }
    }
    
}
