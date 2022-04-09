package com.weatherapplication.demo.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Publisher {
    private final MqttClient publisher;

    public Publisher(String broker, String clientId) throws MqttException {
        this.publisher = new MqttClient(broker, clientId, new MemoryPersistence());
    }

    public MqttClient getPublisher() {
        return publisher;
    }

    public void publish(String topic, MqttMessage message) {
        try {
            publisher.publish(topic, message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            publisher.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void connect(MqttConnectOptions mqttConnectOptions) {
        try {
            publisher.connect(mqttConnectOptions);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
