package com.runticket.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Represents a customer receipt for an automatic or manual ticket purchase.
 * Generates a confirmation number and saves the receipt to a .txt file.
 *
 * @author Henry Ng
 */
public class CustomerReceipt {

    /**
     * Receipt details
     *
     * @param customerName Name of the customer
     * @param eventType Type of the event
     * @param eventName Name of the event
     * @param eventDate Date of the event
     * @param ticketType Type of ticket purchased
     * @param confirmationNumber Unique confirmation number for the receipt
     * @param numTickets Number of tickets purchased
     * @param totalPrice Total price of the purchase
     * @param purchaseDate Date and time of the purchase
     */
    private String customerName, eventType, eventName, eventDate, ticketType, confirmationNumber;
    private int numTickets;
    private double totalPrice;
    private LocalDateTime purchaseDate;

    /**
     * Constructor to initialize a CustomerReceipt object with the provided
     * details.
     *
     * @param customerName Name of the customer
     * @param eventType type of the event
     * @param eventName Name of the event
     * @param eventDate date of the event
     * @param ticketType type of ticket purchased
     * @param numTickets number of tickets purchased
     * @param totalPrice total price of the purchase
     */
    public CustomerReceipt(String customerName, String eventType, String eventName,
            String eventDate, String ticketType, int numTickets, double totalPrice) {
        this.customerName = customerName;
        this.eventType = eventType;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.ticketType = ticketType;
        this.numTickets = numTickets;
        this.totalPrice = totalPrice;
        this.confirmationNumber = UUID.randomUUID().toString().substring(0, 8);
        this.purchaseDate = LocalDateTime.now();
    }

    /**
     * Prints the receipt summary to the console.
     *
     * public void printReceipt() { System.out.println("Customer Receipt Summary
     * for: " + customerName);
     * System.out.println("--------------------------------------");
     * System.out.println("Event Type: " + eventType); System.out.println("Event
     * Name: " + eventName); System.out.println("Event Date: " + eventDate);
     * System.out.println("Ticket Type: " + ticketType);
     * System.out.println("Number of Tickets: " + numTickets);
     * System.out.printf("Total Price: $%.2f\n", totalPrice);
     * System.out.println("Confirmation Number: " + confirmationNumber);
     * System.out.println("Purchase Date: " +
     * purchaseDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
     * System.out.println("--------------------------------------\n"); }
     */
    /**
     * Saves the receipt summary to a text file inside the "receipts" folder.
     */
    public void saveReceiptToFile() {
        try {
            // Create receipts folder if missing
            File folder = new File("src/main/java/com/runticket/receipts");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            //Format date for filename (no special characters)
            DateTimeFormatter fileFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");
            String timestamp = purchaseDate.format(fileFormatter);

            // Safe filename: CustomerName_YYYY-MM-DD_HHMMSS.txt
            String safeName = customerName.replaceAll("\\s+", "_");
            String fileName = safeName + timestamp + ".txt";

            // Create file in receipts folder
            File file = new File(folder, fileName);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write("Customer Receipt Summary for: " + customerName + "\n");
                writer.write("--------------------------------------\n");
                writer.write("Event Type: " + eventType + "\n");
                writer.write("Event Name: " + eventName + "\n");
                writer.write("Event Date: " + eventDate + "\n");
                writer.write("Ticket Type: " + ticketType + "\n");
                writer.write("Number of Tickets: " + numTickets + "\n");
                writer.write(String.format("Total Price: $%.2f\n", totalPrice));
                writer.write("Confirmation Number: " + confirmationNumber + "\n");
                writer.write("Purchase Date: " + purchaseDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n");
                writer.write("--------------------------------------\n");
                Logger.getLogger().Log("New Receipt Generated for: " + this.customerName + " for event " + this.eventName);
            }

            //System.out.println("Receipt saved to: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error saving receipt: " + e.getMessage());
        }
    }

    /**
     * Saves the receipt summary to a text file inside the
     * "receipts/generated8Receipts" folder.
     */
    public void saveReceiptToGenerated8Folder() {
        try {
            // Create receipts/generated8Receipts folder if missing
            File folder = new File("src/main/java/com/runticket/receipts/generated8Receipts");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            //Format date for filename (no special characters)
            DateTimeFormatter fileFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");
            String timestamp = purchaseDate.format(fileFormatter);

            // Safe filename: CustomerName_YYYY-MM-DD_HHMMSS.txt
            String safeName = customerName.replaceAll("\\s+", "_");
            String fileName = safeName + timestamp + ".txt";

            // Create file in generated8Receipts folder
            File file = new File(folder, fileName);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write("Customer Receipt Summary for: " + customerName + "\n");
                writer.write("--------------------------------------\n");
                writer.write("Event Type: " + eventType + "\n");
                writer.write("Event Name: " + eventName + "\n");
                writer.write("Event Date: " + eventDate + "\n");
                writer.write("Ticket Type: " + ticketType + "\n");
                writer.write("Number of Tickets: " + numTickets + "\n");
                writer.write(String.format("Total Price: $%.2f\n", totalPrice));
                writer.write("Confirmation Number: " + confirmationNumber + "\n");
                writer.write("Purchase Date: " + purchaseDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n");
                writer.write("--------------------------------------\n");
                Logger.getLogger().Log("New Receipt Generated for: " + this.customerName + " for event " + this.eventName);
            }

        } catch (IOException e) {
            System.out.println("Error saving receipt: " + e.getMessage());
        }
    }
}
