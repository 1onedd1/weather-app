package com.publisher;

import com.google.gson.Gson;
import com.publisher.model.Message;
import com.publisher.model.WeatherModel;
import com.publisher.utils.HttpURLConnectionBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class Publisher extends Application {
    private static String filePropertyPath = "weather-publisher/src/main/resources/com/publisher/config.properties";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Publisher.class.getResource("FXMLView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Weather Publisher");
//        stage.setScene(scene);
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
                String json = "";

                HttpURLConnection connection = HttpURLConnectionBuilder.builder()
                        .setURL(url)
                        .setMethod("GET")
                        .setRequestProperty("Accept", "application/json").build();
                json = getJsonResponse(connection);

                WeatherModel weatherModel = new Gson().fromJson(json, WeatherModel.class);

                List<Message> messageList = List.of(
                        new Message("/weather/temperature/", String.valueOf(weatherModel.getMain().getTemp())),
                        new Message("/weather/pressure/", String.valueOf(weatherModel.getMain().getPressure())),
                        new Message("/weather/humidity/", String.valueOf(weatherModel.getMain().getHumidity())),
                        new Message("/weather/speedWild/", String.valueOf(weatherModel.getWind().getSpeed()))
                );

                int qos = 2;
                messageList.forEach(message -> {
                    try {
                        MqttClient publisher = new MqttClient(broker, clientId);
                        MqttConnectOptions connectOptions = new MqttConnectOptions();
                        connectOptions.setCleanSession(true);
                        publisher.connect(connectOptions);
                        message.setQos(qos);
                        publisher.publish(message.getTopic(), message);
                        publisher.disconnect();
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                });
            }
        }, 200, 5000);

        launch();
    }

    public static String getJsonResponse(HttpURLConnection connection) {
        String output = "";

        int code = 0;
        try {
            code = connection.getResponseCode();
            if (code != 200) throw new ConnectException("code: " + code);

            InputStream inputStream = connection.getInputStream();
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ((output = bufferedReader.readLine()) != null) {
                stringBuilder.append(output);
            }

            output = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        connection.disconnect();

        return output;
    }
}