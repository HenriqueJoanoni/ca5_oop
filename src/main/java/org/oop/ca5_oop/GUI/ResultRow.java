package org.oop.ca5_oop.GUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import org.oop.ca5_oop.DTO.Product;

public class ResultRow extends HBox {



    ResultRow(ResultsController rc, Product product){
        //code referenced from:
        //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/ButtonBase.html#setOnAction-javafx.event.EventHandler-
        //https://docs.oracle.com/javase/8/docs/api/java/awt/event/ActionEvent.html


        if (product!=null){
            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event){
                    //delete through ref to ResultsController class
                    rc.onDeleteButtonClicked(product.getProductID());
                }
            });

            Button editButton = new Button("Edit");
            editButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event){
                    //delete through ref to ResultsController class
                    rc.onEditButtonPressed(product);
                }
            });

            //this.getChildren().addAll(new TableColumn(""));



            this.getChildren().addAll(new Label("ID: " + product.getProductID() + ", Product: " + product.getProductName() +
                            ", Price: €" + product.getPrice() + ", Qty In Stock: " + product.getQtyInStock() + "\t"),
                    deleteButton,
                    editButton
            );




        }

        //deal with null row
        else {
            this.getChildren().addAll(new Label("Product Not Found."));
        }



    }
}
