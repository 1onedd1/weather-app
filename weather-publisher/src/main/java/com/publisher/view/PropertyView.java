package com.publisher.view;

import com.publisher.WeatherPublisher;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PropertyView {
    public void show() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WeatherPublisher.class.getResource("ModalPropertyView.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Properties");
        stage.setScene(scene);
        stage.show();
    }
}
