/**
 * This is the class that encapsulates the description and information of a location in our game
 * @author Jacob Odenthal, Owen Talberg
 * @version 11/24/2023
 */
public class LocationDescription {
    /**
     * This field stores a descrtiption of the location
     */
    private String description;

    /**
     * This field stores the name of the location
     */
    private String name;

    /**
     * This field stores the special property of a location
     * TODO: Make a list?
     */
    private String property;

    /**
     * Constructor for a new location on the map with no unique property
     * @param description of the location
     * @param name of the location
     */
    public LocationDescription(String description, String name) {
        this.description = description;
        this.name = name;
        this.property = "NONE";
    }   

    /**
     * Constructor for a new location on the map with all params given
     * @param description of the location
     * @param name of the location
     * @param property unique trait for the location
     */
    public LocationDescription(String description, String name, String property) {
        this.description = description;
        this.name = name;
        this.property = property;
    }   

    /**
     * This is a getter method for description
     * @return this.description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * This is a setter method for description
     * @param description new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This is a getter method for name
     * @return this.name
     */
    public String getName() {
        return this.name;
    }

    /**
     * This is a setter method for name
     * @param name new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * TODO: Change to list?
     * This is a getter method for property
     * @return this.property
     */
    public String getProperty() {
        return this.property;
    }

    /**
     * TODO: Change to list?
     * This is a setter method for property
     * @param property new property
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * Check if this and another location are equal to each other
     * @param otherLocation separate location to compare to
     * @return true if the same name, false if not
     */
    public boolean equals(LocationDescription otherLocation) {
        return this.name == otherLocation.getName();
    }

    /**
     * Overrode toString() method for the location
     * @return String containing the name and description of the location
     */
    @Override
    public String toString() {
        return this.name + " : " + this.description;
    }
}