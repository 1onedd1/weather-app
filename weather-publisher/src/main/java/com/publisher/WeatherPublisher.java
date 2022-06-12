package com.publisher;

import com.google.gson.Gson;
import com.publisher.model.WeatherModel;
import com.publisher.service.ConfigPropertyService;
import com.publisher.service.HttpURLConnectionService;
import com.publisher.service.MessageService;
import com.publisher.view.View;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class WeatherPublisher {
    private static final MessageService MESSAGE_SERVICE = new MessageService();
    private static final String PATH_FILE_PROPERTY = "weather-publisher/src/main/resources/com/publisher/config.properties";
    private static final ConfigPropertyService CONFIG_PROPERTY_SERVICE = new ConfigPropertyService(PATH_FILE_PROPERTY);

    public static void main(String[] args) throws IOException {
        new Timer().schedule(new TimerTask() {
            public void run() {
                String response = "";

                try {
                    CONFIG_PROPERTY_SERVICE.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                String broker = CONFIG_PROPERTY_SERVICE.getProperty("mqtt.broker");
                String clientId = CONFIG_PROPERTY_SERVICE.getProperty("mqtt.clientId");
                String key = CONFIG_PROPERTY_SERVICE.getProperty("url.key");
                String city = CONFIG_PROPERTY_SERVICE.getProperty("url.city");
                String units = CONFIG_PROPERTY_SERVICE.getProperty("url.units");
                String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + key+"&units=" + units;

                try {
                    URL u = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) u.openConnection();
                    HttpURLConnectionService connectionService = HttpURLConnectionService.getInstance();
                    response = connectionService.readResponse(connection);
                    connection.disconnect();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                WeatherModel weatherModel = new Gson().fromJson(response, WeatherModel.class);

                MESSAGE_SERVICE.addAll(Map.of(
                        "/weather/temperature/", String.valueOf(weatherModel.getMain().getTemp()),
                        "/weather/pressure/", String.valueOf(weatherModel.getMain().getPressure()),
                        "/weather/humidity/", String.valueOf(weatherModel.getMain().getHumidity()),
                        "/weather/speedWild/", String.valueOf(weatherModel.getWind().getSpeed())
                ));

                int qos = 2;
                try (Publisher publisher = new Publisher(broker, clientId)) {
                    publisher.connect();
                    for (String topic : MESSAGE_SERVICE.getKeys()) {
                        MqttMessage message = new MqttMessage(MESSAGE_SERVICE.getMessage(topic).getBytes());
                        publisher.publishMessage(topic, message, qos);
                    }
                    publisher.disconnect();
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        }, 200, 5000);

        View.launch(View.class, args);
    }

    public static ConfigPropertyService getConfigPropertyService() {
        return CONFIG_PROPERTY_SERVICE;
    }

    public static MessageService getMessageService() {
        return MESSAGE_SERVICE;
    }
}