package com.weatherapplication.demo;

import com.google.gson.Gson;
import com.weatherapplication.demo.model.WeatherModel;
import com.weatherapplication.demo.mqtt.Publisher;
import com.weatherapplication.demo.utils.HttpURLConnectionBuilder;
import com.weatherapplication.demo.view.Viewer;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class WeatherApplication {
    private static String broker = "tcp://broker.mqtt-dashboard.com:1883";
    private static String clientId = "JavaSample";
    private static String filePropertyPath = "src/main/resources/com/weatherapplication/demo/config.properties";

    public static void main(String[] args) {
        String key = "";

        try (FileInputStream fileInputStream = new FileInputStream(filePropertyPath)) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            System.out.println(fileInputStream);
            key = properties.getProperty("key");

            System.out.println(key);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String city = "kiev";
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + key;

        System.out.println(key);
        HttpURLConnection connection = HttpURLConnectionBuilder.builder()
                .setURL(url)
                .setMethod("GET")
                .setRequestProperty("Accept", "application/json").build();

        String json = getJsonResponse(connection);
        WeatherModel weatherModel = new Gson().fromJson(json, WeatherModel.class);

        String topic = "temp";
        String content = "very good news";
        int qos = 2;

        try {
            Publisher publisher = new Publisher(broker, clientId);
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setCleanSession(true);
            publisher.connect(connectOptions);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            publisher.publish(topic, message);
            publisher.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }

        new Timer().schedule(new TimerTask() {
            public void run() {
                try {
                    Scene scene = Window.getWindows().get(0).getScene();
                    TextField temperature = (TextField) scene.lookup("#" + Viewer.ElementsId.FIELD_TEMPERATURE);
                    TextField pressure = (TextField) scene.lookup("#" + Viewer.ElementsId.FIELD_PRESSURE);
                    TextField speedWind = (TextField) scene.lookup("#" + Viewer.ElementsId.FIELD_SPEED_WIND);

                    temperature.setText(String.valueOf(weatherModel.getMain().getTemp()));
                    pressure.setText(String.valueOf(weatherModel.getMain().getPressure()));
                    speedWind.setText(String.valueOf(weatherModel.getWind().getSpeed()));
                } catch (NullPointerException | IndexOutOfBoundsException e) {
                }
            }
        }, 1000, 10000);

        Viewer.launch(Viewer.class, args);
    }

    public static String getJsonResponse(HttpURLConnection connection) {
        String output = "";

        try {
            int code = connection.getResponseCode();

            if (code != 200) throw new ConnectException("code: " + code);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            while ((output = bufferedReader.readLine()) != null) {
                stringBuilder.append(output);
            }

            output = stringBuilder.toString();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }
}