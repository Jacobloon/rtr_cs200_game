import javax.imageio.ImageIO;
import javax.swing.*;
import components.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.Queue;

/**
 * This manages all locations and prepares them to be sent to the GameManager
 * @author Jacob Odenthal, Owen Talberg
 * @version 12/1/2023
 */
public class LocationManager {
  /**
   * Layout of the game containing the connections and descriptions
   */
  GameLayout layout;
  /**
   * Manager for all GamePanels in the game
   */
  GameManager manager;

  /**
   * Constructor that takes in a gameLayout to define the map
   * @param manager GameManager of the GamePanels
   * @param layout GameLayout graph managing class
   */
  public LocationManager(GameManager manager, GameLayout layout) {
    this.layout = layout;
    this.manager = manager;
  }
  
  /**
   * This method finds the shortest route to the player's current objective using BFS
   * @param start String starting location
   * @param end String ending location
   * @return String location name/direction
   */
  public String getShortestRoute(String start) {
    String end = layout.getRevLocation();
    if (start.equals("graveyard") || start.equals(end)) return "";
    Queue<String> bfsQueue = new LinkedList<String>();
    HashMap<String, String> parentMap = new HashMap<>();
    parentMap.put(start, "");
    bfsQueue.offer(start);

    while (!bfsQueue.isEmpty()) {
      String cur = bfsQueue.remove();//.split(":")[0];
      Iterator<String> itr = layout.connectionIterator(cur);
      while (itr.hasNext()) {
        String neighbor = itr.next();
        neighbor = neighbor.split(":")[0];
        if (!parentMap.containsKey(neighbor)) {
          bfsQueue.offer(neighbor);
          parentMap.put(neighbor, cur);
        }
      }
    }

    Stack<String> path = new Stack<String>();
    String target = end;
    String pos = "";
    while (!target.equals(start)) {
      path.push(target);
      target = parentMap.get(target).split(":")[0];
    }
    String nextPlace = path.pop();
    Set<String> edges = layout.getConnections().get(start);
    for (String edge : edges) {
      if (nextPlace.equals(edge.split(":")[0])) {
        pos = edge.split(":")[1];
      }
    }

    return pos;
  }

  /**
   * This retrieves and constructs the location with the given name
   * @param name String of the location
   * @param user Player user to add to the new location
   * @exception e if pointer image cannot be located or loaded
   */
  public GamePanel getLocation(String name, Player user) {
    LocationDescription desc = layout.getDescriptions().get(name);
    Set<String> edges = layout.getConnections().get(name);
    String prop = desc.getProperty();

    String pointerPos = getShortestRoute(name); 
    
    // Menu panel 
    GamePanel panel = new GamePanel(null, manager, user, name, edges);
    
    // Loads enemies if present
    if (prop.equals("ENEMY")) {
      Enemy enemy = new Enemy(user);
      panel.addEnemy(enemy);
    }

    // Loads revolver if it is found
    if (!user.getRevolver() && prop.equals("REVOLVER")) {
    	Revolver rev = new Revolver(user);
    	panel.addRevolver(rev);
    }
    
    // Places the pointer if revolver is not found
    if (!user.getRevolver() && !prop.equals("REVOLVER") && !name.equals("graveyard")) {
      try {
        String fileLocation = "sprites/hand_" + pointerPos + ".png";
        BufferedImage pointerImg = ImageIO.read(new File(fileLocation));
        JLabel pointer = new JLabel(new ImageIcon(pointerImg));
        int x = 0;
        int y = 0;
        switch(pointerPos) {
          case "0":
            y = 265;
            x = 10;
            break;
          case "1":
            x = 275;
            y = 10;
            break;
          case "2":
            y = 265;
            x = 540;
            break;
          case "3":
            y = 520;
            x = 275;
            break;
        }
        pointer.setLocation(x, y);
        pointer.setSize(32, 32);
        panel.add(pointer);
      }
      catch (Exception e) {
        System.out.println("Could not load pointer sprite");
      }
    }
    
    return panel;
  }
}