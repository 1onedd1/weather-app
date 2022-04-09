package com.weatherapplication.demo.view;

import javafx.scene.control.TextField;

public class TextFieldExtend extends TextField {
    public TextFieldExtend(String id) {
        this.setFocusTraversable(false);
        this.setEditable(false);
        this.setMaxWidth(100);
        this.setId(id);
        this.setText(String.valueOf(0));
    }
}
