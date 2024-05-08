package edu.esprit.controller;

import edu.esprit.entites.Moyen_transport;
import edu.esprit.servies.Moyen_transportCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AfficherMoyF {

    @FXML
    private ScrollPane scroll;
    private ObservableList<Moyen_transport> moyensList;
    @FXML
    private Button ajouterButton;

    @FXML
    private Button suivantButton;

    @FXML
    private Button precedentButton;
    // Pagination parameters
    private static final int ITEMS_PER_PAGE = 3;
    private int currentPageIndex = 0;
    @FXML
    private TextField searchField;

    // Méthode pour initialiser activitesList
    public void setMoyensList(ObservableList<Moyen_transport> moyensList) {
        this.moyensList = moyensList;
    }



    public void afficherMoyens() {
        Moyen_transportCrud activityCrud = new Moyen_transportCrud();
        moyensList = FXCollections.observableArrayList(activityCrud.afficher());
        searchField.textProperty().addListener((observable, oldValue, newValue) -> searchMoyens(newValue));

        displayMoyens(moyensList);
    }

    private void searchMoyens(String query) {
        ObservableList<Moyen_transport> filteredList = FXCollections.observableArrayList();

        for (Moyen_transport moyen : moyensList) {
            if (moyen.getType().toLowerCase().contains(query.toLowerCase()) ||
                    moyen.getLieu().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(moyen);
            }
        }

        displayMoyens(filteredList);
    }

    private void displayMoyens(ObservableList<Moyen_transport> moyens) {
        try {
            // Calculate the range of items to display for the current page
            int fromIndex = currentPageIndex * ITEMS_PER_PAGE;
            int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, moyens.size());

            // Create a VBox to contain all item elements
            VBox mainVBox = new VBox();
            mainVBox.setSpacing(20.0); // Vertical spacing between rows

            // Create a HBox for each row of elements
            HBox hBox = new HBox();
            hBox.setSpacing(20.0); // Horizontal spacing between elements

            // Add each pair of elements to a row in the HBox
            for (int i = fromIndex; i < toIndex; i++) {
                Moyen_transport moyen = moyens.get(i);
                // Check if the moyen is valid before adding it to the display
                if (moyen.isValide()) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ItemM.fxml"));
                    Node itemNode = loader.load();
                    ItemMController controller = loader.getController();
                    controller.setData(moyen, null);
                    hBox.getChildren().add(itemNode);

                    // If the row is full (2 elements), add it to the main VBox and create a new row
                    if (hBox.getChildren().size() == 3) {
                        mainVBox.getChildren().add(hBox);
                        hBox = new HBox();
                        hBox.setSpacing(20.0); // Reset horizontal spacing for the new row
                    }
                }
            }

            // Add the last row if it's not full
            if (!hBox.getChildren().isEmpty()) {
                mainVBox.getChildren().add(hBox);
            }

            // Set the content of the ScrollPane as the main VBox
            scroll.setContent(mainVBox);

            System.out.println("Loading items completed successfully.");

            suivantButton.setDisable(toIndex >= moyens.size());
            precedentButton.setDisable(fromIndex == 0);



        } catch (IOException e) {
            e.printStackTrace();
        }
        ajouterButton.setVisible(true);
    }

    @FXML
    private void navigateToPreviousPage() {
        if (currentPageIndex > 0) {
            currentPageIndex--;
            afficherMoyens();
        }
    }

    @FXML
    private void navigateToNextPage() {
        int totalPages = (int) Math.ceil((double) moyensList.size() / ITEMS_PER_PAGE);
        if (currentPageIndex < totalPages - 1) {
            currentPageIndex++;
            afficherMoyens();
        }
    }

    @FXML
    void handleAjouter() {
        try {
            // Charger la vue ou le formulaire d'ajout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterMoyen.fxml"));
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

    @FXML
    void gotodash() {
        try {
            // Charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminDashbord.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page
            Scene scene = new Scene(root);

            // Obtenir la fenêtre actuelle à partir de l'événement
            Stage stage = (Stage) searchField.getScene().getWindow();

            // Définir la nouvelle scène sur la fenêtre et l'afficher
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openWeatherPage(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page weather.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Weather.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec le contenu de weather.fxml
            Scene scene = new Scene(root);

            // Créer une nouvelle fenêtre pour afficher la scène
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Weather Information");

            // Afficher la nouvelle fenêtre
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
