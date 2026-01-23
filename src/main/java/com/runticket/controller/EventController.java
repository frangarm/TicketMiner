package com.runticket.controller;

import com.runticket.view.SwingView;
import com.runticket.model.Event;
import com.runticket.model.Sport;
import com.runticket.model.Concert;
import com.runticket.model.Festival;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.runticket.service.Logger;
import com.runticket.service.AutoPurchase;
import com.runticket.model.Information;
import com.runticket.model.Invoice;
import com.runticket.model.Customer;

import java.util.concurrent.ConcurrentHashMap;

/**
 * The EventController class handles event-related operations including inquiries and additions.
 *
 * @author Adrian Sifuentes
 * @author Francisco Garcia
 * @author Henry Ng
 * @author Sebastian Marquez
 */
public class EventController {
    /** The attributes for the EventController class
     * @param view the GUI view
     */
    private SwingView view;
    /**
     * Constructor for EventController
     * @param view the GUI view
     */
    public EventController(SwingView view) {
        this.view = view;
    }
    
    /**
     * Handles the welcome process for system administrators (GUI version)
     * Lets sysadmin view users, view events, enquire about events, add new events, run the autopurchaser, and exit
     */
    public void welcomeSysadmin() {
        String sysadminInput = "";
        boolean running = true;
        
        while (running) {
            view.displaySysadminWelcome();
            view.displaySysadminMenu();
            sysadminInput = view.getSysadminMenuSelection();
            if (sysadminInput == null) sysadminInput = "exit";
            
            switch (sysadminInput.toLowerCase()) {
                case "view users" -> {
                    view.displayCustomers(Information.getInformation().getCustomerList());
                    Logger.getLogger().Log("System Administrator Viewed All Users");
                }
                case "view events" -> {
                    view.displayAvailableEvents(Information.getInformation().getEventList());
                    Logger.getLogger().Log("System Administrator Viewed All events");
                }
                case "enquire event" -> enquireEvent();
                case "add event" -> addEvent();
                case "run autopurchaser" -> runAutopurchaser();
                case "view event fees" -> viewEventFees();
                case "view all fees" -> viewAllFees();
                case "cancel event" -> cancelEvent();
                case "exit" -> running = false;
                default -> view.displayInvalidInput();
            }
        }
        view.displayGoodbye();
    }
    
    /**
     * Allows the system administrator to enquire about events (GUI version)
     * The system administrator can enquire about an event by its ID or name
     */
    private void enquireEvent() {
        boolean searching = true;
        
        while (searching) {
            boolean foundEvent = false;
            view.displayInquireEventPrompt();
            String input = view.getStringInput("Enquire about an event either by the event's ID or the event's name:", "Enquire Event");
            
            if (input == null || input.trim().equalsIgnoreCase("exit") || input.trim().isEmpty()) {
                searching = false;
                break;
            }
            
            input = input.trim();
            //If the input is all digits, treat it as an ID
            if(input.matches("^\\d+$")){
                if(Information.getInformation().getEventList().containsKey(Integer.valueOf(input))){
                    foundEvent = true;
                    view.displayEventRevenue(Information.getInformation().getEventList().get(Integer.valueOf(input)));
                    Logger.getLogger().Log("System Administrator Successfully Enquired about Event (ID) " + input);
                }
            }
            //else treat it as a name
            else{
                for(int key: Information.getInformation().getEventList().keySet()){
                    if(Information.getInformation().getEventList().get(key).getEventName().equalsIgnoreCase(input)){
                        foundEvent = true;
                        view.displayEventRevenue(Information.getInformation().getEventList().get(key));
                        Logger.getLogger().Log("System Administrator Successfully Enquired about Event (Name) " + input);
                    }
                }
            }
            if(!foundEvent){
                view.displayEventNotFound();
                Logger.getLogger().Log("System Administrator Unsuccessfully Enquired about Event " + input);
            }
        }
    }
    
