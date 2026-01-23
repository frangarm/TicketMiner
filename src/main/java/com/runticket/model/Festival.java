package com.runticket.model;
/**
 * The Festival class represents a festival event and extends the Event class.
 * It provides constructors for creating festival events with various details    
 * and a method to print festival information.
 *
 * @author Francisco Garcia
 */
//Festival Objects will inherit methods from the Event class
public class Festival extends Event{
    /**
     * Default constructor for Festival class
     */
    public Festival(){
     
    }
    /**
     * Constructor for Festival class with name parameter
     * @param nameIn the name of the festival
     */
    public Festival(String nameIn){
        super(nameIn);
    }
    /**
     * Constructor for Festival class with name and ID parameters
     * @param nameIn the name of the festival
     * @param idIn the ID of the festival
     */
    public Festival(String nameIn, int idIn){
        super(nameIn, idIn);
    }
    /**
     * Constructor for Festival class with name, ID, and venue parameters
     * @param nameIn the name of the festival
     * @param idIn the ID of the festival
     * @param venueIn the venue of the festival
     */
    public Festival(String nameIn, int idIn, Venue venueIn){
        super(nameIn, idIn, venueIn);
    }
    /**
     * Prints the Festival information
     */
    //Prints the Festival information
    public void printInfo(){
        System.out.println("-----Festival Information------");
        System.out.println("Festival Name: " + getEventName());
        System.out.println("Festival ID: " + getEventID());
        System.out.println("Festival Date and Time: " + getEventDate() + " at " + getEventTime());
        System.out.println("Festival Venue: " + getEventVenue().getVenueName());
    }
}

