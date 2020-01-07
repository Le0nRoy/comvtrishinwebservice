package com.vtrishin.webservice.repositories;

import java.sql.SQLException;

// Операции с таблицами
public interface TableOperations {

    String ADVERT_TABLE = "adverts";
    String USER_TABLE = "users";

    void createTable() throws SQLException;
    void createForeignKeys() throws SQLException;
//    void createExtraConstraints() throws SQLException;
//    void read();
}
