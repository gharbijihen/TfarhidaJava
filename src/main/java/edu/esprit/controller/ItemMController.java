package edu.esprit.controller;

import edu.esprit.servies.Moyen_transportCrud;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import edu.esprit.entites.Moyen_transport;
import edu.esprit.tests.MyListener;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.Button;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class ItemMController {

    @FXML
    private ImageView img;

    @FXML
    public Label localisationLabel;

    @FXML
    private Label typeLabel;


    @FXML
    private Label idLabel;

    @FXML
    private Button detailsB;


    private Moyen_transport moyen_transport;

    private MyListener myListener;

    public ItemMController() {}

    @FXML
    private void click(MouseEvent mouseEvent) {
        if (myListener != null) {
            myListener.onClickListener(moyen_transport);
        }
    }

    public void setData(Moyen_transport moyen_transport, MyListener myListener) {
        this.moyen_transport = moyen_transport;
        this.myListener = myListener;
        this.typeLabel.setText(moyen_transport.getType());
        this.localisationLabel.setText(moyen_transport.getLieu());
        this.idLabel.setText(String.valueOf(moyen_transport.getId()));
        String idLabelText = idLabel.getText().trim();
        try {
            int idFromLabel = Integer.parseInt(idLabelText);
            System.out.println("ID récupéré à partir du label : " + idFromLabel);
        } catch (NumberFormatException e) {
            System.out.println("Erreur : Impossible de convertir le texte en entier.");
            e.printStackTrace();
        }
        img.setFitWidth(300);
        img.setFitHeight(300);


        String imagePath = moyen_transport.getImage();
        if (imagePath != null) {
            // Charger l'image à partir du chemin d'accès spécifié
            Image image = new Image(new File(imagePath).toURI().toString());
            this.img.setImage(image);
        } else {
            // Afficher une image par défaut ou gérer le cas où l'image est absente
        }

    }
    public void initialize() {
        int idmoy= getIdFromLabel(idLabel);
        detailsB.setUserData(idmoy);
        System.out.println(idmoy);
        // Ajouter un message de journalisation pour vérifier la valeur de idActivite
        System.out.println("ID de l'activité défini comme userData : " + idmoy);
    }
    private int getIdFromLabel(Label label) {
        try {
            String text = label.getText().trim();
            if (!text.isEmpty() && !text.equalsIgnoreCase("ID")) {
                return Integer.parseInt(text);
            } else {
                System.out.println("Le texte du label est vide ou égal à 'ID'.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erreur lors de la conversion du texte en entier : " + e.getMessage());
        }
        return -1; // Valeur par défaut ou une valeur qui indique une erreur
    }




    public void AfficherDetails(javafx.event.ActionEvent actionEvent) {
        // Récupérer l'identifiant de l'activité associée au bouton
        int id = Integer.parseInt(idLabel.getText().trim()); // Assurez-vous que le label contient bien l'ID de l'activité

        // Récupérer l'activité correspondante à partir de votre source de données
        Moyen_transport moyen = Moyen_transportCrud.getMoyenParId(id);

        if (moyen != null) {
            try {
                // Charger la vue FXML de détails
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/show.fxml"));
                Parent root = loader.load();

                // Accéder au contrôleur de vue de détails
                ShowMoyen controller = loader.getController();

                // Appeler la méthode setDatadetail du contrôleur de vue de détails pour initialiser les données de l'activité
                controller.setDatadetail(moyen, null);

                // Créer une nouvelle scène
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Gérer le cas où l'activité est null
            System.out.println("Aucune moyen trouvée avec l'ID : " + id);
        }
    }
    }
