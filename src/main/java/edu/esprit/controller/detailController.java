package edu.esprit.controller;

import edu.esprit.entites.Activite;
import edu.esprit.entites.Categorie;
import edu.esprit.tests.MyListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;

public class detailController {
    @FXML
    private ImageView img;

    @FXML
    private Label locLabel;

    @FXML
    private Button map;

    @FXML
    private Label descLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label nbLabel;
    @FXML
    private Label nomLabel;
    @FXML
    private Label idLabel;

    private Activite activite;
    private Categorie categorie;
    private MyListener myListener;


    @FXML
    private void click(MouseEvent mouseEvent) {
        if (myListener != null) {
            myListener.onClickListener(activite);
        }
    }

    // Méthode pour définir les données de l'activité
    public void setDatadetail(Activite activite, MyListener myListener) {
        this.activite = activite;
        this.myListener = myListener;

        if (activite != null) {
            this.nomLabel.setText(activite.getNom());
            this.priceLabel.setText(activite.getPrix() + " DT/Personne");
            this.locLabel.setText(activite.getLocalisation());
            this.nbLabel.setText(activite.getNb_P() + " Participant");
            this.descLabel.setText(activite.getDescription_act());


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
        } else {
            // Gérer le cas où activite est null, par exemple, en effaçant les valeurs des labels et de l'image
            this.nomLabel.setText("");
            this.priceLabel.setText("");
            this.locLabel.setText("");
            this.nbLabel.setText("");
            this.descLabel.setText("");
            this.img.setImage(null); // Effacer l'image
        }

    }

}
