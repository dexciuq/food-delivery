package com.example.delivery.controller.payment;

public class PaymentService {
    private double cost;
    private final boolean includeDelivery;
    private IPaymentStrategy paymentStrategy;
    public IPaymentStrategy getPaymentStrategy() {
        return paymentStrategy;
    }
    public void setPaymentStrategy(IPaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }
    public PaymentService(IPaymentStrategy paymentStrategy, boolean includeDelivery) {
        this.includeDelivery = includeDelivery;
        this.paymentStrategy = paymentStrategy;
    }
    private double getTotalCost(){
        double costDelivery = 10;
        return includeDelivery ? cost + costDelivery : cost;
    }
    public void processOrder() {
        paymentStrategy.pay(getTotalCost());
    }
}
