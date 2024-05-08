package Controllers;

import Entities.User;
import Service.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ForgotPassword2Controller {

    @FXML
    private TextField codeField1;

    @FXML
    private TextField codeField2;

    @FXML
    private TextField codeField3;

    @FXML
    private TextField codeField4;

    @FXML
    private TextField codeField5;

    @FXML
    private TextField codeField6;

    @FXML
    private Label erreur;

    public static String email;

    @FXML
    void confirmBTNClicked(ActionEvent event) throws IOException {
        erreur.setVisible(false);

        // Vérifiez si tous les champs sont remplis
        if (codeField1.getText().isEmpty() || codeField2.getText().isEmpty() ||
                codeField3.getText().isEmpty() || codeField4.getText().isEmpty() ||
                codeField5.getText().isEmpty() || codeField6.getText().isEmpty()) {
            erreur.setText("Veuillez remplir tous les champs.");
            erreur.setVisible(true);
            return;
        }

        // Concaténez les valeurs des champs pour former le code
        String codeField = codeField1.getText() + codeField2.getText() +
                codeField3.getText() + codeField4.getText() +
                codeField5.getText() + codeField6.getText();

        ServiceUser userService = new ServiceUser();
        User user;

        try {
            user = userService.getOneUser(ForgotPassword2Controller.email);
            if (codeField.equals(String.valueOf(user.getVerificationCode()))) {
                ForgotPassword3Controller.user = user;
                RouterController.navigate("/fxml/ForgotPassword3.fxml");
            } else {
                erreur.setText("Le code de vérification n'est pas correct.");
                erreur.setVisible(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Ajoutez un écouteur d'événements pour chaque champ de texte OTP
    @FXML
    public void initialize() {
        addListener(codeField1, codeField2);
        addListener(codeField2, codeField3);
        addListener(codeField3, codeField4);
        addListener(codeField4, codeField5);
        addListener(codeField5, codeField6);
    }

    // Méthode pour ajouter un écouteur d'événements à un champ de texte
    private void addListener(TextField currentField, TextField nextField) {
        currentField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 1) {
                nextField.requestFocus(); // Déplacez le focus vers le champ de texte suivant
            }
        });
    }

    @FXML
    void toLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
