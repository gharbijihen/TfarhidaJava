package edu.esprit.controller;

import edu.esprit.entites.Logement;
import edu.esprit.servies.LogementCrud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;

public class LogementCardController {

    @FXML
    private Label NomLog;

    @FXML
    private Label localisationLog;
    @FXML
    private Label etatLog;

    @FXML
    private ImageView imageLog;

    @FXML
    private Label numeroLog;

    @FXML
    private Label prixLog;

    @FXML
    private Label typeLog;



    private Image image;
    private Logement logement;
    public void setLogement(Logement logement){
        this.logement=logement;
    }

    public void setData(Logement logement){
        NomLog.setText(logement.getNom());
       // equipLog.setText(logement.equipement.getDescription());
        etatLog.setText(logement.getEtat());
        localisationLog.setText(logement.getLocalisation());

        prixLog.setText(String.valueOf(logement.getPrix()));
        numeroLog.setText(String.valueOf(logement.getNum()));
        typeLog.setText(logement.getType_log());



       // niveau.setText(formation.getNiveau().toString());


        String path = logement.getImage();
        try {
            image = new Image(new File(path).toURI().toURL().toString(), 207, 138, false, true);
            imageLog.setImage(image);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void details(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogementFxml/DetailsLogement.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            System.out.println(logement+"here we go");
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            DetailsLogementController oneOeuvreController=loader.getController();
            oneOeuvreController.setData(logement);
            stage.setTitle("One Logement");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void supprimer(ActionEvent event) {
        try {
            LogementCrud service = new LogementCrud();
            service.supprimer(logement);

            // Show alert for successful deletion
            showAlert("Suppression réussie", "Le logement a été supprimé avec succès.");

            // Refresh the current page (you may need to adjust this based on your implementation)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogementFxml/LogementAffB.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (SQLException | IOException e) {
            // Handle exceptions
            showAlert("Erreur lors de la suppression", "Une erreur s'est produite lors de la suppression du logement.");
            e.printStackTrace();
        }
    }

    @FXML
    void modifier(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogementFxml/logementModifier.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            System.out.println(logement+"new here we go");
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            ModifierLogementB modifierLogementB=loader.getController();
            modifierLogementB.initData(logement);
            stage.setTitle("One Logement");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
