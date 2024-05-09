package edu.esprit.controllers;

import edu.esprit.entites.Restaurant;
import edu.esprit.services.RestaurantService;
import edu.esprit.tools.MyConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;










public class AfficherRestaurantController {
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
    private TextField rechercheTextField;
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
    private Text numdetel1;
    @FXML
    private Text numdetel2;
    @FXML
    private Text numdetel3;
    @FXML
    private Text nmbetoiles1;
    @FXML
    private Text nmbetoiles2;
    @FXML
    private Text nmbetoiles3;

    @FXML
    private Button Detail3;

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
    private Button Detail1;

    @FXML
    private Button Detail2;
    @FXML
    private Button map1;
    @FXML
    private Button map2;
    @FXML
    private Button map3;

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


    void actualise(List<Restaurant> restaurants){
        rechercheTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                // Obtenez la liste complète des restaurants depuis votre source de données (base de données, liste en mémoire, etc.)
                List<Restaurant> allRestaurants = ps.getAll();

                // Filtrez les restaurants en fonction du texte de recherche entré
                List<Restaurant> filteredRestaurants = allRestaurants.stream()
                        .filter(restaurant -> restaurant.getNom().toLowerCase().contains(newValue.toLowerCase()))
                        .collect(Collectors.toList());

                // Mettez à jour l'affichage des restaurants filtrés
                actualise(filteredRestaurants);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
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
                nmbetoiles1.setText(String.valueOf(restaurants.get(i * 3).getNmbetoiles()));
                numdetel1.setText(String.valueOf(restaurants.get(i * 3).getNumdetel()));







            }
            else{BOX1.setVisible(false);
            }
            if(restaurants.size()-2-i*3>=0){
                BOX2.setVisible(true);
                nom2.setText(restaurants.get(1+i*3).getNom());
                adresse2.setText(restaurants.get(1+i*3).getAdresse());
                Img2.setImage(new Image(restaurants.get(1+i*3).getImage()));
                nmbetoiles2.setText(String.valueOf(restaurants.get(1+i * 3).getNmbetoiles()));
                numdetel2.setText(String.valueOf(restaurants.get(1+i * 3).getNumdetel()));



            }
            else{
                BOX2.setVisible(false);
            }
            if(restaurants.size()-3-i*3>=0){
                BOX3.setVisible(true);
                nom3.setText(restaurants.get(2+i*3).getNom());
                adresse3.setText(restaurants.get(2+i*3).getAdresse());
                Img3.setImage(new Image(restaurants.get(2+i*3).getImage()));
                nmbetoiles3.setText(String.valueOf(restaurants.get(2+i * 3).getNmbetoiles()));
                numdetel3.setText(String.valueOf(restaurants.get(2+i * 3).getNumdetel()));



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

    }







   @FXML
   public void initialize() {
       try {
           actualise(ps.getAll());
       } catch (SQLException e) {
           throw new RuntimeException(e);
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

    @FXML
    void updateRestaurant(ActionEvent event) {

        String update = "update restaurant set nom = ?, adresse = ?, numdetel = ?, nmbetoiles = ?, heure_ouverture = ?, heure_fermeture = ? where id = ?";
        connexion = MyConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(update);
            preparedStatement.setString(1, nom.getText());
            preparedStatement.setString(2, adresse.getText());
            preparedStatement.setInt(3, Integer.parseInt(numdetel.getText()));
            preparedStatement.setInt(4, Integer.parseInt(nmbetoiles.getText()));
            preparedStatement.setString(5, heure_ouverture.getText());
            preparedStatement.setString(6, heure_fermeture.getText());
            preparedStatement.setInt(7, id);
            preparedStatement.executeUpdate();
            showRestaurants();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    public void showPlatsForRestaurant1(ActionEvent  event) {
        try {


            // Charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/test1.fxml"));
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
    public void showPlatsForRestaurant2(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/test1.fxml"));
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
    public void showPlatsForRestaurant3(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/test1.fxml"));
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
