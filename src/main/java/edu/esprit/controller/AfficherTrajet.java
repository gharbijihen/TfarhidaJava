package edu.esprit.controller;

import edu.esprit.entites.Moyen_transport;
import edu.esprit.servies.Moyen_transportCrud;
import edu.esprit.servies.TrajetCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;

import edu.esprit.entites.Trajet;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;



public class AfficherTrajet {


    public TextField searchTextField;
    @FXML
    private TableColumn<Trajet, Date> colDate;

    @FXML
    private TableColumn<Trajet, String> colLieuD;



    @FXML
    private TableColumn<Trajet, String> colHeure;

    @FXML
    private TableColumn<Trajet, String> colLieuA;

    @FXML
    private TableColumn<Trajet, Integer> colMoyt;

    @FXML
    private HBox contentHBox;

    @FXML
    private Button trajetButton;

    @FXML
    private ScrollPane trajetScrollPane;

    @FXML
    private TableView<Trajet> tableView;

    private File selectedImageFile;

    private FilteredList<Trajet> filteredTrajetsList;


    @FXML
    private final TrajetCrud ps = new TrajetCrud();



    public void initialize()  {
        List<Trajet> tra = ps.afficher();
        ObservableList<Trajet> observableList = FXCollections.observableList(tra);
        tableView.setItems(observableList);
        filteredTrajetsList = new FilteredList<>(observableList);
        tableView.setItems(filteredTrajetsList);
        // Configure les colonnes pour correspondre aux attributs de l'activité
        colLieuD.setCellValueFactory(new PropertyValueFactory<>("lieu_depart"));
        colLieuA.setCellValueFactory(new PropertyValueFactory<>("lieu_arrivee"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colHeure.setCellValueFactory(new PropertyValueFactory<>("heure"));
        tableView.setStyle("-fx-background-color: #f2f2f2;");


        // Set styles for each TableColumn
        colLieuD.setStyle("-fx-alignment: CENTER;");
        colLieuA.setStyle("-fx-alignment: CENTER;");
        colDate.setStyle("-fx-alignment: CENTER;");
        colHeure.setStyle("-fx-alignment: CENTER;");

        // Set preferred widths for the columns
        colLieuD.setPrefWidth(260);
        colLieuA.setPrefWidth(260);
        colDate.setPrefWidth(260);
        colHeure.setPrefWidth(260);


        tableView.setVisible(true); // Rend la table visible par défaut

    }


    @FXML
    private void affichertrajet() {
        tableView.setVisible(true);


    }

    public void refreshData() {
        try {
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void modifierTrajetAction(ActionEvent event) {
        // Récupérer l'activité sélectionnée dans le TableView
        Trajet trajetSelectionnee = tableView.getSelectionModel().getSelectedItem();

        // Vérifier si une activité est sélectionnée
        if (trajetSelectionnee != null) {
            // Ouvrir la page de modification avec les données de l'activité sélectionnée
            openModifierTrajetPage(trajetSelectionnee);
        } else {
            // Afficher un message d'erreur ou une boîte de dialogue indiquant à l'utilisateur de sélectionner une activité
        }

        affichertrajet();

    }


    @FXML
    private void openModifierTrajetPage(Trajet trajet) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/trajetmodifie.fxml"));
            Parent root = loader.load();
            ModifierTrajet modifierController = loader.getController();
            modifierController.initData(trajet); // Transmettre également le fichier d'image sélectionné
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnHidden(e->refreshData());
            stage.show();

        } catch (IOException e) {
            // Handle IO exceptions
            e.printStackTrace();
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
        }
    }


    @FXML
    void deleteAction(ActionEvent event) {
        // Récupérer l'élément sélectionné dans le TableView
        Trajet trajetSelectionnee = tableView.getSelectionModel().getSelectedItem();

        // Vérifier si un élément est sélectionné
        if (trajetSelectionnee != null) {
            // Afficher une boîte de dialogue de confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Supprimer l'activité sélectionnée");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer cette activité ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Supprimer l'élément de la liste ou de la base de données
                TrajetCrud service = new TrajetCrud();
                try {
                    service.supprimer(trajetSelectionnee);
                    // Rafraîchir le TableView après la suppression
                    tableView.getItems().remove(trajetSelectionnee);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterTrajet.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle fenêtre pour afficher le formulaire d'ajout
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnHidden(e->refreshData());
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
    private void goToaffichertrajet() {
        try {
            // Charger le contenu de afficherActivite.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/affichertrajet.fxml"));
            Node afficherTrajetContent = loader.load();

            // Ajouter le contenu au contentHBox
            contentHBox.getChildren().clear(); // Efface tout contenu précédent
            contentHBox.getChildren().add(afficherTrajetContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void searchAction(javafx.scene.input.KeyEvent keyEvent) {
        // Get the search query from the TextField
        String query = searchTextField.getText().trim().toLowerCase();

        // Apply a filter to the filteredTrajetsList based on the search query
        filteredTrajetsList.setPredicate(trajet -> {
            if (query.isEmpty()) {
                // If the query is empty, display all trajets
                return true;
            } else {
                // Filter the trajets based on the search query
                return trajet.getLieu_depart().toLowerCase().contains(query) ||
                        trajet.getLieu_arrivee().toLowerCase().contains(query) ||
                        trajet.getDate().toString().toLowerCase().contains(query) ||
                        trajet.getHeure().toLowerCase().contains(query);
            }
        });
    }
}