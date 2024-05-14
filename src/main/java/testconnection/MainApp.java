package testconnection;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Controllers.RouterController;

public class MainApp extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    // Load the FXML file
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loadingscreen.fxml"));

    Parent root = loader.load();

    // Set the primary stage
    edu.esprit.controller.RouterController.setPrimaryStage(primaryStage);
    RouterController.setPrimaryStage(primaryStage);


    // Set the scene
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
