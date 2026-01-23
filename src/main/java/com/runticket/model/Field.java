package com.runticket.model;
/**
 * The Field class represents a field venue with a name, type, and capacity.
 * It implements the Venue interface to provide methods for accessing and modifying venue details.
 *
 * @author Francisco Garcia
 */
public class Field implements Venue{
    /**
     * The attributes of Field
     * @param venueName The Field's name
     * @param venueType The Venue's type, in this case Field
     * @param venueCapacity The Field's capacity
     */
    private String venueName, venueType;
    private int venueCapacity;
    /**
     * Default constructor for Field class
     */
    public Field(){

    }
    /**
     * Constructor for Field class with name parameter
     * @param nameIn the name of the field
     */
    public Field(String nameIn){
        this.venueName = nameIn;
    }
    /**
     * Constructor for Field class with name and capacity parameters
     * @param nameIn the name of the field
     * @param capacityIn the capacity of the field
     */
    public Field(String nameIn, int capacityIn){
        this.venueName = nameIn;
        this.venueCapacity = capacityIn;
    }
    /**
     * Constructor for Field class with type, name, and capacity parameters
     * @param typeIn the type of the venue
     * @param nameIn the name of the field
     * @param capacityIn the capacity of the field
     */
    public Field(String typeIn, String nameIn, int capacityIn){
        this.venueType = typeIn;
        this.venueName = nameIn;
        this.venueCapacity = capacityIn;
    }
    /**
     * @return venueCapacity, the capacity of the field
     */
    //Overrides the methods of the Venue interface
    @Override
    public int getVenueCapacity(){
        return this.venueCapacity;
    }
    /**
     * sets the venue's capacity
     * @param capacityIn the capacity to set for the field
     */
    @Override
    public void setVenueCapacity(int capacityIn){
        this.venueCapacity = capacityIn;
    }
    /**
     * @return venueName, the name of the field
     */
    @Override
    public String getVenueName(){
        return this.venueName;
    }
    /**
     * sets the venue's name
     * @param nameIn the name to set for the field
     */
    @Override
    public void setVenueName(String nameIn){
        this.venueName = nameIn;
    }
    /**
     * sets the venue's type
     * @param typeIn the type to set for the field
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
     * Prints information about the Field venue
     */
    //Prints information about the Outdoor venue by calling the the Getter methods from the Venue class, uses printf to insert commas into the Venue capacity
    public void printInfo(){
        System.out.println("-----Field Information-----");
        System.out.println("Field Name: " + getVenueName());
        System.out.printf("Field Capacity: %,d%n" ,getVenueCapacity());
    }

 
}

