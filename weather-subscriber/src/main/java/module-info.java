module com.subscriber.weathersubscriber {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.eclipse.paho.client.mqttv3;

    exports com.subscriber;
    exports com.subscriber.view;

    opens com.subscriber to javafx.fxml;
}