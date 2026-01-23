package com.runticket.controller;

import com.runticket.view.SwingView;
import java.util.InputMismatchException;
import javax.swing.SwingUtilities;

/**
 * The TicketController class is the main controller for the TicketMiner application.
 * It coordinates between the view and other controllers, handling the main application flow.
 *
 * @author Adrian Sifuentes
 * @author Henry Ng 
 * @author Sebastian Marquez
 */
public class TicketController {
    /**
     * The attributes for the TicketController class
     * @param view the GUI view
     * @param customerController the CustomerController to handle customer operations
     * @param eventController the EventController to handle system administrator operations
     */
    private SwingView view;
    private CustomerController customerController;
    private EventController eventController;
    /**
     * Constructor for TicketController
     * Initializes the view and the Customer and Event controllers
     */
    public TicketController() {
        this.view = new SwingView();
        this.customerController = new CustomerController(this.view);
        this.eventController = new EventController(this.view);
    }
    
    /**
     * Gets the SwingView instance used by this controller.
     *
     * @return the SwingView instance
     */
    public SwingView getView(){
        return this.view;
    }
    /**
     * Starts the main application loop with GUI
     */
public void start() {
    // Display welcome message immediately
    view.displayWelcomeMessage();
    
    // Start the application logic in a separate thread to avoid blocking EDT
    Thread appThread = new Thread(() -> {
        // Small delay to ensure GUI is fully visible
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        
        // Run the main application loop in background thread
        String userChoice = "";
        try {
            while (!userChoice.toLowerCase().equals("exit") && !Thread.currentThread().isInterrupted()) {
                view.displayRolePrompt();
                userChoice = view.getRoleSelection();
                if (userChoice == null) {
                    userChoice = "exit";
                    break;
                }
                
                switch (userChoice.toLowerCase()) {
                    case "customer" -> customerController.welcomeUser();
                    case "system administrator" -> eventController.welcomeSysadmin();
                    case "exit" -> {
                        view.displayGoodbye();
                        // Exit the application
                        SwingUtilities.invokeLater(() -> {
                            view.getMainFrame().dispose();
                        });
                        return;
                    }
                    default -> view.displayInvalidChoice();
                }
            }
        } catch (Exception e) {
            view.displayError(e.getMessage());
        }
    });
    
    appThread.start();
}
}