package com.napier.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

    private String db;
    private String userName;
    private String password;
    private Connection conn = null;

    public void setDb(String db) {
        this.db = db;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDb() {
        return db;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Connection getConnection() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db + "?user=" + userName + "&password=" + password);
        //System.out.println("Connected to database");
        return conn;
    }
    public void close() throws SQLException {
        conn.close();
    }
}