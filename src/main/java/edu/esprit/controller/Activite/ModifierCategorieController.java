package edu.esprit.controller.Activite;

import edu.esprit.entites.Categorie;
import edu.esprit.servies.CategorieCrud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ModifierCategorieController {
    @FXML
    private TextField type;
    @FXML
    private TextField descriptionCatt;

    @FXML
    private Button save;

    @FXML
    private Text errorType;

    @FXML
    private Text errorDescC;

    private Categorie categorie;

    public void initData(Categorie categorie) {
        this.categorie = categorie; // Assigner la catégorie reçue à la variable de classe
        // Utilisez les données de la catégorie pour initialiser les champs de saisie
        type.setText(categorie.getType_categorie());
        descriptionCatt.setText(categorie.getDescription());
    }

    @FXML
    void modifierCategorieAction(ActionEvent event) throws SQLException {
        if (isInputValid()) {
            // Récupérer l'ID de la catégorie sélectionnée
            int categorieId = categorie.getId();

            // Si l'ID de la catégorie est valide
            if (categorieId != 0) {
                // Récupérez d'abord les nouvelles valeurs saisies par l'utilisateur dans les champs de texte
                String type_categorie = type.getText();
                String description = descriptionCatt.getText();

                // Créez un objet Categorie avec les nouvelles valeurs
                Categorie categorieModifiee = new Categorie();
                categorieModifiee.setId(categorieId); // Assurez-vous de définir l'ID de la catégorie
                categorieModifiee.setType_categorie(type_categorie);
                categorieModifiee.setDescription(description);
                CategorieCrud service = new CategorieCrud();
               // try {
                    service.modifier(categorieModifiee);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succée");
                    alert.setContentText("Catégorie modifié avec succée");
                    System.out.println("Catégorie mise à jour avec succès !");
                    //alert.showAndWait();
                    Node source = (Node) event.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
               // } catch (SQLException e) {
                   // System.out.println("Erreur lors de la mise à jour de la catégorie : " + e.getMessage());
               // }
            } else {
                System.out.println("L'ID de la catégorie sélectionnée est invalide.");
            }
        } else {
            System.out.println("Les données saisies ne sont pas valides. Veuillez vérifier les champs.");
        }
    }

    private boolean isInputValid() {
        boolean isValid = true;

        // Validate and display error messages
        if (descriptionCatt.getText().isEmpty() || !descriptionCatt.getText().matches("^[\\p{L} \\s]+$")) {
            errorDescC.setText("Description is required and should not contain numbers");
            isValid = false;
        } else {
            errorDescC.setText("");
        }

        if (type.getText().isEmpty() || !type.getText().matches("^[a-zA-Z]+$")) {
            errorType.setText("Type is required and should not contain numbers ");
            isValid = false;
        } else {
            errorType.setText("");
        }

        return isValid;
    }
}
