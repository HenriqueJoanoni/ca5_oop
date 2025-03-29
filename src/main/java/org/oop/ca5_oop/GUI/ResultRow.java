package org.oop.ca5_oop.GUI;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.oop.ca5_oop.DTO.Product;

public class ResultRow extends HBox {
    private int id;
    private String title;
    private float price;

    ResultRow(Product product){
        this.id = product.getProductID();
        this.title = product.getProductName();
        this.price = product.getPrice();
        this.getChildren().addAll(new Label("ID: " + this.id + ", Product: " + this.title + ", Price: €" + this.price),
                                    new Button("Edit"),
                                    new Button("Delete"));

    }
}
