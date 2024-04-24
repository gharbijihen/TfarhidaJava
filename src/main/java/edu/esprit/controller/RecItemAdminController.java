package edu.esprit.controller;

import edu.esprit.entites.Reclamation;
import edu.esprit.entites.Reponse;
import edu.esprit.servies.ReclamationCrud;
import edu.esprit.servies.ReponseCrud;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;

import java.sql.SQLException;

public class RecItemAdminController {

    public Text etat;
    public ImageView image;
    public ImageView etatimg;
    public HBox editReclamation;
    public HBox DisplayReply;
    @FXML
    private Text comment;

    @FXML
    private Text date;

    @FXML
    private Text title;


    private ReponseCrud reponseCrud = new ReponseCrud();

    public void setReviewData(Reclamation reclamation) {
        title.setText(reclamation.getTitre());
        comment.setText(reclamation.getDescription_reclamation());
        date.setText(reclamation.getDate().toString());
        this.image.setImage(new Image("http://localhost:8000/uploads/"+reclamation.getImage()));
        System.out.println("http://localhost:8000/uploads/"+reclamation.getImage());
        if(reclamation.getEtat())
            etat.setText("Traitée");
        else {
            System.out.println("non traitée" + reclamation.getEtat());
            etat.setText("Non traitée");
            etat.setStyle("-fx-text-fill: red;");
            etat.applyCss();

            etatimg.setImage(new Image("assets/img/No_icon_red.png"));
        }
        this.DisplayReply.setOnMouseClicked(event -> {
            System.out.println("ID du réclamation à supprimer : " + reclamation.getId());
            if (reclamation.getReponseid()<1)
                utils.TrayNotificationAlert.notif("Reclamation", "You haven't replied to this Reclamation yet.",
                        NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
            else {
                System.out.println("ID du reclamation à voir : " + reclamation.getId());
                // produitService.supprimerReview(review.getId());
                ReclamationsListController_new.reclamation= reclamation;
                try {
                    Reponse rep = reponseCrud.getById(reclamation.getReponseid());
                    System.out.println("Reponse : "+rep);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                TextArea commentInput = (TextArea) ((Node) event.getSource()).getScene().lookup("#commentInput");
                commentInput.setText(reclamation.getReponse().getDescription());
                commentInput.setDisable(true);
                HBox submitBtn= (HBox) ((Node) event.getSource()).getScene().lookup("#submitBtn");
                submitBtn.setVisible(false);
                HBox addReviewsModel = (HBox) ((Node) event.getSource()).getScene().lookup("#addReviewsModel");
                addReviewsModel.setVisible(true);

            }
        });

        editReclamation.setId(String.valueOf(reclamation.getId()));
        editReclamation.setOnMouseClicked(event -> {
                System.out.println("ID du reclamation à modifier : " + reclamation.getId());
                RecListAdminController.reclamation = reclamation;


                TextArea commentInput = (TextArea) ((Node) event.getSource()).getScene().lookup("#commentInput");
                if(reclamation.getReponse()!=null)
                commentInput.setText(reclamation.getReponse().getDescription());
                else
                    commentInput.setText("");
                commentInput.setDisable(false);
                HBox updateBtn = (HBox) ((Node) event.getSource()).getScene().lookup("#updateBtn");
                updateBtn.setVisible(true);

                HBox updateBtnContainer = (HBox) ((Node) event.getSource()).getScene().lookup("#updateBtnContainer");
                updateBtnContainer.setVisible(true);

                HBox submitBtn = (HBox) ((Node) event.getSource()).getScene().lookup("#submitBtn");
                submitBtn.setVisible(false);

                HBox addReviewsModel = (HBox) ((Node) event.getSource()).getScene().lookup("#addReviewsModel");
                addReviewsModel.setVisible(true);

        });
    }
}
