package com.example.delivery.controller.notifier;


public abstract class BaseNotifier implements INotifier {
    private final INotifier wrapped;

    public BaseNotifier(INotifier wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void notify(String msg) {
        wrapped.notify(msg);
    }

}
