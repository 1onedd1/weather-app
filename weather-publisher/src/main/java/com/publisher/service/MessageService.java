package com.publisher.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MessageService {
    private final Map<String, String> messageMap = new HashMap<>();

    public String add(String topic, String content) {
        return messageMap.put(topic, content);
    }

    public synchronized String remove(String topic) {
        return messageMap.remove(topic);
    }

    public synchronized void addAll(Map<String, String> collection) {
        messageMap.putAll(collection);
    }

    public synchronized void removeAll() {
        messageMap.clear();
    }

    public String getMessage(String topic) {
        return messageMap.get(topic);
    }

    public Collection<String> getValues() {
        return Map.copyOf(messageMap).values();
    }

    public Set<String> getKeys() {
        return Map.copyOf(messageMap).keySet();
    }
}
