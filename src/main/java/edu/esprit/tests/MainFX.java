package edu.esprit.tests;


import edu.esprit.controller.RouterController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class MainFX extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        RouterController.setPrimaryStage(stage); // Assurez-vous d'initialiser primaryStage dans RouterController

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/front.fxml"));
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