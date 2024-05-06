package edu.esprit.controller;

import edu.esprit.entites.Moyen_transport;
import edu.esprit.servies.Moyen_transportCrud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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

public class ModifierMoyen {

    @FXML
    private Button ButtonAjouterMoy;

    @FXML
    private TextField capacite;

    @FXML
    private Text errorCapacite;

    @FXML
    private Text errorEtat;

    @FXML
    private Text errorLieu;

    @FXML
    private Text errorType;

    @FXML
    private Text errorValide;

    @FXML
    private CheckBox etat;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField lieu;

    @FXML
    private Button selectImage;

    @FXML
    private TextField type;

    @FXML
    private CheckBox valide;
    @FXML
    private Button browseImage;
    @FXML
    private TextField selectedImagePath;

    private String imagePathInDatabase;

    private File selectedImageFile;

    @FXML
    private TextField imagePath;
    private Moyen_transport moyen;

    public void initData(Moyen_transport moyen,File selectedImageFile) {
        this.moyen = moyen; // Assigner l'activité reçue à la variable de classe
        // Utilisez les données de l'activité pour initialiser les champs de saisie
        type.setText(moyen.getType());
        capacite.setText(String.valueOf(moyen.getCapacite()));
        lieu.setText(moyen.getLieu());
        etat.setText(String.valueOf(etat.getText()));
        valide.setText(String.valueOf(valide.getText()));
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
            String imagePath = moyen.getImage();
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
    void modifierMoyenAction(ActionEvent event) {
        if (isInputValid()) {
            // Récupérer l'ID de l'activité sélectionnée
            int moyenId = moyen.getId();

            // Si l'ID de l'activité est valide
            if (moyenId != 0) {
                // Récupérez d'abord les nouvelles valeurs saisies par l'utilisateur dans les champs de texte
                try {

                    String typee = type.getText();
                    int capacitee = Integer.parseInt(capacite.getText());
                    String lieuu = lieu.getText();
                    boolean etatt= etat.isSelected();
                    boolean validee = valide.isSelected();

                    // Créez un objet Activite avec les nouvelles valeurs
                    Moyen_transport moyenModifiee = new Moyen_transport();
                    moyenModifiee.setId(moyenId);
                    moyenModifiee.setType(typee);
                    moyenModifiee.setCapacite(capacitee);
                    moyenModifiee.setLieu(lieuu);
                    moyenModifiee.setEtat(etatt);
                    moyenModifiee.setValide(validee);

                    // Si une nouvelle image a été sélectionnée, mettez à jour le chemin de l'image
                    if (selectedImageFile != null) {
                        moyenModifiee.setImage(selectedImageFile.getAbsolutePath());
                    } else {
                        // Si aucune nouvelle image n'a été sélectionnée, conservez le chemin de l'image existante
                        moyenModifiee.setImage(moyen.getImage());
                    }

                    // Utilisez votre service ActiviteCrud pour mettre à jour l'activité dans la base de données
                    Moyen_transportCrud service = new Moyen_transportCrud();
                    service.modifier(moyenModifiee);
                    initData(moyen,selectedImageFile);
                    Node source = (Node) event.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                    System.out.println("Moyen modifiée avec succès !");
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

        // Validate and display error messages
        if (type.getText().isEmpty() || !type.getText().matches("^[a-zA-Z]+$")) {
            errorType.setText("Nom is required and should not contain numbers");
            isValid = false;
        } else {
            errorType.setText("");
        }

        if (lieu.getText().isEmpty() || !lieu.getText().matches("^[a-zA-Z]+$")) {
            errorLieu.setText("Adresse is required and should not contain numbers ");
            isValid = false;
        } else {
            errorLieu.setText("");
        }

        if (capacite.getText().isEmpty()) {
            errorCapacite.setText("Note is required");
            isValid = false;
        }  else {
            errorCapacite.setText("");
        }

        return isValid;
    }}