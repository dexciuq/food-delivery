package com.example.delivery.controller.payment;

public class PaymentByPayPal implements IPaymentStrategy{
    private String email;
    private String password;

    public PaymentByPayPal(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Payment method: PayPal\n" + email);
        System.out.println("Payment " + amount + "$ was successfully done via PayPal " + email + '\n');
    }
}
