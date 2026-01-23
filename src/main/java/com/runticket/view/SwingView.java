package com.runticket.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.text.DefaultCaret;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.runticket.RunTicket;
import com.runticket.model.Event;
import com.runticket.model.Invoice;
import com.runticket.model.Venue;
import com.runticket.model.Customer;

/**
 * The SwingView class handles all GUI output for the TicketMiner application
 * using Java Swing. It provides methods to display menus, messages, and
 * formatted information to the user with a modern, professional
 * Apple/Tesla-inspired design.
 *
 * @author Adrian Sifuentes
 * @author Henry Ng
 * @author Sebastian Marquez
 */
public class SwingView {
    /**
     * Color scheme constants for the GUI design
     */
    private static final Color BACKGROUND_DARK = new Color(18, 18, 18);      // Deep black
    private static final Color BACKGROUND_LIGHT = new Color(28, 28, 30);     // Dark gray
    private static final Color SURFACE_COLOR = new Color(44, 44, 46);        // Card surface
    private static final Color ACCENT_PRIMARY = new Color(0, 122, 255);       // iOS Blue
    private static final Color ACCENT_SECONDARY = new Color(88, 86, 214);    // Purple accent
    private static final Color TEXT_PRIMARY = Color.WHITE;    // White text
    private static final Color TEXT_SECONDARY = new Color(174, 174, 178);    // Light gray text
    private static final Color SUCCESS_COLOR = new Color(52, 199, 89);       // Green
    private static final Color ERROR_COLOR = new Color(255, 59, 48);         // Red
    private static final Color BORDER_COLOR = new Color(58, 58, 60);         // Subtle border
    /**
     * Main frame of the application
     * @param mainFrame The main JFrame
     * @param displayArea The text area for displaying messages
     * @param mainPanel The main panel containing all components
     * @param scrollPane The scroll pane for the display area
     * @param controlPanel The panel for interactive controls
     * @param eventsTable The table displaying events
     * @param eventsTableModel The model for the events table
     * @param customersTable The table displaying customers
     * @param customersTableModel The model for the customers table
     * @param cardLayout The CardLayout for switching views
     * @param cardPanel The panel containing the CardLayout
     * @param eventsScrollPane The scroll pane for the events table
     * @param customersScrollPane The scroll pane for the customers table
     * @param detailsPanel The panel for displaying detailed information
     * @param detailsScrollPane The scroll pane for the details panel
     * @param viewIndicatorLabel The label indicating the current view
     */
    private JFrame mainFrame;
    private JTextArea displayArea;
    private JPanel mainPanel;
    private JScrollPane scrollPane;
    private JPanel controlPanel; // Panel for interactive controls
    private JTable eventsTable; // Events table in main window
    private DefaultTableModel eventsTableModel; // Model for events table
    private JTable customersTable; // Customers table in main window
    private DefaultTableModel customersTableModel; // Model for customers table
    private CardLayout cardLayout; // For switching between views
    private JPanel cardPanel; // Container for card layout
    private JScrollPane eventsScrollPane; // Scroll pane for events table
    private JScrollPane customersScrollPane; // Scroll pane for customers table
    private JPanel detailsPanel; // Panel for displaying detailed information
    private JScrollPane detailsScrollPane; // Scroll pane for details panel
    private JLabel viewIndicatorLabel; // Label to show current view
    /**
     * Input synchronization variables
     * @param inputLock The lock object for synchronizing input
     * @param stringInputResult The result of string input
     * @param stringInputReady Flag indicating if string input is ready
     * @param optionDialogResult The result of option dialog input
     * @param optionDialogReady Flag indicating if option dialog input is ready
     */
    // Synchronization locks for blocking input
    private final Object inputLock = new Object();
    private String stringInputResult = null;
    private boolean stringInputReady = false;
    private int optionDialogResult = -1;
    private boolean optionDialogReady = false;

    /**
     * Constructor for SwingView - initializes the GUI
     */
    public SwingView() {
        // Ensure GUI initialization happens on Event Dispatch Thread
        if (javax.swing.SwingUtilities.isEventDispatchThread()) {
            initializeGUI();
        } else {
            javax.swing.SwingUtilities.invokeLater(() -> {
                initializeGUI();
            });
        }
    }

