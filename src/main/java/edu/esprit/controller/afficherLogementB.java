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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
    private GridPane gridLog;

    @FXML
    private TextField searchLog;

    private final LogementCrud logementCrud = new LogementCrud();
    private ObservableList<Logement> logementList = FXCollections.observableArrayList();



    public ObservableList<Logement> getAllLogementCard() throws SQLException {
        logementList.addAll(logementCrud.afficher());
        System.out.println(logementList);
        return logementList;
    }

    public void displayLogementCards(ObservableList<Logement> logementsList) {
        gridLog.getRowConstraints().clear();
        gridLog.getColumnConstraints().clear();
        int row = 0;
        int column = 0;
        for (Logement logement : logementsList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogementCard.fxml"));
                AnchorPane pane = loader.load();
                LogementCardController logCard = loader.getController();
                logCard.setData(logement);
                logCard.setLogement(logement);
                gridLog.addRow(row);
                gridLog.add(pane, column, row);

                column++;
                if (column == 2) {
                    column = 0;
                    row++;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private void filterData(String searchText) {
        ObservableList<Logement> filteredList = FXCollections.observableArrayList();
        for (Logement logement : logementList) {
            if (logement.getNom().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(logement);
            }
        }
        gridLog.getChildren().clear();
        displayLogementCards(filteredList);
    }

    public void initialize() {
        try {
            getAllLogementCard();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        displayLogementCards(logementList);
        searchLog.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData(newValue);
        });

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



