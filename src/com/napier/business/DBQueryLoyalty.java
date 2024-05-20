package com.napier.business;

import com.napier.database.DBConnect;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class DBQueryLoyalty {
    public void update(String id, String loyalty) {
        try {
            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/de_store?user=UserName&password=Password");
            //establish connection
            DBConnect connection = new DBConnect();
            connection.setDb("de_store");
            connection.setUserName("UserName");
            connection.setPassword("Password");
            Connection conn = connection.getConnection();

            // Set up keyboard input
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    System.in));

            Statement statement = conn
                    .createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
            // Create query string
            String query = "SELECT * FROM customers WHERE id = '"
                    + id + "'";
            // Get results from query
            ResultSet results = statement.executeQuery(query);
            // Check if we have results
            if (results.next()) {
                // loyalty update
                results.first();
                results.updateString("LoyaltyCard", loyalty);
                // Update the row in the DB
                results.updateRow();
                System.out.println("Record updated");

            } else {
                // No matching records. Display message
                System.out.println("Record does not exist");

            }
            // Free statement resources
            statement.close();
            // Free connection resources and commit updates
            conn.close();
            //err
        }  catch (SQLException sqe) {
            System.err.println("Error in SQL Update");
            System.err.println(sqe.getMessage());
            System.exit(-1);
        }
    }
}