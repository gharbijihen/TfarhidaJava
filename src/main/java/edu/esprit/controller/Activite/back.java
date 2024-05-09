package edu.esprit.controller.Activite;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class back {

    @FXML
    private HBox contentHBox;

    @FXML
    private void goToafficher() {
        try {
            // Charger le contenu de afficherActivite.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ActiviteFxml/afficherActivie.fxml"));
            Node afficherActiviteContent = loader.load();

            // Ajouter le contenu au contentHBox
            contentHBox.getChildren().clear(); // Efface tout contenu précédent
            contentHBox.getChildren().add(afficherActiviteContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void goToafficherCat(){
        try {
            // Charger le contenu de afficherActivite.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ActiviteFxml/afficherCategorie.fxml"));
            Node afficherActiviteContent = loader.load();

            // Ajouter le contenu au contentHBox
            contentHBox.getChildren().clear(); // Efface tout contenu précédent
            contentHBox.getChildren().add(afficherActiviteContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


