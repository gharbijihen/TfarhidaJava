package Controllers;

import Entities.User;
import Service.ServiceUser;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

public class GoogleAuthController implements Initializable {
    public void toLogin(ActionEvent event) {
        RouterController.navigate("/fxml/login.fxml");
    }

    MyScheduledService service;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = new MyScheduledService();
        service.start();
    }

    private class MyScheduledService extends ScheduledService<Void> {

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        URL url = new URL("http://localhost:9090/auth/login/success");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                        // set request method and headers
                        connection.setRequestMethod("GET");
                        connection.setRequestProperty("Accept", "application/json");

                        int responseCode = connection.getResponseCode();
                        System.out.println(responseCode);
                        if (responseCode != 403 ) {
                            System.out.println("URL did not return 403");
                            System.out.println("Printing connection");
                            System.out.println(connection.getInputStream());
                            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            String line;
                            StringBuilder response = new StringBuilder();
                            while ((line = br.readLine()) != null) {
                                response.append(line);
                            }
                            br.close();

                            JSONObject jsonResponse = new JSONObject(response.toString());
                            System.out.println(jsonResponse);
                            JSONObject user = jsonResponse.getJSONObject("user");
                            JSONArray emails = user.getJSONArray("emails");
                            JSONObject emailObject = emails.getJSONObject(0);
                            String email = emailObject.getString("value");

                            System.out.println("Email: " + email);

                            cancel();
                            ServiceUser userService = new ServiceUser();
                            User signedUser = userService.getOneUser(email);
                            Platform.runLater(() -> {
                                if (signedUser.getId() == -999) {

                                    // TrayNotificationAlert.notif("Login",
                                    //       "email not associated with zeroWaste account",
                                    //     NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
                                    Platform.runLater(() -> service.cancel());

                                    Parent root;
                                    Platform.runLater(() -> service.cancel());
                                    RouterController.navigate("/fxml/login.fxml");
                                } else {
                                    Platform.runLater(() -> service.cancel());
                                    //   TrayNotificationAlert.notif("Login", "logged in successfully.",
                                    //         NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));
                                    //UserSession.getInstance().setEmail(user.getEmail());
                                    GuiLoginController.user = signedUser;
                                    System.out.println("to the DASHBOARD");

                                    if (signedUser.getRoles().equals("[\"ROLE_USER\"]")) {

                                        RouterController.navigate("/fxml/ClientDashboard.fxml");
                                    } else if (signedUser.getRoles().equals("[\"ROLE_ADMIN\"]")) {
                                        RouterController.navigate("/fxml/AdminDashboard.fxml");
                                    }

                                }

                            });
                        }


                        return null;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            };
        }
    }
}

