package org.oop.ca5_oop.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.oop.ca5_oop.DAO.ProductDao;
import org.oop.ca5_oop.DAO.ProductInterface;
import org.oop.ca5_oop.DTO.Product;
import org.oop.ca5_oop.Exception.DaoException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ResultsModel {
    private List<Product> productsList;

    ObservableList<Product> observableProductsList;

    public void populateList(){
        ProductDao productDao = new ProductDao();
        try {
            this.productsList = productDao.listAllProducts();

            if (this.productsList.get(0)!=null){
                this.observableProductsList.addAll(productsList);
            }
        } catch (DaoException e){
            System.out.println("An error occurred");
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public ResultsModel(){
        this.observableProductsList = FXCollections.observableArrayList();
        this.reloadProductsList();
    }

    public void reloadProductsList(){
        this.observableProductsList.clear();
        this.populateList();
    }

    public ObservableList<Product> getObservableProductsList(){
        return this.observableProductsList;
    }

    public void findByID(int id){
        this.observableProductsList.clear();
        this.productsList.clear();
        ProductDao productDao = new ProductDao();
        try {
            this.productsList.add(productDao.getProductById(id));
            this.observableProductsList.addAll(this.productsList);
        } catch (DaoException e){
            System.out.println("An error occurred");
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }


}
