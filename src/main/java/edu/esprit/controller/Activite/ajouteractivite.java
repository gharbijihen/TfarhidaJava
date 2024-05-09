package edu.esprit.controller.Activite;
import Utils.Datasource;
import edu.esprit.entites.Activite;
import edu.esprit.servies.ActiviteCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javafx.scene.image.ImageView;
import java.io.File;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;


import java.sql.SQLException;
import java.util.Map;

import javafx.stage.Stage;

public class ajouteractivite {
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
    private TableView<Activite> tableView;
    @FXML
    private final ActiviteCrud ps = new ActiviteCrud();

    @FXML
    private Button browseImage;
    @FXML
    private TextField selectedImagePath;
    @FXML
    private ComboBox<String> categorieComboBox;
    private String imagePathInDatabase;
    private ObservableList<Activite> listeActivites = FXCollections.observableArrayList();

    private int selectedCategoryId=-1;

    @FXML
    void ajouteractiviteAction(ActionEvent event) throws SQLException {
        if (nomm.getText().isEmpty()) {
            nomm.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
        } else {
            nomm.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }
        if (prixx.getText().isEmpty()) {
            prixx.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
        } else {
            prixx.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }
        if (localisationn.getText().isEmpty()) {
            localisationn.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
        } else {
            localisationn.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }
        if (descriptionActt.getText().isEmpty()) {
            descriptionActt.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
        } else {
            descriptionActt.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }
        if (nombreP.getText().isEmpty()) {
            nombreP.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
        } else {
            nombreP.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }

        if (isInputValid()) {
            String image=imagePathInDatabase;
            // Assurez-vous que selectedCategoryId est correctement initialisé avec l'ID de la catégorie sélectionnée
            if (selectedCategoryId == -1) {
                System.out.println("Erreur : Aucune catégorie sélectionnée.");
                return;
            }
            String nom= nomm.getText();
            int prix = Integer.parseInt(prixx.getText());
            int nb_P = Integer.parseInt(nombreP.getText());
            String localisation = localisationn.getText();
            String etat = etatt.getText();
            String description_act = descriptionActt.getText();
            ActiviteCrud service = new ActiviteCrud();

            // Vérifiez si la connexion à la base de données est ouverte avant d'ajouter l'activité
            if (Datasource.getConn().isClosed()) {
                System.out.println("Erreur : La connexion à la base de données est fermée.");
                return;
            }

            // Ajoutez des journaux pour vérifier l'état de la connexion
            System.out.println("Connexion à la base de données : Ouverte");


            /*System.out.println(descriptionActt.getText());*/
           /* service.ajouter(new Activite(nom, prix, localisation, nb_P, etat, description_act,categorie_id), image);*/
            service.ajouter(new Activite(selectedCategoryId, nom, prix, localisation, nb_P, etat, description_act), image);


            showAlert("Activité ajoutée", "L'activité a été ajoutée avec succès.");
            //nomm.clear();
           // prixx.clear();
           // nombreP.clear();
            //localisationn.clear();
           // etatt.clear();
           // descriptionActt.clear();
           // categorieComboBox.getSelectionModel().clearSelection();
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();

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


        if (nomm.getText().isEmpty() || !nomm.getText().matches("^[\\p{L} \\s]+$")) {
            errorName.setText("Nom est requis et ne doit pas  contenir des nombres ");
            isValid = false;
        } else {
            errorName.setText("");
        }

        if (descriptionActt.getText().isEmpty() || !descriptionActt.getText().matches("^[\\p{L} \\s]+$")) {
            errorDesc.setText("Description est requise et ne doit pas  contenir des nombres  ");
            isValid = false;
        } else {
            errorDesc.setText("");
        }

        if (localisationn.getText().isEmpty() || !localisationn.getText().matches("^[a-zA-Z]+$")) {
            errorLocalisation.setText("Localisation est requise et ne doit pas  contenir des nombres  ");
            isValid = false;
        } else {
            errorLocalisation.setText("");
        }
        if (etatt.getText().isEmpty() || !etatt.getText().matches("^[a-zA-Z]+$")) {
            etatt.setText("Etat est requis et ne doit pas  contenir des nombres ");
            isValid = false;
        } else {
            errorLocalisation.setText("");
        }

        if (prixx.getText().isEmpty() || !prixx.getText().matches("^[0-9]+$")) {
            errorPrix.setText("Prix est requis et doit contenir uniquement des nombres");
            isValid = false;
        } else {
            errorPrix.setText("");
        }
        if (nombreP.getText().isEmpty() || !nombreP.getText().matches("^[0-9]+$")) {
            errorNbp.setText("Nombre de personne est requis et doit contenir uniquement des nombres");
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
    void initialize()  {
        assert nomm != null : "fx:id=\"nom\" was not injected: check your FXML file 'activiteAjout.fxml'.";
        assert nombreP != null : "fx:id=\"nb_P\" was not injected: check your FXML file 'activiteAjout.fxml'.";
        assert prixx != null : "fx:id=\"prix\" was not injected: check your FXML file 'activiteAjout.fxml'.";
        assert descriptionActt != null : "fx:id=\"description_act\" was not injected: check your FXML file 'activiteAjout.fxml'.";
        assert etatt != null : "fx:id=\"etat\" was not injected: check your FXML file 'activiteAjout.fxml'.";
        assert localisationn != null : "fx:id=\"localisation\" was not injected: check your FXML file 'activiteAjout.fxml'.";
        assert save != null : "fx:id=\"save\" was not injected: check your FXML file 'activiteAjout.fxml'.";
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

