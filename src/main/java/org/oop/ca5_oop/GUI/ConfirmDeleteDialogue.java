package org.oop.ca5_oop.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConfirmDeleteDialogue extends Stage {

    ConfirmDeleteDialogue(GUIController rc, int id){
        Label prompt = new Label("Are you sure you want to delete? This cannot be undone.");
        Button confirmDelete = new Button("Delete");
        Button cancelDelete = new Button("Cancel");

        confirmDelete.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                rc.onConfirmDeleteButtonPressed(id);
                close();
            }
        });

        cancelDelete.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                close();
            }
        });


        VBox content = new VBox();
        content.getChildren().addAll(prompt, confirmDelete, cancelDelete);

        Parent sceneParent = (Parent) content;
        Scene dialogueScene = new Scene(sceneParent);
        this.setScene(dialogueScene);

    }
}
