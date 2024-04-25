package edu.esprit.controller;


import edu.esprit.entites.Equipement;
import edu.esprit.entites.Logement;
import edu.esprit.tests.MyListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;

public class ShowLogement {
    @FXML
    private ImageView img;

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

    private Logement logement;
    private Equipement equipement;
    private MyListener myListener;


    @FXML
    private void click(MouseEvent mouseEvent) {
        if (myListener != null) {
            myListener.onClickListener(logement);
        }
    }

    // Méthode pour définir les données de l'activité
        public void setDatadetail(Logement logement, MyListener myListener, int id) {
        this.logement = logement;
        this.myListener = myListener;
        this.idLabel.setText(String.valueOf(id));
        if (logement != null) {
            this.nomLabel.setText(logement.getNom());
            this.priceLabel.setText(logement.getPrix() + " DT/Personne");
            this.locLabel.setText(logement.getLocalisation());
            this.nbLabel.setText( " +216"+logement.getNum()) ;
            this.descLabel.setText("Note:"+logement.getNote_moyenne());

            String imagePath = logement.getImage();
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
    public void initialize() {
        // Initialise idLabel
        idLabel = new Label();
    }

}