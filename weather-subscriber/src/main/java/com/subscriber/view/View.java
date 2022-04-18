package com.subscriber.view;

import com.subscriber.WeatherSubscriber;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class View extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WeatherSubscriber.class.getResource("FXMLView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Weather Subscriber");
        stage.setScene(scene);
        stage.show();
    }
}
