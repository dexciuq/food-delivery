package com.example.delivery.model;

public class User {
    private static User instance;
    public static User getInstance(){
        if (instance == null) instance = new User();
        return instance;
    }

    public static void setNull() {
        instance = null;
    }
    private User() {}
    private Long id;
    private String name;
    private String username;
    private String password;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
