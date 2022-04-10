package com.publisher.model;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.StandardCharsets;

public class Message extends MqttMessage {
    private final String topic;

    public Message(String topic, String content) {
        super(content.getBytes(StandardCharsets.UTF_8));
        this.topic = topic;
    }

    public Message(String topic, String content, int qos) {
        super(content.getBytes(StandardCharsets.UTF_8));
        super.setQos(qos);
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}
