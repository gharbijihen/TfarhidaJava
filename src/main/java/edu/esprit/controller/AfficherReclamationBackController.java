package edu.esprit.controller;

import edu.esprit.entites.Reclamation;
import edu.esprit.servies.ReclamationCrud;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.List;

public class AfficherReclamationBackController {

    @FXML
    private ListView<Reclamation> reclamationListView;

    private final ReclamationCrud reclamationCrud = new ReclamationCrud();

    public void initialize() {
        displayReclamations();
        setCellFactory();
    }

    private void displayReclamations() {
        List<Reclamation> reclamations = reclamationCrud.afficher();
        reclamationListView.getItems().addAll(reclamations);
    }

    private void setCellFactory() {
        reclamationListView.setCellFactory(listView -> new ListCell<Reclamation>() {
            @Override
            protected void updateItem(Reclamation reclamation, boolean empty) {
                super.updateItem(reclamation, empty);
                if (empty || reclamation == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(reclamation.getTitre());
                    // Ajoutez d'autres éléments de la cellule ici selon vos besoins
                }
            }
        });
    }
}
