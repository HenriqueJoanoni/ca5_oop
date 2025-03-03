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


        try {
            List<Product> products = productDao.listAllProducts();
            for (Product product : products) {
                System.out.println(product);
            }

        } catch (DaoException e) {
            System.out.println("Error retrieving products: " + e.getMessage());
            e.printStackTrace();
        }


    }
}

