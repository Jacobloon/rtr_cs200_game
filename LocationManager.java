import javax.swing.*;
import components.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * This manages all locations and prepares them to be sent to the GameManager
 * @author Jacob Odenthal, Owen Talberg
 * @version 12/1/2023
 */
public class LocationManager {
  /**
   * Layout of the game containing the connections and descriptions
   */
  GameLayout gameLayout;
  
  /**
   * 
   */
  GameManager manager;
  
  /**
   * Constructor that takes in a gameLayout to define the map
   */
  public LocationManager(GameManager manager, GameLayout gameLayout) {
    this.gameLayout = gameLayout;
    this.manager = manager;
  }
  
  /**
   * This retrieves and constructs the location with the given name
   */
  public GamePanel getLocation(String name, Player user) {
    LocationDescription desc = gameLayout.getDescriptions().get(name);
    Set<String> edges = gameLayout.getConnections().get(name);
    //String prop = desc.getProperty();
    
    // Menu panel 
    GamePanel panel = new GamePanel(new GridBagLayout(), manager, user, name);
    GridBagConstraints c = new GridBagConstraints();
    
    // Basic Game Title: can change to an image later if we get ambitious
    JLabel title = new JLabel(name);
    title.setFont(new Font("Comic Sans",Font.PLAIN, 40));
    c.fill = GridBagConstraints.PAGE_START;
    c.weightx = 1.0;
    c.weighty = 1.0;
    c.gridx = 0;
    c.gridy = 0;
    c.ipady = 100;
    panel.add(title, c);
    
    return panel;
  }
  
}