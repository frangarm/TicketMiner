package com.runticket.model;
//Arena Objects will inherit methods from the Venue class
/**
 * The Arena class represents an arena venue with a name, type, and capacity.
 * It implements the Venue interface to provide methods for accessing and modifying venue details.
 *
 * @author Francisco Garcia
 */
public class Arena implements Venue{
    /**
     * @param venueName the name of the arena
     * @param venueType the type of the venue, in this case Arena
     * @param venueCapacity the Arena's capacity
    */
    private String venueName, venueType;
    private int venueCapacity;
    /**
     * Default constructor for Arena class
     */
    public Arena(){

    }
    /**
     * Constructor for Arena class with name parameter
     * @param nameIn the name of the arena
     */
    public Arena(String nameIn){
        this.venueName = nameIn;
    }
    /**
     * Constructor for Arena class with name and capacity parameters
     * @param nameIn the name of the arena
     * @param capacityIn the capacity of the arena
     */
    public Arena(String nameIn, int capacityIn){
        this.venueName = nameIn;
        this.venueCapacity = capacityIn;
    }
    /**
     * Constructor for Arena class with type, name, and capacity parameters
     * @param typeIn the type of the arena
     * @param nameIn the name of the arena
     * @param capacityIn the capacity of the arena
     */
    public Arena(String typeIn, String nameIn, int capacityIn){
        this.venueType = typeIn;
        this.venueName = nameIn;
        this.venueCapacity = capacityIn;
    }
    /**
     * @return venueCapacity
     */
    //Overrides the methods of the Venue interface
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
     * @return venueName
     */
    @Override
    public String getVenueName(){
        return this.venueName;
    }
    /**
     * @param nameIn sets the venue's name
     */
    @Override
    public void setVenueName(String nameIn){
        this.venueName = nameIn;
    }
    /**
     * @param typeIn sets the venue's type
     */
    @Override
    public void setVenueType(String typeIn){
        this.venueType = typeIn;
    }
    /**
     * @return venueType
     */
    @Override
    public String getVenueType(){
        return this.venueType;
    }
    /**
     * Prints information about the Arena
     */
    @Override
    //Prints information about the Arena by calling the Getter methods from the Venue class, uses printf to insert commas into the Venue capacity
    public void printInfo(){
        System.out.println("-----Arena Information-----");
        System.out.println("Arena Name: " + getVenueName());
        System.out.printf("Arena Capacity: %,d%n" , getVenueCapacity());
    }
}

