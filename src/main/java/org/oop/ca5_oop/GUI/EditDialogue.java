package org.oop.ca5_oop.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.oop.ca5_oop.DTO.Product;

public class EditDialogue extends Stage {

    EditDialogue(GUIController rc, Product product) {
        this.setTitle("Edit Product");

        Label nameLabel = new Label("Product Name:");
        Label priceLabel = new Label("Product Price:");
        Label descriptionLabel = new Label("Product Description:");
        Label skuLabel = new Label("Product SKU:");
        Label qtyLabel = new Label("Quantity in Stock:");

        TextField nameTF = new TextField(product.getProductName());
        TextField priceTF = new TextField(String.valueOf(product.getPrice()));
        TextField descriptionTF = new TextField(product.getDescription());
        TextField skuTF = new TextField(product.getProduct_sku());
        TextField qtyTF = new TextField(String.valueOf(product.getQtyInStock()));

        Button confirmChangesButton = new Button("Confirm Changes");
        Button cancelChangesButton = new Button("Cancel");

        confirmChangesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Product newProduct = new Product(
                        product.getProductID(),
                        nameTF.getText(),
                        descriptionTF.getText(),
                        Float.parseFloat(priceTF.getText()),
                        Integer.parseInt(qtyTF.getText()),
                        skuTF.getText(),
                        product.getImageName()
                );
                rc.onConfirmEditButtonPressed(newProduct);
                close();
            }
        });

        cancelChangesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                close();
            }
        });

        VBox dialogueContent = new VBox(10); // 10px spacing
        dialogueContent.getChildren().addAll(
                nameLabel, nameTF,
                priceLabel, priceTF,
                descriptionLabel, descriptionTF,
                skuLabel, skuTF,
                qtyLabel, qtyTF,
                confirmChangesButton, cancelChangesButton
        );

        Parent sceneParent = dialogueContent;
        Scene dialogueScene = new Scene(sceneParent, 400, 500);
        this.setScene(dialogueScene);
    }
}
