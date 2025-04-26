package org.oop.ca5_oop.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.oop.ca5_oop.DTO.Product;
import org.oop.ca5_oop.utils.ProductJsonConverter;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;

import java.io.*;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class GUIController {
    final static int SERVER_PORT = 1024;
    protected Socket socket;

    protected BufferedReader inStream;
    protected PrintWriter outStream;

    ObservableList<Product> observableProductsList = FXCollections.observableArrayList();

    @FXML
    public Button showAllProductsButton;

    @FXML
    public Button createButton;

    @FXML
    public Button searchByIDButton;

    @FXML
    public Button getImagesButton;

    @FXML
    public TextField productIDTextField;

    @FXML
    private ListView<ResultRow> resultsList;

    @FXML
    private ImageView downloadedImageView;


    public GUIController() {
        try {
            socket = new Socket("localhost", SERVER_PORT);
            outStream = new PrintWriter(socket.getOutputStream(), true);
            inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            System.out.println("Error connecting to server");
            System.out.println(e.getMessage());
        }
        System.out.println("Client Running");
    }

    private ObservableList<ResultRow> generateResultsRowList() {
        ObservableList<ResultRow> rowsToDisplay = FXCollections.observableArrayList();
        for (Product product : observableProductsList) {
            rowsToDisplay.add(new ResultRow(this, product));
        }
        return rowsToDisplay;
    }

    @FXML
    protected void onDisplayAllProductsButtonPressed() {
        try {
            outStream.println("get allProducts");

            StringBuilder productsJson = new StringBuilder();
            String nextLine;
            while ((nextLine = inStream.readLine()) != null && !nextLine.equals("END")) {
                productsJson.append(nextLine).append("\n");
            }

            observableProductsList.clear();
            JSONArray jsonArray = new JSONArray(productsJson.toString().trim());
            for (int i = 0; i < jsonArray.length(); i++) {
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

            resultsList.setItems(this.generateResultsRowList());
        } catch (Exception e) {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.exit(1);
        }
    }

    @FXML
    protected void onSearchByIDButtonPressed() {
        if (productIDTextField.getText().isEmpty()) {
            return;
        }
        try {
            int id = Integer.parseInt(productIDTextField.getText());

            outStream.println("find " + id);

            observableProductsList.clear();
            StringBuilder productJsonSB = new StringBuilder();
            String nextLine;
            while ((nextLine = inStream.readLine()) != null && !nextLine.equals("END")) {
                productJsonSB.append(nextLine).append("\n");
            }
            String productJSON = productJsonSB.toString().trim();

            if (!productJSON.equals("NOT FOUND")) {
                JSONObject object = new JSONObject(productJSON);
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
        } catch (InputMismatchException e) {
            System.out.println("Error: ID must be an int");
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public void onDeleteButtonClicked(int id) {
        ConfirmDeleteDialogue confirmDelete = new ConfirmDeleteDialogue(this, id);
        confirmDelete.showAndWait();
    }

    public void onConfirmDeleteButtonPressed(int id) {
        outStream.println("delete " + id);
        onDisplayAllProductsButtonPressed();
    }

    public void onCreateButtonPressed() {
        CreateDialogue createDialog = new CreateDialogue(this);
        createDialog.showAndWait();
    }

    public void onConfirmCreateButtonPressed(Product newProduct) {
        String productJSON = ProductJsonConverter.productToJsonString(newProduct);
        String request = "create " + productJSON.replace('\n', ' ');
        outStream.println(request);

        onDisplayAllProductsButtonPressed();
    }

    public void onEditButtonPressed(Product product) {
        EditDialogue editDialog = new EditDialogue(this, product);
        editDialog.showAndWait();
    }

    public void onConfirmEditButtonPressed(Product product) {
        String productJSON = ProductJsonConverter.productToJsonString(product);
        String request = "update " + productJSON.replace('\n', ' ');
        outStream.println(request);

        onDisplayAllProductsButtonPressed();
    }

    @FXML
    protected void onGetImagesButtonPressed() {
        try {
            outStream.println("get images");

            StringBuilder imagesJson = new StringBuilder();
            String nextLine;
            while ((nextLine = inStream.readLine()) != null && !nextLine.equals("END")) {
                imagesJson.append(nextLine).append("\n");
            }

            JSONArray jsonArray = new JSONArray(imagesJson.toString().trim());

            if (jsonArray.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Images");
                alert.setHeaderText(null);
                alert.setContentText("No images available on the server.");
                alert.showAndWait();
                return;
            }

            List<String> imageNames = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                imageNames.add(jsonArray.getString(i));
            }

            ChoiceDialog<String> dialog = new ChoiceDialog<>(imageNames.get(0), imageNames);
            dialog.setTitle("Download Image");
            dialog.setHeaderText("Select an image to download:");
            dialog.setContentText("Available Images:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(selectedImage -> {
                downloadImage(selectedImage);
            });

        } catch (Exception e) {
            System.out.println("Error while getting images list:");
            e.printStackTrace();
        }
    }

    private void downloadImage(String imageName) {
        try {
            outStream.println("get image " + imageName);

            File folder = new File("downloaded_images");
            if (!folder.exists()) {
                folder.mkdir();
            }

            InputStream inputStream = socket.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream("downloaded_images/" + imageName);

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, bytesRead);
                if (bytesRead < buffer.length) {
                    break;
                }
            }

            fileOutputStream.close();

            javafx.scene.image.Image img = new javafx.scene.image.Image(new FileInputStream("downloaded_images/" + imageName));
            downloadedImageView.setImage(img);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Download Complete");
            alert.setHeaderText(null);
            alert.setContentText("Image downloaded: downloaded_images/" + imageName);
            alert.showAndWait();

        } catch (Exception e) {
            System.out.println("Error downloading image:");
            e.printStackTrace();
        }
    }

    @FXML
    protected void onExitButtonPressed() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Exit");
            alert.setHeaderText("Are you sure you want to exit?");
            alert.setContentText("This will disconnect from the server.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (outStream != null) {
                    outStream.println("quit");
                }

                if (inStream != null) {
                    inStream.close();
                }
                if (outStream != null) {
                    outStream.close();
                }
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }

                System.exit(0);
            }

        } catch (Exception e) {
            System.out.println("Error while exiting:");
            e.printStackTrace();
            System.exit(1);
        }
    }

}

