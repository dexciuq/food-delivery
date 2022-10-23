package com.example.delivery.controller;

import com.example.delivery.Main;
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
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

public class MenuController {
    double total = 0;
    DatabaseAdapter db =  DatabaseAdapter.getInstance();

    public Parent createContent() {
        VBox box = new VBox(10);
        Label totalString = new Label("Total: 0.0$");
        totalString.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        User user = User.getInstance();

        ICollection menu = new Menu(db.selectAllProducts());
        Iterator iterator = menu.getIterator();
        while (iterator.hasNext()) {
            Product item = iterator.next();
            CheckBox checkBox = new CheckBox(item.toString());
            box.getChildren().add(checkBox);
            checkBox.selectedProperty().addListener(
                    (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                        if (new_val) {
                            total += item.getProductPrice();
                        } else if (old_val) {
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

        exitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AuthController authController = new AuthController();
                User.setNull();
                switchScene(event, authController.createContent());
            }
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

    private void switchScene(ActionEvent event, Parent root) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 400);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets().add(String.valueOf(Main.class.getResource("assets/css/style.css")));
        stage.setScene(scene);
        stage.show();
    }
}
