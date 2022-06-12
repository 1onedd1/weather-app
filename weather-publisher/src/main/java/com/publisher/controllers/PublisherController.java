package com.publisher.controllers;

import com.publisher.WeatherPublisher;
import com.publisher.view.PropertyView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

import java.io.IOException;

public class PublisherController {
    @FXML
    private ToggleButton switchOfLamp;

    @FXML
    private TextField owmConnected;

    @FXML
    public void onClick(ActionEvent event) {
        String strSelected = switchOfLamp.isSelected() ? "Lamp On" : "Lamp Off";
        switchOfLamp.setText(strSelected);
        WeatherPublisher.getMessageService().add("/lamp/", strSelected);
    }

    @FXML
    public void onClickProperty(ActionEvent event) throws IOException {
        PropertyView view = new PropertyView();
        view.show();
    }
}