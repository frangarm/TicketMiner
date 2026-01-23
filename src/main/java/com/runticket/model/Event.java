package com.runticket.model;
import java.util.HashMap;
/**
 * The Event class represents an event with details such as name, date, time, type,
 * cost, venue, tickets, seating, and invoices.
 * It provides methods to access and modify event information, manage tickets and seating,
 * and handle invoices for ticket purchases.
 *
 * @author Francisco Garcia
 * @author Adrian Sifuentes
 * @author Henry Ng
 */
public abstract class Event {
    /**
     * Attributes of an Event
     * @param eventName The name of the Event
     * @param eventDate The date in which the Event will take place
     * @param eventTime The time at which the Even will take place
     * @param eventType The type of the Event
     * @param eventCost The total cost of the Event
     * @param fireworkCost The total cost of the Event's fireworks
     * @param expectedProfit The expected profit of the Event
     * @param currentRevenue The total revenue that the Event has raised
     * @param taxRevenue The total tax revenue that the Even has raised, this should not be added to currentRevenue, they are different
     * @param discountCosts The total revenue lost due to TicketMiner memberships
     * @param ticketFees The total revenue raised from ticket fees
     * @param convenienceFees The total revenue raised from convenience fees
     * @param charityFees The total revenue raised from charity fees
     * @param totalFees The total revenue raised from all fees combined
     * @param eventID The Event's ID
     * @param seatsSold How many total seats the event has sold
     * @param eventVenue The Event's Venue
     * @param tickets Stores the different prices for the different Ticket tiers
     * @param seating Stores the seats allocated to the Event according to tier, and how many seats have been sold by each tier.
     * @param seatsLeft Stores the total ammount of seats left by tier
     * @param tierRevenue Stores the total revenue of each ticket tier
     * @param invoiceList Stores the invoice list for each transaction related to the Event
     * @param hasFireworks Stores wheter the Event will have Fireworks or not 
     */
    private String eventName, eventDate, eventTime, eventType;
    private double eventCost, fireworkCost, expectedProfit, currentRevenue, taxRevenue, discountCosts, ticketFees, convenienceFees, charityFees, totalFees;
    private int eventID, seatsSold;
    private Venue eventVenue;
    private HashMap<String, Tickets> tickets = new HashMap<>();
    private HashMap<String, Integer> seating = new HashMap<>();
    private HashMap<String, Integer> seatsLeft = new HashMap<>();
    private HashMap<String, Double> tierRevenue = new HashMap<>();
    private HashMap<String, Invoice> invoiceList = new HashMap<>();
    private boolean hasFireworks;
    /**
     * Default constructor for Event class
     */
    public Event(){

    }
    /**
     * Constructor for Event class with name parameter
     * @param nameIn the name of the event
     */
    public Event(String nameIn){
        this.eventName = nameIn;
    }
    /**
     * Constructor for Event class with name and ID parameters
     * @param nameIn the name of the event
     * @param idIn the ID of the event
     */
    public Event(String nameIn, int idIn){
        this.eventName = nameIn;
        this.eventID = idIn;
    }
    /**
     * Constructor for Event class with name, ID, and venue parameters
     * @param nameIn the name of the event
     * @param idIn the ID of the event
     * @param venueIn the venue of the event
     */
    public Event(String nameIn, int idIn, Venue venueIn){
        this.eventName = nameIn;
        this.eventID = idIn;
        this.eventVenue = venueIn;
    }
    /**
     * Returns the event's name 
     * @return eventName, the name of the event
     */
    //Gets the Event's name
    public String getEventName() {
        return this.eventName;
    }
    /**
     * Sets the event's name
     * @param nameIn the name to set for the event
     */
    //Sets the Event's name
    public void setEventName(String nameIn) {
        this.eventName = nameIn;
    }
    /**
     * Returns the event's ID
     * @return eventID, the ID of the event
     */
    //Gets the Event's ID
    public int getEventID() {
        return this.eventID;
    }
    /**
     * Sets the event's ID
     * @param idIn the ID to set for the event
     */
    //Sets the Event's ID
    public void setEventID(int idIn) {
        this.eventID = idIn;
    }
     /**
     * Sets the event's type
     * @param typeIn the type to set for the event
     */
    //Sets the Event's Type
    public void setEventType(String typeIn){
        this.eventType = typeIn;
    }
     /**
     * Returns the event's type
     * @return eventType the type set for the event
     */
    //Gets the Event's Type
    public String getEventType(){
        return this.eventType;
    }
     /**
     * Returns the event's date
     * @return eventDate the date set for the event
     */
    //Gets the Event's Date
    public String getEventDate() {
        return this.eventDate;
    }
     /**
     * Sets the event's date
     * @param dateIn the date to set for the event
     */
    //Sets the Event's Date
    public void setEventDate(String dateIn) {
        this.eventDate = dateIn;
    }
     /**
     * Returns the event's timer
     * @return eventType the time set for the event
     */
    //Gets the Event's Time
    public String getEventTime() {
        return this.eventTime;
    }
     /**
     * Sets the event's time
     * @param timeIn the time to set for the event
     */
    //Sets the Event's Time
    public void setEventTime(String timeIn) {
        this.eventTime = timeIn;
    }
     /**
     * Returns the event's venue
     * @return eventVenue the venue set for the event
     */
    //Gets the Event's Venue
    public Venue getEventVenue() {
        return this.eventVenue;
    }
    /**
     * Sets the event's venue
     * @param venueIn the venue to set for the event
     */
    //Sets the Event's Venue
    public void setEventVenue(Venue venueIn){
        this.eventVenue = venueIn;
    }
     /**
     * Sets the event's total cost
     * @param eventCostIn the total cost to set for the event
     */
    //Sets the cost of the event
    public void setEventCost(double eventCostIn){
        this.eventCost = eventCostIn;
    }
     /**
     * Returns the event's total cost
     * @return eventCost the total cost of the event
     */
    //Gets the cost of the event
    public double getEventCost(){
        return this.eventCost;
    }
    /**
     * Sets the expected revenue for the event
     * @param profitIn the expected revenue to set for the event
     */
    public void setExpectedRevenue(double profitIn){
        this.expectedProfit = profitIn;
    }
    /**
     * Returns the event's expected revenue
     * @return expectedRevenue the expected revenue of the event
     */
    public double getExpectedRevenue(){
        return this.expectedProfit;
    }
     /**
     * Sets the event's total tax revenue
     * @param taxIn the tax revenue to set for the event
     */
    public void setTaxRevenue(double taxIn){
        this.taxRevenue = taxIn;
    }
     /**
     * Returns the event's tax revenue
     * @return taxRevenue the event's tax revenue
     */
    public double getTaxRevenue(){
        return this.taxRevenue;
    }
     /**
     * Sets the event's discounts costs, the costs caused by membership discounts
     * @param costIn the discount cost to set for the event
     */
    public void setDiscountCosts(double costIn){
        this.discountCosts = costIn;
    }
     /**
     * Returns the event's discounts costs
     * @return discountCosts the event's discount costs
     */
    public double getDiscountCosts(){
        return this.discountCosts;
    }
    /**
     * Returns the event's VIP tier ticket price
     * @return tickets.get("VIP").getPrice() the price set for the ticket VIP tier, stored in the tickets HashMap 
    */
    //Gets the VIP ticket price
    public double getVIPPrice(){
        return this.tickets.get("VIP").getPrice();
    }
    /**
     * Returns the event's Gold tier ticket price
     * @return tickets.get("Gold").getPrice() the price set for the ticket Gold tier, stored in the tickets HashMap 
    */
    //Gets the Gold ticket price
    public double getGoldPrice(){
        return this.tickets.get("Gold").getPrice();
    }
    /**
     * Returns the event's Silver tier ticket price
     * @return tickets.get("Silver").getPrice() the price set for the ticket Silver tier, stored in the tickets HashMap 
    */
    //Gets the Silver ticket price
    public double getSilverPrice(){
        return this.tickets.get("Silver").getPrice();
    }
    /**
     * Returns the event's Bronze tier ticket price
     * @return tickets.get("Bronze").getPrice() the price set for the ticket Bronze tier, stored in the tickets HashMap 
    */
    //Gets the Bronze ticket price
    public double getBronzePrice(){
        return this.tickets.get("Bronze").getPrice();
    }
    /**
     * Returns the event's General Adminission tier ticket price
     * @return tickets.get("General Admission").getPrice() the price set for the ticket General Admission tier, stored in the tickets HashMap 
    */
    //Gets the General Admission ticket price
    public double getGAPrice(){
        return this.tickets.get("General Admission").getPrice();
    }
    /**
     * Sets wheter the event will have fireworks or not
     * @param fireworksIn sets the event's fireworks status
     */
    //Sets if the Event will have fireworks
    public void setFireworks(boolean fireworksIn){
        this.hasFireworks = fireworksIn;
    }
    /**
     * Returns wheter the event will have fireworks or not
     * @return hasFirewors the event's fireworks status
     */
    //Gets if the Event will have fireworks
    public boolean getFireworks(){
        return this.hasFireworks;
    }
    /**
     * Sets the event's fireworks costs
     * @param costIn sets the event's fireworks costs
     */
    //Sets the cost of fireworks for the Event
    public void setFireworksCost(double costIn){
        this.fireworkCost = costIn;
        this.eventCost += costIn;
    }
    /**
     * Returns the event's fireworks costs
     * @return fireworkCost the event's fireworks costs
     */
    //Gets the cost of fireworks for the Event
    public double getFireworkCost(){
        return this.fireworkCost;
    }
    /**
     * Sets the fees for the event.
     *
     * @param fee1In the ticket fee amount
     * @param fee2In the convenience fee amount
     * @param fee3In the charity fee amount
     */
    public void setFees(int fee1In, int fee2In, int fee3In){
        this.ticketFees = fee1In;
        this.convenienceFees = fee2In;
        this.charityFees = fee3In;
        this.totalFees += (fee1In + fee2In + fee3In);
    }
    /**
     * Returns the total ticket fees collected for the event
     * @return ticketFees the total ticket fees
     */
    public double getTicketFees(){
        return this.ticketFees;
    }
    /**
     * Returns the total convenience fees collected for the event
     * @return convenienceFees the total convenience fees
     */
    public double getConvenienceFees(){
        return this.convenienceFees;
    }
    /**
     * Returns the total charity fees collected for the event
     * @return charityFees the total charity fees
     */
    public double getCharityFees(){
        return this.charityFees;
    }
    /**
     * Returns the total fees collected for the event
     * @return totalFees the total fees
     */
    public double getTotalFees(){
        return this.totalFees;
    }
    /**
     * Method to set the prices and percentages for each ticket tier. 
     * Also sets the revenue to 0 for each ticket tier.
     * @param tierIn The ticket tier to be set
     * @param priceIn The price to be set
     * @param percentageIn The percentage to be set
     */
    //Sets the ticket prices according to tier, the tickets are stored in a HashMap called tickets where the ticket tier serves as the key.
    //The percentage of tickets according to tier is also added. Keys and values are added to the HashMap tierRevenue 
    public void setTickets(String tierIn, double priceIn, double percentageIn){
        switch(tierIn.toUpperCase()){
            case "VIP" -> {Tickets vipTicket = new Tickets(tierIn, priceIn);
                           vipTicket.setPercentage(percentageIn);
                           this.tickets.put(tierIn, vipTicket);
                           this.tierRevenue.put("VIP", 0.0);              
            }
            case "GOLD" -> {Tickets goldTicket = new Tickets(tierIn, priceIn);
                           goldTicket.setPercentage(percentageIn);
                           this.tickets.put(tierIn, goldTicket);
                           this.tierRevenue.put("Gold", 0.0);                 
            }
            case "SILVER" -> {Tickets silverTicket = new Tickets(tierIn, priceIn);
                           silverTicket.setPercentage(percentageIn);
                           this.tickets.put(tierIn, silverTicket); 
                           this.tierRevenue.put("Silver", 0.0);                
            }
            case "BRONZE" -> {Tickets bronzeTicket = new Tickets(tierIn, priceIn);
                           bronzeTicket.setPercentage(percentageIn);
                           this.tickets.put(tierIn, bronzeTicket); 
                           this.tierRevenue.put("Bronze", 0.0);                
            }
            case "GENERAL ADMISSION" -> {Tickets gaTicket = new Tickets(tierIn, priceIn);
                           gaTicket.setPercentage(percentageIn);
                           this.tickets.put(tierIn, gaTicket);
                           this.tierRevenue.put("General Admission", 0.0);                 
            }
            default -> System.out.println("Something went wrong...");
        }
    }
    /**
     * Method to print the ticket prices according to tier
     */
    //Prints the Event's Ticket Prices
    public void getPrices(){
        System.out.println("---------------");
        System.out.println("Ticket Prices for " + this.getEventName());
        System.out.printf("VIP Ticket Price: $%.2f\n", this.tickets.get("VIP").getPrice());
        System.out.printf("Gold Ticket Price: $%.2f\n", this.tickets.get("Gold").getPrice());
        System.out.printf("Silver Ticket Price: $%.2f\n", this.tickets.get("Silver").getPrice());
        System.out.printf("Bronze Ticket Price: $%.2f\n", this.tickets.get("Bronze").getPrice());
        System.out.printf("General Admission Ticket Price: $%.2f\n", this.tickets.get("General Admission").getPrice());
        System.out.println("---------------");
     
        System.out.println("---------------");
    }
    /**
     * Method to return the price for a ticket tier
     * @param tierIn The tier for which the price will be returned
     * @return returnPrice the price of the requested tier
     */
    public double getTicketPrice(String tierIn){
        double returnPrice;
        switch (tierIn.toLowerCase()) {
            case "vip" -> returnPrice = this.tickets.get("VIP").getPrice();
            case "gold" -> returnPrice = this.tickets.get("Gold").getPrice();
            case "silver" -> returnPrice =  this.tickets.get("Silver").getPrice();
            case "bronze" -> returnPrice = this.tickets.get("Bronze").getPrice();
            default -> returnPrice = this.tickets.get("General Admission").getPrice();
        }
        return returnPrice;
    }
    /**
     * Method to set the percentage of seats according to tier.
     */
    //Sets the percentage of seats according to tier, initializes the number of seats sold according to tier and sets it to zero
    //Sets the number of seats left, which is unchanged due to no tickets being sold yet
    public void setSeats(){
        this.seatsLeft.put("Total", this.eventVenue.getVenueCapacity());
        int seatsTier;
        seatsTier = (int) (this.eventVenue.getVenueCapacity() * this.tickets.get("VIP").getPercentage());
        this.seating.put("VIP", seatsTier);
        this.seatsLeft.put("VIP", seatsTier);
        this.seating.put("VIP Sold", 0);
        seatsTier = (int) (this.eventVenue.getVenueCapacity() * this.tickets.get("Gold").getPercentage());
        this.seating.put("Gold", seatsTier);
        this.seatsLeft.put("Gold", seatsTier);
        this.seating.put("Gold Sold", 0);
        seatsTier = (int) (this.eventVenue.getVenueCapacity() * this.tickets.get("Silver").getPercentage());
        this.seating.put("Silver", seatsTier);
        this.seatsLeft.put("Silver", seatsTier);
        this.seating.put("Silver Sold", 0);
        seatsTier = (int) (this.eventVenue.getVenueCapacity() * this.tickets.get("Bronze").getPercentage());
        this.seating.put("Bronze", seatsTier);
        this.seatsLeft.put("Bronze", seatsTier);
        this.seating.put("Bronze Sold", 0);
        seatsTier = (int) (this.eventVenue.getVenueCapacity() * this.tickets.get("General Admission").getPercentage());
        this.seating.put("General Admission", seatsTier);
        this.seatsLeft.put("General Admission", seatsTier);
        this.seating.put("General Admission Sold", 0);

    }
    /**
     * Method to add the number of seats sold according to tier when a transaction is made
     * It also substract the number of seats left in the chosen tier
     * @param tierIn The tier of the tickets being purchased
     * @param numIn The number of tickets being purchased
     */
    //Adds the number of seats sold according to tier to the HashMap seating
    //Substracts the number of seats left according to tier
    public void seatsSold(String tierIn, int numIn){
        this.seatsSold += numIn;
        int previousValue;
        switch (tierIn.toUpperCase()){
            case "VIP" -> { previousValue = this.seating.get("VIP Sold");
                            this.seating.put("VIP Sold", previousValue + numIn);
                            previousValue = this.seatsLeft.get("Total");
                            this.seatsLeft.put("Total", previousValue - numIn);
                            previousValue = this.seatsLeft.get("VIP");
                            this.seatsLeft.put("VIP", previousValue - numIn);
            }
            case "GOLD" -> { previousValue = this.seating.get("Gold Sold");
                               this.seating.put("Gold Sold", previousValue + numIn);
                               previousValue = this.seatsLeft.get("Total");
                               this.seatsLeft.put("Total", previousValue - numIn);
                               previousValue = this.seatsLeft.get("Gold");
                               this.seatsLeft.put("Gold", previousValue - numIn);
            }
            case "SILVER" -> { previousValue = this.seating.get("Silver Sold");
                               this.seating.put("Silver Sold", previousValue + numIn);
                               previousValue = this.seatsLeft.get("Total");
                               this.seatsLeft.put("Total", previousValue - numIn);
                               previousValue = this.seatsLeft.get("Silver");
                               this.seatsLeft.put("Silver", previousValue - numIn);
            }
            case "BRONZE" -> { previousValue = this.seating.get("Bronze Sold");
                               this.seating.put("Bronze Sold", previousValue + numIn);
                               previousValue = this.seatsLeft.get("Total");
                               this.seatsLeft.put("Total", previousValue - numIn);
                               previousValue = this.seatsLeft.get("Bronze");
                               this.seatsLeft.put("Bronze", previousValue - numIn);
            }
            case "GENERAL ADMISSION" -> { previousValue = this.seating.get("General Admission Sold");
                                          this.seating.put("General Admission Sold", previousValue + numIn);
                                          previousValue = this.seatsLeft.get("Total");
                                          this.seatsLeft.put("Total", previousValue - numIn);
                                          previousValue = this.seatsLeft.get("General Admission");
                                          this.seatsLeft.put("General Admission", previousValue - numIn);
            }

        }
    }
    /**
     * Returns how many seats are left for a given ticket type
     */
    public int getSeatsLeft(String tier) {
        // Normalize input
        String normalized = tier.trim().toLowerCase();
    
        // Map short names or variants to the actual keys
        if (normalized.equals("ga") || normalized.equals("generaladmission")) {
            tier = "General Admission";
        } else if (normalized.equals("vip")) {
            tier = "VIP";
        } else if (normalized.equals("gold")) {
            tier = "Gold";
        } else if (normalized.equals("silver")) {
            tier = "Silver";
        } else if (normalized.equals("bronze")) {
            tier = "Bronze";
        }
    
        // Now safely look up the key
        if (seatsLeft.containsKey(tier)) {
            return seatsLeft.get(tier);
        } else {
            System.out.println("Tier " + tier + " not found.");
            return 0;
        }
    }
    /**
     * Method to print out the number of seats left according to tier
     */
    //Prints the amount of seats left according to tier
    public void seatsLeft(){
        System.out.println("Total Seats Left: " + this.seatsLeft.get("Total"));
        System.out.println("VIP Tier Seats Left: " + this.seatsLeft.get("VIP"));
        System.out.println("Gold Tier Seats Left: " + this.seatsLeft.get("Gold"));
        System.out.println("Silver Tier Seats Left: " + this.seatsLeft.get("Silver"));
        System.out.println("Bronze Tier Seats Left: " + this.seatsLeft.get("Bronze"));
        System.out.println("General Admission Tier Seats Left: " + this.seatsLeft.get("General Admission"));

    }
    /**
     * Method to add invoices to invoiceList
     * @param invoiceIn the invoice being added
     */
    //Adds an invoice to invoiceList everytime a customer makes a successful purchase
    public void addInvoice(Invoice invoiceIn){
        this.invoiceList.put(invoiceIn.getInvoiceID(), invoiceIn);
    }
    /**
     * Returns the event's invoice list
     * @return invoiceList the event's invoice list
     */
    public HashMap<String, Invoice> getInvoiceList(){
        return this.invoiceList;
    }
    /**
     * Method to display the event's invoice list
     */
    //Diplays the Customer Invoices
    public void displayInvoices(){
          for (String inv: this.invoiceList.keySet()){
            this.invoiceList.get(inv).printInvoice();
        }
    }
    /**
     * Adds tax revenue and the costs of membership discounts
     * @param taxIn The tax being added to taxRevenue
     * @param discountIn The discount costs being added to discountCosts
     */
    //Adds to the tax and discount costs
    public void addOverhead(double taxIn, double discountIn){
        this.taxRevenue += taxIn;
        this.discountCosts += discountIn;
    }
    /**
     * Adds revenue to a ticket tier whenever a ticket is purchased
     * @param tierIn The tier of the ticket being pruchased
     * @param revenueIn The revenue colelcted from the transaction
     * @param ticketFeesIn The ticket fees collected from the transaction
     * @param charityFeesIn The charity fees collected from the transaction
     * @param convenienceFeesIn The convenience fees collected from the transaction
     */
    //Adds revenue everytime a customer purchases tickets to tierRevenue, where the tier is the key and the revenue is the value
    public void addRevenue(String tierIn, double revenueIn, double ticketFeesIn, double charityFeesIn, double convenienceFeesIn){
        //Updates overall event revenue and fee totals
        this.currentRevenue += (revenueIn + ticketFeesIn + charityFeesIn + convenienceFeesIn);
        this.ticketFees += ticketFeesIn;
        this.charityFees += charityFeesIn;
        this.convenienceFees += convenienceFeesIn;
        this.totalFees += (ticketFeesIn + charityFeesIn + convenienceFeesIn);
        this.eventCost -= this.currentRevenue;

        double previousValue;
        switch (tierIn.toUpperCase()){
    
            case "VIP" -> { previousValue = this.tierRevenue.get("VIP");
                            this.tierRevenue.put("VIP" , previousValue + revenueIn);
            }
            case "GOLD"-> { previousValue = this.tierRevenue.get("Gold");
                              this.tierRevenue.put("Gold" , previousValue + revenueIn);
            }
            case "SILVER"-> { previousValue = this.tierRevenue.get("Silver");
                              this.tierRevenue.put("Silver" , previousValue + revenueIn);
            }
            case "BRONZE"-> { previousValue = this.tierRevenue.get("Bronze");
                              this.tierRevenue.put("Bronze" , previousValue + revenueIn);
            }
            case "GENERAL ADMISSION"-> { previousValue = this.tierRevenue.get("General Admission");
                                         this.tierRevenue.put("General Admission" , previousValue + revenueIn);
            }
            
        }
    }
    /**
     * Method to print the event's financial information
     */
    //Prints information about the event, including the number of tickets sold, the total revenue of each ticket tier, and the event's total and expected profit
    public void eventRevenue(){
    
        System.out.println("Event ID: " + this.eventID);
        System.out.println("Event Name: " + this.eventName);
        System.out.println("Event Date and Time: " + this.eventDate + " at " + this.eventTime);
        System.out.println("Event Type: " + this.eventType);
        System.out.println("Event Venue: " + eventVenue.getVenueName()+ " (" + this.eventVenue.getVenueType() + ")");
        System.out.println("Event Capacity: " + this.eventVenue.getVenueCapacity());
        System.out.println("Total Seats Sold: " + this.seatsSold);
        System.out.println("Total VIP Seats Sold: " + this.seating.get("VIP Sold"));
        System.out.println("Total Gold Seats Sold: " + this.seating.get("Gold Sold"));
        System.out.println("Total Silver Seats Sold: " + this.seating.get("Silver Sold"));
        System.out.println("Total Bronze Seats Sold: " + this.seating.get("Bronze Sold"));
        System.out.println("Total General Admission Seats Sold: " + this.seating.get("General Admission Sold"));
        System.out.printf("Total Revenue for VIP Tickets: $%.2f\n", this.tierRevenue.get("VIP"));
        System.out.printf("Total Revenue for Gold Tickets: $%.2f\n", this.tierRevenue.get("Gold"));
        System.out.printf("Total Revenue for Silver Tickets: $%.2f\n", this.tierRevenue.get("Silver"));
        System.out.printf("Total Revenue for Bronze Tickets: $%.2f\n", this.tierRevenue.get("Bronze"));
        System.out.printf("Total Revenue for General Admission Tickets: $%.2f\n", this.tierRevenue.get("General Admission"));
        System.out.printf("Total Revenue for all tickets: $%.2f\n", this.currentRevenue);
        System.out.printf("Total Fee Revenue: $%.2f\n", this.totalFees);
        System.out.printf("Total Convenience Fee Revenue: $%.2f\n", this.convenienceFees);
        System.out.printf("Total Ticket Fee Revenue: $%.2f\n", this.ticketFees);
        System.out.printf("Total Charity Fee Revenue: $%.2f\n", this.charityFees);
        System.out.printf("Expected Profit (Tickets Sold Out): $%.2f\n", this.expectedProfit);
        System.out.printf("Actual Profit: $%.2f\n", (this.eventCost * -1));
        if (this.hasFireworks) {
           System.out.printf("Firework Costs: $%.2f\n", this.fireworkCost); 
        }
        System.out.printf("Tax Revenue: $%.2f\n", this.taxRevenue);
        System.out.printf("Discount Costs: $%.2f\n", (-1 * this.discountCosts));

    }
    /**
     * Method to calculate and return the event's expected profit
     * @return profit the event's expected profit
     */
    //Calculates the exptected profit based on the seating and the prices of each tier
    public double expectedProfit(){
        double profit = 0;

            profit += this.tickets.get("VIP").getPrice() * this.seating.get("VIP");
            profit += this.tickets.get("Gold").getPrice() * this.seating.get("Gold");
            profit += this.tickets.get("Silver").getPrice() * this.seating.get("Silver");
            profit += this.tickets.get("Bronze").getPrice() * this.seating.get("Bronze");
            profit += this.tickets.get("General Admission").getPrice() * this.seating.get("General Admission");
            profit -= this.eventCost;
            
        return profit;
    }
    /**
     * Method to return the number of seats sold in each tier
     * @param tierIn The tier of the number of seats being returned
     * @return seatsSold the number of seats sold
     */
    //Returns the number of seats sold according to tier
    public int getSeatsSold(String tierIn){
        int seatsSold;
        switch(tierIn.toUpperCase()){
            case "VIP" -> seatsSold = this.seating.get("VIP Sold");
            case "GOLD" -> seatsSold = this.seating.get("Gold Sold");
            case "SILVER" -> seatsSold = this.seating.get("Silver Sold");
            case "BRONZE" -> seatsSold = this.seating.get("Bronze Sold");
            default -> seatsSold = this.seating.get("General Admission Sold");
        }
        return seatsSold;
    }
    /**
     * Method to return the revenue of a tier
     * @param tierIn The tier chosen 
     * @return tierRevenue the revenue of the tier chosen
     */
    //Returns the total revenue generated according to tier
    public double getTicketsRevenue(String tierIn){
        double tierRevenue;
        switch(tierIn.toUpperCase()){
            case "VIP" -> tierRevenue = this.tierRevenue.get("VIP");
            case "GOLD" -> tierRevenue = this.tierRevenue.get("Gold");
            case "SILVER" -> tierRevenue = this.tierRevenue.get("Silver");
            case "BRONZE" -> tierRevenue = this.tierRevenue.get("Bronze");
            default -> tierRevenue = this.tierRevenue.get("General Admission");
        }
        return tierRevenue;
    }
    /**
     * Abstract method that will print out event-specific information
     */
    //Abstract method to be implemented by subclasses to print event-specific information
    public abstract void printInfo();
    /**
     * Method to process a refund for a given invoice
     * The method updates the event's financials and seating accordingly
     * Only the fees are non-refundable
     * Removes the invoice from the invoice list
     * @param invoiceIn The invoice to be refunded
     * @param invoiceIDIn The ID of the invoice to be refunded
     */
    //Will substract the revenue, tax, discount costs and seats sold from the event when a refund is processed
    public void refund(Invoice invoiceIn, String invoiceIDIn){
        String tier = invoiceIn.getTicket().getTier();
        tier = tier.trim();
        double refund;
        //If the invoice exists, process the refund
        if (this.invoiceList.containsKey(invoiceIDIn)) {
            //Calculate the refund amount (excluding fees)
            refund = invoiceIn.getEventRefund();
            //Update event financials and seating by subtracting the refunded amounts, adding back the seats to seats available, and substracting the seats sold
            this.currentRevenue -= refund;
            this.tierRevenue.put(tier, this.tierRevenue.get(tier) - refund);
            this.taxRevenue -= invoiceIn.getTax();
            this.seatsSold -= invoiceIn.getTicketNum();
            //If the customer is a member, also substract the member discount from discountCosts
            if(invoiceIn.getCustomer().getMemberStatus()){
                this.discountCosts -= invoiceIn.getMemberDiscount();
            }
            this.seatsLeft.put(tier, this.seatsLeft.get(tier) + invoiceIn.getTicketNum());
            tier = tier + " Sold";
            this.seating.put(tier, this.seating.get(tier) - invoiceIn.getTicketNum());
            //Remove the invoice from the invoice list
            this.invoiceList.remove(invoiceIDIn);
        }
        else{
            System.out.println("Invoice not found");
        }
    }   
}