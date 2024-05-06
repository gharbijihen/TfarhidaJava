package edu.esprit.controller;

import edu.esprit.entites.Moyen_transport;
import edu.esprit.tests.MyListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.File;

public class ShowMoyen {
    @FXML
    private ImageView img;

    @FXML
    private Label TypeLabel;

    @FXML
    private Button map;

    @FXML
    private Label CapaciteLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label LieuLabel;
    @FXML
    private Label EtatLabel;
    @FXML
    private Label idLabel;

    private Moyen_transport moy;
    private MyListener myListener;


    @FXML
    private void click(MouseEvent mouseEvent) {
        if (myListener != null) {
            myListener.onClickListener(moy);

        }
    }


    // Méthode pour définir les données de l'activité
    public void setDatadetail(Moyen_transport moyen, MyListener myListener) {
        this.moy = moyen;
        this.myListener = myListener;

        if (moyen != null) {
            this.CapaciteLabel.setText(Integer.toString(moyen.getCapacite()));
            this.LieuLabel.setText(moyen.getLieu());
            if (moyen.isEtat()) {
                this.EtatLabel.setText("Disponible");
            } else {
                this.EtatLabel.setText("Indisponible");
            }
            try {
                this.TypeLabel.setText(moyen.getType());
            }catch (Exception e) {
                System.err.println("Erreur lors du chargement de l'image : " + e.getMessage());
                // Gérer l'erreur de chargement de l'image
            }


            String imagePath = moyen.getImage();
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
            this.TypeLabel.setText("");
            this.CapaciteLabel.setText("");
            this.LieuLabel.setText("");
            this.EtatLabel.setText("");
            this.img.setImage(null); // Effacer l'image
        }
    }


    public void Retour(javafx.event.ActionEvent event) {
        System.out.println("retourrrr");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}