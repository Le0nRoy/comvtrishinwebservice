package com.vtrishin.webservice.repositories;

import com.vtrishin.webservice.models.BaseModel;

import java.sql.SQLException;
import java.util.List;

// Операции с таблицами
public interface TableOperations {

    String ADVERT_TABLE = "adverts";
    String USER_TABLE = "users";

    // TODO May move same code to BaseTable?
    boolean add(BaseModel model) throws SQLException;
    boolean remove(long id) throws SQLException;
    BaseModel find(long id) throws SQLException;
    // TODO decide how to get all users
    List<BaseModel> getAll(long id) throws SQLException;
}
