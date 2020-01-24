package com.vtrishin.webservice.models;

import com.google.gson.annotations.Expose;

public class BaseModel {

    public int getId() {

        return id;
    }
    public String getCommandCreateTable() {

        return commandCreateTable;
    }

    protected BaseModel(int id) {

        this.id = id;
    }

    // FIXME make static and use in creating tables
    protected static String commandCreateTable;

    @Expose
    private int id;

}
