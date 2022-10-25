package com.example.delivery.controller;

import com.example.delivery.model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import com.example.delivery.db.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class OrderController {
    private List<Product> products;
    private final DatabaseAdapter db =  DatabaseAdapter.getInstance();
    private final Window window = new Window();

    public void setProducts (List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Parent createContent() {
        Button receiptBtn = new Button("Receipt");
        Button confirmBtn = new Button("Confirm");
        Button menuBtn = new Button("Menu");
        receiptBtn.getStyleClass().setAll("btn-sm","btn-info");
        confirmBtn.getStyleClass().setAll("btn-sm","btn-success");
        menuBtn.getStyleClass().setAll("btn-sm", "btn-danger");

        Label heading = new Label("ORDER LIST:");
        String date = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.ENGLISH).format(Calendar.getInstance().getTime());
        Label info = new Label("Date of order: " + date);
        info.setFont(Font.font("Arial", 15));
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        StringBuilder text = new StringBuilder();
        StringBuilder price = new StringBuilder();
        int i = 0;
        double total = 0;
        for (Product product: products) {
            total += product.getProductPrice();
            text.append(++i).append(". ").append(product.getProductName()).append("\t").append(product.getProductPrice()).append("$\n");
        }
        total = ((double) Math.round(total * 100)) / 100;
        price.append("                                     Subtotal:  ")
                .append(total).append("$\n                                     Tax:         ")
                .append(15.0).append("$\n                                     Total:       ")
                .append(total + 15.0).append("$\n");
        text.append(price);
        Text table = new Text(text.toString());
        table.setFont(Font.font("Arial", FontWeight.LIGHT, 12));

        VBox vbox = new VBox(10);
        vbox.getStyleClass().addAll("vbox", "vbox-padding");
        vbox.getChildren().add(heading);
        vbox.getChildren().add(info);
        vbox.getChildren().add(table);

        HBox hBox = new HBox(20);
        hBox.getStyleClass().add("hbox");
        hBox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(hBox);
        hBox.getChildren().addAll(confirmBtn, menuBtn);

        menuBtn.setOnAction(event -> {
            MenuController menuController = new MenuController();
            window.switchScene(event, menuController.createContent());
        });

        double finalTotal = total + 15.0;
        confirmBtn.setOnAction(event -> {
            db.createOrder(date, products, finalTotal);
            MenuController menuController = new MenuController();
            window.switchScene(event, menuController.createContent());
        });

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(50, 25, 25, 25));
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.add(vbox, 0, 2);
        gridPane.add(hBox, 0, 3);
        return gridPane;
    }

}
