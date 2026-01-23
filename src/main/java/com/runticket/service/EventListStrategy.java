package com.runticket.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.runticket.model.Concert;
import com.runticket.model.Customer;
import com.runticket.model.Event;
import com.runticket.model.Festival;
import com.runticket.model.Sport;
import com.runticket.model.Venue;
/**
 * The EventListStrategy class implements the FileParsingStrategy interface
 * to extract event data from a file and populate event and venue lists.
 * It provides methods to extract event data, while customer data extraction
 * and data updating are not supported in this strategy.
 *
 * @author Francisco Garcia
 */
public class EventListStrategy implements FileParsingStrategy{
    /**
     * Extracts event data from a file and populates the event and venue lists.
     * @param fileIn the file containing event data
     * @param scanEventList the Map to populate with Event objects
     * @param venueListIn the Map to populate with Venue objects
     */
    //Will retrieve Event information from a cvs file, then store the information in a HashMap of the different Events where an Event's ID is the Key of the Event
    //Will also store the different Venues on a HashMap, where the Venue's name will serve as the key
    //Implements the Event Parsing Strategy
    @Override
    public void extractEventData(File fileIn, Map<Integer, Event> scanEventList, Map<String, Venue> venueListIn){
     try{
            HashMap<String, Integer> sortedOrder = new HashMap<>();
            Scanner scanner = new Scanner(fileIn);
            boolean isHeader = true;
            String nextLine;
            String[] fieldOrder;
            while(scanner.hasNextLine()){
                nextLine = scanner.nextLine();
                //The first line, the header, of the file is added to the sortedOrder HashMap, which will store the order of the fields of the file, with the event field name
                //seving as the key and the index of the field serving as the value
                if(isHeader){
                    fieldOrder = nextLine.split(","); 
                    int index = 0;
                    for(String parts : fieldOrder ){
                        String key = parts.toLowerCase().trim();
                        key = key.replaceAll("[^A-Za-z ]+", "");    
                        sortedOrder.put(key, index);
                        index++;
                    }
                    isHeader = false;
                    continue;
                }
                //Each line of the file is broken up and stored onto a String array 
                String[] lineParts = nextLine.split(",");
                //Switch case creates new Event objects based on the event type
                //The sortedOrder HashMap will help find the index of each field on the lineParts array
                switch(lineParts[sortedOrder.get("event type")]){
                    case "Sport" -> {
                        //Creates a new Sport object, stores the information of the String array lineParts into the information of the Sport object
                        //Creates a new Venue according to the Venue's type, stores it on the venueListIn HashMap
                        //Sets the Sport Event's Name and ID
                        Venue newVenue = VenueFactory.setVenue(lineParts[sortedOrder.get("venue type")], lineParts[sortedOrder.get("venue name")], Integer.parseInt(lineParts[sortedOrder.get("capacity")]));
                        venueListIn.put(newVenue.getVenueName(), newVenue);
                        Sport newSport = new Sport(lineParts[sortedOrder.get("name")], Integer.parseInt(lineParts[sortedOrder.get("event id")]), newVenue);
                        //Sets the total cost of the event
                        newSport.setEventCost(Double.parseDouble(lineParts[sortedOrder.get("cost")]));
                        if (lineParts[sortedOrder.get("fireworks planned")].toLowerCase().equals("yes")) {
                            newSport.setFireworks(true);
                            if(!lineParts[sortedOrder.get("fireworks cost")].equals("")){
                                newSport.setFireworksCost(Double.parseDouble(lineParts[sortedOrder.get("fireworks cost")]));
                            }
                            else{
                                newSport.setFireworksCost(0);
                            }
                        }
                        else{
                            newSport.setFireworks(false);
                            newSport.setFireworksCost(0);
                        }
                        newSport.setTaxRevenue(0);
                        newSport.setDiscountCosts(0);
                        newSport.setFees(0, 0, 0);
                        newSport.setEventType(lineParts[sortedOrder.get("event type")]);
                        //Sets the Sport Event's Time and Date
                        newSport.setEventTime(lineParts[sortedOrder.get("time")]);
                        newSport.setEventDate(lineParts[sortedOrder.get("date")]);
                        //Sets the Sport Event's Ticket Prices
                        newSport.setTickets("VIP",Double.parseDouble(lineParts[sortedOrder.get("vip price")]), (Integer.parseInt(lineParts[sortedOrder.get("vip pct")]) / 100.0));
                        newSport.setTickets("Gold",Double.parseDouble(lineParts[sortedOrder.get("gold price")]), (Integer.parseInt(lineParts[sortedOrder.get("gold pct")]) / 100.0));
                        newSport.setTickets("Silver",Double.parseDouble(lineParts[sortedOrder.get("silver price")]), (Integer.parseInt(lineParts[sortedOrder.get("silver pct")]) / 100.0));
                        newSport.setTickets("Bronze",Double.parseDouble(lineParts[sortedOrder.get("bronze price")]), (Integer.parseInt(lineParts[sortedOrder.get("bronze pct")]) / 100.0));
                        newSport.setTickets("General Admission",Double.parseDouble(lineParts[sortedOrder.get("general admission price")]), (Integer.parseInt(lineParts[sortedOrder.get("general admission pct")]) / 100.0));
                        //Creates an object according to the type of Venue that the event is being held at, creates a default venue type in the default case
                        newSport.setSeats();
                        newSport.setExpectedRevenue(newSport.expectedProfit());
                        //Stores the Sport object onto the eventList HashMap, the event's ID serves as the key 
                        Logger.getLogger().Log("Event " + newSport.getEventName() + " ID(" + newSport.getEventID()+ ") added to eventList");
                        scanEventList.put(newSport.getEventID(), newSport);
                    }

                    case "Concert" -> {
                        //Creates a new Sport object, stores the information of the String array lineParts into the information of the Sport object
                        //Creates a new Venue according to the Venue's type, stores it on the venueListIn HashMap
                        //Sets the Sport Event's Name and ID
                        Venue newVenue = VenueFactory.setVenue(lineParts[sortedOrder.get("venue type")], lineParts[sortedOrder.get("venue name")], Integer.parseInt(lineParts[sortedOrder.get("capacity")]));
                        venueListIn.put(newVenue.getVenueName(), newVenue);
                        Concert newConcert = new Concert(lineParts[sortedOrder.get("name")], Integer.parseInt(lineParts[sortedOrder.get("event id")]), newVenue);
                        //Sets the total cost of the event
                        newConcert.setEventCost(Double.parseDouble(lineParts[sortedOrder.get("cost")]));
                        if (lineParts[sortedOrder.get("fireworks planned")].toLowerCase().equals("yes")) {
                            newConcert.setFireworks(true);
                           if(!lineParts[sortedOrder.get("fireworks cost")].equals("")){
                                newConcert.setFireworksCost(Double.parseDouble(lineParts[sortedOrder.get("fireworks cost")]));
                            }
                            else{
                                newConcert.setFireworksCost(0);
                            }
                        }
                        else{
                            newConcert.setFireworks(false);
                            newConcert.setFireworksCost(0);
                        }
                        newConcert.setEventType(lineParts[sortedOrder.get("event type")]);
                        newConcert.setTaxRevenue(0);
                        newConcert.setDiscountCosts(0);
                        newConcert.setFees(0,0,0);
                        //Sets the Sport Event's Time and Date
                        newConcert.setEventTime(lineParts[sortedOrder.get("time")]);
                        newConcert.setEventDate(lineParts[sortedOrder.get("date")]);
                        //Sets the Sport Event's Ticket Prices
                        newConcert.setTickets("VIP",Double.parseDouble(lineParts[sortedOrder.get("vip price")]), (Integer.parseInt(lineParts[sortedOrder.get("vip pct")]) / 100.0));
                        newConcert.setTickets("Gold",Double.parseDouble(lineParts[sortedOrder.get("gold price")]), (Integer.parseInt(lineParts[sortedOrder.get("gold pct")]) / 100.0));
                        newConcert.setTickets("Silver",Double.parseDouble(lineParts[sortedOrder.get("silver price")]), (Integer.parseInt(lineParts[sortedOrder.get("silver pct")]) / 100.0));
                        newConcert.setTickets("Bronze",Double.parseDouble(lineParts[sortedOrder.get("bronze price")]), (Integer.parseInt(lineParts[sortedOrder.get("bronze pct")]) / 100.0));
                        newConcert.setTickets("General Admission",Double.parseDouble(lineParts[sortedOrder.get("general admission price")]), (Integer.parseInt(lineParts[sortedOrder.get("general admission pct")]) / 100.0));
                        //Creates an object according to the type of Venue that the event is being held at, creates a default venue type in the default case
                        newConcert.setSeats();
                        newConcert.setExpectedRevenue(newConcert.expectedProfit());
                        //Stores the Sport object onto the eventList HashMap, the event's ID serves as the key 
                        Logger.getLogger().Log("Event " + newConcert.getEventName() + " ID(" + newConcert.getEventID()+ ") added to eventList");
                        scanEventList.put(newConcert.getEventID(), newConcert);
                    }

                      case "Festival" -> {
                        //Creates a new Sport object, stores the information of the String array lineParts into the information of the Sport object
                        //Creates a new Venue according to the Venue's type, stores it on the venueListIn HashMap
                        //Sets the Sport Event's Name and ID
                        Venue newVenue = VenueFactory.setVenue(lineParts[sortedOrder.get("venue type")], lineParts[sortedOrder.get("venue name")], Integer.parseInt(lineParts[sortedOrder.get("capacity")]));
                        venueListIn.put(newVenue.getVenueName(), newVenue);
                        Festival newFestival = new Festival(lineParts[sortedOrder.get("name")], Integer.parseInt(lineParts[sortedOrder.get("event id")]), newVenue);
                        //Sets the total cost of the event
                        newFestival.setEventCost(Double.parseDouble(lineParts[sortedOrder.get("cost")]));
                        if (lineParts[sortedOrder.get("fireworks planned")].toLowerCase().equals("yes")) {
                            newFestival.setFireworks(true);
                            if(!lineParts[sortedOrder.get("fireworks cost")].equals("")){
                                newFestival.setFireworksCost(Double.parseDouble(lineParts[sortedOrder.get("fireworks cost")]));
                            }
                            else{
                                newFestival.setFireworksCost(0);
                            }
                        }
                        else{
                            newFestival.setFireworks(false);
                            newFestival.setFireworksCost(0);
                        }
                        newFestival.setEventType(lineParts[sortedOrder.get("event type")]);
                        newFestival.setTaxRevenue(0);
                        newFestival.setDiscountCosts(0);
                        newFestival.setFees(0, 0, 0);
                        //Sets the Sport Event's Time and Date
                        newFestival.setEventTime(lineParts[sortedOrder.get("time")]);
                        newFestival.setEventDate(lineParts[sortedOrder.get("date")]);
                        //Sets the Sport Event's Ticket Prices
                        newFestival.setTickets("VIP",Double.parseDouble(lineParts[sortedOrder.get("vip price")]), (Integer.parseInt(lineParts[sortedOrder.get("vip pct")]) / 100.0));
                        newFestival.setTickets("Gold",Double.parseDouble(lineParts[sortedOrder.get("gold price")]), (Integer.parseInt(lineParts[sortedOrder.get("gold pct")]) / 100.0));
                        newFestival.setTickets("Silver",Double.parseDouble(lineParts[sortedOrder.get("silver price")]), (Integer.parseInt(lineParts[sortedOrder.get("silver pct")]) / 100.0));
                        newFestival.setTickets("Bronze",Double.parseDouble(lineParts[sortedOrder.get("bronze price")]), (Integer.parseInt(lineParts[sortedOrder.get("bronze pct")]) / 100.0));
                        newFestival.setTickets("General Admission",Double.parseDouble(lineParts[sortedOrder.get("general admission price")]), (Integer.parseInt(lineParts[sortedOrder.get("general admission pct")]) / 100.0));
                        //Creates an object according to the type of Venue that the event is being held at, creates a default venue type in the default case
                        newFestival.setSeats();
                        newFestival.setExpectedRevenue(newFestival.expectedProfit());
                        //Stores the Sport object onto the eventList HashMap, the event's ID serves as the key 
                        Logger.getLogger().Log("Event " + newFestival.getEventName() + " ID(" + newFestival.getEventID()+ ") added to eventList");
                        scanEventList.put(newFestival.getEventID(), newFestival);
                    }

                   

                }
   
            }
            scanner.close();           
        }
        //Will catch the FileNotFoundException
        catch(FileNotFoundException e){
            System.out.println("Error: " + e.getMessage());
        }
        catch (NumberFormatException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    /**
     * Extracts customer data - not applicable for this strategy, and so it won't do anything but display a message saying that this
     * strategy cannot extract Customer data
     * @param fileIn the file containing customer data
     */
    //Won't do anything
    @Override
    public void extractCustomerData(File fileIn, Map<String, Customer> mapIn){
        System.out.println("Cannot extract Customer Data");
    }
    /**
     * Updates customer and event data - not applicable for this strategy, and so it won't do anything but display a message saying that this
     * strategy cannot update data.
     * @param mapIn the Map of Customer objects
     * @param eventMapIn the Map of Event objects
     */
    //Won't do anything
    @Override
    public void updateData(Map<String, Customer> mapIn, Map<Integer, Event> eventMapIn){
        System.out.println("Cannot update data using this strategy");
    }
}