    /**
     * Initializes the main GUI components with modern Apple/Tesla styling
     */
    private void initializeGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("OptionPane.background", BACKGROUND_DARK);
            UIManager.put("Panel.background", BACKGROUND_DARK);
            UIManager.put("OptionPane.messageForeground", TEXT_PRIMARY);
        } catch (Exception e) {
            // Use default look and feel if system L&F fails
        }

        mainFrame = new JFrame("TicketMiner");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1000, 700);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.getContentPane().setBackground(BACKGROUND_DARK);

        // Main panel with gradient background effect
        mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Subtle gradient background
                GradientPaint gradient = new GradientPaint(
                        0, 0, BACKGROUND_DARK,
                        0, getHeight(), BACKGROUND_LIGHT
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        mainPanel.setBackground(BACKGROUND_DARK);

        // Display area for messages with modern styling
        displayArea = new JTextArea() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(SURFACE_COLOR);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
                g2d.dispose();
            }
        };
        displayArea.setEditable(false);
        displayArea.setFont(new Font("-apple-system", Font.PLAIN, 14));
        displayArea.setBackground(SURFACE_COLOR);
        displayArea.setForeground(TEXT_PRIMARY);
        displayArea.setMargin(new Insets(10, 10, 10, 10));
        displayArea.setOpaque(false);

        // Auto scroll to bottom
        DefaultCaret caret = (DefaultCaret) displayArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        // Modern scroll pane
        scrollPane = new JScrollPane(displayArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());

        // Create control panel for interactive elements (right/bottom side)
        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(BACKGROUND_DARK);
        controlPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Wrapper to ensure control panel stays at bottom
        JPanel controlWrapper = new JPanel(new BorderLayout());
        controlWrapper.setBackground(BACKGROUND_DARK);
        controlWrapper.add(controlPanel, BorderLayout.CENTER);
        controlWrapper.setPreferredSize(new Dimension(1000, 200));

        // Create events table with enhanced columns
        String[] eventColumnNames = {"ID", "Event Name", "Date", "Time", "Type", "Venue", "Capacity", 
                                     "Seats Left", "VIP Price", "Gold Price", "Silver Price", "Bronze Price", "GA Price"};
        eventsTableModel = new DefaultTableModel(eventColumnNames, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        eventsTable = new JTable(eventsTableModel);
        styleEventsTable(eventsTable);
        
        // Set column widths for events table
        eventsTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        eventsTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Event Name
        eventsTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Date
        eventsTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // Time
        eventsTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Type
        eventsTable.getColumnModel().getColumn(5).setPreferredWidth(150); // Venue
        eventsTable.getColumnModel().getColumn(6).setPreferredWidth(70);  // Capacity
        eventsTable.getColumnModel().getColumn(7).setPreferredWidth(80);  // Seats Left
        eventsTable.getColumnModel().getColumn(8).setPreferredWidth(80);  // VIP Price
        eventsTable.getColumnModel().getColumn(9).setPreferredWidth(80);  // Gold Price
        eventsTable.getColumnModel().getColumn(10).setPreferredWidth(90); // Silver Price
        eventsTable.getColumnModel().getColumn(11).setPreferredWidth(90); // Bronze Price
        eventsTable.getColumnModel().getColumn(12).setPreferredWidth(80); // GA Price

        eventsScrollPane = new JScrollPane(eventsTable);
        eventsScrollPane.setOpaque(false);
        eventsScrollPane.getViewport().setOpaque(false);
        eventsScrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        eventsScrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());

        // Create customers table
        String[] customerColumnNames = {"Username", "First Name", "Last Name", "Customer ID", 
                                       "Member", "Balance", "Concerts Purchased", "Transactions", "Lifetime Savings"};
        customersTableModel = new DefaultTableModel(customerColumnNames, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        customersTable = new JTable(customersTableModel);
        styleEventsTable(customersTable); // Reuse same styling
        
        // Set column widths for customers table
        customersTable.getColumnModel().getColumn(0).setPreferredWidth(120); // Username
        customersTable.getColumnModel().getColumn(1).setPreferredWidth(120); // First Name
        customersTable.getColumnModel().getColumn(2).setPreferredWidth(120); // Last Name
        customersTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // Customer ID
        customersTable.getColumnModel().getColumn(4).setPreferredWidth(70);  // Member
        customersTable.getColumnModel().getColumn(5).setPreferredWidth(100); // Balance
        customersTable.getColumnModel().getColumn(6).setPreferredWidth(120); // Concerts Purchased
        customersTable.getColumnModel().getColumn(7).setPreferredWidth(100); // Transactions
        customersTable.getColumnModel().getColumn(8).setPreferredWidth(120); // Lifetime Savings

        customersScrollPane = new JScrollPane(customersTable);
        customersScrollPane.setOpaque(false);
        customersScrollPane.getViewport().setOpaque(false);
        customersScrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        customersScrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());

        // Create details panel for displaying detailed information
        detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(SURFACE_COLOR);
        detailsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        detailsScrollPane = new JScrollPane(detailsPanel);
        detailsScrollPane.setOpaque(false);
        detailsScrollPane.getViewport().setOpaque(false);
        detailsScrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        detailsScrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());

        // Create view indicator label
        viewIndicatorLabel = new JLabel("View: Events");
        viewIndicatorLabel.setFont(new Font("-apple-system", Font.BOLD, 14));
        viewIndicatorLabel.setForeground(ACCENT_PRIMARY);
        viewIndicatorLabel.setBorder(new EmptyBorder(10, 20, 5, 20));
        viewIndicatorLabel.setOpaque(false);

        // Create card layout for switching between views
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);
        
        // Add all views to card panel (including log as a main view)
        cardPanel.add(eventsScrollPane, "EVENTS");
        cardPanel.add(customersScrollPane, "CUSTOMERS");
        cardPanel.add(detailsScrollPane, "DETAILS");
        cardPanel.add(scrollPane, "LOG");
        
        // Default to events view
        cardLayout.show(cardPanel, "EVENTS");

        // Create top panel with view indicator
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(viewIndicatorLabel, BorderLayout.WEST);
        
        // Create container for top panel and card panel
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setOpaque(false);
        tableContainer.add(topPanel, BorderLayout.NORTH);
        tableContainer.add(cardPanel, BorderLayout.CENTER);

        // All UI is now in the main panel - no split pane needed
        mainPanel.add(tableContainer, BorderLayout.CENTER);
        mainPanel.add(controlWrapper, BorderLayout.SOUTH);
        
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    /**
     * Clears the control panel to prepare for new input components.
     */
    private void clearControlPanel() {
        controlPanel.removeAll();
        controlPanel.revalidate();
        controlPanel.repaint();
    }

    /**
     * Appends text to the display area.
     */
    private void appendText(String text) {
        SwingUtilities.invokeLater(() -> {
            displayArea.append(text + "\n");
            displayArea.setCaretPosition(displayArea.getDocument().getLength());
        });
    }
    
    /**
     * Public method to append text to the display area.
     */
    public void appendTextToDisplay(String text) {
        appendText(text);
    }

    /**
     * Helper to create styled labels.
     */
    private JLabel createLabel(String text, int size, int style) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("-apple-system", style, size));
        l.setForeground(TEXT_PRIMARY);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    /**
     * Helper to create styled text fields.
     */
    private JTextField createTextField() {
        JTextField tf = new JTextField(20);
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        tf.setBackground(SURFACE_COLOR);
        tf.setForeground(TEXT_PRIMARY);
        tf.setCaretColor(ACCENT_PRIMARY);
        tf.setBorder(BorderFactory.createCompoundBorder(
            new javax.swing.border.LineBorder(BORDER_COLOR),
            new EmptyBorder(5, 5, 5, 5)));
        tf.setAlignmentX(Component.LEFT_ALIGNMENT);
        return tf;
    }

    /**
     * Helper to create styled buttons.
     */
    private JButton createModernButton(String text) {
        JButton btn = new JButton(text);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(false);
        btn.setBackground(ACCENT_PRIMARY);
        btn.setForeground(TEXT_PRIMARY);
        btn.setFocusPainted(false);
        btn.setFont(new Font("-apple-system", Font.BOLD, 14));
        return btn;
    }

    // ===========================================================================
    // BLOCKING INPUT METHODS
    // These methods pause the controller thread until the user interacts with the GUI
    // ===========================================================================

    /**
     * Gets string input from user via inline form in control panel.
     * Blocks the calling thread until input is submitted.
     *
     * @param message the prompt message
     * @param title the form title
     * @return the user's input string, or null if cancelled
     */
    public String getStringInput(String message, String title) {
        synchronized (inputLock) {
            stringInputReady = false;
            stringInputResult = null;
        }

        SwingUtilities.invokeLater(() -> {
            clearControlPanel();
            
            controlPanel.add(createLabel(title, 18, Font.BOLD));
            controlPanel.add(Box.createVerticalStrut(10));
            controlPanel.add(createLabel(message, 14, Font.PLAIN));
            controlPanel.add(Box.createVerticalStrut(10));
            
            JTextField tfInput = createTextField();
            controlPanel.add(tfInput);
            controlPanel.add(Box.createVerticalStrut(10));
            
            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            btnPanel.setBackground(BACKGROUND_DARK);
            
            JButton btnSubmit = createModernButton("Submit");
            btnSubmit.addActionListener(e -> {
                synchronized (inputLock) {
                    stringInputResult = tfInput.getText();
                    stringInputReady = true;
                    inputLock.notifyAll();
                }
                appendText(title + ": " + tfInput.getText());
            });
            
            JButton btnBack = createModernButton("Back");
            btnBack.setBackground(ERROR_COLOR);
            btnBack.addActionListener(e -> {
                synchronized (inputLock) {
                    stringInputResult = null;
                    stringInputReady = true;
                    inputLock.notifyAll();
                }
            });
            
            // Allow Enter key to submit
            tfInput.addActionListener(e -> btnSubmit.doClick());
            
            btnPanel.add(btnSubmit);
            btnPanel.add(btnBack);
            controlPanel.add(btnPanel);
            controlPanel.revalidate();
            controlPanel.repaint();
            tfInput.requestFocusInWindow();
        });

        synchronized (inputLock) {
            while (!stringInputReady) {
                try {
                    inputLock.wait();
                } catch (InterruptedException e) {
                    return null;
                }
            }
        }
        return stringInputResult;
    }

    /**
     * Gets integer input from user via input form.
     *
     * @param message the prompt message
     * @param title the dialog title
     * @return the user's input integer, or -1 if cancelled/invalid
     */
    public int getIntInput(String message, String title) {
        String input = getStringInput(message, title);
        if (input == null) return -1;
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            showErrorDialog("Invalid number. Please enter digits only.");
            return -1;
        }
    }

    /**
     * Gets double input from user via input form.
     *
     * @param message the prompt message
     * @param title the dialog title
     * @return the user's input double, or -1 if cancelled/invalid
     */
    public double getDoubleInput(String message, String title) {
        String input = getStringInput(message, title);
        if (input == null) return -1;
        try {
            return Double.parseDouble(input.trim());
        } catch (NumberFormatException e) {
            showErrorDialog("Invalid number. Please enter a valid number.");
            return -1;
        }
    }

    /**
     * Shows option buttons inline in control panel for user selection.
     * Blocks the calling thread until an option is selected.
     *
     * @param messageText the message to display
     * @param titleText the form title
     * @param options array of option strings
     * @return the selected option index, or -1 if cancelled
     */
    public int showOptionDialog(String messageText, String titleText, String[] options) {
        synchronized (inputLock) {
            optionDialogReady = false;
            optionDialogResult = -1;
        }

        SwingUtilities.invokeLater(() -> {
            clearControlPanel();
            controlPanel.add(createLabel(titleText, 18, Font.BOLD));
            controlPanel.add(Box.createVerticalStrut(5));
            controlPanel.add(createLabel(messageText, 14, Font.PLAIN));
            controlPanel.add(Box.createVerticalStrut(15));

            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            btnPanel.setBackground(BACKGROUND_DARK);

            for (int i = 0; i < options.length; i++) {
                int index = i;
                JButton btn = createModernButton(options[i]);
                btn.addActionListener(e -> {
                    synchronized (inputLock) {
                        optionDialogResult = index;
                        optionDialogReady = true;
                        inputLock.notifyAll();
                    }
                if (options[index].equalsIgnoreCase("Exit") && 
                    titleText.equals("Select Role")) {
                        RunTicket.exitProgram();
                    }});
                btnPanel.add(btn);
            }
            
            // Add Close System button
            JButton closeSystemBtn = createModernButton("Close System");
            closeSystemBtn.setBackground(ERROR_COLOR);
            closeSystemBtn.addActionListener(e -> {
                synchronized (inputLock) {
                    optionDialogResult = -1;
                    optionDialogReady = true;
                    inputLock.notifyAll();
                }
                // Close the application
                SwingUtilities.invokeLater(() -> {
                    if (mainFrame != null) {
                        mainFrame.dispose();
                    }
                    RunTicket.exitProgram();
                });
            });
            btnPanel.add(closeSystemBtn);
            
            controlPanel.add(btnPanel);
            controlPanel.revalidate();
            controlPanel.repaint();
        });

        synchronized (inputLock) {
            while (!optionDialogReady) {
                try {
                    inputLock.wait();
                } catch (InterruptedException e) {
                    return -1;
                }
            }
        }
        return optionDialogResult;
    }

    /**
     * Shows yes/no buttons inline in control panel.
     *
     * @param message the message to display
     * @param title the form title
     * @return true if yes, false if no or cancelled
     */
    public boolean showYesNoDialog(String message, String title) {
        int result = showOptionDialog(message, title, new String[]{"Yes", "No"});
        return result == 0;
    }

    // ===========================================================================
    // SPECIFIC MENU WRAPPERS
    // ===========================================================================

    public String getRoleSelection() {
        String[] options = {"Customer", "System Administrator", "Exit"};
        int choice = showOptionDialog("Are you a Customer, or a System Administrator?", "Select Role", options);
        if (choice == 2 || choice == -1) {
            return "exit";
        }
        return options[choice].toLowerCase();
    }

    public String getCustomerAction() {
        String[] options = {"Login", "Exit"};
        int choice = showOptionDialog("Welcome! Do you want to log in?", "Customer Welcome", options);
        if (choice == 1 || choice == -1) {
            return "exit";
        }
        return options[choice].toLowerCase();
    }

    public String getPurchaseMenu() {
        String[] options = {"View Events", "Purchase", "View Invoices", "View My Info", "Cancel Order", "Logout"};
        int choice = showOptionDialog("Select an action:", "Customer Menu", options);
        if (choice == -1) return "logout";
        return options[choice].toLowerCase();
    }

    public String getSysadminMenuSelection() {
        String[] options = {"View Users", "View Events", "Enquire Event", "Add Event", "Run Autopurchaser", "View Event Fees", "View All Fees", "Cancel Event", "Exit"};
        int choice = showOptionDialog("System Administrator Actions:", "Admin Menu", options);
        if (choice == -1) return "exit";
        return options[choice].toLowerCase();
    }

    // ===========================================================================
    // DISPLAY METHODS
    // ===========================================================================

    public JFrame getMainFrame() { return mainFrame; }
    public void displayWelcomeMessage() { appendText("----- Welcome to TicketMiner! -----"); }
    public void displayRolePrompt() { appendText("Please select your role."); }
    public void displayGoodbye() { appendText("Goodbye!"); }
    public void displayInvalidChoice() { showErrorDialog("Invalid choice!"); }
    public void displaySysadminWelcome() { appendText("Welcome System Administrator!"); }
    public void displaySysadminMenu() { appendText("Select an option from the menu."); }
    public void displayInvalidInput() { showErrorDialog("Invalid input"); }
    public void displayInquireEventPrompt() { appendText("Inquire about an event."); }
    public void displayEventNotFound() { showErrorDialog("Event not found"); }
    public void displayError(String message) { showErrorDialog(message); }
    
    public void displayNewEventPrompt() { 
        appendText("Enter the new Event's Information");
        // Clear and show starting message in details panel
        SwingUtilities.invokeLater(() -> {
            clearDetailsPanel();
            switchView("DETAILS");
            JLabel header = new JLabel("Add New Event");
            header.setFont(new Font("-apple-system", Font.BOLD, 20));
            header.setForeground(ACCENT_PRIMARY);
            header.setAlignmentX(Component.LEFT_ALIGNMENT);
            header.setBorder(new EmptyBorder(0, 0, 20, 0));
            detailsPanel.add(header);
            JLabel prompt = new JLabel("Please fill in the event information below.");
            prompt.setFont(new Font("-apple-system", Font.PLAIN, 14));
            prompt.setForeground(TEXT_SECONDARY);
            prompt.setAlignmentX(Component.LEFT_ALIGNMENT);
            prompt.setBorder(new EmptyBorder(0, 0, 20, 0));
            detailsPanel.add(prompt);
            detailsPanel.revalidate();
            detailsPanel.repaint();
        });
    }
    public void displayInvalidEventType() { showErrorDialog("Not a valid event type"); }
    public void displayEventNamePrompt() { appendText("Enter Event Name:"); }
    public void displayEventDatePrompt() { appendText("Enter Event Date:"); }
    public void displayEventTimePrompt() { appendText("Enter Event Time:"); }
    public void displayVenuePrompt() { appendText("Select Event Venue:"); }
    public void displayInvalidVenueChoice() { showErrorDialog("Invalid venue choice"); }
    public void displayGAPricePrompt() { appendText("Enter General Admission Price:"); }
    public void displayInvalidPrice() { showErrorDialog("Invalid Price"); }
    public void displayFireworksPrompt() { appendText("Fireworks Check"); }
    public void displayFireworksCostPrompt() { appendText("Enter Fireworks Cost:"); }
    public void displayEventCostPrompt() { appendText("Enter Event Base Cost:"); }
    public void displayCustomerWelcome() { appendText("Customer Portal"); }
    public void displayInvalidCustomerChoice() { showErrorDialog("Invalid Choice"); }
    public void displayUsernamePrompt() { appendText("Enter Username:"); }
    public void displayPasswordPrompt() { appendText("Enter Password:"); }
    public void displayLoginFailure() { showErrorDialog("Login failed."); }
    
    public void displayCustomerGreeting(String firstName, String lastName) {
        appendText("Hello, " + firstName + " " + lastName + "!");
    }

    public void displayBalance(double balance) {
        appendText(String.format("Balance: $%.2f", balance));
    }

    public void displayCustomerMenu() { appendText("Customer Menu Options"); }

    /**
     * Switches the view to show events, customers, details, or log
     * @param viewName "EVENTS", "CUSTOMERS", "DETAILS", or "LOG"
     */
    public void switchView(String viewName) {
        SwingUtilities.invokeLater(() -> {
            if (viewName.equalsIgnoreCase("EVENTS")) {
                cardLayout.show(cardPanel, "EVENTS");
                viewIndicatorLabel.setText("View: Events");
                mainFrame.setTitle("TicketMiner - Events");
            } else if (viewName.equalsIgnoreCase("CUSTOMERS")) {
                cardLayout.show(cardPanel, "CUSTOMERS");
                viewIndicatorLabel.setText("View: Customers");
                mainFrame.setTitle("TicketMiner - Customers");
            } else if (viewName.equalsIgnoreCase("DETAILS")) {
                cardLayout.show(cardPanel, "DETAILS");
                viewIndicatorLabel.setText("View: Details");
                mainFrame.setTitle("TicketMiner - Details");
            } else if (viewName.equalsIgnoreCase("LOG")) {
                cardLayout.show(cardPanel, "LOG");
                viewIndicatorLabel.setText("View: Activity Log");
                mainFrame.setTitle("TicketMiner - Activity Log");
            }
        });
    }
    
    /**
     * Clears the details panel
     */
    private void clearDetailsPanel() {
        SwingUtilities.invokeLater(() -> {
            detailsPanel.removeAll();
            detailsPanel.revalidate();
            detailsPanel.repaint();
        });
    }
    
    /**
     * Creates a styled section label for the details panel
     */
    private JLabel createSectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("-apple-system", Font.BOLD, 16));
        label.setForeground(ACCENT_PRIMARY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(new EmptyBorder(15, 0, 10, 0));
        return label;
    }
    
    /**
     * Creates a styled info label for the details panel
     */
    private JLabel createInfoLabel(String label, String value) {
        JLabel infoLabel = new JLabel("<html><div style='width: 100%;'><span style='color: " + 
            String.format("#%02x%02x%02x", TEXT_SECONDARY.getRed(), TEXT_SECONDARY.getGreen(), TEXT_SECONDARY.getBlue()) + 
            ";'>" + label + ":</span> <span style='color: " + 
            String.format("#%02x%02x%02x", TEXT_PRIMARY.getRed(), TEXT_PRIMARY.getGreen(), TEXT_PRIMARY.getBlue()) + 
            "; font-weight: 500;'>" + value + "</span></div></html>");
        infoLabel.setFont(new Font("-apple-system", Font.PLAIN, 14));
        infoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoLabel.setBorder(new EmptyBorder(5, 0, 5, 0));
        return infoLabel;
    }
    
    /**
     * Creates a styled separator for the details panel
     */
    private Component createSeparator() {
        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setForeground(BORDER_COLOR);
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        separator.setBorder(new EmptyBorder(10, 0, 10, 0));
        return separator;
    }

    /**
     * Updates the events table in the main window with the provided list of
     * events. Shows enhanced information including seats available and pricing.
     *
     * @param eventList A HashMap containing all available events
     */
    public void displayAvailableEvents(ConcurrentHashMap<Integer, Event> eventList) {
        SwingUtilities.invokeLater(() -> {
            // Switch view directly (we're already on EDT, no need for nested invokeLater)
            cardLayout.show(cardPanel, "EVENTS");
            viewIndicatorLabel.setText("View: Events");
            mainFrame.setTitle("TicketMiner - Events");
            
            eventsTableModel.setRowCount(0);
            for (Event event : eventList.values()) {
                int totalSeatsLeft = event.getSeatsLeft("Total");
                Object[] rowData = {
                    event.getEventID(),
                    event.getEventName(),
                    event.getEventDate(),
                    event.getEventTime(),
                    event.getEventType(),
                    event.getEventVenue().getVenueName(),
                    event.getEventVenue().getVenueCapacity(),
                    totalSeatsLeft,
                    String.format("$%.2f", event.getVIPPrice()),
                    String.format("$%.2f", event.getGoldPrice()),
                    String.format("$%.2f", event.getSilverPrice()),
                    String.format("$%.2f", event.getBronzePrice()),
                    String.format("$%.2f", event.getGAPrice())
                };
                eventsTableModel.addRow(rowData);
            }
        });
        appendText("Displaying all available events with pricing and availability.");
    }

    /**
     * Updates the customers table in the main window with the provided list of
     * customers.
     *
     * @param customerList A HashMap containing all customers
     */
    public void displayCustomers(ConcurrentHashMap<String, Customer> customerList) {
        SwingUtilities.invokeLater(() -> {
            // Switch view directly (we're already on EDT, no need for nested invokeLater)
            cardLayout.show(cardPanel, "CUSTOMERS");
            viewIndicatorLabel.setText("View: Customers");
            mainFrame.setTitle("TicketMiner - Customers");
            
            customersTableModel.setRowCount(0);
            for (Customer customer : customerList.values()) {
                Object[] rowData = {
                    customer.getUsername(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getCustomerID(),
                    customer.getMemberStatus() ? "Yes" : "No",
                    String.format("$%.2f", customer.getBalance()),
                    customer.getConcertsPurchased(),
                    customer.getTransactionCount(),
                    String.format("$%.2f", customer.getTotalSavings())
                };
                customersTableModel.addRow(rowData);
            }
        });
        appendText("Displaying all customers.");
    }
    /**
     * Displays the list of venues in the display area.
     * @param venueList
     */
    public void displayVenues(ConcurrentHashMap<String, Venue> venueList) {
        StringBuilder sb = new StringBuilder("Available Venues:\n");
        for (String key : venueList.keySet()) {
            sb.append("- ").append(key).append("\n");
        }
        appendText(sb.toString());
    }
    /**
     * Displays detailed revenue report for a specific event in the details panel.
     * @param event The event for which to display the revenue report
     */
    public void displayEventRevenue(Event event) {
        SwingUtilities.invokeLater(() -> {
            // Clear panel synchronously within the same EDT block
            detailsPanel.removeAll();
            
            // Switch view directly (we're already on EDT, no need for nested invokeLater)
            cardLayout.show(cardPanel, "DETAILS");
            viewIndicatorLabel.setText("View: Details");
            mainFrame.setTitle("TicketMiner - Details");
            
            // Calculate total seats sold by summing all tiers
            int totalSeatsSold = event.getSeatsSold("VIP") + event.getSeatsSold("Gold") + 
                                 event.getSeatsSold("Silver") + event.getSeatsSold("Bronze") + 
                                 event.getSeatsSold("General Admission");
            
            // Calculate total revenue from all tiers
            double totalRevenue = event.getTicketsRevenue("VIP") + event.getTicketsRevenue("Gold") + 
                                 event.getTicketsRevenue("Silver") + event.getTicketsRevenue("Bronze") + 
                                 event.getTicketsRevenue("General Admission");
            
            // Header
            JLabel header = new JLabel("Event Revenue Report");
            header.setFont(new Font("-apple-system", Font.BOLD, 20));
            header.setForeground(ACCENT_PRIMARY);
            header.setAlignmentX(Component.LEFT_ALIGNMENT);
            header.setBorder(new EmptyBorder(0, 0, 20, 0));
            detailsPanel.add(header);
            
            // Event Information Section
            detailsPanel.add(createSectionLabel("Event Information"));
            detailsPanel.add(createInfoLabel("Event ID", String.valueOf(event.getEventID())));
            detailsPanel.add(createInfoLabel("Event Name", event.getEventName()));
            detailsPanel.add(createInfoLabel("Date and Time", event.getEventDate() + " at " + event.getEventTime()));
            detailsPanel.add(createInfoLabel("Event Type", event.getEventType()));
            detailsPanel.add(createInfoLabel("Venue", event.getEventVenue().getVenueName() + " (" + event.getEventVenue().getVenueType() + ")"));
            detailsPanel.add(createInfoLabel("Capacity", String.valueOf(event.getEventVenue().getVenueCapacity())));
            
            detailsPanel.add(createSeparator());
            
            // Seats Sold Section
            detailsPanel.add(createSectionLabel("Seats Sold"));
            detailsPanel.add(createInfoLabel("Total Seats Sold", String.valueOf(totalSeatsSold)));
            detailsPanel.add(createInfoLabel("VIP Seats Sold", String.valueOf(event.getSeatsSold("VIP"))));
            detailsPanel.add(createInfoLabel("Gold Seats Sold", String.valueOf(event.getSeatsSold("Gold"))));
            detailsPanel.add(createInfoLabel("Silver Seats Sold", String.valueOf(event.getSeatsSold("Silver"))));
            detailsPanel.add(createInfoLabel("Bronze Seats Sold", String.valueOf(event.getSeatsSold("Bronze"))));
            detailsPanel.add(createInfoLabel("General Admission Seats Sold", String.valueOf(event.getSeatsSold("General Admission"))));
            
            detailsPanel.add(createSeparator());
            
            // Revenue Section
            detailsPanel.add(createSectionLabel("Revenue by Tier"));
            detailsPanel.add(createInfoLabel("VIP Revenue", String.format("$%.2f", event.getTicketsRevenue("VIP"))));
            detailsPanel.add(createInfoLabel("Gold Revenue", String.format("$%.2f", event.getTicketsRevenue("Gold"))));
            detailsPanel.add(createInfoLabel("Silver Revenue", String.format("$%.2f", event.getTicketsRevenue("Silver"))));
            detailsPanel.add(createInfoLabel("Bronze Revenue", String.format("$%.2f", event.getTicketsRevenue("Bronze"))));
            detailsPanel.add(createInfoLabel("General Admission Revenue", String.format("$%.2f", event.getTicketsRevenue("General Admission"))));
            
            detailsPanel.add(createSeparator());
            
            // Financial Summary Section
            detailsPanel.add(createSectionLabel("Financial Summary"));
            detailsPanel.add(createInfoLabel("Total Revenue", String.format("$%.2f", totalRevenue)));
            detailsPanel.add(createInfoLabel("Expected Profit (Sold Out)", String.format("$%.2f", event.getExpectedRevenue())));
            detailsPanel.add(createInfoLabel("Actual Profit", String.format("$%.2f", event.getEventCost() * -1)));
            if (event.getFireworks()) {
                detailsPanel.add(createInfoLabel("Firework Costs", String.format("$%.2f", event.getFireworkCost())));
            }
            detailsPanel.add(createInfoLabel("Tax Revenue", String.format("$%.2f", event.getTaxRevenue())));
            detailsPanel.add(createInfoLabel("Discount Costs", String.format("$%.2f", -1 * event.getDiscountCosts())));
            
            detailsPanel.add(Box.createVerticalGlue());
            detailsPanel.revalidate();
            detailsPanel.repaint();
        });
        
        // Also log to text area for reference
        appendText("Event Revenue Report displayed for: " + event.getEventName());
    }
    
    /**
     * Displays fee information for a specific event
     * @param event The event for which to display fees
     */
    public void displayEventFees(Event event) {
        SwingUtilities.invokeLater(() -> {
            // Clear panel synchronously within the same EDT block
            detailsPanel.removeAll();
            
            // Switch view directly
            cardLayout.show(cardPanel, "DETAILS");
            viewIndicatorLabel.setText("View: Details");
            mainFrame.setTitle("TicketMiner - Event Fees");
            
            // Header
            JLabel header = new JLabel("TicketMiner Company Fees - Event Report");
            header.setFont(new Font("-apple-system", Font.BOLD, 20));
            header.setForeground(ACCENT_PRIMARY);
            header.setAlignmentX(Component.LEFT_ALIGNMENT);
            header.setBorder(new EmptyBorder(0, 0, 20, 0));
            detailsPanel.add(header);
            
            // Event Information Section
            detailsPanel.add(createSectionLabel("Event Information"));
            detailsPanel.add(createInfoLabel("Event ID", String.valueOf(event.getEventID())));
            detailsPanel.add(createInfoLabel("Event Name", event.getEventName()));
            detailsPanel.add(createInfoLabel("Date and Time", event.getEventDate() + " at " + event.getEventTime()));
            detailsPanel.add(createInfoLabel("Event Type", event.getEventType()));
            
            detailsPanel.add(createSeparator());
            
            // Fees Section
            detailsPanel.add(createSectionLabel("TicketMiner Company Fees"));
            detailsPanel.add(createInfoLabel("Service Fees (Ticket Fees)", String.format("$%.2f", event.getTicketFees())));
            detailsPanel.add(createInfoLabel("Convenience Fees", String.format("$%.2f", event.getConvenienceFees())));
            detailsPanel.add(createInfoLabel("Charity Fees", String.format("$%.2f", event.getCharityFees())));
            detailsPanel.add(createInfoLabel("Total Fees", String.format("$%.2f", event.getTotalFees())));
            
            detailsPanel.add(Box.createVerticalGlue());
            detailsPanel.revalidate();
            detailsPanel.repaint();
        });
        
        // Also log to text area for reference
        appendText("Event Fees Report displayed for: " + event.getEventName());
    }
    
    /**
     * Displays fee information for all events
     * @param totalTicketFees Total service fees across all events
     * @param totalConvenienceFees Total convenience fees across all events
     * @param totalCharityFees Total charity fees across all events
     * @param grandTotalFees Total fees across all events
     */
    public void displayAllFees(double totalTicketFees, double totalConvenienceFees, double totalCharityFees, double grandTotalFees) {
        SwingUtilities.invokeLater(() -> {
            // Clear panel synchronously within the same EDT block
            detailsPanel.removeAll();
            
            // Switch view directly
            cardLayout.show(cardPanel, "DETAILS");
            viewIndicatorLabel.setText("View: Details");
            mainFrame.setTitle("TicketMiner - All Fees");
            
            // Header
            JLabel header = new JLabel("TicketMiner Company Fees - All Events Report");
            header.setFont(new Font("-apple-system", Font.BOLD, 20));
            header.setForeground(ACCENT_PRIMARY);
            header.setAlignmentX(Component.LEFT_ALIGNMENT);
            header.setBorder(new EmptyBorder(0, 0, 20, 0));
            detailsPanel.add(header);
            
            // Fees Section
            detailsPanel.add(createSectionLabel("Total Fees Across All Events"));
            detailsPanel.add(createInfoLabel("Service Fees (Ticket Fees)", String.format("$%.2f", totalTicketFees)));
            detailsPanel.add(createInfoLabel("Convenience Fees", String.format("$%.2f", totalConvenienceFees)));
            detailsPanel.add(createInfoLabel("Total Fees", String.format("$%.2f", grandTotalFees)));
            
            detailsPanel.add(Box.createVerticalGlue());
            detailsPanel.revalidate();
            detailsPanel.repaint();
        });
        
        // Also log to text area for reference
        appendText("All Events Fees Report displayed");
        appendText(String.format("Service Fees: $%.2f", totalTicketFees));
        appendText(String.format("Convenience Fees: $%.2f", totalConvenienceFees));
        appendText(String.format("Total Fees: $%.2f", grandTotalFees));
    }
    
    /**
     * Displays event cancellation results
     * @param event The cancelled event
     * @param refundCount Number of customers refunded
     * @param totalRefunded Total amount refunded
     */
    public void displayEventCancellationResults(Event event, int refundCount, double totalRefunded) {
        SwingUtilities.invokeLater(() -> {
            // Clear panel synchronously within the same EDT block
            detailsPanel.removeAll();
            
            // Switch view directly
            cardLayout.show(cardPanel, "DETAILS");
            viewIndicatorLabel.setText("View: Details");
            mainFrame.setTitle("TicketMiner - Event Cancellation");
            
            // Header
            JLabel header = new JLabel("Event Cancellation Complete");
            header.setFont(new Font("-apple-system", Font.BOLD, 20));
            header.setForeground(ACCENT_PRIMARY);
            header.setAlignmentX(Component.LEFT_ALIGNMENT);
            header.setBorder(new EmptyBorder(0, 0, 20, 0));
            detailsPanel.add(header);
            
            // Event Information Section
            detailsPanel.add(createSectionLabel("Cancelled Event"));
            detailsPanel.add(createInfoLabel("Event ID", String.valueOf(event.getEventID())));
            detailsPanel.add(createInfoLabel("Event Name", event.getEventName()));
            detailsPanel.add(createInfoLabel("Date and Time", event.getEventDate() + " at " + event.getEventTime()));
            
            detailsPanel.add(createSeparator());
            
            // Refund Summary Section
            detailsPanel.add(createSectionLabel("Refund Summary"));
            detailsPanel.add(createInfoLabel("Customers Refunded", String.valueOf(refundCount)));
            detailsPanel.add(createInfoLabel("Total Amount Refunded", String.format("$%.2f", totalRefunded)));
            detailsPanel.add(createInfoLabel("Note", "All customers have been refunded including service fees."));
            
            detailsPanel.add(Box.createVerticalGlue());
            detailsPanel.revalidate();
            detailsPanel.repaint();
        });
        
        // Also log to text area for reference
        appendText("Event cancelled: " + event.getEventName());
        appendText("Customers refunded: " + refundCount);
        appendText(String.format("Total refunded: $%.2f", totalRefunded));
    }
    
    /**
     * Displays detailed information about a specific event in the details panel,
     * including seat availability.
     * @param event The event for which to display details
     */
    public void displayEventDetailsWithCapacity(Event event) {
        SwingUtilities.invokeLater(() -> {
            clearDetailsPanel();
            switchView("DETAILS");
            
            // Header
            JLabel header = new JLabel("Event Details");
            header.setFont(new Font("-apple-system", Font.BOLD, 20));
            header.setForeground(ACCENT_PRIMARY);
            header.setAlignmentX(Component.LEFT_ALIGNMENT);
            header.setBorder(new EmptyBorder(0, 0, 20, 0));
            detailsPanel.add(header);
            
            // Event Information Section
            detailsPanel.add(createSectionLabel("Event Information"));
            detailsPanel.add(createInfoLabel("Event ID", String.valueOf(event.getEventID())));
            detailsPanel.add(createInfoLabel("Event Name", event.getEventName()));
            detailsPanel.add(createInfoLabel("Date and Time", event.getEventDate() + " at " + event.getEventTime()));
            detailsPanel.add(createInfoLabel("Event Type", event.getEventType()));
            detailsPanel.add(createInfoLabel("Venue", event.getEventVenue().getVenueName() + " (" + event.getEventVenue().getVenueType() + ")"));
            detailsPanel.add(createInfoLabel("Total Capacity", String.valueOf(event.getEventVenue().getVenueCapacity())));
            
            detailsPanel.add(createSeparator());
            
            // Seats Available Section
            detailsPanel.add(createSectionLabel("Seats Available"));
            detailsPanel.add(createInfoLabel("Total Seats Left", String.valueOf(event.getSeatsLeft("Total"))));
            detailsPanel.add(createInfoLabel("VIP Seats Left", String.valueOf(event.getSeatsLeft("VIP"))));
            detailsPanel.add(createInfoLabel("Gold Seats Left", String.valueOf(event.getSeatsLeft("Gold"))));
            detailsPanel.add(createInfoLabel("Silver Seats Left", String.valueOf(event.getSeatsLeft("Silver"))));
            detailsPanel.add(createInfoLabel("Bronze Seats Left", String.valueOf(event.getSeatsLeft("Bronze"))));
            detailsPanel.add(createInfoLabel("General Admission Seats Left", String.valueOf(event.getSeatsLeft("General Admission"))));
            
            detailsPanel.add(createSeparator());
            
            // Ticket Prices Section
            detailsPanel.add(createSectionLabel("Ticket Prices"));
            detailsPanel.add(createInfoLabel("VIP", String.format("$%.2f", event.getVIPPrice())));
            detailsPanel.add(createInfoLabel("Gold", String.format("$%.2f", event.getGoldPrice())));
            detailsPanel.add(createInfoLabel("Silver", String.format("$%.2f", event.getSilverPrice())));
            detailsPanel.add(createInfoLabel("Bronze", String.format("$%.2f", event.getBronzePrice())));
            detailsPanel.add(createInfoLabel("General Admission", String.format("$%.2f", event.getGAPrice())));
            
            detailsPanel.add(Box.createVerticalGlue());
            detailsPanel.revalidate();
            detailsPanel.repaint();
        });
        
        // Also log to text area for reference
        appendText("Event details displayed: " + event.getEventName());
    }
    
    /**
     * Displays a confirmation message and the event details after successfully adding an event
     * @param event the event that was just added
     */
    public void displayEventAddedConfirmation(Event event) {
        // Show prominent dialog in the main frame
        SwingUtilities.invokeLater(() -> {
            String message = "<html><div style='text-align: center; padding: 10px;'>" +
                            "<h2 style='color: #34C759; margin: 10px 0;'>✓ Event Added Successfully!</h2>" +
                            "<p style='font-size: 18px; font-weight: bold; color: #007AFF; margin: 20px 0;'>" +
                            "Event ID: " + event.getEventID() + "</p>" +
                            "<p style='margin: 10px 0;'>Event Name: " + event.getEventName() + "</p>" +
                            "</div></html>";
            
            JOptionPane.showMessageDialog(
                mainFrame,
                message,
                "Event Added Successfully",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
        
        // Also display in the main panel (details view)
        SwingUtilities.invokeLater(() -> {
            clearDetailsPanel();
            switchView("DETAILS");
            
            // Success message with checkmark
            JLabel successHeader = new JLabel("✓ Event Added Successfully!");
            successHeader.setFont(new Font("-apple-system", Font.BOLD, 24));
            successHeader.setForeground(SUCCESS_COLOR);
            successHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
            successHeader.setBorder(new EmptyBorder(0, 0, 20, 0));
            detailsPanel.add(successHeader);
            
            // Event ID prominently displayed
            JLabel idLabel = new JLabel("Event ID: " + event.getEventID());
            idLabel.setFont(new Font("-apple-system", Font.BOLD, 18));
            idLabel.setForeground(ACCENT_PRIMARY);
            idLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            idLabel.setBorder(new EmptyBorder(0, 0, 30, 0));
            detailsPanel.add(idLabel);
            
            // Event Information Section
            detailsPanel.add(createSectionLabel("Event Information"));
            detailsPanel.add(createInfoLabel("Event Name", event.getEventName()));
            detailsPanel.add(createInfoLabel("Date and Time", event.getEventDate() + " at " + event.getEventTime()));
            detailsPanel.add(createInfoLabel("Event Type", event.getEventType()));
            detailsPanel.add(createInfoLabel("Venue", event.getEventVenue().getVenueName() + " (" + event.getEventVenue().getVenueType() + ")"));
            detailsPanel.add(createInfoLabel("Total Capacity", String.valueOf(event.getEventVenue().getVenueCapacity())));
            
            detailsPanel.add(createSeparator());
            
            // Ticket Prices Section
            detailsPanel.add(createSectionLabel("Ticket Prices"));
            detailsPanel.add(createInfoLabel("VIP", String.format("$%.2f", event.getVIPPrice())));
            detailsPanel.add(createInfoLabel("Gold", String.format("$%.2f", event.getGoldPrice())));
            detailsPanel.add(createInfoLabel("Silver", String.format("$%.2f", event.getSilverPrice())));
            detailsPanel.add(createInfoLabel("Bronze", String.format("$%.2f", event.getBronzePrice())));
            detailsPanel.add(createInfoLabel("General Admission", String.format("$%.2f", event.getGAPrice())));
            
            detailsPanel.add(createSeparator());
            
            // Additional Details Section
            detailsPanel.add(createSectionLabel("Additional Details"));
            detailsPanel.add(createInfoLabel("Fireworks", event.getFireworks() ? "Yes" : "No"));
            if (event.getFireworks()) {
                detailsPanel.add(createInfoLabel("Fireworks Cost", String.format("$%.2f", event.getFireworkCost())));
            }
            detailsPanel.add(createInfoLabel("Event Cost", String.format("$%.2f", event.getEventCost())));
            detailsPanel.add(createInfoLabel("Expected Revenue", String.format("$%.2f", event.getExpectedRevenue())));
            
            detailsPanel.add(Box.createVerticalGlue());
            detailsPanel.revalidate();
            detailsPanel.repaint();
        });
        
        // Also log to text area for reference
        appendText("Event added successfully! Event ID: " + event.getEventID() + " - " + event.getEventName());
    }
    /**
     * Displays detailed invoice information in the details panel.
     * @param invoice The invoice to display
     */
    public void displayInvoice(Invoice invoice) {
        SwingUtilities.invokeLater(() -> {
            clearDetailsPanel();
            switchView("DETAILS");
            
            // Header
            JLabel header = new JLabel("Invoice");
            header.setFont(new Font("-apple-system", Font.BOLD, 20));
            header.setForeground(ACCENT_PRIMARY);
            header.setAlignmentX(Component.LEFT_ALIGNMENT);
            header.setBorder(new EmptyBorder(0, 0, 20, 0));
            detailsPanel.add(header);
            
            // Customer Information Section
            detailsPanel.add(createSectionLabel("Customer Information"));
            detailsPanel.add(createInfoLabel("Customer Name", invoice.getCustomer().getFirstName() + " " + invoice.getCustomer().getLastName()));
            detailsPanel.add(createInfoLabel("Member Status", invoice.getCustomer().getMemberStatus() ? "TicketMiner Member" : "Standard Customer"));
            
            detailsPanel.add(createSeparator());
            
            // Event Information Section
            detailsPanel.add(createSectionLabel("Event Information"));
            detailsPanel.add(createInfoLabel("Event Name", invoice.getEvent().getEventName()));
            detailsPanel.add(createInfoLabel("Event Type", invoice.getEvent().getEventType()));
            detailsPanel.add(createInfoLabel("Location", invoice.getEvent().getEventVenue().getVenueName()));
            detailsPanel.add(createInfoLabel("Venue Type", invoice.getEvent().getEventVenue().getVenueType()));
            
            detailsPanel.add(createSeparator());
            
            // Ticket Information Section
            detailsPanel.add(createSectionLabel("Ticket Information"));
            detailsPanel.add(createInfoLabel("Ticket Type", invoice.getTicket().getTier()));
            detailsPanel.add(createInfoLabel("Quantity", String.valueOf(invoice.getTicket().getQuantity())));
            detailsPanel.add(createInfoLabel("Price per Ticket", String.format("$%.2f", invoice.getEvent().getTicketPrice(invoice.getTicket().getTier()))));
            
            detailsPanel.add(createSeparator());
            
            // Payment Information Section
            detailsPanel.add(createSectionLabel("Payment Information"));
            detailsPanel.add(createInfoLabel("Ticket Fee", String.format("$%.2f", invoice.getTicketFee())));
            detailsPanel.add(createInfoLabel("Convenience Fee", String.format("$%.2f", invoice.getConvenienceFee())));
            detailsPanel.add(createInfoLabel("Charity Fee", String.format("$%.2f", invoice.getCharityFee())));
            if (invoice.getCustomer().getMemberStatus()) {
                detailsPanel.add(createInfoLabel("Member Discount", String.format("-$%.2f", invoice.getMemberDiscount())));
                detailsPanel.add(createInfoLabel("Total (with Membership)", String.format("$%.2f", invoice.returnTotal())));
                JLabel savingsLabel = createInfoLabel("You Saved", String.format("$%.2f", invoice.getMemberDiscount()));
                savingsLabel.setForeground(SUCCESS_COLOR);
                detailsPanel.add(savingsLabel);
            } else {
                detailsPanel.add(createInfoLabel("Total", String.format("$%.2f", invoice.returnTotal())));
            }
            
            detailsPanel.add(createSeparator());
            
            // Invoice ID
            JLabel invoiceIdLabel = createInfoLabel("Invoice ID", invoice.getInvoiceID());
            invoiceIdLabel.setFont(new Font("-apple-system", Font.BOLD, 14));
            invoiceIdLabel.setForeground(ACCENT_SECONDARY);
            detailsPanel.add(invoiceIdLabel);
            
            detailsPanel.add(Box.createVerticalGlue());
            detailsPanel.revalidate();
            detailsPanel.repaint();
        });
        
        // Also log to text area for reference
        appendText("Invoice displayed: " + invoice.getInvoiceID());
    }
    /**
     * Displays a list of invoices in the details panel.
     * @param invoices A HashMap of invoice IDs to Invoice objects
     */
    public void displayInvoices(java.util.HashMap<String, Invoice> invoices) {
        if (invoices == null || invoices.isEmpty()) {
            SwingUtilities.invokeLater(() -> {
                // Clear panel synchronously within the same EDT block
                detailsPanel.removeAll();
                
                // Switch view directly
                cardLayout.show(cardPanel, "DETAILS");
                viewIndicatorLabel.setText("View: Details");
                mainFrame.setTitle("TicketMiner - Details");
                
                JLabel noInvoicesLabel = new JLabel("No invoices found.");
                noInvoicesLabel.setFont(new Font("-apple-system", Font.PLAIN, 16));
                noInvoicesLabel.setForeground(TEXT_SECONDARY);
                noInvoicesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                noInvoicesLabel.setBorder(new EmptyBorder(20, 0, 20, 0));
                detailsPanel.add(noInvoicesLabel);
                detailsPanel.revalidate();
                detailsPanel.repaint();
            });
            appendText("No invoices found.");
            return;
        }
        
        SwingUtilities.invokeLater(() -> {
            // Clear panel synchronously within the same EDT block (like displayEventRevenue does)
            detailsPanel.removeAll();
            
            // Switch view directly (we're already on EDT, no need for nested invokeLater)
            cardLayout.show(cardPanel, "DETAILS");
            viewIndicatorLabel.setText("View: Details");
            mainFrame.setTitle("TicketMiner - Details");
            
            // Header
            JLabel header = new JLabel("Your Invoices");
            header.setFont(new Font("-apple-system", Font.BOLD, 20));
            header.setForeground(ACCENT_PRIMARY);
            header.setAlignmentX(Component.LEFT_ALIGNMENT);
            header.setBorder(new EmptyBorder(0, 0, 20, 0));
            detailsPanel.add(header);
            
            // Create invoice cards
            int invoiceCount = 0;
            for (String invId : invoices.keySet()) {
                Invoice invoice = invoices.get(invId);
                invoiceCount++;
                
                // Invoice card container
                JPanel invoiceCard = new JPanel();
                invoiceCard.setLayout(new BoxLayout(invoiceCard, BoxLayout.Y_AXIS));
                invoiceCard.setBackground(new Color(SURFACE_COLOR.getRed() + 10, SURFACE_COLOR.getGreen() + 10, SURFACE_COLOR.getBlue() + 10));
                invoiceCard.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER_COLOR, 1),
                    new EmptyBorder(15, 15, 15, 15)
                ));
                invoiceCard.setAlignmentX(Component.LEFT_ALIGNMENT);
                invoiceCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
                
                // Invoice header
                JLabel invoiceHeader = new JLabel("Invoice #" + invoiceCount);
                invoiceHeader.setFont(new Font("-apple-system", Font.BOLD, 16));
                invoiceHeader.setForeground(ACCENT_SECONDARY);
                invoiceCard.add(invoiceHeader);
                
                invoiceCard.add(Box.createVerticalStrut(10));
                
                // Invoice ID - make it prominent
                JLabel invoiceIdLabel = new JLabel("Invoice ID: " + invoice.getInvoiceID());
                invoiceIdLabel.setFont(new Font("-apple-system", Font.BOLD, 14));
                invoiceIdLabel.setForeground(ACCENT_PRIMARY);
                invoiceIdLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                invoiceIdLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
                invoiceCard.add(invoiceIdLabel);
                
                invoiceCard.add(Box.createVerticalStrut(5));
                
                // Invoice details
                invoiceCard.add(createInfoLabel("Event", invoice.getEvent().getEventName()));
                invoiceCard.add(createInfoLabel("Ticket Type", invoice.getTicket().getTier()));
                invoiceCard.add(createInfoLabel("Quantity", String.valueOf(invoice.getTicket().getQuantity())));
                invoiceCard.add(createInfoLabel("Total", String.format("$%.2f", invoice.returnTotal())));
                if (invoice.getCustomer().getMemberStatus()) {
                    invoiceCard.add(createInfoLabel("Member Savings", String.format("$%.2f", invoice.getMemberDiscount())));
                }
                
                detailsPanel.add(invoiceCard);
                detailsPanel.add(Box.createVerticalStrut(15));
            }
            
            detailsPanel.add(Box.createVerticalGlue());
            detailsPanel.revalidate();
            detailsPanel.repaint();
        });
        
        appendText("Displaying " + invoices.size() + " invoice(s).");
    }

    /**
     * Displays receipt files for a customer in the details panel.
     * Reads receipt files from the receipts folder that match the customer's name.
     * 
     * @param customerFullName The full name of the customer (First Last)
     * @param append If true, appends receipts to existing content instead of clearing
     */
    public void displayReceipts(String customerFullName, boolean append) {
        SwingUtilities.invokeLater(() -> {
            if (!append) {
                clearDetailsPanel();
            }
            switchView("DETAILS");
            
            // Try multiple possible receipt folder locations
            File[] receiptFolders = {
                new File("receipts"),
                new File("src/main/java/com/runticket/receipts"),
                new File("src/main/java/com/runticket/receipts")
            };
            
            File receiptFolder = null;
            for (File folder : receiptFolders) {
                if (folder.exists() && folder.isDirectory()) {
                    receiptFolder = folder;
                    break;
                }
            }
            
            if (receiptFolder == null || !receiptFolder.exists()) {
                JLabel noReceiptsLabel = new JLabel("Receipts folder not found.");
                noReceiptsLabel.setFont(new Font("-apple-system", Font.PLAIN, 16));
                noReceiptsLabel.setForeground(TEXT_SECONDARY);
                noReceiptsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                noReceiptsLabel.setBorder(new EmptyBorder(20, 0, 20, 0));
                detailsPanel.add(noReceiptsLabel);
                detailsPanel.revalidate();
                detailsPanel.repaint();
                appendText("Receipts folder not found.");
                return;
            }
            
            // Find all receipt files for this customer
            // Receipt files are named: CustomerName_YYYY-MM-DD_HHMMSS.txt
            String safeCustomerName = customerFullName.replaceAll("\\s+", "_");
            File[] receiptFiles = receiptFolder.listFiles((dir, name) -> 
                name.startsWith(safeCustomerName) && name.endsWith(".txt"));
            
            if (receiptFiles == null || receiptFiles.length == 0) {
                JLabel noReceiptsLabel = new JLabel("No receipts found for " + customerFullName + ".");
                noReceiptsLabel.setFont(new Font("-apple-system", Font.PLAIN, 16));
                noReceiptsLabel.setForeground(TEXT_SECONDARY);
                noReceiptsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                noReceiptsLabel.setBorder(new EmptyBorder(20, 0, 20, 0));
                detailsPanel.add(noReceiptsLabel);
                detailsPanel.revalidate();
                detailsPanel.repaint();
                appendText("No receipts found for " + customerFullName + ".");
                return;
            }
            
            // Header
            JLabel header = new JLabel("Your Receipts");
            header.setFont(new Font("-apple-system", Font.BOLD, 20));
            header.setForeground(ACCENT_PRIMARY);
            header.setAlignmentX(Component.LEFT_ALIGNMENT);
            header.setBorder(new EmptyBorder(0, 0, 20, 0));
            detailsPanel.add(header);
            
            // Read and display each receipt
            int receiptCount = 0;
            for (File receiptFile : receiptFiles) {
                receiptCount++;
                try {
                    // Read receipt file content
                    List<String> receiptLines = new ArrayList<>();
                    try (BufferedReader reader = new BufferedReader(new FileReader(receiptFile))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            receiptLines.add(line);
                        }
                    }
                    
                    // Create receipt card
                    JPanel receiptCard = new JPanel();
                    receiptCard.setLayout(new BoxLayout(receiptCard, BoxLayout.Y_AXIS));
                    receiptCard.setBackground(new Color(SURFACE_COLOR.getRed() + 10, SURFACE_COLOR.getGreen() + 10, SURFACE_COLOR.getBlue() + 10));
                    receiptCard.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 1),
                        new EmptyBorder(15, 15, 15, 15)
                    ));
                    receiptCard.setAlignmentX(Component.LEFT_ALIGNMENT);
                    receiptCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
                    
                    // Receipt header
                    JLabel receiptHeader = new JLabel("Receipt #" + receiptCount);
                    receiptHeader.setFont(new Font("-apple-system", Font.BOLD, 16));
                    receiptHeader.setForeground(ACCENT_SECONDARY);
                    receiptCard.add(receiptHeader);
                    
                    receiptCard.add(Box.createVerticalStrut(10));
                    
                    // Display receipt content
                    for (String receiptLine : receiptLines) {
                        if (receiptLine.trim().isEmpty() || receiptLine.trim().equals("--------------------------------------")) {
                            receiptCard.add(createSeparator());
                            continue;
                        }
                        
                        // Parse and format receipt line
                        if (receiptLine.contains(":")) {
                            String[] parts = receiptLine.split(":", 2);
                            if (parts.length == 2) {
                                String label = parts[0].trim();
                                String value = parts[1].trim();
                                receiptCard.add(createInfoLabel(label, value));
                            } else {
                                JLabel lineLabel = new JLabel(receiptLine);
                                lineLabel.setFont(new Font("-apple-system", Font.PLAIN, 14));
                                lineLabel.setForeground(TEXT_PRIMARY);
                                lineLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                                lineLabel.setBorder(new EmptyBorder(5, 0, 5, 0));
                                receiptCard.add(lineLabel);
                            }
                        } else {
                            JLabel lineLabel = new JLabel(receiptLine);
                            lineLabel.setFont(new Font("-apple-system", Font.PLAIN, 14));
                            lineLabel.setForeground(TEXT_PRIMARY);
                            lineLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                            lineLabel.setBorder(new EmptyBorder(5, 0, 5, 0));
                            receiptCard.add(lineLabel);
                        }
                    }
                    
                    detailsPanel.add(receiptCard);
                    detailsPanel.add(Box.createVerticalStrut(15));
                    
                } catch (IOException e) {
                    JLabel errorLabel = new JLabel("Error reading receipt: " + receiptFile.getName());
                    errorLabel.setFont(new Font("-apple-system", Font.PLAIN, 14));
                    errorLabel.setForeground(ERROR_COLOR);
                    errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    detailsPanel.add(errorLabel);
                    appendText("Error reading receipt: " + receiptFile.getName());
                }
            }
            
            detailsPanel.add(Box.createVerticalGlue());
            detailsPanel.revalidate();
            detailsPanel.repaint();
        });
        
        appendText("Displaying receipt(s) for " + customerFullName + ".");
    }
    
    /**
     * Overloaded method for backward compatibility - clears panel by default
     */
    public void displayReceipts(String customerFullName) {
        displayReceipts(customerFullName, false);
    }
    
    /**
     * Displays both invoices and receipts for a customer in the details panel.
     * Shows invoices first, then receipts below them.
     * 
     * @param invoices The customer's invoices HashMap
     * @param customerFullName The full name of the customer (First Last)
     */
    public void displayInvoicesAndReceipts(java.util.HashMap<String, Invoice> invoices, String customerFullName) {
        SwingUtilities.invokeLater(() -> {
            // Clear panel synchronously within the same EDT block (like displayEventRevenue does)
            detailsPanel.removeAll();
            
            // Switch view directly (we're already on EDT, no need for nested invokeLater)
            cardLayout.show(cardPanel, "DETAILS");
            viewIndicatorLabel.setText("View: Details");
            mainFrame.setTitle("TicketMiner - Details");
            
            // Show invoices first
            if (invoices != null && !invoices.isEmpty()) {
                // Header
                JLabel header = new JLabel("Your Invoices");
                header.setFont(new Font("-apple-system", Font.BOLD, 20));
                header.setForeground(ACCENT_PRIMARY);
                header.setAlignmentX(Component.LEFT_ALIGNMENT);
                header.setBorder(new EmptyBorder(0, 0, 20, 0));
                detailsPanel.add(header);
                
                // Create invoice cards
                int invoiceCount = 0;
                for (String invId : invoices.keySet()) {
                    Invoice invoice = invoices.get(invId);
                    invoiceCount++;
                    
                    // Invoice card container
                    JPanel invoiceCard = new JPanel();
                    invoiceCard.setLayout(new BoxLayout(invoiceCard, BoxLayout.Y_AXIS));
                    invoiceCard.setBackground(new Color(SURFACE_COLOR.getRed() + 10, SURFACE_COLOR.getGreen() + 10, SURFACE_COLOR.getBlue() + 10));
                    invoiceCard.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 1),
                        new EmptyBorder(15, 15, 15, 15)
                    ));
                    invoiceCard.setAlignmentX(Component.LEFT_ALIGNMENT);
                    invoiceCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
                    
                    // Invoice header
                    JLabel invoiceHeader = new JLabel("Invoice #" + invoiceCount);
                    invoiceHeader.setFont(new Font("-apple-system", Font.BOLD, 16));
                    invoiceHeader.setForeground(ACCENT_SECONDARY);
                    invoiceCard.add(invoiceHeader);
                    
                    invoiceCard.add(Box.createVerticalStrut(10));
                    
                    // Invoice details
                    invoiceCard.add(createInfoLabel("Invoice ID", invoice.getInvoiceID()));
                    invoiceCard.add(createInfoLabel("Event", invoice.getEvent().getEventName()));
                    invoiceCard.add(createInfoLabel("Ticket Type", invoice.getTicket().getTier()));
                    invoiceCard.add(createInfoLabel("Quantity", String.valueOf(invoice.getTicket().getQuantity())));
                    invoiceCard.add(createInfoLabel("Total", String.format("$%.2f", invoice.returnTotal())));
                    if (invoice.getCustomer().getMemberStatus()) {
                        invoiceCard.add(createInfoLabel("Member Savings", String.format("$%.2f", invoice.getMemberDiscount())));
                    }
                    
                    detailsPanel.add(invoiceCard);
                    detailsPanel.add(Box.createVerticalStrut(15));
                }
                
                // Add separator before receipts
                detailsPanel.add(createSeparator());
                detailsPanel.add(Box.createVerticalStrut(20));
            } else {
                // Show message when no invoices
                JLabel header = new JLabel("Your Invoices");
                header.setFont(new Font("-apple-system", Font.BOLD, 20));
                header.setForeground(ACCENT_PRIMARY);
                header.setAlignmentX(Component.LEFT_ALIGNMENT);
                header.setBorder(new EmptyBorder(0, 0, 20, 0));
                detailsPanel.add(header);
                
                JLabel noInvoicesLabel = new JLabel("No invoices found. Purchase tickets to see your invoices here.");
                noInvoicesLabel.setFont(new Font("-apple-system", Font.PLAIN, 14));
                noInvoicesLabel.setForeground(TEXT_SECONDARY);
                noInvoicesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                noInvoicesLabel.setBorder(new EmptyBorder(10, 0, 20, 0));
                detailsPanel.add(noInvoicesLabel);
                
                // Add separator before receipts
                detailsPanel.add(createSeparator());
                detailsPanel.add(Box.createVerticalStrut(20));
            }
            
            // Now append receipts
            // Try multiple possible receipt folder locations
            File[] receiptFolders = {
                new File("receipts"),
                new File("src/main/java/com/runticket/receipts"),
                new File("src/main/java/com/runticket/receipts")
            };
            
            File receiptFolder = null;
            for (File folder : receiptFolders) {
                if (folder.exists() && folder.isDirectory()) {
                    receiptFolder = folder;
                    break;
                }
            }
            
            if (receiptFolder != null && receiptFolder.exists()) {
                // Find all receipt files for this customer
                String safeCustomerName = customerFullName.replaceAll("\\s+", "_");
                File[] receiptFiles = receiptFolder.listFiles((dir, name) -> 
                    name.startsWith(safeCustomerName) && name.endsWith(".txt"));
                
                if (receiptFiles != null && receiptFiles.length > 0) {
                    // Receipts header
                    JLabel receiptsHeader = new JLabel("Your Receipt Files");
                    receiptsHeader.setFont(new Font("-apple-system", Font.BOLD, 20));
                    receiptsHeader.setForeground(ACCENT_PRIMARY);
                    receiptsHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
                    receiptsHeader.setBorder(new EmptyBorder(0, 0, 20, 0));
                    detailsPanel.add(receiptsHeader);
                    
                    // Read and display each receipt
                    int receiptCount = 0;
                    for (File receiptFile : receiptFiles) {
                        receiptCount++;
                        try {
                            // Read receipt file content
                            List<String> receiptLines = new ArrayList<>();
                            try (BufferedReader reader = new BufferedReader(new FileReader(receiptFile))) {
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    receiptLines.add(line);
                                }
                            }
                            
                            // Create receipt card
                            JPanel receiptCard = new JPanel();
                            receiptCard.setLayout(new BoxLayout(receiptCard, BoxLayout.Y_AXIS));
                            receiptCard.setBackground(new Color(SURFACE_COLOR.getRed() + 10, SURFACE_COLOR.getGreen() + 10, SURFACE_COLOR.getBlue() + 10));
                            receiptCard.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                                new EmptyBorder(15, 15, 15, 15)
                            ));
                            receiptCard.setAlignmentX(Component.LEFT_ALIGNMENT);
                            receiptCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
                            
                            // Receipt header
                            JLabel receiptHeader = new JLabel("Receipt #" + receiptCount);
                            receiptHeader.setFont(new Font("-apple-system", Font.BOLD, 16));
                            receiptHeader.setForeground(ACCENT_SECONDARY);
                            receiptCard.add(receiptHeader);
                            
                            receiptCard.add(Box.createVerticalStrut(10));
                            
                            // Display receipt content
                            for (String receiptLine : receiptLines) {
                                if (receiptLine.trim().isEmpty() || receiptLine.trim().equals("--------------------------------------")) {
                                    receiptCard.add(createSeparator());
                                    continue;
                                }
                                
                                // Parse and format receipt line
                                if (receiptLine.contains(":")) {
                                    String[] parts = receiptLine.split(":", 2);
                                    if (parts.length == 2) {
                                        String label = parts[0].trim();
                                        String value = parts[1].trim();
                                        receiptCard.add(createInfoLabel(label, value));
                                    } else {
                                        JLabel lineLabel = new JLabel(receiptLine);
                                        lineLabel.setFont(new Font("-apple-system", Font.PLAIN, 14));
                                        lineLabel.setForeground(TEXT_PRIMARY);
                                        lineLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                                        lineLabel.setBorder(new EmptyBorder(5, 0, 5, 0));
                                        receiptCard.add(lineLabel);
                                    }
                                } else {
                                    JLabel lineLabel = new JLabel(receiptLine);
                                    lineLabel.setFont(new Font("-apple-system", Font.PLAIN, 14));
                                    lineLabel.setForeground(TEXT_PRIMARY);
                                    lineLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                                    lineLabel.setBorder(new EmptyBorder(5, 0, 5, 0));
                                    receiptCard.add(lineLabel);
                                }
                            }
                            
                            detailsPanel.add(receiptCard);
                            detailsPanel.add(Box.createVerticalStrut(15));
                            
                        } catch (IOException e) {
                            JLabel errorLabel = new JLabel("Error reading receipt: " + receiptFile.getName());
                            errorLabel.setFont(new Font("-apple-system", Font.PLAIN, 14));
                            errorLabel.setForeground(ERROR_COLOR);
                            errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                            detailsPanel.add(errorLabel);
                            appendText("Error reading receipt: " + receiptFile.getName());
                        }
                    }
                }
            }
            
            detailsPanel.add(Box.createVerticalGlue());
            detailsPanel.revalidate();
            detailsPanel.repaint();
        });
        
        // Also log to text area for reference
        int invoiceCount = (invoices != null && !invoices.isEmpty()) ? invoices.size() : 0;
        appendText("Displaying " + invoiceCount + " invoice(s) and receipt files for " + customerFullName + ".");
    }
    
    /**
     * Displays a loading/processing message in the details panel while auto purchase is running.
     * 
     * @param filename The name of the file being processed
     */
    public void displayAutoPurchaseLoading(String filename) {
        SwingUtilities.invokeLater(() -> {
            // Clear panel synchronously within the same EDT block
            detailsPanel.removeAll();
            
            // Switch view directly
            cardLayout.show(cardPanel, "DETAILS");
            viewIndicatorLabel.setText("View: Details");
            mainFrame.setTitle("TicketMiner - Processing Auto Purchase");
            
            // Header
            JLabel header = new JLabel("Processing Auto Purchase");
            header.setFont(new Font("-apple-system", Font.BOLD, 20));
            header.setForeground(ACCENT_PRIMARY);
            header.setAlignmentX(Component.LEFT_ALIGNMENT);
            header.setBorder(new EmptyBorder(0, 0, 20, 0));
            detailsPanel.add(header);
            
            // Loading message
            JLabel loadingLabel = new JLabel("⏳ Processing file: " + filename);
            loadingLabel.setFont(new Font("-apple-system", Font.PLAIN, 16));
            loadingLabel.setForeground(TEXT_PRIMARY);
            loadingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            loadingLabel.setBorder(new EmptyBorder(20, 0, 10, 0));
            detailsPanel.add(loadingLabel);
            
            // Status message
            JLabel statusLabel = new JLabel("Please wait while purchases are being processed...");
            statusLabel.setFont(new Font("-apple-system", Font.PLAIN, 14));
            statusLabel.setForeground(TEXT_SECONDARY);
            statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            statusLabel.setBorder(new EmptyBorder(10, 0, 20, 0));
            detailsPanel.add(statusLabel);
            
            // Animated loading dots (simple text animation)
            JLabel dotsLabel = new JLabel("Processing");
            dotsLabel.setFont(new Font("-apple-system", Font.PLAIN, 14));
            dotsLabel.setForeground(ACCENT_SECONDARY);
            dotsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            dotsLabel.setBorder(new EmptyBorder(10, 0, 20, 0));
            detailsPanel.add(dotsLabel);
            
            // Start animation timer
            javax.swing.Timer timer = new javax.swing.Timer(500, e -> {
                String text = dotsLabel.getText();
                if (text.endsWith("...")) {
                    dotsLabel.setText("Processing");
                } else {
                    dotsLabel.setText(text + ".");
                }
            });
            timer.start();
            
            // Store timer reference so we can stop it later
            detailsPanel.putClientProperty("loadingTimer", timer);
            
            detailsPanel.add(Box.createVerticalGlue());
            detailsPanel.revalidate();
            detailsPanel.repaint();
        });
        
        // Also log to text area
        appendText("Starting auto purchase processing for: " + filename);
    }
    
    /**
     * Displays auto purchase results in the details panel.
     * Shows a summary and all successfully created invoices.
     * 
     * @param invoices The list of invoices created by the auto purchaser
     * @param totalProcessed The total number of purchase attempts processed
     * @param successful The number of successful purchases
     * @param failed The number of failed purchases
     */
    public void displayAutoPurchaseResults(List<Invoice> invoices, int totalProcessed, int successful, int failed) {
        SwingUtilities.invokeLater(() -> {
            // Stop loading timer if it exists
            Object timerObj = detailsPanel.getClientProperty("loadingTimer");
            if (timerObj instanceof javax.swing.Timer) {
                ((javax.swing.Timer) timerObj).stop();
            }
            
            // Clear panel synchronously within the same EDT block
            detailsPanel.removeAll();
            
            // Switch view directly
            cardLayout.show(cardPanel, "DETAILS");
            viewIndicatorLabel.setText("View: Details");
            mainFrame.setTitle("TicketMiner - Auto Purchase Results");
            
            // Header
            JLabel header = new JLabel("Auto Purchase Results");
            header.setFont(new Font("-apple-system", Font.BOLD, 20));
            header.setForeground(ACCENT_PRIMARY);
            header.setAlignmentX(Component.LEFT_ALIGNMENT);
            header.setBorder(new EmptyBorder(0, 0, 20, 0));
            detailsPanel.add(header);
            
            // Summary Section
            detailsPanel.add(createSectionLabel("Summary"));
            detailsPanel.add(createInfoLabel("Total Processed", String.valueOf(totalProcessed)));
            detailsPanel.add(createInfoLabel("Successful Purchases", String.valueOf(successful)));
            detailsPanel.add(createInfoLabel("Failed Purchases", String.valueOf(failed)));
            
            if (successful > 0) {
                JLabel successLabel = createInfoLabel("Success Rate", String.format("%.1f%%", (successful * 100.0 / totalProcessed)));
                successLabel.setForeground(SUCCESS_COLOR);
                detailsPanel.add(successLabel);
            }
            
            detailsPanel.add(createSeparator());
            
            // Invoices Section
            if (invoices != null && !invoices.isEmpty()) {
                detailsPanel.add(createSectionLabel("Created Invoices (" + invoices.size() + ")"));
                
                // Create invoice cards
                int invoiceCount = 0;
                for (Invoice invoice : invoices) {
                    invoiceCount++;
                    
                    // Invoice card container
                    JPanel invoiceCard = new JPanel();
                    invoiceCard.setLayout(new BoxLayout(invoiceCard, BoxLayout.Y_AXIS));
                    invoiceCard.setBackground(new Color(SURFACE_COLOR.getRed() + 10, SURFACE_COLOR.getGreen() + 10, SURFACE_COLOR.getBlue() + 10));
                    invoiceCard.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 1),
                        new EmptyBorder(15, 15, 15, 15)
                    ));
                    invoiceCard.setAlignmentX(Component.LEFT_ALIGNMENT);
                    invoiceCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
                    
                    // Invoice header
                    JLabel invoiceHeader = new JLabel("Invoice #" + invoiceCount);
                    invoiceHeader.setFont(new Font("-apple-system", Font.BOLD, 16));
                    invoiceHeader.setForeground(ACCENT_SECONDARY);
                    invoiceCard.add(invoiceHeader);
                    
                    invoiceCard.add(Box.createVerticalStrut(10));
                    
                    // Invoice details
                    invoiceCard.add(createInfoLabel("Invoice ID", invoice.getInvoiceID()));
                    invoiceCard.add(createInfoLabel("Customer", invoice.getCustomer().getFirstName() + " " + invoice.getCustomer().getLastName()));
                    invoiceCard.add(createInfoLabel("Event", invoice.getEvent().getEventName()));
                    invoiceCard.add(createInfoLabel("Ticket Type", invoice.getTicket().getTier()));
                    invoiceCard.add(createInfoLabel("Quantity", String.valueOf(invoice.getTicket().getQuantity())));
                    invoiceCard.add(createInfoLabel("Total", String.format("$%.2f", invoice.returnTotal())));
                    if (invoice.getCustomer().getMemberStatus()) {
                        invoiceCard.add(createInfoLabel("Member Savings", String.format("$%.2f", invoice.getMemberDiscount())));
                    }
                    
                    detailsPanel.add(invoiceCard);
                    detailsPanel.add(Box.createVerticalStrut(15));
                }
            } else {
                JLabel noInvoicesLabel = new JLabel("No invoices were created. All purchase attempts failed.");
                noInvoicesLabel.setFont(new Font("-apple-system", Font.PLAIN, 14));
                noInvoicesLabel.setForeground(TEXT_SECONDARY);
                noInvoicesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                noInvoicesLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
                detailsPanel.add(noInvoicesLabel);
            }
            
            detailsPanel.add(Box.createVerticalGlue());
            detailsPanel.revalidate();
            detailsPanel.repaint();
        });
        
        // Also log to text area for reference
        appendText("Auto Purchase completed: " + successful + " successful, " + failed + " failed out of " + totalProcessed + " total.");
    }
    
    /**
     * Displays customer information in the details panel.
     * Shows amount of purchases, available funds, first name, last name, password, and username.
     * 
     * @param customer The customer whose information to display
     */
    public void displayCustomerInfo(Customer customer) {
        SwingUtilities.invokeLater(() -> {
            // Clear panel synchronously within the same EDT block
            detailsPanel.removeAll();
            
            // Switch view directly
            cardLayout.show(cardPanel, "DETAILS");
            viewIndicatorLabel.setText("View: Details");
            mainFrame.setTitle("TicketMiner - My Information");
            
            // Calculate total amount spent from invoices
            double totalAmountSpent = 0.0;
            if (customer.getCustomerInvoices() != null && !customer.getCustomerInvoices().isEmpty()) {
                for (Invoice invoice : customer.getCustomerInvoices().values()) {
                    totalAmountSpent += invoice.returnTotal();
                }
            }
            
            // Header
            JLabel header = new JLabel("My Information");
            header.setFont(new Font("-apple-system", Font.BOLD, 20));
            header.setForeground(ACCENT_PRIMARY);
            header.setAlignmentX(Component.LEFT_ALIGNMENT);
            header.setBorder(new EmptyBorder(0, 0, 20, 0));
            detailsPanel.add(header);
            
            // Personal Information Section
            detailsPanel.add(createSectionLabel("Personal Information"));
            detailsPanel.add(createInfoLabel("First Name", customer.getFirstName()));
            detailsPanel.add(createInfoLabel("Last Name", customer.getLastName()));
            detailsPanel.add(createInfoLabel("Username", customer.getUsername()));
            detailsPanel.add(createInfoLabel("Password", customer.getPassword()));
            
            detailsPanel.add(createSeparator());
            
            // Account Information Section
            detailsPanel.add(createSectionLabel("Account Information"));
            detailsPanel.add(createInfoLabel("Customer ID", String.valueOf(customer.getCustomerID())));
            detailsPanel.add(createInfoLabel("Member Status", customer.getMemberStatus() ? "TicketMiner Member" : "Standard Customer"));
            if (customer.getMemberStatus()) {
                JLabel savingsLabel = createInfoLabel("Lifetime Savings", String.format("$%.2f", customer.getTotalSavings()));
                savingsLabel.setForeground(SUCCESS_COLOR);
                detailsPanel.add(savingsLabel);
            }
            
            detailsPanel.add(createSeparator());
            
            // Financial Information Section
            detailsPanel.add(createSectionLabel("Financial Information"));
            detailsPanel.add(createInfoLabel("Available Funds", String.format("$%.2f", customer.getBalance())));
            detailsPanel.add(createInfoLabel("Total Amount Spent", String.format("$%.2f", totalAmountSpent)));
            detailsPanel.add(createInfoLabel("Number of Purchases", String.valueOf(customer.getConcertsPurchased()) + " ticket(s)"));
            detailsPanel.add(createInfoLabel("Total Transactions", String.valueOf(customer.getTransactionCount())));
            
            detailsPanel.add(Box.createVerticalGlue());
            detailsPanel.revalidate();
            detailsPanel.repaint();
        });
        
        // Also log to text area for reference
        appendText("Customer information displayed for: " + customer.getFirstName() + " " + customer.getLastName());
    }
    /**
     * Displays an error dialog with the specified message.
     * @param message The error message to display
     */
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(mainFrame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    /**
     * Styles the events table with modern aesthetics.
     * @param table The JTable to style
     */
    private void styleEventsTable(JTable table) {
        // Modern table styling with better contrast
        table.setBackground(SURFACE_COLOR);
        table.setForeground(TEXT_PRIMARY);
        table.setSelectionBackground(ACCENT_PRIMARY);
        table.setSelectionForeground(TEXT_PRIMARY);
        table.setRowHeight(28);
        table.setFont(new Font("-apple-system", Font.PLAIN, 13));
        table.setGridColor(BORDER_COLOR);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(0, 0));

        // Center align numeric columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        centerRenderer.setBackground(SURFACE_COLOR);
        centerRenderer.setForeground(TEXT_PRIMARY);
        
        // Apply center alignment to numeric columns (ID, Capacity, Seats Left, Prices)
        if (table.getColumnCount() > 0) {
            for (int i = 0; i < table.getColumnCount(); i++) {
                String columnName = table.getColumnName(i);
                if (columnName.equals("ID") || columnName.equals("Capacity") || 
                    columnName.equals("Seats Left") || columnName.contains("Price") ||
                    columnName.equals("Customer ID") || columnName.equals("Balance") ||
                    columnName.equals("Concerts Purchased") || columnName.equals("Transactions") ||
                    columnName.equals("Lifetime Savings")) {
                    table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }
            }
        }

        // Style header
        JTableHeader header = table.getTableHeader();
        header.setOpaque(true);
        header.setBackground(BACKGROUND_LIGHT);
        header.setForeground(TEXT_PRIMARY);
        header.setFont(new Font("-apple-system", Font.BOLD, 13));
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, ACCENT_PRIMARY),
            new EmptyBorder(5, 5, 5, 5)
        ));

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer)
        table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        headerRenderer.setOpaque(true);
        headerRenderer.setBackground(BACKGROUND_LIGHT);
        headerRenderer.setForeground(TEXT_PRIMARY);
        table.getTableHeader().setDefaultRenderer(headerRenderer);
        
        // Alternating row colors for better readability
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(SURFACE_COLOR);
                    } else {
                        c.setBackground(new Color(SURFACE_COLOR.getRed() + 5, 
                                                  SURFACE_COLOR.getGreen() + 5, 
                                                  SURFACE_COLOR.getBlue() + 5));
                    }
                }
                c.setForeground(TEXT_PRIMARY);
                return c;
            }
        });
    }

    /**
     * Custom scrollbar UI with modern styling
     */
    private static class ModernScrollBarUI extends BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = BORDER_COLOR;
            this.trackColor = BACKGROUND_DARK;
        }
        @Override protected JButton createDecreaseButton(int orientation) { return createZeroButton(); }
        @Override protected JButton createIncreaseButton(int orientation) { return createZeroButton(); }
        private JButton createZeroButton() {
            JButton b = new JButton();
            b.setPreferredSize(new Dimension(0, 0));
            return b;
        }
    }
}
