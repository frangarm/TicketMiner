/*
    Simple test class to open and test the SwingView GUI
    This is Option 2 - test the GUI without changing your existing code
 */
package com.runticket;

import javax.swing.SwingUtilities;

import com.runticket.view.SwingView;

/**
 * Simple test class to demonstrate the SwingView GUI This opens the GUI window
 * so you can see it works without modifying your controllers
 *
 * To run: Right-click this file -> Run As -> Java Application
 *
 * @author Adrian Sifuentes
 * @author Henry Ng
 * @author Sebastian Marquez
 */
public class TestSwingView {

    public static void main(String[] args) {
        // Schedule GUI creation on the Event Dispatch Thread (required for Swing)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Create the GUI view
                    SwingView view = new SwingView();
                    
                    // Test some display methods
                    view.displayWelcomeMessage();
                    view.displayRolePrompt();
                    
                    // Add a test message
                    System.out.println("GUI window opened! You can see the messages in the GUI window.");
                    System.out.println("This test shows the GUI works without changing your controllers.");
                    
                    // Keep the window open
                    // The GUI will stay open until you close it
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Error opening GUI: " + e.getMessage());
                }
            }
        });
    }
}
