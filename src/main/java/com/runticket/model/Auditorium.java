package com.runticket.model;
/**
 * The Auditorium class represents an auditorium venue with a name, type, and capacity.
 * It implements the Venue interface to provide methods for accessing and modifying venue details.
 *
 * @author Francisco Garcia
 */

//Auditorium Objects will inherit methods from the Venue class
public class Auditorium implements Venue{
    /**
     * @param venueName the name of the arena
     * @param venueType the type of the venue, in this case Auditorium
     * @param venueCapacity the Auditorium's capacity
    */
    private String venueName, venueType;
    private int venueCapacity;
    /**
     * Default constructor for Auditorium class
     */
    public Auditorium(){

    }
    /**
     * Constructor for Auditorium class with name parameter
     * @param nameIn the name of the auditorium
     */
    public Auditorium(String nameIn){
        this.venueName = nameIn;
    }
    /**
     * Constructor for Auditorium class with name and capacity parameters
     * @param nameIn the name of the auditorium
     * @param capacityIn the capacity of the auditorium
     */
    public Auditorium(String nameIn, int capacityIn){
        this.venueName = nameIn;
        this.venueCapacity = capacityIn;
    }
    /**
     * Constructor for Auditorium class with type, name, and capacity parameters
     * @param typeIn the type of the auditorium
     * @param nameIn the name of the auditorium
     * @param capacityIn the capacity of the auditorium
     */
    public Auditorium(String typeIn, String nameIn, int capacityIn){
        this.venueType = typeIn;
        this.venueName = nameIn;
        this.venueCapacity = capacityIn;
    }
    /**
     * @return venueCapacity, the capacity of the auditorium
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
     * @return venueName, the name of the auditorium
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
     * @return venueType, the venue's type
     */
    @Override
    public String getVenueType(){
        return this.venueType;
    }
    /**
     * Prints information about the Auditorium
     */
    @Override
    //Prints information about the Auditorium by calling the Getter methods from the Venue class, uses printf to insert commas into the Venue capacity
    public void printInfo(){
        System.out.println("-----Auditorium Information-----");
        System.out.println("Auditorium Name: " + getVenueName());
        System.out.printf("Auditorium Capacity: %,d%n" ,getVenueCapacity());
    }
}

