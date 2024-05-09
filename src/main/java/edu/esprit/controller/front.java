package edu.esprit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class front {
    @FXML
    private Pane affiche;
    @FXML
    void goToMoyens(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherMoyenF.fxml"));
            Pane contenu = loader.load();
            affiche.getChildren().setAll(contenu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}