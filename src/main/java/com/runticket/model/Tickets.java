package com.runticket.model;
/**
 * The Tickets class represents a ticket with a tier, price, quantity, and percentage available.
 * It provides methods to access and modify ticket details.
 *
 * @author Francisco Garcia
 * @author Henry Ng
 */
public class Tickets{
    /**
     * Attributes of Tickets
     * @param ticketPrice Stores the Tickets' price
     * @param percentage Stores the percentage of seats allocated for a ticket tier
     * @param ticketTier Stores the Tickets' tier
     * @param ticketQuantity Stores the number of Tickets being purchased by a Customer
     */
    //Variables will store the ticket price as a double, the percentage of seats allocated to the ticket tier as a double, the ticket tier as a String, and the quantity as an int
    private double ticketPrice, percentage;
    private String ticketTier;
    private int ticketQuantity;
    /**
     * Default constructor for Tickets class
     */
    public Tickets(){
    
    }
    /**
     * Constructor for Tickets class with tier and price parameters
     * @param tierIn the tier of the ticket
     * @param priceIn the price of the ticket
     */
    public Tickets(String tierIn, double priceIn){
        this.ticketTier = setAcceptedTier(tierIn);
        this.ticketPrice = priceIn;
    }
    /**
     * Sets the accepted ticket tier based on input
     * standardizes the tier so that it matches accepted values
     * @param tierIn
     * @return tier
     */
    //Standardizes the ticket tier to accepted values
    private String setAcceptedTier(String tierIn){
        String tier;
        switch(tierIn.toLowerCase()){
            case "vip" -> tier = "VIP";
            case "gold" -> tier = "Gold";
            case "silver" -> tier = "Silver";
            case "bronze" -> tier = "Bronze";
            default -> tier = "General Admission";
        }
        return tier;
    }
    /**
     * sets the ticket quantity
     * @param quantityIn the quantity of tickets
     */
    public void setQuantity(int quantityIn){
        this.ticketQuantity = quantityIn;
    }
    /**
     * @return ticketQuantity, the quantity of tickets
    */
    public int getQuantity(){
        return this.ticketQuantity;
    }
    /**
     * @param priceIn sets the ticket price
     */
    //Sets the ticket price
    public void setPrice(double priceIn){
        this.ticketPrice = priceIn;
    }
    /**
     * @return ticketPrice, the price of the ticket
     */
    //Gets the ticket price
    public double getPrice(){
        return this.ticketPrice;
    }
    /**
     * @param tierIn sets the ticket tier
     */
    //Sets the Tickets tier
    public void setTier(String tierIn){
        this.ticketTier = tierIn;
    }
    /**
     * @return ticketTier, the tier of the ticket
     */
    //Gets the Tickets tier
    public String getTier(){
        return this.ticketTier;
    }
    /**
     * @param percentageIn sets the percentage of tickets set for the event
     */
    //Sets the percentage of tickets available
    public void setPercentage(double percentageIn){
        this.percentage = percentageIn;
    }
    /**
     * @return percentage, the percentage of tickets set for the event
     */
    //Gets the percentage of tickets available
    public double getPercentage(){
        return this.percentage;
    }


}

