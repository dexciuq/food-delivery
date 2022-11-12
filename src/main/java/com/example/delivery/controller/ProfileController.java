package com.example.delivery.controller;

import com.example.delivery.Main;
import com.example.delivery.db.DatabaseAdapter;
import com.example.delivery.model.*;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
        Label heading = new Label("Your orders:");
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

        Button settingsBtn = new Button("Settings");
        ImageView settingsIcon = new ImageView(String.valueOf(Main.class.getResource("assets/img/settings.png")));
        settingsIcon.setFitHeight(20);
        settingsIcon.setFitWidth(20);
        settingsBtn.setGraphic(settingsIcon);
        settingsBtn.getStyleClass().setAll("btn-sm","btn-danger");
        settingsBtn.setOnAction(event -> {
            window.switchScene(event, this.createSettingsContent());
        });

        HBox hBox1 = new HBox(100, greeting, settingsBtn);
        hBox1.setAlignment(Pos.TOP_RIGHT);

        //VBOX
        VBox vBox = new VBox(10, hBox1, heading, hBox, menuBtn);
        vBox.getStyleClass().addAll("vbox", "vbox-padding");

        // GRID PANE
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(vBox, 0, 0);
        return gridPane;
    }

    public Parent createSettingsContent() {
        Label heading = new Label("Change password:");
        heading.setFont(Font.font("Arial", FontWeight.NORMAL, 13));

        Label oldPasswordLabel = setLabel( new Label("Current Password: "));
        Label newPasswordLabel = setLabel( new Label("New Password: "));
        Label repeatNewPasswordLabel = setLabel( new Label("Repeat Password: "));
        PasswordField oldPasswordField = new PasswordField();
        oldPasswordField.setPrefWidth(200);

        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPrefWidth(200);

        PasswordField passwordField = new PasswordField();
        passwordField.setPrefWidth(200);

        // BUTTONS
        Button back = new Button("Back");
        ImageView backIcon = new ImageView(String.valueOf(Main.class.getResource("assets/img/back.png")));
        backIcon.setFitHeight(20);
        backIcon.setFitWidth(20);
        back.setGraphic(backIcon);
        back.getStyleClass().setAll("btn-sm", "btn-info");
        back.setOnAction(event -> {
            window.switchScene(event, this.createContent());
        });

        Button changeBtn = new Button("Change");
        ImageView settingsIcon = new ImageView(String.valueOf(Main.class.getResource("assets/img/password.png")));
        settingsIcon.setFitHeight(20);
        settingsIcon.setFitWidth(20);
        changeBtn.setGraphic(settingsIcon);
        changeBtn.getStyleClass().setAll("btn-sm","btn-danger");
        Text actionTarget = new Text();
        changeBtn.setOnAction(event -> {
            String oldPassword = oldPasswordField.getText();
            String newPassword = newPasswordField.getText();
            String repeatedPassword = passwordField.getText();

            actionTarget.setFill(Color.FIREBRICK);
            if (oldPassword.isEmpty()) {
                actionTarget.setText("Empty old password... Try again!");
            } else if (newPassword.isEmpty()) {
                actionTarget.setText("Empty new password... Try again!");
            } else if (repeatedPassword.isEmpty()) {
                actionTarget.setText("Empty repeated password... Try again!");
            } else if (!oldPassword.equals(user.getPassword())) {
                actionTarget.setText("Old password is wrong!");
            } else if (!newPassword.equals(repeatedPassword)) {
                actionTarget.setText("Repeated & new password don't same!");
            } else {
                db.updatePassword(user.getUsername(), repeatedPassword);
                actionTarget.setFill(Color.GREEN);
                actionTarget.setText("Success!");
            }
        });

        HBox hBox = new HBox(10, back, changeBtn);
        VBox vBox1 = new VBox(20, oldPasswordLabel, newPasswordLabel, repeatNewPasswordLabel);
        VBox vBox2 = new VBox(10, oldPasswordField, newPasswordField, passwordField);
        HBox hBox1 = new HBox(10, vBox1, vBox2);

        //VBOX
        VBox vBox = new VBox(20, heading, hBox1, hBox, actionTarget);
        vBox.getStyleClass().addAll("vbox", "vbox-padding");

        // GRID PANE
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(vBox, 0, 0);
        return gridPane;
    }

    private Label setLabel(Label labelName) {
        labelName.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
        return labelName;
    }

}
