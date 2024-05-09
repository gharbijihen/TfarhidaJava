package edu.esprit.controller.controllersRE;


import edu.esprit.entites.Restaurant;
import edu.esprit.servies.PlatService;
import edu.esprit.servies.RestaurantService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class AjouterRestaurantController {
    private final RestaurantService ps = new RestaurantService();
    @FXML
    private TextField nom;
    @FXML
    private TextField adresse;
    @FXML
    private TextField numdetel;
    @FXML
    private TextField nmbetoiles;
    @FXML
    private String imagePath;
    private Connection connexion;
    private ImageView imagef;
     @FXML
     private ImageView myImageView;


    @FXML
    private TextField heure_ouverture;
    @FXML
    private TextField heure_fermeture;
    @FXML
    private Button choisir;

    @FXML
    private VBox platsContainer;
    private final PlatService pl = new PlatService();







    @FXML
    void ajouterRestaurant(ActionEvent event) throws SQLException {

        // Vérifier que les champs obligatoires ne sont pas vides
        if (nom.getText().isEmpty() || adresse.getText().isEmpty() || heure_ouverture.getText().isEmpty() || heure_fermeture.getText().isEmpty()) {
            showAlert("Error", "Veuillez remplir tous les champs obligatoires.");
            return;
        }


        // Vérifier que le numéro de téléphone a exactement 8 chiffres
        String numTel = numdetel.getText();
        if (!numTel.matches("\\d{8}")) {
            showAlert("Error", "Le numéro de téléphone doit être composé exactement de 8 chiffres.");
            return;
        }

        // Vérifier que le nombre d'étoiles est compris entre 1 et 5
        int nbEtoiles;
        try {
            nbEtoiles = Integer.parseInt(nmbetoiles.getText());
            if (nbEtoiles < 1 || nbEtoiles > 5) {
                showAlert("Error", "Le nombre d'étoiles doit être compris entre 1 et 5.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Le nombre d'étoiles doit être un entier.");
            return;
        }

        // Vérifier le format de l'heure d'ouverture et de fermeture
        if (!heure_ouverture.getText().matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$") || !heure_fermeture.getText().matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
            showAlert("Error", "Le format de l'heure doit être hh:mm (ex: 09:30).");
            return;
        }

        // Si toutes les vérifications sont réussies, ajouter le restaurant
        try {
            ps.ajouter(new Restaurant(nom.getText(), adresse.getText(), Integer.parseInt(numTel), nbEtoiles, imagePath, heure_ouverture.getText(), heure_fermeture.getText()));

            showSuccessAlert("Succès", "Le restaurant a été ajouté avec succès.");
        } catch (SQLException e) {
            showAlert("Error", e.getMessage());
            return;
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
            Parent root = FXMLLoader.load(getClass().getResource("/FXMLRes/restaurantaffb.fxml"));
            numdetel.getScene().setRoot(root);
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
            myImageView.setImage(image);}
    }


}
