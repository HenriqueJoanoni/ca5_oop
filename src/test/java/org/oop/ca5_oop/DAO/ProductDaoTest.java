package org.oop.ca5_oop.DAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.oop.ca5_oop.DTO.Product;
import org.oop.ca5_oop.Exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoTest {
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        productDao = new ProductDao();
        clearDatabase();
    }

    private void clearDatabase() {
        try (Connection conn = productDao.startConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM product");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to clean database", e);
        }
    }

    @Test
    void insertProductAndReturn() throws DaoException {
        Product testProduct = new Product(0, "Test Chair", "Comfy chair", 99.99f, 10, "CHAIR-123");
        productDao.insertProduct(testProduct);

        List<Product> allProducts = productDao.listAllProducts();
        assertEquals(1, allProducts.size());
        assertEquals("CHAIR-123", allProducts.get(0).getProduct_sku());
    }

    @Test
    void updateProduct() throws DaoException {
        Product original = createTestProduct("Original", "OLD", 50.0f, 5, "OLD-123");

        Product updated = new Product(original.getProductID(), "Updated", "NEW", 75.0f, 3, "NEW-456");
        productDao.updateProduct(original.getProductID(), updated);

        Product result = productDao.getProductById(original.getProductID());
        assertAll("Should update all fields",
                () -> assertEquals("Updated", result.getProductName()),
                () -> assertEquals("NEW", result.getDescription()),
                () -> assertEquals(75.0f, result.getPrice(), 0.01)
        );
    }

    @Test
    void deleteProduct() throws DaoException {
        Product testProduct = createTestProduct("To Delete", "Delete me", 10.0f, 1, "DEL-123");

        productDao.deleteProductById(testProduct.getProductID());

        assertNull(productDao.getProductById(testProduct.getProductID()));
    }

    @Test
    void findProductsBasicFilter() throws DaoException {
        createTestProduct("Chair", "Office chair", 99.99f, 10, "FURN-1");
        createTestProduct("Desk", "Wooden desk", 199.99f, 5, "FURN-2");

        Product filter = new Product();
        filter.setPrice(199.99f);

        List<Product> results = productDao.findProducts(filter);
        assertEquals(1, results.size());
        assertEquals("FURN-2", results.getFirst().getProduct_sku());
    }

    @Test
    void listAllProducts() throws DaoException {
        createTestProduct("Item1", "Desc1", 10.0f, 1, "ITM-1");
        createTestProduct("Item2", "Desc2", 20.0f, 2, "ITM-2");
        createTestProduct("Item3", "Desc3", 30.0f, 3, "ITM-3");

        List<Product> all = productDao.listAllProducts();
        assertEquals(3, all.size());
    }

    private Product createTestProduct(String name, String desc, float price, int qty, String sku)
            throws DaoException {
        Product p = new Product(0, name, desc, price, qty, sku);
        productDao.insertProduct(p);

        List<Product> all = productDao.listAllProducts();
        return all.stream()
                .filter(product -> sku.equals(product.getProduct_sku()))
                .findFirst()
                .orElseThrow();
    }
}