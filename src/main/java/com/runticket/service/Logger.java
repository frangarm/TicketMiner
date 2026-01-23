package com.runticket.service;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
/**
 * The Logger class is responsible for logging actions taken within the ticketing system.
 * It follows the Singleton design pattern to ensure only one instance of the logger exists.
 * It provides a method to log actions to a file named "Log.txt".
 *
 * @author Francisco Garcia
 */
public class Logger {
    /**
     * @param logger the singleton logger, only one logger will exist, so it is static and volatile, meaning that only one logger exist on the main memory
     */
    private static volatile Logger logger = null;
    private final File logFile;
    private final BufferedWriter writeLogger;

    /**
     * Default constructor for Logger class, private to enforce singleton by preventing outside instantiation 
     * Initializes the log file and creates it if it does not exist.
     */
    private Logger(){
        try {
            this.logFile = new File("src/main/java/com/runticket/Log.txt");
            File parent = this.logFile.getParentFile();
            if (parent != null) parent.mkdirs();
            if (!this.logFile.exists()) this.logFile.createNewFile();
            this.writeLogger = new BufferedWriter(new FileWriter(this.logFile, true));
        } 
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Logs the specified action to a file named "Log.txt"
     * @param actionIn the action to be logged
     */
    //Will write onto a file, called Log.txt, each action that is taken
    public synchronized void Log(String actionIn){
        try {
            writeLogger.write("Action Taken: " + actionIn);
            writeLogger.newLine();
            writeLogger.flush();
        } 
        catch(FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        catch (IOException e){
            System.out.println("IO Exception: " + e.getMessage());
        }
    }
    /**
     * getLogger() returns the logger
     * Double checks to make sure that only one logger exist
     * initializes the logger if it does not exist
     * @return logger
     */
    public static Logger getLogger(){
        if(logger == null){
            synchronized (Logger.class) {
                if(logger == null){
                    logger = new Logger();
                }
            }
        }
        return logger;
    }
    /**
     * Closes the logger and releases resources.
     */
    public void closeLogger() {
        try {
            writeLogger.close();
        } 
        catch (IOException e) {
            System.out.println("IO Exception while closing logger: " + e.getMessage());
        }
    }
}

