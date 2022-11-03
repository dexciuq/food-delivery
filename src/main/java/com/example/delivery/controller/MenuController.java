package com.example.delivery.controller;

import com.example.delivery.Main;
import com.example.delivery.controller.menu.ICollection;
import com.example.delivery.controller.menu.Iterator;
import com.example.delivery.controller.menu.Menu;
import com.example.delivery.db.*;
import com.example.delivery.model.Product;
import com.example.delivery.model.User;
import javafx.beans.value.ObservableValue;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import java.util.*;

public class MenuController {
    private final List<Product> products;
    private final DatabaseAdapter db;
    private final Window window;
    private final User user;
    private double total = 0;

    public MenuController() {
        products = new ArrayList<>();
        db =  DatabaseAdapter.getInstance();
        window = new Window();
        user = User.getInstance();
    }

    public Parent createContent() {
        // HEADING, PRICE
        Text menuText = new Text("MENU:");
        Label totalString = new Label("Total: 0.0$");
        menuText.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        totalString.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        // MENU
        VBox menuListBox = new VBox(10);
        ICollection menu = new Menu(db.selectAllProducts());
        Iterator iterator = menu.getIterator();
        while (iterator.hasNext()) {
            Product item = iterator.next();

            ImageView imageView = new ImageView(String.valueOf(Main.class.getResource("assets/img/" + item.getProductName().toLowerCase() + ".png")));
            //ImageView imageView = new ImageView(item.getProductSrc());
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);

            CheckBox checkBox = new CheckBox(item.toString());
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

            HBox itemBox = new HBox(10, imageView, checkBox);
            menuListBox.getChildren().add(itemBox);
        }

        // BUTTONS
        Button usernameBtn = new Button(user.getName());
        Button exitBtn = new Button("Exit");
        Button orderBtn = new Button("Order");
        Text actionTarget = new Text();

        usernameBtn.getStyleClass().setAll("btn-sm","btn-default");
        exitBtn.getStyleClass().setAll("btn-sm","btn-danger");
        orderBtn.getStyleClass().setAll("btn-sm","btn-success");

        usernameBtn.setOnAction(event -> {
            ProfileController profileController = new ProfileController(user);
            window.switchScene(event, profileController.createContent());
        });

        orderBtn.setOnAction(event -> {
            if (total == 0) {
                actionTarget.setFill(Color.FIREBRICK);
                actionTarget.setText("First choose product!!");
                return;
            }

            OrderController orderController = new OrderController(products);
            window.switchScene(event, orderController.createContent());
        });

        exitBtn.setOnAction(event -> {
            AuthController authController = new AuthController();
            User.setNull();
            window.switchScene(event, authController.createContent());
        });

        HBox hBoxBtn = new HBox(10, usernameBtn, exitBtn);
        HBox hBoxTitle = new HBox(80, menuText, hBoxBtn);
        hBoxTitle.setAlignment(Pos.CENTER_RIGHT);


        // RESULT BOX
        VBox vBox = new VBox(10, hBoxTitle, menuListBox, totalString, orderBtn, actionTarget);
        vBox.getStyleClass().addAll("vbox", "menu");


        // GRID PANE
        GridPane gridPane = new GridPane();
        gridPane.add(vBox, 0 , 0);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10, 25, 25, 25));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        return gridPane;
    }
}
