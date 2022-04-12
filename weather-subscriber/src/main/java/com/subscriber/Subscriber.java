package com.subscriber;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Subscriber extends Application {
    private static String filePropertyPath = "weather-subscriber/src/main/resources/com/subscriber/config.properties";

    @Override
    public void start(Stage stage) throws IOException, MqttException {
        FXMLLoader fxmlLoader = new FXMLLoader(Subscriber.class.getResource("FXMLView.fxml"));

        FileInputStream fileInputStream = new FileInputStream(filePropertyPath);
        Properties properties = new Properties();
        properties.load(fileInputStream);

        String broker = properties.getProperty("mqtt.broker");
        String clientId = properties.getProperty("mqtt.clientId");

        MqttClient subscriber = new MqttClient(broker, clientId);
        subscriber.connect();
        subscriber.setCallback(new SubscribeHandler());

        String[] topics = {
                "/weather/temperature/",
                "/weather/pressure/",
                "/weather/humidity/",
                "/weather/speedWild/"
        };

        subscriber.subscribe(topics);

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Weather Subscriber");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException, MqttException {
        launch();
    }
}