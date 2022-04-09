module com.weatherapplication.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.eclipse.paho.client.mqttv3;

    exports com.weatherapplication.demo;
    exports com.weatherapplication.demo.mqtt;
    exports com.weatherapplication.demo.model;
    exports com.weatherapplication.demo.view;

    opens com.weatherapplication.demo.mqtt to javafx.fxml;
    opens com.weatherapplication.demo.model to com.google.gson;
}