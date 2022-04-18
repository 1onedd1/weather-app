module com.example.weatherpublisher {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.eclipse.paho.client.mqttv3;

    exports com.publisher;
    exports com.publisher.view;

    opens com.publisher.model to com.google.gson;
    opens com.publisher to javafx.fxml;
}