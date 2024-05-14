package Controllers;


import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import Entities.User;
import Service.ServiceUser;
import edu.esprit.servies.ReclamationCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class StatistiqueContoller {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BarChart<String , Integer > activiteBarChart;
    @FXML
    private PieChart pieChart;

    @FXML

    private ImageView btnReturn;


    @FXML
    void initialize() {

        ServiceUser serviceMoyen = new ServiceUser();

        // Récupération des statistiques sur les espaces de coworking par adresse
        Map<Boolean, Integer> moyenbyEtat = serviceMoyen.getusersByEtat();

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

        List<User > userList = new ServiceUser().ReadAll();

        // Comptez le nombre d'occurrences de chaque niveau d'activité
        Map<String, Integer> stats = new HashMap<>();
        for (User  user : userList ) {
            String Roles = user.getRoles() ;
            stats.put(Roles, stats.getOrDefault(Roles, 0) + 1);
        }

        // Créez une série de données pour le graphique
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Utilisateur par Role");
        for (String Roles : stats.keySet()) {
            int count = stats.get(Roles);
            XYChart.Data<String, Integer> data = new XYChart.Data<>(Roles, count);
            String label = Roles + " (" + count + ")";
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


    public void returnTo(MouseEvent mouseEvent) {
        RouterController.navigate("/fxml/UsersCRUD.fxml");
    }
}
