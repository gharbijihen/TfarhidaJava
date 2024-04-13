package edu.esprit.controller;



import edu.esprit.entites.Activite;
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
        private Label nbpLabel;

        @FXML
        private Label priceLable;
        private Activite activite;
        private MyListener myListener;

        @FXML
        private void click(MouseEvent mouseEvent) {
            this.myListener.onClickListener(this.activite);
        }

        public void setData(Activite activite, MyListener myListener) {
            this.activite = activite;
            this.myListener = myListener;
            this.nameLabel.setText(activite.getNom());
            this.priceLable.setText(activite.getPrix()+"DT/Personne" );
            this.loalisationLabel.setText(activite.getLocalisation());
            this.nbpLabel.setText(activite.getNb_P()+"Participant");
            String imagePath = activite.getImage();
            Image image = new Image(new File(imagePath).toURI().toString());
            this.img.setImage(image);
        }

    }

