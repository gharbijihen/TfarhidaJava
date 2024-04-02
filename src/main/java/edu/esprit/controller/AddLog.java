package edu.esprit.controller;

import edu.esprit.servies.LogementCrud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import java.io.File;
import javafx.stage.FileChooser;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import edu.esprit.entites.Logement;
import javafx.scene.control.TextField;


public class AddLog {
    @FXML
    private Button ButtonAjouterLog;

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
    private ImageView image;

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
    private ImageView  imageView;;

    private File selectedImageFile;

    @FXML
    void ajouterLogementAction(ActionEvent event) {


        if (isInputValid()) {
            String nomL = nom.getText();
            String localisationL = localisation.getText();
            int numL = Integer.parseInt(num.getText());
            int noteL = Integer.parseInt(note.getText());
            int prixL = Integer.parseInt(prix.getText());


           String imageL = (selectedImageFile != null) ? selectedImageFile.getPath():"";

            Logement logement = new Logement();
            LogementCrud service = new LogementCrud();
            service.ajouter(logement);


            nom.clear();
            localisation.clear();
            num.clear();
            note.clear();
            prix.clear();

            imageView.setImage(null);
            selectedImageFile = null;


        }


    }

    private boolean isInputValid() {
        boolean isValid = true;

        // Validate and display error messages
        if (nom.getText().isEmpty() || !nom.getText().matches("^[a-zA-Z]+$")) {
            errorNom.setText("Nom is required and should not contain numbers");
            isValid = false;
        } else {
            errorNom.setText("");
        }

        if (localisation.getText().isEmpty() || !localisation.getText().matches("^[a-zA-Z]+$")) {
            errorLocalisation.setText("Adresse is required and should not contain numbers ");
            isValid = false;
        } else {
            errorLocalisation.setText("");
        }

        if (note.getText().isEmpty()) {
            errorNote.setText("Note is required");
            isValid = false;
        }  else {
            errorNote.setText("");
        }

        if (num.getText().isEmpty() || !num.getText().matches("^[0-9]+$")) {
            errorNum.setText("Phone is required and should be 8 digits long");
            isValid = false;
        } else {
            errorNum.setText("");
        }
        if (prix.getText().isEmpty() || !prix.getText().matches("^[0-9]+$")) {
            errorPrix.setText("Phone is required and should be 4 digits long");
            isValid = false;
        } else {
            errorPrix.setText("");
        }

        return isValid;
    }


    @FXML
    void selectImageAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter( ".png", ".jpg")
        );
        selectedImageFile = fileChooser.showOpenDialog(null);

        if (selectedImageFile != null) {
            // Display the selected image in the ImageView
            Image image = new Image(selectedImageFile.toURI().toString());
            imageView.setImage(image);
        }
    }

    @FXML
    void initialize()  {

        assert localisation != null : "fx:id=\"localisation\" was not injected: check your FXML file 'ajouteragence.fxml'.";
        assert prix != null : "fx:id=\"prix\" was not injected: check your FXML file 'ajouteragence.fxml'.";
        assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'ajouteragence.fxml'.";
        assert num != null : "fx:id=\"phone\" was not injected: check your FXML file 'ajouteragence.fxml'.";
        assert ButtonAjouterLog != null : "fx:id=\"save\" was not injected: check your FXML file 'ajouteragence.fxml'.";

    }




}
