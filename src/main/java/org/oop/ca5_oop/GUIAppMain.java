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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/user-view.fxml"));
        Parent root = loader.load();

        //load scene
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("CA5");
        stage.show();
    }
}
