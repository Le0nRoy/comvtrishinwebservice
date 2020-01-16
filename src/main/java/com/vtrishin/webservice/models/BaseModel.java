package com.vtrishin.webservice.models;

import java.util.Objects;

// Базовый класс модели, имеющий ключ id
public class BaseModel {

    public long getId() {

        return id;
    }
    public String getCommandCreateTable() {

        return commandCreateTable;
    }
    public static long getMaxId() {

        return maxId;
    }
    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseModel baseModel = (BaseModel) o;
        return id == baseModel.id;
    }
    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    protected BaseModel(long id) {

        this.id = id;
        maxId = id == (maxId + 1) ? id : maxId;
    }

    // FIXME different for Users and Adverts
    protected String commandCreateTable;
    private static long maxId = 0;
    private long id;


}
