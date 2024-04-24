
package edu.esprit.controller;

        import edu.esprit.entites.Reclamation;
        import edu.esprit.servies.ReclamationCrud;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Node;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.control.ListView;
        import javafx.scene.layout.HBox;
        import javafx.scene.layout.VBox;

        import java.awt.*;
        import java.io.IOException;
        import java.sql.SQLException;
        import java.util.List;

public class AfficherReclamationFrontController  {

    @FXML
    private ListView<String> reclamationListView;

    @FXML
    private VBox cardsContainer;

    private final ReclamationCrud reclamationCrud = new ReclamationCrud();

    public void initialize() {
       // displayReclamations();
        displayList();
    }

    private void displayReclamations() {
        List<Reclamation> reclamations = reclamationCrud.afficher();
        for (Reclamation reclamation : reclamations) {
            String reclamationDescription = reclamation.getId() + " - " + reclamation.getTitre();
            reclamationListView.getItems().add(reclamationDescription);

            VBox card = new VBox();
            card.getStyleClass().add("card");
            Label reclamationLabel = new Label(reclamationDescription);
            Button deleteButton = new Button("Delete");
            Button editButton = new Button("Edit");

            deleteButton.setOnAction(event -> {
                try {
                    reclamationCrud.supprimer(reclamation);
                    displayReclamations(); // Actualiser l'affichage après suppression
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            editButton.setOnAction(event -> {
                // Mettez en place la logique pour l'édition de la réclamation ici
                System.out.println("Edit button clicked for reclamation: " + reclamation.getId());
            });

            HBox buttonBox = new HBox(deleteButton, editButton);
            buttonBox.getStyleClass().add("button-box");
            card.getChildren().addAll(reclamationLabel, buttonBox);
            cardsContainer.getChildren().add(card);
        }
    }

    @FXML
    private void addReclamation() {
        // Mettez en place la logique pour l'ajout de la réclamation ici
        System.out.println("Add Reclamation button clicked");
    }

    private void displayList(){
        try {
            List<Reclamation> reclamations = reclamationCrud.afficher();
            for (Reclamation reclamation : reclamations) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/component/card_rec.fxml"));
                Node content = loader.load();
                cardsContainer.getChildren().add(content);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
