import java.io.*;
import java.util.*;

/**
 * Class to define the game's layout, specifically the graphs
 * 
 * @author Jacob Odenthal, Owen Talberg
 * @version 11/25/23
 */
public class GameLayout {
    // associates a given location label with a set of connected locations
    private HashMap<String, Set<String>> connections;
    // associates a given location with its description object
    private HashMap<String, LocationDescription> descriptions;

    /**
     * Default constructor with no starting info given
     */
    public GameLayout() {
        this.connections = new HashMap<String, Set<String>>();
        this.descriptions = new HashMap<String, LocationDescription>();
    }

    /**
     * Returns a location's description with a given string name
     * @param name String location's name
     * @return LocationDescription of the location
     */
    public LocationDescription getDescription(String name) {
        return descriptions.get(name);
    }

    /**
     * Saves the current game's information
     * 
     * @param filename to save info to
     */
    // TODO: Fix specifics and make sure it works
    public void saveGame(String filename) {
        try {
            ObjectOutputStream ow = new ObjectOutputStream(new FileOutputStream(filename));
            ow.writeObject(this);
            ow.close();
        }
        catch (IOException io) {
            System.out.println("File could not be created");
        }
    }

    /**
     * Loads a game from saved file and sets current GameLayout to the state
     * 
     * @param filename to load info from
     */
    // TODO: Fix specifics and make sure it works
    public void loadGame(String filename) {
        try {
            ObjectInputStream ow = new ObjectInputStream(new FileInputStream(filename));
            GameLayout loaded = (GameLayout) ow.readObject();
            this.connections = loaded.getConnections();
            this.descriptions = loaded.getDescriptions();
            ow.close();
        }
        catch (IOException io) {
            System.out.println("File could not be loaded");
        }
        catch(ClassNotFoundException cge) {
            System.out.println("Issue in converting class information");
        }
    }

    /**
     * Creates an iterator object that loops through all location names
     * 
     * @return iterator of the locations
     */
    public Iterator locationIterator() {
        return connections.keySet().iterator();
    }

    /**
     * Creates an iterator object that loops through all given location's connections
     * 
     * @return iterator of the connections, null if empty
     */
    public Iterator connectionIterator(String location) {
        return connections.get(location).iterator();
    }

    /**
     * Getter method for connections
     * 
     * @return all connections of the current game layout
     */
    public HashMap<String, Set<String>> getConnections() {
        return this.connections;
    }

    /**
     * Setter method for connections
     * 
     * @param connections for the gamelayout
     */
    public void setConnections(HashMap<String, Set<String>> connections) {
        this.connections = connections;
    }

    /**
     * Getter method for descriptions
     * 
     * @return all descriptions of the current game layout
     */
    public HashMap<String, LocationDescription> getDescriptions() {
        return this.descriptions;
    }

    /**
     * Setter method for descriptions
     * 
     * @param descriptions for the gamelayout
     */
    public void setDescriptions(HashMap<String, LocationDescription> descriptions) {
        this.descriptions = descriptions;
    }
}
