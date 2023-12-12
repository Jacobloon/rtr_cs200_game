import java.io.*;
import java.util.*;

import components.Player;

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
    // Location of the revolver on the map
    private String revolverLocation;
    // Player reference
    private Player player;
    // Current Location
    private String curLocation;
    // Number of enemies left
    private int enemies;

    /**
     * Default constructor with no starting info given
     */
    public GameLayout(Player player) {
        this.connections = new HashMap<String, Set<String>>();
        this.descriptions = new HashMap<String, LocationDescription>();
        this.player = player;
        this.newGame();
        this.enemies = getNumEnemies();
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
        this.enemies = getNumEnemies();
    }

    /**
     * Returns a location's description with a given string name
     * @param name String location's name
     * @return LocationDescription of the location
     */
    public LocationDescription getDescription(String name) {
        return descriptions.get(name);
    }
    
    public int getNumEnemies() {
    	int tempNum = 0;
    	for (LocationDescription loc : this.descriptions.values()) {
    		if (loc.getProperty().equals("ENEMY")) {
    			tempNum++;
    		}
    	}
    	return tempNum;
    }
    
    // TODO: Complete
    public void newGame() {
        try {
            ArrayList<String> locations = new ArrayList<String>();
            this.curLocation = "graveyard";
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
                String property = (i == revolverPos) ? "REVOLVER" : "ENEMY";
                if (locations.get(i).equals("graveyard")) property = "NONE";
                LocationDescription place = new LocationDescription("Place", locations.get(i), property);
                this.descriptions.put(locations.get(i), place);
                // SAVE REVOLVER LOCATION STRING =========================
                if (i == revolverPos) {
                    this.revolverLocation = locations.get(i);
                }
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
            FileWriter ow = new FileWriter(filename);
            for (LocationDescription desc : this.descriptions.values()) {
                ow.write(desc.getDescription() + ":" + desc.getName() + ":" + desc.getProperty() + "\n");
            }
            ow.write("*\n");
            ow.write(getCurLocation() + "\n"); // Cur location
            ow.write(player.getRevolver() + "\n"); // Player has revolver
            ow.write(player.getLife() + "\n"); // Player life amount
            System.out.println("File saved");
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
    public void loadGame(File savefile) {
        try {
            Scanner ow = new Scanner(savefile);
            // Gets location descriptions
            HashMap<String, LocationDescription> loadDesc = new HashMap<String, LocationDescription>();
            String line = ow.nextLine().strip();
            while (!line.equals("*")) {
                String[] lineArr = line.split(":");
                LocationDescription loc = new LocationDescription(lineArr[0], lineArr[1], lineArr[2]);
                loadDesc.put(lineArr[1], loc);
                line = ow.nextLine().strip();
            }
            this.descriptions = loadDesc;
            // Gets player info
            setCurLocation(ow.next());
            String hasGun = ow.next();
            if (hasGun.equals("true")) player.setRevolver(true);
            player.setLife(ow.nextInt());
            ow.close();
        }
        catch (IOException io) {
            System.out.println("File could not be loaded");
        }
    }

    /**
     * Creates an iterator object that loops through all location names
     * 
     * @return iterator of the locations
     */
    public Iterator<String> locationIterator() {
        return connections.keySet().iterator();
    }

    /**
     * Creates an iterator object that loops through all given location's connections
     * 
     * @return iterator of the connections, null if empty
     */
    public Iterator<String> connectionIterator(String location) {
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

    /**
     * Returns the string name of the revolver's location
     */
    public String getRevLocation() {
        return this.revolverLocation;
    }


    /**
     * Gets and sets current location
     */
    public String getCurLocation() {
        return this.curLocation;
    }
    public void setCurLocation(String curLocation) {
        this.curLocation = curLocation;
    }
}
