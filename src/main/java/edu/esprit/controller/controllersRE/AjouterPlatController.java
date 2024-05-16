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
import javafx.stage.Stage;
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
import java.io.File;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
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

    private  File selectedImageFile;


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
    void ajouterPlat(ActionEvent event) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        // Vérifier que les champs obligatoires ne sont pas vides
        if (nom.getText().isEmpty() || description.getText().isEmpty()) {
            showAlert("Error", "Veuillez remplir tous les champs obligatoires.");
            return;
        }

        try {
            uploadImage(selectedImageFile);
            // Créez le plat en lui passant l'ID du restaurant
            Plat plat = new Plat(nom.getText(), description.getText(), selectedImageFile.getName(), restaurantId);

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
        // Filtrer les types de fichiers si nécessaire
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            selectedImageFile = selectedFile;
            // Stocker le chemin de l'image sélectionnée dans la variable de classe
            imagePath = selectedFile.toURI().toString();
            // Charger l'image sélectionnée dans l'ImageView
            Image image = new Image(selectedFile.toURI().toString());
            imagef.setImage(image);
        }
    }

    public void initData(int restaurantId) {
        this.restaurantId = restaurantId;
    }
}
