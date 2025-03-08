package org.oop.ca5_oop.Objects;

import org.oop.ca5_oop.DAO.ProductDao;
import org.oop.ca5_oop.DTO.Product;
import org.oop.ca5_oop.Exception.DaoException;

import java.util.ArrayList;
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
                    break;
                case 2:
                    getProductByID();
                    break;
                case 3:
                    deleteProductById();
                    break;
                case 4:
                    createProduct();
                    break;
                case 5:
                    updateProduct();
                    break;
                case 6:
                    filterProducts();
                    break;
                case 7:
                    System.out.println("Exiting");
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;

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
        System.out.println("6) Filter products.");
        System.out.print("--Your choice: ");
        int choice;
        choice = kb.nextInt();
        kb.nextLine();
        return choice;
    }

    public void getAllProducts(){
        try {
            ProductDao productDao = new ProductDao();
            List<Product> products = productDao.listAllProducts();
            for (Product product: products){
                System.out.println(product);
            }
        } catch(DaoException e){
            System.out.println("Error reading from DB");
            e.printStackTrace();
        }

    }
  
  
    public void getProductByID(){
        ProductDao productDao = new ProductDao();
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("\nEnter Product ID to get product: ");
            int productId = scanner.nextInt();
            scanner.nextLine();


            Product product = productDao.getProductById(productId);
            if (product != null) {
                System.out.println("--Product Details:");
                System.out.println(product);
            } else {
                System.out.println("--No product found with ID: " + productId);
            }
        } catch (DaoException e) {
            System.out.println("--Error retrieving product: " + e.getMessage());
            e.printStackTrace();
        }


    }

    public void deleteProductById(){
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter product key to delete:");
        int id = kb.nextInt();
        kb.nextLine();

        ProductDao productDao = new ProductDao();
        try {

            productDao.deleteProductById(id);
        } catch (DaoException e){
            System.out.println("Unable to delete");
        }


    }

    public void createProduct(){
        Scanner kb = new Scanner(System.in);
        ProductDao productDao = new ProductDao();
        String productName;
        String description;
        float price;
        int qtyInStock;
        String product_sku;

        System.out.print("Enter product name:");
        productName = kb.nextLine();

        System.out.print("Enter product description:");
        description = kb.nextLine();

        System.out.print("Enter product price:");
        price = kb.nextFloat();
        kb.nextLine();

        System.out.print("Enter quantity in stock:");
        qtyInStock = kb.nextInt();
        kb.nextLine();

        System.out.print("Enter product sku");
        product_sku = kb.nextLine();

        Product newProduct = new Product(productName, description, price, qtyInStock, product_sku);

        productDao.insertProduct(newProduct);




    }

    public void updateProduct(){
        Scanner kb = new Scanner(System.in);
        ProductDao productDao = new ProductDao();
        try {
            System.out.print("Enter product ID:");
            int id = kb.nextInt();
            kb.nextLine();

            Product product = productDao.getProductById(id);
            if (product==null){
                System.out.println("Product not found");
                return;
            }

            System.out.println("Product Found:" + product.getProductName());
            System.out.print("Enter new name:");
            String name = kb.nextLine();

            System.out.print("Enter new description: ");
            String description = kb.nextLine();

            System.out.print("Enter new price: ");
            float price = kb.nextFloat();
            kb.nextLine();

            System.out.print("Enter quantity in stock:");
            int qtyInStock = kb.nextInt();
            kb.nextLine();

            //product SKU should be a constant value,
            // therefore we are choosing not to give the user the ability to update it

            String productSKU = product.getProduct_sku();
            Product updatedProduct = new Product(name, description, price, qtyInStock, productSKU);
            productDao.updateProduct(id, updatedProduct);
            System.out.println("Successfully updated");
        } catch (DaoException e){
            System.out.println("Error updating product:");
            System.out.println(e.getMessage());
        }
    }

    public void filterProducts(){
        Scanner kb = new Scanner(System.in);
        ProductDao productDao = new ProductDao();

        try {
            System.out.print("Names to filter by:");
            String nameFilter = kb.nextLine();

            System.out.print("Description to filter by:");
            String descFilter = kb.nextLine();

            System.out.print("Price to search for:");
            Float priceFilter = kb.nextFloat();
            kb.nextLine();

            System.out.println("Enter quantity in stock to search for");
            int qtyInStockFilter = kb.nextInt();
            kb.nextLine();

            System.out.println("Product SKU to search for:");
            String productSKUFilter = kb.nextLine();

            Product filterProduct = new Product(nameFilter, descFilter, priceFilter, qtyInStockFilter, productSKUFilter);
            List<Product> results = productDao.findProducts(filterProduct);

            System.out.println("Results:");
            for (Product product: results){
                System.out.println(product);
            }

        }
        catch (DaoException e){
            System.out.println("An error occurred");
            System.out.println(e.getMessage());
        }
    }




}