    /**
     * Allows the system administrator to add a new event (GUI version)
     */
    private void addEvent() {
        Event newEvent = null;
        boolean successfulField = false;
        String sysadminInput;
        int largestKey = Collections.max(Information.getInformation().getEventList().keySet());
        double gaTicketPrice;
        
        view.displayNewEventPrompt();
        // Get event type
        String[] eventTypes = {"Sport", "Festival", "Concert"};
        int typeChoice = view.showOptionDialog("Event Type:", "Event Type", eventTypes);
        if (typeChoice == -1) return;
        
        switch (typeChoice) {
            case 0 -> {
                newEvent = new Sport();
                newEvent.setEventID(largestKey + 1);
                newEvent.setEventType("Sport");
            }
            case 1 -> {
                newEvent = new Festival();
                newEvent.setEventID(largestKey + 1);
                newEvent.setEventType("Festival");
            }
            case 2 -> {
                newEvent = new Concert();
                newEvent.setEventID(largestKey + 1);
                newEvent.setEventType("Concert");
            }
        }
        
        // Get event name
        view.displayEventNamePrompt();
        sysadminInput = view.getStringInput("Event Name:", "New Event");
        if (sysadminInput == null) return;
        newEvent.setEventName(sysadminInput);
        
        // Get event date
        view.displayEventDatePrompt();
        sysadminInput = view.getStringInput("Event Date (ex. 01/01/2025):", "New Event");
        if (sysadminInput == null) return;
        newEvent.setEventDate(sysadminInput);
        
        // Get event time
        view.displayEventTimePrompt();
        sysadminInput = view.getStringInput("Event Time (ex. 10:00 AM or 2:30 PM):", "New Event");
        if (sysadminInput == null) return;
        newEvent.setEventTime(sysadminInput);
        
        // Get venue
        view.displayVenuePrompt();
        view.displayVenues(Information.getInformation().getVenueList());
        String[] venueNames = Information.getInformation().getVenueList().keySet().toArray(new String[0]);
        int venueChoice = view.showOptionDialog("Select Venue:", "Venue Selection", venueNames);
        if (venueChoice == -1) return;
        newEvent.setEventVenue(Information.getInformation().getVenueList().get(venueNames[venueChoice]));
        
        // Get GA price
        view.displayGAPricePrompt();
        while (!successfulField) {
            gaTicketPrice = view.getDoubleInput("Enter the General Admission Ticket price (Maximum Value $4000)", "Ticket Price");
            if (gaTicketPrice == -1) return;
            
            if (gaTicketPrice < 4000.00 && gaTicketPrice >= 0) {
                newEvent.setTickets("General Admission", gaTicketPrice, 0.45);
                newEvent.setTickets("Bronze", (gaTicketPrice * 1.5), 0.20);
                newEvent.setTickets("Silver", (gaTicketPrice * 2.5), 0.15);
                newEvent.setTickets("Gold", (gaTicketPrice * 3.0), 0.10);
                newEvent.setTickets("VIP", (gaTicketPrice * 5.0), 0.05);
                newEvent.setSeats();
                successfulField = true;
            } 
            else {
                view.displayInvalidPrice();
            }
        }
        
        // Get fireworks
        view.displayFireworksPrompt();
        boolean hasFireworks = view.showYesNoDialog("Will this event have fireworks?", "Fireworks");
        newEvent.setFireworks(hasFireworks);
        
        if (newEvent.getFireworks()) {
            view.displayFireworksCostPrompt();
            double fireworksCost = view.getDoubleInput("Enter the Fireworks Cost:", "Fireworks Cost");
            if (fireworksCost == -1) fireworksCost = 0;
            newEvent.setFireworksCost(fireworksCost);
        }
        
        view.displayEventCostPrompt();
        double eventCost = view.getDoubleInput("Enter the cost of the event:", "Event Cost");
        if (eventCost == -1) eventCost = 0;
        newEvent.setEventCost(eventCost);
        
        newEvent.setExpectedRevenue(newEvent.expectedProfit());
        Information.getInformation().getEventList().put(newEvent.getEventID(), newEvent);
        Logger.getLogger().Log("New Event (ID) " + newEvent.getEventID() + " added to event list");
        
        // Display confirmation with event details
        view.displayEventAddedConfirmation(newEvent);
    }
    /**
     * Allows the system administrator to run the autopurchaser (GUI version)
     * The system administrator can select from predefined CSV files to process auto purchases
     */
    private void runAutopurchaser(){
        // Define the available auto purchase CSV files with their full paths
        String[] fileOptions = {
            "src/main/java/com/runticket/AutoPurchase10k.csv",
            "src/main/java/com/runticket/AutoPurchas100k.csv",
            "src/main/java/com/runticket/AutoPurchas500k.csv"
        };
        
        // Display friendly names for the buttons
        String[] buttonLabels = {
            "AutoPurchase10k.csv (10k purchases)",
            "AutoPurchas100k.csv (100k purchases)",
            "AutoPurchas500k.csv (500k purchases)"
        };
        
        // Let user select which file to use
        int choice = view.showOptionDialog("Select which auto purchase file to run:", "Run Auto Purchaser", buttonLabels);
        
        if (choice == -1) {
            return; // User cancelled
        }
        
        String filename = fileOptions[choice];
        
        // Try multiple possible paths
        String[] possiblePaths = {
            filename,
            "src/main/java/com/runticket/" + filename,
            filename.replace("src/main/java/com/runticket/", "")
        };
        
        java.io.File file = null;
        String actualPath = null;
        
        // Find the file in one of the possible locations
        for (String path : possiblePaths) {
            file = new java.io.File(path);
            if (file.exists()) {
                actualPath = path;
                break;
            }
        }
        
        // If still not found, try just the filename in current directory
        if (actualPath == null) {
            file = new java.io.File(filename);
            if (file.exists()) {
                actualPath = filename;
            }
        }
        
        if (actualPath == null || !file.exists()) {
            view.displayError("File not found: " + filename);
            Logger.getLogger().Log("System Administrator attempted to run autopurchaser with non-existent file: " + filename);
            return;
        }
        
        // Show loading/processing message in details panel
        view.displayAutoPurchaseLoading(actualPath);
        
        // Update log
        view.appendTextToDisplay("Processing auto purchase file: " + actualPath);
        
        // Process on a separate thread to keep UI responsive (though processing will still block)
        // For now, we'll process synchronously but show loading state
        // Create AutoPurchase instance and process file
        AutoPurchase autoPurchase = new AutoPurchase();
        List<Invoice> createdInvoices = autoPurchase.processFile(
            actualPath,
            Information.getInformation().getCustomerList(),
            Information.getInformation().getEventList()
        );
        
        // Update log with completion
        view.appendTextToDisplay("Auto purchase processing completed. Created " + createdInvoices.size() + " invoices.");
        
        // Count total processed (we need to read the file again to count, or track in AutoPurchase)
        // For now, we'll estimate based on created invoices and log messages
        // A better approach would be to modify AutoPurchase to return statistics
        int successful = createdInvoices.size();
        
        // Read file to count total lines (excluding header)
        int totalProcessed = 0;
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(actualPath))) {
            reader.readLine(); // Skip header
            while (reader.readLine() != null) {
                totalProcessed++;
            }
        } catch (java.io.IOException e) {
            // If we can't read the file, use successful count as estimate
            totalProcessed = successful;
        }
        
        int failed = totalProcessed - successful;
        
        // Display results in the details panel
        view.displayAutoPurchaseResults(createdInvoices, totalProcessed, successful, failed);
        
        Logger.getLogger().Log("System Administrator ran autopurchaser: " + successful + " successful, " + failed + " failed out of " + totalProcessed + " total.");
    }
    
    /**
     * Allows the system administrator to view fees for a specific event
     * Displays service fees (ticket fees), convenience fees, charity fees, and total fees
     */
    private void viewEventFees() {
        boolean searching = true;
        
        while (searching) {
            boolean foundEvent = false;
            view.displayInquireEventPrompt();
            String input = view.getStringInput("Enter event ID or name to view fees:", "View Event Fees");
            
            if (input == null || input.trim().equalsIgnoreCase("exit") || input.trim().isEmpty()) {
                searching = false;
                break;
            }
            
            input = input.trim();
            Event selectedEvent = null;
            
            //If the input is all digits, treat it as an ID
            if(input.matches("^\\d+$")){
                if(Information.getInformation().getEventList().containsKey(Integer.valueOf(input))){
                    foundEvent = true;
                    selectedEvent = Information.getInformation().getEventList().get(Integer.valueOf(input));
                }
            }
            //else treat it as a name
            else{
                for(int key: Information.getInformation().getEventList().keySet()){
                    if(Information.getInformation().getEventList().get(key).getEventName().equalsIgnoreCase(input)){
                        foundEvent = true;
                        selectedEvent = Information.getInformation().getEventList().get(key);
                        break;
                    }
                }
            }
            
            if(foundEvent && selectedEvent != null){
                view.displayEventFees(selectedEvent);
                Logger.getLogger().Log("System Administrator Viewed Fees for Event (ID) " + selectedEvent.getEventID());
            }
            else{
                view.displayEventNotFound();
                Logger.getLogger().Log("System Administrator Unsuccessfully Attempted to View Fees for Event " + input);
            }
        }
    }
    
    /**
     * Allows the system administrator to view fees for all events
     * Displays total service fees, convenience fees, and total fees across all events
     */
    private void viewAllFees() {
        ConcurrentHashMap<Integer, Event> eventList = Information.getInformation().getEventList();
        double totalTicketFees = 0.0;
        double totalConvenienceFees = 0.0;
        double totalCharityFees = 0.0;
        double grandTotalFees = 0.0;
        
        for (Event event : eventList.values()) {
            totalTicketFees += event.getTicketFees();
            totalConvenienceFees += event.getConvenienceFees();
            totalCharityFees += event.getCharityFees();
            grandTotalFees += event.getTotalFees();
        }
        
        view.displayAllFees(totalTicketFees, totalConvenienceFees, totalCharityFees, grandTotalFees);
        Logger.getLogger().Log("System Administrator Viewed Fees for All Events");
    }
    
    /**
     * Allows the system administrator to cancel an event
     * Refunds all money to customers including service fees
     */
    private void cancelEvent() {
        boolean searching = true;
        
        while (searching) {
            boolean foundEvent = false;
            view.displayInquireEventPrompt();
            String input = view.getStringInput("Enter event ID or name to cancel:", "Cancel Event");
            
            if (input == null || input.trim().equalsIgnoreCase("exit") || input.trim().isEmpty()) {
                searching = false;
                break;
            }
            
            input = input.trim();
            Event selectedEvent = null;
            
            //If the input is all digits, treat it as an ID
            if(input.matches("^\\d+$")){
                if(Information.getInformation().getEventList().containsKey(Integer.valueOf(input))){
                    foundEvent = true;
                    selectedEvent = Information.getInformation().getEventList().get(Integer.valueOf(input));
                }
            }
            //else treat it as a name
            else{
                for(int key: Information.getInformation().getEventList().keySet()){
                    if(Information.getInformation().getEventList().get(key).getEventName().equalsIgnoreCase(input)){
                        foundEvent = true;
                        selectedEvent = Information.getInformation().getEventList().get(key);
                        break;
                    }
                }
            }
            
            if(foundEvent && selectedEvent != null){
                // Confirm cancellation
                boolean confirm = view.showYesNoDialog(
                    "Are you sure you want to cancel event: " + selectedEvent.getEventName() + "?\n" +
                    "This will refund all customers including service fees.",
                    "Confirm Event Cancellation"
                );
                
                if (confirm) {
                    processEventCancellation(selectedEvent);
                    Logger.getLogger().Log("System Administrator Cancelled Event (ID) " + selectedEvent.getEventID());
                } else {
                    view.appendTextToDisplay("Event cancellation cancelled.");
                }
                searching = false;
            }
            else{
                view.displayEventNotFound();
                Logger.getLogger().Log("System Administrator Unsuccessfully Attempted to Cancel Event " + input);
            }
        }
    }
    
    /**
     * Processes the cancellation of an event by refunding all customers
     * @param event the event to cancel
     */
    private void processEventCancellation(Event event) {
        HashMap<String, Invoice> invoiceList = event.getInvoiceList();
        int refundCount = 0;
        double totalRefunded = 0.0;
        //Mirror to avoid concurrent modification
        ArrayList<Invoice> invoiceListMirror = new ArrayList<>(invoiceList.values());
        // Process refunds for all invoices associated with this event
        for (Invoice invoice : invoiceListMirror) {
            Customer customer = invoice.getCustomer();
            String invoiceID = invoice.getInvoiceID();
            
            // Process full refund (including fees)
            double refundAmount = customer.refundFull(invoiceID);
            if (refundAmount > 0) {
                refundCount++;
                totalRefunded += refundAmount;
                
                // Update customer in customer list
                Information.getInformation().getCustomerList().put(customer.getUsername(), customer);
                
                // Update event financials by processing refund
                event.refund(invoice, invoiceID);
            }
        }
        
        // Remove event from event list
        Information.getInformation().getEventList().remove(event.getEventID());
        
        // Display cancellation results
        view.displayEventCancellationResults(event, refundCount, totalRefunded);
    }
}