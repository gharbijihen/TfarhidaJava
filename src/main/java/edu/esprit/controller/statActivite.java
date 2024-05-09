package edu.esprit.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import edu.esprit.servies.ActiviteCrud;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class statActivite implements Initializable {

        @FXML
        private PieChart pieChart;

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            ActiviteCrud serviceActivite = new ActiviteCrud();

            // Récupération des statistiques sur les espaces de coworking par adresse
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
    }

