package edu.esprit.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import edu.esprit.entites.Moyen_transport;
import edu.esprit.tests.MyListener;
import javafx.scene.input.MouseEvent;

import java.io.File;

public class ItemMController {

    @FXML
    private ImageView img;

    @FXML
    public Label localisationLabel;

    @FXML
    private Label typeLabel;

    private Moyen_transport moyen_transport;

    private MyListener myListener;

    public ItemMController() {}

    @FXML
    private void click(MouseEvent mouseEvent) {
        if (myListener != null) {
            myListener.onClickListener(moyen_transport);
        }
    }

    public void setData(Moyen_transport moyen_transport, MyListener myListener) {
        this.moyen_transport = moyen_transport;
        this.myListener = myListener;
        this.typeLabel.setText(moyen_transport.getType());
        this.localisationLabel.setText(moyen_transport.getLieu());
        img.setFitWidth(300);
        img.setFitHeight(300);


        String imagePath = moyen_transport.getImage();
        if (imagePath != null) {
            // Charger l'image à partir du chemin d'accès spécifié
            Image image = new Image(new File(imagePath).toURI().toString());
            this.img.setImage(image);
        } else {
            // Afficher une image par défaut ou gérer le cas où l'image est absente
        }

    }

}