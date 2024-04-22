package edu.esprit.controller;

import edu.esprit.entites.Activite;
import edu.esprit.servies.ActiviteCrud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class ModifierActiviteController {

    @FXML
    private TextField nomm;
    @FXML
    private TextField prixx;
    @FXML
    private TextField categoriee;
    @FXML
    private TextField localisationn;
    @FXML
    private TextField nombreP;
    @FXML
    private TextField etatt;
    @FXML
    private TextField descriptionActt;

    @FXML
    private Button save;

    @FXML
    private Text errorPrix;
    @FXML
    private Text errorName;
    @FXML
    private Text errorLocalisation;
    @FXML
    private Text errorCategorie;
    @FXML
    private Text errorNbp;
    @FXML
    private Text errorEtat;
    @FXML
    private Text errorDesc;

    @FXML
    private Button selectImage;
    @FXML
    private ImageView imageView;

    private File selectedImageFile;

    private String imagePathInDatabase;
    private Activite activite;

    public void initData(Activite activite,File selectedImageFile) {
        this.activite = activite; // Assigner l'activité reçue à la variable de classe
        // Utilisez les données de l'activité pour initialiser les champs de saisie
        nomm.setText(activite.getNom());
        prixx.setText(String.valueOf(activite.getPrix()));
        categoriee.setText(String.valueOf(activite.getCategorie_id()));
        localisationn.setText(activite.getLocalisation());
        nombreP.setText(String.valueOf(activite.getNb_P()));
        etatt.setText(activite.getEtat());
        descriptionActt.setText(activite.getDescription_act());
        if (selectedImageFile != null) {
            // Affichez l'image sélectionnée dans l'ImageView
            try {
                Image image = new Image(new FileInputStream(selectedImageFile));
                imageView.setImage(image);
            } catch (FileNotFoundException e) {
                System.out.println("Erreur lors du chargement de l'image : " + e.getMessage());
            }
        } else {
            // Aucun fichier image sélectionné, conservez l'image existante de l'activité
            String imagePath = activite.getImage();
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                Image image = new Image(imageFile.toURI().toString());
                imageView.setImage(image);
            } else {
                // Le fichier image n'existe pas, affichez un message d'erreur ou une image par défaut
                System.out.println("Le fichier image n'existe pas : " + imagePath);
            }
        }
       /* String imagePath = activite.getImage();
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            Image image = new Image(imageFile.toURI().toString());
            imageView.setImage(image);
        } else {
            // Le fichier image n'existe pas, affichez un message d'erreur ou une image par défaut
            System.out.println("Le fichier image n'existe pas : " + imagePath);
        }*/
    }

    @FXML
    void browseImageAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                selectedImageFile = selectedFile;
                imagePathInDatabase = selectedFile.getAbsolutePath();
                Image image = new Image(new FileInputStream(selectedFile));
                imageView.setImage(image);
            } catch (FileNotFoundException e) {
                System.out.println("Erreur lors du chargement de l'image : " + e.getMessage());
            }
        } else {
            System.out.println("Aucun fichier sélectionné");
        }
    }

    @FXML
    void modifierActiviteAction(ActionEvent event) {
        if (isInputValid()) {
            // Récupérer l'ID de l'activité sélectionnée
            int activiteId = activite.getId();

            // Si l'ID de l'activité est valide
            if (activiteId != 0) {
                // Récupérez d'abord les nouvelles valeurs saisies par l'utilisateur dans les champs de texte
                try {
                    int categorie_id = Integer.parseInt(categoriee.getText());
                    String nom = nomm.getText();
                    int prix = Integer.parseInt(prixx.getText());
                    int nb_P = Integer.parseInt(nombreP.getText());
                    String localisation = localisationn.getText();
                    String etat = etatt.getText();
                    String description_act = descriptionActt.getText();

                    // Créez un objet Activite avec les nouvelles valeurs
                    Activite activiteModifiee = new Activite();
                    activiteModifiee.setId(activiteId); // Assurez-vous de définir l'ID de l'activité
                    activiteModifiee.setCategorie_id(categorie_id);
                    activiteModifiee.setNom(nom);
                    activiteModifiee.setPrix(prix);
                    activiteModifiee.setNb_P(nb_P);
                    activiteModifiee.setLocalisation(localisation);
                    activiteModifiee.setEtat(etat);
                    activiteModifiee.setDescription_act(description_act);

                    // Si une nouvelle image a été sélectionnée, mettez à jour le chemin de l'image
                    if (selectedImageFile != null) {
                        activiteModifiee.setImage(selectedImageFile.getAbsolutePath());
                    } else {
                        // Si aucune nouvelle image n'a été sélectionnée, conservez le chemin de l'image existante
                        activiteModifiee.setImage(activite.getImage());
                    }

                    // Utilisez votre service ActiviteCrud pour mettre à jour l'activité dans la base de données
                    ActiviteCrud service = new ActiviteCrud();
                    service.modifier(activiteModifiee);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("workout updated successfully");
                    alert.showAndWait();

                    System.out.println("Activité modifiée avec succès !");
                    // Vous pouvez également afficher une boîte de dialogue ou un message pour informer l'utilisateur
                } catch (NumberFormatException e) {
                    System.out.println("Erreur de format : Assurez-vous que les champs Prix et Nombre Participant sont des nombres entiers.");
                    // Afficher un message d'erreur dans l'interface utilisateur
                } catch (SQLException e) {
                    System.out.println("Erreur lors de la modification de l'activité : " + e.getMessage());
                    // Afficher un message d'erreur dans l'interface utilisateur
                }
            } else {
                System.out.println("L'ID de l'activité sélectionnée est invalide.");
                // Afficher un message d'erreur dans l'interface utilisateur
            }
        } else {
            // Les données saisies par l'utilisateur ne sont pas valides, affichez un message d'erreur ou effectuez une action appropriée
            System.out.println("Les données saisies ne sont pas valides. Veuillez vérifier les champs.");
            // Vous pouvez également afficher des messages d'erreur spécifiques à chaque champ si nécessaire
        }
    }



    private boolean isInputValid() {
        boolean isValid = true;
        if (categoriee.getText().isEmpty() || !categoriee.getText().matches("^[0-9]+$")) {
            errorCategorie.setText("id categorie est requis et doit contenir uniquement des nombres");
            isValid = false;
        } else {
            errorCategorie.setText("");
        }

        if (nomm.getText().isEmpty() || !nomm.getText().matches("^[a-zA-Z]+$")) {
            errorName.setText("Nom is required and should not contain numbers");
            isValid = false;
        } else {
            errorName.setText("");
        }

        if (descriptionActt.getText().isEmpty() || !descriptionActt.getText().matches("^[a-zA-Z]+$")) {
            errorDesc.setText("Description is required and should not contain numbers ");
            isValid = false;
        } else {
            errorDesc.setText("");
        }

        if (localisationn.getText().isEmpty() || !localisationn.getText().matches("^[a-zA-Z]+$")) {
            errorLocalisation.setText("Localisation is required and should not contain numbers ");
            isValid = false;
        } else {
            errorLocalisation.setText("");
        }
        if (etatt.getText().isEmpty() || !etatt.getText().matches("^[a-zA-Z]+$")) {
            etatt.setText("etat is required and should not contain numbers ");
            isValid = false;
        } else {
            errorLocalisation.setText("");
        }

        if (prixx.getText().isEmpty() || !prixx.getText().matches("^[0-9]+$")) {
            errorPrix.setText("Le prix est requis et doit contenir uniquement des nombres");
            isValid = false;
        } else {
            errorPrix.setText("");
        }
        if (nombreP.getText().isEmpty() || !nombreP.getText().matches("^[0-9]+$")) {
            errorNbp.setText("Le prix est requis et doit contenir uniquement des nombres");
            isValid = false;
        } else {
            errorNbp.setText("");
        }

        return isValid;
    }
}

