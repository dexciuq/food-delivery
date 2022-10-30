package com.example.delivery.controller;

import com.example.delivery.db.DatabaseAdapter;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class PaymentController {
    private final DatabaseAdapter db =  DatabaseAdapter.getInstance();
    private final Window window = new Window();

    public Parent createContent() {
        GridPane gridPane = new GridPane();
        gridPane.add(new Button("flksd"), 0, 0);
        return gridPane;
    }
}
