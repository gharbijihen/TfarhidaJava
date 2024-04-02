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
    private Label phoneLabel;

    @FXML
    private Label priceLable;
    private Logement logement;
    private MyListener myListener;

    @FXML
    private void click(MouseEvent mouseEvent) {
        this.myListener.onClickListener(this.logement);
    }

    public void setData(Logement logement, MyListener myListener) {
        this.logement = logement;
        this.myListener = myListener;
        this.nameLabel.setText(logement.getNom());
        this.priceLable.setText(logement.getPrix()+"DT/Personne" );
        this.loalisationLabel.setText(logement.getLocalisation());
        this.phoneLabel.setText("+216"+logement.getNum());
        String imagePath = logement.getImage();


        Image image = new Image(new File(imagePath).toURI().toString());
        this.img.setImage(image);
    }

}

