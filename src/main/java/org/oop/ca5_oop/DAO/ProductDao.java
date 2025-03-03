package org.oop.ca5_oop.DAO;

import org.oop.ca5_oop.DTO.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductDao extends MySQLDao implements ProductInterface{

    public void deleteProductByKey(int ID){
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

    public void insertProduct(Product product){
        try {
            Connection conn = startConnection();
            String query = "INSERT INTO product (product_name, product_description, product_price, qty_in_stock, product_sku) " +
                            "VALUES (?, ?, ?, ?, ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, product.product_name);
            ps.setString(2, product.product_description);
            ps.setDouble(3, product.product_price);
            ps.setInt(4, product.qty_in_stock);
            ps.setString(5, product.product_SKU);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0){
                System.out.println("Product Deleted");
            }
        } catch (SQLException e){
            System.out.println("Error adding to database");
        }
    }
}
