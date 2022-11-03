package com.example.delivery.controller.notifier;


import com.example.delivery.model.User;

public class PhoneDecorator extends BaseNotifier {
    public PhoneDecorator(INotifier wrapped) {
        super(wrapped);
    }

    @Override
    public void notify(String msg) {
        System.out.println("Message was send to Phone number to " + User.getInstance().getUsername() + " by " +
                User.getInstance().getPhoneNumber());
        super.notify(msg);
    }
}
