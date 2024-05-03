package edu.esprit.controller;

import edu.esprit.entites.Equipement;
import edu.esprit.entites.Logement;
import edu.esprit.servies.EquipementCrud;
import edu.esprit.servies.LogementCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AjouterEquipement implements Initializable {


    @FXML
    private Button ButtonAjouterEquipement;

    @FXML
    private TextField DescriptionEquipement;
    public static Logement logement;
    @FXML
    private CheckBox climatitation;

    @FXML
    private Text errorDescriptionEquipement;

    @FXML
    private Text errorNbrChambre;

    @FXML
    private Text errorNum11;

    @FXML
    private Text errorTypeCHambre;

    @FXML
    private CheckBox internet;

    @FXML
    private TextField nbrChambre;

    @FXML
    private CheckBox parking;

    @FXML
    private TextField typeChambre;

    @FXML
    void ajouterEquipementAction(ActionEvent event) throws SQLException {
        if (isInputValid()) {
            boolean climatisationE = climatitation.isSelected();
            boolean internetE = internet.isSelected();
            boolean parkingE = parking.isSelected();
            int nbrChambreE = Integer.parseInt(nbrChambre.getText());
            String descriptionE = DescriptionEquipement.getText();
            String types_de_chambre = typeChambre.getText();

            EquipementCrud service = new EquipementCrud();
            boolean ajoutReussi = service.ajouter(new Equipement(parkingE, internetE, climatisationE, nbrChambreE, types_de_chambre, descriptionE));

            if (ajoutReussi) {
                // Afficher un message dans le terminal
                System.out.println("Equipement ajouté");

                showAlert("Equipement ajoutée", "Votre equipement a été ajoutée avec succès.");

                // Réinitialiser les champs
                climatitation.setSelected(false);
                internet.setSelected(false);
                parking.setSelected(false);
                DescriptionEquipement.clear();
                typeChambre.clear();
                nbrChambre.clear();
                naviguezVersAffichage(event);
            } else {
                // Afficher un message d'erreur dans le terminal
                System.out.println("Échec de l'ajout de l'équipement");
            }
        }
    }
    void naviguezVersAffichage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/indexLogement.fxml"));
            DescriptionEquipement.getScene().setRoot(root);
            internet.getScene().setRoot(root);
            parking.getScene().setRoot(root);
            nbrChambre.getScene().setRoot(root);
            climatitation.getScene().setRoot(root);
            typeChambre.getScene().setRoot(root);

        } catch (IOException e) {
            System.err.println(e.getMessage());
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
    @FXML

    private boolean isInputValid() {
        boolean isValid = true;

        // Validate and display error messages
        if (DescriptionEquipement.getText().isEmpty() || !DescriptionEquipement.getText().matches("^[a-zA-Z]+$")) {
            errorDescriptionEquipement.setText("Description is required and should not contain numbers");
            isValid = false;
        } else {
            errorDescriptionEquipement.setText("");
        }

        if (nbrChambre.getText().isEmpty() || !nbrChambre.getText().matches("^[0-9]+$")) {
            errorNbrChambre.setText("nombre des chambres is required and should contain numbers ");
            isValid = false;
        } else {
            errorNbrChambre.setText("");
        }


        if (typeChambre.getText().isEmpty() || !typeChambre.getText().matches("^[a-zA-Z]+$")) {
            errorTypeCHambre.setText("le type de chambre is required ");
            isValid = false;
        } else {
            errorTypeCHambre.setText("");
        }

        return isValid;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(AjouterEquipement.logement);
    }
}


