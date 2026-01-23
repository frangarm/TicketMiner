package com.runticket.model;
/**
 * The Stadium class represents a stadium venue with a name, type, and capacity.
 * It implements the Venue interface to provide methods for accessing and modifying venue details.
 *
 * @author Francisco Garcia
 */
//Stadium Objects will inherit methods from the Venue class
public class Stadium implements Venue{
    /**
     * The attributes of Stadium
     * @param venueName The Stadium's name
     * @param venueType The Venue's type, in this case Stadium
     * @param venueCapacity The Stadium's capacity
    */
    private String venueName, venueType;
    private int venueCapacity;
    /**
     * Default constructor for Stadium class
     */
    public Stadium(){

    }
    /**
     * Constructor for Stadium class with name parameter
     * @param nameIn the name of the stadium
     */
    public Stadium(String nameIn){
        this.venueName = nameIn;
    }
    /**
     * Constructor for Stadium class with name and capacity parameters
     * @param nameIn the name of the stadium
     * @param capacityIn the capacity of the stadium
     */
    public Stadium(String nameIn, int capacityIn){
        this.venueName = nameIn;
        this.venueCapacity = capacityIn;
    }
    /**
     * Constructor for Stadium class with type, name, and capacity parameters
     * @param typeIn the type of the stadium
     * @param nameIn the name of the stadium
     * @param capacityIn the capacity of the stadium
     */
    public Stadium(String typeIn, String nameIn, int capacityIn){
        this.venueType = typeIn;
        this.venueName = nameIn;
        this.venueCapacity = capacityIn;
    }
    /**
     * @return venueCapacity, the capacity of the stadium
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
     * @return venueName, the name of the stadium
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
    public void setVenueType(String typeIn) {
        this.venueType = typeIn;
    }
    /**
     * @return venueType, the type of the stadium
     */
    @Override
    public String getVenueType() {
        return this.venueType;
    }
    /**
     * Prints information about the Stadium
     */
    //Prints information about the Stadium by calling the Getter methods from the Venue class, uses printf to insert commas into the Venue capacity
    public void printInfo(){
        System.out.println("-----Stadium Information-----");
        System.out.println("Stadium Name: " + getVenueName());
        System.out.printf("Stadium Capacity: %,d%n" ,getVenueCapacity());
    }

    
}

