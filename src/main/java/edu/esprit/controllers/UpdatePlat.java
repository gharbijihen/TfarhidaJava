package edu.esprit.controllers;

import edu.esprit.entites.Plat;
import edu.esprit.entites.Restaurant;
import edu.esprit.services.RestaurantService;
import edu.esprit.tools.MyConnection;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class UpdatePlat {
    private final RestaurantService ps = new RestaurantService();
    @FXML
    private TableColumn<Restaurant, String> nomCol;
    @FXML
    private TableColumn<Restaurant, String> adresseCol;
    @FXML
    private TableColumn<Restaurant, Integer> numdetelCol;
    @FXML
    private TableColumn<Restaurant, Integer> nmbetoilesCol;
    @FXML
    private TableColumn<Restaurant, String> heure_ouvertureCol;
    @FXML
    private TableColumn<Restaurant, String> heure_fermetureCol;
    @FXML
    private TableView<Restaurant> tableView;
    int id = 0;
    @FXML
    private TextField nom;
    @FXML
    private TextField description;
    @FXML
    private TextField numdetel;
    @FXML
    private TextField nmbetoiles;
    @FXML
    private TextField heure_ouverture;
    @FXML
    private TextField heure_fermeture;

    @FXML
    private javafx.scene.control.Button btnSave;
    @FXML
    private AnchorPane BOX1;

    @FXML
    private AnchorPane BOX2;

    @FXML
    private AnchorPane BOX3;

    @FXML
    private ImageView Img1;

    @FXML
    private ImageView Img2;

    @FXML
    private ImageView Img3;

    @FXML
    private AnchorPane MainPane;

    @FXML
    private Text adresse1;

    @FXML
    private Text adresse2;

    @FXML
    private Text adresse3;

    @FXML
    private Button btnDetail1;

    @FXML
    private Button btnModif1;

    @FXML
    private Button btnModif2;

    @FXML
    private Button btnModif3;

    @FXML
    private Button btnSupp1;

    @FXML
    private Button btnSupp2;

    @FXML
    private Button btnajout;
    @FXML
    private Button mail;

    @FXML
    private Button btndetail2;

    @FXML
    private Button btndetail3;

    @FXML
    private Button btnretour;

    @FXML
    private Button btnsuivant;

    @FXML
    private Button btnsupp3;

    @FXML
    private Text nom1;

    @FXML
    private Text nom2;

    @FXML
    private Text nom3;
    private Connection connexion;

    int i = 0;
    private Restaurant restaurant;












    public void initData(Plat plat) {

        //this.restaurant = restaurant; // Assigner l'activité reçue à la variable de classe
        id = plat.getId(); // Attribuer l'ID du restaurant à la variable id

        // Utilisez les données de l'activité pour initialiser les champs de saisie
        nom.setText(plat.getNom());
        description.setText((plat.getDescription()));





    }



    @FXML
    void updatePlat(ActionEvent event) {
        // Vérifier que les champs obligatoires ne sont pas vides
        if (nom.getText().isEmpty() || description.getText().isEmpty()) {
            showAlert("Error", "Veuillez remplir tous les champs obligatoires.");
            return;
        }



        // Mettre à jour le restaurant s'il passe toutes les vérifications
        String update = "update plat set nom = ?, description = ? where id = ?";
        connexion = MyConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(update);
            preparedStatement.setString(1, nom.getText());
            preparedStatement.setString(2, description.getText());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();

            showSuccessAlert("Succès", "Le plat a été modifié avec succès.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour afficher une boîte de dialogue d'erreur
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void deleteRestaurant(ActionEvent event) {
        String sql = "delete from restaurant where id = ?";
        connexion = MyConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    public void retour(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/plataffb.fxml"));
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
    public void goToAjoutPlat(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PlatAjouter.fxml"));
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
