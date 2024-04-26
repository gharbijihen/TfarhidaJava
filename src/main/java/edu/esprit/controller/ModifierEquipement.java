package edu.esprit.controller;

import edu.esprit.entites.Equipement;
import edu.esprit.entites.Logement;
import edu.esprit.servies.EquipementCrud;
import edu.esprit.tools.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.File;
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
        this.equipement = equipement; // Assigner la catégorie reçue à la variable de classe
        // Utilisez les données de la catégorie pour initialiser les champs de saisie
        typeChambre.setText(equipement.getTypes_de_chambre());
        DescriptionEquipement.setText(equipement.getDescription());
        nbrChambre.setText(String.valueOf(equipement.getNbr_chambre()));
        climatitation.setSelected(equipement.isClimatisation());
        internet.setSelected(equipement.isInternet());
        parking.setSelected(equipement.isParking());


    }

    @FXML
    void modifierEquipementAction(ActionEvent event) {
        if (isInputValid()) {
            // Récupérer l'ID de la catégorie sélectionnée
            int equipementId = equipement.getId();

            // Si l'ID de la catégorie est valide
            if (equipementId != 0) {
                // Récupérez d'abord les nouvelles valeurs saisies par l'utilisateur dans les champs de texte
                String typeChambreE = typeChambre.getText();
                String description = DescriptionEquipement.getText();
                int nbrChambreE= Integer.parseInt(nbrChambre.getText());
                Boolean parkingE = Boolean.valueOf(parking.getText());
                Boolean climatitationE = Boolean.valueOf(climatitation.getText());
                Boolean internetE = Boolean.valueOf(internet.getText());

                // Créez un objet Categorie avec les nouvelles valeurs
                Equipement equipementModifiee = new Equipement();
                equipementModifiee.setId(equipementId); // Assurez-vous de définir l'ID de la catégorie
                equipementModifiee.setTypes_de_chambre(typeChambreE);
                equipementModifiee.setDescription(description);
                equipementModifiee.setNbr_chambre(nbrChambreE);
                equipementModifiee.setParking(parkingE);
                equipementModifiee.setInetrnet(internetE);
                equipementModifiee.setClimatisation(climatitationE);


                // Utilisez votre service CategorieCrud pour mettre à jour la catégorie dans la base de données
                EquipementCrud service = new EquipementCrud();
                // Appelez la méthode modifier de votre service pour mettre à jour la catégorie
                service.modifier(equipementModifiee);
                System.out.println("Equipement mise à jour avec succès !");
                // Vous pouvez également afficher une boîte de dialogue ou un message pour informer l'utilisateur
            } else {
                System.out.println("L'ID de l'equipement sélectionnée est invalide.");
                // Afficher un message d'erreur dans l'interface utilisateur
            }
        } else {
            // Les données saisies par l'utilisateur ne sont pas valides, affichez un message d'erreur ou effectuez une action appropriée
            System.out.println("Les données saisies ne sont pas valides. Veuillez vérifier les champs.");
            // Vous pouvez également afficher des messages d'erreur spécifiques à chaque champ si nécessaire
        }
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
    }



