package edu.esprit.controller;

import edu.esprit.entites.Equipement;
import edu.esprit.entites.Logement;
import edu.esprit.servies.EquipementCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class AfficherEquipementB {
    @FXML
    private HBox contentHBox;
    @FXML
    private TableView<Equipement> tableView;
    @FXML
    private TableColumn<Equipement, Boolean> Climatisation;

    @FXML
    private TableColumn<Equipement, String> Description;

    @FXML
    private TableColumn<Equipement, Boolean> Internet;

    @FXML
    private TableColumn<Equipement, Integer> NbrChambre;

    @FXML
    private TableColumn<Equipement, Boolean> Parking;

    @FXML
    private TableColumn<Equipement, String> TypeChambre;

    @FXML
    private ScrollPane logementScrollPane;


    @FXML
    private final EquipementCrud ps = new EquipementCrud();

    private List<Equipement> equipements = ps.afficher();
    private ObservableList<Equipement> observableList = FXCollections.observableArrayList(equipements);;
    // Dans afficherLogementB.java

    public void initialize() {

                tableView.setItems(observableList);
            // Initialisez les cellules de la TableView avec les PropertyValueFactory
            Parking.setCellValueFactory(new PropertyValueFactory<>("parking"));
            Internet.setCellValueFactory(new PropertyValueFactory<>("internet"));
            Climatisation.setCellValueFactory(new PropertyValueFactory<>("climatisation"));
            Description.setCellValueFactory(new PropertyValueFactory<>("description"));
            NbrChambre.setCellValueFactory(new PropertyValueFactory<>("nbr_chambre"));
            TypeChambre.setCellValueFactory(new PropertyValueFactory<>("types_de_chambre"));
        }



    public AfficherEquipementB() {
        if (equipements != null) {
            observableList = FXCollections.observableArrayList(equipements);
        } else {
            observableList = FXCollections.observableArrayList();
        }
    }

       @FXML
    void deleteAction(ActionEvent event) {
        // Récupérer l'élément sélectionné dans le TableView
        Equipement equipementSelectionnee = tableView.getSelectionModel().getSelectedItem();

        // Vérifier si un élément est sélectionné
        if (equipementSelectionnee != null) {
            // Afficher une boîte de dialogue de confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Supprimer l'activité sélectionnée");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer cette activité ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Supprimer l'élément de la liste ou de la base de données
                EquipementCrud service = new EquipementCrud();
                try {
                    service.supprimer(equipementSelectionnee);
                    // Rafraîchir le TableView après la suppression
                    tableView.getItems().remove(equipementSelectionnee);
                    System.out.println("Equipement supprimée avec succès !");
                } catch (SQLException e) {
                    System.out.println("Erreur lors de la suppression de l'equipement: " + e.getMessage());
                }
            }
        } else {
            // Afficher un message d'erreur ou une boîte de dialogue indiquant à l'utilisateur de sélectionner une activité à supprimer
        }
    }

    @FXML
    void handleAjouter(ActionEvent event) {
        try {
            // Charger la vue ou le formulaire d'ajout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEquipementB.fxml"));
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
    void modifierAction(ActionEvent event) {
        // Récupérer l'activité sélectionnée dans le TableView
        Equipement equipementSelectionnee = tableView.getSelectionModel().getSelectedItem();

        // Vérifier si une activité est sélectionnée
        if (equipementSelectionnee != null) {
            // Ouvrir la page de modification avec les données de l'activité sélectionnée
            openModifierEquipementPage(equipementSelectionnee);
        } else {
            // Afficher un message d'erreur ou une boîte de dialogue indiquant à l'utilisateur de sélectionner une activité
        }
        AfficherEquipementB();
    
    }

    private void AfficherEquipementB() {
        tableView.setVisible(true);
    }

    @FXML
    public void openModifierEquipementPage(Equipement equipement) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierEquipement.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur de la fenêtre de modification
            ModifierEquipement modifierController = loader.getController();

            // Initialiser les données de l'équipement dans le contrôleur
            modifierController.initData(equipement);

            // Afficher la fenêtre de modification
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToafficherNavBar(ActionEvent event) {
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


    @FXML
    public void goToafficherLogement() {
        try {
            // Charger le contenu de afficherActivite.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherLogementB.fxml"));
            Node afficherLogementContent = loader.load();

            // Ajouter le contenu au contentHBox
            contentHBox.getChildren().clear(); // Efface tout contenu précédent
            contentHBox.getChildren().add(afficherLogementContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void goToClient(MouseEvent mouseEvent) {
        try {
            // Charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/indexLogement.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page
            Scene scene = new Scene(root);

            // Obtenir la fenêtre actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            // Définir la nouvelle scène sur la fenêtre et l'afficher
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initData(Equipement equipement) {



    }

    public void showLogement(ActionEvent event) {
    }
}
