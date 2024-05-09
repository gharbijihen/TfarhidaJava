package edu.esprit.entites;

import javax.mail.*;
import javax.mail.internet.*;
import javafx.scene.control.Alert;
import java.util.Properties;

public class mailAct {

    private String host = "smtp.gmail.com";
    private String from = "salhi.nour@esprit.tn";
    private String password = "211JFT7460";
    private Activite activite;

    public void sendEmail(String email, Activite activite) {
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
            message.setSubject("Activité est postée avec succès!");

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
                    + "<p>Votre activité <strong>" + activite.getNom() + "</strong> a été acceptée avec succès.</p>"
                    + "<p>Informations sur l'activité :</p>"
                    + "<p>Nom de l'activité: " + activite.getNom() + "</p>"
                    + "<p>Description: " + activite.getDescription_act() + "</p>"
                    + "<p>Prix: " + activite.getPrix() + " DT/Personne</p>"
                    + "<p>Localisation: " + activite.getLocalisation() + "</p>"
                    + "<p>Nombre de participants: " + activite.getNb_P() + "</p>"
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
    public void setActivite(Activite activite) {
        this.activite = activite;
    }

}
