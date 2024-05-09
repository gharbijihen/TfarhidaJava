package edu.esprit.controller.controllersRE;

import edu.esprit.entites.Plat;
import edu.esprit.entites.Restaurant;
import edu.esprit.servies.PlatService;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


public class AfficherPlatController {
    private final PlatService ps = new PlatService();
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
    private TableView<Plat> tableView;
    int id = 0;
    @FXML
    private TextField nom;
    @FXML
    private TextField rechercheTextField;
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
    private Button restaurants;

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


    void actualise(List<Plat> plats){
        rechercheTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                // Obtenez la liste complète des restaurants depuis votre source de données (base de données, liste en mémoire, etc.)
                List<Plat> allPlats = ps.getAll();

                // Filtrez les restaurants en fonction du texte de recherche entré
                List<Plat> filteredPlats = allPlats.stream()
                        .filter(restaurant -> restaurant.getNom().toLowerCase().contains(newValue.toLowerCase()))
                        .collect(Collectors.toList());

                // Mettez à jour l'affichage des restaurants filtrés
                actualise(filteredPlats);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        if(plats.size()-1-i*3>0){btnsuivant.setVisible(true);
        }

        if(plats.size()-1-i*3 <= 0){btnsuivant.setVisible(false);
        }
        if(i > 0){btnretour.setVisible(true);
        }
        if(i == 0){btnretour.setVisible(false);
        }
        if(!plats.isEmpty()){
            if(plats.size()-1-i*3>=0){
                BOX1.setVisible(true);
                nom1.setText(plats.get(i*3).getNom());
                Img1.setImage(new Image(plats.get(i*3).getImage()));






            }
            else{BOX1.setVisible(false);
            }
            if(plats.size()-2-i*3>=0){
                BOX2.setVisible(true);
                nom2.setText(plats.get(1+i*3).getNom());
                Img2.setImage(new Image(plats.get(i*3).getImage()));

            }
            else{
                BOX2.setVisible(false);
            }
            if(plats.size()-3-i*3>=0){
                BOX3.setVisible(true);
                nom3.setText(plats.get(2+i*3).getNom());
                Img3.setImage(new Image(plats.get(i*3).getImage()));


            }else{BOX3.setVisible(false);}}else{
            BOX1.setVisible(false);
            BOX2.setVisible(false);
            BOX3.setVisible(false);
        }
        btnsuivant.setVisible(plats.size()-3*i > 3);
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
    void showRecipeDetails1(ActionEvent event) {
        Button button = (Button) event.getSource();
        VBox parentBox = (VBox) button.getParent().getParent(); // Get the parent of the parent of the button (i.e., the box)
        String mealName = ""; // Initialize the dish name

        // Iterate over the children of the parent box
        for (Node node : parentBox.getChildren()) {
            if (node instanceof HBox) { // If the node is an HBox
                HBox hbox = (HBox) node;
                for (Node hboxChild : hbox.getChildren()) { // Iterate over the children of HBox
                    if (hboxChild instanceof Text) { // If the child is a Text
                        Text text = (Text) hboxChild;
                        if ("nom1".equals(text.getId())) { // If the ID of the Text matches "nom1"
                            mealName = text.getText(); // Retrieve the text of the Text
                            break;
                        }
                    }
                }
                if (!mealName.isEmpty()) { // If the dish name is found, exit the outer loop
                    break;
                }
            }
        }

        try {
            String recipeDetails = MealDBAPI.getRecipeDetails(mealName);
            // Display the recipe details in an alert dialog
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Recipe Details");
            alert.setHeaderText(null);
            alert.getDialogPane().setPrefSize(600, 400); // Définir une taille préférée pour la boîte de dialogue d'alerte

            alert.setContentText(recipeDetails);
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle errors while retrieving recipe details
        }
    }

    @FXML
    void showRecipeDetails2(ActionEvent event) {
        Button button = (Button) event.getSource();
        VBox parentBox = (VBox) button.getParent().getParent(); // Get the parent of the parent of the button (i.e., the box)
        String mealName = ""; // Initialize the dish name

        // Iterate over the children of the parent box
        for (Node node : parentBox.getChildren()) {
            if (node instanceof HBox) { // If the node is an HBox
                HBox hbox = (HBox) node;
                for (Node hboxChild : hbox.getChildren()) { // Iterate over the children of HBox
                    if (hboxChild instanceof Text) { // If the child is a Text
                        Text text = (Text) hboxChild;
                        if ("nom2".equals(text.getId())) { // If the ID of the Text matches "nom1"
                            mealName = text.getText(); // Retrieve the text of the Text
                            break;
                        }
                    }
                }
                if (!mealName.isEmpty()) { // If the dish name is found, exit the outer loop
                    break;
                }
            }
        }

        try {
            String recipeDetails = MealDBAPI.getRecipeDetails(mealName);
            // Display the recipe details in an alert dialog
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Recipe Details");
            alert.setHeaderText(null);
            alert.getDialogPane().setPrefSize(600, 400); // Définir une taille préférée pour la boîte de dialogue d'alerte

            alert.setContentText(recipeDetails);
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle errors while retrieving recipe details
        }
    }
    @FXML
    void showRecipeDetails3(ActionEvent event) {
        Button button = (Button) event.getSource();
        VBox parentBox = (VBox) button.getParent().getParent(); // Get the parent of the parent of the button (i.e., the box)
        String mealName = ""; // Initialize the dish name

        // Iterate over the children of the parent box
        for (Node node : parentBox.getChildren()) {
            if (node instanceof HBox) { // If the node is an HBox
                HBox hbox = (HBox) node;
                for (Node hboxChild : hbox.getChildren()) { // Iterate over the children of HBox
                    if (hboxChild instanceof Text) { // If the child is a Text
                        Text text = (Text) hboxChild;
                        if ("nom3".equals(text.getId())) { // If the ID of the Text matches "nom1"
                            mealName = text.getText(); // Retrieve the text of the Text
                            break;
                        }
                    }
                }
                if (!mealName.isEmpty()) { // If the dish name is found, exit the outer loop
                    break;
                }
            }
        }

        try {
            String recipeDetails = MealDBAPI.getRecipeDetails(mealName);
            // Display the recipe details in an alert dialog
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Recipe Details");
            alert.setHeaderText(null);
            alert.getDialogPane().setPrefSize(600, 400); // Définir une taille préférée pour la boîte de dialogue d'alerte

            alert.setContentText(recipeDetails);
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle errors while retrieving recipe details
        }
    }

    @FXML
    public void retourverstest(ActionEvent  event) {
        try {


            // Charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLRes/test.fxml"));
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
