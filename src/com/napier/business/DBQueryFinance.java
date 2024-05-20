package com.napier.business;

import com.napier.database.DBConnect;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class DBQueryFinance {
    public void update(String id, String approval) {
        try {
            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/de_store?user=UserName&password=Password");
            //establish connection
            DBConnect connection = new DBConnect();
            connection.setDb("de_store");
            connection.setUserName("UserName");
            connection.setPassword("Password");
            Connection conn = connection.getConnection();

            Statement statement = conn
                    .createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
            // Create query string
            String query = "SELECT * FROM customers WHERE id = '"
                    + id + "'";
            // Get results from query
            ResultSet results = statement.executeQuery(query);
            if (results.next()) {
                // update approval
                results.first();
                results.updateString("financeApproval", approval);
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
            //error
        } catch (SQLException sqe) {
            System.err.println("Error in SQL Update");
            System.err.println(sqe.getMessage());
            System.exit(-1);
        }
    }
}
