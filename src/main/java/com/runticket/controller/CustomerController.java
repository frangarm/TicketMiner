package com.runticket.controller;


import java.util.InputMismatchException;
import com.runticket.model.Customer;
import com.runticket.model.Information;
import com.runticket.service.Logger;
import com.runticket.service.PurchaseTickets;
import com.runticket.view.SwingView;

/**
 * The CustomerController class handles customer-related operations including login and ticket purchasing.
 *
 * @author Adrian Sifuentes 
 * @author Francisco Garcia
 * @author Henry Ng 
 * @author Sebastian Marquez
 */
public class CustomerController {
    /**
     * The attributes for the CustomerController class
     * @param view the GUI view
     * @param purchaseTickets the PurchaseTickets service, which allows customer to buy tickets and canacel orders
     */
    private final SwingView view;
    private final PurchaseTickets purchaseTickets;
    
    /**
     * Constructor for CustomerController
     * @param view the GUI view
     */
    public CustomerController(SwingView view) {
        this.view = view;
        this.purchaseTickets = new PurchaseTickets(view);
    }
    
    /**
     * Handles the welcome process for customers (GUI version)
     * Lets user login, view events, purchase tickets, view their invoices, cancel orders, and logout
     */
    public void welcomeUser() {
        String userChoice = "";
        String user;
        String password;
        view.displayCustomerWelcome();
        Customer currentUser;
        boolean exit = false;
        
        try {
            while (!exit) {
                userChoice = view.getCustomerAction();
                if (userChoice == null) userChoice = "exit";
                
                switch (userChoice.toLowerCase()) {
                    case "login" -> {
                        view.displayUsernamePrompt();
                        user = view.getStringInput("Enter your Username:", "Login");
                        if (user == null || user.trim().isEmpty()) {
                            continue;
                        }
                        
                        view.displayPasswordPrompt();
                        password = view.getStringInput("Enter your Password:", "Login");
                        if (password == null || password.trim().isEmpty()) {
                            continue;
                        }   

                        currentUser = Information.getInformation().getCustomerList().get(user);
                        
                        if (currentUser != null && currentUser.login(user, password)) {
                            view.displayCustomerGreeting(currentUser.getFirstName(), currentUser.getLastName());
                            view.displayBalance(currentUser.getBalance());
                            view.displayCustomerMenu();
                            
                            boolean loggedIn = true;
                            while(loggedIn) {
                                userChoice = view.getPurchaseMenu();
                                switch (userChoice.toLowerCase()) {
                                    case "view events" -> {
                                            view.displayAvailableEvents(Information.getInformation().getEventList());
                                            Logger.getLogger().Log(currentUser.getUsername() + " Viewed All Events");
                                    }
                                    case "purchase" -> {
                                        purchaseTickets.buyTickets(Information.getInformation().getEventList(), Information.getInformation().getCustomerList(), currentUser.getUsername());
                                        // Refresh customer reference after purchase to get updated balance and invoices
                                        Customer latestCustomer = Information.getInformation().getCustomerList().get(currentUser.getUsername());
                                        if (latestCustomer != null) {
                                            currentUser = latestCustomer; // Update reference to latest customer
                                        }
                                    }
                                    case "view invoices" -> {
                                        // Refresh customer reference to get latest invoices from Information singleton
                                        Customer latestCustomer = Information.getInformation().getCustomerList().get(currentUser.getUsername());
                                        if (latestCustomer != null) {
                                            currentUser = latestCustomer; // Update reference to latest customer
                                        }
                                        // Display invoices and receipts in the mainframe details panel
                                        String customerFullName = currentUser.getFirstName() + " " + currentUser.getLastName();
                                        view.displayInvoicesAndReceipts(currentUser.getCustomerInvoices(), customerFullName);
                                        Logger.getLogger().Log(currentUser.getUsername() + " Viewed Invoices and Receipts");
                                    }
                                    case "view my info" -> {
                                        // Refresh customer reference to get latest information
                                        Customer latestCustomer = Information.getInformation().getCustomerList().get(currentUser.getUsername());
                                        if (latestCustomer != null) {
                                            currentUser = latestCustomer; // Update reference to latest customer
                                        }
                                        // Display customer information in the details panel
                                        view.displayCustomerInfo(currentUser);
                                        Logger.getLogger().Log(currentUser.getUsername() + " Viewed My Info");
                                    }
                                    case "cancel order" -> {
                                        purchaseTickets.cancelOrder(Information.getInformation().getEventList(), Information.getInformation().getCustomerList(), currentUser.getUsername());
                                        // Refresh customer reference after cancel to get updated balance and invoices
                                        Customer latestCustomer = Information.getInformation().getCustomerList().get(currentUser.getUsername());
                                        if (latestCustomer != null) {
                                            currentUser = latestCustomer; // Update reference to latest customer
                                        }
                                    }
                                    case "logout" -> {
                                        loggedIn = false;
                                    }
                                    default -> view.displayInvalidCustomerChoice();
                                }
                            }
                        }
                        else {
                            view.displayLoginFailure();
                            if(currentUser != null) {
                                Logger.getLogger().Log(currentUser.getUsername() + " Customer login failed");
                            }
                        }
                    }
                    case "exit" -> exit = true;
                    default -> view.displayInvalidCustomerChoice();
                }
            }
        } 
        catch (InputMismatchException e) {
            view.displayError(e.getMessage());
        }
    }
}

