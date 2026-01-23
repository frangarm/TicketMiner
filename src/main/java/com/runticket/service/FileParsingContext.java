package com.runticket.service;
import java.io.File;
import java.util.Map;

import com.runticket.model.Customer;
import com.runticket.model.Event;
import com.runticket.model.Venue;
/**
 * The FileParsingContext class utilizes the Strategy design pattern to handle different file parsing strategies.
 * It allows for dynamic selection of parsing strategies for extracting event and customer data, as well as updating data.
 *
 * @author Francisco Garcia
 */
public class FileParsingContext {
    private FileParsingStrategy parsingStrategy;
    /**
    * Default constructor for FileParsingContext class
    */
    public FileParsingContext(){
        
    }
    /**
     * Constructor for FileParsingContext class with strategy parameter
     * @param strategyIn the file parsing strategy to be used
     */
    public FileParsingContext(FileParsingStrategy strategyIn){
        this.parsingStrategy = strategyIn;
    }
    /**
     * Sets the file parsing strategy
     * @param strategyIn the file parsing strategy to be set
     */
    public void setParsingStrategy(FileParsingStrategy strategyIn){
        this.parsingStrategy = strategyIn;
    }
    /**
     * Extracts event data from a file using the current parsing strategy
     * @param fileIn the file containing event data
     * @param mapIn the map to store extracted event data
     * @param venueListIn the list of venues for reference
     */
    public void extractEventData(File fileIn, Map<Integer, Event> mapIn, Map<String, Venue> venueListIn){
        this.parsingStrategy.extractEventData(fileIn, mapIn, venueListIn);
    }
    /**
     * Extracts customer data from a file using the current parsing strategy
     * @param fileIn the file containing customer data
     * @param mapIn the map to store extracted customer data
     */
    public void extractCustomerData(File fileIn, Map<String, Customer> mapIn){
        this.parsingStrategy.extractCustomerData(fileIn, mapIn);
    }
    /**
     * Updates data using the current parsing strategy
     * @param mapIn the map of customers to be updated
     * @param eventMapIn the map of events to be updated
     */
    public void updateData(Map<String, Customer> mapIn, Map<Integer, Event> eventMapIn){
        this.parsingStrategy.updateData( mapIn, eventMapIn);
    }
}