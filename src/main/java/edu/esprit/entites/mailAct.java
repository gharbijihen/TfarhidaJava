package edu.esprit.entites;

import javax.mail.*;
import javax.mail.internet.*;
import javafx.scene.control.Alert;
import java.util.Properties;

public class mailAct {

    private String host = "smtp.gmail.com";
    private String from = "salhi.nour@esprit.tn";
    private String password = "211JFT7460";

    public void sendEmail(String email) {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Activité est posté avec succée!");
            message.setText("Bienvenue chez Tfarhida ! Votre activité a été accepté avec succès.");
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace(); // Affiche l'erreur dans la console

            // Afficher un message d'erreur à l'utilisateur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Échec de l'envoi de l'email");
            alert.setContentText("Une erreur s'est produite lors de l'envoi de l'email. Veuillez réessayer plus tard.");
            alert.showAndWait();
        }
    }
}
