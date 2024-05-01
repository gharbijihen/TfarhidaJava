package testconnection;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.control.Hyperlink;

public class LoginSuccessListener extends Application {

    @FXML
    private AnchorPane left;

    @FXML
    private Hyperlink logInLink;

    @FXML
    void toLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private String email = "Hello, world!"; // initial value
    MyScheduledService service;

    public void initialize() {

        service = new MyScheduledService();
        service.start();

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    private class MyScheduledService extends ScheduledService<Void> {

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        URL url = new URL("http://192.168.1.189:8080/:8080/auth/login/success"); // replace with your URL
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                        // set request method and headers
                        connection.setRequestMethod("GET");
                        connection.setRequestProperty("Accept", "application/json");

                        // read response
                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String line;
                        StringBuilder response = new StringBuilder();
                        while ((line = br.readLine()) != null) {
                            response.append(line);
                        }
                        br.close();
                        int responseCode = connection.getResponseCode();

                        // parse JSON response and update value
                        String newValue = response.toString();
                        System.out.println(newValue);
                        if (responseCode != 403) {
                            System.out.println("URL did not return 403");
                            cancel(); // Cancel the task
                            return null;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return null;
                }
            };
        }
    }
    public static void main(String[] args)
    {
        LoginSuccessListener lsl=new LoginSuccessListener();
        lsl.initialize();
        System.out.println("test");
       Application.launch(args);

    }
}