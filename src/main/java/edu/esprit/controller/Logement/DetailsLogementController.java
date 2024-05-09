package edu.esprit.controller.Logement;

import edu.esprit.controller.Logement.ModifierLogementB;
import edu.esprit.entites.Equipement;
import edu.esprit.entites.Logement;
import edu.esprit.servies.LogementCrud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;

public class DetailsLogementController {

    @FXML
    private Label clim;

    @FXML
    private Button delete;

    @FXML
    private Label desc;

    @FXML
    private Label etatLog;

    @FXML
    private ImageView img;

    @FXML
    private Label internet;

    @FXML
    private Label localLog;

    @FXML
    private Label nbrCham;

    @FXML
    private Label nomLog;

    @FXML
    private Label numLog;

    @FXML
    private Label noteMoy;

    @FXML
    private Label parking;

    @FXML
    private Label prixLog;

    @FXML
    private Label typeCham;

    @FXML
    private Label typeLog;

    @FXML
    private Button update;

    private Image image;
private Logement logement;

    @FXML
    void ModifierLogement(ActionEvent event) {
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

    @FXML
    void Retour1(ActionEvent event) {
       /* try {
            Stage Currentstage = (Stage) nom_Oeuvre.getScene().getWindow();
            Currentstage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../AfficherOeuvre.fxml"));
            Parent root = loader.load();
            afficherController = loader.getController();
            Stage stage = new Stage();
            stage.setTitle("Afficher Oeuvre");
            stage.setScene(new Scene(root));
            stage.show();
            System.out.println("Bonjour"+artisteId);
            afficherController.refreshScrollPane(oeuvre.getArtiste_id().getId());
            afficherController.serData(oeuvre.getArtiste_id());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
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

    private void showAlert(String suppressionRéussie, String s) {
    }

    @FXML
    void Retour(ActionEvent event) {

            try {
                System.out.println("testtttttttttt");

                // Charger le fichier FXML de la nouvelle page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogementFxml/LogementAffB.fxml"));
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



    public void setData(Logement logement){
        this.logement=logement;
        nomLog.setText(logement.getNom());
        typeLog.setText(logement.getType_log());
        localLog.setText(logement.getLocalisation());
        prixLog.setText((logement.getPrix()+ "DT/Personne"));
        noteMoy.setText(String.valueOf(logement.getNote_moyenne()));
        etatLog.setText(logement.getEtat());
        numLog.setText(( "+216"+logement.getNum()));

        // Fetch Equipement from database using EquipementCrud
       // EquipementCrud equipementCrud = new EquipementCrud();
        Equipement equipement = logement.equipement_id;
        System.out.println(equipement+"assssss");

        System.out.println(equipement+"hhh");
        // Set Equipement details
        if (equipement != null) {
            nbrCham.setText(String.valueOf(equipement.getNbr_chambre()));
            typeCham.setText(equipement.getTypes_de_chambre());
            desc.setText(equipement.getDescription());

            parking.setText(equipement.isParking() ? "oui" : "non");
            clim.setText(equipement.isClimatisation() ? "oui" : "non");
            internet.setText(equipement.isInternet() ? "oui" : "non");
        }

        // Set image
        String path = logement.getImage();
        try {
            image = new Image(new File(path).toURI().toURL().toString(), 207, 138, false, true);
            img.setImage(image);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void Accepter(MouseEvent mouseEvent) {
        System.out.println("abv");
        // Récupérer le texte actuel de l'état du logement
        String etat = etatLog.getText();

        // Vérifier si l'état actuel est "refusee"
        if (etat.equals("refusee")) {
            // Mettre à jour l'état du logement en "Acceptee"
            logement.setEtat("Acceptee");

            // Appeler la méthode modifier pour enregistrer les modifications
            LogementCrud logementCrud = new LogementCrud();
            logementCrud.modifier(logement);

            // Rafraîchir l'affichage de l'interface utilisateur
            System.out.println("aaaa");

        }
    }

    public void refusee(MouseEvent mouseEvent) {
        // Mettre à jour l'état du logement en refusé
        logement.setEtat("Refuse");

        // Appeler la méthode modifier pour enregistrer les modifications
        LogementCrud logementCrud = new LogementCrud();
        logementCrud.modifier(logement);

        // Rafraîchir l'affichage de l'interface utilisateur
    }



}
