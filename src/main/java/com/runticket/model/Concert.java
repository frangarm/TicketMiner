package com.runticket.model;
/**
 * The Concert class represents a concert event and extends the Event class.
 * It provides constructors for creating concert events with various details    
 * and a method to print concert information.
 *
 * @author Francisco Garcia
 */

//Concert Objects will inherit methods from the Event class
public class Concert extends Event{  
    /**
     * Default constructor for Concert class
    */
    public Concert(){
     
    }
    /**
     * Constructor for Concert class with name parameter
     * @param nameIn the name of the concert
     */
    public Concert(String nameIn){
        super(nameIn);
    }
    /**
     * Constructor for Concert class with name and ID parameters
     * @param nameIn the name of the concert
     * @param idIn the ID of the concert
     */
    public Concert(String nameIn, int idIn){
        super(nameIn, idIn);
    }
    /**
     * Constructor for Concert class with name, ID, and venue parameters
     * @param nameIn the name of the concert
     * @param idIn the ID of the concert
     * @param venueIn the venue of the concert
     */
    public Concert(String nameIn, int idIn, Venue venueIn){
        super(nameIn, idIn, venueIn);
    }
    /**
     * Prints information about the Concert
     */
    //Prints information about the Concert by calling the Getter methods from the Event class
    public void printInfo(){
        System.out.println("-----Concert Information------");
        System.out.println("Concert Name: " + getEventName());
        System.out.println("Concert ID: " + getEventID());
        System.out.println("Concert Date and Time: " + getEventDate() + " at " + getEventTime());
        System.out.println("Concert Venue: " + getEventVenue().getVenueName());
    }
}

