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
        String query = "SELECT * FROM products";

        try (Connection connection = this.startConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("productID");
                String name = resultSet.getString("productName");
                String description = resultSet.getString("description");
                float price = resultSet.getFloat("price");
                int qtyInStock = resultSet.getInt("qtyInStock");
                String sku = resultSet.getString("product_sku");

                products.add(new Product(id, name, description, price, qtyInStock, sku));
            }
        } catch (SQLException e) {
            throw new DaoException("listAllProducts() " + e.getMessage());
        }
        return products;
    }



    public void updateProduct() {




    }
}
