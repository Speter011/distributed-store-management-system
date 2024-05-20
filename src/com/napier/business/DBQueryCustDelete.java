package com.napier.business;

import com.napier.database.DBConnect;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBQueryCustDelete {
    public void delete(String id){
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
            String update = "DELETE FROM customers WHERE id= '" + id + "'";

            // Execute the statement
            statement.executeUpdate(update);
            // Release resources held by the statement
            statement.close();
            // Release resources held by the connection.  This also ensures that the INSERT completes
            conn.close();
            System.out.println("Deletion successful");

        }
        //error check
        catch (SQLException sqe)
        {
            System.err.println("Error performing SQL Update");
            System.err.println(sqe.getMessage());
            System.exit(-1);
        }
    }

}
