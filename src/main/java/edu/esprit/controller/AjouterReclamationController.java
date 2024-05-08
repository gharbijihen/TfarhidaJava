//package edu.esprit.controller;
//
//import edu.esprit.entites.Reclamation;
//import edu.esprit.servies.ReclamationCrud;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.Parent;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.DatePicker;
//import javafx.scene.control.TextField;
//import javafx.scene.image.ImageView;
//import javafx.scene.image.Image;
//import javafx.scene.text.Text;
//import javafx.stage.FileChooser;
//import javafx.fxml.FXMLLoader;
//import javafx.stage.Stage;
//import javafx.util.Duration;
//
//import java.io.File;
//import java.io.IOException;
//import java.sql.Date;
//import java.time.LocalDate;
//
//public class AjouterReclamationController {
//
//    @FXML
//    private Button ButtonAjouterReclamation;
//
//    @FXML
//    private Text errorDescription;
//
//    @FXML
//    private Text errorTitre;
//
//    @FXML
//    private TextField idDescriptionRec;
//
//    @FXML
//    private TextField idTitre;
//
//    @FXML
//    private TextField idTypeRec;
//
//    @FXML
//    private DatePicker idDateRec;
//
//    @FXML
//    private ImageView imageView;
//
//    @FXML
//    private Button selectImage;
//
//    private File selectedImageFile;
//
//    @FXML
//    private Button browseImage;
//    @FXML
//    private TextField selectedImagePath;
//
//    private String imagePathInDatabase;
//    @FXML
//    void AjouterImageAction(ActionEvent event) {
//
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Choisir une image");
//        // Filtrer les types de fichiers si nécessaire
//        File selectedFile = fileChooser.showOpenDialog(new Stage());
//        if (selectedFile != null) {
//            // Stocker le chemin de l'image sélectionnée dans la variable de classe
//            imagePathInDatabase = selectedFile.getAbsolutePath();
//            // Charger l'image sélectionnée dans l'ImageView
//            Image image = new Image(selectedFile.toURI().toString());
//            imageView.setImage(image);
//        }
//
//    }
//
//    @FXML
//    void ajouterReclamationAction(ActionEvent event) {
//        if (isInputValid()) {
//            String image=imagePathInDatabase;
//            String titre= idTitre.getText();
//            String description= idDescriptionRec.getText();
//            String type= idTypeRec.getText();
//           Date date= Date.valueOf(idDateRec.getValue());
//
//
//            ReclamationCrud service = new ReclamationCrud();
//
//            service.ajouter(new Reclamation(titre,description,false,date,image,type));
//
//            showAlert("Reclamation ajoutée", "La réclamation a été ajoutée avec succés.");
//
//            idTitre.clear();
//            idDescriptionRec.clear();
//            idTypeRec.clear();
//
//        }
//
//    }
//
//    @FXML
//    private void showAlert(String title, String content) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(content);
//        alert.showAndWait();
//    }
////    @FXML
////    void naviguezVersAffichage(ActionEvent event) {
////        try {
////            Parent root = FXMLLoader.load(getClass().getResource("/affiche.fxml"));
////            idTitre.getScene().setRoot(root);
////        } catch (IOException e) {
////            System.err.println(e.getMessage());
////        }
////    }
//
//
//
//    private boolean isInputValid() {
//        boolean isValid = true;
//
//
//        // Validate and display error messages
//        if (idTitre.getText().isEmpty() || !idTitre.getText().matches("^[a-zA-Z]+$")) {
//            errorTitre.setText("Titre is required and should not contain numbers");
//            isValid = false;
//        } else {
//            errorTitre.setText("");
//        }
//
//        if (idDescriptionRec.getText().isEmpty() && !idDescriptionRec.getText().matches("^[a-zA-Z]+$")) {
//            errorDescription.setText("Description is required and should not contain numbers ");
//            isValid = false;
//        } else {
//            errorDescription.setText("");
//        }
//
//
//
//        if (imageView.getImage() == null) {
//            isValid = false;
//        }
//        return isValid;
//    }
//
//
//
//
//    @FXML
//    void initialize()  {
//
//        assert idTitre != null : "fx:id=\"idTitre\" was not injected: check your FXML file 'activiteAjout.fxml'.";
//        assert idDescriptionRec != null : "fx:id=\"iddescription\" was not injected: check your FXML file 'activiteAjout.fxml'.";
//        assert idTypeRec != null : "fx:id=\"idTypeRec\" was not injected: check your FXML file 'activiteAjout.fxml'.";
//        assert idDateRec != null : "fx:id=\"idDateRec\" was not injected: check your FXML file 'activiteAjout.fxml'.";
//
//
//    }
//
//}
