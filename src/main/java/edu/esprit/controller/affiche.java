package edu.esprit.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import edu.esprit.entites.Activite;

import edu.esprit.servies.ActiviteCrud;

import java.sql.SQLException;
import java.util.List;
public class affiche {
    private final ActiviteCrud ps = new ActiviteCrud();
    @FXML
    private TableColumn<Activite, Integer> DescCol;
    @FXML
    private TableColumn<Activite, String> nomCol;
    @FXML
    private TableColumn<Activite, String> LocCol;
    @FXML
    private TableView<Activite> tableView1;

    @FXML
    void initializee() throws SQLException {
        List<Activite> act = ps.afficher();
        ObservableList<Activite> observableList = FXCollections.observableList(act);
        tableView1.setItems(observableList);

        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        DescCol.setCellValueFactory(new PropertyValueFactory<>("description_act"));
        LocCol.setCellValueFactory(new PropertyValueFactory<>("localisation"));
    }
}
