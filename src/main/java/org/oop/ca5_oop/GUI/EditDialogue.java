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


    EditDialogue(GUIController rc, Product product){
        this.setTitle("Edit Product");


        Label nameLabel = new Label("Product Name:");
        Label priceLabel = new Label("Product Price:");
        Label descriptionLabel = new Label("Product Description:");
        Label skuLabel = new Label("Product SKU:");
        Label qtyLabel = new Label("Quantity in Stock:");


        TextField nameTF = new TextField();
         nameTF.setText(product.getProductName());

        TextField priceTF = new TextField();
        priceTF.setText(String.valueOf(product.getPrice()));

        TextField descriptionTF = new TextField();
        descriptionTF.setText(product.getDescription());

        TextField skuTF = new TextField();
        skuTF.setText(product.getProduct_sku());

        TextField qtyTF = new TextField();
        qtyTF.setText(String.valueOf(product.getQtyInStock()));

        Button confirmChangesButton = new Button("Confirm Changes");
        Button cancelChangesButton = new Button("Cancel");

        confirmChangesButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                Product newProduct = new Product(
                        product.getProductID(),
                        nameTF.getText(),
                        descriptionTF.getText(),
                        Float.valueOf(priceTF.getText()),
                        Integer.valueOf(qtyTF.getText()),
                        skuTF.getText()
                );

                rc.onConfirmEditButtonPressed(newProduct);
                close();
            }
        });


        cancelChangesButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                close();
            }
        });


        VBox dialogueContent = new VBox();
        dialogueContent.getChildren().addAll(nameLabel, nameTF,
                priceLabel, priceTF,
                descriptionLabel, descriptionTF,
                skuLabel, skuTF,
                qtyLabel, qtyTF,
                cancelChangesButton,
                confirmChangesButton);

        Parent sceneParent = (Parent) dialogueContent;
        Scene dialogueScene = new Scene(sceneParent);
        this.setScene(dialogueScene);

    }
}
