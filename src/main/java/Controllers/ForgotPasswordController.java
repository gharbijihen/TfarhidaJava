package Controllers;

import Entities.User;
import Service.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;
import Utils.sendMail;

import javax.mail.MessagingException;


public class ForgotPasswordController {
    public TextField emailInput;
    public ServiceUser serviceUser = new ServiceUser();
    public Label erreur;

    public void sendCode(ActionEvent event) {
        erreur.setVisible(false);

        User user;
        try {
            System.out.println("Getting user");
            user = serviceUser.getOneUser(emailInput.getText());
            System.out.println("Got user");

            if (!forgetPasswordValidator(emailInput.getText(), user)) {
                System.out.println("pwd non validated");

                return;
            }

            int code = new Random().nextInt(900000) + 100000;
            user.setVerificationCode(code);

            Map<String, String> data = new HashMap<>();
            data.put("emailSubject", "Reset password request");
            data.put("titlePlaceholder", "Reset password request");
            data.put("msgPlaceholder",
                    "It seems like you forgot your password. If this is true, here's the code to reset your password");

            sendMail.send(user, data);
            System.out.println("sent");

            serviceUser.update(user);

         /*   TrayNotificationAlert.notif("Forgot password",
                    "A verification code was sent to you, please check you email.",
                    NotificationType.WARNING, AnimationType.POPUP, Duration.millis(2500));
*/
         //   UserSession.getInstance().setEmail(user.getEmail());
            ForgotPassword2Controller.email=user.getEmail();
           RouterController.navigate("/fxml/ForgotPassword2.fxml");

        } catch (SQLException | MessagingException e) {
            e.printStackTrace();
        }
    }

    private boolean forgetPasswordValidator(String email, User user) {
        if (email.equals("")) {

                erreur.setText("Email ne pourra pas etre vide");
                erreur.setVisible(true);
                return false;
        }

            Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
            if (!emailPattern.matcher(email).matches()) {
                erreur.setText("L'email " + email + " est pas valide.");
                erreur.setVisible(true);
                return false;
            }

            if (user.getId() == -999) {
                erreur.setText("L'email " + email + " n'ést pas associé a un compte tfarhida");
                erreur.setVisible(true);

                return false;
            }

            return true;
        }
}
