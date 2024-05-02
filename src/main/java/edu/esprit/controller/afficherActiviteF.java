package edu.esprit.controller;

import edu.esprit.entites.Activite;
import edu.esprit.entites.mailAct;
import edu.esprit.servies.ActiviteCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class afficherActiviteF {

    @FXML
    private ScrollPane scroll;
    private ObservableList<Activite> activitesList;
    @FXML
    private Button ajouterButton;
    @FXML
    private FlowPane activiteflow;

    // Déclaration de la liste d'offres


    // Méthode pour initialiser activitesList
    public void setActivitesList(ObservableList<Activite> activitesList) {
        this.activitesList = activitesList;
    }
    @FXML
    private int currentPage = 0;
    private int pageSize = 3;
    @FXML
    private VBox paginationContent;

    @FXML
    private Button precedentButton;

    @FXML
    private Button suivantButton;
    @FXML
    private TextField champRecherche; // Ajout du champ de recherche

    private ObservableList<Activite> listeFiltree;
    @FXML
    private Pane statPane; // Injection du Pane pour les statistiques
    @FXML
    private Button triButton;
    @FXML
    void initialize() {
        // Initialisez activitesList avec les données appropriées
        ActiviteCrud activityCrud = new ActiviteCrud();
        activitesList = FXCollections.observableArrayList(activityCrud.afficher());

        // Initialisez la liste filtrée avec toutes les activités au démarrage
        listeFiltree = FXCollections.observableArrayList(activitesList);

        // Configurer la fonctionnalité de recherche
        champRecherche.textProperty().addListener((observable, ancienneValeur, nouvelleValeur) -> {
            // Appel de la méthode de filtrage à chaque modification du texte de recherche
            filtrerActivites(nouvelleValeur);
            // Afficher les activités filtrées à partir de la première page
            afficherActivites(0);
        });
    }
    @FXML
    void handleAfficherActivites(ActionEvent event) {
        afficherActivites(currentPage);
    }
    //recehrche
    private void filtrerActivites(String texteRecherche) {
        // Effacer la liste filtrée actuelle
        listeFiltree.clear();
        // Si le texte de recherche est vide, ajouter toutes les activités à la liste filtrée
        if (texteRecherche.isEmpty()) {
            listeFiltree.addAll(activitesList);
        } else {
            // Convertir le texte de recherche en minuscules pour une comparaison insensible à la casse
            String rechercheEnMinuscules = texteRecherche.toLowerCase();
            // Parcourir toutes les activités
            for (Activite activite : activitesList) {
                // Obtenir le nom et la localisation de l'activité en minuscules
                String nom = activite.getNom().toLowerCase();
                String localisation = activite.getLocalisation().toLowerCase();
                // Vérifier si le nom ou la localisation de l'activité contient le texte de recherche
                if (nom.contains(rechercheEnMinuscules) || localisation.contains(rechercheEnMinuscules)) {
                    // Ajouter l'activité à la liste filtrée
                    listeFiltree.add(activite);
                }
            }
        }
    }
    @FXML
    void OnclickTrier(ActionEvent event) throws SQLException {
        ActiviteCrud serviceAct = new ActiviteCrud();
        List<Activite> coworkingListTrie = serviceAct.trierParPrix(activitesList);

        // Mettre à jour la liste filtrée et afficher la première page
        listeFiltree.setAll(coworkingListTrie);
        afficherActivites(0);
    }




    @FXML
    void previousPage(ActionEvent event) {
        if (currentPage > 0) {
            currentPage--;
            afficherActivites(currentPage);
        }
    }

    @FXML
    void nextPage(ActionEvent event) {
        int totalPages = (int) Math.ceil((double) activitesList.size() / pageSize);
        if (currentPage < totalPages - 1) {
            currentPage++;
            afficherActivites(currentPage);
        }
    }
    @FXML
    void afficherActivites() {
        // Appeler la méthode d'affichage en passant la page actuelle
        afficherActivites(currentPage);
    }
    @FXML
    void afficherActivites(int page) {
        try {
            // Créer un VBox pour contenir tous les éléments Iteam
            VBox mainVBox = new VBox();
            mainVBox.setSpacing(20.0); // Espacement vertical entre les lignes

            // Calculer les index de début et de fin pour la page spécifiée
            int startIndex = page * pageSize;
            int endIndex = Math.min(startIndex + pageSize, listeFiltree.size());

            // Créer une HBox pour chaque ligne d'éléments
            HBox hBox = new HBox();
            hBox.setSpacing(10.0); // Espacement horizontal entre les éléments

            // Ajouter chaque paire d'éléments à une ligne dans la HBox
            for (int i = startIndex; i < endIndex; i++) {
                Activite activite = listeFiltree.get(i);
                // Vérifier si l'état de l'activité est "accepter"
                if (activite.getEtat().equals("Acceptee")) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/IteamA.fxml"));
                    Node itemNode = loader.load();
                    IteamController controller = loader.getController();
                    controller.setData(activite, null);

                    // Ajouter l'élément à la ligne actuelle
                    hBox.getChildren().add(itemNode);
                }
            }
            // Ajouter la ligne actuelle au VBox principal
            mainVBox.getChildren().add(hBox);
            // Remplacer le contenu de paginationContent par le VBox principal
            paginationContent.getChildren().setAll(mainVBox);

            System.out.println("Chargement des éléments de la page " + page + " terminé avec succès.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ajouterButton.setVisible(true);
        precedentButton.setVisible(true);
        suivantButton.setVisible(true);
        champRecherche.setVisible(true);
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




