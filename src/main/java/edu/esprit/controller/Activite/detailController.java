package edu.esprit.controller.Activite;

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


   // @FXML
    //private void click(MouseEvent mouseEvent) {
      //  if (myListener != null) {
        //    myListener.onClickListener(activite);
       // }
   // }

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

            img.setImage(new Image("http://localhost:8000/uploads/"+ activite.getImage()));
            System.out.println("http://localhost:8000/uploads/"+activite.getImage());
            // Gérer le cas où activite est null, par exemple, en effaçant les valeurs des labels et de l'image

        }
    }
    @FXML
    void onClose() {
        // Fermer la fenêtre
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }


}

