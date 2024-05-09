package edu.esprit.controller.Activite;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;



public class MapController implements Initializable {

    public static double lon;
    public static double lat;

    @FXML
    private WebView wv;
    @FXML
    private TextField coordinatesField;
    private WebEngine engine;
    @FXML
    private Button btn;
    @FXML
    private Button closeButton;
    @FXML
    public Button weatherButton;
    public static String pos;


    public void setLocation(String location) {
        // Charger la carte correspondante en fonction de la localisation

        // Construire l'URL de la carte en utilisant la localisation
        String url = "https://www.google.com/maps?q=" + location;

        // Charger l'URL dans le composant WebView
        WebEngine webEngine = wv.getEngine();
        webEngine.load(url);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            File file = new File("C:\\Users\\ASUS\\IdeaProjects\\TfarhidaJava\\src\\main\\java\\edu\\esprit\\map\\map1.html");
            URL htmlUrl = file.toURI().toURL();
            engine = wv.getEngine();
            engine.load(htmlUrl.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    private void openWeatherPage(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page weather.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ActiviteFxml/Weather.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec le contenu de weather.fxml
            Scene scene = new Scene(root);

            // Créer une nouvelle fenêtre pour afficher la scène
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Weather Information");

            // Afficher la nouvelle fenêtre
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void tt(MouseEvent event) {
        lat = (Double) wv.getEngine().executeScript("lat");
        lon = (Double) wv.getEngine().executeScript("lon");


        //System.out.println("Lat: " + lat);
        //System.out.println("Lon " + lon);
        coordinatesField.setText("Latitude : "+Double.toString(lat)+" Longitude : "+Double.toString(lon));
    }
    @FXML
    void onClose() {
        // Fermer la fenêtre
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

   /* @FXML
    private void sendd(ActionEvent event) throws IOException {
        // Récupérer l'instance du contrôleur actuel
        FXMLLoader loader = new FXMLLoader(getClass().getResource("IteamA.fxml"));
        Parent root = loader.load();
        IteamController controller = loader.getController();

        // Vérifier si le contrôleur a été correctement récupéré
        if (controller != null) {
            // Récupérer l'activité associée au contrôleur
            Activite activite = controller.getActivite();
            if (activite != null) {
                // Récupérer la localisation de l'activité et l'affecter à la méthode setlocal
                String localisation = activite.getLocalisation();
                controller.setLocal(localisation);
            }
        }

        // Afficher la scène
        Scene scene = new Scene(root);
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }*/

   /* public void setLocation(String localisation) {
        // Mettez à jour la localisation dans votre vue de carte
        // Par exemple, vous pouvez afficher la localisation dans un TextField ou une étiquette
        coordinatesField.setText(localisation);
    }*/

    // JavaScript interface object
    private class JavaApp {
        public void exit() {
            Platform.exit();
        }}
    //recuperer la localisation pour le weather
    public static String getLocation() {
        return Double.toString(lat) + "," + Double.toString(lon);
    }

}
