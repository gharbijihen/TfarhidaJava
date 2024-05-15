package edu.esprit.controller.Logement;

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
    void ajouterLogementAction(ActionEvent event) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        if (nom.getText().isEmpty()) {
            nom.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
        } else {
            nom.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }
        if (note.getText().isEmpty()) {
            note.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
        } else {
            note.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }
        if (localisation.getText().isEmpty()) {
            localisation.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
        } else {
            localisation.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }
        if (prix.getText().isEmpty()) {
            prix.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
        } else {
            prix.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }
        if (num.getText().isEmpty()) {
            num.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
        } else {
            num.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }
        if (type_log.getValue()==null ) {
            type_log.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
        } else {
            type_log.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }
        if (isInputValid()) {
            // Ajoutez le logement à la base de données
            String nomL = nom.getText();
            String localisationL = localisation.getText();
            int numL = Integer.parseInt(num.getText());
            int noteL = Integer.parseInt(note.getText());
            int prixL = Integer.parseInt(prix.getText());
            String typeLog = type_log.getValue();
            String imageL = imagePathInDatabase;

            LogementCrud service = new LogementCrud();
            Logement log;
            uploadImage(selectedImageFile);
            service.ajouter(log =new Logement(nomL, localisationL, numL, prixL, selectedImageFile.getName(), "Acceptee", typeLog, noteL));
            System.out.println("loggg eli tzed"+log);
            showAlert("Logement ajouté", "Votre logement a été ajouté avec succès.");

            // Effacez les champs et réinitialisez l'image
            nom.clear();
            localisation.clear();
            num.clear();
            note.clear();
            prix.clear();
            type_log.getSelectionModel().clearSelection();
            imageView.setImage(null);
            selectedImageFile = null;

            naviguezVersEquipement(event,log);

        }
    }




    private void resetFormFields() {
        nom.clear();
        localisation.clear();
        num.clear();
        note.clear();
        prix.clear();
        typelLog.clear();
        //equipementId.clear();
        imageView.setImage(null);
        selectedImageFile = null;
    }
    @FXML
    void naviguezVersEquipement(ActionEvent event,Logement logement) {
        try {
            //System.out.println(logement+"navigiha");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogementFxml/AjouterEquipement.fxml"));
            Parent root = loader.load();
            nom.getScene().setRoot(root);
            AjouterEquipementB controller = loader.getController();
            controller.initData(logement);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    private boolean isInputValid() {
        boolean isValid = true;

        // Validate and display error messages
        if (nom.getText().isEmpty() || !nom.getText().matches("^[a-zA-Z ]+$")) {
            errorNom.setText("Nom is required and should not contain numbers");
            isValid = false;
        } else {
            errorNom.setText("");
        }

        if (localisation.getText().isEmpty() || !localisation.getText().matches("^[a-zA-Z ]+$")) {
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
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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
