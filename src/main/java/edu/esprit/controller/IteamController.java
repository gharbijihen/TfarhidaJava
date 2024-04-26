package edu.esprit.controller;

import edu.esprit.entites.Activite;
import edu.esprit.entites.Categorie;
import edu.esprit.servies.ActiviteCrud;
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
    @FXML
    private Label idLabel;
    @FXML
    private Button voirDetailsButton;
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
        this.idLabel.setText(String.valueOf(activite.getId()));
        String idLabelText = idLabel.getText().trim();
        try {
            int idFromLabel = Integer.parseInt(idLabelText);
            System.out.println("ID récupéré à partir du label : " + idFromLabel);
        } catch (NumberFormatException e) {
            System.out.println("Erreur : Impossible de convertir le texte en entier.");
            e.printStackTrace();
        }
        img.setFitWidth(400); // Ajuster la largeur de l'image
        img.setFitHeight(300); // Ajuster la hauteur de l'image


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
  /* public void initialize() {
        // Supposons que vous avez l'ID de l'activité dans une variable nommée idActivite
        int idActivite = 46; // Remplacez 123 par l'ID réel de l'activité
        voirDetailsButton.setUserData(idActivite);
    }*/
  @FXML

  public void initialize() {
      int idActivite = getIdFromLabel(idLabel);
      voirDetailsButton.setUserData(idActivite);
      System.out.println("ID de l'activité défini comme userData : " + idActivite);
  }

    private int getIdFromLabel(Label label) {
            try {
                String text = label.getText().trim();
                if (!text.isEmpty()) {
                    return Integer.parseInt(text);
                } else {
                    System.out.println("Le texte du label est vide.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erreur lors de la conversion du texte en entier : " + e.getMessage());
            }
            return -1;
        }




    @FXML
    void afficherDetails(ActionEvent event) {
        // Récupérer l'identifiant de l'activité associée au bouton
        int id = Integer.parseInt(idLabel.getText().trim()); // Assurez-vous que le label contient bien l'ID de l'activité

        Activite activite = ActiviteCrud.getActiviteParId(id);

        if (activite != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/show.fxml"));
                Parent root = loader.load();

                // Accéder au contrôleur de vue de détails
                detailController controller = loader.getController();

                // Appeler la méthode setDatadetail du contrôleur de vue de détails pour initialiser les données de l'activité
                controller.setDatadetail(activite, null);

                // Créer une nouvelle scène
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Gérer le cas où l'activité est null
            System.out.println("Aucune activité trouvée avec l'ID : " + id);
        }
    }

}









