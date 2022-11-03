package com.example.delivery.controller.payment;

public class PaymentByCreditCard implements IPaymentStrategy {
    private String cardNumber;
    private String cvv;

    public PaymentByCreditCard(String cardNumber, String cvv) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Payment method: Credit Card\n" + cardNumber);
        System.out.println("Payment " + amount + "$ was successfully done via CreditCard" + '\n');
    }
}
