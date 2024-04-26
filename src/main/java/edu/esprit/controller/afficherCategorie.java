package edu.esprit.controller;

import edu.esprit.entites.Categorie;
import edu.esprit.servies.ActiviteCrud;
import edu.esprit.servies.CategorieCrud;
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

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import edu.esprit.entites.Activite;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class afficherCategorie{
    @FXML
    private TableView<Categorie> tableView;

    @FXML
    private TableColumn<Categorie, String> colType;

    @FXML
    private TableColumn<Categorie, String> colDesc;


    @FXML
    private final CategorieCrud ps = new CategorieCrud();
    @FXML
    private ScrollPane categorieScrollPane; // Ajout du ScrollPane
    private ScrollPane logementScrollPane; // Ajout du ScrollPane

    @FXML
    private Button categorieButton; // Référence au bouton "Categorie"
    private File selectedImageFile;




    public void initialize() throws SQLException {

        List<Categorie> act = ps.afficher();
        ObservableList<Categorie> observableList = FXCollections.observableList(act);
        tableView.setItems(observableList);
        // Configure les colonnes pour correspondre aux attributs de l'activité
        colType.setCellValueFactory(new PropertyValueFactory<>("type_categorie"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableView.setVisible(true);
    }


    @FXML
    private void afficherCategoriee() {
        tableView.setVisible(true);

    }
    @FXML
    void modifierActiviteAction(ActionEvent event) {
        // Récupérer l'activité sélectionnée dans le TableView
        Categorie categorieSelectionnee = tableView.getSelectionModel().getSelectedItem();

        // Vérifier si une activité est sélectionnée
        if (categorieSelectionnee != null) {
            // Ouvrir la page de modification avec les données de l'activité sélectionnée
            openModifierCategoriePage(categorieSelectionnee);
        } else {
            // Afficher un message d'erreur ou une boîte de dialogue indiquant à l'utilisateur de sélectionner une activité
        }
    }
    @FXML

    private void openModifierCategoriePage(Categorie categorie) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/categorieModife.fxml"));
            Parent root = loader.load();
            ModifierCategorieController modifierController = loader.getController();
            modifierController.initData(categorie);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnHidden(e -> refreshList());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void deleteAction(ActionEvent event) throws SQLException {
        // Récupérer l'élément sélectionné dans le TableView
        Categorie categorieSelectionnee = tableView.getSelectionModel().getSelectedItem();

        // Vérifier si un élément est sélectionné
        if (categorieSelectionnee != null) {
            // Afficher une boîte de dialogue de confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Supprimer la catégorie sélectionnée");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer cette catégorie ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Supprimer l'élément de la liste ou de la base de données
                CategorieCrud service = new CategorieCrud();
                try {
                    service.supprimer(categorieSelectionnee);
                    // Rafraîchir le TableView après la suppression
                    tableView.getItems().remove(categorieSelectionnee);
                    System.out.println("Catégorie supprimée avec succès !");
                } catch (SQLException e) {
                    System.out.println("Erreur lors de la suppression de l'activité : " + e.getMessage());
                }
            }
        } else {
            // Afficher un message d'erreur ou une boîte de dialogue indiquant à l'utilisateur de sélectionner une activité à supprimer
        }
    }
            /*if (result.isPresent() && result.get() == ButtonType.OK) {
                // Supprimer l'élément de la liste ou de la base de données
                service.supprimer(categorieSelectionnee);
                // Rafraîchir le TableView après la suppression
                tableView.getItems().remove(categorieSelectionnee);
                System.out.println("Catégorie supprimée avec succès !");
            }
        } else {
            // Afficher un message d'erreur ou une boîte de dialogue indiquant à l'utilisateur de sélectionner une activité à supprimer
        }
    }*/
    @FXML
    void handleAjouter(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/categorieAjouter.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnHiding(e -> {
                refreshList();
            });
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void refreshList() {
        try {
            initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void goToafficherNavBarCat(ActionEvent event) {
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
    public void goToActivite(MouseEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ActiviteAffB.fxml"));
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




