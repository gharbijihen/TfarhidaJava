package edu.esprit.controller;
import edu.esprit.entites.Activite;
import edu.esprit.entites.Categorie;
import edu.esprit.servies.ActiviteCrud;
import edu.esprit.tools.MyConnection;
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
import javafx.scene.control.ComboBox;

import java.util.*;

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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.Stage;
import javafx.util.Pair;

public class ajouteractiviteF {
    @FXML
    private TextField nomm;
    @FXML
    private TextField prixx;
    @FXML
    private TextField categoriee;


    @FXML
    private TextField localisationn;
    @FXML
    private TextField nombreP;
    @FXML
    private TextField etatt;
    @FXML
    private TextField descriptionActt;

    @FXML
    private Button save;

    @FXML
    private Text errorPrix;
    @FXML
    private Text errorName;


    @FXML
    private Text errorLocalisation;
    @FXML
    private Text errorCategorie;


    @FXML
    private Text errorNbp;

    @FXML
    private Text errorEtat;
    @FXML
    private Text errorDesc;

    @FXML
    private Button selectImage;
    @FXML
    private ImageView imageView;

    private File selectedImageFile;

    @FXML
    private TextField imagePath;


    @FXML
    private Button browseImage;
    @FXML
    private TextField selectedImagePath;

    private String imagePathInDatabase;
    @FXML

    // private ComboBox<Integer> categorieComboBox;
    private ComboBox<String> categorieComboBox;




    private List<Pair<Integer, String>> categories; // Déclarez cette liste en tant que variable de classe


   /* void ajouteractiviteAction(ActionEvent event) {
        if (isInputValid()) {
            String image = imagePathInDatabase;
            //  int categorie_id = Integer.parseInt(categoriee.getText());
            // Récupérer le nom de la catégorie sélectionnée
            //int categorie_id = categorieComboBox.getValue();
            // System.out.println("hello"+categorie_id);
            // int categorie_id = categorieComboBox.getValue();

            // Récupérer l'ID de la catégorie à partir du type sélectionné
            String nom = nomm.getText();
            int prix = Integer.parseInt(prixx.getText());
            int nb_P = Integer.parseInt(nombreP.getText());
            String localisation = localisationn.getText();
            String description_act = descriptionActt.getText();
            ActiviteCrud service = new ActiviteCrud();
            System.out.println(description_act);
            //service.ajouter(new Activite(nom, prix, localisation, nb_P, etat, description_act,categorie_id), image);
            service.ajouter(new Activite(selectedCategoryId, nom, prix, localisation, nb_P, "en cours", description_act), image);

            showAlert("Activité ajoutée", "L'activité a été ajoutée avec succès.");
            categorieComboBox.getSelectionModel().clearSelection();
            nomm.clear();
            prixx.clear();
            nombreP.clear();
            localisationn.clear();
            descriptionActt.clear();
        }
    }*/

