package com.example.delivery.controller.payment;

public class PaymentByCreditCard implements IPaymentStrategy{
    @Override
    public void pay(double amount) {
        System.out.println("payment " + amount + "$ was successfully done via CreditCard");
    }
}
