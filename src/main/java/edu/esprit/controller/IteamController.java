package edu.esprit.controller;

import edu.esprit.entites.Activite;
import edu.esprit.entites.Categorie;
import edu.esprit.tests.MyListener;
import edu.esprit.tools.MyConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IteamController {

    @FXML
    private ImageView img;

    @FXML
    private Label loalisationLabel;

    @FXML
    private Button map;

    @FXML
    private Label nameLabel;

    @FXML
    private Label nbpLabel;

    @FXML
    private Label priceLable;
    @FXML
    private Label typeLabel;
    @FXML
    private Label descLabel;

    private Activite activite;
    private Categorie categorie;
    private MyListener myListener;

    // Constructeur par défaut
    public IteamController() {}

    public void setActivite(Activite activite) {
        this.activite = activite;
    }
    @FXML
    private void click(MouseEvent mouseEvent) {
        if (myListener != null) {
            myListener.onClickListener(activite);
        }
    }

    // Méthode pour définir les données de l'activité
    public void setData(Activite activite, MyListener myListener) {
        this.activite = activite;
        this.myListener = myListener;
        this.nameLabel.setText(activite.getNom());
        this.priceLable.setText(activite.getPrix() + " DT/Personne");
        this.loalisationLabel.setText(activite.getLocalisation());
        this.nbpLabel.setText(activite.getNb_P() + " Participant");
        img.setFitWidth(350); // Ajuster la largeur de l'image
        img.setFitHeight(350); // Ajuster la hauteur de l'image


        String imagePath = activite.getImage();
        if (imagePath != null) {
            // Charger l'image à partir du chemin d'accès spécifié
            Image image = new Image(new File(imagePath).toURI().toString());
            this.img.setImage(image);
        } else {
            // Afficher une image par défaut ou gérer le cas où l'image est absente
        }

    }
    public void setData1(Categorie categorie, MyListener myListener) {
        this.categorie = categorie;
        this.myListener = myListener;
        this.typeLabel.setText(categorie.getType_categorie());
        this.descLabel.setText(categorie.getDescription());

    }
    @FXML
    void voirDetails(ActionEvent event) {
        // Code pour charger la vue de l'affichage de la catégorie correspondante
        // Par exemple :
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/IteamC.fxml"));
        Parent root;
        try {
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void afficherDetails(ActionEvent event) throws IOException {
        // Récupérer le bouton sur lequel l'événement a été déclenché
        Button detailButton = (Button) event.getSource();

        // Récupérer l'activité associée au bouton à partir de la propriété userData
        Activite activite = (Activite) detailButton.getUserData();

        // Afficher l'ID de l'activité dans une boîte de dialogue contextuelle
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Détails de l'activité");
        if (activite != null) {
            // Appeler des méthodes sur l'objet activite
            alert.setHeaderText("ID de l'activité: " + activite.getId());
        } else {
            // Gérer le cas où activite est null
            System.out.println("L'objet activite est null.");
        }        alert.setContentText("Autres détails de l'activité...");
        alert.showAndWait();

        // Charger la vue FXML de détails
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/show.fxml"));
        Parent root = loader.load();

        // Accéder au contrôleur de vue de détails
        detailController controller = loader.getController();

        // Appeler la méthode setDatadetail du contrôleur de vue de détails pour initialiser les données de l'activité
        controller.setDatadetail(activite,null);

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Accéder à la fenêtre principale (stage)
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }



}





