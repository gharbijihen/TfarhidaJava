package edu.esprit.controller;

import com.itextpdf.text.DocumentException;
import edu.esprit.entites.Trajet;
import edu.esprit.servies.Moyen_transportCrud;
import edu.esprit.servies.generatepdf;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import javafx.scene.control.Button;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;

import edu.esprit.entites.Moyen_transport;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;



public class AffichermoyenB {
    @FXML
    private HBox contentHBox;

    private Button MoyenButton;
    public TextField STextField;



    @FXML
    private TableView<Moyen_transport> tableView;

    @FXML
    private TableColumn<Moyen_transport, String> colType;

    @FXML
    private TableColumn<Moyen_transport, Integer> colCapacite;

    @FXML
    private TableColumn<Moyen_transport, String> colLieu;

    @FXML
    private TableColumn<Moyen_transport, Boolean> colEtat;

    @FXML
    private TableColumn<Moyen_transport, Boolean> colValide;


    @FXML
    private final Moyen_transportCrud ps = new Moyen_transportCrud();
    @FXML
    private ScrollPane activityScrollPane; // Ajout du ScrollPane
    @FXML
    private ScrollPane logementScrollPane; // Ajout du ScrollPane

    @FXML
    private Button activiteButton; // Référence au bouton "Activité"
    private File selectedImageFile;
    @FXML
    private Button accepterButton;

    @FXML
    private Button refreshButton;
    @FXML
    private Button refuserButton;
    @FXML
    private TableColumn<Moyen_transport, Void> colAction;
    @FXML
    private FilteredList<Moyen_transport> filteredMoyensList;

    @FXML
    private Button exportPDFButton;






    /*
        public void initialize() throws SQLException {

           List<Activite> act = ps.afficher();
            ObservableList<Activite> observableList = FXCollections.observableList(act);
            tableView.setItems(observableList);
            // Configure les colonnes pour correspondre aux attributs de l'activité
            colNomm.setCellValueFactory(new PropertyValueFactory<>("nom"));
            colCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie_id"));
            colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
            colLocalisation.setCellValueFactory(new PropertyValueFactory<>("localisation"));
            colNbPersonnes.setCellValueFactory(new PropertyValueFactory<>("nb_P"));
            colEtat.setCellValueFactory(new PropertyValueFactory<>("etat"));
            colDescription.setCellValueFactory(new PropertyValueFactory<>("description_act"));
            tableView.setVisible(false);
        }
    */
    public void initialize()  {
        List<Moyen_transport> moy = ps.afficher();
        ObservableList<Moyen_transport> observableList = FXCollections.observableList(moy);
        tableView.setItems(observableList);
        filteredMoyensList = new FilteredList<>(observableList);
        tableView.setItems(filteredMoyensList);
        // Configure les colonnes pour correspondre aux attributs de l'activité
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colCapacite.setCellValueFactory(new PropertyValueFactory<>("Capacite"));
        colLieu.setCellValueFactory(new PropertyValueFactory<>("Lieu"));
        colEtat.setCellValueFactory(new PropertyValueFactory<>("Etat"));
        colValide.setCellValueFactory(new PropertyValueFactory<>("Valide"));
        tableView.setStyle("-fx-background-color: #f2f2f2;");
        colAction.setCellFactory(cell -> new ActionCell());


        // Set styles for each TableColumn
        colType.setStyle("-fx-alignment: CENTER;");
        colCapacite.setStyle("-fx-alignment: CENTER;");
        colLieu.setStyle("-fx-alignment: CENTER;");
        colEtat.setStyle("-fx-alignment: CENTER;");
        colValide.setStyle("-fx-alignment: CENTER;");
        colAction.setStyle("-fx-alignment: CENTER;");

        // Set preferred widths for the columns
        colType.setPrefWidth(170);
        colCapacite.setPrefWidth(170);
        colLieu.setPrefWidth(170);
        colEtat.setPrefWidth(170);
        colValide.setPrefWidth(170);
        colAction.setPrefWidth(190);


        tableView.setVisible(true); // Rend la table visible par défaut

    }


    @FXML
    private void affichermoyen() {
        tableView.setVisible(true);


    }

