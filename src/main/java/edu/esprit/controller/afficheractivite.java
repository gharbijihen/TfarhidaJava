package edu.esprit.controller;

import edu.esprit.servies.ActiviteCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import edu.esprit.entites.Activite;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class afficheractivite{

    @FXML
    private TableView<Activite> tableView;

    @FXML
    private TableColumn<Activite, String> colNomm;

    @FXML
    private TableColumn<Activite, Integer> colCategorie;

    @FXML
    private TableColumn<Activite, Integer> colPrix;

    @FXML
    private TableColumn<Activite, String> colLocalisation;

    @FXML
    private TableColumn<Activite, Integer> colNbPersonnes;

    @FXML
    private TableColumn<Activite, String> colEtat;

    @FXML
    private TableColumn<Activite, String> colDescription;
    @FXML
    private final ActiviteCrud ps = new ActiviteCrud();
    @FXML
    private ScrollPane activityScrollPane; // Ajout du ScrollPane
    private ScrollPane logementScrollPane; // Ajout du ScrollPane

    @FXML
    private Button activiteButton; // Référence au bouton "Activité"
    private File selectedImageFile;
    @FXML
    private Button accepterButton;
    @FXML
    private Button refreshButton;
    @FXML
    private Button refuserButton;
    @FXML
    private TableColumn<Activite, Void> colAction;




/*
    public void initialize() throws SQLException {

       List<Activite> act = ps.afficher();
        ObservableList<Activite> observableList = FXCollections.observableList(act);
        tableView.setItems(observableList);
        // Configure les colonnes pour correspondre aux attributs de l'activité
        colNomm.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie_id"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colLocalisation.setCellValueFactory(new PropertyValueFactory<>("localisation"));
        colNbPersonnes.setCellValueFactory(new PropertyValueFactory<>("nb_P"));
        colEtat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description_act"));
        tableView.setVisible(false);
    }
*/
public void initialize() throws SQLException {
    List<Activite> act = ps.afficher();
    ObservableList<Activite> observableList = FXCollections.observableList(act);
    tableView.setItems(observableList);
    // Configure les colonnes pour correspondre aux attributs de l'activité
    colNomm.setCellValueFactory(new PropertyValueFactory<>("nom"));
    colCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie_id"));
    colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
    colLocalisation.setCellValueFactory(new PropertyValueFactory<>("localisation"));
    colNbPersonnes.setCellValueFactory(new PropertyValueFactory<>("nb_P"));
    colEtat.setCellValueFactory(new PropertyValueFactory<>("etat"));
    colDescription.setCellValueFactory(new PropertyValueFactory<>("description_act"));
    tableView.setStyle("-fx-background-color: #f2f2f2;");
    colAction.setCellFactory(cell -> new ActionCell());


    // Set styles for each TableColumn
    colNomm.setStyle("-fx-alignment: CENTER;");
    colCategorie.setStyle("-fx-alignment: CENTER;");
    colPrix.setStyle("-fx-alignment: CENTER;");
    colLocalisation.setStyle("-fx-alignment: CENTER;");
    colNbPersonnes.setStyle("-fx-alignment: CENTER;");
    colEtat.setStyle("-fx-alignment: CENTER;");
    colDescription.setStyle("-fx-alignment: CENTER;");
    colAction.setStyle("-fx-alignment: CENTER;");

    // Set preferred widths for the columns
    colNomm.setPrefWidth(150);
    colCategorie.setPrefWidth(100);
    colPrix.setPrefWidth(100);
    colLocalisation.setPrefWidth(150);
    colNbPersonnes.setPrefWidth(100);
    colEtat.setPrefWidth(100);
    colDescription.setPrefWidth(200);
    colAction.setPrefWidth(200);


    tableView.setVisible(true); // Rend la table visible par défaut
}

    @FXML
    private void afficherActivite() {
            tableView.setVisible(true);

    }
    @FXML
    void modifierActiviteAction(ActionEvent event) {
        // Récupérer l'activité sélectionnée dans le TableView
        Activite activiteSelectionnee = tableView.getSelectionModel().getSelectedItem();

        // Vérifier si une activité est sélectionnée
        if (activiteSelectionnee != null) {
            // Ouvrir la page de modification avec les données de l'activité sélectionnée
            openModifierActivitePage(activiteSelectionnee);
        } else {
            // Afficher un message d'erreur ou une boîte de dialogue indiquant à l'utilisateur de sélectionner une activité
        }
    }

    @FXML
    private void openModifierActivitePage(Activite activite) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/activiteModife.fxml"));
            Parent root = loader.load();
            ModifierActiviteController modifierController = loader.getController();
            modifierController.initData(activite, selectedImageFile); // Transmettre également le fichier d'image sélectionné
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteAction(ActionEvent event) {
        // Récupérer l'élément sélectionné dans le TableView
        Activite activiteSelectionnee = tableView.getSelectionModel().getSelectedItem();

        // Vérifier si un élément est sélectionné
        if (activiteSelectionnee != null) {
            // Afficher une boîte de dialogue de confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Supprimer l'activité sélectionnée");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer cette activité ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Supprimer l'élément de la liste ou de la base de données
                ActiviteCrud service = new ActiviteCrud();
                try {
                    service.supprimer(activiteSelectionnee);
                    // Rafraîchir le TableView après la suppression
                    tableView.getItems().remove(activiteSelectionnee);
                    System.out.println("Activité supprimée avec succès !");
                } catch (SQLException e) {
                    System.out.println("Erreur lors de la suppression de l'activité : " + e.getMessage());
                }
            }
        } else {
            // Afficher un message d'erreur ou une boîte de dialogue indiquant à l'utilisateur de sélectionner une activité à supprimer
        }
    }
    @FXML
    void handleAjouter(ActionEvent event) throws SQLException {
        try {
            // Charger la vue ou le formulaire d'ajout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/activiteAjout.fxml"));
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



