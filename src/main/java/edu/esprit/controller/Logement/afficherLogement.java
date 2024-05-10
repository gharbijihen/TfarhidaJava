package edu.esprit.controller.Logement;

import Controllers.RouterController;
import edu.esprit.entites.Logement;
import edu.esprit.servies.LogementCrud;
import edu.esprit.tests.MyListener;
import edu.esprit.tools.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class afficherLogement  {
    private List<Logement> logements;
    private MyListener myListener;
    @FXML
    private AnchorPane bord;
    @FXML
    private ScrollPane scroll;
    private ObservableList<Logement> logementsList;

    @FXML
    private GridPane grid;
    @FXML
    private ComboBox<String> filtreTypeLog;

    ObservableList <String> typeList = FXCollections.observableArrayList(Data.typeLogement);

    public void fillfiltreTypeLog()
    {
        filtreTypeLog.setItems(typeList);
    }

    private int currentPage = 0;
    private int itemsPerPage = 3;
    // Adjust this based on your preference
    @FXML
    private Button prevPageBtn; // Pagination previous page button
    @FXML
    private Button nextPageBtn;
    @FXML
    private HBox paginationContainer; // Container for pagination controls
    private int totalItems;
    @FXML
    private Button ajouterButton;
    @FXML
    private RadioButton hotelRadioButton;

    @FXML
    private RadioButton villaRadioButton;

    private LogementCrud serviceLogement = new LogementCrud();

    // Méthode pour initialiser activitesList
    public void setLogementsList(ObservableList<Logement> logementsList) {
        this.logementsList = logementsList;
    }

    @FXML
    private void returnTo(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Home.fxml"));
        try {
            Parent root = loader.load();
            bord.getChildren().setAll(root);

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    @FXML
    public void initialize() {
        // Autres initialisations...
        ObservableList <String> typeList = FXCollections.observableArrayList(Data.typeLogement);
        filtreTypeLog.setItems(typeList);
        // Ajouter un gestionnaire d'événements à la ComboBox pour détecter les changements de sélection
        filtreTypeLog.setOnAction(event -> handleTypeSelection());
    }

    private void handleTypeSelection() {

        String selectedType = filtreTypeLog.getValue(); // Récupérer le type de logement sélectionné
        if (selectedType != null && !selectedType.isEmpty()) {
            // Effectuer une action en fonction du type sélectionné, par exemple, afficher les logements de ce type
            // Vous pouvez appeler une méthode de votre service LogementCrud pour récupérer et afficher les logements de ce type
            ObservableList<Logement> logements = LogementCrud.getAllByType(selectedType);
            afficherLogements(logements); // Mettre à jour l'affichage avec les logements du type sélectionné
        } else {
            // Gérer le cas où aucun type n'est sélectionné
            System.out.println("Veuillez sélectionner un type de logement.");
        }
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
                if (logement.getEtat().equals("Acceptee")) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogementFxml/IteamLogement.fxml"));
                    Node itemNode = loader.load();
                    IteamLogementController controller = loader.getController();
                    controller.setData(logement, null);

                    // Ajouter l'élément à la ligne actuelle
                    hBox.getChildren().add(itemNode);

                    // Si la ligne est pleine (2 éléments), l'ajouter au VBox principal et créer une nouvelle ligne
                    if (hBox.getChildren().size() == 3) {
                        mainVBox.getChildren().add(hBox);
                        hBox = new HBox();
                        hBox.setSpacing(20.0); // Réinitialiser l'espacement horizontal pour la nouvelle ligne
                    }
                }}

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogementFxml/LogementAjouter.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle fenêtre pour afficher le formulaire d'ajout
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void OnclickTrier(ActionEvent event) {
        // Appeler la méthode de tri par prix de votre service LogementCrud
        try {
            ObservableList<Logement> logementsTri = serviceLogement.trierParPrix(logementsList);
            afficherLogements(logementsTri); // Mettre à jour l'affichage avec la nouvelle liste triée
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public void afficherLogements(ObservableList<Logement> logements) {


        showLogement();
    }

    private void setupPaginationControls() {
        paginationContainer.getChildren().clear(); // Clear existing controls

        // Add the Previous button
        prevPageBtn.setDisable(currentPage == 0);
        paginationContainer.getChildren().add(prevPageBtn);

        // Calculate the number of pages
        int pageCount = (int) Math.ceil((double) totalItems / itemsPerPage);

        // Add page number buttons dynamically
        for (int i = 0; i < pageCount; i++) {
            Button pageBtn = new Button(String.valueOf(i + 1));
            int finalI = i;
            pageBtn.setOnAction(e -> {
                currentPage = finalI;
                setupPaginationControls(); // Refresh pagination controls on click
            });
            pageBtn.setDisable(currentPage == i); // Disable the button of the current page
            paginationContainer.getChildren().add(pageBtn);
        }

        // Add the Next button
        nextPageBtn.setDisable(currentPage >= pageCount - 1);
        paginationContainer.getChildren().add(nextPageBtn);
    }




    public void chatBotAction(ActionEvent event) {
        try {
            // Charger la vue ou le formulaire d'ajout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogementFxml/chatBot.fxml"));
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
        edu.esprit.controller.RouterController.navigate("/fxml/ClientDashboard.fxml");
    }
}