package edu.esprit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminDashbord {
    @FXML
    private Label adminNameLabel;

    public void initialize() {
        // Set the admin name in the label
        //    GuiLoginController guilogin = new GuiLoginController();
        //  String name="Bienvenue "+guilogin.user.getPrenom()+"!";
        // adminNameLabel.setText(name);
    }
    public void goToLogn(MouseEvent mouseEvent) {
    }

    public void goToNavigate(ActionEvent actionEvent) {
        RouterController router=new RouterController();
        router.navigate("Back.fxml");
    }


    public void goToLogement(MouseEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogementAffB.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page
            Scene scene = new Scene(root);

            // Obtenir la fenêtre actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène sur la fenêtre et l'afficher
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void goToEquipement(MouseEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEquipementB.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page
            Scene scene = new Scene(root);

            // Obtenir la fenêtre actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène sur la fenêtre et l'afficher
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToCommands(MouseEvent mouseEvent) {
    }

    public void goToReclamations(MouseEvent mouseEvent) {
    }

    public void goToEvent(MouseEvent mouseEvent) {
    }

    public void goToLivraisons(MouseEvent mouseEvent) {
    }

}
