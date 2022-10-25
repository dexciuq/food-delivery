package com.example.delivery.controller;

import com.example.delivery.controller.menu.ICollection;
import com.example.delivery.controller.menu.Iterator;
import com.example.delivery.controller.menu.Menu;
import com.example.delivery.db.*;
import com.example.delivery.model.Product;
import com.example.delivery.model.User;
import javafx.beans.value.ObservableValue;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.*;

import java.util.ArrayList;
import java.util.List;

public class MenuController {
    private final List<Product> products = new ArrayList<>();
    private double total = 0;
    private final DatabaseAdapter db =  DatabaseAdapter.getInstance();
    private final Window window = new Window();

    public Parent createContent() {
        VBox box = new VBox(10);
        Label totalString = new Label("Total: 0.0$");
        totalString.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        User user = User.getInstance();

        ICollection menu = new Menu(db.selectAllProducts());
        Iterator iterator = menu.getIterator();
        while (iterator.hasNext()) {
            HBox vBox = new HBox(10);
            Product item = iterator.next();
            ImageView imageView = new ImageView(item.getProductSrc());
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            CheckBox checkBox = new CheckBox(item.toString());
            vBox.getChildren().add(imageView);
            vBox.getChildren().add(checkBox);
            box.getChildren().add(vBox);
            checkBox.selectedProperty().addListener(
                    (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                        if (new_val) {
                            products.add(item);
                            total += item.getProductPrice();
                        } else if (old_val) {
                            products.remove(item);
                            total -= item.getProductPrice();
                        }
                        total = ((double) Math.round(total * 100)) / 100;
                        totalString.setText("Total: " + total + '$');
                    });
        }
        Text menuText = setHeadingThree(new Text("Menu: "));
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.TOP_RIGHT);
        Button usernameBtn = new Button(user.getName());
        Button exitBtn = new Button("Exit");
        usernameBtn.getStyleClass().setAll("btn-sm","btn-default");
        exitBtn.getStyleClass().setAll("btn-sm","btn-danger");
        hBox.getChildren().add(usernameBtn);
        hBox.getChildren().add(exitBtn);
        Button orderBtn = new Button("Order");
        orderBtn.getStyleClass().setAll("btn-sm","btn-success");

        orderBtn.setOnAction(event -> {
            OrderController orderController = new OrderController();
            orderController.setProducts(products);
            window.switchScene(event, orderController.createContent());
        });

        exitBtn.setOnAction(event -> {
            AuthController authController = new AuthController();
            User.setNull();
            window.switchScene(event, authController.createContent());
        });

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 25, 25, 25));
        gridPane.add(hBox, 1, 0);
        gridPane.add(menuText, 0, 1);
        gridPane.add(box, 0, 3);
        gridPane.add(totalString, 1, 4);
        gridPane.add(orderBtn, 0, 5);

        return gridPane;
    }

    private Text setHeadingThree(Text sceneTitle) {
        sceneTitle.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        return sceneTitle;
    }
}
