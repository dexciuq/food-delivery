package com.example.delivery.controller;

import com.example.delivery.Main;
import com.example.delivery.db.DatabaseAdapter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.util.Objects;

public class AuthController {
    DatabaseAdapter db = DatabaseAdapter.getInstance();
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

    private void switchScene(ActionEvent event, Parent root) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 400);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets().add(String.valueOf(Main.class.getResource("assets/css/style.css")));
        stage.setScene(scene);
        stage.show();
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

        registerBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                switchScene(event, createContent("Register"));
            }
        });

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
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
                    switchScene(e, menu.createContent());
                } else {
                    actionTarget.setText("Wrong username or password...");
                }
            }
        });
        gridPane.add(sceneTitle, 0, 0, 2, 1);
        gridPane.add(usernameLabel, 0, 1);
        gridPane.add(usernameField, 1, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(horizontalBox, 1, 4);
        gridPane.add(actionTarget, 1, 6);
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

        registerBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = nameField.getText();
                String username = usernameField.getText();
                String password = passwordField.getText();

                actionTarget.setFill(Color.FIREBRICK);
                if (name.isEmpty()) {
                    actionTarget.setText("Empty name... Try again!");
                } else if (username.isEmpty()) {
                    actionTarget.setText("Empty username... Try again!");
                } else if (password.isEmpty()) {
                    actionTarget.setText("Empty password... Try again!");
                } else {
                    db.createUser(name, username, password);
                    actionTarget.setFill(Color.GREEN);
                    actionTarget.setText("Success!");
                    switchScene(event, createContent("Login"));
                }
            }
        });

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                switchScene(event, createContent("Login"));
            }
        });

        gridPane.add(sceneTitle, 0, 0, 2, 1);
        gridPane.add(name, 0, 1);
        gridPane.add(nameField, 1, 1);
        gridPane.add(username, 0, 2);
        gridPane.add(usernameField, 1,  2);
        gridPane.add(password, 0, 3);
        gridPane.add(passwordField, 1, 3);
        gridPane.add(horizontalBox, 1, 4);
        gridPane.add(actionTarget, 1, 6);

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
