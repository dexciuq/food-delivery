package com.example.delivery.controller.menu;

import com.example.delivery.model.Product;

import java.util.List;

public class Menu implements ICollection {
    private final List<Product> menuList;
    public Menu(List<Product> menuList) {
        this.menuList = menuList;
    }
    @Override
    public Iterator getIterator() {
        return new MenuIterator();
    }

    private class MenuIterator implements Iterator {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < menuList.size();
        }

        @Override
        public Product next() {
            if (hasNext()) return menuList.get(index++);
            return null;
        }
    }
}

