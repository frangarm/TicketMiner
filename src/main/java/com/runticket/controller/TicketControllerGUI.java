package com.runticket.controller;
 
import com.runticket.view.SwingView;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * GUI Version of TicketController - Uses SwingView instead of ConsoleView
 * This is the same as TicketController but uses SwingView for GUI display.
 *
 * @author Adrian Sifuentes
 * @author Henry Ng
 * @author Sebastian Marquez
 */
public class TicketControllerGUI {
    private SwingView view;
    private CustomerController customerController;
    private EventController eventController;
    /**
     * Constructor for TicketControllerGUI - uses SwingView.
     * Initializes the view for GUI display.
     */
    public TicketControllerGUI() {
        this.view = new SwingView();
    }
    
    /**
     * Starts the main application loop with GUI.
     * Displays welcome message and handles user role selection.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        String userChoice = "";
        try {
            view.displayWelcomeMessage();
            while (!userChoice.toLowerCase().equals("exit")) {
                view.displayRolePrompt();
                userChoice = scanner.nextLine().trim();
                // Note: Customer and Event controllers would need to be GUI versions too
                // This is a simplified version
                switch (userChoice.toLowerCase()) {
                    case "customer" -> {
                        view.displayError("GUI Customer controller not implemented yet. Use regular TicketController.");
                    }
                    case "system administrator" -> {
                        view.displayError("GUI Event controller not implemented yet. Use regular TicketController.");
                    }
                    case "exit" -> view.displayGoodbye();
                    default -> view.displayInvalidChoice();
                }
            }
        } catch (InputMismatchException e) {
            view.displayError(e.getMessage());
        } finally {
            scanner.close();
        }
    }
}

