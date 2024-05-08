package edu.esprit.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Weather {

    @FXML
    private TextField city;

    @FXML
    private Text weatherData;
    @FXML
    private Button closeButton;
    @FXML
    private void getWeatherData() {
            String cityName = city.getText();
            if (cityName.isEmpty()) {
                // Récupérer la localisation à partir de la carte en appelant directement la méthode statique de MapController
                cityName = Map.getLocation();
            }
        try {
            // Call a method to fetch weather data using OpenWeatherMap API
            String weatherInfo = fetchWeatherData(cityName);
            // Update the weatherData text field with the fetched weather information
            weatherData.setText(weatherInfo);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to fetch weather data", "An error occurred while fetching weather data. Please try again later.");
            // Handle error
        }
    }


    private static final String API_KEY = "71cb47b919634b05569d4f38f26c804a";
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather";


    private String fetchWeatherData(String cityName) throws IOException {
        String urlString = API_URL + "?q=" + cityName + "&appid=" + API_KEY;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            connection.disconnect();
            throw e;
        }

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code: " + responseCode);
        }

        JSONObject jsonResponse = new JSONObject(response.toString());
        return parseWeatherData(jsonResponse, cityName);
    }
    private String parseWeatherData(JSONObject jsonResponse, String cityName) {
        JSONObject main = jsonResponse.getJSONObject("main");
        double temperatureKelvin = main.getDouble("temp");
        double feelsLikeKelvin = main.getDouble("feels_like");
        int humidity = main.getInt("humidity");

        double temperatureCelsius = temperatureKelvin - 273.15;
        double feelsLikeCelsius = feelsLikeKelvin - 273.15;

        JSONObject weatherObject = jsonResponse.getJSONArray("weather").getJSONObject(0);
        String weatherDescription = weatherObject.getString("description");

        StringBuilder weatherInfo = new StringBuilder();
        weatherInfo.append("Weather in ").append(cityName).append(": ").append(weatherDescription).append("\n");
        weatherInfo.append("Temperature: ").append(temperatureCelsius).append("°C\n");
        weatherInfo.append("Feels Like: ").append(feelsLikeCelsius).append("°C\n");
        weatherInfo.append("Humidity: ").append(humidity).append("%");

        return weatherInfo.toString();
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    void onClose() {
        // Fermer la fenêtre
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}