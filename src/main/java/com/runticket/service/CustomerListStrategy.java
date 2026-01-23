package com.runticket.service;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.runticket.model.Customer;
import com.runticket.model.Event;
import com.runticket.model.Venue;
/**
 * The CustomerListStrategy class implements the FileParsingStrategy interface
 * to extract customer data from a file and populate a HashMap of Customer objects.
 *
 * @author Francisco Garcia
 */
public class CustomerListStrategy implements FileParsingStrategy{
    /**
     * Extracts customer data from a file and populates the customerList HashMap
     * @param fileIn the file containing customer data
     * @param customerList the Map to populate with Customer objects
     */
    //Will retrieve Customer information from a cvs file, then store the information in a HashMap of the different Customers where an Customer's ID is the Key of the Event
    //Implements the Customer Parsing Strategy
    @Override
    public void extractCustomerData(File fileIn, Map<String, Customer> customerList){
        HashMap<String, Integer> sortedOrder = new HashMap<>();
        try {
            Scanner scanner = new Scanner(fileIn);
            boolean isHeader = true;
            String nextLine;
            String[] fieldOrder;
            while(scanner.hasNextLine()){
                nextLine = scanner.nextLine();
                //The first line of the file, the header, is skipped
                if(isHeader){
                        //Stores the header of the file into fieldOrder
                        fieldOrder = nextLine.split(","); 
                        int index = 0;
                    for(String parts : fieldOrder ){
                        String key = parts.toLowerCase().trim();
                        //Removes any character is not a letter
                        key = key.replaceAll("[^A-Za-z ]+", "");  
                        //The fields of the header file will serve as a key in sortedOrder, the index where the field is on the array will serve as the value   
                        sortedOrder.put(key, index);
                        index++;
                    }
                    isHeader = false;
                    continue;
                }
                //Each line of the file is broken up and stored into a String array
                String[] lineParts = nextLine.split(",");
                //newCustomer will be assigned the information from the file stored in the array, the customer's ID, first and last names, etc. 
                Customer newCustomer = new Customer();
                //The keys of the sortedOrder HashMap will help find the index of each field, such as the ID helping find what the index of the Customer's ID is on the lineParts array
                //Sets the Customer's ID
                newCustomer.setCustomerID(Integer.parseInt(lineParts[sortedOrder.get("id")]));
                //Sets the Customer's First and Last Names
                newCustomer.setFirstName(lineParts[sortedOrder.get("first name")]);
                newCustomer.setLastName(lineParts[sortedOrder.get("last name")]);
                //Sets the Customer's balance
                newCustomer.setBalance(Double.parseDouble(lineParts[sortedOrder.get("money available")]));
                //Sets the number of concerts the Customer has purchased
                newCustomer.setConcertsPurchased(Integer.parseInt(lineParts[sortedOrder.get("concerts purchased")]));
                newCustomer.setTransactionCount(0);
                //Sets the member status of the Customer
                newCustomer.setMemberStatus(Boolean.parseBoolean(lineParts[sortedOrder.get("ticketminer membership")]));
                //Sets the Customer's Username and Password
                newCustomer.setUsername(lineParts[sortedOrder.get("username")]);
                newCustomer.setPassword(lineParts[sortedOrder.get("password")]);
                //newCustomer will be stored into the customerList HashMap, the customer's ID will serve as the key
                Logger.getLogger().Log("Customer " + newCustomer.getFirstName() + " " + newCustomer.getLastName() + " added to customerList");
                customerList.put(newCustomer.getUsername(), newCustomer);
            }
            scanner.close();
        } 
        //Will catch the FileNotFoundException
        catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    /**
     * Extracts event data - not applicable for this strategy and so it won't do anything but display a message that says that it cannot extract
     * Event Data.
     * @param fileIn the file containing event data
     * @param mapIn the Map to populate with Event objects
     * @param venueListIn the Map of Venue objects
     */
    //Won't do anything
    @Override
    public void extractEventData(File fileIn, Map<Integer, Event> mapIn, Map<String, Venue> venueListIn){
        System.out.println("Cannot extract Event Data");
    }
    /**
     * Updates customer and event data - not applicable for this strategy and so it won't do anything but display a message that says that it cannot update
     * data.
     * @param mapIn the Map of Customer objects
     * @param eventMapIn the Map of Event objects
     */
    //Won't do anything
    @Override
    public void updateData(Map<String, Customer> mapIn, Map<Integer, Event> eventMapIn){
        System.out.println("Cannot update data using this strategy");
    }
}