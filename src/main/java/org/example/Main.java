package org.example;

import java.sql.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static Connection connection;
    public static void main(String[] args) throws SQLException {
        try {
            openDatabaseConnection();
        } finally {
            closeDatabaseConnection();
        }
    }

    private static void openDatabaseConnection() throws SQLException {
        System.out.println("Connecting to the database...");
        connection = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/jdbc_demo",
                "bilal", "password"
        );

        System.out.println("Connection valid: " + connection.isValid(5));
    }

    private static void closeDatabaseConnection() throws SQLException {
        System.out.println("Closing the database connection...");
        connection.close();
        System.out.println("Connection valid: " + connection.isValid(5));
    }
}