package com.runticket.model;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

import com.runticket.service.CustomerListStrategy;
import com.runticket.service.EventListStrategy;
import com.runticket.service.FileParsingContext;
import com.runticket.service.UpdateDataStrategy;

/**
 * Singleton class that holds the main data structures for events, customers,
 * and venues. It initializes data from CSV files and provides methods to access
 * and update the data.
 *
 * @author Francisco Garcia
 */
public class Information {

    /**
     * Data structures to hold events, customers, and venues. These are
     * thread-safe to allow concurrent access.
     *
     * @param eventList A map that stores events with their IDs as keys
     * @param customerList A map that stores customers with their usernames as
     * keys
     * @param venueList A map that stores venues with their names as keys
     * @param info The singleton instance of the Information class
     */
    private final ConcurrentHashMap<Integer, Event> eventList = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Customer> customerList = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Venue> venueList = new ConcurrentHashMap<>();
    private static volatile Information info = null;

    /**
     * Private constructor to prevent instantiation from outside the class.
     * Initializes the data structures by extracting data from CSV files.
     */
    private Information() {
        extractData();
    }

    /**
     * Extracts data from CSV files and populates the eventList, customerList,
     * and venueList. Uses the FileParsingContext with different strategies for
     * customers and events.
     */
    private void extractData() {
        FileParsingContext parse = new FileParsingContext();
        parse.setParsingStrategy(new CustomerListStrategy());
        parse.extractCustomerData(new File("src/main/java/com/runticket/CustomerListPA4(Sheet1).csv"), this.customerList);
        parse.setParsingStrategy(new EventListStrategy());
        parse.extractEventData(new File("src/main/java/com/runticket/EventListPA4(Sheet1).csv"), this.eventList, this.venueList);
    }

    /**
     * Gets the customer list.
     *
     * @return customerList
     */
    public ConcurrentHashMap<String, Customer> getCustomerList() {
        return this.customerList;
    }

    /**
     * Gets the event list.
     *
     * @return eventList
     */
    public ConcurrentHashMap<Integer, Event> getEventList() {
        return this.eventList;
    }

    /**
     * Gets the venue list.
     *
     * @return venueList
     */
    public ConcurrentHashMap<String, Venue> getVenueList() {
        return this.venueList;
    }

    /**
     * Adds an event to the event list if the ID does not already exist.
     *
     * @param eventIn the event to add
     * @param newIDIn the ID for the event
     */
    private void addEvent(Event eventIn, int newIDIn) {
        if (this.eventList.contains(newIDIn)) {
            System.out.println("Event ID already exists");
        } else {
            this.eventList.put(newIDIn, eventIn);
        }
    }

    /**
     * Updates the customer and event data using the UpdateDataStrategy.
     */
    private void update() {
        FileParsingContext update = new FileParsingContext();
        update.setParsingStrategy(new UpdateDataStrategy());
        update.updateData(this.customerList, this.eventList);
    }

    /**
     * Public method to trigger the update of customer and event data.
     */
    public void updateData() {
        update();
    }

    /**
     * Gets the singleton instance of the Information class. Initializes the
     * instance if it does not already exist.
     *
     * @return info The singleton instance
     */
    public static Information getInformation() {
        if (info == null) {
            synchronized (Information.class) {
                if (info == null) {
                    info = new Information();
                }
            }
        }
        return info;
    }
}
