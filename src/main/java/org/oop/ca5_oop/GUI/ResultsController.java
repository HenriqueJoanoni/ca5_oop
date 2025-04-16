package org.oop.ca5_oop.GUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.oop.ca5_oop.DAO.ProductDao;
import org.oop.ca5_oop.DTO.Product;
import org.oop.ca5_oop.Exception.DaoException;

import java.io.*;
import java.net.Socket;
import java.util.InputMismatchException;

public class ResultsController {
    private ResultsModel resultsModel;
    final static int SERVER_PORT = 1024;
    protected Socket socket;

    protected BufferedReader inStream;
    protected PrintWriter outStream;


    //PRODUCTS LIST
    ObservableList<Product> observableProductsList = FXCollections.observableArrayList();



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


    public ResultsController(){
        //this.resultsModel = new ResultsModel();
        //this.resultsModel.reloadProductsList();


        //connect to server
        try {
            Socket socket = new Socket("localhost", SERVER_PORT);
            this.socket = socket;

    
            PrintWriter outStream = new PrintWriter(this.socket.getOutputStream(), true);
            BufferedReader inStream = new BufferedReader( new InputStreamReader(socket.getInputStream()));


            //set instance vars

            this.outStream = outStream;
            this.inStream = inStream;


            this.outStream.println("Connection Request");


        } catch (Exception e){
            System.out.println("Error connecting to server");
            System.out.println(e.getMessage());
        }
        System.out.println("Client Running");

    }





    private ObservableList generateResultsRowList(){
        //this generates the Row elements to be displayed inside the ListView


        ObservableList<ResultRow> rowsToDisplay = FXCollections.observableArrayList();
        for (Product product: observableProductsList){
            //passing a ref to this class to let me call delete method from non static context
            //rowsToDisplay.add(new ResultRow(this, product));
            rowsToDisplay.add(new ResultRow(this, product));
        }

        return rowsToDisplay;
    }



    @FXML
    protected void onDisplayAllProductsButtonPressed(){
        try {
            //send req
            this.outStream.println("get allProducts");

            //await res
            StringBuilder productsJson = new StringBuilder();
            String nextLine = "";
            while ((nextLine = this.inStream.readLine()) != null && !nextLine.equals("END")){
                productsJson.append(nextLine).append("\n");
            }


            //reset products array
            observableProductsList.clear();
            JSONArray jsonArray = new JSONArray(productsJson.toString().trim());
            for (int i=0;i<jsonArray.length();i++){
                JSONObject object = (JSONObject) jsonArray.get(i);
                observableProductsList.add(new Product(
                        object.getInt("productID"),
                        object.getString("productName"),
                        object.getString("description"),
                        object.getFloat("price"),
                        object.getInt("qtyInStock"),
                        object.getString("product_sku")
                ));
            }

            //rerender list
            resultsList.setItems(this.generateResultsRowList());
        } catch (Exception e){
            System.out.println("An error occurred");
            System.out.println(e.getMessage());
            System.exit(1);
        }

    }


    @FXML
    protected void onSearchByIDButtonPressed(){
        if (productIDTextField.getText().equals("")||productIDTextField.getText()==null){
            return;
        }
        try {
            //find product, check for non int input
            int id = Integer.parseInt(productIDTextField.getText());


            //send req
            this.outStream.println("find " + id);

            //await res
            //await res
            StringBuilder productJsonSB = new StringBuilder();
            String nextLine = "";
            while ((nextLine = this.inStream.readLine()) != null && !nextLine.equals("END")){
                productJsonSB.append(nextLine).append("\n");
            }
            String productJSON = productJsonSB.toString().trim();


            System.out.println(productJSON);
            if (!productJSON.equals("NOT FOUND")){
                JSONObject object = new JSONObject(productJSON.toString().trim());
                observableProductsList.add(new Product(
                        object.getInt("productID"),
                        object.getString("productName"),
                        object.getString("description"),
                        object.getFloat("price"),
                        object.getInt("qtyInStock"),
                        object.getString("product_sku")
                ));
            }

            resultsList.setItems(this.generateResultsRowList());
        } catch (InputMismatchException e){
            System.out.println("Error: ID must be an int");
        } catch(IOException e){
            System.out.println("An error occurred");
            System.out.println(e.getMessage());
        }

    }

    public void onDeleteButtonClicked(int id){
        ConfirmDeleteDialogue confirmDelete = new ConfirmDeleteDialogue(this, id);
        confirmDelete.showAndWait();
    }

    public void onConfirmDeleteButtonPressed(int id){
        //send req
        this.outStream.println("delete " + id);
        this.onDisplayAllProductsButtonPressed();
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
