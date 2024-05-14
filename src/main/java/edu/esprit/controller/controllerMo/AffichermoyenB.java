package edu.esprit.controller.controllerMo;

import Controllers.RouterController;
import com.itextpdf.text.DocumentException;
import edu.esprit.servies.Moyen_transportCrud;
import edu.esprit.servies.generatepdfMo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.scene.control.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

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
    @FXML
    private PieChart pieChart;

    @FXML
    private AnchorPane  parentPane;






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
    public void initialize() {
        List<Moyen_transport> moy = ps.afficher();
        ObservableList<Moyen_transport> observableList = FXCollections.observableList(moy);
        filteredMoyensList = new FilteredList<>(observableList);
        tableView.setItems(filteredMoyensList);

        // Configure the columns to match the attributes of the Moyen_transport
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colCapacite.setCellValueFactory(new PropertyValueFactory<>("capacite"));
        colLieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        colEtat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        colValide.setCellValueFactory(new PropertyValueFactory<>("valide"));

        // Set Comparators for specific sorting
        colType.setComparator(Comparator.comparing(String::toLowerCase));
        colCapacite.setComparator(Comparator.comparingInt(Integer::intValue));
        colLieu.setComparator(Comparator.comparing(String::toLowerCase));
        colEtat.setComparator(Comparator.comparing(Boolean::booleanValue));
        colValide.setComparator(Comparator.comparing(Boolean::booleanValue));

        // Custom cell factory for displaying custom text
        colEtat.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : (item ? "Disponible" : "Non-disponible"));
            }
        });

        colValide.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : (item ? "Validé" : "Non-Validé"));
            }
        });

        // Set styles and other properties
        tableView.setStyle("-fx-background-color: #f2f2f2;");
        colAction.setCellFactory(cell -> new ActionCell());

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

        tableView.setVisible(true); // Make the table visible by default

        // Programmatically initial sort (optional, remove if not needed)
        tableView.getSortOrder().add(colType); // You can add multiple columns here
        tableView.sort();
        tableView.refresh();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MoyenFxml/moyenmodifie.fxml"));
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
                    filteredMoyensList.getSource().remove(moyenSelectionnee);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MoyenFxml/ajouterCrudF.fxml"));
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
            RouterController router=new RouterController();
            router.navigate("/fxml/AdminDashboard.fxml");
             } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
  /*  @FXML
    private void goToafficherLogement() {
        try {
            // Charger le contenu de afficherActivite.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MoyenFxml/afficherMoyenB.fxml"));
            Node afficherMoyenContent = loader.load();

            // Ajouter le contenu au contentHBox
            contentHBox.getChildren().clear(); // Efface tout contenu précédent
            contentHBox.getChildren().add(afficherMoyenContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/







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
    void stat(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MoyenFxml/stat.fxml"));


        Parent root = loader.load();

        // Créer une nouvelle fenêtre pour afficher le formulaire d'ajout
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    void generatePdf(ActionEvent event) throws DocumentException, SQLException {
        try {
            // Initialize a file chooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF File");

            // Set extension filters if needed
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                    new FileChooser.ExtensionFilter("All Files", "*.*")
            );

            // Show the save file dialog
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);

            // If the user cancels the dialog, file will be null, so return
            if (file == null) {
                return;
            }

            // Proceed with generating PDF using the selected file
            ArrayList<Moyen_transport> moyens = (ArrayList<Moyen_transport>) new Moyen_transportCrud().afficher();
            generatepdfMo.generatePDF(moyens, new FileOutputStream(file), "C:\\Users\\MSI\\Downloads\\tfarhida2\\src\\main\\resources\\MoyenFxml\\images\\logo.png");
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
     public void goToClient(MouseEvent event)
     {
         
     }
    public void goToLogin(MouseEvent mouseEvent) {
        RouterController router=new RouterController();
        router.navigate("/fxml/login.fxml");
    }
    public void goToafficherLogement(ActionEvent actionEvent) {
 
    }

    public void goToNavigate(ActionEvent actionEvent) {
        RouterController router=new RouterController();
        router.navigate("/fxml/AdminDashboard.fxml");
    }
}