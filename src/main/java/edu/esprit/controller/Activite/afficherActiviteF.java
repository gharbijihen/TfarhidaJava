package edu.esprit.controller.Activite;

import edu.esprit.controller.RouterController;
import edu.esprit.entites.Activite;
import edu.esprit.servies.ActiviteCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class afficherActiviteF {

    @FXML
    private ScrollPane scroll;
    private ObservableList<Activite> activitesList;
    @FXML
    private Button ajouterButton;
    @FXML
    private FlowPane activiteflow;
    @FXML
    private WebView mapWebView;

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
    private Button chatbot;

    @FXML
    private Pane chat;
    @FXML
    private Label trie;
    @FXML
    private Label loupe;
    @FXML
    private Label sb;
    private void loadMap(String adresse) {
        // Générer l'URL de la carte en remplaçant les espaces par "%20" dans l'adresse
        String mapUrl = "https://maps.google.com/maps?q=" + adresse.replace(" ", "%20") + "&output=embed";

        // Charger l'URL de la carte dans la WebView
        WebEngine webEngine = mapWebView.getEngine();
        webEngine.loadContent(generateMapHtml(mapUrl));
    }

    private String generateMapHtml(String adresse) {
        // Générer l'URL de la carte en remplaçant les espaces par "%20" dans l'adresse
        String mapUrl = "https://maps.google.com/maps?q=" + adresse.replace(" ", "%20") + "&output=embed";

        // Generate HTML content with the correct map URL
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Google Maps Example</title>\n" +
                "    <style>\n" +
                "        /* Adjust the size and position of the map */\n" +
                "        #mapouter {\n" +
                "            position: relative;\n" +
                "            text-align: right;\n" +
                "            height: 500px; /* Adjust the height as needed */\n" +
                "            width: 500px; /* Adjust the width as needed */\n" +
                "        }\n" +
                "\n" +
                "        #gmap_canvas2 {\n" +
                "            overflow: hidden;\n" +
                "            background: none !important;\n" +
                "            height: 500px; /* Adjust the height as needed */\n" +
                "            width: 500px; /* Adjust the width as needed */\n" +
                "        }\n" +
                "\n" +
                "        #gmap_canvas {\n" +
                "            width: 100%;\n" +
                "            height: 100%;\n" +
                "            border: 0;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div id=\"mapouter\">\n" +
                "    <div id=\"gmap_canvas2\">\n" +
                "        <iframe id=\"gmap_canvas\"\n" +
                "                src=\"" + mapUrl + "\" frameborder=\"0\" scrolling=\"no\" marginheight=\"0\" marginwidth=\"0\"></iframe>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
    }

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

        // Appeler la méthode d'affichage en passant la page actuelle
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
        List<Activite> activiteListTrie = serviceAct.trierParPrix(activitesList);

        // Mettre à jour la liste filtrée et afficher la première page
        listeFiltree.setAll(activiteListTrie);
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

            // Filtrer les activités acceptées uniquement
            List<Activite> activitesAcceptees = listeFiltree.filtered(a -> a.getEtat().equals("Acceptee"));

            // Calculer les index de début et de fin pour la page spécifiée
            int startIndex = page * pageSize;
            int endIndex = Math.min(startIndex + pageSize, activitesAcceptees.size());

            // Créer une HBox pour chaque ligne d'éléments
            HBox hBox = new HBox();
            hBox.setSpacing(10.0); // Espacement horizontal entre les éléments

            // Ajouter chaque paire d'éléments à une ligne dans la HBox
            for (int i = startIndex; i < endIndex; i++) {
                Activite activite = activitesAcceptees.get(i);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ActiviteFxml/IteamA.fxml"));
                Node itemNode = loader.load();
                edu.esprit.controller.Activite.IteamController controller = loader.getController();
                controller.setData(activite, null);

                // Ajouter l'élément à la ligne actuelle
                hBox.getChildren().add(itemNode);
            }

            // Ajouter la ligne actuelle au VBox principal
            mainVBox.getChildren().add(hBox);

            // Remplacer le contenu de paginationContent par le VBox principal
            paginationContent.getChildren().setAll(mainVBox);

            // Désactiver le bouton suivant s'il n'y a plus de pages suivantes
            suivantButton.setDisable(endIndex >= activitesAcceptees.size());

            System.out.println("Chargement des éléments de la page " + page + " terminé avec succès.");
        } catch (IOException e) {
            e.printStackTrace();

        }
        ajouterButton.setVisible(true);
        precedentButton.setVisible(true);
        suivantButton.setVisible(true);
        champRecherche.setVisible(true);
        triButton.setVisible(true);
        chatbot.setVisible(true);
        chat.setVisible(true);
        trie.setVisible(true);
        loupe.setVisible(true);
        sb.setVisible(true);
    }


    public void openChatbotPopup(ActionEvent actionEvent) {
        try {
            // Load the FXML file for the chatbot popup
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ActiviteFxml/ChatBotPopup.fxml"));
            Parent root = loader.load();

            // Create a new stage for the chatbot popup
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Chatbot");
            stage.setScene(new Scene(root));

            // Show the chatbot popup
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void handleAjouter(ActionEvent event) {
        try {
            // Charger la vue ou le formulaire d'ajout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ActiviteFxml/ajouterCrudF.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle fenêtre pour afficher le formulaire d'ajout
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void goToNavigate(ActionEvent event) {
        RouterController.navigate("/fxml/ClientDashboard.fxml");
    }

    public void gotoAdmin(MouseEvent mouseEvent) {
        RouterController.navigate("/fxml/AdminDashboard.fxml");
    }



}