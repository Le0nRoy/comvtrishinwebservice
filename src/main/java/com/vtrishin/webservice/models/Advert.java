package com.vtrishin.webservice.models;

import java.time.LocalDate;

public class Advert extends BaseModel {

    // TODO maybe change personId to User and get id from there
    public Advert(int advertId, int personId, String header, String category, String phoneNumber) {

        super(advertId);
        this.personId = personId;
        this.header = header;
        this.category = category;
        this.phoneNumber = phoneNumber;

        commandCreateTable = "(" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "personId BIGINT " + //TODO add foreign key
                "header VARCHAR(255) NOT NULL," +
                "category VARCHAR(255) NOT NULL," +
                "phoneNumber VARCHAR(255) NOT NULL," +
                "creationTime TIMESTAMP NOT NULL)";
    }
    public int getPersonId() {

        return this.personId;
    }
    public String getHeader() {

        return header;
    }
    public String getCategory() {

        return category;
    }
    public String getPhoneNumber() {

        return phoneNumber;
    }
    public LocalDate getCreationDate() {

        return creationDate;
    }

    private int personId = 0;
    private String header = "";
    private String category = "";
    private String phoneNumber = "";
    private final LocalDate creationDate = LocalDate.now();

    // TODO do this methods in other classes
//    @Override
//    public String toString() {
//        return "User{" +
//                "name='" + name + '\'' +
//                ", password='" + password + '\'' +
//                '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        User user = (User) o;
//
//        if (name != null ? !name.equals(user.name) : user.name != null) return false;
//        return password != null ? password.equals(user.password) : user.password == null;
//
//    }
//
//    @Override
//    public int hashCode() {
//        int result = name != null ? name.hashCode() : 0;
//        result = 31 * result + (password != null ? password.hashCode() : 0);
//        return result;
//    }
}
