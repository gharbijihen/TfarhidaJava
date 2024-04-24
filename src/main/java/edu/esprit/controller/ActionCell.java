package edu.esprit.controller;

import edu.esprit.entites.Moyen_transport;
import edu.esprit.servies.Moyen_transportCrud;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.sql.SQLException;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;



public class ActionCell extends TableCell<Moyen_transport, Void>  {
    private final Button acceptButton;
    private final Button refuseButton;
    private Moyen_transportCrud moyenCrud = new Moyen_transportCrud(); // Ajout d'une référence à LogementCrud

    public ActionCell() {
        acceptButton = createButton ("Accepter", "#4CAF50");
        refuseButton = createButton("Refuser","#f44336");

        acceptButton.setOnAction(event -> {
            Moyen_transport moyen = getTableView().getItems().get(getIndex());
            // Mettre à jour l'état du logement en acceptation
            moyen.setValide(true);
            // Mettre à jour la ligne de la table
            getTableView().refresh();
            // Enregistrer les modifications dans la base de données
            try {
                moyenCrud.modifier(moyen);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        refuseButton.setOnAction(event -> {
            Moyen_transport moyen = getTableView().getItems().get(getIndex());
            // Mettre à jour l'état du logement en refus
            moyen.setValide(false);
            // Mettre à jour la ligne de la table
            getTableView().refresh();
            // Enregistrer les modifications dans la base de données
            try {
                moyenCrud.modifier(moyen);
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
            Moyen_transport moyen = getTableView().getItems().get(getIndex());
            setGraphic(new HBox(acceptButton, refuseButton));

    }}



    private Button createButton(String text, String backgroundColor) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + backgroundColor + "; -fx-text-fill: white;");
        button.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        return button;
    }
}