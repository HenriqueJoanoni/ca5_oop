package org.oop.ca5_oop.GUI;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import org.oop.ca5_oop.DTO.Product;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ResultRow extends HBox {

    public ResultRow(GUIController rc, Product product) {

        if (product != null) {
            ImageView productImageView = new ImageView();
            productImageView.setFitHeight(50);
            productImageView.setFitWidth(50);
            productImageView.setPreserveRatio(true);
            productImageView.setSmooth(true);
            productImageView.setCache(true);

            try {
                String imageName = product.getImageName();
                if (imageName != null && !imageName.isEmpty()) {
                    File imageFile = new File("downloaded_images/" + imageName);
                    if (imageFile.exists()) {
                        Image productImage = new Image(new FileInputStream(imageFile));
                        productImageView.setImage(productImage);
                    } else {
                        loadDefaultImage(productImageView);
                    }
                } else {
                    loadDefaultImage(productImageView);
                }
            } catch (Exception e) {
                System.out.println("Could not load image for product: " + product.getProductName());
                e.printStackTrace();
                loadDefaultImage(productImageView);
            }

            Label productInfoLabel = new Label(
                    "ID: " + product.getProductID() +
                            ", Product: " + product.getProductName() +
                            ", Price: €" + String.format("%.2f", product.getPrice()) +
                            ", Qty In Stock: " + product.getQtyInStock()
            );
            productInfoLabel.setStyle("-fx-font-size: 14px;");

            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction((ActionEvent event) -> {
                rc.onDeleteButtonClicked(product.getProductID());
            });
            deleteButton.setStyle("-fx-background-color: #b22222; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand;");

            Button editButton = new Button("Edit");
            editButton.setOnAction((ActionEvent event) -> {
                rc.onEditButtonPressed(product);
            });
            editButton.setStyle("-fx-background-color: gray; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand;");

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);


            this.setSpacing(20);
            this.getChildren().addAll(productImageView, productInfoLabel, spacer, deleteButton, editButton);
            this.setStyle("-fx-padding: 10px; -fx-alignment: CENTER_LEFT;");
        } else {
            Label notFoundLabel = new Label("Product Not Found.");
            notFoundLabel.setStyle("-fx-text-fill: red;");
            this.getChildren().add(notFoundLabel);
        }
    }

    private void loadDefaultImage(ImageView imageView) {
        try {
            File defaultImageFile = new File("downloaded_images/no_photo.png");
            if (defaultImageFile.exists()) {
                Image defaultImage = new Image(new FileInputStream(defaultImageFile));
                imageView.setImage(defaultImage);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
