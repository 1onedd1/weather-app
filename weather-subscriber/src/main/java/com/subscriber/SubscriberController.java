package com.subscriber;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SubscriberController {
    @FXML private TextField fTemperature;
    @FXML private TextField fPressure;
    @FXML private TextField fSpeedWild;
    @FXML private TextField fHumidity;

    public TextField getfTemperature() {
        return fTemperature;
    }

    public TextField getfPressure() {
        return fPressure;
    }

    public TextField getfSpeedWild() {
        return fSpeedWild;
    }

    public TextField getfHumidity() {
        return fHumidity;
    }
}