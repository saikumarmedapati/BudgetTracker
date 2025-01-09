package com.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionTest {
    
    public static void main(String[] args) {
        // Database URL, username, and password
        String dbURL = "jdbc:mysql://localhost:3306/budgettracker_db";  // Replace with your actual database URL
        String dbUsername = "root";  // Replace with your actual database username
        String dbPassword = "root";  // Replace with your actual database password
        
        // Load and register the MySQL driver (if using MySQL)
        try {
            // Load MySQL driver class
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            Connection connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
            
            if (connection != null) {
                System.out.println("Connection successful!");
            } else {
                System.out.println("Failed to make connection.");
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Driver class not found: " + e.getMessage());
        }
    }
}
