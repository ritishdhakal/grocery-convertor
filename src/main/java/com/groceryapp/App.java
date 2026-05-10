package com.groceryapp;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class App {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/grocerydb";
        String user = "root";
        String password = "";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection successful");

            // read all stores;
            readStores(conn);

            // inset a product
            insertProduct(conn, "Pasta", "500g", "Staples");

            // read again
            readProduct(conn);
            conn.close();

        } catch (Exception e) {
            System.out.println("Connetion faield" + e.getMessage());
        }

    }

    static void readStores(Connection conn) throws Exception {
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery("Select * from stores");

        System.out.println("\n--------STORES-----------");

        while (result.next()) {
            System.out.println(result.getInt("id") + "|" + result.getString("name"));
        }

    }

    // insert into database;

    static void insertProduct(Connection conn, String name, String unit, String category) throws Exception {
        String sql = "insert into products(name, unit, category values(?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, unit);
        ps.setString(3, category);
        System.out.println("\n Prodcut inserted " + name);

    }

    static void readProduct(Connection conn) throws Exception {
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery("Select * from products");

        System.out.println("\n--------Products-----------");

        while (result.next()) {
            System.out.println(result.getInt("id") + "|" + result.getString("name"));
        }

    }
}
