package edu.esprit.controller;

import edu.esprit.entites.Moyen_transport;
import edu.esprit.entites.Trajet;
import edu.esprit.servies.Moyen_transportCrud;
import edu.esprit.servies.TrajetCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ModifierTrajet {

    @FXML
    private Button ButtonModfiertrajet;

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
    private Text errormoyen;

    @FXML
    private TextField heure;

    @FXML
    private TextField lieuarrivée;

    @FXML
    private TextField lieudepart;

    @FXML
    private ChoiceBox<Moyen_transport> moyt;

    private Trajet trajet;



    @FXML
    private final TrajetCrud ps = new TrajetCrud();

    private ObservableList<String> trajetsList;
    public void settrajetsList(ObservableList<Moyen_transport> moyensList) {
        this.trajetsList= trajetsList;
    }

    public void initData(Trajet trajet) {
        this.trajet = trajet; // Assigner l'activité reçue à la variable de classe
        // Utilisez les données de l'activité pour initialiser les champs de saisie
        lieudepart.setText(trajet.getLieu_depart());
        lieuarrivée.setText(trajet.getLieu_arrivee());
        date.setValue(trajet.getDate().toLocalDate());
        heure.setText(trajet.getHeure());
        Moyen_transportCrud moyenTransportCrud = new Moyen_transportCrud();
        moyt.setItems(FXCollections.observableArrayList(moyenTransportCrud.getAllMoyensTransport()));
        moyt.setValue(Trajet.getMoyen_transport());
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



    @FXML
    void modifierMoyenAction(ActionEvent event) {
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
            // Récupérer l'ID de l'activité sélectionnée
            int trajetId = trajet.getId();

            // Si l'ID de l'activité est valide
            if (trajetId != 0) {
                // Récupérez d'abord les nouvelles valeurs saisies par l'utilisateur dans les champs de texte
                try {

                    String lieuD = lieudepart.getText();
                    String lieuA = lieuarrivée.getText();
                    Date datee = Date.valueOf(date.getValue());
                    String heuree = heure.getText();

                    Moyen_transport moyen = moyt.getValue();
                    int id= moyen.getId();
                    trajet.setMoyen_transport_id(id);


                    // Créez un objet Activite avec les nouvelles valeurs
                    Trajet trajetModifiee = new Trajet();
                    trajetModifiee.setId(trajetId);
                    trajetModifiee.setLieu_depart(lieuD);
                    trajetModifiee.setLieu_arrivee(lieuA);
                    trajetModifiee.setDate(datee);
                    trajetModifiee.setHeure(heuree);
                    trajetModifiee.setMoyen_transport(moyen);
                    trajetModifiee.setMoyen_transport_id(id);



                    // Utilisez votre service ActiviteCrud pour mettre à jour l'activité dans la base de données
                    TrajetCrud service = new TrajetCrud();
                    service.modifier(trajetModifiee);
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
        if (lieudepart.getText().isEmpty() || !lieudepart.getText().matches("^[a-zA-Z]+$")) {
            errorlieud.setText("Nom is required and should not contain numbers");
            isValid = false;
        } else {
            errorlieud.setText("");
        }

        if (lieuarrivée.getText().isEmpty() || !lieuarrivée.getText().matches("^[a-zA-Z]+$")) {
            errorlieuA.setText("Adresse is required and should not contain numbers ");
            isValid = false;
        } else {
            errorlieuA.setText("");
        }

        if (heure.getText().isEmpty()) {
            errorheure.setText("Heure is required");
            isValid = false;
        }  else {
            errorheure.setText("");
        }

        return isValid;
    }}