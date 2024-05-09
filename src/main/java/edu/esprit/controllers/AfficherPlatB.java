package edu.esprit.controllers;

import edu.esprit.entites.Plat;
import edu.esprit.entites.Restaurant;
import edu.esprit.services.PlatService;
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
import java.util.List;


public class AfficherPlatB {
    private final PlatService ps = new PlatService();
    @FXML
    private TableColumn<Plat, String> nomCol;
    @FXML
    private TableColumn<Plat, String> adresseCol;
    @FXML
    private TableColumn<Plat, String> descriptionCol;
    @FXML
    private TableColumn<Plat, Integer> numdetelCol;
    @FXML
    private TableColumn<Plat, Integer> nmbetoilesCol;
    @FXML
    private TableColumn<Plat, String> heure_ouvertureCol;
    @FXML
    private TableColumn<Plat, String> heure_fermetureCol;
    @FXML
    private TableView<Plat> tableView;
    int id = 0;
    @FXML
    private TextField nom;
    @FXML
    private TextField description;
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


    /*void actualise(List<Restaurant> restaurants){
        if(restaurants.size()-1-i*3>0){btnsuivant.setVisible(true);
        }

        if(restaurants.size()-1-i*3 <= 0){btnsuivant.setVisible(false);
        }
        if(i > 0){btnretour.setVisible(true);
        }
        if(i == 0){btnretour.setVisible(false);
        }
        if(!restaurants.isEmpty()){
            if(restaurants.size()-1-i*3>=0){
                BOX1.setVisible(true);
                nom1.setText(restaurants.get(i*3).getNom());
                adresse1.setText(restaurants.get(i*3).getAdresse());
                Img1.setImage(new Image(restaurants.get(i*3).getImage()));

            }
            else{BOX1.setVisible(false);
            }
            if(restaurants.size()-2-i*3>=0){
                BOX2.setVisible(true);
                nom2.setText(restaurants.get(1+i*3).getNom());
                adresse2.setText(restaurants.get(1+i*3).getAdresse());
                Img2.setImage(new Image(restaurants.get(1+i*3).getImage()));

            }
            else{
                BOX2.setVisible(false);
            }
            if(restaurants.size()-3-i*3>=0){
                BOX3.setVisible(true);
                nom3.setText(restaurants.get(2+i*3).getNom());
                adresse3.setText(restaurants.get(2+i*3).getAdresse());
                Img3.setImage(new Image(restaurants.get(2+i*3).getImage()));

            }else{BOX3.setVisible(false);}}else{
            BOX1.setVisible(false);
            BOX2.setVisible(false);
            BOX3.setVisible(false);
        }
        btnsuivant.setVisible(restaurants.size()-3*i > 3);
    }

    @FXML
    void retour(ActionEvent event) {
        i -=1;
        try {
            actualise(ps.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void suivant(ActionEvent event) {
        i +=1;
        try {

            actualise(ps.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }*/

    @FXML
    void initialize() {
        try {
            List<Plat> plats = ps.recuperer();
            ObservableList<Plat> observableList = FXCollections.observableList(plats);
            tableView.setItems(observableList);

            nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }





    @FXML
    void getData(MouseEvent event) {
        Plat plat = tableView.getSelectionModel().getSelectedItem();
        id = plat.getId();
        nom.setText(plat.getNom());
        description.setText(plat.getDescription());
        btnSave.setDisable(true);

    }

    @FXML
    void showPlats() {
        try {
            List<Plat> plats = ps.recuperer();
            ObservableList<Plat> observableList = FXCollections.observableList(plats);
            tableView.setItems(observableList);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    public void goToAjoutRestaurant(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RestaurantAjouter.fxml"));
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
    void updatePlat(ActionEvent event) {

        String update = "update plat set nom = ?, description = ? where id = ?";
        connexion = MyConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(update);
            preparedStatement.setString(1, nom.getText());
            preparedStatement.setString(2, description.getText());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            showPlats();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void deletePlat(ActionEvent event) {
        String sql = "delete from plat where id = ?";
        connexion = MyConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            showPlats();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    /*@FXML
    void naviguezVersAjout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterRestaurant.fxml"));
            numdetel.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }*/
    @FXML
    void goToUpdatePlat(ActionEvent event) {

        Plat platSelectionnee = tableView.getSelectionModel().getSelectedItem();


        // Vérifier si une activité est sélectionnée
        if (platSelectionnee != null) {
            // Ouvrir la page de modification avec les données de l'activité sélectionnée
            openModifierPlatPage(platSelectionnee);
        } else {
            // Afficher un message d'erreur ou une boîte de dialogue indiquant à l'utilisateur de sélectionner une activité
        }

    }

    @FXML
    private void openModifierPlatPage(Plat plat) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateplat.fxml"));
            Parent root = loader.load();
            UpdatePlat modifierController = loader.getController();
            modifierController.initData(plat); // Transmettre également le fichier d'image sélectionné
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
}
