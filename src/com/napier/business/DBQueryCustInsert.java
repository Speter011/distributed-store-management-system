package com.napier.business;

import com.napier.database.DBConnect;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBQueryCustInsert {
    public void insert(String id, String name, String loyalty, String purchases, String approval){
        try{
            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/de_store?user=UserName&password=Password");
            //establish connection
            DBConnect connection = new DBConnect();
            connection.setDb("de_store");
            connection.setUserName("UserName");
            connection.setPassword("Password");
            Connection conn = connection.getConnection();

            // Create a new SQL statement
            Statement statement = conn.createStatement();
            // Build the INSERT statement
            String update = "INSERT INTO customers (id, CustomerName, LoyaltyCard, PurchaseNum, financeApproval) " +
                    "VALUES ('" + id + "', '" + name + "', '" + loyalty + "', '" + purchases + "', '" + approval + "')";

            // Execute the statement
            statement.executeUpdate(update);
            // Release resources held by the statement
            statement.close();
            // Release resources held by the connection.  This also ensures that the INSERT completes
            conn.close();
            System.out.println("Update successful");

        }
        //error
        catch (SQLException sqe)
        {
            System.err.println("Error performing SQL Update");
            System.err.println(sqe.getMessage());
            System.exit(-1);
        }
    }
}
