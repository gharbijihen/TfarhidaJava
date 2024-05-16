package edu.esprit.controller.Logement;

import edu.esprit.controller.Logement.SmsController;
import edu.esprit.controller.ReclamationsListController_new;
import edu.esprit.entites.Logement;
import edu.esprit.servies.LogementCrud;
import edu.esprit.tools.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;


public class ModifierLogementB {

    @FXML
    private Button ButtonModifierLogement;

    @FXML
    private TextField equipementId;

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
    private ImageView imageView;

    @FXML
    private TextField localisation;
    @FXML
    private Text etat;
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
    private ComboBox<String> type_log;

    private String img;
    private Image image;
    private Image imageGet;
    private Logement logement;
    private File selectedImageFile;
    ObservableList<String> typeLog = FXCollections.observableArrayList(Data.typeLogement);

    private String imagePathInDatabase;

    public void initData(Logement logement) {
        this.logement = logement;

        this.img= logement.getImage();

        nom.setText(logement.getNom());
        prix.setText(String.valueOf(logement.getPrix()));
        type_log.setItems(typeLog);
        type_log.setValue(logement.getType_log());
        localisation.setText(logement.getLocalisation());
        note.setText(String.valueOf(logement.getNote_moyenne()));
        num.setText(String.valueOf(logement.getNum()));
        etat.setText(logement.getEtat());

        if (selectedImageFile != null) {
              try {
                Image image = new Image(new FileInputStream(selectedImageFile));
                imageView.setImage(image);
            } catch (FileNotFoundException e) {
                System.out.println("Erreur lors du chargement de l'image : " + e.getMessage());
            }
        } else {
              String imagePath = logement.getImage();
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                Image image = new Image(imageFile.toURI().toString());
                imageView.setImage(image);
            } else {
                System.out.println("Le fichier image n'existe pas : " + imagePath);
            }
        }

