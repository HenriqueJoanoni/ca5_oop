package org.oop.ca5_oop.Objects;

import org.oop.ca5_oop.DAO.ProductDao;
import org.oop.ca5_oop.DTO.Product;
import org.oop.ca5_oop.Exception.DaoException;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
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
