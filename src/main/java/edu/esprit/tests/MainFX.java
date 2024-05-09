package edu.esprit.tests;


import edu.esprit.controller.RouterController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class MainFX extends Application {
    @Override
    public void start(Stage stage) {
        Parent root = null;
        try {
            RouterController.setPrimaryStage(stage);

            root = FXMLLoader.load(getClass().getResource("/login.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Tfarhida");
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) {
        launch();
    }


}