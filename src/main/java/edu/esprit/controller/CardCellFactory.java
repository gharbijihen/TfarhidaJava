package edu.esprit.controller;

import edu.esprit.entites.Reclamation;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.scene.layout.VBox;

public class CardCellFactory implements Callback<ListView<Reclamation>, ListCell<Reclamation>> {

    @Override
    public ListCell<Reclamation> call(ListView<Reclamation> param) {
        return new ListCell<Reclamation>() {
            @Override
            protected void updateItem(Reclamation reclamation, boolean empty) {
                super.updateItem(reclamation, empty);

                if (empty || reclamation == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    ImageView imageView = new ImageView(new Image(reclamation.getImage()));
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);

                    setText(reclamation.getTitre() + " - " + reclamation.getDate().toString());

                    VBox vbox = new VBox(10);
                    vbox.getChildren().addAll(imageView, new Label(reclamation.getDescription_reclamation()));

                    setGraphic(vbox);
                }
            }
        };
    }
}