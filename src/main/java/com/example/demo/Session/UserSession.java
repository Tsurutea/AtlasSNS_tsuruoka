package com.example.demo.Session;

import java.io.Serializable;

public class UserSession implements Serializable {
    private Integer id;
    private String name;
    private String email;
    private String iconImage;

    public UserSession(Integer id, String name, String email, String iconImage) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.iconImage = iconImage;
    }
    

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getIconImage() { return iconImage; }
}