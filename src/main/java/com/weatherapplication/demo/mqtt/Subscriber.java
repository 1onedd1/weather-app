package com.weatherapplication.demo.mqtt;

import org.eclipse.paho.client.mqttv3.*;

public class Subscriber implements MqttCallback {
    private MqttClient subscriber;

    public Subscriber(String broker, String clientId) throws MqttException {
        this.subscriber = new MqttClient(broker, clientId);
    }

    public MqttClient getSubscriber() {
        return subscriber;
    }

    public void subscribe(String[] topics) {
        try {
            subscriber.subscribe(topics);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void setCallback(MqttCallback mqttCallback) {
        subscriber.setCallback(mqttCallback);
    }

    public void connect(MqttConnectOptions mqttConnectOptions) {
        try {
            subscriber.connect(mqttConnectOptions);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            subscriber.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
