package edu.esprit.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import edu.esprit.servies.ReclamationCrud;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class StatistiquesController implements Initializable {

    @FXML
    private PieChart pieChart;

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
    public void Retour(javafx.event.ActionEvent event) {
        System.out.println("retourrrr");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}

