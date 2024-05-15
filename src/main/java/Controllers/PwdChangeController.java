package Controllers;

import Entities.User;
import Service.ServiceUser;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

import static Controllers.GuiLoginController.user;

public class PwdChangeController {
    public TextField oldpwd;
    public TextField newpwd;

    public void returnTo(MouseEvent mouseEvent) {
        RouterController.navigate("/fxml/ProfileDetails.fxml");
    }
    public ServiceUser su=new ServiceUser();

    public void ChangePwd(ActionEvent actionEvent) throws SQLException {
        if(checkpwd(user.getPassword(),oldpwd.getText())) {
            String encryptedPassword = BCrypt.hashpw(newpwd.getText(), BCrypt.gensalt(13));
            user.setPassword(encryptedPassword);
            su.update(user);
            showSuccessMessage("Password Changed succesfully");
            RouterController.navigate("/fxml/ClientDashboard.fxml");
        }
        else
            showAlert("Verifier le mot de passe ancien");
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
    private boolean checkpwd(String oldpwd, String oldtypedpassword) {
        return su.checkpassword(user,oldtypedpassword);
    }
}
