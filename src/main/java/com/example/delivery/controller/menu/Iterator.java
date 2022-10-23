package com.example.delivery.controller.menu;

import com.example.delivery.model.Product;

public interface Iterator {
    boolean hasNext();
    Product next();
}
