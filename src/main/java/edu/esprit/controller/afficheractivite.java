package edu.esprit.controller;

import com.itextpdf.text.DocumentException;
import edu.esprit.servies.ActiviteCrud;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import edu.esprit.servies.generatepdf;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import edu.esprit.entites.Activite;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class afficheractivite{

    @FXML
    private TableView<Activite> tableView;

    @FXML
    private TableColumn<Activite, String> colNomm;

    @FXML
    private TableColumn<Activite, Integer> colCategorie;

    @FXML
    private TableColumn<Activite, Integer> colPrix;

    @FXML
    private TableColumn<Activite, String> colLocalisation;

    @FXML
    private TableColumn<Activite, Integer> colNbPersonnes;

    @FXML
    private TableColumn<Activite, String> colEtat;

    @FXML
    private TableColumn<Activite, String> colDescription;
    @FXML
    private final ActiviteCrud ps = new ActiviteCrud();
    @FXML
    private ScrollPane activityScrollPane; // Ajout du ScrollPane
    private ScrollPane logementScrollPane; // Ajout du ScrollPane
    @FXML
    private Pagination pagination;
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
    private TableColumn<Activite, Void> colAction;

    private IntegerProperty totalPages = new SimpleIntegerProperty(1);
    private IntegerProperty currentPage = new SimpleIntegerProperty(1);
    private ObservableList<Activite> observableList = FXCollections.observableArrayList();
    private List<Activite> activites;

    @FXML
    private TextField filtrefield;
    private FilteredList<Activite> filteredList;




    @FXML
    private RadioButton accepteeRadioButton;

    @FXML
    private RadioButton refuseeRadioButton;

    @FXML
    private RadioButton enCoursRadioButton;
    private ToggleGroup etatToggleGroup;
    private List<Activite> act;
    @FXML
    private PieChart pieChart;




    public void initialize() throws SQLException {
        act = ps.afficher();
        ObservableList<Activite> observableList = FXCollections.observableList(act);
        tableView.setItems(observableList);
        // Configure les colonnes pour correspondre aux attributs de l'activité
        colNomm.setCellValueFactory(new PropertyValueFactory<>("nom"));
       // colCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie_id"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colLocalisation.setCellValueFactory(new PropertyValueFactory<>("localisation"));
        colNbPersonnes.setCellValueFactory(new PropertyValueFactory<>("nb_P"));
        colEtat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description_act"));
        tableView.setStyle("-fx-background-color: #f2f2f2;");
        colAction.setCellFactory(cell -> new ActionCellAC());


        // Set styles for each TableColumn
        colNomm.setStyle("-fx-alignment: CENTER;");
        //colCategorie.setStyle("-fx-alignment: CENTER;");
        colPrix.setStyle("-fx-alignment: CENTER;");
        colLocalisation.setStyle("-fx-alignment: CENTER;");
        colNbPersonnes.setStyle("-fx-alignment: CENTER;");
        colEtat.setStyle("-fx-alignment: CENTER;");
        colDescription.setStyle("-fx-alignment: CENTER;");
        colAction.setStyle("-fx-alignment: CENTER;");

        // Set preferred widths for the columns
        colNomm.setPrefWidth(150);
        //colCategorie.setPrefWidth(100);
        colPrix.setPrefWidth(100);
        colLocalisation.setPrefWidth(150);
        colNbPersonnes.setPrefWidth(100);
        colEtat.setPrefWidth(100);
        colDescription.setPrefWidth(200);
        colAction.setPrefWidth(200);


        tableView.setVisible(true); // Rend la table visible par défaut
        // Wrap the ObservableList in a FilteredList (initially display all data).
        filteredList = new FilteredList<>(tableView.getItems(), b -> true);

        // Set the filter Predicate whenever the filter changes.
        filtrefield.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTableView();
        });
        ActiviteCrud serviceActivite = new ActiviteCrud();

        Map<String, Integer> activitebyEtat = serviceActivite.getActivitebyEtat();

        // Création des données pour le graphique PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : activitebyEtat.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }


        // Configuration du PieChart
        pieChart.setData(pieChartData);
        pieChart.setTitle("Statistique des activites par etat");
    }
