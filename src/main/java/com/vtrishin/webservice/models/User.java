package com.vtrishin.webservice.models;

import com.google.gson.annotations.Expose;

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

    @Override
    public String toString(){

        return "UserId = " + getId() + "\nName = " + name + "\nSecond name = " + secondName +
                "\nEmail = " + userEmail + "\nEntity = " + isEntity;
    }

    static {
        commandCreateTable = "(" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "name varchar(255) NOT NULL," +
                "secondName varchar(255) NOT NULL," +
                "email varchar(255) NOT NULL," +
                "isEntity boolean NOT NULL)";
    }
    @Expose
    private String name = "";
    @Expose
    private String secondName = "";
    @Expose
    private String userEmail = "";
    @Expose
    private boolean isEntity = false;

}
