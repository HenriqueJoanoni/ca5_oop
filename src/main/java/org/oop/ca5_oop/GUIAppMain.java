package org.oop.ca5_oop;
import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIAppMain extends Application{
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        //to make this work, i had to use code referenced from these docs:
        //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/ButtonBase.html#setOnAction-javafx.event.EventHandler-
        //https://docs.oracle.com/javase/8/docs/api/java/awt/event/ActionEvent.html
        //https://examples.javacodegeeks.com/java-development/desktop-java/javafx/dialog-javafx/javafx-dialog-example/
        //https://www.geeksforgeeks.org/javafx-textinputdialog/
        //https://docs.oracle.com/javase/8//javafx/api/javafx/scene/control/Dialog.html#:~:text=A%20Dialog%20in%20JavaFX%20wraps%20a%20DialogPane%20and,alternative%20options%20%28such%20as%20%27lightweight%27%20or%20%27internal%27%20dialogs%29.
        //https://codingtechroom.com/question/javafx-close-dialog-programmatically


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/user-view.fxml"));
        Parent root = loader.load();



        //load scene
        Scene scene = new Scene(root, 1000, 800);
        stage.setScene(scene);
        stage.setTitle("CA5");
        stage.show();
    }
}