        String path = logement.getImage();
        try {
            imageGet = new Image(new File(path).toURI().toURL().toString(), 207, 138, false, true);
            imageView.setImage(imageGet);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println("imagini"+logement.getImage());

    }
    @FXML
    void modifierLogementAction(ActionEvent event) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, SQLException {

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

            int LogementId = logement.getId();


            if (LogementId != 0) {
                    try {
                    String nomL = nom.getText();
                    String typeL = type_log.getValue();
                    int prixL = Integer.parseInt(prix.getText());
                    int numL = Integer.parseInt(num.getText());
                    int noteL = Integer.parseInt(note.getText());
                    String localisationL = localisation.getText();

                    // Créez un objet Activite avec les nouvelles valeurs
                    Logement logementModifiee = new Logement(4);
                    logementModifiee.setId(LogementId); // Assurez-vous de définir l'ID de l'activité
                    logementModifiee.setNom(nomL);
                    logementModifiee.setNum(numL);
                    logementModifiee.setPrix(prixL);
                    logementModifiee.setNote_moyenne(noteL);
                    logementModifiee.setLocalisation(localisationL);
                    logementModifiee.setType_log(typeL);

                        if(selectedImageFile!=null) {
                            System.out.println("Image is uploaded"+selectedImageFile.getName());
                            uploadImage(selectedImageFile);
                            logementModifiee.setImage(selectedImageFile.getName());
                        }else{
                            System.out.println("Image is not uploaded");
                            logementModifiee.setImage(logement.getImage());
                        }

                      LogementCrud service = new LogementCrud();
                    service.modifier(logementModifiee);

                    System.out.println("Logement modifiée avec succès !");
                    showAlert("Logement modifiee", "Votre logement a été modiffie avec succès.");

                       } catch (NumberFormatException e) {
                    System.out.println("Erreur de format : Assurez-vous que les champs Prix et nombre de chambre  Participant sont des nombres entiers.");
                    }
            } else {
                System.out.println("L'ID de l'activité sélectionnée est invalide.");
                   }
        } else {
              System.out.println("Les données saisies ne sont pas valides. Veuillez vérifier les champs.");
           }
        naviguezVersEquipement(event);

    }
    //image
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
                String contentDisposition = contentDispositionHeader.getValue();
                String filename = extractFilenameFromContentDisposition(contentDisposition);
                System.out.println("Success upload. Filename: " + filename);
            } else {
                System.out.println("Success upload, but filename not found in the response");
            }
        } else {
            System.out.println("Failed upload");
        }
    }
    private String extractFilenameFromContentDisposition(String contentDisposition) {
        String filename = null;
        if (contentDisposition != null && contentDisposition.contains("filename=")) {
            String[] parts = contentDisposition.split(";");
            for (String part : parts) {
                if (part.trim().startsWith("filename=")) {
                    filename = part.substring(part.indexOf('=') + 1).trim().replace("\"", "");
                    break;
                }
            }
        }
        return filename;
    }
    @FXML
    void naviguezVersEquipement(ActionEvent event) {

            try {
                // Charger le fichier FXML de la nouvelle page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogementFxml/LogementAffB.fxml"));
                Parent root = loader.load();

                // Créer une nouvelle scène avec la nouvelle page
                Scene scene = new Scene(root);

                // Obtenir la fenêtre actuelle à partir de l'événement
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Définir la nouvelle scène sur la fenêtre et l'afficher
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
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


        if (nom.getText().isEmpty() || !nom.getText().matches("^[a-z A-Z]+$")) {
            errorNom.setText("Nom is required and should not contain numbers");
            isValid = false;
        } else {
            errorNom.setText("");
        }

        if (localisation.getText().isEmpty() || !localisation.getText().matches("^[a-z A-Z]+$")) {
            errorLocalisation.setText("Localisation is required and should not contain numbers ");
            isValid = false;
        } else {
            errorLocalisation.setText("");
        }

        if (num.getText().isEmpty() || !num.getText().matches("^[0-9]+$")) {
            errorNum.setText("Numero est requis et doit contenir uniquement 8 nombres");
            isValid = false;
        } else {
            errorNum.setText("");
        }


        if (prix.getText().isEmpty() || !prix.getText().matches("^[0-9]+$")) {
            errorPrix.setText("Le prix est requis et doit contenir uniquement des nombres");
            isValid = false;
        } else {
            errorPrix.setText("");
        }
        if (note.getText().isEmpty() || !note.getText().matches("^[0-9]+$")) {
            errorNote.setText("Le prix est requis et doit contenir uniquement des nombres");
            isValid = false;
        } else {
            errorNote.setText("");
        }

        return isValid;
    }

    @FXML
    void selectImageAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                selectedImageFile = selectedFile;
                imagePathInDatabase = selectedFile.getAbsolutePath();
                Image image = new Image(new FileInputStream(selectedFile));
                imageView.setImage(image);
            } catch (FileNotFoundException e) {
                System.out.println("Erreur lors du chargement de l'image : " + e.getMessage());
            }
        } else {
            System.out.println("Aucun fichier sélectionné");
        }
    }

    @FXML
    void updateEquip(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogementFxml/modifierEquipement.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            System.out.println(logement+"new here we go");
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            ModifierEquipement modifierEquipement=loader.getController();
            modifierEquipement.initData(logement.equipement_id);
            stage.setTitle("One Equip");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void refusee(ActionEvent event) {
        if (logement != null) {
            // Modifier l'état du logement en "Accepté"
            logement.setEtat("Refusé");

            // Instancier LogementCrud
            LogementCrud service = new LogementCrud();

            // Appeler la méthode de mise à jour de l'état du logement
            service.modifierEtat(logement.getId(), "Refusé");

            // Afficher une alerte pour indiquer que l'état du logement a été modifié avec succès
            showAlert("État modifié", "L'état du logement a été modifié avec succès.");
            SmsController.SmsRéfuse();

        } else {
            System.out.println("Erreur : le logement n'est pas initialisé.");
        }
    }
    @FXML
    public void accepter(ActionEvent event) {
        if (logement != null) {
            // Modifier l'état du logement en "Accepté"
            logement.setEtat("Acceptee");

            // Instancier LogementCrud
            LogementCrud service = new LogementCrud();

            // Appeler la méthode de mise à jour de l'état du logement
            service.modifierEtat(logement.getId(), "Acceptee");

            // Afficher une alerte pour indiquer que l'état du logement a été modifié avec succès
            showAlert("État modifié", "L'état du logement a été modifié avec succès.");
            SmsController.SmsAccepter();

        } else {
            System.out.println("Erreur : le logement n'est pas initialisé.");
        }
}
}
