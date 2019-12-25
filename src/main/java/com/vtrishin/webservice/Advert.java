package com.vtrishin.webservice;

import java.time.LocalDate;

public class Advert {

    public Advert(int advertId, String header, String category, String phoneNumber) {
        this.advertId = advertId;
        this.header = header;
        this.category = category;
        this.phoneNumber = phoneNumber;
    }

    public int getAdvertId() {
        return advertId;
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

    private int advertId = 0;
    private String header = "";
    private String category = "";
    private String phoneNumber = "";
    private final LocalDate creationDate = LocalDate.now();

}
