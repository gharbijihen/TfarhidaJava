package edu.esprit.controller;

import edu.esprit.entites.Logement;
import edu.esprit.servies.LogementCrud;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ActionCell extends TableCell<Logement, Void> {
    private final Button acceptButton;
    private final Button refuseButton;
    private LogementCrud logementCrud = new LogementCrud(); // Ajout d'une référence à LogementCrud

    public ActionCell() {
        this.logementCrud = logementCrud; // Initialisation de la référence à LogementCrud
        acceptButton = createButton ("Accepter", "#4CAF50");
        refuseButton = createButton("Refuser","#f44336");

        acceptButton.setOnAction(event -> {
            Logement logement = getTableView().getItems().get(getIndex());
            // Mettre à jour l'état du logement en acceptation
            logement.setEtat("Acceptee");
            // Mettre à jour la ligne de la table
            getTableView().refresh();
            // Enregistrer les modifications dans la base de données
            logementCrud.modifier(logement);
        });

        refuseButton.setOnAction(event -> {
            Logement logement = getTableView().getItems().get(getIndex());
            // Mettre à jour l'état du logement en refus
            logement.setEtat("Refuse");
            // Mettre à jour la ligne de la table
            getTableView().refresh();
            // Enregistrer les modifications dans la base de données
            logementCrud.modifier(logement);
        });
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {
            // Afficher les boutons uniquement si l'état est "En attente"
            Logement logement = getTableView().getItems().get(getIndex());
            if ("Acceptee".equals(logement.getEtat()) || ("Refuse".equals(logement.getEtat() )||"en cours".equals(logement.getEtat()))) {
                setGraphic(new HBox(acceptButton, refuseButton));
            } else {
                setGraphic(null);
            }
        }
    }
    private Button createButton(String text,  String backgroundColor) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + backgroundColor + "; -fx-text-fill: white;");
        button.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        return button;
    }
}
