package com.runticket.view;

import java.util.HashMap;

import com.runticket.model.Event;
import com.runticket.model.Invoice;

/**
 * The ConsoleView class handles all console output for the TicketMiner
 * application. It provides methods to display menus, messages, and formatted
 * information to the user.
 *
 * @author Adrian Sifuentes
 * @author Henry Ng
 * @author Sebastian Marquez
 */
public class ConsoleView {

    /**
     * Displays the welcome message
     */
    public void displayWelcomeMessage() {
        System.out.println("-----Welcome to TickerMiner!-----");
    }

    /**
     * Prompts the user to select their role
     */
    public void displayRolePrompt() {
        System.out.println("Are you a Customer, or a System Administrator? (Type Exit to Exit)");
    }

    /**
     * Displays goodbye message
     */
    public void displayGoodbye() {
        System.out.println("Goodbye!");
    }

    /**
     * Displays invalid choice message
     */
    public void displayInvalidChoice() {
        System.out.println("Invalid choice!");
    }

    /**
     * Displays system administrator welcome message
     */
    public void displaySysadminWelcome() {
        System.out.println("------------------------------");
        System.out.println("Welcome System Administrator!");
        System.out.println("------------------------------");
    }

    /**
     * Displays system administrator menu options
     */
    public void displaySysadminMenu() {
        System.out.println("What do you want to do?");
        System.out.println("1. Inquire about an event");
        System.out.println("2. Add an event");
    }

    /**
     * Displays invalid input message
     */
    public void displayInvalidInput() {
        System.out.println("Invalid input");
    }

    /**
     * Prompts for event inquiry
     */
    public void displayInquireEventPrompt() {
        System.out.print("Inquire about an event either by the event's ID or the event's name (Type Exit to Exit): ");
    }

    /**
     * Displays event not found message
     */
    public void displayEventNotFound() {
        System.out.println("Event not found");
    }

    /**
     * Displays error message.
     *
     * @param message the error message to display
     */
    public void displayError(String message) {
        System.out.println("Error: " + message);
    }

    /**
     * Prompts for new event information
     */
    public void displayNewEventPrompt() {
        System.out.println("Enter the new Event's Information");
        System.out.println("Event Type (Sport, Festival, Concert): ");
    }

    /**
     * Displays invalid event type message
     */
    public void displayInvalidEventType() {
        System.out.println("Not a valid event type");
    }

    /**
     * Prompts for event name
     */
    public void displayEventNamePrompt() {
        System.out.print("Event Name: ");
    }

    /**
     * Prompts for event date
     */
    public void displayEventDatePrompt() {
        System.out.print("Event Date: ");
    }

    /**
     * Prompts for event time
     */
    public void displayEventTimePrompt() {
        System.out.print("Event Time: ");
    }

    /**
     * Displays venue selection prompt
     */
    public void displayVenuePrompt() {
        System.out.println("Event Venue (Choose from list, Enter Venue Name)");
    }

    /**
     * Displays available venues.
     *
     * @param venueList the map of venues to display
     */
    public void displayVenues(HashMap<String, com.runticket.model.Venue> venueList) {
        int count = 1;
        for (String key : venueList.keySet()) {
            System.out.println(count + ". " + key);
            count++;
        }
    }

    /**
     * Displays invalid venue choice message
     */
    public void displayInvalidVenueChoice() {
        System.out.println("Invalid choice");
    }

    /**
     * Prompts for general admission ticket price
     */
    public void displayGAPricePrompt() {
        System.out.println("Enter the General Admission Ticket price (Maximum Value $4000)");
        System.out.print("General Admission Price: $");
    }

    /**
     * Displays invalid price message
     */
    public void displayInvalidPrice() {
        System.out.println("Invalid Price");
    }

    /**
     * Prompts for number input
     */
    public void displayNumberPrompt() {
        System.out.println("Please enter a number");
    }

    /**
     * Prompts for fireworks question
     */
    public void displayFireworksPrompt() {
        System.out.println("Will this event have fireworks? (Yes/No)");
    }

    /**
     * Displays invalid choice message for fireworks
     */
    public void displayInvalidFireworksChoice() {
        System.out.println("Invalid choice");
    }

    /**
     * Prompts for fireworks cost
     */
    public void displayFireworksCostPrompt() {
        System.out.print("Enter the Fireworks Cost: $");
    }

    /**
     * Prompts for event cost
     */
    public void displayEventCostPrompt() {
        System.out.print("Enter the cost of the event: $");
    }

