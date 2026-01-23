package com.runticket.model;
/**
 * The Venue interface defines the methods that any venue type must implement.
 * It includes methods for getting and setting the venue's capacity, name, and type,
 * as well as a method for printing venue information.
 *
 * @author Francisco Garcia
 */
public interface Venue{
    /**
     * @return venueCapacity, the capacity of the venue
     */
    //The Getter and Setter methods that the classes that implement the Venue interface must implement
    //Gets and Sets the Venue's Capacity
    public int getVenueCapacity();
    /**
     * @param capacityIn sets the venue capacity
     */
    public void setVenueCapacity(int capacityIn);
    //Gets and Sets the Venue's name
    /**
     * @return venueName, the name of the venue
     */
    public String getVenueName();
    /**
     * @param nameIn sets the venue name
     */
    public void setVenueName(String nameIn);
    /**
     * @return venueType, the type of the venue
     */
    //Gets and Sets the Venue's Type
    public String getVenueType();
    /**
     * @param typeIn sets the venue type
    */
    public void setVenueType(String typeIn);
    /**
     * Prints the venue information
     */
    public void printInfo();

}

