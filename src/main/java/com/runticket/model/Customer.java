package com.runticket.model;
import java.util.HashMap;
/**
 * The Customer class represents a customer with personal details, membership status,
 * balance, and a list of invoices for purchased tickets.
 * It provides methods to access and modify customer information, print details,
 * add invoices, and display all invoices.
 *
 * @author Francisco Garcia
 * @author Adrian Sifuentes
 */
public class Customer{
    /**
     * Customer class attributes
     * @param firstName The Customer's First Name
     * @param lastName The Customer's Last Name
     * @param userName The Customer's Username
     * @param password The Customer's Password
     * @param customerID The Customer's ID
     * @param concertsPurchased The number of tickets a Customer has purchased
     * @param transactionCount The number of transactions that a Customer has completed
     * @param isMember The membership status of a member
     * @param balance The available money of a Customer
     * @param totalSavings How much a Customer has saved with their TicketMiner membership
     * @param customerInvoices Stores the invoices of a Customer
     */
    private String firstName, lastName, userName, password;
    private int customerID, concertsPurchased, transactionCount;
    private boolean isMember;
    private double balance, totalSavings = 0;
    private HashMap<String, Invoice> customerInvoices= new HashMap<>();
    /**
     * Default constructor for Customer class
     */
    public Customer() {

    }
    /**
     * Constructor for Customer class with first and last name parameters
     * @param firtsNameIn the first name of the customer
     * @param lastNameIn the last name of the customer
     */
    public Customer(String firtsNameIn, String lastNameIn){
        this.firstName = firtsNameIn; 
        this.lastName = lastNameIn;
    }
    /**
     * Constructor for Customer class with first name, last name, and customer ID parameters
     * @param firtsNameIn the first name of the customer
     * @param lastNameIn the last name of the customer
     * @param customerIDIn the ID of the customer
     */
    public Customer(String firtsNameIn, String lastNameIn, int customerIDIn){
        this.firstName = firtsNameIn; 
        this.lastName = lastNameIn;
        this.customerID = customerIDIn;
    }
   