    /**
     * Displays customer welcome message
     */
    public void displayCustomerWelcome() {
        System.out.println("Welcome! Do you want to log in?");
        System.out.println("Login\nExit");
    }

    /**
     * Displays invalid choice message for customer
     */
    public void displayInvalidCustomerChoice() {
        System.out.println("Invalid Choice");
    }

    /**
     * Prompts for username
     */
    public void displayUsernamePrompt() {
        System.out.println("Login with your username and password");
        System.out.print("Username: ");
    }

    /**
     * Prompts for password
     */
    public void displayPasswordPrompt() {
        System.out.print("Password: ");
    }

    /**
     * Displays login failure message
     */
    public void displayLoginFailure() {
        System.out.println("Wrong username or password!");
    }

    /**
     * Displays customer greeting.
     *
     * @param firstName the customer's first name
     * @param lastName the customer's last name
     */
    public void displayCustomerGreeting(String firstName, String lastName) {
        System.out.println("Hello, " + firstName + " " + lastName + "!");
    }

    /**
     * Displays customer balance.
     *
     * @param balance the customer's balance to display
     */
    public void displayBalance(double balance) {
        System.out.printf("Balance: $%.2f\n", balance);
    }

    /**
     * Displays customer menu options
     */
    public void displayCustomerMenu() {
        System.out.println("Option 1: Purchase\nOption 2: Exit");
    }

    /**
     * Displays available events.
     *
     * @param eventList the map of events to display
     */
    public void displayAvailableEvents(HashMap<Integer, Event> eventList) {
        System.out.println("\nAvailable Events:");
        for (int key : eventList.keySet()) {
            System.out.println(eventList.get(key).getEventID() + ": " + eventList.get(key).getEventName() + " on " + eventList.get(key).getEventDate()
                    + " | Venue: " + eventList.get(key).getEventVenue().getVenueName()
                    + " | Venue Type: " + eventList.get(key).getEventVenue().getVenueType()
                    + " | Type: " + eventList.get(key).getEventType());
        }
    }

    /**
     * Prompts for event ID selection
     */
    public void displayEventIDPrompt() {
        System.out.print("\nEnter Event ID to purchase (or 'back' to return): ");
    }

    /**
     * Displays invalid event ID message
     */
    public void displayInvalidEventID() {
        System.out.println("Invalid Event ID.");
    }

    /**
     * Prompts for ticket type selection
     */
    public void displayTicketTypePrompt() {
        System.out.print("Enter ticket type (VIP, Gold, Silver, Bronze, General) or 'back' to cancel: ");
    }

    /**
     * Displays ticket price.
     *
     * @param ticketType the type of ticket
     * @param price the price of the ticket
     */
    public void displayTicketPrice(String ticketType, double price) {
        System.out.printf(ticketType + " Ticket Price: $%.2f", price);
        System.out.println();
    }

    /**
     * Displays invalid ticket type message
     */
    public void displayInvalidTicketType() {
        System.out.println("Invalid ticket type.");
    }

    /**
     * Prompts for quantity
     */
    public void displayQuantityPrompt() {
        System.out.println("Enter quantity (1-6): ");
    }

    /**
     * Displays invalid quantity message
     */
    public void displayInvalidQuantity() {
        System.out.println("Quantity must be between 1 and 6. Try again.");
    }

    /**
     * Displays invalid number input message
     */
    public void displayInvalidNumberInput() {
        System.out.println("Invalid input. Please enter number between 1 and 6.");
    }

    /**
     * Displays insufficient funds message
     */
    public void displayInsufficientFunds() {
        System.out.println("Not enough money. Purchase failed.");
    }

    /**
     * Displays not enough funds message
     */
    public void displayNotEnoughFunds() {
        System.out.println("Not enough funds");
    }

    /**
     * Displays invalid number format message
     */
    public void displayInvalidNumberFormat() {
        System.out.println("Invalid number. Please enter digits only.");
    }

    /**
     * Displays exception error message.
     *
     * @param message the error message to display
     */
    public void displayExceptionError(String message) {
        System.out.println("Error: " + message);
    }

    /**
     * Displays event revenue information. This method calls the event's
     * eventRevenue() method to display revenue information.
     *
     * @param event the event for which to display revenue information
     */
    public void displayEventRevenue(Event event) {
        event.eventRevenue();
    }

    /**
     * Displays invoice information.
     *
     * @param invoice the invoice to display
     */
    public void displayInvoice(Invoice invoice) {
        invoice.printInvoice();
    }
}
