package com.publisher.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigPropertyService {
    private final Properties properties;
    private final String filePath;

    public ConfigPropertyService(String filePath) {
        properties = new Properties();
        this.filePath = filePath;
    }

    public void load() throws IOException {
        properties.load(new FileInputStream(filePath));
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        String value = properties.getProperty(key);
        return (value == null) ? defaultValue : value;
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    public void save() throws IOException {
        properties.store(new FileOutputStream(filePath), null);
    }
}
