package edu.esprit.controller.controllerMo;

import edu.esprit.entites.Trajet;
import edu.esprit.servies.TrajetCrud;
import edu.esprit.entites.Moyen_transport;
import edu.esprit.servies.Moyen_transportCrud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.File;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javafx.stage.Stage;
import javafx.util.StringConverter;


public class AjouterTrajet {

    @FXML
    private Button ButtonAjoutertrajet;

    @FXML
    private DatePicker date;

    @FXML
    private Text errordate;

    @FXML
    private Text errorheure;

    @FXML
    private Text errorlieuA;

    @FXML
    private Text errorlieud;

    @FXML
    private ChoiceBox<Moyen_transport> moyt; // Assuming this is the ChoiceBox for displaying moyen_transport

    @FXML
    private TextField heure;

    @FXML
    private TextField lieuarrivée;

    @FXML
    private TextField lieudepart;

    @FXML
    private Button selectImage;

    @FXML
    private File selectedImageFile;

    @FXML
    void ajoutertrajetAction(ActionEvent event) {
        if (lieudepart.getText().isEmpty()) {
            lieudepart.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
        } else {
            lieudepart.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }
        if (lieuarrivée.getText().isEmpty()) {
            lieuarrivée.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
        } else {
            lieuarrivée.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }
        if (date.getValue() == null) {
            date.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
        } else {
            date.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }
        if (heure.getText().isEmpty()) {
            heure.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
        } else {
            heure.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }
        if (moyt.getValue()==null) {
            moyt.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
        } else {
            moyt.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }

        if (isInputValid()) {
            String lieud= lieudepart.getText();
            String lieua = lieuarrivée.getText();
            LocalDate localDate = date.getValue();
            Date date1 = Date.valueOf(localDate);
            String heure1 = heure.getText();
            Moyen_transport moyenTransport = moyt.getValue(); // Get the selected moyen_transport
            int moyenTransportId = moyenTransport.getId(); // Assuming getId() returns the ID of moyen_transport

            Trajet trajet = new Trajet(lieud, lieua, heure1, date1, moyenTransportId);
            TrajetCrud service = new TrajetCrud();
            service.ajouter(trajet);
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();

           // lieudepart.clear();
            //lieuarrivée.clear();
//            date.setValue(null);
//            heure.clear();
//            moyt.setValue(null); // Clear the selected moyen_transport
        }
    }

    private boolean isInputValid() {
        boolean isValid = true;

        // Validate and display error messages
        if (lieudepart.getText().isEmpty() || !lieudepart.getText().matches("^[a-zA-Z]+$")) {
            errorlieud.setText("Address is required and should not contain numbers");
            isValid = false;
        } else {
            errorlieud.setText("");
        }

        if (lieuarrivée.getText().isEmpty() || !lieuarrivée.getText().matches("^[a-zA-Z]+$")) {
            errorlieuA.setText("Address is required and should not contain numbers ");
            isValid = false;
        } else {
            errorlieuA.setText("");
        }

        if (date.getValue() == null) {
            errordate.setText("Date is required");
            isValid = false;
        }  else {
            errordate.setText("");
        }

        if (heure.getText().isEmpty()){
            errorheure.setText("Hour is required ");
            isValid = false;
        } else {
            errorheure.setText("");
        }

        return isValid;
    }

    @FXML
    void initialize() {
        // Fetch existing moyen_transport from the database
        Moyen_transportCrud moyenTransportCrud = new Moyen_transportCrud();
        List<Moyen_transport> moyenTransportList = moyenTransportCrud.afficher();

        // Populate the ChoiceBox with the fetched moyen_transport
        moyt.getItems().addAll(moyenTransportList);

        // Define how to display moyen_transport in the ChoiceBox
        moyt.setConverter(new StringConverter<Moyen_transport>() {
            @Override
            public String toString(Moyen_transport moyen_transport) {
                if (moyen_transport != null) {
                    return moyen_transport.getType(); // Display the type of moyen_transport
                }
                return null;
            }

            @Override
            public Moyen_transport fromString(String string) {
                // Not needed for this example
                return null;
            }
        });
    }
}
