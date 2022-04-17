package com.publisher;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Publisher extends MqttClient {
    public Publisher(String serverURI, String clientId) throws MqttException {
        super(serverURI, clientId);
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setCleanSession(true);
        connect(mqttConnectOptions);
    }

    public void publishMessage(String topic, MqttMessage message, int qos) throws MqttException {
        message.setQos(qos);
        publish(topic, message);
    }

    public void disconnect() throws MqttException {
        super.disconnect();
    }
}
