package edu.esprit.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import edu.esprit.entites.Reponse;
import edu.esprit.servies.ReponseCrud;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.Node;
import javafx.scene.Parent;
import edu.esprit.servies.ReclamationCrud;
import edu.esprit.entites.Reclamation;
import javafx.util.Duration;
import javafx.application.Platform;
import tray.animations.AnimationType;
import tray.notification.NotificationType;

public class ReclamationItem {

    public Text etat;
    public ImageView image;

    public HBox displayReponse;
    public ImageView etatimg;
    public HBox deleteReclamation;
    public HBox editReclamation;
    public Text type;
    @FXML
    private Text comment;

    @FXML
    private Text date;

    @FXML
    private Text title;

    @FXML
    private Text userName;



    public void setReviewData(Reclamation reclamation) {
        // Instancier le service de produit
        // IProduitService produitService = new ProduitService();
        title.setText(reclamation.getTitre());
        comment.setText(reclamation.getDescription_reclamation());
        date.setText(reclamation.getDate().toString());
        this.image.setImage(new Image("http://localhost:8000/uploads/" + reclamation.getImage()));
        System.out.println("http://localhost:8000/uploads/" + reclamation.getImage());
        type.setText(reclamation.getType());
        if (reclamation.getEtat()){
            etat.setText("Traitée");
        displayReponse.setVisible(true);
        }
        else {
            System.out.println("non traitée" + reclamation.getEtat());
            etat.setText("Non traitée");
            etat.setStyle("-fx-text-fill: red;");
            etat.applyCss();
            displayReponse.setVisible(false);
            etatimg.setImage(new Image("assets/img/No_icon_red.png"));
        }
        ReponseCrud reponseCrud = new ReponseCrud();
        this.displayReponse.setOnMouseClicked(event -> {
            System.out.println("ID du réclamation à supprimer : " + reclamation.getId());
            if (reclamation.getReponseid()<1)
                utils.TrayNotificationAlert.notif("Reclamation", "You haven't replied to this Reclamation yet.",
                        NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
            else {
                showSuccessMessage(reclamation.getReponse().description);
            }
        });

        this.deleteReclamation.setOnMouseClicked(event -> {
               System.out.println("ID du réclamation à supprimer : " + reclamation.getId());
               if (reclamation.getEtat())

                   utils.TrayNotificationAlert.notif("Reclamation", "Reclamation not deleted, it's already treated.",
                           NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));

               else {
                   try {
                       ReclamationCrud reclamationCrud = new ReclamationCrud();
                       reclamationCrud.supprimer(reclamation);
                       utils.TrayNotificationAlert.notif("Reclamation", "Reclamation deleted successfully.",
                               NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));
                       FXMLLoader loader = new FXMLLoader(
                               getClass().getResource("/RecItem/ReclamationsList.fxml"));
                       try {
                           Parent root = loader.load();
                           Pane contentArea = (Pane) ((Node) event.getSource()).getScene().lookup("#content_area");

                           contentArea.getChildren().clear();
                           contentArea.getChildren().add(root);
                       } catch (IOException e) {
                           e.printStackTrace();
                       }

                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
               }
           });

            editReclamation.setId(String.valueOf(reclamation.getId()));

           editReclamation.setOnMouseClicked(event -> {
               if(reclamation.getEtat()==true) {
                   utils.TrayNotificationAlert.notif("Reclamation", "Reclamation cannot be modified upon admin supervision.",
                           NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
               }
                   else {
                   System.out.println("ID du reclamation à modifier : " + reclamation.getId());
                   ReclamationsListController_new.reclamation = reclamation;

                   TextField titleInput = (TextField) ((Node) event.getSource()).getScene().lookup("#titleInput");
                   titleInput.setText(reclamation.getTitre());

                   TextArea commentInput = (TextArea) ((Node) event.getSource()).getScene().lookup("#commentInput");
                   commentInput.setText(reclamation.getDescription_reclamation());

                   ImageView image = (ImageView) ((Node) ((Node) event.getSource()).getScene().lookup("#img"));
                   Image imagemodif = new Image("http://localhost:8000/uploads/" + reclamation.getImage());
                   image.setImage(imagemodif);

                   System.out.println(image);

                   ComboBox<String> typeInput = (ComboBox<String>) ((Node) event.getSource()).getScene().lookup("#TypeInput");
                   typeInput.getSelectionModel().select(reclamation.getType());
                   HBox updateBtn = (HBox) ((Node) event.getSource()).getScene().lookup("#updateBtn");
                   updateBtn.setVisible(true);

                   System.out.println("image " + "https://localhost:8000/uploads/" + reclamation.getImage());
                   HBox updateBtnContainer = (HBox) ((Node) event.getSource()).getScene().lookup("#updateBtnContainer");
                   updateBtnContainer.setVisible(true);

                   HBox submitBtn = (HBox) ((Node) event.getSource()).getScene().lookup("#submitBtn");
                   submitBtn.setVisible(false);

                   HBox addReviewsModel = (HBox) ((Node) event.getSource()).getScene().lookup("#addReviewsModel");
                   addReviewsModel.setVisible(true);
               }
            });
        }
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Réponse");
        alert.setHeaderText("Réponse");
        alert.setContentText(message);
        alert.showAndWait();
    }
    }


