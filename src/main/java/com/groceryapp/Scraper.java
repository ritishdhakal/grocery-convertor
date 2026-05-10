package com.groceryapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Scraper {
    // Database connection
    static String url = "jdbc:mysql://localhost:3306/grocerydb";
    static String user = "root";
    static String password = "";

    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless"); // uncomment this later to run without
        // opening browser
        WebDriver driver = new ChromeDriver(options);

        try {

            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected");

            driver.get("https://books.toscrape.com");
            System.out.println("Page OPened");

            List<WebElement> titles = driver.findElements(By.cssSelector(".product_pod h3 a"));
            List<WebElement> prices = driver.findElements(By.className("price_color"));
            System.out.println("\n--Scrapping and saving--");

            // loop through each book and save to db

            for (int i = 0; i < titles.size(); i++) {
                String title = titles.get(i).getAttribute("title");
                String price = prices.get(i).getText().replace("£", "").trim();
                /*
                 * "£51.77" → getText()
                 * "£51.77" → replace("£", "") → "51.77"
                 * "51.77" → trim() → "51.77"
                 * Clean number ready to save in MySQL!
                 * 
                 * Make sense? Ready to run it now?
                 */

                // save to database

                saveProduct(conn, title, price);

                System.out.println("Saved" + title + " | £" + price);

            }
            System.out.println("\n all done ");
            conn.close();

        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());

        } finally {
            driver.quit();
        }

    }

    static void saveProduct(Connection conn, String name, String price) throws Exception {
        String sql = "Insert into products (name, unit, category) Values (?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, "1 book");
        ps.setString(3, "books");
        ps.executeUpdate();
    }
}
