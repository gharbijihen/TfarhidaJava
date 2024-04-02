package edu.esprit.controller;

import edu.esprit.entites.Logement;
import edu.esprit.servies.LogementCrud;
import edu.esprit.tests.MyListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class afficherLogement implements Initializable {
    private List<Logement> logements;
    private MyListener myListener;
    private GridPane grid;


    private LogementCrud serviceLogemen = new LogementCrud();

    @FXML
    private HBox vboxdash;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // eventView.setOnMouseClicked(event -> loadFXML("/gererevenement.fxml"));
    }
    @FXML
    void showLogement(MouseEvent event) {
        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/indexLogement.fxml"));
            Node eventFXML = loader.load();

            // Clear existing content from FieldHolder
            vboxdash.getChildren().clear();

            // Add the loaded userFXML to FieldHolder
            vboxdash.getChildren().add(eventFXML);
        } catch (IOException e) {
            // Handle exception (e.g., file not found or invalid FXML)
            e.printStackTrace();
        }
    }


}
