package com.napier.business;

import com.napier.database.DBConnect;

import java.sql.*;

public class DBQueryStatistics {

    public void stats() {
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
            // Create query string (average loyal spent)
            String query = "SELECT AVG(orders.totalPrice) FROM orders INNER JOIN customers ON orders.customerID=customers.ID " +
                    "WHERE customers.LoyaltyCard = 'YES'";

            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                System.out.println("Average spent by a loyal customer: " + results.getString(1)+ "£");
            }

            // Free statement resources
            statement.close();
            // Free connection resources and commit updates
            conn.close();

        } catch (SQLException sqe) {
            System.err.println("Error in SQL Update");
            System.err.println(sqe.getMessage());
            System.exit(-1);
        }

    }
    public void moreStats(){
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
            // Create query string (all income)
            String query = "SELECT SUM(orders.totalPrice) FROM orders WHERE YEAR(Date)='2023'";

            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                System.out.println("Total income in the last year: " + results.getString(1) + "£");
            }

            // Free statement resources
            statement.close();
            // Free connection resources and commit updates
            conn.close();

        } catch (SQLException sqe) {
            System.err.println("Error in SQL Update");
            System.err.println(sqe.getMessage());
            System.exit(-1);
        }
    }
    public void list(){
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
            String query = "SELECT Count(*) FROM orders WHERE YEAR(Date)='2023'";

            //query (order number)
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                //results.updateString("Price", price);
                //results.updateRow();
                System.out.println("Number of orders in the last year: " + results.getString(1));

            }
            //query (financial approved orders)
            String query2 = "SELECT Count(customers.financeApproval) FROM customers INNER JOIN orders ON orders.customerID=customers.ID " +
                                        "WHERE customers.financeApproval = 'YES'";

            ResultSet results2 = statement.executeQuery(query2);
            while (results2.next()) {
                System.out.println("Number of financially approved orders: " + results2.getString(1));
            }
            // Free statement resources
            statement.close();
            // Free connection resources and commit updates
            conn.close();

        } catch (SQLException sqe) {
            System.err.println("Error in SQL Update");
            System.err.println(sqe.getMessage());
            System.exit(-1);
        }
    }
}