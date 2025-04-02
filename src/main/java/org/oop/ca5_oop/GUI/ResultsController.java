package org.oop.ca5_oop.GUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.oop.ca5_oop.DAO.ProductDao;
import org.oop.ca5_oop.DTO.Product;
import org.oop.ca5_oop.Exception.DaoException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class ResultsController {
    private ResultsModel resultsModel;


    @FXML
    public Button showAllProductsButton;

    @FXML
    public Button createButton;

    @FXML
    public Button searchByIDButton;

    @FXML
    public TextField productIDTextField;

    @FXML
    private ListView<Product> resultsList;


    private ObservableList generateResultsRowList(){
        ObservableList<Product> ol = resultsModel.getObservableProductsList();
        ObservableList<ResultRow> rowsToDisplay = FXCollections.observableArrayList();
        for (Product product: ol){
            //passing a ref to this class to let me call delete method from non static context
            //rowsToDisplay.add(new ResultRow(this, product));
            rowsToDisplay.add(new ResultRow(this, product));
        }

        return rowsToDisplay;
    }

    public ResultsController(){
        this.resultsModel = new ResultsModel();
        this.resultsModel.reloadProductsList();

    }

    @FXML
    protected void onDisplayAllProductsButtonPressed(){
        resultsModel.reloadProductsList();
        resultsList.setItems(this.generateResultsRowList());
    }


    @FXML
    protected void onSearchByIDButtonPressed(){
        if (productIDTextField.getText().equals("")||productIDTextField.getText()==null){
            return;
        }
        try {
            //find product, check for non int input
            int id = Integer.parseInt(productIDTextField.getText());
            resultsModel.findByID(id);

            resultsList.setItems(this.generateResultsRowList());
        } catch (InputMismatchException e){
            System.out.println("Error: ID must be an int");
        }

    }

    public void onDeleteButtonClicked(int id){
        ConfirmDeleteDialogue confirmDelete = new ConfirmDeleteDialogue(this, id);
        confirmDelete.showAndWait();
    }

    public void onConfirmDeleteButtonPressed(int id){
        resultsModel.deleteProduct(id);
        resultsModel.reloadProductsList();
        resultsList.setItems(this.generateResultsRowList());
    }

    public void onCreateButtonPressed(){
        CreateDialogue createDialog = new CreateDialogue(this);

        createDialog.showAndWait();
    }

    public void onConfirmCreateButtonPressed(Product newProduct){
        ProductDao productDao = new ProductDao();
        productDao.insertProduct(newProduct);
        this.onDisplayAllProductsButtonPressed();
        System.out.println("Created");
    }

    public void onEditButtonPressed(Product product){
        EditDialogue editDialog = new EditDialogue(this, product);
        editDialog.showAndWait();
    }

    public void onConfirmEditButtonPressed(Product product){
        ProductDao productDao = new ProductDao();
        try {
            productDao.updateProduct(product.getProductID(), product);
            this.onDisplayAllProductsButtonPressed();
        } catch (DaoException e){
            System.out.println("An error occurred.");
            System.out.println(e.getMessage());
        }
    }





}
