package edu.esprit.controller;

import edu.esprit.entites.Activite;
import edu.esprit.tests.MyListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;

public class IteamController {

    @FXML
    private ImageView img;

    @FXML
    private Label loalisationLabel;

    @FXML
    private Button map;

    @FXML
    private Label nameLabel;

    @FXML
    private Label nbpLabel;

    @FXML
    private Label priceLable;

    private Activite activite;
    private MyListener myListener;

    // Constructeur par défaut
    public IteamController() {}

    @FXML
    private void click(MouseEvent mouseEvent) {
        if (myListener != null) {
            myListener.onClickListener(activite);
        }
    }

    // Méthode pour définir les données de l'activité
    public void setData(Activite activite, MyListener myListener) {
        this.activite = activite;
        this.myListener = myListener;
        this.nameLabel.setText(activite.getNom());
        this.priceLable.setText(activite.getPrix() + " DT/Personne");
        this.loalisationLabel.setText(activite.getLocalisation());
        this.nbpLabel.setText(activite.getNb_P() + " Participant");
        img.setFitWidth(350); // Ajuster la largeur de l'image
        img.setFitHeight(350); // Ajuster la hauteur de l'image


        String imagePath = activite.getImage();
        if (imagePath != null) {
            // Charger l'image à partir du chemin d'accès spécifié
            Image image = new Image(new File(imagePath).toURI().toString());
            this.img.setImage(image);
        } else {
            // Afficher une image par défaut ou gérer le cas où l'image est absente
        }

    }

}



