package org.oop.ca5_oop.DAO;
import org.oop.ca5_oop.DTO.Product;
import org.oop.ca5_oop.Exception.DaoException;
import java.sql.*;
import java.util.List;


public interface ProductInterface {
    public List<Product> listAllProducts() throws DaoException;
}
