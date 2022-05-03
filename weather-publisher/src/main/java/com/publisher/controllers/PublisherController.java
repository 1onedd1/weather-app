package com.publisher;

import com.publisher.model.Message;
import com.publisher.view.PropertyView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;

public class PublisherController {
    @FXML
    private Label temperature;

    @FXML
    private Label pressure;

    @FXML
    private TextField fPressure;

    @FXML
    private Label speedWild;

    @FXML
    private Label lamp;

    private boolean activeLamp;

    @FXML
    public void onClick(ActionEvent event) {
        try {
            Publisher publisher = new Publisher("tcp://broker.mqtt-dashboard.com:1883", "client");
            activeLamp = !activeLamp;
            Message message = new Message("/weather/lamp/", activeLamp ? "1" : "0", 2);
            lamp.setText(Boolean.toString(activeLamp));
            publisher.publish(message.getTopic(), message);
            publisher.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onClickProperty(ActionEvent event) {
        PropertyView view = new PropertyView();

        try {
            view.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}