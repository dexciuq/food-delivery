package com.example.delivery;

import com.example.delivery.controller.AuthController;
import com.example.delivery.db.DatabaseAdapter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kordamp.bootstrapfx.BootstrapFX;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        DatabaseAdapter db = DatabaseAdapter.getInstance();
        db.connect();
        AuthController auth = new AuthController();
        Scene scene = new Scene(auth.createContent(), 750, 400);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets().add(String.valueOf(Main.class.getResource("assets/css/style.css")));
        stage.getIcons().add(new Image(String.valueOf(Main.class.getResource("assets/img/icon.png"))));
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Food Delivery \"Slice\"");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}