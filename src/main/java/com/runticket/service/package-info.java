/*
    Name: Adrian Sifuentes, Francisco Garcia, Henry Ng, Sebastian Marquez
    Date: 11/25/25
    Course: CS3331, CRN 11809
    Intructor: Daniel Mejia
    Programming Assignment 3
    Lab Description: The purpose of this lab is to is to implement a system that lets a user buy tickets for different types of events. Users will be able to login into
    their account using an username and password and buy different tiers of tickets for events, one type for each transaction, and receive an invoice. They will also be 
    able to cancel an order and receive a refund. The system will also have a list of tickets purchased for each event, and the revenue made from each event, including fees.
    A System Administrator also needs to be able to see how many tickets/seats an event has sold, included for each ticket tier and an event's expected revenue and 
    current revenue. The System Administrator will also be able to add new events to the system and cancel events. An Autopurchaser which purchases tickets according to 
    the information on a file will also be implemented. There will also be a logger that logs each action taken, and a csv file will have the update values of customers and 
    events each time the system is exited. A receipt will be generated for each Customer transaction. The system will have a GUI.
    Honesty Statement: We completed this assignment as a team, with assitance from generative AI, but without outside assistance from other people not on the team.
 */

/**
 * Services for the RunTicket application.
 * These classes provide core functionalities such as ticket purchasing and logging.
 * They encapsulate the business logic required to manage ticket sales, generate invoices, and maintain system logs.
 *
 * @author Francisco Garcia
 * @author Adrian Sifuentes
 * @author Henry Ng
 * @author Sebastian Marquez
 */
package com.runticket.service;