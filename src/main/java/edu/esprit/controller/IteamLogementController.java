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

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;

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

        idLabel.setText(String.valueOf(logement.getId()));
        // Récupérer l'image à partir de la base de données
        String imagePath = logement.getImage();
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                byte[] imageData = Files.readAllBytes(Paths.get(imagePath));
                if (imageData != null && imageData.length > 0) {
                    // Convertir les données d'image en image JavaFX
                    Image image = new Image(new ByteArrayInputStream(imageData));
                    imgLogement.setImage(image);
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(1000); // Définissez la largeur souhaitée
                    imageView.setFitHeight(600);
                    imgLogement.setImage(imageView.snapshot(null, null));
                } else {
                    System.err.println("L'image est vide.");
                }
            } catch (IOException e) {
                System.err.println("Erreur lors de la lecture de l'image : " + e.getMessage());
            }
        } else {
            System.err.println("Chemin d'accès à l'image non spécifié.");
        }}
    // Méthode pour redimensionner une image
    private Image resizeImage(Image image, int width, int height) {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        java.awt.Image scaledImage = bufferedImage.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        graphics.dispose();
        return SwingFXUtils.toFXImage(bufferedImage, null);
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


    @FXML
    private void afficherCarte(ActionEvent event) {
        String location = loalisationLogement.getText(); // Récupérer la localisation de l'activité
        openMap(location);
    }
    private void openMap(String location) {
        try {
            // Charger le fichier FXML de la vue de la carte
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Map.fxml"));
            Parent root = loader.load();

            // Obtenir le contrôleur de la vue de la carte
            MapController mapController = loader.getController();

            // Passer la localisation au contrôleur de la vue de la carte
            mapController.setLocation(location);

            // Afficher la vue de la carte dans une nouvelle fenêtre
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




