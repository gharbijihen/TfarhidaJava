package edu.esprit.controller.Logement;
import  edu.esprit.controller.Logement.IteamLogementController;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.esprit.entites.Logement;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import javafx.concurrent.Worker;
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
    private void tt(MouseEvent event) {
        lat = (Double) wv.getEngine().executeScript("lat");
        lon = (Double) wv.getEngine().executeScript("lon");


        //System.out.println("Lat: " + lat);
        //System.out.println("Lon " + lon);
        coordinatesField.setText("Latitude : "+Double.toString(lat)+" Longitude : "+Double.toString(lon));
    }

    @FXML
    void Retour(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }


    // JavaScript interface object
    private class JavaApp {
        public void exit() {
            Platform.exit();
        }}

}