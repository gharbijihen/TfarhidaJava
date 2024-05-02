package edu.esprit.controller;
import edu.esprit.entites.Moyen_transport;
import edu.esprit.servies.Moyen_transportCrud;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javafx.scene.image.ImageView;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javafx.stage.FileChooser;
import javafx.util.Duration;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javafx.stage.Stage;
import javafx.util.Pair;

public class AjoutermoyenF {
    @FXML
    private Button ButtonAjouterMoy;

    @FXML
    private TextField capacite;

    @FXML
    private Text errorCapacite;

    @FXML
    private Text errorEtat;

    @FXML
    private Text errorLieu;

    @FXML
    private Text errorType;

    @FXML
    private Text errorValide;

    @FXML
    private CheckBox etat;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField lieu;

    @FXML
    private Button selectImage;

    @FXML
    private TextField type;

    @FXML
    private CheckBox valide;
    @FXML
    private Button browseImage;
    @FXML
    private TextField selectedImagePath;

    private String imagePathInDatabase;

    private File selectedImageFile;

    @FXML
    private TextField imagePath;
    private ComboBox<Integer> trajetsComboBox;
    private List<Pair<Integer, String>> trajets; // Déclarez cette liste en tant que variable de classe


    @FXML
    void ajoutermoyenAction(ActionEvent event) {
        if (type.getText().isEmpty()) {
            type.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
        } else {
            type.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }
        if (lieu.getText().isEmpty()) {
            lieu.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
        } else {
            lieu.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }
        if (capacite.getText().isEmpty()) {
            capacite.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
        } else {
            capacite.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }

        if (isInputValid()) {
            if (isInputValid()) {
                String image=imagePathInDatabase;
                String typeM = type.getText();
                int capaciteM = Integer.parseInt(capacite.getText());
                String lieuM = lieu.getText();
                boolean etatM = etat.isSelected();

                String imageL = (selectedImageFile != null) ? selectedImageFile.getPath():"";

                Moyen_transport moyen_transport = new Moyen_transport();
                Moyen_transportCrud service = new Moyen_transportCrud();
                service.ajouter(new Moyen_transport(typeM, capaciteM, lieuM, etatM, false, image));
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                SmsController.Sms();

                type.clear();
                capacite.clear();
                lieu.clear();
                etat.setSelected(false);

                imageView.setImage(null);
                selectedImageFile = null; }
        }
    }
    @FXML
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
//    @FXML
//    void naviguezVersAffichage(ActionEvent event) {
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("/affiche.fxml"));
//            type.getScene().setRoot(root);
//        } catch (IOException e) {
//            System.err.println(e.getMessage());
//        }
//    }


    @FXML
    void browseImageAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        // Filtrer les types de fichiers si nécessaire
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            // Stocker le chemin de l'image sélectionnée dans la variable de classe
            imagePathInDatabase = selectedFile.getAbsolutePath();
            // Charger l'image sélectionnée dans l'ImageView
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);

        }
    }
    private boolean isInputValid() {
        boolean isValid = true;
        if (type.getText().isEmpty() || !type.getText().matches("^[a-zA-Z]+$")) {
            errorType.setText("Nom is required and should not contain numbers");
            isValid = false;
        } else {
            errorType.setText("");
        }

        if (lieu.getText().isEmpty() || !lieu.getText().matches("^[a-zA-Z]+$")) {
            errorLieu.setText("Adresse is required and should not contain numbers ");
            isValid = false;
        } else {
            errorLieu.setText("");
        }

        if (capacite.getText().isEmpty()) {
            errorCapacite.setText("Note is required");
            isValid = false;
        }  else {
            errorCapacite.setText("");
        }

        return isValid;
    }




    @FXML
    void initialize() {
        assert type != null : "fx:id=\"type\" was not injected: check your FXML file 'ajoutermoyen.fxml'.";
        assert capacite != null : "fx:id=\"capacite\" was not injected: check your FXML file 'ajoutermoyen.fxml'.";
        assert lieu != null : "fx:id=\"lieu\" was not injected: check your FXML file 'ajoutermoyen.fxml'.";
        assert etat != null : "fx:id=\"etat\" was not injected: check your FXML file 'ajoutermoyen.fxml'.";
        assert valide != null : "fx:id=\"valide\" was not injected: check your FXML file 'ajouteramoyen.fxml'.";
        assert ButtonAjouterMoy != null : "fx:id=\"save\" was not injected: check your FXML file 'activiteAjout.fxml'.";

    }
}