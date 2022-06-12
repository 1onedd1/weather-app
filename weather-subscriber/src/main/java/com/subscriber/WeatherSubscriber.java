package com.subscriber;

import com.subscriber.view.View;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class WeatherSubscriber {
    private static String filePropertyPath = "weather-subscriber/src/main/resources/com/subscriber/config.properties";

    public static void main(String[] args) throws IOException, MqttException {
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
                "/weather/speedWild/",
                "/lamp/"
        };

        subscriber.subscribe(topics);

        View.launch(View.class, args);
    }
}