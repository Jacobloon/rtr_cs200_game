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
    // starting position on a loaded game
    private String start = "";

    /**
     * Default constructor with no starting info given
     */
    public GameLayout() {
        this.connections = new HashMap<String, Set<String>>();
        this.descriptions = new HashMap<String, LocationDescription>();
        this.newGame();
    }

    /**
     * Constructor for loaded games
     * @param connections
     * @param descriptions
     * @param start String starting location
     */
    public GameLayout(HashMap<String, Set<String>> connections, HashMap<String, LocationDescription> descriptions, String start) {
        this.connections = connections;
        this.descriptions = descriptions;
        this.start = start;
    }

    /**
     * Returns a location's description with a given string name
     * @param name String location's name
     * @return LocationDescription of the location
     */
    public LocationDescription getDescription(String name) {
        return descriptions.get(name);
    }

    // TODO: Complete
    public void newGame() {
        try {
            ArrayList<String> locations = new ArrayList<String>();
            File template = new File("locations");
            System.out.println(template.getAbsolutePath()); // TEST
            Scanner fileS = new Scanner(template);
            while (fileS.hasNext()) {
                locations.add(fileS.next());
            }
            Random rand = new Random();
            int revolverPos = rand.nextInt(2, locations.size());
            // TODO: Add trap and nemesis
            for (int i=0;i<locations.size();i++) {
                String property = (i == revolverPos) ? "REVOLVER" : "NONE";
                LocationDescription place = new LocationDescription("Place", locations.get(i), property);
                this.descriptions.put(locations.get(i), place);
            }
            fileS.close();
            // TODO: Add connections
            template = new File("connections");
            fileS = new Scanner(template);
            while (fileS.hasNextLine()) {
                String[] line = fileS.nextLine().split(",");
                if (!connections.containsKey(line[0])) {
                    Set<String> newSet = new HashSet<String>();
                    connections.put(line[0], newSet);
                }
                connections.get(line[0]).add(line[1] + ":" + line[2]);
            }
            fileS.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found exception");
        }
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
            ow.writeObject(new GameLayout(this.connections, this.descriptions, this.start));
            System.out.println("Variable mAde");
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

    /**
     * returns the player's starting location
     */
    public String getStart() {
        return this.start;
    }
}
