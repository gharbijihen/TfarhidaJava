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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;


import java.io.IOException;
import java.util.List;

public class AfficherMoyF {

    @FXML
    private ScrollPane scroll;
    private ObservableList<Moyen_transport> moyensList;
    @FXML
    private Button ajouterButton;
    // Pagination parameters
    private static final int ITEMS_PER_PAGE = 2;
    private int currentPageIndex = 0;

    // Méthode pour initialiser activitesList
    public void setMoyensList(ObservableList<Moyen_transport> moyensList) {
        this.moyensList = moyensList;
    }





    @FXML
    public void afficherMoyens() {
        Moyen_transportCrud activityCrud = new Moyen_transportCrud();
        moyensList = FXCollections.observableArrayList(activityCrud.afficher());

        // Afficher les activités filtrées à partir de la première page

        try {
            // Calculate the range of items to display for the current page
            int fromIndex = currentPageIndex * ITEMS_PER_PAGE;
            int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, moyensList.size());

            // Create a VBox to contain all item elements
            VBox mainVBox = new VBox();
            mainVBox.setSpacing(20.0); // Vertical spacing between rows

            // Create a HBox for each row of elements
            HBox hBox = new HBox();
            hBox.setSpacing(20.0); // Horizontal spacing between elements

            // Add each pair of elements to a row in the HBox
            for (int i = fromIndex; i < toIndex; i++) {
                Moyen_transport moyen = moyensList.get(i);
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
    void handleAjouter(ActionEvent event) {
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

    public void gotodash(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminDashbord.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page
            Scene scene = new Scene(root);

            // Obtenir la fenêtre actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène sur la fenêtre et l'afficher
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
