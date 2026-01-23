package com.runticket.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.runticket.model.Customer;
import com.runticket.model.Event;
import com.runticket.model.Invoice;
import com.runticket.model.Tickets;

/**
 * Handles automatic ticket purchasing.
 * Follows all manual purchasing constraints:
 *  - Only buys tickets if customer has enough balance
 *  - Only buys tickets if there are enough available
 *  - Generates a receipt for each successful purchase
 *  - Stores successful purchases in each event
 *
 * @author Henry Ng
 */
public class AutoPurchase{
   

    public AutoPurchase() {

    }
     /**
     * Reads and processes all automatic ticket purchases from CSV file.
     * @param filename The name of the csv file that the autopurchaser will use
     * @param customers The list of customers
     * @param events The list of events
     * @return A list of successfully created invoices
     */
    public List<Invoice> processFile(String filename, ConcurrentHashMap<String, Customer> customers, ConcurrentHashMap<Integer, Event> events){
        List<Invoice> createdInvoices = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String line = reader.readLine();
            while ((line = reader.readLine()) != null){
                String[] parts = line.split(",");

                if (parts.length < 7){
                    System.out.println("Skipping invalid row: " + line);
                    continue;
                }

                String firstName = parts[0].trim();
                String lastName = parts[1].trim();
                String eventID = parts[3].trim();
                String eventName = parts[4].trim();
                int quantity = Integer.parseInt(parts[5].trim());
                String ticketType = parts[6].trim();

                Customer customer = findCustomerByName(firstName, lastName, customers);
                Event event = events.get(Integer.valueOf(eventID));

                if (customer == null) {
                    Logger.getLogger().Log("Autopurchaser: Customer not found: " + firstName + " " +  lastName );
                    System.out.println();
                    continue;
                }
                if (event == null) {
                    Logger.getLogger().Log("Autopurchaser: Event not found: " + eventName);
                    continue;
                }
                double ticketPrice = event.getTicketPrice(ticketType);
                double totalCost = ticketPrice * quantity;

                if (customer.getBalance() < totalCost) {
                    Logger.getLogger().Log("Autopurchaser: " + customer.getFirstName() + " has insufficient funds for " + eventName);
                    continue;
                }
                Integer seatsRemaining = event.getSeatsLeft(ticketType);
                if (seatsRemaining == 0 || seatsRemaining < quantity){
                    Logger.getLogger().Log("Autopurchaser: Not enough " + ticketType + "tickets for" + eventName);
                    continue;
                }

                Tickets newTickets = new Tickets(ticketType, ticketPrice);
                newTickets.setQuantity(quantity);
                Invoice newInvoice = new Invoice(customer, newTickets, event);
                double balanceCheck = newInvoice.calculateTotalAmount();
                //Checks if the total amount (with tax) is not bigger than the Customer's balance
                if (balanceCheck > customer.getBalance()) {
                    Logger.getLogger().Log("Autopurchaser: " + customer.getFirstName() + " has insufficient funds for " + eventName);
                    continue;
                } 
                else {
                    //Adds the revenue, the tax revenue, and the discount costs to the event
                    event.addOverhead(newInvoice.getTax(), newInvoice.getMemberDiscount());
                    event.addRevenue(newTickets.getTier(), newInvoice.getEventTicketProfit(), newInvoice.getTicketFee(), newInvoice.getCharityFee(), newInvoice.getConvenienceFee());
                    event.seatsSold(newTickets.getTier(), newTickets.getQuantity());
                    event.addInvoice(newInvoice);
                    //Adds the invoice to the customer and puts the new information to the customer and event list
                    customer.addInvoice(newInvoice);
                    customers.put(customer.getUsername(), customer);
                    events.put(event.getEventID(), event);

                    // Add invoice to the list of created invoices
                    createdInvoices.add(newInvoice);

                    //Writing receipt to .txt file
                    String fullName = firstName + " " + lastName;
                
                    CustomerReceipt receipt = new CustomerReceipt(
                        fullName,
                        event.getEventType(),
                        event.getEventName(),
                        event.getEventDate(),
                        newTickets.getTier(),
                        newTickets.getQuantity(),
                        balanceCheck
                        );
                
                        // Saving to .txt file
                        receipt.saveReceiptToFile(); 
                    
                }

                
                
            }
            

        } 
        catch (IOException e){
            System.out.println("Error reading AutoPurchase file: " + e.getMessage());
        }
        return createdInvoices;
    }
    /**
     * Finds the buying customer
     * @param first The customer's first name
     * @param last The customer's last name
     * @param customerList The customer
     * @return a customer if found, null if not
     */
    private Customer findCustomerByName(String first, String last, ConcurrentHashMap<String, Customer> customerList) {
        for (String key : customerList.keySet()) {
            if (customerList.get(key).getFirstName().equalsIgnoreCase(first) && customerList.get(key).getLastName().equalsIgnoreCase(last)) {
                return customerList.get(key);
            }
        }
        return null;
    }
    
}