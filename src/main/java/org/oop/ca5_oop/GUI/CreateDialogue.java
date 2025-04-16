package org.oop.ca5_oop.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.oop.ca5_oop.DTO.Product;

public class CreateDialogue extends Stage {

    CreateDialogue(GUIController rc) {
        this.setTitle("Create Product");

        Label nameLabel = new Label("Product Name:");
        Label priceLabel = new Label("Product Price:");
        Label descriptionLabel = new Label("Product Description:");
        Label skuLabel = new Label("Product SKU:");
        Label qtyLabel = new Label("Quantity in Stock:");

        TextField nameTF = new TextField();
        TextField priceTF = new TextField();
        TextField descriptionTF = new TextField();
        TextField skuTF = new TextField();
        TextField qtyTF = new TextField();

        Button confirmCreateButton = new Button("Create Product");

        confirmCreateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product newProduct = new Product(0,
                        nameTF.getText(),
                        descriptionTF.getText(),
                        Float.valueOf(priceTF.getText()),
                        Integer.parseInt(qtyTF.getText()),
                        skuTF.getText()
                );
                rc.onConfirmCreateButtonPressed(newProduct);
                System.out.println("Should be here");
                close();

            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setCancelButton(true);
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                close();
            }
        });

        VBox dialogueContent = new VBox();
        dialogueContent.getChildren().addAll(nameLabel, nameTF,
                priceLabel, priceTF,
                descriptionLabel, descriptionTF,
                skuLabel, skuTF,
                qtyLabel, qtyTF,
                cancelButton,
                confirmCreateButton);

        Parent sceneParent = (Parent) dialogueContent;
        Scene dialogueScene = new Scene(sceneParent);
        this.setScene(dialogueScene);
    }
}
