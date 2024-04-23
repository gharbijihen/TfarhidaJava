package edu.esprit.controller;

import edu.esprit.entites.Logement;
import edu.esprit.servies.LogementCrud;
import edu.esprit.tests.MyListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.awt.*;
import javafx.scene.control.Button;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class afficherLogement  {
    private List<Logement> logements;
    private MyListener myListener;

    @FXML
    private ScrollPane scroll;
    private ObservableList<Logement> logementsList;

    @FXML
    private GridPane grid;

    @FXML
    private HBox vboxdash;
    @FXML
    private Button ajouterButton;

    private LogementCrud serviceLogement = new LogementCrud();

    // Méthode pour initialiser activitesList
    public void setLogementsList(ObservableList<Logement> logementsList) {
        this.logementsList = logementsList;
    }



    public void showLogement() {

     try {
        // Créer un VBox pour contenir tous les éléments Iteam
        VBox mainVBox = new VBox();
        mainVBox.setSpacing(20.0); // Espacement vertical entre les lignes

        // Récupérer la liste des activités depuis la base de données
       LogementCrud logementCrud = new LogementCrud();
        List<Logement> logementsList =logementCrud.afficher();

        // Créer une HBox pour chaque ligne d'éléments
        HBox hBox = new HBox();
        hBox.setSpacing(20.0); // Espacement horizontal entre les éléments

        // Ajouter chaque paire d'éléments à une ligne dans la HBox
        for (Logement logement : logementsList) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/IteamLogement.fxml"));
            Node itemNode = loader.load();
            IteamLogementController controller = loader.getController();
            controller.setData(logement, null);

            // Ajouter l'élément à la ligne actuelle
            hBox.getChildren().add(itemNode);

            // Si la ligne est pleine (2 éléments), l'ajouter au VBox principal et créer une nouvelle ligne
            if (hBox.getChildren().size() == 2) {
                mainVBox.getChildren().add(hBox);
                hBox = new HBox();
                hBox.setSpacing(20.0); // Réinitialiser l'espacement horizontal pour la nouvelle ligne
            }
        }

        // Ajouter la dernière ligne si elle n'est pas pleine
        if (!hBox.getChildren().isEmpty()) {
            mainVBox.getChildren().add(hBox);
        }

        // Définir le contenu du ScrollPane comme le VBox principal
        scroll.setContent(mainVBox);

        System.out.println("Chargement des éléments terminé avec succès.");

    } catch (IOException e) {
        e.printStackTrace();
    }
        ajouterButton.setVisible(true);

}
    @FXML


    public void handleAjouterLogement(ActionEvent event) {
        try {
            // Charger la vue ou le formulaire d'ajout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogementAjouter.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle fenêtre pour afficher le formulaire d'ajout
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

