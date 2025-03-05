package org.oop.ca5_oop.Objects;
import org.oop.ca5_oop.DAO.ProductDao;
import org.oop.ca5_oop.DAO.ProductInterface;
import org.oop.ca5_oop.DTO.Product;
import org.oop.ca5_oop.Exception.DaoException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class App {
    public static void main(String[] args) {
        ProductDao productDao = new ProductDao();
        App app = new App();
        app.start();
    }

    public void start(){
        int choice = 0;
        do {
            choice = runMenu();
            System.out.println("Chosen " + choice);
        } while (choice != 7);
    }

    public int runMenu(){
        Scanner kb = new Scanner(System.in);
        System.out.println("---Menu---");
        System.out.println("1) Get all products.");
        System.out.println("2) Find product by key.");
        System.out.println("3) Delete product by key.");
        System.out.println("4) Create product.");
        System.out.println("5) Update product.");
        System.out.println("6) Filter by quantity in stock.");
        System.out.print("--Your choice: ");
        int choice;
        choice = kb.nextInt();
        kb.nextLine();
        return choice;


//
//        try {
//            List<Product> products = productDao.listAllProducts();
//            for (Product product : products) {
//                System.out.println(product);
//            }
//
//        } catch (DaoException e) {
//            System.out.println("Error retrieving products: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//


    }
}

