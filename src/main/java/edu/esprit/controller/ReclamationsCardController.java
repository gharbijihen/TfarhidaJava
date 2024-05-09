package edu.esprit.controller;

import edu.esprit.entites.Reclamation;
import edu.esprit.servies.ReclamationCrud;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.Node;
import javafx.scene.Parent;
public class ReclamationsCardController {

    public static Reclamation reclamation_static;
    public GridPane ReclamationsListContainer;
    public Text repliedTo;
    @FXML
    private ImageView img;

    @FXML
    private Text name;

    @FXML
    private Text description;

    @FXML
    private Text stockQty;

    @FXML
    private Text price;

    @FXML
    private HBox Details;

    @FXML
    private HBox openDetails;


    public void setReclamationsData(Reclamation reclamation) {
        // Instancier le service de produit
        ReclamationCrud serviceReclamation = new ReclamationCrud();

     /*   if (reclamation.getBlobImage() != null) {
            Image img = new Image(new ByteArrayInputStream(reclamation.getBlobImage()));
            this.img.setImage(img);
        }*/

        name.setText(reclamation.getTitre());

        description.setText("" + reclamation.getDescription_reclamation());
        price.setText("" + reclamation.getType());
        stockQty.setText("Date : " + reclamation.getDate());

        repliedTo.setText("A une réponse");

        openDetails.setOnMouseClicked(event -> {
            System.out.println("ID du Reclamation à afficher les details : " + reclamation.getId());
            ReclamationsCardController.reclamation_static=reclamation;
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/Reclamations/ReclamationsDetailsCard.fxml"));
            try {
                Parent root = loader.load();
                Pane contentArea = (Pane) ((Node) event.getSource()).getScene().lookup("#content_area");

                contentArea.getChildren().clear();
                contentArea.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }
}
