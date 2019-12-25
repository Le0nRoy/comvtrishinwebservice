package com.vtrishin.webservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Logger;

public class User {
    public User(int userId, String name, String secondName, String userEmail, boolean isEntity) {
        this.userId = userId;
        this.name = name;
        this.secondName = secondName;
        this.userEmail = userEmail;
        this.isEntity = isEntity;
    }

    public int getUserId() {
        return userId;
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

    public int createAdvert() {
        return 0;
    }

    public void deleteAdvert(int id) {

    }

    public Advert findAdvert(int id) {
        return null;
    }

    private int userId = 0;
    private String name = "";
    private String secondName = "";
    private String userEmail = "";
    private boolean isEntity = false;

    Connection databaseConnection = createDatabaseConnection();
    private Connection createDatabaseConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager
                    .getConnection("jdbc:postgresql:/../db/Adverts" + userId + ".userDataBase",
                            "postgres", "123");
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return null;
    }

    Logger logger = Logger.getLogger(User.class.getName());

}
