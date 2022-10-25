package com.example.delivery.controller;

import com.example.delivery.db.DatabaseAdapter;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

import java.util.Objects;

public class AuthController {
    DatabaseAdapter db = DatabaseAdapter.getInstance();
    Window window = new Window();
    public Parent createContent(){
        return createContent("Login");
    }
    private Parent createContent(String content) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        if (Objects.equals(content, "Register")) return createRegisterContent(gridPane);
        return createLoginContent(gridPane);
    }

    private Parent createLoginContent(GridPane gridPane) {
        Text sceneTitle = setHeadingOne(new Text("Welcome"));
        Text actionTarget = new Text();

        Label usernameLabel = setLabel( new Label("Username: "));
        Label passwordLabel = setLabel( new Label("Password: "));
        TextField usernameField = setTextField(new TextField());
            usernameField.setPrefWidth(200);
        PasswordField passwordField = new PasswordField();
            passwordField.setPrefWidth(200);

        Button btn = new Button("Sign in");
        btn.getStyleClass().setAll("btn-sm","btn-info");

        Button registerBtn = new Button("Registration");
        registerBtn.getStyleClass().setAll("btn-sm","btn-default");

        HBox horizontalBox = new HBox(10);
        horizontalBox.setAlignment(Pos.BOTTOM_RIGHT);
        horizontalBox.getChildren().add(registerBtn);
        horizontalBox.getChildren().add(btn);

        registerBtn.setOnAction(event -> window.switchScene(event, createContent("Register")));

        btn.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            actionTarget.setFill(Color.FIREBRICK);
            if (username.isEmpty()) {
                actionTarget.setText("Empty username... Try again!");
            } else if (password.isEmpty()) {
                actionTarget.setText("Empty password... Try again!");
            } else if (db.loginUser(username, password)) {
                MenuController menu = new MenuController();
                actionTarget.setFill(Color.GREEN);
                actionTarget.setText("Success!");
                window.switchScene(event, menu.createContent());
            } else {
                actionTarget.setText("Wrong username or password...");
            }
        });

        VBox vBox = new VBox(20, usernameLabel, passwordLabel);
        VBox vBoxFlied = new VBox(10, usernameField, passwordField);
        HBox hBox = new HBox(10, vBox, vBoxFlied);
        VBox vBoxResult = new VBox(20, sceneTitle, hBox, horizontalBox, actionTarget);
        vBoxResult.getStyleClass().add("vbox");
        gridPane.add(vBoxResult, 0, 0);
        return gridPane;
    }

    private Parent createRegisterContent(GridPane gridPane) {
        Text sceneTitle = setHeadingOne(new Text("Registration"));
        Text actionTarget = new Text();

        Label name = setLabel( new Label("Name: "));
        Label username = setLabel( new Label("Username: "));
        Label password = setLabel( new Label("Password: "));
        TextField nameField = setTextField(new TextField());
            nameField.setPrefWidth(200);
        TextField usernameField = setTextField(new TextField());
            usernameField.setPrefWidth(200);
        PasswordField passwordField = new PasswordField();
            passwordField.setPrefWidth(200);

        Button registerBtn = new Button("Register");
        registerBtn.getStyleClass().setAll("btn-sm","btn-info");

        Button btn = new Button("I've an account");
        btn.getStyleClass().setAll("btn-sm","btn-default");

        HBox horizontalBox = new HBox(10);
        horizontalBox.setAlignment(Pos.BOTTOM_RIGHT);
        horizontalBox.getChildren().add(btn);
        horizontalBox.getChildren().add(registerBtn);

        registerBtn.setOnAction(event -> {
            String name1 = nameField.getText();
            String username1 = usernameField.getText();
            String password1 = passwordField.getText();

            actionTarget.setFill(Color.FIREBRICK);
            if (name1.isEmpty()) {
                actionTarget.setText("Empty name... Try again!");
            } else if (username1.isEmpty()) {
                actionTarget.setText("Empty username... Try again!");
            } else if (password1.isEmpty()) {
                actionTarget.setText("Empty password... Try again!");
            } else {
                db.createUser(name1, username1, password1);
                actionTarget.setFill(Color.GREEN);
                actionTarget.setText("Success!");
                window.switchScene(event, createContent("Login"));
            }
        });

        btn.setOnAction(event -> window.switchScene(event, createContent("Login")));

        VBox vBox = new VBox(20, name, username, password);
        VBox vBoxFlied = new VBox(10, nameField, usernameField, passwordField);
        HBox hBox = new HBox(10, vBox, vBoxFlied);
        VBox vBoxResult = new VBox(20, sceneTitle, hBox, horizontalBox, actionTarget);
        vBoxResult.getStyleClass().add("vbox");
        gridPane.add(vBoxResult, 0, 0);

        return gridPane;
    }

    private Text setHeadingOne(Text sceneTitle) {
        sceneTitle.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        return sceneTitle;
    }

    private Label setLabel(Label labelName) {
        labelName.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
        return labelName;
    }

    private TextField setTextField(TextField name) {
        name.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
        return name;
    }
}
