//package edu.esprit.controller;
//
//import edu.esprit.entites.Reclamation;
//import edu.esprit.entites.Reponse;
//import edu.esprit.servies.ReclamationCrud;
//import edu.esprit.servies.ReponseCrud;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.DatePicker;
//import javafx.scene.control.TextField;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.text.Text;
//import javafx.stage.FileChooser;
//import javafx.stage.Stage;
//
//import java.io.File;
//import java.sql.Date;
//
//public class AjouterReponseController {
//
// @FXML
// private Button ButtonAjouterReponse;
//
// @FXML
// private Text errorDescription;
//
// @FXML
// private DatePicker idDateRep;
//
// @FXML
// private TextField idDescriptionRep;
//
//
//
//
//
// @FXML
// void ajouterReponseAction(ActionEvent event) {
//  if (isInputValid()) {
//
//   String descriptionRep= idDescriptionRep.getText();
//   Date dateRep= Date.valueOf(idDateRep.getValue());
//
//
//   ReponseCrud service = new ReponseCrud();
//   service.ajouter(new Reponse(descriptionRep,dateRep ));
//
//   showAlert("Reponse ajoutée", "La réponse a été ajoutée avec succés.");
//
//   idDescriptionRep.clear();
//
//
//  }
// }
// @FXML
// private void showAlert(String title, String content) {
//  Alert alert = new Alert(Alert.AlertType.INFORMATION);
//  alert.setTitle(title);
//  alert.setHeaderText(null);
//  alert.setContentText(content);
//  alert.showAndWait();
// }
//
// private boolean isInputValid() {
//  boolean isValid = true;
//
//
//
//
//  if (idDescriptionRep.getText().isEmpty() && !idDescriptionRep.getText().matches("^[a-zA-Z]+$")) {
//   errorDescription.setText("Description is required and should not contain numbers ");
//   isValid = false;
//  } else {
//   errorDescription.setText("");
//  }
//
//
//
//
//  return isValid;
// }
//
//
//
//
// @FXML
// void initialize()  {
//
//  assert idDescriptionRep != null : "fx:id=\"nb_P\" was not injected: check your FXML file 'activiteAjout.fxml'.";
//  assert idDateRep != null : "fx:id=\"description_act\" was not injected: check your FXML file 'activiteAjout.fxml'.";
//
//
// }
//
// public void setParentFXMLLoader(AfficherReponseBackController afficherReponseBackController) {
// }
//}
