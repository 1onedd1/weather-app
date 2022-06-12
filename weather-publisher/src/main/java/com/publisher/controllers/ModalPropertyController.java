package com.publisher.controllers;

import com.publisher.WeatherPublisher;
import com.publisher.service.ConfigPropertyService;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ModalPropertyController {
    @FXML
    TextField city;
    @FXML
    ChoiceBox<String> units;
    @FXML
    Button btnApply;
    
    @FXML
    public void onClickApply(ActionEvent event) {
        ConfigPropertyService service = WeatherPublisher.getConfigPropertyService();
        service.setProperty("url.city", city.getText().equals("") ? "Kiev" : city.getText());
        service.setProperty("url.units", units.getValue());
        try {
            service.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        btnApply.setDisable(true);
    }

    @FXML
    public void onCheckBoxChanged(ActionEvent event) {
        btnApply.setDisable(false);
    }

    @FXML
    public void onTextFieldChanged(Event event) {
        btnApply.setDisable(false);
    }
}
