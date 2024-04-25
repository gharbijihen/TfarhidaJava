package edu.esprit.tests;


import edu.esprit.controller.RouterController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class MainFX extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        RouterController.setPrimaryStage(stage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/indexLogement.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Tfarida");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}




