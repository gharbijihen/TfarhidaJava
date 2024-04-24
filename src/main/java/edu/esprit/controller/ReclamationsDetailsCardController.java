package edu.esprit.controller;

import edu.esprit.entites.Reclamation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ReclamationsDetailsCardController  implements Initializable {



    @FXML
    private Text description;

    @FXML
    private ImageView img;
    public static Reclamation reclamations_static;
    public Reclamation reclamation;


    @FXML
    private Text Price;



    @FXML
    private Text stock;



   // private User user = GuiLoginController.user;

    private int found = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {



        // set product details
        reclamation = ReclamationsCardController.reclamation_static;
        // System.out.println(produit);





       /* if (product. getBlobImage() != null) {
            Image img = new Image(new ByteArrayInputStream(product.getBlobImage()));
            this.img.setImage(img);
        }*/
        Price.setText("Titre Reclamation: "+reclamation.getTitre()+","+String.valueOf(reclamation.getType()));
        description.setText(reclamation.getDescription_reclamation());

      //  float prixApresOffre = 0;



    }
    public void commande(MouseEvent mouseEvent) {
   //     RouterController.navigatepay("/fxml/Client/GuiPaiement.fxml",product);
    }

    //@FXML
    //  void partageFacebook(MouseEvent event) throws SQLException {
        /*Collecte produit = new Collecte();
        ProduitService produitService = new ProduitService();
        produit = produitService.getOneProduct(Collecte.getIdProduit());

        String appId = "232528662540085";
        String appSecret = "60988e9928012f06c205e07717bb4196";
        String accessTokenString = "EAADTe8xUrzUBALc1rb6aaElV1pappD7JSyoXACZAK83fZBP6OcsTKxVDvUIR5fq8q7kx5EBPiUiNU6CzWJBFLqxDZAQCmgm4YSlqXYrNsmeAbZBzTTIdYiZBprB0Gl7ubxoZAH4FPs9D5IwhmmHlMutCZCB7fhJcto7V1JtK5v33kgFYeopUIwYl1ZCJHFJqVP1zQdlmh1YJe9RegTMJ3Avu";

        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAppId(appId, appSecret);
        facebook.setOAuthAccessToken(new AccessToken(accessTokenString, null));

   /*     String msg = "A new product is available on ZeroWaste" + "\n***Product Name: " + produit.getNom_produit()
                + "\n***Product Description: "
                + produit.getDescription()
                + "\n***Product Price: "
                + produit.getPrix_produit() + "\n***Product Points: "
                + produit.getPrix_point_produit();
        try {
            /*facebook.postStatusMessage(msg);
            TrayNotificationAlert.notif("Product", "Product shared successfully.",
                    NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));
        } catch (FacebookException e) {
            e.printStackTrace();
            TrayNotificationAlert.notif("Product", "token was changed.",
                    NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
        }

        }


    }*/

}
