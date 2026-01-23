package com.runticket.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import com.runticket.model.Customer;
import com.runticket.model.Event;
import com.runticket.model.Venue;
/**
 * The UpdateDataStrategy class implements the FileParsingStrategy interface to provide
 * functionality for updating event and customer data to files.
 * It does not support extracting data from files.
 *
 * @author Francisco Garcia
 */
public class UpdateDataStrategy implements FileParsingStrategy{
    /**
     * Won't do anything since this strategy only updates data, 
     */
    @Override
    //Won't do anything
    public void extractEventData(File fileIn, Map<Integer, Event> mapIn, Map<String, Venue> venueListIn){
        System.out.println("Cannot extract Event Data using this strategy");
    }
    /**
     * Won't do anything since this strategy only updates data
     */
    @Override
    //Won't do anything
    public void extractCustomerData(File fileIn, Map<String, Customer> mapIn){
        System.out.println("Cannot extract Customer Data using this strategy");
    }
    /**
     * Updates event and customer data to new CSV files
     * @param customerList the map of customers to be updated
     * @param eventList the map of events to be updated
     */
    @Override
    //Updates both the Event List and Customer List csv files with new data
    public void updateData(Map<String, Customer> customerList, Map<Integer, Event> eventList){
        String headerLine;
        //Updates the Event List, creates a new csv file for the updated values
        try (BufferedWriter eventWriter = new BufferedWriter(new FileWriter(new File("src/main/java/com/runticket/UpdatedEventListPA4(Sheet1).csv")))) {
            //Writes a new header line with the needed fields to the file
            headerLine = "Event ID,Event Type,Name,Date,Time,VIP Price,Gold Price,Silver Price,Bronze Price,General Admission Price,Venue Name,Pct Seats Unavailable,Venue Type,Capacity,Cost,VIP Pct,Gold Pct,Silver Pct,Bronze Pct,General Adminission Pct,Reserved Extra Pct,Fireworks,Fireworks Cost,VIP Seats Sold,Gold Seats Sold,Silver Seats Sold,Bronze Seats Sold,General Admission Seats Sold,Total VIP Revenue,Total Gold Revenue,Total Silver Revenue,Total Bronze Revenue,Total General Admission Revenue";
            eventWriter.write(headerLine);
            eventWriter.newLine();
            String formattedEventData;
            //Iterates through the HashMap and stores the infromation of each event into an array, which is then written to the file
            for(Integer key : eventList.keySet()){
                String[] updateEvents = new String[33];
                updateEvents[0] = Integer.toString(eventList.get(key).getEventID());
                updateEvents[1] = eventList.get(key).getEventType();
                updateEvents[2] = eventList.get(key).getEventName();
                updateEvents[3] = eventList.get(key).getEventDate();
                updateEvents[4] = eventList.get(key).getEventTime();
                updateEvents[5] = Double.toString(eventList.get(key).getTicketPrice("VIP"));
                updateEvents[6] = Double.toString(eventList.get(key).getTicketPrice("Gold"));
                updateEvents[7] = Double.toString(eventList.get(key).getTicketPrice("Silver"));
                updateEvents[8] = Double.toString(eventList.get(key).getTicketPrice("Bronze"));
                updateEvents[9] = Double.toString(eventList.get(key).getTicketPrice("General Adminission"));
                updateEvents[10] = eventList.get(key).getEventVenue().getVenueName();
                updateEvents[11] = "0";
                updateEvents[12] = eventList.get(key).getEventVenue().getVenueType();
                updateEvents[13] = Integer.toString(eventList.get(key).getEventVenue().getVenueCapacity());
                formattedEventData = String.format("%.2f", eventList.get(key).getEventCost());
                updateEvents[14] = formattedEventData;
                updateEvents[15] = Double.toString(0.45 * 100);
                updateEvents[16] = Double.toString(0.20 * 100);
                updateEvents[17] = Double.toString(0.15 * 100);
                updateEvents[18] = Double.toString(0.10 * 100);
                updateEvents[19] = Double.toString(0.05 * 100);
                updateEvents[20] = "5";
                if(eventList.get(key).getFireworks()) {
                    updateEvents[21] = "Yes";
                    updateEvents[22] = Double.toString(eventList.get(key).getFireworkCost());
                }
                else{
                    updateEvents[21] = "No";
                    updateEvents[22] = "0";
                }
                updateEvents[23] = Integer.toString(eventList.get(key).getSeatsSold("VIP"));
                updateEvents[24] = Integer.toString(eventList.get(key).getSeatsSold("Gold"));
                updateEvents[25] = Integer.toString(eventList.get(key).getSeatsSold("Silver"));
                updateEvents[26] = Integer.toString(eventList.get(key).getSeatsSold("Bronze"));
                updateEvents[27] = Integer.toString(eventList.get(key).getSeatsSold("General Admission"));
                formattedEventData = String.format("%.2f", eventList.get(key).getTicketsRevenue("VIP"));
                updateEvents[28] = formattedEventData;
                formattedEventData = String.format("%.2f", eventList.get(key).getTicketsRevenue("Gold"));
                updateEvents[29] = formattedEventData;
                formattedEventData = String.format("%.2f", eventList.get(key).getTicketsRevenue("Silver"));
                updateEvents[30] = formattedEventData;
                formattedEventData = String.format("%.2f", eventList.get(key).getTicketsRevenue("Bronze"));
                updateEvents[31] = formattedEventData;
                formattedEventData = String.format("%.2f", eventList.get(key).getTicketsRevenue("General Admission"));
                updateEvents[32] = formattedEventData;
                eventWriter.write(String.join(",", updateEvents));
                eventWriter.newLine();
                Logger.getLogger().Log("Updated Event (ID): " + eventList.get(key).getEventID());
            }
           
        }
             
        catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        //Updates the Customer list, creates a new csv file for the updated values
        try (BufferedWriter customerWriter = new BufferedWriter(new FileWriter(new File("src/main/java/com/runticket/UpdatedCustomerListPA4(Sheet1).csv")))) {
            headerLine = "ID, First Name, Last Name, Money Available, Concerts Purchased, TicketMiner Membership, Username, Password, Transaction Count";
            customerWriter.write(headerLine);
            customerWriter.newLine();
            String formattedCustomerBalance;
            //Iterates through the customerList HashMap and adds the updated values
            for(String key : customerList.keySet()){
                String[] updateCustomer = new String[9];
                updateCustomer[0] = Integer.toString(customerList.get(key).getCustomerID());
                updateCustomer[1] = customerList.get(key).getFirstName();
                updateCustomer[2] = customerList.get(key).getLastName();
                formattedCustomerBalance = String.format("%.2f", customerList.get(key).getBalance());
                updateCustomer[3] = formattedCustomerBalance;
                updateCustomer[4] = Integer.toString(customerList.get(key).getConcertsPurchased());
                if(customerList.get(key).getMemberStatus()) {
                    updateCustomer[5] = "Yes";
                }
                else{
                    updateCustomer[5] = "No";
                }
                updateCustomer[6] = customerList.get(key).getUsername();
                updateCustomer[7] = "REDACTED";
                updateCustomer[8] = Integer.toString(customerList.get(key).getTransactionCount());
                customerWriter.write(String.join(",", updateCustomer));
                customerWriter.newLine();
                Logger.getLogger().Log("Updated Customer (ID): " + customerList.get(key).getCustomerID());
            }
        } 
        catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}