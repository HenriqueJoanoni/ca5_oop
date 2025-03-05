package org.oop.ca5_oop.DAO;

import org.oop.ca5_oop.DTO.Product;
import org.oop.ca5_oop.Exception.DaoException;

import java.util.List;

public interface ProductInterface {

    void updateProduct(int productId, Product product) throws DaoException;

    List<Product> findProducts(Product filter) throws DaoException;
}
