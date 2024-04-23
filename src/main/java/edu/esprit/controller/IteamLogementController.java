package edu.esprit.controller;

import edu.esprit.entites.Logement;
import edu.esprit.tests.MyListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;

public class IteamLogementController {

    @FXML
    private ImageView imgLogement;

    @FXML
    private Label loalisationLogement;

    @FXML
    private Button map;

    @FXML
    private Label nameLogement;

    @FXML
    private Label phoneLogement;

    @FXML
    private Label priceLogement;

    private Logement logement;
    private MyListener myListener;

    // Méthode appelée lors du clic sur l'élément logement
    @FXML
    private void click(MouseEvent mouseEvent) {
        this.myListener.onClickListener(this.logement);
    }

    // Méthode pour définir les données du logement dans l'interface utilisateur
    public void setData(Logement logement, MyListener myListener) {
        this.logement = logement;
        this.myListener = myListener;
        this.nameLogement.setText(logement.getNom());
        this.priceLogement.setText(logement.getPrix() + "DT/Personne");
        this.loalisationLogement.setText(logement.getLocalisation());
        this.phoneLogement.setText("+216" + logement.getNum());
        imgLogement.setFitWidth(300); // Ajuster la largeur de l'image
        imgLogement.setFitHeight(300); // Ajuster la hauteur de l'image
        String imagePath = logement.getImage();

        if (imagePath != null && !imagePath.isEmpty()) {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                Image image = new Image(imageFile.toURI().toString());
                this.imgLogement.setImage(image);
            } else {
                System.err.println("L'image n'existe pas à l'emplacement spécifié : " + imagePath);
                // Afficher une image par défaut ou un message d'erreur
                // Par exemple :
                // Image image = new Image(getClass().getResourceAsStream("default_image.png"));
                // this.imgLogement.setImage(image);
            }
        } else {
            System.err.println("Le chemin d'accès à l'image est vide ou invalide pour le logement : " + logement.getNom());
            // Afficher une image par défaut ou un message d'erreur
            // Par exemple :
            // Image image = new Image(getClass().getResourceAsStream("default_image.png"));
            // this.imgLogement.setImage(image);
        }
    }

}
