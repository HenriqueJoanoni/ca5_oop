package org.oop.ca5_oop.DAO;
import org.oop.ca5_oop.DTO.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.oop.ca5_oop.Exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ProductDao extends MySQLDao implements ProductInterface {

    @Override
    public void updateProduct(int productId, Product product) throws DaoException {
        String query = "UPDATE product SET product_name=?, product_description=?, product_price=?, qty_in_stock=?, product_sku=? WHERE product_ID=?";

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
            conditions.add("product_description = ?");
            parameters.add(filter.getDescription());
        }

        if (filter.getPrice() != 0.0f) {
            conditions.add("product_price = ?");
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
                        rs.getString("product_description"),
                        rs.getFloat("product_price"),
                        rs.getInt("qty_in_stock"),
                        rs.getString("product_sku"),
                        rs.getString("imageName")
                );
                products.add(product);
            }

        } catch (SQLException e) {
            throw new DaoException("ERROR: could not retrieve products: " + e.getMessage());
        }

        return products;
    }

    @Override
    public List<Product> listAllProducts() throws DaoException{
        System.out.println("print new");
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product";

        try (Connection connection = this.startConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("product_ID");
                String name = resultSet.getString("product_name");
                String description = resultSet.getString("product_description");
                float price = resultSet.getFloat("product_price");
                int qtyInStock = resultSet.getInt("qty_in_stock");
                String sku = resultSet.getString("product_sku");
                String imageName = resultSet.getString("image_name");

                products.add(new Product(id, name, description, price, qtyInStock, sku, imageName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("listAllProducts() " + e.getMessage());
        }
        return products;
    }

    @Override
    public Product getProductById(int id) throws DaoException {
        String query = "SELECT * FROM product WHERE product_ID = ?";
        Product product = null;

        try (Connection connection = this.startConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("product_name");
                String description = resultSet.getString("product_description");
                float price = resultSet.getFloat("product_price");
                int qtyInStock = resultSet.getInt("qty_in_stock");
                String sku = resultSet.getString("product_sku");
                String imageName = resultSet.getString("image_name");

                product = new Product(id, name, description, price, qtyInStock, sku, imageName);
            }
        } catch (SQLException e) {
            throw new DaoException("getProductById() " + e.getMessage());
        }
        return product;
    }

    @Override
    public void deleteProductById(int ID) throws DaoException{
        try {
            Connection conn = startConnection();
            String query = "DELETE FROM product WHERE product_ID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, ID);

            int affectedRows = ps.executeUpdate();
            if (affectedRows>0){
                System.out.println("Product Deleted");
            }
        } catch (SQLException e){
            System.out.println("Error Deleting from database");
        }
    }

    @Override
    public void insertProduct(Product product){
        try {
            Connection conn = startConnection();
            String query = "INSERT INTO product (product_name, product_description, product_price, qty_in_stock, product_sku, image_name) " +
                    "VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, product.getProductName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getQtyInStock());
            ps.setString(5, product.getProduct_sku());
            ps.setString(6, product.getImageName());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0){
                System.out.println("Product Inserted");
            }
        } catch (SQLException e){
            System.out.println("Error adding to database");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}


