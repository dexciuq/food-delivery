package com.example.delivery.controller.notifier;


import com.example.delivery.model.User;

public class EmailDecorator extends BaseNotifier {
    public EmailDecorator(INotifier wrapped) {
        super(wrapped);
    }

    @Override
    public void notify(String msg) {
        System.out.println("Message was send to Email to " + User.getInstance().getUsername() + " by " +
                User.getInstance().getEmail());
        super.notify(msg);
    }
}
