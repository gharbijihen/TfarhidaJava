package edu.esprit.controller;

import edu.esprit.entites.Activite;
import edu.esprit.entites.Categorie;
import edu.esprit.tests.MyListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;

public class detailController {
    @FXML
    private ImageView img;
    @FXML
    private Button closeButton;

    @FXML
    private Label locLabel;

    @FXML
    private Button map;

    @FXML
    private Label descLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label nbLabel;
    @FXML
    private Label nomLabel;
    @FXML
    private Label idLabel;

    private Activite activite;
    private Categorie categorie;
    private MyListener myListener;


//    @FXML
//    private void click(MouseEvent mouseEvent) {
//        if (myListener != null) {
//            myListener.onClickListener(activite);
//        }
//    }

    // Méthode pour définir les données de l'activité
    public void setDatadetail(Activite activite, MyListener myListener) {
        this.activite = activite;
        this.myListener = myListener;

        if (activite != null) {
            this.nomLabel.setText(activite.getNom());
            this.priceLabel.setText(activite.getPrix() + " DT/Personne");
            this.locLabel.setText(activite.getLocalisation());
            this.nbLabel.setText(activite.getNb_P() + " Participant");
            this.descLabel.setText(activite.getDescription_act());

            String imagePath = activite.getImage();
            if (imagePath != null && !imagePath.isEmpty()) {
                // Vérifiez si le fichier image existe
                File file = new File(imagePath);
                if (file.exists() && !file.isDirectory()) {
                    try {
                        // Charger l'image à partir du chemin d'accès spécifié
                        Image image = new Image(file.toURI().toString());
                        double desiredWidth = 600; // Spécifiez la largeur souhaitée
                        double desiredHeight = 600;
                        // Définir l'image dans l'élément ImageView
                        img.setImage(image);
                        img.setFitWidth(desiredWidth);
                        img.setFitHeight(desiredHeight);
                    } catch (Exception e) {
                        System.err.println("Erreur lors du chargement de l'image : " + e.getMessage());
                        // Gérer l'erreur de chargement de l'image
                    }
                } else {
                    System.err.println("Le fichier image n'existe pas : " + imagePath);
                    // Gérer le cas où le fichier image est introuvable
                }
            } else {
                System.err.println("Chemin d'accès à l'image non spécifié.");
                // Gérer le cas où le chemin d'accès de l'image n'est pas spécifié
            }
        } else {
            // Gérer le cas où activite est null, par exemple, en effaçant les valeurs des labels et de l'image
            this.nomLabel.setText("");
            this.priceLabel.setText("");
            this.locLabel.setText("");
            this.nbLabel.setText("");
            this.descLabel.setText("");
            this.img.setImage(null); // Effacer l'image
        }
    }
    @FXML
    void onClose() {
        // Fermer la fenêtre
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }


}