    /**
     * Returns the customer's first name
     * @return firstName, the customer's first name
     */
    //Gets and Sets the Customer's First Name
    public String getFirstName() {
        return this.firstName;
    }
   /**
    * Sets the customer's first name
    * @param firstNameIn
    */
    public void setFirstName(String firstNameIn) {
        this.firstName = firstNameIn;
    }
    /**
     * Returns the customer's last name
     * @return lastName, the customer's last name
     */
    //Gets and Sets the Customer's Last Name
    public String getLastName() {
        return this.lastName;
    }
    /**
     * Sets the customer's last name
     * @param lastNameIn
     */
    public void setLastName(String lastNameIn) {
        this.lastName = lastNameIn;
    }
    /**
     * Returns the customer's username
     * @return userName, the customer's username
     */
    //Gets and Sets the Customer's Username
    public String getUsername() {
        return this.userName;
    }
    /**
     * Sets the customer's username
     * @param userNameIn
     */
    public void setUsername(String userNameIn) {
        this.userName = userNameIn;
    }
    /**
     * Returns the customer's password
     * @return password, the customer's password
     */
    //Gets and Sets the Customer's Password
    public String getPassword() {
        return this.password;
    }
    /**
     * Sets the customer's password
     * @param passwordIn
     */
    public void setPassword(String passwordIn) {
        this.password = passwordIn;
    }
    /**
     * Returns the customer's ID
     * @return customerID, the customer's ID
     */
    //Gets and Sets the Customer's ID
    public int getCustomerID() {
        return this.customerID;
    }
    /**
     * Sets the customer's ID
     * @param customerIDIn
     */
    public void setCustomerID(int customerIDIn) {
        this.customerID = customerIDIn;
    }
    /**
     * Returns the customer's membership status
     * @return isMember, the customer's membership status
     */
    //Gets and Sets the Customer's member status (Wheter the customer is a member or not a member)
    public boolean getMemberStatus() {
        return this.isMember;
    }
    /**
     * Sets the customer's membership status
     * @param statusIn
     */
    public void setMemberStatus(boolean statusIn) {
        this.isMember = statusIn;
    }
    /**
     * Returns the customer's balance
     * @return balance, the customer's balance
     */
    //Gets and Sets the Customer's available money to spend on tickets
    public double getBalance() {
        return this.balance;
    }
    /**
     * Sets the customer's balance
     * @param balanceIn
     */
    public void setBalance(double balanceIn) {
        this.balance = balanceIn;
    }
    /**
     * Returns the number of concerts purchased by the customer
     * @return concertsPurchased, the number of concerts purchased
     */
    //Gets and Sets the Customer's number of concerts purchased
    public int getConcertsPurchased() {
        return this.concertsPurchased;
    }
    /**
     * Sets the number of concerts purchased by the customer
     * @param concertsIn
     */
    public void setConcertsPurchased(int concertsIn) {
        this.concertsPurchased = concertsIn;
    }
    /**
     * Sets the number of transactions made by the customer
     * @param countIn
     */
    //Sets and gets the Customer's transaction count 
    public void setTransactionCount(int countIn){
        this.transactionCount = countIn;
    }
    /**
     * Returns the number of transactions made by the customer
     * @return transactionCount, the number of transactions made
     */
    public int getTransactionCount(){
        return this.transactionCount;
    }
    /**
     * sets the customer's lifetime savings due to membership discounts
     * @param savingsIn
     */
    //Sets and gets the Customer's lifetime savings
    public void setTotalSavings(double savingsIn){
        this.totalSavings = savingsIn;
    }
    /**
     * Returns the customer's lifetime savings due to membership discounts
     * @return totalSavings, the customer's lifetime savings
     */
    public double getTotalSavings(){
        return this.totalSavings;
    }
    /**
     * Prints information about the Customer
     */
    //Prints the customer's information
    public void printInfo(){
        System.out.println("==========Customer Information==========");
        System.out.println("Customer Name: " + this.firstName + " " + this.lastName);
        System.out.println("Customer ID: " + this.customerID);
        System.out.println("Customer Username: " + this.userName);
        System.out.println("Customer is a member: " + this.isMember);
        System.out.printf("Customer balance: $%.2f\n", this.balance);
        System.out.println("Number of Concerts Purchased: " + this.concertsPurchased);
        System.out.printf("Lifetime Membership Savings: $%.2f\n", this.totalSavings);
        System.out.println("Total Number of Transactions: " + this.transactionCount);
        System.out.println("====================");
    }
    /**
     * Adds an invoice to the Customer's list of invoices
     * Decreases the available money of the Customer based on the total amount on the Invoice.
     * Increases the total savings of the Customer if they have a TicketMiner membership
     * Increases the number of tickets purchased based on the invoice, and increases the Customer's transaction count by one.
     * @param invoiceIn the invoice to be added
     */
    //Adds an invoice to the Customer's customerInvoices array, substracts the total of the invoice from the Customer's balance
    //Increases the number of Concerts purchased and the number of transactions of the Customer
    public void addInvoice(Invoice invoiceIn){
        this.balance -= invoiceIn.returnTotal();
        this.totalSavings += invoiceIn.getMemberDiscount();
        this.concertsPurchased += invoiceIn.getTicketNum();
        this.transactionCount += 1;
        this.customerInvoices.put(invoiceIn.getInvoiceID(), invoiceIn);
    }
    /**
     * Displays all invoices of the customer
     */
    //Displays the Customer's invoices
    public void displayInvoices(){
        for (String inv: this.customerInvoices.keySet()){
            this.customerInvoices.get(inv).printInvoice();
        }
    }
    /**
     * Gets a specific invoice by its ID
     * @param invoiceIDIn the ID of the invoice to retrieve
     * @return the Invoice object if found, null otherwise
     */
    //Returns a specific invoice by its ID
    public Invoice getInvoice(String invoiceIDIn){
        if(this.customerInvoices.containsKey(invoiceIDIn)) {
            return this.customerInvoices.get(invoiceIDIn);
        }
        else{
            return null;
        }
    }
    /**
     * Returns the customer's invoices
     * @return customerInvoices, the customer's invoices
     */
    //Returns all of the Customer's invoices
    public HashMap<String, Invoice> getCustomerInvoices(){
        return this.customerInvoices;
    }
    /**
     * Processes a refund for a specific invoice by its ID
     * returns the refunded amount to the customer's balance,
     * decreases the number of concerts purchased and total savings accordingly,
     * and removes the invoice from the customer's list.
     * The refunded amount does not include the fees charged.
     * @param invoiceIDIn the ID of the invoice to refund
     */
    //Processes a refund for a specific invoice by its ID
    public void refund(String invoiceIDIn){
        System.out.printf("Balance Before Refund: $%.2f\n", this.balance);
        System.out.println("Processing Refund...");
        //If the invoice exists, process the refund
        if (this.customerInvoices.containsKey(invoiceIDIn)) {
            //The refunded amount is added back to the customer's balance
            this.balance += this.customerInvoices.get(invoiceIDIn).getCustomerRefund();
            //The number of concerts purchased is decreased based on the invoice
            this.concertsPurchased -= this.customerInvoices.get(invoiceIDIn).getTicketNum();
            //If the customer is a member, decrease their total savings based on the invoice's member discount
            if(this.isMember) {
                this.totalSavings -= this.customerInvoices.get(invoiceIDIn).getMemberDiscount();
            }
            //Remove the invoice from the customer's list of invoices
            this.customerInvoices.remove(invoiceIDIn);
            System.out.println("Refund successful!");
            System.out.printf("Balance After Refund: $%.2f\n", this.balance);  
        }
        else{
            System.out.println("Invoice not found, refund failed");
        }
    }
    /**
     * Processes a full refund for a specific invoice by its ID (including all fees)
     * Used when an event is cancelled. Returns the full refunded amount to the customer's balance,
     * decreases the number of concerts purchased and total savings accordingly,
     * and removes the invoice from the customer's list.
     * @param invoiceIDIn the ID of the invoice to refund
     * @return the full refund amount if successful, -1 if invoice not found
     */
    public double refundFull(String invoiceIDIn){
        //If the invoice exists, process the full refund
        if (this.customerInvoices.containsKey(invoiceIDIn)) {
            Invoice invoice = this.customerInvoices.get(invoiceIDIn);
            double fullRefundAmount = invoice.getFullRefund();
            //The full refunded amount (including fees) is added back to the customer's balance
            this.balance += fullRefundAmount;
            //The number of concerts purchased is decreased based on the invoice
            this.concertsPurchased -= invoice.getTicketNum();
            //If the customer is a member, decrease their total savings based on the invoice's member discount
            if(this.isMember) {
                this.totalSavings -= invoice.getMemberDiscount();
            }
            //Remove the invoice from the customer's list of invoices
            this.customerInvoices.remove(invoiceIDIn);
            return fullRefundAmount;
        }
        else{
            return -1;
        }
    }
    /**
     * Validates customer login credentials
     * @param username the username
     * @param password the password
     * @return true if the credentials are valid, false otherwise
     */
    //Validates the customer's login
    public boolean login(String username, String password){
        //Check if the provided username and password match the customer's username and password
        if (!this.getUsername().equals(username) || !this.getPassword().equals(password)) {
            System.out.println("Invalid username or password.");
            return false;
        }
        return true;
    }

}

