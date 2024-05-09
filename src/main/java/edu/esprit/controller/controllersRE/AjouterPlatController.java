package edu.esprit.controller.controllersRE;


import edu.esprit.entites.Plat;
import edu.esprit.servies.PlatService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterPlatController {
    private final PlatService ps = new PlatService();
    @FXML
    private TextField nom;
    @FXML
    private TextField description;
    @FXML
    private int restaurantId;
    @FXML
    private TextField numdetel;

    @FXML
    private TextField nmbetoiles;
    @FXML
    private String imagePath;
    @FXML
    private ImageView imagef;

    @FXML
    private TextField heure_ouverture;
    @FXML
    private TextField heure_fermeture;
    @FXML
    private Button choisir;






    @FXML
    void ajouterPlat(ActionEvent event) {
        // Vérifier que les champs obligatoires ne sont pas vides
        if (nom.getText().isEmpty() || description.getText().isEmpty()) {
            showAlert("Error", "Veuillez remplir tous les champs obligatoires.");
            return;
        }

        try {
            // Créez le plat en lui passant l'ID du restaurant
            Plat plat = new Plat(nom.getText(), description.getText(), imagePath, restaurantId);

            // Ajoutez le plat en utilisant le service PlatService
            ps.ajouter(plat);
            System.out.println("ID du restaurant récupéré : " + restaurantId);
            showSuccessAlert("Succès", "Le plat a été ajouté avec succès.");
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void naviguezVersAffichage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/FXMLRes/plataffb.fxml"));
            description.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void insererimage(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Fichiers image", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imagePath = selectedFile.toURI().toString();
            Image image = new Image(imagePath);
            imagef.setImage(image);}
    }

    public void initData(int restaurantId) {
        this.restaurantId = restaurantId;
    }
}
