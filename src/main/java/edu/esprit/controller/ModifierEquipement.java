package edu.esprit.controller;

import edu.esprit.entites.Equipement;
import edu.esprit.entites.Logement;
import edu.esprit.servies.EquipementCrud;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class ModifierEquipement {

    @FXML
    private Button ButtonModifierEquipement;

    @FXML
    private TextField DescriptionEquipement;

    @FXML
    private CheckBox climatitation;

    @FXML
    private Text errorDescriptionEquipement;

    @FXML
    private Text errorNbrChambre;

    @FXML
    private Text errorTypeCHambre;

    @FXML
    private CheckBox internet;

    @FXML
    private TextField nbrChambre;

    @FXML
    private CheckBox parking;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField typeChambre;
    private Equipement equipement;


    public void initData(Equipement equipement) {
        this.equipement = equipement;
        typeChambre.setText(equipement.getTypes_de_chambre());
        DescriptionEquipement.setText(equipement.getDescription());
        nbrChambre.setText(String.valueOf(equipement.getNbr_chambre()));
        climatitation.setSelected(equipement.isClimatisation());
        internet.setSelected(equipement.isInternet());
        parking.setSelected(equipement.isParking());


    }

    @FXML
    void modifierEquipementAction(ActionEvent event) {

            int equipementId = equipement.getId();
            if (equipementId != 0) {
                String typeChambreE = typeChambre.getText();
                String description = DescriptionEquipement.getText();
                int nbrChambreE = Integer.parseInt(nbrChambre.getText());
                boolean parkingE = parking.isSelected();
                boolean climatitationE = climatitation.isSelected();
                boolean internetE = internet.isSelected();

                Equipement equipementModifiee = new Equipement();
                equipementModifiee.setId(equipementId);
                equipementModifiee.setTypes_de_chambre(typeChambreE);
                equipementModifiee.setDescription(description);
                equipementModifiee.setNbr_chambre(nbrChambreE);
                equipementModifiee.setParking(parkingE);
                equipementModifiee.setClimatisation(climatitationE);
                equipementModifiee.setInetrnet(internetE);

                EquipementCrud service = new EquipementCrud();
                service.modifier(equipementModifiee);
                System.out.println("Equipement mis à jour avec succès !");
                showAlert("Logement modifiee", "Votre logement a été modiffie avec succès.");

            } else {
                System.out.println("L'ID de l'equipement sélectionnée est invalide.");
            }
        naviguezVersEquipement(event);
    }

    // Méthode de validation des données saisies par l'utilisateur
    private boolean isInputValid() {
        boolean isValid = true;

        // Validate and display error messages
        if (DescriptionEquipement.getText().isEmpty() || !DescriptionEquipement.getText().matches("^[a-zA-Z]+$")) {
            errorDescriptionEquipement.setText("Description is required and should not contain numbers");
            isValid = false;
        } else {
            errorDescriptionEquipement.setText("");
        }

        if (typeChambre.getText().isEmpty() || !typeChambre.getText().matches("^[0-9]+$")) {
            errorTypeCHambre.setText("Type is required and should not contain numbers ");
            isValid = false;
        } else {
            errorTypeCHambre.setText("");
        }
        if (nbrChambre.getText().isEmpty() || !nbrChambre.getText().matches("^[0-9]+$")) {
            errorNbrChambre.setText("Type is required and should not contain numbers ");
            isValid = false;
        } else {
            errorTypeCHambre.setText("");
        }

        return isValid;
    }
    @FXML
    void CancelAction(ActionEvent event) {

    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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
    }



