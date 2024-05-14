package edu.esprit.controller.Logement;

import Controllers.RouterController;
import com.itextpdf.text.DocumentException;
import edu.esprit.controller.Logement.LogementCardController;
import edu.esprit.controller.Logement.StatLogement;
import edu.esprit.entites.Logement;
import edu.esprit.servies.LogementCrud;
import edu.esprit.servies.generatepdf;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.util.ArrayList;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;


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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogementFxml/LogementCard.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogementFxml/AjouterLogementB.fxml"));
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
            RouterController router=new RouterController();
            router.navigate("/fxml/AdminDashboard.fxml");
        }








    @FXML
    void GeneratePdf(ActionEvent event) throws DocumentException, SQLException {
        try {
            ArrayList<Logement> logementList = new ArrayList<>(new LogementCrud().afficher());

            generatepdf.generatePDF(logementList, new FileOutputStream("C:\\Users\\MSI\\Downloads\\tfarhida2\\src\\Logements.pdf"), "C:\\Users\\MSI\\Downloads\\tfarhida2\\src\\main\\resources\\images\\tfarhida.png");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PDF Généré");
            alert.setHeaderText(null);
            alert.setContentText("Le PDF des Logements a été généré avec succès!");
            alert.showAndWait();
        } catch (FileNotFoundException | DocumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de la génération du PDF des Activités: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void StatAction(ActionEvent event) {
        try {
            // Charger le fichier FXML de la vue de la carte
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogementFxml/StatLogement.fxml"));
            Parent root = loader.load();

            // Obtenir le contrôleur de la vue de la carte
            StatLogement statLogement = loader.getController();

            // Passer la localisation au contrôleur de la vue de la carte

            // Afficher la vue de la carte dans une nouvelle fenêtre
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToNavigate(ActionEvent event) {
        edu.esprit.controller.RouterController.navigate("/fxml/AdminDashboard.fxml");
    }
    public void goToClient(MouseEvent mouseEvent) {
        try {
            // Charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogementFxml/indexLogement.fxml"));
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

    public void goToLogin(MouseEvent mouseEvent) {
        RouterController router=new RouterController();
        router.navigate("/fxml/login.fxml");
    }
}