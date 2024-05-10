package edu.esprit.controller;


import Entities.User;
import Service.ServiceUser;
import edu.esprit.entites.Reclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import edu.esprit.servies.ReclamationCrud;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class StatistiquesController implements Initializable {

    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart<String , Integer > activiteBarChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ReclamationCrud serviceMoyen = new ReclamationCrud();



        // Récupération des statistiques sur les espaces de coworking par adresse
        Map<Boolean, Integer> moyenbyEtat = serviceMoyen.getReclamationByEtat();

        // Création des données pour le graphique PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<Boolean, Integer> entry : moyenbyEtat.entrySet()) {
            String etat = entry.getKey() ? "Traitée" : "Non Traitée";
            PieChart.Data data = new PieChart.Data(etat, entry.getValue());
            // Custom label for each PieChart.Data showing the occurrences
            data.setName(etat + " : " + entry.getValue());
            pieChartData.add(data);
        }

        // Configuration du PieChart
        pieChart.setData(pieChartData);
        pieChart.setTitle("Statistiques des réclamations par état");
    }
    @FXML
    private void handleActiviteStatsButtonAction (ActionEvent event) throws SQLException {
        // Obtenez la liste des activités

        List<Reclamation> reclamationList = new ReclamationCrud().afficher();

        // Comptez le nombre d'occurrences de chaque niveau d'activité
        Map<String, Integer> stats = new HashMap<>();
        for (Reclamation  reclamation : reclamationList ) {
            String Types = reclamation.getType() ;
            stats.put(Types, stats.getOrDefault(Types, 0) + 1);
        }

        // Créez une série de données pour le graphique
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Reclamation par Type");
        for (String Types : stats.keySet()) {
            int count = stats.get(Types);
            XYChart.Data<String, Integer> data = new XYChart.Data<>(Types, count);
            String label = Types + " (" + count + ")";
            Tooltip tooltip = new Tooltip(label);
            Tooltip.install(data.getNode(), tooltip);
            series.getData().add(data);

        }

        // Afficher le graphique
        activiteBarChart.getData().clear();
        activiteBarChart.getData().add(series);
        activiteBarChart.getXAxis().setLabel("Role");
        activiteBarChart.getYAxis().setLabel("Nombre des utilisateurs ");
        activiteBarChart.setTitle("Statistiques des utilisateurs  par Role");
    }
    public void Retour(javafx.event.ActionEvent event) {
        System.out.println("retourrrr");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}

