package edu.esprit.controller;

import edu.esprit.entites.Equipement;
import edu.esprit.entites.Logement;
import edu.esprit.servies.EquipementCrud;
import edu.esprit.servies.LogementCrud;
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

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class AjouterEquipementB {

    @FXML
    private Button ButtonAjouterEquipement;

    @FXML
    private TextField DescriptionEquipement;
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
    private TextField nomLogementField;
    private Logement logement;
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


            Equipement equipement;
            boolean ajoutReussi = service.ajouter( equipement = new Equipement(parkingE, internetE, climatisationE, nbrChambreE, types_de_chambre, descriptionE));
           //logement.setEquipement_id(equipement.getId());
            if (ajoutReussi) {
                // Afficher un message dans le terminal
                System.out.println("Equipement ajouté");
                System.out.println("hedha eqq"+equipement);

                showAlert("Equipement ajoutée", "Votre equipement a été ajoutée avec succès.");

                // Réinitialiser les champs
                climatitation.setSelected(false);
                internet.setSelected(false);
                parking.setSelected(false);
                DescriptionEquipement.clear();
                typeChambre.clear();
                nbrChambre.clear();
                System.out.println(logement+"log equipe");
                // Associate equipment with lodging
                LogementCrud logementCrud = new LogementCrud();
                logementCrud.associerEquipementALogement(logement, equipement);

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogementAffB.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);

                    // Refresh the list by initializing the controller again
                    afficherLogementB controller = loader.getController();
                    //controller.initialize(); // You may need to create this method in your list controller

                    // Show the list scene
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Afficher un message d'erreur dans le terminal
                System.out.println("Échec de l'ajout de l'équipement");
            }
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

    public void initData(Logement logement) {
        this.logement = logement;

        // Initialiser les champs de la page avec les données du logement
        // Initialiser les autres champs avec les autres données du logement
    }
}
