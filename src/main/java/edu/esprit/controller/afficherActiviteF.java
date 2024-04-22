package edu.esprit.controller;

import edu.esprit.entites.Activite;
import edu.esprit.servies.ActiviteCrud;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class afficherActiviteF {

    @FXML
    private ScrollPane scroll;
    private ObservableList<Activite> activitesList;
    @FXML
    private Button ajouterButton;

    // Méthode pour initialiser activitesList
    public void setActivitesList(ObservableList<Activite> activitesList) {
        this.activitesList = activitesList;
    }


    @FXML
    void afficherActivites() {
        try {
            // Créer un VBox pour contenir tous les éléments Iteam
            VBox mainVBox = new VBox();
            mainVBox.setSpacing(20.0); // Espacement vertical entre les lignes

            // Récupérer la liste des activités depuis la base de données
            ActiviteCrud activityCrud = new ActiviteCrud();
            List<Activite> activitesList = activityCrud.afficher();

            // Créer une HBox pour chaque ligne d'éléments
            HBox hBox = new HBox();
            hBox.setSpacing(10.0); // Espacement horizontal entre les éléments

            // Ajouter chaque paire d'éléments à une ligne dans la HBox
            for (Activite activite : activitesList) {
                // Vérifier si l'état de l'activité est "accepter"
                if (activite.getEtat().equals("Acceptee")) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/IteamA.fxml"));
                    Node itemNode = loader.load();
                    IteamController controller = loader.getController();
                    controller.setData(activite, null);

                    // Ajouter l'élément à la ligne actuelle
                    hBox.getChildren().add(itemNode);

                    // Si la ligne est pleine (2 éléments), l'ajouter au VBox principal et créer une nouvelle ligne
                    if (hBox.getChildren().size() == 3) {
                        mainVBox.getChildren().add(hBox);
                        hBox = new HBox();
                        hBox.setSpacing(20.0); // Réinitialiser l'espacement horizontal pour la nouvelle ligne
                    }
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
    void handleAjouter(ActionEvent event) {
        try {
            // Charger la vue ou le formulaire d'ajout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterCrudF.fxml"));
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
    /*@FXML
    void afficherActivites() {
        try {
            // Charger le contenu de afficherActivite.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Iteam.fxml"));
            Node afficherActiviteContent = loader.load();

            // Définir le contenu du ScrollPane
            scroll.setContent(afficherActiviteContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/



