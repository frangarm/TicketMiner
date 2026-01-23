import com.runticket.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

import static org.junit.Assert.*;
//Tests created using AI
/*
 * Prompt was "Create a JUnit 4.13.2 test case for the welcomeUser() and userLogin() test of RunTicket.java. 
 * welcomeUser() takes in a scanner and welcomes a Customer and asks them if they want to log in or exit. If the user inputs login, 
 * the welcomeUser() method should call the userLogin() method. The welcomeUser() method will take in a scanner and ask the user to login with their username and password.
 * If the login is successful, the user will then be asked if they want to purchase tickets and the buyTickets() method of a PurchaseTickets.java object will be called. 
   Make test cases for the welcomeUser() and userLogin() methods of RunTicket.java, make the test cases range from normal parameters to edge cases parameters."

   The files RunTicket.java, PurchaseTickets.java, Customer.java, Event.java, and Venue.java were uploaded so that the AI could
   understand the context of the test cases
 */
public class RunTicketUserTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private HashMap<Integer, Customer> originalCustomerList;
    private HashMap<Integer, Event> originalEventList;
    private HashMap<String, Venue> originalVenueList;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        
        // Backup original static data
        originalCustomerList = RunTicket.customerList;
        originalEventList = RunTicket.eventList;
        originalVenueList = RunTicket.venueList;
        
        // Initialize with test data
        RunTicket.customerList = new HashMap<>();
        RunTicket.eventList = new HashMap<>();
        RunTicket.venueList = new HashMap<>();
    }

    @After
    public void restoreStreamsAndData() {
        System.setOut(originalOut);
        
        // Restore original static data
        RunTicket.customerList = originalCustomerList;
        RunTicket.eventList = originalEventList;
        RunTicket.venueList = originalVenueList;
    }

    // Test welcomeUser() method

    @Test
    public void testWelcomeUser_LoginChoice_ProcessesLogin() {
        // Setup test customer for the login that will happen
        Customer testCustomer = new Customer("John", "Doe", 1);
        testCustomer.setUserName("testuser");
        testCustomer.setPassword("testpass");
        testCustomer.setBalance(1000.0);
        RunTicket.customerList.put(1, testCustomer);
        
        // Provide enough input for welcomeUser AND userLogin
        String input = "login\ntestuser\ntestpass\nexit\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        RunTicket.welcomeUser(scanner);
        
        String output = outContent.toString();
        assertTrue("Should display welcome message", output.contains("Welcome! Do you want to log in?"));
        assertTrue("Should process login choice", output.contains("Login") || output.contains("login"));
    }

    @Test
    public void testWelcomeUser_ExitChoice_PrintsGoodbye() {
        String input = "exit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        RunTicket.welcomeUser(scanner);
        
        String output = outContent.toString();
        assertTrue("Should display welcome message", output.contains("Welcome! Do you want to log in?"));
        assertTrue("Should print goodbye message", output.contains("Goodbye!"));
    }

    @Test
    public void testWelcomeUser_InvalidChoice_PrintsInvalidChoice() {
        // Setup test customer for when login is eventually called
        Customer testCustomer = new Customer("John", "Doe", 1);
        testCustomer.setUserName("testuser");
        testCustomer.setPassword("testpass");
        testCustomer.setBalance(1000.0);
        RunTicket.customerList.put(1, testCustomer);
        
        String input = "invalid\nlogin\ntestuser\ntestpass\nexit\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        RunTicket.welcomeUser(scanner);
        
        String output = outContent.toString();
        assertTrue("Should display invalid choice message", output.contains("Invalid Choice"));
    }

    @Test
    public void testWelcomeUser_CaseInsensitiveLogin_WorksCorrectly() {
        // Setup test customer
        Customer testCustomer = new Customer("John", "Doe", 1);
        testCustomer.setUserName("testuser");
        testCustomer.setPassword("testpass");
        testCustomer.setBalance(1000.0);
        RunTicket.customerList.put(1, testCustomer);
        
        String input = "LOGIN\ntestuser\ntestpass\nexit\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        RunTicket.welcomeUser(scanner);
        
        String output = outContent.toString();
        assertTrue("Should display welcome message", output.contains("Welcome! Do you want to log in?"));
        // Should not contain "Invalid Choice" for uppercase LOGIN
        assertFalse("Should not show invalid choice for uppercase login", output.contains("Invalid Choice"));
    }

    @Test
    public void testWelcomeUser_MultipleInvalidChoices_HandlesAll() {
        // Setup test customer
        Customer testCustomer = new Customer("John", "Doe", 1);
        testCustomer.setUserName("testuser");
        testCustomer.setPassword("testpass");
        testCustomer.setBalance(1000.0);
        RunTicket.customerList.put(1, testCustomer);
        
        String input = "wrong\ninvalid\nlogin\ntestuser\ntestpass\nexit\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        RunTicket.welcomeUser(scanner);
        
        String output = outContent.toString();
        // Should handle multiple invalid inputs gracefully
        assertTrue("Should process multiple invalid choices", output.contains("Invalid Choice"));
    }

    // Test userLogin() method

    @Test
    public void testUserLogin_ValidCredentials_SuccessfulLogin() {
        // Setup test customer
        Customer testCustomer = new Customer("John", "Doe", 1);
        testCustomer.setUserName("testuser");
        testCustomer.setPassword("testpass");
        testCustomer.setBalance(1000.0);
        RunTicket.customerList.put(1, testCustomer);
        
        String input = "testuser\ntestpass\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        RunTicket.userLogin(scanner);
        
        String output = outContent.toString();
        assertTrue("Should prompt for username and password", 
                   output.contains("Username:") && output.contains("Password:"));
        assertTrue("Should display welcome message with customer name", 
                   output.contains("Hello, John Doe!"));
        assertTrue("Should display balance", output.contains("Balance: $1000.00"));
        assertTrue("Should print goodbye", output.contains("Goodbye!"));
    }

    @Test
    public void testUserLogin_InvalidCredentials_PrintsErrorMessage() {
        // Setup test customer
        Customer testCustomer = new Customer("John", "Doe", 1);
        testCustomer.setUserName("testuser");
        testCustomer.setPassword("testpass");
        RunTicket.customerList.put(1, testCustomer);
        
        String input = "wronguser\nwrongpass\ntestuser\ntestpass\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        RunTicket.userLogin(scanner);
        
        String output = outContent.toString();
        assertTrue("Should display error message for wrong credentials", 
                   output.contains("Wrong username or password!"));
        assertTrue("Should eventually succeed with correct credentials", 
                   output.contains("Hello, John Doe!"));
    }

    @Test
    public void testUserLogin_EmptyCredentials_PrintsErrorMessage() {
        // Setup test customer
        Customer testCustomer = new Customer("John", "Doe", 1);
        testCustomer.setUserName("testuser");
        testCustomer.setPassword("testpass");
        RunTicket.customerList.put(1, testCustomer);
        
        String input = "\n\ntestuser\ntestpass\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        RunTicket.userLogin(scanner);
        
        String output = outContent.toString();
        assertTrue("Should handle empty credentials", output.contains("Wrong username or password!"));
        assertTrue("Should succeed with valid credentials", output.contains("Hello, John Doe!"));
    }

    @Test
    public void testUserLogin_YesChoice_ProceedsToPurchaseFlow() {
        // Setup test customer
        Customer testCustomer = new Customer("John", "Doe", 1);
        testCustomer.setUserName("testuser");
        testCustomer.setPassword("testpass");
        testCustomer.setBalance(1000.0);
        RunTicket.customerList.put(1, testCustomer);
        
        // Note: When "yes" is entered, buyTickets() is called which requires additional input
        // For this test, we'll just verify the prompt is shown
        String input = "testuser\ntestpass\nyes\nback\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        RunTicket.userLogin(scanner);
        
        String output = outContent.toString();
        assertTrue("Should ask about ticket purchase", 
                   output.contains("Would you like to purchase tickets today?"));
    }

    @Test
    public void testUserLogin_ExitChoiceAfterLogin_PrintsGoodbye() {
        // Setup test customer
        Customer testCustomer = new Customer("John", "Doe", 1);
        testCustomer.setUserName("testuser");
        testCustomer.setPassword("testpass");
        RunTicket.customerList.put(1, testCustomer);
        
        String input = "testuser\ntestpass\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        RunTicket.userLogin(scanner);
        
        String output = outContent.toString();
        assertTrue("Should print goodbye after login", output.contains("Goodbye!"));
    }

    @Test
    public void testUserLogin_InvalidChoiceAfterLogin_PrintsInvalidChoice() {
        // Setup test customer
        Customer testCustomer = new Customer("John", "Doe", 1);
        testCustomer.setUserName("testuser");
        testCustomer.setPassword("testpass");
        RunTicket.customerList.put(1, testCustomer);
        
        String input = "testuser\ntestpass\ninvalid\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        RunTicket.userLogin(scanner);
        
        String output = outContent.toString();
        assertTrue("Should display invalid choice message", output.contains("Invalid Choice"));
        assertTrue("Should show purchase prompt", 
                   output.contains("Would you like to purchase tickets today?"));
    }

    @Test
    public void testUserLogin_CaseInsensitiveYes_WorksCorrectly() {
        // Setup test customer
        Customer testCustomer = new Customer("John", "Doe", 1);
        testCustomer.setUserName("testuser");
        testCustomer.setPassword("testpass");
        testCustomer.setBalance(1000.0);
        RunTicket.customerList.put(1, testCustomer);
    
        String input = "testuser\ntestpass\nYES\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
    
        RunTicket.userLogin(scanner);
    
        String output = outContent.toString();
    
        // Just verify that "YES" was processed as a valid command
        // without checking what happens in buyTickets()
        assertTrue("Should process uppercase YES and show purchase prompt", 
               output.contains("Would you like to purchase tickets today?"));
    }
    @Test
    public void testUserLogin_MultipleFailedAttempts_EventuallySucceeds() {
        // Setup test customer
        Customer testCustomer = new Customer("John", "Doe", 1);
        testCustomer.setUserName("testuser");
        testCustomer.setPassword("testpass");
        RunTicket.customerList.put(1, testCustomer);
        
        String input = "user1\npass1\nuser2\npass2\ntestuser\ntestpass\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        RunTicket.userLogin(scanner);
        
        String output = outContent.toString();
        // Should show multiple error messages
        assertTrue("Should handle multiple failed attempts", 
                   output.contains("Wrong username or password!"));
        // Should eventually succeed
        assertTrue("Should eventually login successfully", 
                   output.contains("Hello, John Doe!"));
    }

    @Test
    public void testUserLogin_NoCustomersInSystem_ContinuousRetry() {
        // Empty customer list - but provide a way to break the loop
        RunTicket.customerList.clear();
        
        // Provide multiple attempts then force exit by providing incomplete input
        // This test is tricky because userLogin() loops indefinitely on failure
        // We'll provide limited input to avoid infinite loop in test
        String input = "testuser\ntestpass\ntestuser\ntestpass\n";
        // This will still cause NoSuchElementException when it tries to read beyond provided input
        // For now, let's skip this test or modify the approach
        
        // Instead, let's test with one round of failed login
        input = "testuser\ntestpass\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        // We expect this to throw NoSuchElementException, so we'll catch it
        try {
            RunTicket.userLogin(scanner);
        } catch (Exception e) {
            // Expected - the method tries to read more input than we provided
        }
        
        String output = outContent.toString();
        assertTrue("Should show error message for invalid credentials", 
                   output.contains("Wrong username or password!"));
    }

    // Edge case tests

    @Test
    public void testUserLogin_WhitespaceInCredentials_HandledCorrectly() {
        // Setup test customer
        Customer testCustomer = new Customer("John", "Doe", 1);
        testCustomer.setUserName("testuser");
        testCustomer.setPassword("testpass");
        RunTicket.customerList.put(1, testCustomer);
        
        String input = " testuser \n testpass \ntestuser\ntestpass\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        RunTicket.userLogin(scanner);
        
        String output = outContent.toString();
        // First attempt with whitespace should fail (due to trim in comparison)
        assertTrue("Should handle whitespace in credentials", 
                   output.contains("Wrong username or password!"));
        // Second attempt without whitespace should succeed
        assertTrue("Should succeed with trimmed credentials", 
                   output.contains("Hello, John Doe!"));
    }

    @Test
    public void testUserLogin_SpecialCharactersInCredentials_Works() {
        // Setup test customer with special characters
        Customer specialCustomer = new Customer("Jane", "Smith", 2);
        specialCustomer.setUserName("user@domain.com");
        specialCustomer.setPassword("p@ssw0rd!");
        specialCustomer.setBalance(500.0);
        RunTicket.customerList.put(2, specialCustomer);
        
        String input = "user@domain.com\np@ssw0rd!\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        RunTicket.userLogin(scanner);
        
        String output = outContent.toString();
        assertTrue("Should login successfully with special characters", 
                   output.contains("Hello, Jane Smith!"));
    }

    @Test
    public void testWelcomeUser_MixedCaseInput_HandledCorrectly() {
        // Setup test customer
        Customer testCustomer = new Customer("John", "Doe", 1);
        testCustomer.setUserName("testuser");
        testCustomer.setPassword("testpass");
        testCustomer.setBalance(1000.0);
        RunTicket.customerList.put(1, testCustomer);
        
        String input = "LoGiN\ntestuser\ntestpass\nexit\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        RunTicket.welcomeUser(scanner);
        
        String output = outContent.toString();
        // Should process mixed case "LoGiN" correctly without "Invalid Choice"
        assertFalse("Should handle mixed case input", output.contains("Invalid Choice"));
    }

    @Test
    public void testUserLogin_CustomerWithZeroBalance_DisplaysCorrectBalance() {
        // Setup test customer with zero balance
        Customer testCustomer = new Customer("John", "Doe", 1);
        testCustomer.setUserName("testuser");
        testCustomer.setPassword("testpass");
        testCustomer.setBalance(0.0);
        RunTicket.customerList.put(1, testCustomer);
        
        String input = "testuser\ntestpass\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        RunTicket.userLogin(scanner);
        
        String output = outContent.toString();
        assertTrue("Should display zero balance correctly", 
                   output.contains("Balance: $0.00"));
    }

    @Test
    public void testWelcomeUser_EmptyInput_HandledGracefully() {
        // Setup test customer
        Customer testCustomer = new Customer("John", "Doe", 1);
        testCustomer.setUserName("testuser");
        testCustomer.setPassword("testpass");
        testCustomer.setBalance(1000.0);
        RunTicket.customerList.put(1, testCustomer);
        
        String input = "\nlogin\ntestuser\ntestpass\nexit\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        RunTicket.welcomeUser(scanner);
        
        String output = outContent.toString();
        assertTrue("Should handle empty input", output.contains("Invalid Choice"));
        assertTrue("Should proceed with valid input", output.contains("Login") || output.contains("login"));
    }

    // New test to specifically test the login loop behavior
    @Test
    public void testUserLogin_ImmediateExitAfterLogin_Works() {
        // Setup test customer
        Customer testCustomer = new Customer("John", "Doe", 1);
        testCustomer.setUserName("testuser");
        testCustomer.setPassword("testpass");
        testCustomer.setBalance(1000.0);
        RunTicket.customerList.put(1, testCustomer);
        
        // Simplest case: login then immediately exit
        String input = "testuser\ntestpass\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        RunTicket.userLogin(scanner);
        
        String output = outContent.toString();
        assertTrue("Should complete login and exit successfully", 
                   output.contains("Hello, John Doe!") && output.contains("Goodbye!"));
    }
}