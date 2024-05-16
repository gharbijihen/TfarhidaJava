package edu.esprit.controller.Activite;
import Utils.Datasource;
import edu.esprit.entites.Activite;
import edu.esprit.servies.ActiviteCrud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import javafx.scene.image.ImageView;
import java.io.File;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;


import java.sql.SQLException;
import java.util.List;

import javafx.stage.Stage;
import javafx.util.Pair;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;

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
    void ajouteractiviteAction(ActionEvent event) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, SQLException {
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
            uploadImage(selectedImageFile);


            // Vérifiez si la connexion à la base de données est ouverte avant d'ajouter l'activité
            if (Datasource.getConn().isClosed()) {
                System.out.println("Erreur : La connexion à la base de données est fermée.");
                return;
            }

            // Ajoutez des journaux pour vérifier l'état de la connexion
            System.out.println("Connexion à la base de données : Ouverte");

            // Ajoutez l'activité en utilisant l'ID de la catégorie sélectionnée
            service.ajouter(new Activite(selectedCategoryId, nom, prix, localisation, nb_P, "en cours", description_act), selectedImageFile.getName());

            showAlert("Activité ajoutée", "L'activité a été ajoutée avec succès.");
           // categorieComboBox.getSelectionModel().clearSelection();
           // nomm.clear();
           // prixx.clear();
           // nombreP.clear();
            //localisationn.clear();
            //descriptionActt.clear();
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
    public void uploadImage(File imageFile) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        HttpPost httpPost = new HttpPost("http://localhost:8000/upload-image");

        HttpEntity requestEntity = MultipartEntityBuilder.create()
                .addBinaryBody("image", imageFile, ContentType.APPLICATION_OCTET_STREAM, imageFile.getName())
                .build();

        httpPost.setEntity(requestEntity);
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(new TrustSelfSignedStrategy()).build();

        HttpClient httpClient = HttpClients.custom().setSSLContext(sslContext).build();
        HttpResponse response = httpClient.execute(httpPost);
        System.out.println(response);

        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 200) {
            Header contentDispositionHeader = response.getFirstHeader("Content-Disposition");
            if (contentDispositionHeader != null) {
                System.out.println("Success upload. Filename");
            } else {
                System.out.println("Success upload, but filename not found in the response");
            }
        } else {
            System.out.println("Failed upload");
        }
    }
    @FXML
    void selectImageAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        // Filtrer les types de fichiers si nécessaire
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            selectedImageFile=selectedFile;
            // Stocker le chemin de l'image sélectionnée dans la variable de classe
            imagePathInDatabase = selectedFile.getAbsolutePath();
            // Charger l'image sélectionnée dans l'ImageView
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
        }
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




