package org.oop.ca5_oop.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.oop.ca5_oop.DTO.Product;

import java.io.File;

public class CreateDialogue extends Stage {

    private String selectedImageFileName = null;

    CreateDialogue(GUIController rc){
        this.setTitle("Create New Product");

        VBox dialogueContent = new VBox(10);
        dialogueContent.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 20; -fx-border-radius: 10; -fx-background-radius: 10;");

        Label nameLabel = new Label("Product Name:");
        TextField nameTF = new TextField();

        Label priceLabel = new Label("Product Price:");
        TextField priceTF = new TextField();

        Label descriptionLabel = new Label("Product Description:");
        TextField descriptionTF = new TextField();

        Label skuLabel = new Label("Product SKU:");
        TextField skuTF = new TextField();

        Label qtyLabel = new Label("Quantity in Stock:");
        TextField qtyTF = new TextField();

        Label imageLabel = new Label("Product Image:");
        Button uploadImageButton = new Button("Choose Image");
        ImageView selectedImagePreview = new ImageView();
        selectedImagePreview.setFitWidth(100);
        selectedImagePreview.setFitHeight(100);
        selectedImagePreview.setPreserveRatio(true);

        uploadImageButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Product Image");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png")
            );
            File selectedFile = fileChooser.showOpenDialog(this);
            if (selectedFile != null) {
                selectedImageFileName = selectedFile.getName();
                selectedImagePreview.setImage(new Image(selectedFile.toURI().toString()));
            }
        });

        HBox imageUploadBox = new HBox(10, uploadImageButton, selectedImagePreview);

        Button confirmCreateButton = new Button("Create Product");
        Button cancelButton = new Button("Cancel");

        confirmCreateButton.setOnAction(event -> {
            try {
                String finalImageName = (selectedImageFileName != null && !selectedImageFileName.trim().isEmpty())
                        ? selectedImageFileName
                        : "no_photo.png";

                Product newProduct = new Product(
                        nameTF.getText(),
                        descriptionTF.getText(),
                        Float.parseFloat(priceTF.getText()),
                        Integer.parseInt(qtyTF.getText()),
                        skuTF.getText(),
                        finalImageName
                );

                rc.onConfirmCreateButtonPressed(newProduct);
                close();
            } catch (Exception e) {
                System.out.println("Error creating product: " + e.getMessage());
            }
        });


        cancelButton.setOnAction(event -> close());

        HBox buttons = new HBox(15, cancelButton, confirmCreateButton);
        buttons.setStyle("-fx-alignment: center; -fx-padding: 10;");

        dialogueContent.getChildren().addAll(
                nameLabel, nameTF,
                priceLabel, priceTF,
                descriptionLabel, descriptionTF,
                skuLabel, skuTF,
                qtyLabel, qtyTF,
                imageLabel, imageUploadBox,
                buttons
        );

        Parent sceneParent = dialogueContent;
        Scene dialogueScene = new Scene(sceneParent, 400, 600);
        this.setScene(dialogueScene);
    }
}
