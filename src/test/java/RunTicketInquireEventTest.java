import com.runticket.*;

import org.junit.*;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;
//Tests created using AI
/*
 * Prompt was "Write a JUnit 4.13.2 test case for the inquireEvent(scanner) method of RunTicket. The method
   takes a scanner and displays messages asking the user to inquire about an event by inputting the event's ID or name. 
   Include all test cases, from normal test cases to edge test cases"

   The files RunTicket.java, PurchaseTickets.java, Customer.java, Event.java, and Venue.java were uploaded so that the AI could
   understand the context of the test cases
 */
public class RunTicketInquireEventTest{
    
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private static HashMap<Integer, Event> originalEventList;

    // Mock Event class for testing
    private static class MockEvent extends Event {
        private String eventName;
        private int eventID;
        
        public MockEvent(int id, String name) {
            this.eventID = id;
            this.eventName = name;
        }
        
        @Override
        public String getEventName() {
            return eventName;
        }
        
        @Override
        public int getEventID() {
            return eventID;
        }
        
        @Override
        public void eventRevenue() {
            System.out.println("Revenue for: " + eventName);
        }
        
        @Override
        public void printInfo() {
            System.out.println("Event: " + eventName);
        }
    }
    
    @BeforeClass
    public static void setUpClass() {
        // Backup original eventList
        originalEventList = RunTicket.eventList;
    }
    