//tri

    @FXML
    private void trierActivitesAcceptees(ActionEvent event) {
        trierActivitesParEtat("Acceptee",ps.afficher());
    }

    @FXML
    private void trierActivitesRefusees(ActionEvent event) {
        trierActivitesParEtat("Refuse",ps.afficher());
    }

    @FXML
    private void trierActivitesEnCours(ActionEvent event) {
        trierActivitesParEtat("en cours",ps.afficher());
    }
    private void trierActivitesParEtat(String etat, List<Activite> activites) {
        List<Activite> activitesTrie = new ArrayList<>();
        for (Activite activite : activites) {
            if (activite.getEtat().equalsIgnoreCase(etat)) {
                activitesTrie.add(activite);
            }
        }

        if (activitesTrie.isEmpty()) {
            tableView.getItems().setAll(activites); // Réinitialiser avec toutes les activités
        } else {
            tableView.getItems().setAll(activitesTrie); // Mettre à jour avec les activités triées
        }
    }
    //pagination
    private Node createPage(int pageIndex) {
        return tableView;
    }
    public void refreshList() {
        try {
            initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void GeneratePdf(ActionEvent event) throws DocumentException, SQLException {
        try {
            ArrayList<Activite> activites= (ArrayList<Activite>) new ActiviteCrud().afficher();
           generatepdf .generatePDF(activites, new FileOutputStream("C:\\Users\\ASUS\\IdeaProjects\\TfarhidaJava\\Activite.pdf"), "C:\\Users\\ASUS\\IdeaProjects\\TfarhidaJava\\src\\main\\resources\\images\\logo.png");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PDF Généré");
            alert.setHeaderText(null);
            alert.setContentText("Le PDF des activites a été généré avec succès!");
            alert.showAndWait();
        } catch (FileNotFoundException | DocumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de la génération du PDF des Activités: " + e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    private void filterTableView() {
        // Créer un prédicat pour filtrer les éléments de la TableView
        Predicate<Activite> filterPredicate = activite -> {
            // Vérifier si le texte de filtrage correspond à l'une des propriétés de l'offre
            return activite.getNom().toLowerCase().contains(filtrefield.getText().toLowerCase())
                    || activite.getDescription_act().toLowerCase().contains(filtrefield.getText().toLowerCase())
                    || String.valueOf(activite.getId()).contains(filtrefield.getText())
                    || String.valueOf(activite.getPrix()).contains(filtrefield.getText());
        };

        // Mettre à jour la liste filtrée
        filteredList.setPredicate(filterPredicate);

        // Lier la liste filtrée à la TableView
        SortedList<Activite> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }

    @FXML
    private void afficherActivite() {
            tableView.setVisible(true);

    }

    @FXML
    void modifierActiviteAction(ActionEvent event) {
        Activite activiteSelectionnee = tableView.getSelectionModel().getSelectedItem();

        if (activiteSelectionnee != null) {
            openModifierActivitePage(activiteSelectionnee);
        } else {
            // Afficher un message d'erreur ou une boîte de dialogue indiquant à l'utilisateur de sélectionner une activité
        }
    }

    @FXML
    private void openModifierActivitePage(Activite activite) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/activiteModife.fxml"));
            Parent root = loader.load();
            ModifierActiviteController modifierController = loader.getController();
            modifierController.initData(activite, selectedImageFile); // Transmettre également le fichier d'image sélectionné
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnHidden(e -> refreshList());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteAction(ActionEvent event) {
        Activite activiteSelectionnee = tableView.getSelectionModel().getSelectedItem();

        if (activiteSelectionnee != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Supprimer l'activité sélectionnée");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer cette activité ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Supprimer l'élément de la liste ou de la base de données
                ActiviteCrud service = new ActiviteCrud();
                try {
                    service.supprimer(activiteSelectionnee);
                    // Rafraîchir le TableView après la suppression
                    tableView.getItems().remove(activiteSelectionnee);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/activiteAjout.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle fenêtre pour afficher le formulaire d'ajout
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnHiding(e -> {
                refreshList();
            });

            stage.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        @FXML
        public void goToafficherNavBarAct(ActionEvent event) {
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
    public void goToCategorie(MouseEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CategorieAffB.fxml"));
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
    public void goBack(MouseEvent mouseEvent) {
        RouterController.navigate("/front.fxml");
    }


}









