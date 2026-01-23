/*
    Name: Adrian Sifuentes, Francisco Garcia, Henry Ng, Sebastian Marquez
    Date: 11/25/25
    Course: CS3331, CRN 11809
    Intructor: Daniel Mejia
    Programming Assignment 3
    Lab Description: The purpose of this lab is to is to implement a system that lets a user buy tickets for different types of events. Users will be able to login into
    their account using an username and password and buy different tiers of tickets for events, one type for each transaction, and receive an invoice. They will also be 
    able to cancel an order and receive a refund. The system will also have a list of tickets purchased for each event, and the revenue made from each event, including fees.
    A System Administrator also needs to be able to see how many tickets/seats an event has sold, included for each ticket tier and an event's expected revenue and 
    current revenue. The System Administrator will also be able to add new events to the system and cancel events. An Autopurchaser which purchases tickets according to 
    the information on a file will also be implemented. There will also be a logger that logs each action taken, and a csv file will have the update values of customers and 
    events each time the system is exited. A receipt will be generated for each Customer transaction. The system will have a GUI.
    Honesty Statement: We completed this assignment as a team, with assitance from generative AI, but without outside assistance from other people not on the team.
 */

package com.runticket;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import com.runticket.controller.TicketController;
import com.runticket.model.Information;
import com.runticket.model.Customer;
import com.runticket.model.Event;
import com.runticket.service.Logger;
import com.runticket.service.CustomerReceipt;
import java.util.concurrent.ConcurrentHashMap;
/**
 * The RunTicket class is the main entry point for the TicketMiner application.
 * It initializes data and starts the TicketController.
 *
 * @author Adrian Sifuentes
 * @author Francisco Garcia
 * @author Henry Ng
 * @author Sebastian Marquez
 */
public class RunTicket {
    /**
     * The main method initializes the TicketMiner application, loads data from
     * files, activates the autopurchasers, starts the controller, and updates
     * the data when the program is exited.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Logger.getLogger().Log("Program Initialized");
        // Use SwingUtilities to ensure GUI runs on Event Dispatch Thread
        SwingUtilities.invokeLater(() ->{
            try {

                // Create controller (this will create the GUI window immediately)
                TicketController controller = new TicketController();
                
                //Will update data when window is closed
                controller.getView().getMainFrame().addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    exitProgram();
                }});
                // Start the GUI application
                controller.start();
               
            } 
            catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error starting application: " + e.getMessage());
                // Show error in GUI if possible
                JOptionPane.showMessageDialog(
                        null,
                        "Error starting application: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
    /**
     * Exits the program after updating data.
     * This method ensures that all data is saved before the application terminates.
     * It also closes the logger
     */
    public static void exitProgram(){
        // Generate 8 receipts for specified customers before exiting
        generate8Receipts();
        
        //Will update data and then exit the program
        Information.getInformation().updateData();
        Logger.getLogger().Log("Program exited and data updated.");
        Logger.getLogger().closeLogger();
        System.exit(0);
    }

    /**
     * Generates 8 receipts for specified customers and saves them in the generated8Receipts folder.
     */
    private static void generate8Receipts() {
        ConcurrentHashMap<String, Customer> customerList = Information.getInformation().getCustomerList();
        ConcurrentHashMap<Integer, Event> eventList = Information.getInformation().getEventList();
        
        // Get the first available event for receipts
        Event firstEvent = null;
        if (!eventList.isEmpty()) {
            firstEvent = eventList.values().iterator().next();
        }
        
        if (firstEvent == null) {
            Logger.getLogger().Log("No events available to generate receipts.");
            return;
        }
        
        // List of customers to generate receipts for
        String[] customerNames = {
            "Daniel Mejia",
            "Alireza Nouri",
            "Adrian Sifuentes",
            "Henry Ng",
            "Sebastian Marquez",
            "Francisco Garcia",
            "Mickey Mouse",
            "Donald Duck"
        };
        
        // Find and generate receipts for each customer
        for (String fullName : customerNames) {
            Customer customer = findCustomerByName(fullName, customerList);
            if (customer != null) {
                // Generate receipt with default values
                String ticketType = "GA";
                int numTickets = 1;
                double totalPrice = firstEvent.getGAPrice();
                
                CustomerReceipt receipt = new CustomerReceipt(
                    fullName,
                    firstEvent.getEventType(),
                    firstEvent.getEventName(),
                    firstEvent.getEventDate(),
                    ticketType,
                    numTickets,
                    totalPrice
                );
                
                receipt.saveReceiptToGenerated8Folder();
                Logger.getLogger().Log("Generated receipt for: " + fullName);
            } else {
                Logger.getLogger().Log("Customer not found: " + fullName);
            }
        }
    }

    /**
     * Finds a customer by their full name (first and last name).
     * @param fullName The full name of the customer
     * @param customerList The customer list to search
     * @return The customer if found, null otherwise
     */
    private static Customer findCustomerByName(String fullName, ConcurrentHashMap<String, Customer> customerList) {
        String[] nameParts = fullName.trim().split("\\s+", 2);
        String firstName = nameParts[0];
        String lastName = nameParts.length > 1 ? nameParts[1] : "";
        
        for (Customer customer : customerList.values()) {
            if (customer.getFirstName() != null && customer.getLastName() != null) {
                if (customer.getFirstName().equalsIgnoreCase(firstName)) {
                    if (lastName.isEmpty() || customer.getLastName().equalsIgnoreCase(lastName)) {
                        return customer;
                    }
                }
            }
        }
        return null;
    }
}
