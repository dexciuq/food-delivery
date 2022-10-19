package com.example.delivery.controller;

import com.example.delivery.controller.menu.ICollection;
import com.example.delivery.controller.menu.Iterator;
import com.example.delivery.controller.menu.Menu;
import com.example.delivery.db.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class MenuController {
    DatabaseAdapter db;
    public MenuController(DatabaseAdapter db) {
        this.db = db;
    }

    public Parent createContent() {
        StringBuilder stringBuilder = new StringBuilder();
        ICollection menu = new Menu(db.selectAllProducts());
        Iterator iterator = menu.getIterator();
        while (iterator.hasNext()) {
            stringBuilder.append(iterator.next().toString());
        }
        Text menuList = new Text();
        menuList.setText(String.valueOf(stringBuilder));

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        gridPane.add(menuList, 0, 0, 2, 1);
        return gridPane;
    }
}
