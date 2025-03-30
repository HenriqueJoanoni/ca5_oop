package org.oop.ca5_oop.GUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.oop.ca5_oop.DTO.Product;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class ResultsController {
    private ResultsModel resultsModel;


    @FXML
    public Button showAllProductsButton;

    @FXML
    public Button searchByIDButton;

    @FXML
    public TextField productIDTextField;

    @FXML
    private ListView<Product> resultsListView;


    private ObservableList generateResultsRowList(){
        ObservableList<Product> ol = resultsModel.getObservableProductsList();
        ObservableList<ResultRow> rowsToDisplay = FXCollections.observableArrayList();
        for (Product product: ol){
            //passing a ref to this class to let me call delete method from non static context
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
        resultsListView.setItems(this.generateResultsRowList());
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

            resultsListView.setItems(this.generateResultsRowList());
        } catch (InputMismatchException e){
            System.out.println("Error: ID must be an int");
        }

    }

    public void onDeleteButtonClicked(int id){
        resultsModel.deleteProduct(id);
        resultsModel.reloadProductsList();
        resultsListView.setItems(this.generateResultsRowList());
    }





}
