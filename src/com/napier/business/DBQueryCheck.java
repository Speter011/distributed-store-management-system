package com.napier.business;

import com.napier.database.DBConnect;

import java.sql.*;

public class DBQueryCheck {
    public String check(){
        try {

            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/de_store?user=UserName&password=Password");
            //establish connection
            DBConnect connection = new DBConnect();
            connection.setDb("de_store");
            connection.setUserName("UserName");
            connection.setPassword("Password");
            Connection conn = connection.getConnection();

            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            // Create query string
            String query = "SELECT * FROM items";

            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                //warning check
                String itemName = results.getString("ItemName");
                int quantity = results.getInt("ItemQuantity");
                if(quantity < 5){
                    System.out.println("Warning! \n" + itemName + " is running out of stock!");
                    return itemName; //send back name
                }
                else{
                    return null;
                }
            }
            // Free statement resources
            statement.close();
            // Free connection resources and commit updates
            connection.close();

            //error check
        } catch (SQLException sqe) {
            System.err.println("Error in SQL Update");
            System.err.println(sqe.getMessage());
            System.exit(-1);
        }
        return null;
    }
}
