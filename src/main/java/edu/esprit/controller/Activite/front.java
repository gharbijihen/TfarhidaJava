package edu.esprit.controller.Activite;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class front {
    @FXML
    private Pane affiche;
    @FXML
    void goToActivite(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ActiviteFxml/activiteAfficher.fxml"));
            Pane contenu = loader.load();
            affiche.getChildren().setAll(contenu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

