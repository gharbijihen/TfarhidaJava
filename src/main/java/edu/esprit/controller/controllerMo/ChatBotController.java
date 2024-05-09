package edu.esprit.controller.controllerMo;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class ChatBotController {

    @FXML
    private TextField inputTextField;

    @FXML
    private TextArea outputLabel;

    @FXML
    private void processInput(ActionEvent event) {
        String input = inputTextField.getText();
        CompletableFuture.supplyAsync(() -> getOpenAIAPIAnswer(input))
                .thenAccept(answer -> Platform.runLater(() -> outputLabel.setText(answer)));
        inputTextField.clear();
    }



    public static void main(String[] args) {
        String question = "What is the meaning of life?";
        CompletableFuture.supplyAsync(() -> getOpenAIAPIAnswer(question))
                .thenAccept(System.out::println)
                .join();
    }


    private static final String apiKey = "sk-proj-ywjb34TJvgpNmHYop1cUT3BlbkFJvhmoTV8Lr56e8w1YGkDc";
    private static final String model = "gpt-3.5-turbo";  // Adjust the model name

    private static String getOpenAIAPIAnswer(String question) {
        try {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            String requestBody = "{\"model\":\"" + model + "\",\"messages\":[{\"role\":\"system\",\"content\":\"You are a helpful assistant.\"},{\"role\":\"user\",\"content\":\"" + question + "\"}],\"max_tokens\":150}";

            Request request = new Request.Builder()
                    .url("https://api.openai.com/v1/chat/completions")  // Use the correct endpoint for chat
                    .post(RequestBody.create(requestBody, mediaType))
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + apiKey)
                    .build();

            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();

            if (response.isSuccessful()) {
                // Parse JSON response and extract content
                JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
                JsonArray choicesArray = jsonResponse.getAsJsonArray("choices");
                if (choicesArray.size() > 0) {
                    JsonObject firstChoice = choicesArray.get(0).getAsJsonObject();
                    JsonObject message = firstChoice.getAsJsonObject("message");
                    String content = message.getAsJsonPrimitive("content").getAsString();
                    return content;
                } else {
                    return "No response content available.";
                }
            } else {
                // Log detailed error information
                System.err.println("Error Response Code: " + response.code());
                System.err.println("Error Response Body: " + responseBody);
                return "Oops! An error occurred while fetching the answer. Response code: " + response.code();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Oops! An error occurred while processing your request. Error: " + e.getMessage();
        }
    }

    @FXML
    public void RETOUR(ActionEvent event) {
        System.out.println("retourrrr");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}