    @AfterClass
    public static void tearDownClass() {
        // Restore original eventList
        RunTicket.eventList = originalEventList;
    }
    
    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        // Initialize eventList for each test
        RunTicket.eventList = new HashMap<>();
    }
    
    @After
    public void tearDown() {
        System.setOut(originalOut);
    }
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    // Helper method to create test events
    private void setupTestEvents() {
        RunTicket.eventList.put(1, new MockEvent(1, "Rock Concert"));
        RunTicket.eventList.put(2, new MockEvent(2, "Jazz Festival"));
        RunTicket.eventList.put(3, new MockEvent(3, "Basketball Game"));
        RunTicket.eventList.put(100, new MockEvent(100, "Special Event"));
    }
    
    // Normal Test Cases
    
    @Test
    public void testInquireByExistingID() {
        // Setup
        setupTestEvents();
        String input = "1\nExit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        // Execute
        RunTicket.inquireEvent(scanner);
        
        // Verify
        String output = outContent.toString();
        assertTrue("Should contain inquire prompt", output.contains("Inquire about an event"));
        assertTrue("Should display revenue for Rock Concert", output.contains("Revenue for: Rock Concert"));
        assertTrue("Should exit properly", output.contains("Goodbye!"));
    }
    
    @Test
    public void testInquireByExistingName() {
        // Setup
        setupTestEvents();
        String input = "Rock Concert\nExit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        // Execute
        RunTicket.inquireEvent(scanner);
        
        // Verify
        String output = outContent.toString();
        assertTrue("Should display revenue for Rock Concert", output.contains("Revenue for: Rock Concert"));
        assertTrue("Should exit properly", output.contains("Goodbye!"));
    }
    
    @Test
    public void testInquireByExistingNameCaseInsensitive() {
        // Setup
        setupTestEvents();
        String input = "ROCK CONCERT\nExit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        // Execute
        RunTicket.inquireEvent(scanner);
        
        // Verify
        String output = outContent.toString();
        assertTrue("Should display revenue for Rock Concert (case insensitive)", 
                   output.contains("Revenue for: Rock Concert"));
    }
    
    @Test
    public void testInquireByExistingNameMixedCase() {
        // Setup
        setupTestEvents();
        String input = "RoCk CoNcErT\nExit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        // Execute
        RunTicket.inquireEvent(scanner);
        
        // Verify
        String output = outContent.toString();
        assertTrue("Should display revenue for Rock Concert (mixed case)", 
                   output.contains("Revenue for: Rock Concert"));
    }
    
    @Test
    public void testMultipleInquiries() {
        // Setup
        setupTestEvents();
        String input = "1\nJazz Festival\nExit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        // Execute
        RunTicket.inquireEvent(scanner);
        
        // Verify
        String output = outContent.toString();
        assertTrue("Should contain multiple revenue displays", 
                   output.contains("Revenue for: Rock Concert") && 
                   output.contains("Revenue for: Jazz Festival"));
    }
    
    // Edge Cases - Non-existing entries
    
    @Test
    public void testInquireByNonExistingID() {
        // Setup
        setupTestEvents();
        String input = "999\nExit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        // Execute
        RunTicket.inquireEvent(scanner);
        
        // Verify
        String output = outContent.toString();
        assertTrue("Should display 'Event not found' for non-existing ID", 
                   output.contains("Event not found"));
    }
    
    @Test
    public void testInquireByNonExistingName() {
        // Setup
        setupTestEvents();
        String input = "NonExistingEvent\nExit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        // Execute
        RunTicket.inquireEvent(scanner);
        
        // Verify
        String output = outContent.toString();
        assertTrue("Should display 'Event not found' for non-existing name", 
                   output.contains("Event not found"));
    }
    
    @Test
    public void testInquireWithEmptyEventList() {
        // Setup - empty eventList
        String input = "1\nExit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        // Execute
        RunTicket.inquireEvent(scanner);
        
        // Verify
        String output = outContent.toString();
        assertTrue("Should display 'Event not found' when event list is empty", 
                   output.contains("Event not found"));
    }
    
    // Edge Cases - Exit functionality
    
    @Test
    public void testExitImmediately() {
        // Setup
        setupTestEvents();
        String input = "Exit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        // Execute
        RunTicket.inquireEvent(scanner);
        
        // Verify
        String output = outContent.toString();
        assertTrue("Should exit immediately with goodbye message", 
                   output.contains("Goodbye!"));
        assertFalse("Should not display any revenue", 
                    output.contains("Revenue for:"));
    }
    
    @Test
    public void testExitCaseInsensitive() {
        // Setup
        setupTestEvents();
        String input = "EXIT\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        // Execute
        RunTicket.inquireEvent(scanner);
        
        // Verify
        String output = outContent.toString();
        assertTrue("Should exit with goodbye message (case insensitive)", 
                   output.contains("Goodbye!"));
    }
    
    @Test
    public void testExitAfterMultipleAttempts() {
        // Setup
        setupTestEvents();
        String input = "999\nWrongEvent\nExit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        // Execute
        RunTicket.inquireEvent(scanner);
        
        // Verify
        String output = outContent.toString();
        assertTrue("Should display 'Event not found' multiple times", 
                   output.contains("Event not found"));
        assertTrue("Should exit properly after multiple attempts", 
                   output.contains("Goodbye!"));
    }
    
    // Edge Cases - Input variations
    
    @Test
    public void testInquireWithSpacesInName() {
        // Setup
        RunTicket.eventList.put(1, new MockEvent(1, "Rock Concert 2024"));
        String input = "Rock Concert 2024\nExit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        // Execute
        RunTicket.inquireEvent(scanner);
        
        // Verify
        String output = outContent.toString();
        assertTrue("Should handle events with spaces in name", 
                   output.contains("Revenue for: Rock Concert 2024"));
    }
    
    @Test
    public void testInquireWithLeadingTrailingSpaces() {
        // Setup
        setupTestEvents();
        String input = "  Rock Concert  \nExit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        // Execute
        RunTicket.inquireEvent(scanner);
        
        // Verify
        String output = outContent.toString();
        assertTrue("Should trim leading/trailing spaces in event name", 
                   output.contains("Revenue for: Rock Concert"));
    }
    
    @Test
    public void testInquireWithSpecialCharactersInName() {
        // Setup
        RunTicket.eventList.put(1, new MockEvent(1, "Concert-2024!"));
        String input = "Concert-2024!\nExit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        // Execute
        RunTicket.inquireEvent(scanner);
        
        // Verify
        String output = outContent.toString();
        assertTrue("Should handle special characters in event name", 
                   output.contains("Revenue for: Concert-2024!"));
    }
    
    // Edge Cases - Large numbers and boundaries
    
    @Test
    public void testInquireWithLargeID() {
        // Setup
        RunTicket.eventList.put(Integer.MAX_VALUE, new MockEvent(Integer.MAX_VALUE, "Max Event"));
        
        String input = Integer.MAX_VALUE + "\nExit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        // Execute
        RunTicket.inquireEvent(scanner);
        
        // Verify
        String output = outContent.toString();
        assertTrue("Should handle very large event IDs", 
                   output.contains("Revenue for: Max Event"));
    }
    
    @Test
    public void testInquireWithNegativeID() {
        // Setup
        RunTicket.eventList.put(-1, new MockEvent(-1, "Negative Event"));
        
        String input = "-1\nExit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        // Execute
        RunTicket.inquireEvent(scanner);
        
        // Verify
        String output = outContent.toString();
        assertTrue("Should handle negative event IDs", 
                   output.contains("Revenue for: Negative Event"));
    }
    
    @Test
    public void testInquireWithZeroID() {
        // Setup
        RunTicket.eventList.put(0, new MockEvent(0, "Zero Event"));
        
        String input = "0\nExit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        // Execute
        RunTicket.inquireEvent(scanner);
        
        // Verify
        String output = outContent.toString();
        assertTrue("Should handle zero event ID", 
                   output.contains("Revenue for: Zero Event"));
    }
    
    // Edge Cases - Empty input and whitespace
    
    @Test
    public void testEmptyInputThenExit() {
        // Setup
        setupTestEvents();
        String input = "\nExit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        // Execute
        RunTicket.inquireEvent(scanner);
        
        // Verify
        String output = outContent.toString();
        assertTrue("Should handle empty input and show 'Event not found'", 
                   output.contains("Event not found"));
        assertTrue("Should exit properly", output.contains("Goodbye!"));
    }
    
    @Test
    public void testWhitespaceOnlyInput() {
        // Setup
        setupTestEvents();
        String input = "   \nExit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        // Execute
        RunTicket.inquireEvent(scanner);
        
        // Verify
        String output = outContent.toString();
        assertTrue("Should handle whitespace-only input", 
                   output.contains("Event not found"));
    }
    
    @Test
    public void testMultipleWhitespaceInput() {
        // Setup
        setupTestEvents();
        String input = "   \n   \nExit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        // Execute
        RunTicket.inquireEvent(scanner);
        
        // Verify
        String output = outContent.toString();
        // Should contain multiple "Event not found" messages
        assertTrue("Should handle multiple whitespace inputs", 
                   output.contains("Event not found"));
    }
    
    // Performance test for large event list
    @Test(timeout = 2000) // 2 second timeout
    public void testWithLargeEventList() {
        // Setup - create large event list
        for (int i = 0; i < 1000; i++) {
            RunTicket.eventList.put(i, new MockEvent(i, "Event" + i));
        }
        
        String input = "Event999\nExit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        // Execute
        RunTicket.inquireEvent(scanner);
        
        // Verify
        String output = outContent.toString();
        assertTrue("Should find event in large list", 
                   output.contains("Revenue for: Event999"));
    }
    
    // Test name collision handling
    @Test
    public void testMultipleEventsWithSameName() {
        // Setup - multiple events with same name but different IDs
        RunTicket.eventList.put(1, new MockEvent(1, "Concert"));
        RunTicket.eventList.put(2, new MockEvent(2, "Concert"));
        RunTicket.eventList.put(3, new MockEvent(3, "Theater"));
        
        String input = "Concert\nExit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        // Execute
        RunTicket.inquireEvent(scanner);
        
        // Verify - should find the first matching event
        String output = outContent.toString();
        assertTrue("Should find an event with duplicate name", 
                   output.contains("Revenue for: Concert"));
        // The method finds the first match, so we can't guarantee which one it is
    }
    
    // Test mixed input sequence
    @Test
    public void testMixedIDAndNameInputs() {
        // Setup
        setupTestEvents();
        String input = "1\nJazz Festival\n100\nExit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        // Execute
        RunTicket.inquireEvent(scanner);
        
        // Verify
        String output = outContent.toString();
        assertTrue("Should handle mixed ID and name inputs", 
                   output.contains("Revenue for: Rock Concert") && 
                   output.contains("Revenue for: Jazz Festival") &&
                   output.contains("Revenue for: Special Event"));
    }
    
    // Test immediate exit after failed search
    @Test
    public void testExitAfterFailedSearch() {
        // Setup
        setupTestEvents();
        String input = "Nonexistent\nExit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        // Execute
        RunTicket.inquireEvent(scanner);
        
        // Verify
        String output = outContent.toString();
        assertTrue("Should show 'Event not found' before exit", 
                   output.contains("Event not found"));
        assertTrue("Should exit properly", output.contains("Goodbye!"));
    }
    
    // Test very long event name
    @Test
    public void testVeryLongEventName() {
        // Setup
        String longName = "This is a very long event name that might test the system's handling of lengthy input strings";
        RunTicket.eventList.put(1, new MockEvent(1, longName));
        
        String input = longName + "\nExit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        
        // Execute
        RunTicket.inquireEvent(scanner);
        
        // Verify
        String output = outContent.toString();
        assertTrue("Should handle very long event names", 
                   output.contains("Revenue for: " + longName));
    }
}
