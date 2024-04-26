package edu.esprit.controller;

import edu.esprit.entites.Logement;
import edu.esprit.servies.LogementCrud;
import edu.esprit.tools.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class ModifierLogementB {

    @FXML
    private Button ButtonModifierLogement;

    @FXML
    private TextField equipementId;

    @FXML
    private Text errorLocalisation;

    @FXML
    private Text errorNom;

    @FXML
    private Text errorNote;

    @FXML
    private Text errorNum;

    @FXML
    private Text errorPrix;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField localisation;

    @FXML
    private TextField nom;

    @FXML
    private TextField note;

    @FXML
    private TextField num;

    @FXML
    private TextField prix;

    @FXML
    private Button selectImage;

    @FXML
    private ComboBox<String> type_log;



    private Logement logement;
    private File selectedImageFile;
    ObservableList<String> typeLog = FXCollections.observableArrayList(Data.typeLogement);

    private String imagePathInDatabase;

    public void initData(Logement logement, File selectedImageFile) {
        this.logement = logement; // Assigner l'activité reçue à la variable de classe
        // Utilisez les données de l'activité pour initialiser les champs de saisie
        nom.setText(logement.getNom());
        prix.setText(String.valueOf(logement.getPrix()));
        type_log.setItems(typeLog);
       // typelLog.setText(String.valueOf(logement.getType_log()));
        localisation.setText(logement.getLocalisation());
        note.setText(String.valueOf(logement.getNote_moyenne()));
        num.setText(String.valueOf(logement.getNote_moyenne()));
        equipementId.setText(String.valueOf(logement.getEquipement()));
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
            String imagePath = logement.getImage();
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
    void modifierLogementAction(ActionEvent event) {
        if (nom.getText().isEmpty()) {
            nom.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
        } else {
            nom.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }
        if (note.getText().isEmpty()) {
            note.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
        } else {
            note.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }
        if (localisation.getText().isEmpty()) {
            localisation.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
        } else {
            localisation.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }
        if (prix.getText().isEmpty()) {
            prix.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
        } else {
            prix.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }
        if (num.getText().isEmpty()) {
            num.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
        } else {
            num.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }
        if (type_log.getValue()==null ) {
            type_log.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
        } else {
            type_log.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }
        if (isInputValid()) {
            // Récupérer l'ID de l'activité sélectionnée
            int LogementId = logement.getId();

            // Si l'ID de l'activité est valide
            if (LogementId != 0) {
                // Récupérez d'abord les nouvelles valeurs saisies par l'utilisateur dans les champs de texte
                try {
                    int equipement_Id = Integer.parseInt(equipementId.getText());
                    String nomL = nom.getText();
                    String typeL = type_log.getValue();
                    int prixL = Integer.parseInt(prix.getText());
                    int numL = Integer.parseInt(num.getText());
                    int noteL = Integer.parseInt(note.getText());
                    String localisationL = localisation.getText();

                    // Créez un objet Activite avec les nouvelles valeurs
                    Logement logementModifiee = new Logement();
                    logementModifiee.setId(LogementId); // Assurez-vous de définir l'ID de l'activité
                    logementModifiee.setNom(nomL);
                    logementModifiee.setNum(numL);
                    logementModifiee.setPrix(prixL);
                    logementModifiee.setNote_moyenne(noteL);
                    logementModifiee.setLocalisation(localisationL);
                    logementModifiee.setType_log(typeL);

                    // Si une nouvelle image a été sélectionnée, mettez à jour le chemin de l'image
                    if (selectedImageFile != null) {
                        logementModifiee.setImage(selectedImageFile.getAbsolutePath());
                    } else {
                        // Si aucune nouvelle image n'a été sélectionnée, conservez le chemin de l'image existante
                        logementModifiee.setImage(logement.getImage());
                    }

                    // Utilisez votre service ActiviteCrud pour mettre à jour l'activité dans la base de données
                    LogementCrud service = new LogementCrud();
                    service.modifier(logementModifiee);

                    System.out.println("Logement modifiée avec succès !");
                    showAlert("Logement modifiee", "Votre logement a été modiffie avec succès.");

                    // Vous pouvez également afficher une boîte de dialogue ou un message pour informer l'utilisateur
                } catch (NumberFormatException e) {
                    System.out.println("Erreur de format : Assurez-vous que les champs Prix et nombre de chambre  Participant sont des nombres entiers.");
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
        naviguezVersEquipement(event);

    }
    @FXML
    void naviguezVersEquipement(ActionEvent event) {

            try {
                // Charger le fichier FXML de la nouvelle page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogementAffB.fxml"));
                Parent root = loader.load();

                // Créer une nouvelle scène avec la nouvelle page
                Scene scene = new Scene(root);

                // Obtenir la fenêtre actuelle à partir de l'événement
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Définir la nouvelle scène sur la fenêtre et l'afficher
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    @FXML
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean isInputValid() {
        boolean isValid = true;


        if (nom.getText().isEmpty() || !nom.getText().matches("^[a-z A-Z]+$")) {
            errorNom.setText("Nom is required and should not contain numbers");
            isValid = false;
        } else {
            errorNom.setText("");
        }

        if (localisation.getText().isEmpty() || !localisation.getText().matches("^[a-z A-Z]+$")) {
            errorLocalisation.setText("Localisation is required and should not contain numbers ");
            isValid = false;
        } else {
            errorLocalisation.setText("");
        }

        if (num.getText().isEmpty() || !num.getText().matches("^[0-9]+$")) {
            errorNum.setText("Numero est requis et doit contenir uniquement 8 nombres");
            isValid = false;
        } else {
            errorNum.setText("");
        }


        if (prix.getText().isEmpty() || !prix.getText().matches("^[0-9]+$")) {
            errorPrix.setText("Le prix est requis et doit contenir uniquement des nombres");
            isValid = false;
        } else {
            errorPrix.setText("");
        }
        if (note.getText().isEmpty() || !note.getText().matches("^[0-9]+$")) {
            errorNote.setText("Le prix est requis et doit contenir uniquement des nombres");
            isValid = false;
        } else {
            errorNote.setText("");
        }

        return isValid;
    }

    @FXML
    void selectImageAction(ActionEvent event) {
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

}
