package edu.esprit.controller;

import edu.esprit.entites.Activite;
import edu.esprit.servies.ActiviteCrud;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.sql.SQLException;

public class ActionCell extends TableCell<Activite, Void> {
    private final Button acceptButton;
    private final Button refuseButton;
    private ActiviteCrud activiteCrud = new ActiviteCrud(); // Ajout d'une référence à LogementCrud

    public ActionCell() {
        this.activiteCrud = activiteCrud; // Initialisation de la référence à LogementCrud
        acceptButton = createButton ("Accepter", "#4CAF50");
        refuseButton = createButton("Refuser","#f44336");

        acceptButton.setOnAction(event -> {
            Activite activite = getTableView().getItems().get(getIndex());
            // Mettre à jour l'état du logement en acceptation
            activite.setEtat("Acceptee");
            // Mettre à jour la ligne de la table
            getTableView().refresh();
            // Enregistrer les modifications dans la base de données
            try {
                activiteCrud.modifier(activite);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        refuseButton.setOnAction(event -> {
            Activite activite = getTableView().getItems().get(getIndex());
            // Mettre à jour l'état du logement en refus
            activite.setEtat("Refuse");
            // Mettre à jour la ligne de la table
            getTableView().refresh();
            // Enregistrer les modifications dans la base de données
            try {
                activiteCrud.modifier(activite);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {
            // Afficher les boutons uniquement si l'état est "En attente"
            Activite activite = getTableView().getItems().get(getIndex());
            if ("Acceptee".equals(activite.getEtat()) || ("Refuse".equals(activite.getEtat() )||"en cours".equals(activite.getEtat()))) {
                setGraphic(new HBox(acceptButton, refuseButton));
            } else {
                setGraphic(null);
            }
        }
    }
    private Button createButton(String text, String backgroundColor) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + backgroundColor + "; -fx-text-fill: white;");
        button.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        return button;
    }
}


