package com.runticket.model;
/**
 * The Sport class represents a sport event, inheriting from the Event class.
 * It provides constructors for creating sport events and overrides the printInfo method to display event details.
 *
 * @author Francisco Garcia
 */
//Sport Objects will inherit methods from the Event class
public class Sport extends Event{
    /**
    * Default constructor for Sport class
    */
    public Sport(){
     
    }
    /**
     * Constructor for Sport class with name parameter
     * @param nameIn the name of the sport event
     */
    public Sport(String nameIn){
        super(nameIn);
    }
    /**
     * Constructor for Sport class with name and ID parameters
     * @param nameIn the name of the sport event
     * @param idIn the ID of the sport event
     */
    public Sport(String nameIn, int idIn){
        super(nameIn, idIn);
    }
    /**
     * Constructor for Sport class with name, ID, and venue parameters
     * @param nameIn the name of the sport event
     * @param idIn the ID of the sport event
     * @param venueIn the venue of the sport event
     */
    public Sport(String nameIn, int idIn, Venue venueIn){
        super(nameIn, idIn, venueIn);
    }
    /**
     * Prints information about the Sport Event
     */
    @Override
    //Prints information about the Sport Event by calling the Getter methods from the Event class
    public void printInfo(){
        System.out.println("-----Sport Event Information------");
        System.out.println("Sport Event Name: " + getEventName());
        System.out.println("Sport Event ID: " + getEventID());
        System.out.println("Sport Event Date and Time: " + getEventDate() + " at " + getEventTime());
        System.out.println("Sport Event Venue: " + getEventVenue().getVenueName());
    }
}

