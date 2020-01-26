package com.vtrishin.webservice.models;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;

public class Advert extends BaseModel {

    // FIXME maybe change personId to User and get id from there
    /**
     * Fill model for advert
     * FIXME add enum for categories
     * @param category
     */
    public Advert(int advertId, int userId, String header, String category,
                  String phoneNumber, LocalDateTime creationDate) {

        super(advertId);
        this.userId = userId;
        this.header = header;
        this.category = category;
        this.phoneNumber = phoneNumber;
        this.creationDate = creationDate;
    }
    public int getUserId() {

        return this.userId;
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
    public LocalDateTime getCreationDate() {

        return creationDate;
    }
    public void setCreationDate(LocalDateTime creationDate){
        this.creationDate = creationDate;
    }
    @Override
    public String toString(){

        return "AdvertId = " + getId() + "\nUserId = " + userId + "\nHeader = " + header +
                "\nCategory = " + category + "\nPhone Number = " + phoneNumber + "\nCreation Date = " + creationDate;
    }

    static {
        commandCreateTable = "(" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "personId BIGINT " +
                "header VARCHAR(255) NOT NULL," +
                "category VARCHAR(255) NOT NULL," +
                "phoneNumber VARCHAR(255) NOT NULL," +
                "creationTime TIMESTAMP NOT NULL)";
//        commandAddForeignKey =
    }
    @Expose
    private int userId = 0;
    @Expose
    private String header = "";
    @Expose
    private String category = "";
    @Expose
    private String phoneNumber = "";
    @Expose
    private LocalDateTime creationDate;

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
