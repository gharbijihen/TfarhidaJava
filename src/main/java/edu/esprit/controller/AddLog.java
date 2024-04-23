package edu.esprit.controller;

import edu.esprit.entites.Equipement;
import edu.esprit.servies.LogementCrud;
import edu.esprit.tools.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import edu.esprit.entites.Logement;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class AddLog {

    @FXML
    private TextField equipementId;
    @FXML
    private Button ButtonAjouterLog;
    @FXML
    private Text errorLocalisation;
    @FXML
    private Text errorNom;
    @FXML
    private Text errorNote;
    @FXML
    private Text errorNum;
    @FXML
    private Text errorPrix;
    @FXML
    private ImageView image;
    @FXML
    private TextField localisation;
    @FXML
    private TextField nom;
    @FXML
    private TextField note;
    @FXML
    private TextField num;
    @FXML
    private TextField prix;
    @FXML
    private Button selectImage;
    @FXML
    private ImageView  imageView;
    private File selectedImageFile;
    @FXML
    private TextField typelLog;
    @FXML
    private ComboBox<String>type_log;
    ObservableList<String>typeLog = FXCollections.observableArrayList(Data.typeLogement);

    private String imagePathInDatabase;


    @FXML
    void ajouterLogementAction(ActionEvent event) {
        afficherLogementB afficherB =new afficherLogementB() ;
        if (isInputValid()) {

            String nomL = nom.getText();
            String localisationL = localisation.getText();
            int numL = Integer.parseInt(num.getText());
            int noteL = Integer.parseInt(note.getText());
            int prixL = Integer.parseInt(prix.getText());
            int equipement_id = Integer.parseInt(equipementId.getText());
            String typeLog = type_log.getValue();
            String imageL = imagePathInDatabase;

            LogementCrud service = new LogementCrud();
            service.ajouter(new Logement(nomL, localisationL, numL, prixL, imageL, "en cours", typeLog, noteL, equipement_id));

            showAlert("Logement ajouté", "Votre logement a été ajouté avec succès.");

            nom.clear();
            localisation.clear();
            num.clear();
            note.clear();
            prix.clear();
            typelLog.clear();
            equipementId.clear();

            imageView.setImage(null);
            selectedImageFile = null;


        }
    }

    private void goBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/indexLogement.fxml"));
            nom.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private boolean isInputValid() {
        boolean isValid = true;

        // Validate and display error messages
        if (nom.getText().isEmpty() || !nom.getText().matches("^[a-zA-Z]+$")) {
            errorNom.setText("Nom is required and should not contain numbers");
            isValid = false;
        } else {
            errorNom.setText("");
        }

        if (localisation.getText().isEmpty() || !localisation.getText().matches("^[a-zA-Z]+$")) {
            errorLocalisation.setText("Adresse is required and should not contain numbers ");
            isValid = false;
        } else {
            errorLocalisation.setText("");
        }

        if (note.getText().isEmpty()) {
            errorNote.setText("Note is required");
            isValid = false;
        } else {
            errorNote.setText("");
        }

        if (num.getText().isEmpty() || !num.getText().matches("^[0-9]+$")) {
            errorNum.setText("Phone is required and should be 8 digits long");
            isValid = false;
        } else {
            errorNum.setText("");
        }

        if (prix.getText().isEmpty() || !prix.getText().matches("^[0-9]+$")) {
            errorPrix.setText("Price is required and should be 4 digits long");
            isValid = false;
        } else {
            errorPrix.setText("");
        }

        if (equipementId.getText().isEmpty() || !equipementId.getText().matches("^[0-9]+$")) {
            errorPrix.setText("Equipement ID is required and should be 4 digits long");
            isValid = false;
        } else {
            errorPrix.setText("");
        }

        return isValid;
    }

    @FXML
    void selectImageAction(ActionEvent event) {
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

    @FXML
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void naviguezVersAffichage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/indexLogement.fxml"));
            nom.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }



    @FXML
    void initialize() {
        assert localisation != null : "fx:id=\"localisation\" was not injected: check your FXML file 'ajouteragence.fxml'.";
        assert prix != null : "fx:id=\"prix\" was not injected: check your FXML file 'ajouteragence.fxml'.";
        assert equipementId != null : "fx:id=\"equipementId\" was not injected: check your FXML file 'ajouteragence.fxml'.";
        assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'ajouteragence.fxml'.";
        assert num != null : "fx:id=\"phone\" was not injected: check your FXML file 'ajouteragence.fxml'.";
        assert ButtonAjouterLog != null : "fx:id=\"save\" was not injected: check your FXML file 'ajouteragence.fxml'.";
        type_log.setItems(typeLog);
    }
}
