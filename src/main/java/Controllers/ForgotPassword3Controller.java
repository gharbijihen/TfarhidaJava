package Controllers;

import Entities.User;
import Service.ServiceUser;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

import static Controllers.GuiLoginController.user;

public class ForgotPassword3Controller {
    public static User user;
    public TextField newpwd;
    public TextField confirmpwd;

    public ServiceUser serviceUser=new ServiceUser();

    public void ChangePwd(ActionEvent actionEvent) throws SQLException {
        System.out.println("Checking for pwd");
        if(newpwd.getText().equals(confirmpwd.getText())) {
            System.out.println("PASSWORDS MATCH");
            String encryptedPassword = BCrypt.hashpw(newpwd.getText(), BCrypt.gensalt());
            user.setPassword(encryptedPassword);
            serviceUser.update(user);
            showSuccessMessage("Votre mot de passe à été changé avec succées success");
            GuiLoginController.user=user;
            RouterController.navigate("/fxml/login.fxml");
        }
        else {
            System.out.println("PASSWORDS NOT MATCH");

            showAlert("Verifiez votre confirmation du mot de passe");
        }
    }

    private void showAlert(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Message");
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Message");
        alert.setContentText(message);
        alert.showAndWait();
    }

}
