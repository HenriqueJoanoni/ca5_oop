package org.oop.ca5_oop.GUI;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConfirmDeleteDialogue extends Stage {

    ConfirmDeleteDialogue(GUIController rc, int id) {
        Label prompt = new Label("Are you sure you want to delete?\nThis cannot be undone.");
        prompt.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-padding: 10;");

        Button confirmDelete = new Button("🔥 Delete");
        confirmDelete.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px;");

        Button cancelDelete = new Button("Cancel");
        cancelDelete.setStyle("-fx-background-color: grey; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px;");

        confirmDelete.setOnAction((ActionEvent event) -> {
            rc.onConfirmDeleteButtonPressed(id);
            close();
        });

        cancelDelete.setOnAction((ActionEvent event) -> {
            close();
        });

        HBox buttonBox = new HBox(15, cancelDelete, confirmDelete);
        buttonBox.setAlignment(Pos.CENTER);

        VBox content = new VBox(20, prompt, buttonBox);
        content.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 20; -fx-border-color: #ff5722; -fx-border-width: 2; -fx-background-radius: 10;");
        content.setAlignment(Pos.CENTER);

        Parent sceneParent = content;
        Scene dialogueScene = new Scene(sceneParent, 350, 150);
        this.setScene(dialogueScene);
        this.setTitle("Confirm Delete");
    }
}
