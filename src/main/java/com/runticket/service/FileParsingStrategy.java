package com.runticket.service;

import java.io.File;
import java.util.Map;

import com.runticket.model.Customer;
import com.runticket.model.Event;
import com.runticket.model.Venue;
/**
 * The FileParsingStrategy interface defines methods for parsing event and customer data from files
 * and updating data. It is implemented by different parsing strategy classes to provide specific parsing logic.
 *
 * @author Francisco Garcia
 */
public interface FileParsingStrategy {
     /**
      * Extracts event data from a file
      * @param fileIn
      * @param eventMapIn
      * @param venueListIn
      */
     public void extractEventData(File fileIn, Map<Integer, Event> eventMapIn, Map<String, Venue> venueListIn);
     /**
      * Extracts customer data from a file
      * @param fileIn
      * @param customerMapIn
      */
     public void extractCustomerData(File fileIn, Map<String, Customer> customerMapIn);
     /**
      * Updates data to a file
      * @param customerMapIn
      * @param eventMapIn
      */
     public void updateData(Map<String, Customer> customerMapIn, Map<Integer, Event> eventMapIn);
}
