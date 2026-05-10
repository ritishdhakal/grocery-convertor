package com.groceryapp;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class App {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/grocerydb";
        String user = "root";
        String password = "";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection successful");

            Statement statement = conn.createStatement(); // statement is java object that has ability to send sql to
                                                          // mysql and get response back
            ResultSet result = statement.executeQuery("Select * from stores");
            while (result.next()) {
                System.out.println("Store: " + result.getString("name"));
            }
            conn.close();

        } catch (Exception e) {
            System.out.println("Connetion faield" + e.getMessage());
        }

    }
}
