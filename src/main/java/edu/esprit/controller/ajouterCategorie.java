package edu.esprit.controller;
import edu.esprit.entites.Activite;
import edu.esprit.entites.Categorie;
import edu.esprit.servies.ActiviteCrud;
import edu.esprit.servies.CategorieCrud;
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
import java.util.UUID;
import javafx.stage.Stage;

public class ajouterCategorie {
    @FXML
    private TextField type;
    @FXML
    private TextField descriptionCatt;
    @FXML
    private Button save;

    @FXML
    private Text errorType;

    @FXML
    private Text errorDescC;

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
    void ajoutercategorieAction(ActionEvent event) {
        if (type.getText().isEmpty()) {
            type.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
        } else {
            type.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }
        if (descriptionCatt.getText().isEmpty()) {
            descriptionCatt.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
        } else {
            descriptionCatt.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }
        if (isInputValid()) {

            String  description= descriptionCatt.getText();
            String type_categorie = type.getText();
            CategorieCrud service = new CategorieCrud();
            System.out.println(description);

            /*System.out.println(descriptionActt.getText());*/
            /* service.ajouter(new Activite(nom, prix, localisation, nb_P, etat, description_act,categorie_id), image);*/
            service.ajouter(new Categorie(description,type_categorie));

            showAlert("Catégorie ajoutée", "La catégorie a été ajoutée avec succès.");
            //descriptionCatt.clear();
            //type.clear();

            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();

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




    private boolean isInputValid() {
        boolean isValid = true;


        // Validate and display error messages
        if (descriptionCatt.getText().isEmpty() || !descriptionCatt.getText().matches("^[\\p{L} \\s]+$")) {
            errorDescC.setText("Description est requise et ne doit pas  contenir des nombres");
            isValid = false;
        } else {
            errorDescC.setText("");
        }
        if (type.getText().isEmpty() && !type.getText().matches("^[\\p{L} \\s]+$")) {
            errorType.setText("Type est requis et ne doit pas  contenir des nombres ");
            isValid = false;
        } else {
            errorType.setText("");
        }
        return isValid;
    }




    @FXML
    void initialize()  {
        assert descriptionCatt != null : "fx:id=\"description\" was not injected: check your FXML file 'categorieAjouter.fxml'.";
        assert type != null : "fx:id=\"type_categorie\" was not injected: check your FXML file 'categorieAjouter.fxml'.";

        assert save != null : "fx:id=\"save\" was not injected: check your FXML file 'activiteAjout.fxml'.";

    }
}

