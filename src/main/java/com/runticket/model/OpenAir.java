package com.runticket.model;
/**
 * The OpenAir class represents an open air venue with a name, type, and capacity.
 * It implements the Venue interface to provide methods for accessing and modifying venue details.
 *
 * @author Francisco Garcia
 */
//OpenAir Objects will inherit methods from the Venue class
public class OpenAir implements Venue{
    /**
     * The attributes of OpenAir
     * @param venueName The OpenAir Venue's name
     * @param venueType The Venue's type, in this case OpenAir
     * @param venueCapacity The OpenAir Venue's capacity
     */
    private String venueName, venueType;
    private int venueCapacity;
    /**
     * Default constructor for OpenAir class
     */
    public OpenAir(){

    }
    /**
     * Constructor for OpenAir class with name parameter
     * @param nameIn the name of the open air venue
     */
    public OpenAir(String nameIn){
        this.venueName = nameIn;
    }
    /**
     * Constructor for OpenAir class with name and capacity parameters
     * @param nameIn the name of the open air venue
     * @param capacityIn the capacity of the open air venue
     */
    public OpenAir(String nameIn, int capacityIn){
        this.venueName = nameIn;
        this.venueCapacity = capacityIn;
    }
    /**
     * Constructor for OpenAir class with type, name, and capacity parameters
     * @param typeIn the type of the venue
     * @param nameIn the name of the open air venue
     * @param capacityIn the capacity of the open air venue
     */
    public OpenAir(String typeIn, String nameIn, int capacityIn){
        this.venueType = typeIn;
        this.venueName = nameIn;
        this.venueCapacity = capacityIn;
    }
    /**
     * @return venueCapacity, the capacity of the open air venue
     */
    //Overrides the methods of the Venue interface
    @Override
    public int getVenueCapacity(){
        return this.venueCapacity;
    }
    /**
     * @param capacityIn the capacity to set for the open air venue
     */
    @Override
    public void setVenueCapacity(int capacityIn){
        this.venueCapacity = capacityIn;
    }
    /**
     * @return venueName, the name of the open air venue
     */
    @Override
    public String getVenueName(){
        return this.venueName;
    }
    /**
     * Sets the name of the open air venue
     * @param nameIn the name to set for the open air venue
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
     * Prints information about the Open Air Venue
     */
    //Prints information about the Open Air Venue by calling the Getter methods from the Venue class, uses printf to insert commas into the Venue capacity
    public void printInfo(){
        System.out.println("-----Open Air Venue Information-----");
        System.out.println("Open Air Venue Location: " + getVenueName());
        System.out.printf("Capacity: %,d%n" ,getVenueCapacity());
    }

   
}

