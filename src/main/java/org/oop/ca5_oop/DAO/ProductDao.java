package org.oop.ca5_oop.DAO;
import org.oop.ca5_oop.DTO.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.oop.ca5_oop.Exception.DaoException;
import org.oop.ca5_oop.DAO.MySQLDao;

public class ProductDao extends MySQLDao implements ProductInterface {

    public List<Product> listAllProducts() throws DaoException{
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

                products.add(new Product(id, name, description, price, qtyInStock, sku));
            }
        } catch (SQLException e) {
            throw new DaoException("listAllProducts() " + e.getMessage());
        }
        return products;
    }




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



    public void updateProduct() {




    }
}
