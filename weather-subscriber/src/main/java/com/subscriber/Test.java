package com.subscriber;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Test implements MqttCallback {
    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) {
        System.out.println(s);

        if(Window.getWindows().size() == 0) return;

        Scene scene = Window.getWindows().get(0).getScene();

        switch (s) {
            case "/weather/temperature/" -> ((TextField) scene.lookup("#fTemperature")).setText(mqttMessage.toString());
            case "/weather/pressure/" -> ((TextField) scene.lookup("#fPressure")).setText(mqttMessage.toString());
            case "/weather/speedWild/" -> ((TextField) scene.lookup("#fSpeedWild")).setText(mqttMessage.toString());
            case "/weather/humidity/" -> ((TextField) scene.lookup("#fHumidity")).setText(mqttMessage.toString());

        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
