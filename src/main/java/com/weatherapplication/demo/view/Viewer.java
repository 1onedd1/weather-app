package com.weatherapplication.demo.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Viewer extends Application {
    private final int WIDTH = 250;
    private final int HEIGHT = 400;
    private final String APP_NAME = "Weather Application";

    private Stage initiation(Stage stage) {
        stage.setHeight(HEIGHT);
        stage.setWidth(WIDTH);
        stage.setTitle(APP_NAME);

        Menu menu = new Menu("Options");
        MenuItem item1 = new MenuItem("Start");
        menu.getItems().add(item1);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);

        Label temperature = new LabelExtend("Temperature");
        TextField textFieldTemperature = new TextFieldExtend(ElementsId.FIELD_TEMPERATURE.name);

        Label pressure = new LabelExtend("Pressure");
        TextField textFieldPressure = new TextFieldExtend(ElementsId.FIELD_PRESSURE.name);

        Label speedWild = new LabelExtend("Speed wild");
        TextField textFieldSpeedWild = new TextFieldExtend(ElementsId.FIELD_SPEED_WIND.name);

        VBox main = new VBox();
        HBox hBoxTemperature = new HBox(temperature, textFieldTemperature);
        HBox hBoxPressure = new HBox(pressure, textFieldPressure);
        HBox hBoxSpeedWild = new HBox(speedWild, textFieldSpeedWild);

        main.getChildren().addAll(menuBar, hBoxTemperature, hBoxPressure, hBoxSpeedWild);

        Scene scene = new Scene(main);
        stage.setScene(scene);

        return stage;
    }

    @Override
    public void start(Stage stage) {
        stage = initiation(stage);
        stage.show();
    }

    public enum ElementsId {
        FIELD_TEMPERATURE("fieldTemperature"),
        FIELD_PRESSURE("fieldPressure"),
        FIELD_SPEED_WIND("fieldSpeedWind");

        private String name;

        ElementsId(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
