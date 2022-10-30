package com.example.delivery.controller.notifier;


public class AlertDecorator extends BaseNotifier {
    public AlertDecorator(INotifier wrapped) {
        super(wrapped);
    }

    @Override
    public void notify(String msg) {

        super.notify(msg);
    }
}
