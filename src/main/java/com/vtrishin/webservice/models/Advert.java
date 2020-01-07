package com.vtrishin.webservice.models;

import java.time.LocalDate;

public class Advert extends BaseModel {

    public Advert(int advertId, String header, String category, String phoneNumber) {

        super(advertId);
        this.header = header;
        this.category = category;
        this.phoneNumber = phoneNumber;
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

    private String header = "";
    private String category = "";
    private String phoneNumber = "";
    private final LocalDate creationDate = LocalDate.now();

}
