package edu.esprit.controller;

import com.google.zxing.qrcode.encoder.QRCode;
import edu.esprit.entites.Activite;
import edu.esprit.entites.Categorie;
import edu.esprit.servies.ActiviteCrud;
import edu.esprit.tests.MyListener;
import javafx.event.ActionEvent;
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


import java.io.File;
import java.io.IOException;

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
//    @FXML
//    private void click(MouseEvent mouseEvent) {
//        if (myListener != null) {
//            myListener.onClickListener(activite);
//        }
//    }

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
   public void setLocal(String localisation) {
        if (this.loalisationLabel.getText().isEmpty()) {
            this.loalisationLabel.setText(localisation);
        }
    }



    public Activite getActivite() {
        return activite;
    }
    @FXML
    void QrCode(ActionEvent event) {
// Récupérer le logement associé à cette carte
        Activite selectedAct =getActivite();

        try {
            // Chargez le fichier FXML des détails du logement
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/QRcode.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            // Obtenez le contrôleur des détails du logement
            QrCode QrController = loader.getController();

            // Passez les informations du logement sélectionné au contrôleur des détails du logement
            QrController.setAct(selectedAct);

            // Afficher la fenêtre modale des détails du logement
            stage.setTitle("Scan du QrCode");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/showM.fxml"));
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
    //Map

    @FXML
    private void afficherCarte(ActionEvent event) {
        String location = loalisationLabel.getText(); // Récupérer la localisation de l'activité
        openMap(location);
    }
    private void openMap(String location) {
        try {
            // Charger le fichier FXML de la vue de la carte
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MapM.fxml"));
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
   /*@FXML
    private void map(ActionEvent event) throws IOException {
        // Récupérer la localisation de l'activité sélectionnée
        String location = loalisationLabel.getText(); // Assurez-vous que la localisation est correctement définie dans votre vue

        // Charger le fichier FXML de la vue de la carte
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MapM.fxml"));
        Parent root = loader.load();

        // Obtenir le contrôleur de la vue de la carte
        MapController mapController = loader.getController();

        // Passer la localisation au contrôleur de la vue de la carte
        mapController.setLocal(location);

        // Afficher la vue de la carte dans une nouvelle fenêtre
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        // Fermer la fenêtre actuelle
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }*/

}









