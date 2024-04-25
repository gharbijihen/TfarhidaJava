package edu.esprit.controller;

import edu.esprit.entites.Logement;
import edu.esprit.servies.LogementCrud;
import edu.esprit.tests.MyListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class IteamLogementController {

    @FXML
    private ImageView imgLogement;
    @FXML
    private Button voirDetailsButton;
    @FXML
    private Label loalisationLogement;

    @FXML
    private Button map;

    @FXML
    private Label nameLogement;

    @FXML
    private Label phoneLogement;

    @FXML
    private Label priceLogement;

    private Logement logement;
    private MyListener myListener;

    @FXML
    private Label idLabel;

    // Méthode appelée lors du clic sur l'élément logement
    @FXML
    private void click(MouseEvent mouseEvent) {
        this.myListener.onClickListener(this.logement);
    }
    @FXML

    public void afficherDetails(ActionEvent event) {
        if (idLabel != null && idLabel.getText() != null && !idLabel.getText().isEmpty()) {
            try {
                int id = Integer.parseInt(idLabel.getText().trim());
                Logement logement = LogementCrud.getLogementParId(id);

                if (logement != null) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowLogement.fxml"));
                    Parent root = loader.load();
                    ShowLogement controller = loader.getController();
                    controller.setDatadetail(logement, null, id);
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } else {
                    System.out.println("Aucun logement trouvé avec l'ID : " + id);
                }
            } catch (NumberFormatException e) {
                System.err.println("La chaîne n'est pas un entier valide : " + idLabel.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("L'élément idLabel n'est pas correctement initialisé.");
        }
    }


    // Méthode pour définir les données du logement dans l'interface utilisateur
    public void setData(Logement logement, MyListener myListener) {
        this.logement = logement;
        this.myListener = myListener;
        this.nameLogement.setText(logement.getNom());
        this.priceLogement.setText(logement.getPrix() + "DT/Personne");
        this.loalisationLogement.setText(logement.getLocalisation());
        this.phoneLogement.setText("+216" + logement.getNum());
        imgLogement.setFitWidth(450); // Ajuster la largeur de l'image
        imgLogement.setFitHeight(350); // Ajuster la hauteur de l'image
        String imagePath = logement.getImage();
        idLabel.setText(String.valueOf(logement.getId()));

        if (imagePath != null && !imagePath.isEmpty()) {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                Image image = new Image(imageFile.toURI().toString());
                this.imgLogement.setImage(image);
            } else {
                System.err.println("L'image n'existe pas à l'emplacement spécifié : " + imagePath);
                // Afficher une image par défaut ou un message d'erreur
                // Par exemple :
                // Image image = new Image(getClass().getResourceAsStream("default_image.png"));
                // this.imgLogement.setImage(image);
            }
        } else {
            System.err.println("Le chemin d'accès à l'image est vide ou invalide pour le logement : " + logement.getNom());
            // Afficher une image par défaut ou un message d'erreur
            // Par exemple :
            // Image image = new Image(getClass().getResourceAsStream("default_image.png"));
            // this.imgLogement.setImage(image);
        }
    }




    @FXML
    public void initialize() {
        if (idLabel != null && idLabel.getText() != null && !idLabel.getText().isEmpty()) {
            try {
                int idLogement = Integer.parseInt(idLabel.getText().trim());
                voirDetailsButton.setUserData(idLogement);

                // Ajouter un message de journalisation pour vérifier la valeur de idActivite
                System.out.println("ID de l'activité défini comme userData : " + idLogement);
            } catch (NumberFormatException e) {
                System.err.println("La chaîne n'est pas un entier valide : " + idLabel.getText());
            }
        } else {
            System.err.println("Label is null or empty.");
        }
    }

    public int getIdFromLabel() {
        int id = 0;
        if (idLabel != null && idLabel.getText() != null && !idLabel.getText().isEmpty()) {
            try {
                id = Integer.parseInt(idLabel.getText().trim());
            } catch (NumberFormatException e) {
                System.err.println("La chaîne n'est pas un entier valide : " + idLabel.getText());
            }
        } else {
            System.err.println("Label is null or empty.");
        }
        return id;
    }


}




