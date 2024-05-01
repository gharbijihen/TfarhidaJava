package Controllers;

import Entities.User;
import Service.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;

public class ForgotPassword2Controller {
    public Label erreur;
    @FXML
    private TextField codeField;

    @FXML
    private Button confirmBTN;

    @FXML
    private AnchorPane left;

    @FXML
    private Hyperlink logInLink;
    public static String email;

    @FXML
    void confirmBTNClicked(ActionEvent event) throws IOException {
        this.erreur.setVisible(false);

        ServiceUser userService = new ServiceUser();
        User user;
        try {
            user = userService.getOneUser(ForgotPassword2Controller.email);
            System.out.println(user.getVerificationCode());
            System.out.println(codeField.getText());
            System.out.println(codeField.getText().equals(String.valueOf(user.getVerificationCode())));


            if (codeField.getText().equals(String.valueOf(user.getVerificationCode()))) {
                ForgotPassword3Controller.user=user;
              RouterController.navigate("/fxml/ForgotPassword3.fxml");

            } else {
              this.erreur.setText("Le code de vérification n'est pas correcte");
              this.erreur.setVisible(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
