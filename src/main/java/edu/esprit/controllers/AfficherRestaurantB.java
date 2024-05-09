package edu.esprit.controllers;

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

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;


public class AfficherRestaurantB {
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
    private TextField rechercheTextField;
    @FXML
    private ChoiceBox<String> sortChoiceBox;


    @FXML
    private javafx.scene.control.Button btnSave;








    private Connection connexion;






    @FXML
    void initialize() {
        try {
            // Récupérer la liste des restaurants
            List<Restaurant> restaurants = ps.recuperer();
            ObservableList<Restaurant> observableList = FXCollections.observableList(restaurants);

            // Appliquer la liste des restaurants dans le TableView
            tableView.setItems(observableList);

            // Définir les PropertyValueFactory pour chaque colonne
            nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
            adresseCol.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            numdetelCol.setCellValueFactory(new PropertyValueFactory<>("numdetel"));
            nmbetoilesCol.setCellValueFactory(new PropertyValueFactory<>("nmbetoiles"));
            heure_ouvertureCol.setCellValueFactory(new PropertyValueFactory<>("heure_ouverture"));
            heure_fermetureCol.setCellValueFactory(new PropertyValueFactory<>("heure_fermeture"));

            // Ajouter un écouteur sur le ChoiceBox pour le tri
            sortChoiceBox.getItems().addAll("Trier par Nom", "Trier par Nombre d'étoiles");
            sortChoiceBox.setOnAction(this::handleSort);

            // Ajouter un écouteur sur le champ de recherche
            rechercheTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                // Filtrer la liste des restaurants en fonction du texte de recherche
                ObservableList<Restaurant> filteredList = observableList.filtered(
                        restaurant -> restaurant.getNom().toLowerCase().contains(newValue.toLowerCase())
                );
                // Mettre à jour le TableView avec la nouvelle liste filtrée
                tableView.setItems(filteredList);
            });
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void handleSort(ActionEvent event) {
        String selectedSortOption = sortChoiceBox.getValue();
        if (selectedSortOption != null) {
            switch (selectedSortOption) {
                case "Trier par Nom":
                    tableView.getItems().sort(Comparator.comparing(Restaurant::getNom));
                    break;
                case "Trier par Nombre d'étoiles":
                    tableView.getItems().sort(Comparator.comparingInt(Restaurant::getNmbetoiles).reversed());
                    break;
                default:
                    break;
            }
        }
    }



    @FXML
    void getData(MouseEvent event) {
        Restaurant restaurant = tableView.getSelectionModel().getSelectedItem();
        if (restaurant != null) {
            id = restaurant.getId(); // Stockez l'identifiant du restaurant sélectionné
            nom.setText(restaurant.getNom());
            adresse.setText(restaurant.getAdresse());
            numdetel.setText(String.valueOf(restaurant.getNumdetel()));
            nmbetoiles.setText(String.valueOf(restaurant.getNmbetoiles()));
            heure_ouverture.setText(restaurant.getHeure_ouverture());
            heure_fermeture.setText(restaurant.getHeure_fermeture());
            btnSave.setDisable(true);
        }

    }


    @FXML
    void showRestaurants() {
        try {
            List<Restaurant> restaurants = ps.recuperer();
            ObservableList<Restaurant> observableList = FXCollections.observableList(restaurants);
            tableView.setItems(observableList);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /*@FXML
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

        // Mettre à jour le restaurant s'il passe toutes les vérifications
        String update = "update restaurant set nom = ?, adresse = ?, numdetel = ?, nmbetoiles = ?, heure_ouverture = ?, heure_fermeture = ? where id = ?";
        connexion = MyConnection.getInstance().getConnection();
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
            showRestaurants();
            showSuccessAlert("Succès", "Le restaurant a été modifié avec succès.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/

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
            showRestaurants();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void allerversstat(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/stat.fxml"));
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
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

    @FXML
    void goToUpdateRestaurant(ActionEvent event) {

        Restaurant restaurantSelectionnee = tableView.getSelectionModel().getSelectedItem();


        // Vérifier si une activité est sélectionnée
        if (restaurantSelectionnee != null) {
            // Ouvrir la page de modification avec les données de l'activité sélectionnée
            openModifierRestaurantPage(restaurantSelectionnee);
        } else {
            // Afficher un message d'erreur ou une boîte de dialogue indiquant à l'utilisateur de sélectionner une activité
        }

    }

    @FXML
    private void openModifierRestaurantPage(Restaurant restaurant) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateRestaurant.fxml"));
            Parent root = loader.load();
            UpdateRestaurant modifierController = loader.getController();
            modifierController.initData(restaurant); // Transmettre également le fichier d'image sélectionné
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ajouterPlat(ActionEvent event) {
        try {
            // Récupérer l'ID du restaurant sélectionné
            Restaurant restaurant = tableView.getSelectionModel().getSelectedItem();
            int restaurantId = restaurant.getId();

            // Charger la vue PlatAjouter.fxml

            // Charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PlatAjouter.fxml"));
            Parent root = loader.load();
            AjouterPlatController ajouterPlatController = loader.getController();
            ajouterPlatController.initData(restaurantId);

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
