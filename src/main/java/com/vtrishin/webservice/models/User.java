package com.vtrishin.webservice.models;

public class User extends BaseModel {

    public User(int userId, String name, String secondName, String userEmail, boolean isEntity) {

        super(userId);
        this.name = name;
        this.secondName = secondName;
        this.userEmail = userEmail;
        this.isEntity = isEntity;
    }
    public String getName() {

        return name;
    }
    public String getSecondName() {

        return secondName;
    }
    public String getUserEmail() {

        return userEmail;
    }
    public boolean isEntity() {

        return isEntity;
    }

    private String name = "";
    private String secondName = "";
    private String userEmail = "";
    private boolean isEntity = false;


}
