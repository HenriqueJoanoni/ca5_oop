package org.oop.ca5_oop.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import org.oop.ca5_oop.DTO.Product;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ResultRow extends HBox {

    ResultRow(GUIController rc, Product product) {

        if (product != null) {
            ImageView productImageView = new ImageView();
            productImageView.setFitHeight(50);
            productImageView.setFitWidth(50);
            productImageView.setPreserveRatio(true);
            productImageView.setSmooth(true);
            productImageView.setCache(true);


            try {
                String filename = product.getProductName().replaceAll(" ", "_").toLowerCase() + ".jpg";
                File imageFile = new File("downloaded_images/" + filename);

                if (imageFile.exists()) {
                    Image productImage = new Image(new FileInputStream(imageFile));
                    productImageView.setImage(productImage);
                } else {
                    File defaultImageFile = new File("downloaded_images/no_photo.png");
                    if (defaultImageFile.exists()) {
                        Image defaultImage = new Image(new FileInputStream(defaultImageFile));
                        productImageView.setImage(defaultImage);
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Could not load image for product: " + product.getProductName());
                e.printStackTrace();
            }

            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    rc.onDeleteButtonClicked(product.getProductID());
                }
            });

            Button editButton = new Button("Edit");
            editButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    rc.onEditButtonPressed(product);
                }
            });

            Label productInfoLabel = new Label("ID: " + product.getProductID() +
                    ", Product: " + product.getProductName() +
                    ", Price: €" + product.getPrice() +
                    ", Qty In Stock: " + product.getQtyInStock() + "\t");

            this.setSpacing(10);
            this.getChildren().addAll(productImageView, productInfoLabel, deleteButton, editButton);

        } else {
            this.getChildren().add(new Label("Product Not Found."));
        }
    }
}
