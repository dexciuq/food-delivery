package com.example.delivery.controller;

import com.example.delivery.Main;
import com.example.delivery.controller.notifier.*;
import com.example.delivery.controller.payment.IPaymentStrategy;
import com.example.delivery.controller.payment.PaymentByCreditCard;
import com.example.delivery.controller.payment.PaymentByPayPal;
import com.example.delivery.db.DatabaseAdapter;
import com.example.delivery.model.Product;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.List;

public class PaymentController {
    private final DatabaseAdapter db =  DatabaseAdapter.getInstance();
    private final Window window = new Window();
    private IPaymentStrategy paymentStrategy;
    private int method = 0;
    private final double finalTotal;
    private final String date;
    private final List<Product> products;

    public PaymentController(double finalTotal, String date, List<Product> products) {
        this.finalTotal = finalTotal;
        this.date = date;
        this.products = products;
    }

    public Parent createContent() {
        Text heading = new Text("Choose payment method");
        Label label1 = new Label("Card Number");
        Label label2 = new Label("CVV");
        TextField textField1 = new TextField();
        TextField textField2 = new PasswordField();
        textField1.setPrefWidth(200);
        textField2.setPrefWidth(200);
        label1.setLabelFor(textField1);
        label2.setLabelFor(textField2);
        label1.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
        label2.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 15));


        ToggleGroup group = new ToggleGroup();
        ImageView imageView = new ImageView(String.valueOf(Main.class.getResource("assets/img/card.png")));
        imageView.setFitWidth(200);
        imageView.setFitHeight(80);

        RadioButton creditCard = new RadioButton("Credit Card");
        creditCard.setOnAction(event -> {
            label1.setText("Card Number");
            label2.setText("CVV");
            method = 0;
            imageView.setImage(new Image(String.valueOf(Main.class.getResource("assets/img/card.png"))));
            imageView.setFitWidth(200);
            imageView.setFitHeight(80);
        });

        RadioButton payPal = new RadioButton("PayPal");
        payPal.setOnAction((event -> {
            label1.setText("Email");
            label2.setText("Password");
            method = 1;
            imageView.setImage(new Image(String.valueOf(Main.class.getResource("assets/img/pay-pal.png"))));
            imageView.setFitWidth(180);
            imageView.setFitHeight(80);
        }));

        creditCard.setToggleGroup(group);
        payPal.setToggleGroup(group);
        creditCard.setSelected(true);

        HBox hBox0 = new HBox(10, creditCard, payPal);
        VBox hBox1 = new VBox(20, label1, label2);
        VBox hBox2 = new VBox(10, textField1, textField2);
        HBox hBox = new HBox(10, hBox1, hBox2);

        VBox vBox1 = new VBox(10, heading, hBox0, imageView, hBox);
        vBox1.setAlignment(Pos.CENTER);

        Button confirmBtn = new Button("Confirm");
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        confirmBtn.getStyleClass().addAll("btn-sm", "btn-success");
        confirmBtn.setOnAction(event -> {
            if (method == 0) {
                paymentStrategy = new PaymentByCreditCard(textField1.getText(), textField2.getText());
            } else {
                paymentStrategy = new PaymentByPayPal(textField1.getText(), textField2.getText());
            }
            paymentStrategy.pay(finalTotal);
            INotifier notifier =
                    new EmailDecorator(
                            new PhoneDecorator(new UserNotifier()));

            String msg = "Successfully ordered! Your order will be ready in 45 min.\nThanks for using our service\n";
            notifier.notify(msg);
            a.setTitle("Delivery");
            a.setHeaderText("Attention!!! Your order...");
            a.setContentText(msg);
            a.showAndWait();

            db.createOrder(date, this.products, finalTotal);
            MenuController menuController = new MenuController();
            window.switchScene(event, menuController.createContent());
        });

        VBox vBox = new VBox(10);
        vBox.getStyleClass().addAll("vbox");
        vBox.getChildren().addAll(vBox1,confirmBtn);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(vBox, 0, 0);
        return gridPane;
    }
}
