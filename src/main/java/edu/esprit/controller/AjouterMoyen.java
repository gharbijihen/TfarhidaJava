package edu.esprit.controller;

import edu.esprit.entites.Moyen_transport;
import edu.esprit.servies.Moyen_transportCrud;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
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
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javafx.stage.Stage;

public class AjouterMoyen {

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






    @FXML
    void ajouterMoyenAction(ActionEvent event) {


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


            type.clear();
            capacite.clear();
            lieu.clear();
            etat.setSelected(false);

            imageView.setImage(null);
            selectedImageFile = null;


        }


    }

    private boolean isInputValid() {
        boolean isValid = true;

        // Validate and display error messages
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

    @FXML
    void initialize()  {

        assert type != null : "fx:id=\"type\" was not injected: check your FXML file 'ajoutermoyen.fxml'.";
        assert capacite != null : "fx:id=\"capacite\" was not injected: check your FXML file 'ajoutermoyen.fxml'.";
        assert lieu != null : "fx:id=\"lieu\" was not injected: check your FXML file 'ajoutermoyen.fxml'.";
        assert etat != null : "fx:id=\"etat\" was not injected: check your FXML file 'ajoutermoyen.fxml'.";
        assert valide != null : "fx:id=\"valide\" was not injected: check your FXML file 'ajouteramoyen.fxml'.";
        assert ButtonAjouterMoy != null : "fx:id=\"save\" was not injected: check your FXML file 'ajoutermoyen.fxml'.";

    }




}
