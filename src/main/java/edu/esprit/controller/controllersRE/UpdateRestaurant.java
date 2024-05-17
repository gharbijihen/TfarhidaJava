package edu.esprit.controller.controllersRE;

import edu.esprit.entites.Restaurant;
import edu.esprit.servies.RestaurantService;
import Utils.Datasource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class UpdateRestaurant {
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
    private TextField adresse;
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












   public void initData(Restaurant restaurant) {

       //this.restaurant = restaurant; // Assigner l'activité reçue à la variable de classe
       id = restaurant.getId(); // Attribuer l'ID du restaurant à la variable id

       // Utilisez les données de l'activité pour initialiser les champs de saisie
       nom.setText(restaurant.getNom());
       adresse.setText((restaurant.getAdresse()));
       numdetel.setText(String.valueOf(restaurant.getNumdetel()));
       nmbetoiles.setText(String.valueOf(restaurant.getNmbetoiles()));
       heure_ouverture.setText(restaurant.getHeure_ouverture());
       heure_fermeture.setText(restaurant.getHeure_fermeture());




   }



    @FXML
    void updateRestaurant(ActionEvent event) {
        // Vérifier que les champs obligatoires ne sont pas vides
        if (nom.getText().isEmpty() || adresse.getText().isEmpty() || heure_ouverture.getText().isEmpty() || heure_fermeture.getText().isEmpty()) {
            showAlert("Error", "Veuillez remplir tous les champs obligatoires.");
            return;
        }

        // Vérifier que le numéro de téléphone a exactement 8 chiffres
        String numTel = numdetel.getText();
        if (!numTel.matches("\\d{8}")) {
            showAlert("Error", "Le numéro de téléphone doit être composé exactement de 8 chiffres.");
            return;
        }

        // Vérifier que le nombre d'étoiles est compris entre 1 et 5
        int nbEtoiles;
        try {
            nbEtoiles = Integer.parseInt(nmbetoiles.getText());
            if (nbEtoiles < 1 || nbEtoiles > 5) {
                showAlert("Error", "Le nombre d'étoiles doit être compris entre 1 et 5.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Le nombre d'étoiles doit être un entier.");
            return;
        }

        // Vérifier le format de l'heure d'ouverture et de fermeture
       // if (!heure_ouverture.getText().matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$") || !heure_fermeture.getText().matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
           // showAlert("Error", "Le format de l'heure doit être hh:mm (ex: 09:30).");
           // return;
        //}

        // Mettre à jour le restaurant s'il passe toutes les vérifications
        String update = "update restaurant set nom = ?, adresse = ?, numdetel = ?, nmbetoiles = ?, heure_ouverture = ?, heure_fermeture = ? where id = ?";
        connexion = Datasource.getConn();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(update);
            preparedStatement.setString(1, nom.getText());
            preparedStatement.setString(2, adresse.getText());
            preparedStatement.setInt(3, Integer.parseInt(numTel));
            preparedStatement.setInt(4, nbEtoiles);
            preparedStatement.setString(5, heure_ouverture.getText());
            preparedStatement.setString(6, heure_fermeture.getText());
            preparedStatement.setInt(7, id);
            preparedStatement.executeUpdate();

            showSuccessAlert("Succès", "Le restaurant a été modifié avec succès.");
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
        connexion = Datasource.getConn();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLRes/restaurantaffb.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLRes/AdminDashbord.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLRes/PlatAjouter.fxml"));
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
