package edu.esprit.controller;

import edu.esprit.entites.Logement;
import edu.esprit.servies.LogementCrud;
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

public class afficherLogementB {
    @FXML
    private HBox contentHBox;
    @FXML
    private Button logementButton;
    @FXML
    private TableColumn<Logement, Void> colAction;

    @FXML
    private TableColumn<Logement,String> colEtat;

    @FXML
    private TableColumn<Logement,Integer> colIdEquipement;

    @FXML
    private TableColumn<Logement, Integer> colIdUser;

    @FXML
    private TableColumn<Logement,String> colLocalisation;

    @FXML
    private TableColumn<Logement,String> colNom;

    @FXML
    private TableColumn<Logement,Integer> colNote;

    @FXML
    private TableColumn<Logement,Integer> colNum;

    @FXML
    private TableColumn<Logement,Integer> colPrix;

    @FXML
    private TableColumn<Logement,String> colTypeLog;
    @FXML
    private TableView<Logement> tableView;

    @FXML
    private ScrollPane logementScrollPane;
    @FXML
    private final LogementCrud ps = new LogementCrud();
    private File selectedImageFile;

    public List<Logement> logements = ps.afficher();

    public ObservableList<Logement> observableList = FXCollections.observableArrayList(logements);
    // Dans afficherLogementB.java

    public void initialize() {
        tableView.setItems(observableList);
      //  colIdUser.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        colIdEquipement.setCellValueFactory(new PropertyValueFactory<>("equipement_id"));
        colTypeLog.setCellValueFactory(new PropertyValueFactory<>("type_log"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNote.setCellValueFactory(new PropertyValueFactory<>("note_moyenne"));
        colLocalisation.setCellValueFactory(new PropertyValueFactory<>("localisation"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colNum.setCellValueFactory(new PropertyValueFactory<>("num"));
        colEtat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        colAction.setCellFactory(cell -> new ActionCell());


    }

    @FXML
    private void afficherLogementB() {
            tableView.setVisible(true);


    }

    @FXML
    void deleteAction(ActionEvent event) {
            // Récupérer l'élément sélectionné dans le TableView
            Logement logementSelectionnee = tableView.getSelectionModel().getSelectedItem();

            // Vérifier si un élément est sélectionné
            if (logementSelectionnee != null) {
                // Afficher une boîte de dialogue de confirmation
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation de suppression");
                alert.setHeaderText("Supprimer le logement sélectionné");
                alert.setContentText("Êtes-vous sûr de vouloir supprimer ce logement ?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Supprimer l'élément de la liste ou de la base de données
                    LogementCrud service = new LogementCrud();
                    try {
                        service.supprimer(logementSelectionnee);
                        // Rafraîchir le TableView après la suppression
                        tableView.getItems().remove(logementSelectionnee);
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
     public void handleAjouter(ActionEvent event) {
       // RouterController.navigate("AjouterEquipement.fxml");
        try {
            // Charger la vue ou le formulaire d'ajout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterLogementB.fxml"));
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
    public void modifierLogementAction(ActionEvent event) {
        // Récupérer l'activité sélectionnée dans le TableView
        Logement logementSelectionnee = tableView.getSelectionModel().getSelectedItem();

        // Vérifier si une activité est sélectionnée
        if (logementSelectionnee != null) {
            // Ouvrir la page de modification avec les données de l'activité sélectionnée
            openModifierLogementPage(logementSelectionnee);
        } else {
            // Afficher un message d'erreur ou une boîte de dialogue indiquant à l'utilisateur de sélectionner une activité
        }
        afficherLogementB();
    }

    @FXML
    public void openModifierLogementPage(Logement logement) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/logementModifier.fxml"));
            Parent root = loader.load();
            ModifierLogementB modifierController = loader.getController();
            modifierController.initData(logement, selectedImageFile); // Transmettre également le fichier d'image sélectionné
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  //  private void refreshTable()
   // {
       // observableList.clear();
       // observableList = LogementCrud.afficher();
     //   tableView.setItems(observableList);
   // }
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

    public void goToafficherEquipement(ActionEvent event) {
        try {
            // Charger le contenu de afficherActivite.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EquipementB.fxml"));
            Node afficherLogementContent = loader.load();

            // Ajouter le contenu au contentHBox
            contentHBox.getChildren().clear(); // Efface tout contenu précédent
            contentHBox.getChildren().add(afficherLogementContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



