package com.publisher;

import com.google.gson.Gson;
import com.publisher.model.Message;
import com.publisher.model.WeatherModel;
import com.publisher.utils.HttpURLConnectionBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class WeatherPublisher extends Application {
    private static String filePropertyPath = "weather-publisher/src/main/resources/com/publisher/config.properties";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WeatherPublisher.class.getResource("FXMLView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Weather Publisher");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePropertyPath);
        Properties properties = new Properties();
        properties.load(fileInputStream);

        String broker = properties.getProperty("mqtt.broker");
        String clientId = properties.getProperty("mqtt.clientId");
        String key = properties.getProperty("url.key");
        String city = properties.getProperty("url.city");
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + key;

        new Timer().schedule(new TimerTask() {
            public void run() {
                String response = HttpURLConnectionBuilder.builder()
                        .setURL(url)
                        .setMethod("GET")
                        .setRequestProperty("Accept", "application/json")
                        .ifConnectionSuccessfulGetResponse();

                WeatherModel weatherModel = new Gson().fromJson(response, WeatherModel.class);

                List<Message> messageList = List.of(
                        new Message("/weather/temperature/", String.valueOf(weatherModel.getMain().getTemp())),
                        new Message("/weather/pressure/", String.valueOf(weatherModel.getMain().getPressure())),
                        new Message("/weather/humidity/", String.valueOf(weatherModel.getMain().getHumidity())),
                        new Message("/weather/speedWild/", String.valueOf(weatherModel.getWind().getSpeed()))
                );

                int qos = 2;
                messageList.forEach(message -> {
                    try (Publisher publisher = new Publisher(broker, clientId)){
                        publisher.publishMessage(message.getTopic(), message, qos);
                        publisher.disconnect();
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                });
            }
        }, 200, 5000);

        launch();
    }
}