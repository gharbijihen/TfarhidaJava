package edu.esprit.controller;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Random;

public class ChatBot {

    @FXML
    private TextField userInputField;
    @FXML
    private TextArea chatArea;
    @FXML
    private Button sendButton;
    @FXML
    private ComboBox<String> questionComboBox;

    // Define predefined questions and responses
    private final String[] predefinedQuestions = {
            "Combien de temps dure le processus de verification de l'activité ?",
            "Y a-t-il des restrictions d'âge ou de condition physique ?",
            "Quels sont les services supplémentaires disponibles (restauration, logement, etc.) ?",
            "Quelles sont les activités les plus populaires ou les mieux notées par les utilisateurs ?",
            "Est ce que je peux faire la réservation de votre site ?"
    };

    private final String[] predefinedResponses = {
            "Le processus de vérification de l'activité peut varier en fonction de plusieurs facteurs, notamment la complexité de l'activité et le nombre de participants. En général, il peut prendre de quelques heures à quelques jours pour être complété.",
            "Oui, certaines activités peuvent avoir des restrictions d'âge ou de condition physique pour des raisons de sécurité. Il est recommandé de consulter les détails de chaque activité pour connaître les restrictions spécifiques.",
            "Les services supplémentaires disponibles sont les restaurants,les logements et les transports.",
            "Les activités les plus populaires ou les mieux notées par les utilisateurs peuvent varier en fonction des préférences individuelles . Certaines activités populaires peuvent inclures des activités de plein air telles que la randonnée ou le kayak, des expériences saheriennes, des campings c, etc. Il est recommandé de consulter les details de chaque activité pour pouvoir touvé la meilleur activité pour toi.",
            "Non,notre site fournit les bons plans seulement tu peux pas  faire la réservation."
    };

    private final Random random = new Random();

    @FXML
    void initialize() {
        // Display the introductory message from the chatbot
        chatArea.appendText("Bonjour! Je suis Chatbot. Comment puis-je vous aider aujourd'hui?\n");
    }

    @FXML
    void sendMessage() {
        // Get user input
        String userMessage = userInputField.getText().trim();
        // Display user input in the chat area
        chatArea.appendText("Vous: " + userMessage + "\n");

        // Check if the user's message matches any predefined question
        boolean matched = false;
        for (int i = 0; i < predefinedQuestions.length; i++) {
            if (userMessage.equalsIgnoreCase(predefinedQuestions[i])) {
                // Display predefined response
                chatArea.appendText("Chatbot: " + predefinedResponses[i] + "\n");
                matched = true;
                break;
            }
        }

        // If the user's message doesn't match any predefined question, check the selected question from ComboBox
        if (!matched) {
            String selectedQuestion = questionComboBox.getValue();
            if (selectedQuestion != null) {
                for (int i = 0; i < predefinedQuestions.length; i++) {
                    if (selectedQuestion.equalsIgnoreCase(predefinedQuestions[i])) {
                        // Display predefined response for the selected question
                        chatArea.appendText("Chatbot: " + predefinedResponses[i] + "\n");
                        matched = true;
                        break;
                    }
                }
            }
        }

        // If the user's message doesn't match any predefined question or selected question, respond randomly
        if (!matched) {
            int randomIndex = random.nextInt(predefinedQuestions.length);
            chatArea.appendText("Chatbot: " + predefinedResponses[randomIndex] + "\n");
        }

        // Clear user input field
        userInputField.clear();
    }

    @FXML
    void onClose() {
        // Create and show an alert message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Chatbot");
        alert.setHeaderText(null);
        alert.setContentText("Merci d'avoir utilisé Chatbot!");
        alert.showAndWait();
        // Close the popup window
        Stage stage = (Stage) sendButton.getScene().getWindow();
        stage.close();
    }
}






