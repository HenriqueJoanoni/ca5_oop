package org.oop.ca5_oop.GUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.oop.ca5_oop.DTO.Product;

public class ResultRow extends HBox {

    ResultRow(ResultsController rc, Product product){
        if (product!=null){
            Button deleteButton = new Button("Delete");

            //code referenced from:
            //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/ButtonBase.html#setOnAction-javafx.event.EventHandler-
            //https://docs.oracle.com/javase/8/docs/api/java/awt/event/ActionEvent.html
            deleteButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event){
                    //delete through ref to ResultsController class
                    rc.onDeleteButtonClicked(product.getProductID());
                }
            });


            this.getChildren().addAll(new Label("ID: " + product.getProductID() + ", Product: " + product.getProductName() + ", Price: €" + product.getPrice()),
                    deleteButton,
                    new Button("Edit")
            );
        }

        //deal with null row
        else {
            this.getChildren().addAll(new Label("Product Not Found."));
        }



    }
}
