package com.weatherapplication.demo.view;

import javafx.scene.control.Label;

public class LabelExtend extends Label {
    public LabelExtend(String name) {
        super(name);
        this.setMinWidth(80);
    }
}
