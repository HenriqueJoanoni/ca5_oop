package org.oop.ca5_oop.DAO;

import org.oop.ca5_oop.DTO.Product;
import org.oop.ca5_oop.Exception.DaoException;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao extends MySQLDao implements ProductInterface {

    @Override
    public void updateProduct(int productId, Product product) throws DaoException {
        String query = "UPDATE product SET product_name=?, description=?, price=?, qty_in_stock=?, product_sku=? WHERE product_ID=?";

        try (Connection conn = this.startConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getDescription());
            stmt.setFloat(3, product.getPrice());
            stmt.setInt(4, product.getQtyInStock());
            stmt.setString(5, product.getProduct_sku());
            stmt.setInt(6, productId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("ERROR: could not update product: " + e.getMessage());
        }
    }

    @Override
    public List<Product> findProducts(Product filter) throws DaoException {
        List<Product> products = new ArrayList<>();
        List<String> conditions = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM product");

        if (filter.getProductName() != null && !filter.getProductName().isEmpty()) {
            conditions.add("product_name = ?");
            parameters.add(filter.getProductName());
        }
        if (filter.getDescription() != null && !filter.getDescription().isEmpty()) {
            conditions.add("description = ?");
            parameters.add(filter.getDescription());
        }
        if (filter.getPrice() != 0.0f) {
            conditions.add("price = ?");
            parameters.add(filter.getPrice());
        }
        if (filter.getQtyInStock() != 0) {
            conditions.add("qty_in_stock = ?");
            parameters.add(filter.getQtyInStock());
        }
        if (filter.getProduct_sku() != null && !filter.getProduct_sku().isEmpty()) {
            conditions.add("product_sku = ?");
            parameters.add(filter.getProduct_sku());
        }

        if (!conditions.isEmpty()) {
            queryBuilder.append(" WHERE ");
            queryBuilder.append(String.join(" AND ", conditions));
        }

        try (Connection conn = this.startConnection();
             PreparedStatement stmt = conn.prepareStatement(queryBuilder.toString())) {

            for (int i = 0; i < parameters.size(); i++) {
                Object param = parameters.get(i);
                if (param instanceof String) {
                    stmt.setString(i + 1, (String) param);
                } else if (param instanceof Float) {
                    stmt.setFloat(i + 1, (Float) param);
                } else if (param instanceof Integer) {
                    stmt.setInt(i + 1, (Integer) param);
                }
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_ID"),
                        rs.getString("product_name"),
                        rs.getString("description"),
                        rs.getFloat("price"),
                        rs.getInt("qty_in_stock"),
                        rs.getString("product_sku")
                );
                products.add(product);
            }

        } catch (SQLException e) {
            throw new DaoException("ERROR: could not retrieve products: " + e.getMessage());
        }

        return products;
    }
}
