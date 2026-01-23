package com.runticket.model;
import java.util.concurrent.ThreadLocalRandom;
import java.math.BigDecimal;
import java.math.RoundingMode;
/**
 * The Invoice class represents an invoice for a ticket purchase by a Customer for a specific event.
 * It calculates the total amount including tax and member discounts, and provides methods to print the invoice details.
 *
 * @author Francisco Garcia
 * @author Henry Ng
 * @author Adrian Sifuentes
 */
public class Invoice {
    /**
     * The attributes of Invoice
     * @param customer The customer that will receive the Invoice
     * @param ticket Stores the type of ticket being purchased
     * @param event The Event that the tickets are being purchased for
     * @param invoiceId The unique identifier for the invoice
     * @param total The total amount for the transaction
     * @param subtotal The subtotal amount before tax and fees
     * @param charityFee The charity fee included in the transaction
     * @param ticketFee The ticket fee included in the transaction
     * @param fees The total fees included in the transaction
     * @param eventTicketProfit The profit made by the event from the ticket sale, not including fees or tax
     * @param tax The tax collected on the transaction
     * @param discountSavings How much a Customer saved due to TicketMiner membership.
     * @param MEMBER_DISCOUNT The discount rate for members
     * @param TAX_RATE The tax rate applied to the transaction
     * @param CONVENIENCE_FEE The convenience fee applied to the transaction
     * @param TICKET_FEE The ticket fee rate applied to the transaction
     * @param CHARITY_FEE The charity fee rate applied to the transaction
     * @param alphanumericCharacters An array of characters used to generate a random alphanumeric string for the invoice ID
     */
    private final Customer customer;
    private final Tickets ticket;
    private final Event event;
    private final String invoiceId;
    private double discountSavings, total, subtotal, tax, charityFee, ticketFee, fees, eventTicketProfit;
    private final double MEMBER_DISCOUNT = 0.10, TAX_RATE = 0.0825, CONVENIENCE_FEE = 2.50, TICKET_FEE = 0.005, CHARITY_FEE = 0.0075;
    private static final char[] alphanumericCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    
    /**
     * Constructor for Invoice class with customer, ticket, and event parameters
     * @param customer the customer making the purchase
     * @param ticket the tickets being purchased
     * @param event the event for which the tickets are being purchased
     */
    public Invoice(Customer customer, Tickets ticket, Event event){
        this.customer = customer;
        this.ticket = ticket;
        this.event = event;
        this.invoiceId = generateID();
    }
    //Generates an invoice ID, where the first part is the Customer's ID, the second part is a random alphanumeric string, and the third part is the Event's ID
    /**
     * Generates an invoice ID, where the first part is the Customer's ID, the second part is a random alphanumeric string, and the third part is the Event's ID
     * @return the generated ID
     */
    private String generateID(){
        ThreadLocalRandom random = ThreadLocalRandom.current();
        StringBuilder stringBuilder = new StringBuilder(10);
        //The first part is the Customer's ID
        stringBuilder.append(this.customer.getCustomerID());
        stringBuilder.append("-");
        //The second part is a random alphanumeric string of length 5
        for (int i = 0; i < 5; i++) {
            stringBuilder.append(alphanumericCharacters[random.nextInt(alphanumericCharacters.length)]);
        }
        //The third part is the Event's ID
        stringBuilder.append("-");
        stringBuilder.append(this.event.getEventID());

        return stringBuilder.toString();
    }
    /**
     * @return customer, the customer making the purchase
     */
    public Customer getCustomer(){
        return this.customer;
    }
    /**
     * @return ticket, the tickets being purchased
     */
    public Tickets getTicket(){
        return this.ticket;
    }
    /**
     * @return tax, the tax amount collected
     */
    public double getTax(){
        return this.tax;
    }
    /**
     * @return discountSavings, the discount amount for members
     */
    public double getMemberDiscount(){
        return this.discountSavings;
    }
    /**
     * Gets the event associated with this invoice.
     *
     * @return the event for which the tickets were purchased
     */
    public Event getEvent(){
        return this.event;
    }
    /**
     * @return the ticket amount, the number of tickets purchased
     */
    public int getTicketNum(){
        return this.ticket.getQuantity();
    }   
    /**
     * Returns the total amount
     * @return total
     */
    public double returnTotal(){
        return this.total;
    }
    /**
     * Returns the convenience fee
     * @return CONVENIENCE_FEE
     */
    public double getConvenienceFee(){
        return this.CONVENIENCE_FEE;
    }
    /**
     * Returns the ticket fee
     * @return
     */
    public double getTicketFee(){
        return this.ticketFee;
    }
    /**
     * Returns the charity fee
     * @return charityFee
     */
    public double getCharityFee(){
        return this.charityFee;
    }
    /**
     * Returns the invoice ID
     * @return invoiceId
     */
    public String getInvoiceID(){
        return this.invoiceId;
    }
    /**
     * Returns the amount to be deducted from the event due to refunds
     * @return eventTicketProfit
     */
    public double getEventRefund(){
        return this.eventTicketProfit;
    }
    /**
     * Returns the amount to be refunded to the customer, not including fees
     * @return customerRefund
     */
    public double getCustomerRefund(){
        double customerRefund = roundToTwoDecimals(this.total - this.fees);
        return customerRefund;
    }
    /**
     * Returns the full refund amount including all fees (used for event cancellation)
     * @return fullRefund the total amount including fees
     */
    public double getFullRefund(){
        return this.total;
    }
    /**
     * Returns the profit made by the event from the ticket sale, not including fees or tax
     * @return eventTicketProfit
     */
    public double getEventTicketProfit(){
        return this.eventTicketProfit;
    }
    /**
     * Calculates the total amount for the invoice including tax, member discounts, and fees
     * @return total, the total amount
     */
    //Calculates the total amount for the invoice including tax, member discounts, and fees
    public double calculateTotalAmount() {
        double ticketCostAfterDiscount;
        this.eventTicketProfit = this.ticket.getQuantity() * this.event.getTicketPrice(this.ticket.getTier());
        this.ticketFee = (this.eventTicketProfit * this.TICKET_FEE);
        this.charityFee = (this.eventTicketProfit * this.CHARITY_FEE);
        this.fees = roundToTwoDecimals(this.CONVENIENCE_FEE + this.ticketFee + this.charityFee);        
        if(this.customer.getMemberStatus()){
            this.discountSavings = roundToTwoDecimals(this.eventTicketProfit * this.MEMBER_DISCOUNT);  
        }
        else{
            this.discountSavings = 0;
        }
        ticketCostAfterDiscount = this.eventTicketProfit - this.discountSavings;
        this.subtotal = roundToTwoDecimals(ticketCostAfterDiscount + this.fees);
        this.tax = roundToTwoDecimals(this.subtotal * this.TAX_RATE);
        this.total = roundToTwoDecimals(this.subtotal + this.tax);
        return this.total;
    }
    /**
     * helper method to round down to two decimal values
     * @param value
     * @return the value rounded down to two decimals
     */
    // Helper method to round down to two decimal values
    private double roundToTwoDecimals(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.DOWN).doubleValue();
    }

    /**
     * Prints the invoice details including customer information, event details, ticket information, and total amount
     */

    public void printInvoice() {
        System.out.println("\n===== INVOICE =====");
        System.out.println("Customer: " + this.customer.getFirstName() + " " + this.customer.getLastName());
        System.out.println("Event: " + this.event.getEventName());
        System.out.println("Event Type: "+ this.event.getEventType());
        System.out.println("Location: " + this.event.getEventVenue().getVenueName());
        System.out.println("Venue Type: "+ this.event.getEventVenue().getVenueType());
        System.out.println("Ticket Type: " + this.ticket.getTier());
        System.out.println("Quantity: " + this.ticket.getQuantity());
        System.out.println("Price per ticket: $" + this.event.getTicketPrice(this.ticket.getTier()));
        if (this.customer.getMemberStatus()) {
            System.out.println("Total with TickerMiner Membership: $" + this.total);
            System.out.printf("You saved: $%.2f\n", this.discountSavings);
        }
        else{
           System.out.println("Total: $" + this.total); 
        }
        System.out.printf("Ticket Fee: $%.2f\n", this.ticketFee);
        System.out.printf("Convenience Fee: $%.2f\n", this.CONVENIENCE_FEE);
        System.out.printf("Charity Fee: $%.2f\n", this.charityFee);
        System.out.println("Invoice ID: " + this.invoiceId);
        System.out.println("==================\n");
    }
}