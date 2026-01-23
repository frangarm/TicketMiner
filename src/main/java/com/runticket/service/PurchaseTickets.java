package com.runticket.service;

import java.awt.HeadlessException;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JOptionPane;
import com.runticket.model.Customer;
import com.runticket.model.Event;
import com.runticket.model.Invoice;
import com.runticket.model.Tickets;
import com.runticket.view.SwingView;
/**
 * The PurchaseTickets class handles the ticket purchasing process for customers.
 * It allows customers to select events, choose ticket types, and complete purchases while generating invoices.
 *
 * @author Francisco Garcia
 * @author Adrian Sifuentes
 * @author Henry Ng
 */
public class PurchaseTickets {
    /**
     * @param buyingCustomer Will store the Customer purchasing tickets
     * @param selectedEvent Will store the Event for which the tickets are being purchased for
     * @param view The GUI view for displaying messages and getting input
     */
    private Customer buyingCustomer;
    private Event selectedEvent;
    private SwingView view;
    
    public PurchaseTickets() {
        // Default constructor for backward compatibility
    }
    /**
     * Constructor for PurchaseTickets with GUI view
     * @param view The GUI view for displaying messages and getting input
     */
    public PurchaseTickets(SwingView view) {
        this.view = view;
    }
    /**
     * Sets the GUI view for displaying messages and getting input
     * @param view The GUI view for displaying messages and getting input
     */
    public void setView(SwingView view) {
        this.view = view;
    }
    /**
     * Method to facilitate the ticket purchasing process for a customer
     * @param eventList the list of available events
     * @param customerList the list of customers
     * @param customerUsername the username of the customer making the purchase
     */
    public void buyTickets(ConcurrentHashMap<Integer, Event> eventList, ConcurrentHashMap<String, Customer> customerList, String customerUsername){
        if (view == null) {
            // Fallback to console if view not set
            System.out.println("Error: GUI view not initialized");
            return;
        }
        
        this.buyingCustomer = customerList.get(customerUsername);
        try {
            // Display all available events with details
            view.displayAvailableEvents(eventList);
            view.appendTextToDisplay("\nAvailable Events:");
            for (int key : eventList.keySet()) {
                Event evt = eventList.get(key);
                view.appendTextToDisplay(evt.getEventID() + ": " + evt.getEventName() + " on " + evt.getEventDate() +
                    " | Venue: " + evt.getEventVenue().getVenueName() +
                    " | Venue Type: " + evt.getEventVenue().getVenueType() +
                    " | Type: " + evt.getEventType() +
                    " | Capacity: " + evt.getEventVenue().getVenueCapacity());
            }

            // Get event ID
            String input = view.getStringInput("Enter Event ID to purchase:", "Purchase Tickets");
            if (input == null || input.trim().equalsIgnoreCase("back") || input.trim().isEmpty()) {
                return;
            }
            
            int eventId;
            try {
                eventId = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                view.displayError("Invalid Event ID. Please enter a number.");
                return;
            }
            
            this.selectedEvent = null;
            for (int key : eventList.keySet()) {
                if (eventList.get(key).getEventID() == eventId) {
                    this.selectedEvent = eventList.get(key);
                    break;
                }
            }

            if (this.selectedEvent == null) {
                view.displayError("Event ID not found. Please enter a valid Event ID from the list.");
                return;
            }

            // Display event details with capacity
            view.displayEventDetailsWithCapacity(this.selectedEvent);

            // Get ticket type
            String[] ticketTypes = {"VIP", "Gold", "Silver", "Bronze", "General"};
            int ticketChoice = view.showOptionDialog("Select ticket type:", "Ticket Selection", ticketTypes);
            if (ticketChoice == -1) {
                return;
            }
            
            String ticketType = ticketTypes[ticketChoice];
            double price;
            switch(ticketType.toLowerCase()){
                case "vip" -> {
                    view.appendTextToDisplay(String.format("VIP Ticket Price: $%.2f", this.selectedEvent.getVIPPrice()));
                    price = this.selectedEvent.getVIPPrice();
                }
                case "gold" -> {
                    view.appendTextToDisplay(String.format("Gold Ticket Price: $%.2f", this.selectedEvent.getGoldPrice()));
                    price = this.selectedEvent.getGoldPrice();
                }
                case "silver" -> {
                    view.appendTextToDisplay(String.format("Silver Ticket Price: $%.2f", this.selectedEvent.getSilverPrice()));
                    price = this.selectedEvent.getSilverPrice();
                }
                case "bronze" -> {
                    view.appendTextToDisplay(String.format("Bronze Ticket Price: $%.2f", this.selectedEvent.getBronzePrice()));
                    price = this.selectedEvent.getBronzePrice();
                }
                case "general" -> {
                    view.appendTextToDisplay(String.format("General Admission Ticket Price: $%.2f", this.selectedEvent.getGAPrice()));
                    price = this.selectedEvent.getGAPrice();
                }
                default -> {
                    view.displayError("Invalid ticket type.");
                    return;
                }
            }
            
            // Check seats available for this tier
            int seatsLeft = this.selectedEvent.getSeatsLeft(ticketType);
            view.appendTextToDisplay("Seats available for " + ticketType + ": " + seatsLeft);
            
            // Get quantity
            int quantity = 0;
            while (true) {
                quantity = view.getIntInput("Enter quantity (1-6):", "Quantity");
                if (quantity == -1) {
                    return; // User cancelled
                }
                if (quantity < 1 || quantity > 6) {
                    view.displayError("Quantity must be between 1 and 6. Try again.");
                } else if (quantity > seatsLeft) {
                    view.displayError("Not enough seats available. Only " + seatsLeft + " seats left for " + ticketType + ".");
                } else {
                    break;
                }
            }
            
            double totalCost = price * quantity;
            Logger.getLogger().Log(this.buyingCustomer.getUsername()+ " is attempting to purchase "+ quantity + " " + ticketType +" tickets for "+ this.selectedEvent.getEventName());
            if (totalCost > this.buyingCustomer.getBalance()) {
                double shortfall = totalCost - this.buyingCustomer.getBalance();
                view.displayError(String.format("Insufficient funds. Required: $%.2f, Available: $%.2f. You need $%.2f more to complete this purchase.", 
                    totalCost, this.buyingCustomer.getBalance(), shortfall));
                return;
            }

            Tickets ticket = new Tickets(ticketType, price);
            ticket.setQuantity(quantity);
            Invoice newInvoice = new Invoice(this.buyingCustomer, ticket, this.selectedEvent);
            // Makes sure that the Customer has enough funds when the tax is included
            double balanceCheck = newInvoice.calculateTotalAmount();
            if(balanceCheck > buyingCustomer.getBalance()) {
                double shortfall = balanceCheck - buyingCustomer.getBalance();
                view.displayError(String.format("Insufficient funds including taxes and fees. Total required: $%.2f, Available balance: $%.2f. You need $%.2f more to complete this purchase.", 
                    balanceCheck, buyingCustomer.getBalance(), shortfall));
                return;
            }
            else{
                this.selectedEvent.addOverhead(newInvoice.getTax(), newInvoice.getMemberDiscount());
                this.buyingCustomer.addInvoice(newInvoice);
                this.selectedEvent.addInvoice(newInvoice);
                this.selectedEvent.addRevenue(ticket.getTier(), newInvoice.getEventTicketProfit(), newInvoice.getTicketFee(), newInvoice.getCharityFee(), newInvoice.getConvenienceFee());
                this.selectedEvent.seatsSold(ticket.getTier(), quantity);
                view.displayInvoice(newInvoice);
                eventList.put(this.selectedEvent.getEventID(), this.selectedEvent);
                customerList.put(this.buyingCustomer.getUsername(), this.buyingCustomer);
                String fullName = this.buyingCustomer.getFirstName() + " " + this.buyingCustomer.getLastName();
                CustomerReceipt receipt = new CustomerReceipt(
                        fullName,
                        this.selectedEvent.getEventType(),
                        this.selectedEvent.getEventName(),
                        this.selectedEvent.getEventDate(),
                        ticket.getTier(),
                        ticket.getQuantity(),
                        newInvoice.calculateTotalAmount()
                );
            
                // Saving to .txt file
                receipt.saveReceiptToFile(); 

                Logger.getLogger().Log("Customer " + this.buyingCustomer.getUsername() + " purchased " + quantity + " " + ticketType + " tickets for " + this.selectedEvent.getEventName());
                
                // Display purchase confirmation
                String confirmationMessage = String.format(
                    "✓ Purchase Successful!\n\n" +
                    "Event: %s\n" +
                    "Ticket Type: %s\n" +
                    "Quantity: %d\n" +
                    "Total Amount: $%.2f\n\n" +
                    "Your receipt has been saved. Thank you for your purchase!",
                    this.selectedEvent.getEventName(),
                    ticket.getTier(),
                    ticket.getQuantity(),
                    newInvoice.calculateTotalAmount()
                );
                JOptionPane.showMessageDialog(null, confirmationMessage, "Purchase Confirmation", JOptionPane.INFORMATION_MESSAGE);
            }

        } 
        catch (NumberFormatException e) {
            view.displayError("Invalid number. Please enter digits only.");
        } 
        catch (HeadlessException e) {
            view.displayError("Error: " + e.getMessage());
        }
    }
    /**
     * Method to facilitate the order cancellation process for a customer
     * It refunds the customer (excluding fees) and updates the event and customer records accordingly.
     * Allows the customer to select an invoice to cancel
     * @param eventList The map of events
     * @param customerList The map of Customers
     * @param customerUsername The username of the customer requesting the cancellation
     */
    public void cancelOrder(ConcurrentHashMap<Integer, Event> eventList, ConcurrentHashMap<String, Customer> customerList, String customerUsername){
        if (view == null) {
            // Fallback to console if view not set
            System.out.println("Error: GUI view not initialized");
            return;
        }
        //Display all invoices for the customer
        this.buyingCustomer = customerList.get(customerUsername);
        view.displayInvoices(this.buyingCustomer.getCustomerInvoices());
        //Prompt user to select an invoice to cancel
        String userChoice = view.getStringInput("Enter the Invoice ID (shown above, NOT the Confirmation Number) to cancel the order (Fees will not be refunded):", "Cancel Order");
        if (userChoice == null || userChoice.trim().equalsIgnoreCase("exit") || userChoice.trim().equalsIgnoreCase("cancel")) {
            view.appendTextToDisplay("Refund cancelled");
            return;
        }
        //Find the invoice to cancel
        Invoice invoiceToCancel = this.buyingCustomer.getInvoice(userChoice.trim());
        Logger.getLogger().Log("Customer " + this.buyingCustomer.getUsername() + " is attempting to cancel an order");
        //If the invoice is found, proceed with cancellation
        if(invoiceToCancel != null) {
            //Process the refund and update records
            this.selectedEvent = invoiceToCancel.getEvent();        
            this.selectedEvent.refund(invoiceToCancel, invoiceToCancel.getInvoiceID());
            this.buyingCustomer.refund(invoiceToCancel.getInvoiceID());
            //Update event and customer data in the maps
            customerList.put(this.buyingCustomer.getUsername(), this.buyingCustomer);
            eventList.put(this.selectedEvent.getEventID(), this.selectedEvent);
            view.appendTextToDisplay("Order cancelled successfully. Refund processed.");
            
            // Display cancellation confirmation dialog
            String confirmationMessage = String.format(
                "✓ Order Cancelled Successfully!\n\n" +
                "Invoice ID: %s\n" +
                "Event: %s\n" +
                "Ticket Type: %s\n" +
                "Quantity: %d\n" +
                "Refund Amount: $%.2f\n\n" +
                "Note: Fees are not refunded as per policy.",
                invoiceToCancel.getInvoiceID(),
                this.selectedEvent.getEventName(),
                invoiceToCancel.getTicket().getTier(),
                invoiceToCancel.getTicketNum(),
                invoiceToCancel.getCustomerRefund()
            );
            JOptionPane.showMessageDialog(null, confirmationMessage, "Cancellation Confirmation", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            view.displayError("Invoice not found. Please use the Invoice ID shown in the list above (format: CustomerID-XXXXX-EventID), not the Confirmation Number from receipts.");
        }
    }
}

