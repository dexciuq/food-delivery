package com.example.delivery.controller;

import com.example.delivery.db.DatabaseAdapter;
import com.example.delivery.model.*;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.List;

public class ProfileController {
    private List<Order> orders;
    private final DatabaseAdapter db;
    private final Window window;
    private final User user;

    public ProfileController(User user) {
        db =  DatabaseAdapter.getInstance();
        window = new Window();
        this.user = user;
    }

    public Parent createContent() {
        Label greeting = new Label("Hi, " + user.getName());
        greeting.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        Label heading = new Label("ORDER LIST:");
        heading.setFont(Font.font("Arial", FontWeight.NORMAL, 13));


        HBox hBox = new HBox(20);
        StringBuilder stringBuilder;
        orders = db.selectAllOrdersById(user.getId());
        for (Order order: orders) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(order.toString()).append("\n");
            Text hBoxText = new Text(stringBuilder.toString());
            hBox.getChildren().add(hBoxText);
        }

        // BUTTONS
        Button menuBtn = new Button("Menu");
        menuBtn.getStyleClass().setAll("btn-sm", "btn-info");
        menuBtn.setOnAction(event -> {
            MenuController menuController = new MenuController();
            window.switchScene(event, menuController.createContent());
        });

        //VBOX
        VBox vBox = new VBox(10, greeting, heading, hBox, menuBtn);
        vBox.getStyleClass().addAll("vbox", "vbox-padding");

        // GRID PANE
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(vBox, 0, 0);
        return gridPane;
    }
}