   private int selectedCategoryId=-1;
    @FXML
    void ajouteractiviteAction(ActionEvent event) throws SQLException {
        if (isInputValid()) {
            String image = imagePathInDatabase;

            // Assurez-vous que selectedCategoryId est correctement initialisé avec l'ID de la catégorie sélectionnée
            if (selectedCategoryId == -1) {
                System.out.println("Erreur : Aucune catégorie sélectionnée.");
                return;
            }

            String nom = nomm.getText();
            int prix = Integer.parseInt(prixx.getText());
            int nb_P = Integer.parseInt(nombreP.getText());
            String localisation = localisationn.getText();
            String description_act = descriptionActt.getText();

            ActiviteCrud service = new ActiviteCrud();

            // Vérifiez si la connexion à la base de données est ouverte avant d'ajouter l'activité
            if (MyConnection.getInstance().getCnx().isClosed()) {
                System.out.println("Erreur : La connexion à la base de données est fermée.");
                return;
            }

            // Ajoutez des journaux pour vérifier l'état de la connexion
            System.out.println("Connexion à la base de données : Ouverte");

            // Ajoutez l'activité en utilisant l'ID de la catégorie sélectionnée
            service.ajouter(new Activite(selectedCategoryId, nom, prix, localisation, nb_P, "en cours", description_act), image);

            showAlert("Activité ajoutée", "L'activité a été ajoutée avec succès.");
            categorieComboBox.getSelectionModel().clearSelection();
            nomm.clear();
            prixx.clear();
            nombreP.clear();
            localisationn.clear();
            descriptionActt.clear();
        } else {
            System.out.println("Erreur : Données d'entrée non valides.");
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
            Parent root = FXMLLoader.load(getClass().getResource("/affiche.fxml"));
            nomm.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
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

    private boolean isInputValid() {
        boolean isValid = true;
        /*if (categorieComboBox.getValue() == null) {
            errorCategorie.setText("Veuillez sélectionner une catégorie.");
            isValid = false;
        } else {
            errorCategorie.setText("");
        }*/
        if (categorieComboBox.getValue() == null) {
            errorCategorie.setText("Veuillez sélectionner une catégorie.");
            isValid = false;
        } else {
            errorCategorie.setText("");
        }

        // Validate and display error messages
        if (nomm.getText().isEmpty() || !nomm.getText().matches("^[a-zA-Z]+$")) {
            errorName.setText("Nom is required and should not contain numbers");
            isValid = false;
        } else {
            errorName.setText("");
        }

        if (descriptionActt.getText().isEmpty() && !descriptionActt.getText().matches("^[a-zA-Z]+$")) {
            errorDesc.setText("Description is required and should not contain numbers ");
            isValid = false;
        } else {
            errorDesc.setText("");
        }

        if (localisationn.getText().isEmpty() || !localisationn.getText().matches("^[a-zA-Z]+$")) {
            errorLocalisation.setText("Localisation is required and should not contain numbers ");
            isValid = false;
        } else {
            errorLocalisation.setText("");
        }


        if (prixx.getText().isEmpty() || !prixx.getText().matches("^[0-9]+$")) {
            errorPrix.setText("Le prix est requis et doit contenir uniquement des nombres");
            isValid = false;
        } else {
            errorPrix.setText("");
        }
        if (nombreP.getText().isEmpty() || !nombreP.getText().matches("^[0-9]+$")) {
            errorNbp.setText("Le prix est requis et doit contenir uniquement des nombres");
            isValid = false;
        } else {
            errorNbp.setText("");
        }
        if (imageView.getImage() == null) {
            isValid = false;
        }
        return isValid;
    }


    @FXML
    void initialize() {
        assert nomm != null : "fx:id=\"nom\" was not injected: check your FXML file 'activiteAjout.fxml'.";
        assert nombreP != null : "fx:id=\"nb_P\" was not injected: check your FXML file 'activiteAjout.fxml'.";
        assert prixx != null : "fx:id=\"prix\" was not injected: check your FXML file 'activiteAjout.fxml'.";
        assert descriptionActt != null : "fx:id=\"description_act\" was not injected: check your FXML file 'activiteAjout.fxml'.";

        assert localisationn != null : "fx:id=\"localisation\" was not injected: check your FXML file 'activiteAjout.fxml'.";
        assert save != null : "fx:id=\"save\" was not injected: check your FXML file 'activiteAjout.fxml'.";
       /*ActiviteCrud service = new ActiviteCrud();
        List<Integer> categorieIds = service.getAllCategorieIds();
        if (categorieIds != null) {
            categorieComboBox.getItems().addAll(categorieIds);
        } else {
            System.out.println("La liste des IDs de catégories est null.");
        }*/
        ActiviteCrud service = new ActiviteCrud();
        Map<String, Integer> typesCategories = service.getAllTypesCategories();

        if (!typesCategories.isEmpty()) {
            categorieComboBox.getItems().addAll(typesCategories.keySet());

            // Ajouter un écouteur d'événements pour mettre à jour selectedCategoryId
            categorieComboBox.setOnAction(event -> {
                if (categorieComboBox.getValue() != null) {
                    String selectedType = categorieComboBox.getValue().toString();
                    selectedCategoryId = typesCategories.get(selectedType);
                    System.out.println("ID de la catégorie sélectionnée : " + selectedCategoryId);
                }
            });

        } else {
            System.out.println("Aucune catégorie disponible.");
        }
    }
}




