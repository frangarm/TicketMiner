package com.runticket.model;
/**
 * The Outdoor class represents an outdoor venue with a name, type, and capacity.
 * It implements the Venue interface to provide methods for accessing and modifying venue details.
 *
 * @author Francisco Garcia
 */
//Outdoor objects will inherit methods from the Venue class
public class Outdoor implements  Venue{
    /**
     * The attributes of Outdoor
     * @param venueName The Outdoor Venue's name
     * @param venueType The Venue's type, in this case Outdoor
     * @param venueCapacity The Outdoor Venue's capacity
     */
    private String venueName, venueType;
    private int venueCapacity;
    /**
     * Default constructor for Outdoor class
     */
    public Outdoor(){

    }
    /**
     * Constructor for Outdoor class with name parameter
     * @param nameIn the name of the outdoor venue
     */
    public Outdoor(String nameIn){
        this.venueName = nameIn;
    }
    /**
     * Constructor for Outdoor class with name and capacity parameters
     * @param nameIn the name of the outdoor venue
     * @param capacityIn the capacity of the outdoor venue
     */
    public Outdoor(String nameIn, int capacityIn){
        this.venueName = nameIn;
        this.venueCapacity = capacityIn;
    }
    /**
     * Constructor for Outdoor class with type, name, and capacity parameters
     * @param typeIn the type of the venue
     * @param nameIn the name of the outdoor venue
     * @param capacityIn the capacity of the outdoor venue
     */
    public Outdoor(String typeIn, String nameIn, int capacityIn){
        this.venueType = typeIn;
        this.venueName = nameIn;
        this.venueCapacity = capacityIn;
    }
    /**
     * @return venueCapacity, the capacity of the outdoor venue
     */
    //Overrides the Venue interface methods
    @Override
    public int getVenueCapacity(){
        return this.venueCapacity;
    }
    /**
     * @param capacityIn sets the venue's capacity
     */
    @Override
    public void setVenueCapacity(int capacityIn){
        this.venueCapacity = capacityIn;
    }
    /**
     * @return venueName, the name of the outdoor venue
     */
    @Override
    public String getVenueName(){
        return this.venueName;
    }
    /**
     * Sets the name of the outdoor venue
     * @param nameIn the name to set for the outdoor venue
     */
    @Override
    public void setVenueName(String nameIn){
        this.venueName = nameIn;
    }
    /**
     * Sets the type of the venue
     * @param typeIn the type to set for the venue
     */
    @Override
    public void setVenueType(String typeIn) {
        this.venueType = typeIn;
    }
    /**
     * @return venueType, the type of the venue
     */
    @Override
    public String getVenueType() {
        return this.venueType;
    }
    /**
     * Prints information about the Outdoor venue
     */
    //Prints information about the Outdoor venue by calling the the Getter methods from the Venue class, uses printf to insert commas into the Venue capacity
    @Override
    public void printInfo(){
        System.out.println("-----Outdoor Venue Information-----");
        System.out.println("Outdoor Venue Location: " + getVenueName());
        System.out.printf("OUtdoor Venue Capacity: %,d%n" ,getVenueCapacity());
    }

  
}

