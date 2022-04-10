package com.subscriber;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.net.URL;
import java.util.ResourceBundle;

public class SubscriberController {
    @FXML private TextField fTemperature;
    @FXML private TextField fPressure;
    @FXML private TextField fSpeedWild;
    @FXML private TextField fHumidity;

    public TextField getfTemperature() {
        return fTemperature;
    }

    public TextField getfPressure() {
        return fPressure;
    }

    public TextField getfSpeedWild() {
        return fSpeedWild;
    }

    public TextField getfHumidity() {
        return fHumidity;
    }
}