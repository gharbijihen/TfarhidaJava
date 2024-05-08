package edu.esprit.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import edu.esprit.servies.LogementCrud;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
public class StatLogement implements Initializable {
    @FXML
    private PieChart pieChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LogementCrud serviceLogement = new LogementCrud();

        // Récupération des statistiques sur les espaces de coworking par adresse
        Map<String, Integer> logementByType = serviceLogement.getLogementByType();

        // Création des données pour le graphique PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : logementByType.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }


        // Configuration du PieChart
        pieChart.setData(pieChartData);
        pieChart.setTitle("Statistique des logement par type de logement disponible");
    }


        @FXML
        void Retour(ActionEvent event) {
            System.out.println("retourrrr");
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }

}

