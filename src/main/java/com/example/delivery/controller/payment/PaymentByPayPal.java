package com.example.delivery.controller.payment;

public class PaymentByPayPal implements IPaymentStrategy{
    @Override
    public void pay(double amount) {
        System.out.println("payment " + amount + "$ was successfully done via PayPal");
    }
}
