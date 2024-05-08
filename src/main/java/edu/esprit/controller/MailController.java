package edu.esprit.controller;

import javax.mail.*;
import javax.mail.internet.*;

import edu.esprit.entites.Moyen_transport;
import javafx.scene.control.Alert;
import java.util.Properties;

public class MailController {

    private String host = "smtp.gmail.com";
    private String from = "salhi.nour@esprit.tn";
    private String password = "211JFT7460";
    private Moyen_transport moyen;

    public void sendEmail(String email, Moyen_transport moyen) {
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
            message.setSubject("Moyen est postée avec succès!");

            // Créer le contenu HTML avec du CSS intégré
            String htmlContent = "<html><head><style>"
                    + "body { font-family: Arial, sans-serif; }"
                    + ".container { max-width: 600px; margin: auto; padding: 20px; }"
                    + ".header { background-color: #007bff; color: #fff; padding: 10px; text-align: center; }"
                    + ".content { padding: 20px; background-color: #f7f7f7; }"
                    + "</style></head><body>"
                    + "<div class='container'>"
                    + "<div class='header'>Bienvenue chez Tfarhida !</div>"
                    + "<div class='content'>"
                    + "<p>Votre moyen <strong>" + moyen.getType() + "</strong> a été validé</p>"
                    + "<p>Informations sur le moyen :</p>"
                    + "<p>Nom de moyen: " + moyen.getType() + "</p>"
                    + "<p>Capacité: " + moyen.getCapacite() + "</p>"
                    + "<p>Lieu: " + moyen.getLieu()
                    + "</div>"
                    + "</div></body></html>";

            // Créer une partie pour le contenu HTML
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlContent, "text/html");

            // Créer un contenu multipart
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlPart);

            // Définir le contenu multipart comme contenu du message
            message.setContent(multipart);

            // Envoyer le message
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
    public void setMoyen(Moyen_transport moyen) {
        this.moyen = moyen;
    }

}