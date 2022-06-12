module com.example.weatherpublisher {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.eclipse.paho.client.mqttv3;

    exports com.publisher;
    exports com.publisher.view;
    exports com.publisher.model;

    opens com.publisher.model to com.google.gson;
    opens com.publisher to javafx.fxml;
    exports com.publisher.controllers;
    opens com.publisher.controllers to javafx.fxml;
    exports com.publisher.service;
    opens com.publisher.service to javafx.fxml;


}