package org.oop.ca5_oop.Objects;

import org.oop.ca5_oop.DAO.ProductDao;
import org.oop.ca5_oop.DTO.Product;
import org.oop.ca5_oop.Exception.DaoException;
import org.oop.ca5_oop.Handlers.InputHandler;
import org.oop.ca5_oop.utils.ProductJsonConverter;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        App app = new App();
        app.start();
    }

    public void start() {
        int choice = 0;
        do {
            choice = runMenu();
            switch (choice) {
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
                    displayAllProductsAsJson();
                    break;
                case 8:
                    displaySingleProductAsJson();
                    break;
                case 9:
                    System.out.println("Exiting");
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } while (choice != 9);
    }

    public int runMenu() {
        Scanner kb = new Scanner(System.in);
        System.out.println("---Menu---");
        System.out.println("1) Get all products.");
        System.out.println("2) Find product by key.");
        System.out.println("3) Delete product by key.");
        System.out.println("4) Create product.");
        System.out.println("5) Update product.");
        System.out.println("6) Filter products.");
        System.out.println("7) Show ALL products as JSON.");
        System.out.println("8) Show ONE product as JSON.");
        System.out.println("9) Exit.");
        System.out.print("--Your choice: ");
        int choice = kb.nextInt();
        kb.nextLine();
        return choice;
    }

    public void getAllProducts() {
        try {
            ProductDao productDao = new ProductDao();
            List<Product> products = productDao.listAllProducts();
            for (Product product : products) {
                System.out.println(product);
            }
        } catch (DaoException e) {
            System.out.println("Error reading from DB");
            e.printStackTrace();
        }
    }

    public void getProductByID() {
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

    public void deleteProductById() {
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter product key to delete:");
        int id = kb.nextInt();
        kb.nextLine();

        ProductDao productDao = new ProductDao();
        try {
            productDao.deleteProductById(id);
        } catch (DaoException e) {
            System.out.println("Unable to delete");
        }
    }

    public void createProduct() {
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

        System.out.print("Enter product sku: ");
        product_sku = kb.nextLine();

        System.out.print("Enter image file name (or leave empty for 'no_photo.png'): ");
        String imageName = kb.nextLine();
        if (imageName.isEmpty()) {
            imageName = "no_photo.png";
        }


        Product newProduct = new Product(productName, description, price, qtyInStock, product_sku, imageName);

        productDao.insertProduct(newProduct);
    }

    public void updateProduct() {
        Scanner kb = new Scanner(System.in);
        ProductDao productDao = new ProductDao();
        try {
            System.out.print("Enter product ID:");
            int id = kb.nextInt();
            kb.nextLine();

            Product product = productDao.getProductById(id);
            if (product == null) {
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

            System.out.print("Enter new image file name (or leave empty to keep current '" + product.getImageName() + "'): ");
            String imageName = kb.nextLine();
            if (imageName.isEmpty()) {
                imageName = product.getImageName();
            }

            String productSKU = product.getProduct_sku();
            Product updatedProduct = new Product(name, description, price, qtyInStock, productSKU, imageName);
            productDao.updateProduct(id, updatedProduct);
            System.out.println("Successfully updated");
        } catch (DaoException e) {
            System.out.println("Error updating product:");
            System.out.println(e.getMessage());
        }
    }

    public void filterProducts() {
        Scanner kb = new Scanner(System.in);
        ProductDao productDao = new ProductDao();
        InputHandler handler = new InputHandler();

        System.out.println("\nSearch Products By:");
        System.out.println("1. Product Name");
        System.out.println("2. Description");
        System.out.println("3. Price");
        System.out.println("4. Quantity in Stock");
        System.out.println("5. SKU");
        System.out.println("6. Back to main menu");

        int userChoice = 0;
        boolean validChoice = false;

        while (!validChoice) {
            try {
                System.out.print("Enter your choice (1-6): ");
                userChoice = kb.nextInt();
                kb.nextLine();

                if (userChoice < 1 || userChoice > 6) {
                    throw new InputMismatchException();
                }
                validChoice = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number between 1 and 6.");
                kb.nextLine();
            }
        }

        Product filter = new Product();

        try {
            switch (userChoice) {
                case 1:
                    handler.handleStringInput(kb, "product name", filter::setProductName);
                    break;
                case 2:
                    handler.handleStringInput(kb, "description", filter::setDescription);
                    break;
                case 3:
                    handler.handleNumericInput(kb, "price (e.g., 19.99)", "float",
                            () -> filter.setPrice(kb.nextFloat()));
                    break;
                case 4:
                    handler.handleNumericInput(kb, "quantity in stock", "integer",
                            () -> filter.setQtyInStock(kb.nextInt()));
                    break;
                case 5:
                    handler.handleStringInput(kb, "SKU", filter::setProduct_sku);
                    break;
                case 6:
                    runMenu();
                    break;
            }
        } catch (Exception e) {
            System.err.println("Error processing input: " + e.getMessage());
            return;
        }

        try {
            List<Product> results = productDao.findProducts(filter);

            if (results.isEmpty()) {
                System.out.println("\nNo matching products found.");
            } else {
                System.out.println("\nFound " + results.size() + " matching product(s):");
                results.forEach(System.out::println);
            }
        } catch (DaoException e) {
            System.err.println("\nDatabase error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void displayAllProductsAsJson() {
        try {
            ProductDao dao = new ProductDao();
            List<Product> products = dao.listAllProducts();
            String json = ProductJsonConverter.productsListToJsonString(products);
            System.out.println("\nAll Products in JSON:\n" + json);
        } catch (DaoException e) {
            System.out.println("Error converting products to JSON: " + e.getMessage());
        }
    }

    public void displaySingleProductAsJson() {
        Scanner scanner = new Scanner(System.in);
        ProductDao dao = new ProductDao();
        try {
            System.out.print("Enter Product ID to convert to JSON: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Product p = dao.getProductById(id);
            if (p != null) {
                String json = ProductJsonConverter.productToJsonString(p);
                System.out.println("\nProduct in JSON:\n" + json);
            } else {
                System.out.println("Product not found.");
            }
        } catch (DaoException e) {
            System.out.println("Error converting product to JSON: " + e.getMessage());
        }
    }
}
