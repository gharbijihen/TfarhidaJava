package edu.esprit.controller;

import edu.esprit.entites.Moyen_transport;
import edu.esprit.servies.Moyen_transportCrud;
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

public class AfficherMoyF {

    @FXML
    private ScrollPane scroll;
    private ObservableList<Moyen_transport> moyensList;
    @FXML
    private Button ajouterButton;

    // Méthode pour initialiser activitesList
    public void setMoyensList(ObservableList<Moyen_transport> moyensList) {
        this.moyensList = moyensList;
    }

    @FXML
    void afficherMoyens() {
        try {
            // Créer un VBox pour contenir tous les éléments Item
            VBox mainVBox = new VBox();
            mainVBox.setSpacing(20.0); // Espacement vertical entre les lignes

            // Récupérer la liste des moyens depuis la base de données
            Moyen_transportCrud crud = new Moyen_transportCrud();
            List<Moyen_transport> moyensList = crud.afficher();

            // Créer une HBox pour chaque ligne d'éléments
            HBox hBox = new HBox();
            hBox.setSpacing(20.0); // Espacement horizontal entre les éléments

            // Ajouter chaque paire d'éléments à une ligne dans la HBox
            for (Moyen_transport moyen : moyensList) {
                // Vérifier si le moyen est valide avant de l'ajouter à l'affichage
                if (moyen.isValide()) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ItemM.fxml"));
                    Node itemNode = loader.load();
                    ItemMController controller = loader.getController();
                    controller.setData(moyen, null);

                    // Ajouter l'élément à la ligne actuelle
                    hBox.getChildren().add(itemNode);

                    // Si la ligne est pleine (2 éléments), l'ajouter au VBox principal et créer une nouvelle ligne
                    if (hBox.getChildren().size() == 2) {
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
            // Handle the exception appropriately (e.g., show an error message to the user)
            e.printStackTrace();
        }
    }
}
