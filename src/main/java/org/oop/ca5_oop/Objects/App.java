package org.oop.ca5_oop.Objects;

import org.oop.ca5_oop.DAO.ProductDao;
import org.oop.ca5_oop.DTO.Product;
import org.oop.ca5_oop.Exception.DaoException;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        App app = new App();
        app.start();
    }

    public void start(){
        int choice = 0;
        do {
            choice = runMenu();
            switch (choice){
                case 1:
                    getAllProducts();
            }
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
    }

    public void getAllProducts(){

    }
  
  
    public void getProductByID(){
        ProductDao productDao = new ProductDao();
        Scanner scanner = new Scanner(System.in);

        try {
            List<Product> products = productDao.listAllProducts();
            for (Product product : products) {
                System.out.println(product);
            }

            System.out.println("\nEnter Product ID to get product: ");
            int productId = scanner.nextInt();


            Product product = productDao.getProductById(productId);
            if (product != null) {
                System.out.println("\nProduct Details:");
                System.out.println(product);
            } else {
                System.out.println("\nNo product found with ID: " + productId);
            }
        } catch (DaoException e) {
            System.out.println("Error retrieving product: " + e.getMessage());
            e.printStackTrace();
        }

        scanner.close();
    }
}

