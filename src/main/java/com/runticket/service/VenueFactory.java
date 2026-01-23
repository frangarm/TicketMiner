package com.runticket.service;

import com.runticket.model.Venue;
import com.runticket.model.Arena;
import com.runticket.model.Auditorium;
import com.runticket.model.Field;
import com.runticket.model.OpenAir;
import com.runticket.model.Outdoor;
import com.runticket.model.Stadium;
/**
 * The VenueFactory class is responsible for creating Venue objects based on the provided type, name, and capacity.
 * It uses the Factory Design Pattern to encapsulate the instantiation logic for different venue types.
 *
 * @author Francisco Garcia
 */
public class VenueFactory {
    /**
     * Creates and returns a Venue object based on the specified type, name, and capacity.
     * @param typeIn the type of the venue (e.g., "ARENA", "AUDITORIUM", "FIELD", "OPEN AIR", "OUTDOOR", "STADIUM")
     * @param nameIn the name of the venue
     * @param capacityIn the capacity of the venue
     * @return a Venue object corresponding to the specified type
     */
    //Will receive a Venue's Type, Name, and capcity and return a Venue according the Venue type
    public static Venue setVenue(String typeIn, String nameIn, int capacityIn){
        Venue newVenue;
        switch(typeIn.toUpperCase()){
            case "ARENA" -> newVenue = new Arena(typeIn, nameIn, capacityIn);
            case "AUDITORIUM" -> newVenue= new Auditorium(typeIn, nameIn, capacityIn);
            case "FIELD" -> newVenue= new Field(typeIn, nameIn, capacityIn);
            case "OPEN AIR" -> newVenue= new OpenAir(typeIn, nameIn, capacityIn);
            case "OUTDOOR" -> newVenue= new Outdoor(typeIn, nameIn, capacityIn);
            default -> newVenue = new Stadium(typeIn, nameIn, capacityIn);
        } 

        return newVenue;
    }
}

