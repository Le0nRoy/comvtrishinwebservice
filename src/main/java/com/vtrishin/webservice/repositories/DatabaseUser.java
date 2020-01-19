package com.vtrishin.webservice.repositories;

import com.vtrishin.webservice.models.BaseModel;
import com.vtrishin.webservice.models.User;

import java.sql.SQLException;
import java.util.List;

public class DatabaseUser extends BaseTable implements TableOperations {

    public DatabaseUser() throws SQLException {

        super(USER_TABLE);
    }
    @Override
    public boolean add(BaseModel model) throws SQLException {
//
//        String message;
//        if (model == null || model.getClass() == User.class) {
//            message = "Wrong class or null given as parameter.";
//            logger.warning(message);
//            return false;
//        }
//
//        try {
//            // TODO make check by id before adding
//            executeSqlStatement();
//            message = "";
//        } catch (SQLException e) {
//            message = "Failed to add " + model.getClass().toString().toLowerCase() + " to database.";
//            logger.warning(message);
//            return false;
//        }
//        logger.info(message);
        return true;
    }
    @Override
    public boolean remove(long id) throws SQLException {

//        String message;
//        if (id > BaseModel.getMaxId()) {
//            message = "No element with id = " + id + " in database.";
//            logger.warning(message);
//            return false;
//        }
//
//        try {
//            executeSqlStatement();
//            message = "";
//        } catch (SQLException e) {
//            message = "Failed to remove element with id " + id + " from database.\n" + e.getMessage();
//            logger.warning(message);
//            return false;
//        }
//        logger.info(message);
        return true;
    }
    @Override
    public BaseModel find(long id) throws SQLException {

//        String message;
//        if (id > BaseModel.getMaxId()) {
//            message = "No element with id = " + id + " in database.";
//            logger.warning(message);
//            return null;
//        }
//
//        User user;
//        try {
//            // TODO make check by id before adding
//            executeSqlStatement();
//            message = "";
//        } catch (SQLException e) {
//            message = "Failed to find element with id " + id + " in database.\n" + e.getMessage();
//            logger.warning(message);
//            return null;
//        }
//        logger.info(message);
//        return user;
        return null;
    }
    @Override
    public List<BaseModel> getAll(long id) throws SQLException {
//
//        String message;
//        // FIXME no check, just list all persons?
//        if (id > BaseModel.getMaxId()) {
//            message = "No person with id = " + id + " in database.";
//            logger.warning(message);
//            return null;
//        }
//
//        List<User> users;
//        try {
//            // TODO make check by id before adding
//            executeSqlStatement();
//            message = "";
//        } catch (SQLException e) {
//            message = "Failed to find element with id " + id + " in database.\n" + e.getMessage();
//            logger.warning(message);
//            return null;
//        }
//        logger.info(message);
//        return users;
        return null;
    }

    @Override
    protected void createTable() throws SQLException {

        // TODO may move it to BaseTable and set commands as strings in classes (will be read from BaseModel)
//        try {
//        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS" +
//                        tableName + "(" +
//                        "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
//                        "name varchar(255) NOT NULL," +
//                        "secondName varchar(255) NOT NULL," +
//                        "email varchar(255) NOT NULL," +
//                        "isEntity boolean NOT NULL)",
//                "Создана таблица " + tableName);
//        } catch (SQLException e) {
//            logger.warning("Failed to create table " + tableName + "!");
//        }
    }
    @Override
    protected void createForeignKeys() throws SQLException { }
}