    public void refreshData() {
        try {
           initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void modifierMoyenAction(ActionEvent event) {
        // Récupérer l'activité sélectionnée dans le TableView
        Moyen_transport moyenSelectionnee = tableView.getSelectionModel().getSelectedItem();

        // Vérifier si une activité est sélectionnée
        if (moyenSelectionnee != null) {
            // Ouvrir la page de modification avec les données de l'activité sélectionnée
            openModifierMoyenPage(moyenSelectionnee);
        } else {
            // Afficher un message d'erreur ou une boîte de dialogue indiquant à l'utilisateur de sélectionner une activité
        }

            affichermoyen();

    }


    @FXML
    private void openModifierMoyenPage(Moyen_transport moyen) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/moyenmodifie.fxml"));
            Parent root = loader.load();
            ModifierMoyen modifierController = loader.getController();
            modifierController.initData(moyen, selectedImageFile); // Transmettre également le fichier d'image sélectionné
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnHidden(e->refreshData());
            stage.show();

        } catch (IOException e) {
            // Handle IO exceptions
            e.printStackTrace();
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
        }
    }


    @FXML
    void deleteAction(ActionEvent event) {
        // Récupérer l'élément sélectionné dans le TableView
        Moyen_transport moyenSelectionnee = tableView.getSelectionModel().getSelectedItem();

        // Vérifier si un élément est sélectionné
        if (moyenSelectionnee != null) {
            // Afficher une boîte de dialogue de confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Supprimer l'activité sélectionnée");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer cette activité ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Supprimer l'élément de la liste ou de la base de données
                Moyen_transportCrud service = new Moyen_transportCrud();
                try {
                    service.supprimer(moyenSelectionnee);
                    // Rafraîchir le TableView après la suppression
                    tableView.getItems().remove(moyenSelectionnee);
                    System.out.println("Activité supprimée avec succès !");
                } catch (SQLException e) {
                    System.out.println("Erreur lors de la suppression de l'activité : " + e.getMessage());
                }
            }
        } else {
            // Afficher un message d'erreur ou une boîte de dialogue indiquant à l'utilisateur de sélectionner une activité à supprimer
        }
    }
    @FXML
    void handleAjouter(ActionEvent event) throws SQLException {
        try {
            // Charger la vue ou le formulaire d'ajout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterCrudF.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle fenêtre pour afficher le formulaire d'ajout
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnHidden(e->refreshData());
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void goToafficherNavBar(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminDashbord.fxml"));
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
    private void goToafficherLogement() {
        try {
            // Charger le contenu de afficherActivite.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/affichermoyenB.fxml"));
            Node afficherMoyenContent = loader.load();

            // Ajouter le contenu au contentHBox
            contentHBox.getChildren().clear(); // Efface tout contenu précédent
            contentHBox.getChildren().add(afficherMoyenContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    public void goToClient(javafx.scene.input.MouseEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherMoyenF.fxml"));
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

    public void searchAction(KeyEvent keyEvent) {
        String query = STextField.getText().trim().toLowerCase();

        filteredMoyensList.setPredicate(moyen -> {
            if (query.isEmpty()) {
                return true; // Show all items if the query is empty
            } else {
                String capaciteString = String.valueOf(moyen.getCapacite());
                String etatString = moyen.isEtat() ? "true" : "false";
                String valideString = moyen.isValide() ? "true" : "false";

                // Filter the moyens based on the search query
                return moyen.getType().toLowerCase().contains(query) ||
                        capaciteString.contains(query) ||
                        moyen.getLieu().toLowerCase().contains(query) ||
                        etatString.toLowerCase().contains(query) ||
                        valideString.toLowerCase().contains(query);
            }
        });

    }
    @FXML
    void GeneratePdf(ActionEvent event) throws DocumentException, SQLException {
        try {
            ArrayList<Moyen_transport> moyens= (ArrayList<Moyen_transport>) new Moyen_transportCrud().afficher();
            generatepdf.generatePDF(moyens, new FileOutputStream("C:\\Users\\RT0\\Desktop\\pijava\\TfarhidaJava\\Activite.pdf"), "C:\\Users\\RT0\\Desktop\\pijava\\TfarhidaJava\\src\\main\\resources\\images\\logo.png");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PDF Généré");
            alert.setHeaderText(null);
            alert.setContentText("Le PDF des moyens a été généré avec succès!");
            alert.showAndWait();
        } catch (FileNotFoundException | DocumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de la génération du PDF des moyens: " + e.getMessage());
            alert.showAndWait();
        }
    }

}