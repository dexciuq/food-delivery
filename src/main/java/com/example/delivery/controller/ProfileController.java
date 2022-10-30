package com.example.delivery.controller;

import com.example.delivery.db.DatabaseAdapter;
import com.example.delivery.model.*;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class ProfileController {
    private List<Order> orders;
    private final DatabaseAdapter db =  DatabaseAdapter.getInstance();
    private final Window window = new Window();
    private final User user;

    public ProfileController(User user) {
        this.user = user;
    }

    public Parent createContent() {
        Label greeting = new Label("Hi, " + user.getName());
        Label heading = new Label("ORDER LIST:");

        StringBuilder stringBuilder = new StringBuilder();
        orders = db.selectAllOrdersById(user.getId());
        for (Order order: orders) {
            stringBuilder.append(order.toString()).append("\n");
        }

        // BUTTONS
        Button menuBtn = new Button("Menu");
        menuBtn.getStyleClass().setAll("btn-sm", "btn-info");
        menuBtn.setOnAction(event -> {
            MenuController menuController = new MenuController();
            window.switchScene(event, menuController.createContent());
        });

        //VBOX
        VBox vBox = new VBox(10, greeting, heading, new Text(stringBuilder.toString()), menuBtn);
        vBox.getStyleClass().addAll("vbox", "vbox-padding");

        // GRID PANE
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.add(vBox, 0, 0);
        return gridPane;
    }
}
