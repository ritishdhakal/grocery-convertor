package com.groceryapp;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Scraper {
    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless"); // uncomment this later to run without
        // opening browser
        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://books.toscrape.com");
            System.out.println("Page OPened" + driver.getTitle());
            List<WebElement> titles = driver.findElements(By.className("product_pod"));
            System.out.println("\n--- BOOKS FOUND: " + titles.size() + " ---");

            // find all prices;
            List<WebElement> prices = driver.findElements(By.className("price_color"));
            for (WebElement price : prices) {
                System.out.println("Price: " + price.getText());
            }

        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());

        } finally {
            driver.quit();
        }

    }
}
