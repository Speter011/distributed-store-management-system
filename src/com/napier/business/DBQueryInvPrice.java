package com.napier.business;


import com.napier.database.DBConnect;

import java.sql.*;

public class DBQueryInvPrice {
    public void list() {
        try {

            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/de_store?user=UserName&password=Password");
            //establish connection
            DBConnect connection = new DBConnect();
            connection.setDb("de_store");
            connection.setUserName("UserName");
            connection.setPassword("Password");
            Connection conn = connection.getConnection();

            //statement obj
            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
            // Create query string
            String query = "SELECT * FROM items";

            ResultSet results = statement.executeQuery(query);
            while (results.next())
            {
                //query for list
                String itemName = results.getString("ItemName");
                String id = results.getString("id");
                String price = results.getString("price");
                String offer = results.getString("ItemOffer");
                //print list
                System.out.println("Item name: " + itemName + " ID: " + id + " Price: " + price +" Offer: " + offer);

            }
            // Free statement resources
            statement.close();
            // Free connection resources and commit updates
            conn.close();

        }
        //error
        catch (SQLException sqe) {
            System.err.println("Error in SQL Update");
            System.err.println(sqe.getMessage());
            System.exit(-1);
        }
    }

    public void priceChange(String id, String price, String offer){
        try{
            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/de_store?user=UserName&password=Password");
            //establish connection
            DBConnect connection = new DBConnect();
            connection.setDb("de_store");
            connection.setUserName("UserName");
            connection.setPassword("Password");
            Connection conn = connection.getConnection();

            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            // Create query string
            String query = "SELECT * FROM items WHERE id = '"
                    + id + "'";
            // Get results from query
            ResultSet results = statement.executeQuery(query);
            // Check if we have results
            if (results.next()) {
                // We will update the first hit (there should be only one)
                results.first();
                //get for output
                String oldPrice = results.getString("price");
                String name = results.getString("ItemName");
                results.updateString("price", price);
                results.updateString("ItemOffer", offer);
                // Update the row in the DB
                results.updateRow();
                //print
                System.out.println("Price of " + name + " changed from " + oldPrice + " to: " + price);
                System.out.println("Offer changed to: " + offer);

            } else {
                // No matching records. Display message
                System.out.println("Record does not exist");

            }
            // Free statement resources
            statement.close();
            // Free connection resources and commit updates
            conn.close();
        }  catch (SQLException sqe) {
        System.err.println("Error in SQL Update");
        System.err.println(sqe.getMessage());
        System.exit(-1);
    }
    }
}
