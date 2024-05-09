package edu.esprit.controllers;



import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import edu.esprit.entites.Restaurant;
import edu.esprit.services.RestaurantService;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class statcontroller {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private Button retour;

    private RestaurantService restaurantService;

    public statcontroller() {
        this.restaurantService = new RestaurantService();
    }

    @FXML
    public void initialize() {
        try {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            List<Restaurant> restaurants = restaurantService.getAll();

            // Compter le nombre de restaurants pour chaque nombre d'étoiles
            int[] starCount = new int[6]; // Un tableau de 6 éléments pour stocker le nombre de restaurants pour chaque nombre d'étoiles
            for (Restaurant restaurant : restaurants) {
                int stars = restaurant.getNmbetoiles();
                if (stars >= 0 && stars <= 5) {
                    starCount[stars]++;
                }
            }

            // Ajouter les données au graphique
            for (int i = 0; i <= 5; i++) {
                series.getData().add(new XYChart.Data<>(String.valueOf(i), starCount[i]));
            }

            barChart.getData().add(series);

            // Formatage des valeurs de l'axe des ordonnées en entiers sans virgule
            yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis) {
                @Override
                public String toString(Number object) {
                    return String.format("%d", object.intValue());
                }
            });

            // Définir la valeur de départ de l'axe des ordonnées à 0
            yAxis.setTickUnit(1);
            yAxis.setLowerBound(0);

            // Désactiver la génération automatique des étiquettes
            yAxis.setAutoRanging(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void retour(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/restaurantaffb.fxml"));
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
    @FXML
    private Button Excel;
    @FXML
    void Excel(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier Excel");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));
        File file = fileChooser.showSaveDialog(barChart.getScene().getWindow());
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("Etoiles,                   nbResto\n");
                for (XYChart.Data<String, Number> data : barChart.getData().get(0).getData()) {
                    writer.write(data.getXValue() + "                       ," + data.getYValue() + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
