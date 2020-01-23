package com.vtrishin.webservice.repositories;

import com.vtrishin.webservice.models.BaseModel;
import com.vtrishin.webservice.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUser extends BaseTable {

    /**
     * Sets table name of database (table name is taken from BaseTable)
     * @throws SQLException
     */
    public DatabaseUser() throws SQLException {

        super(USER_TABLE);
    }
    @Override
    public boolean add(BaseModel model) throws SQLException {
//
//        if (!(model instanceof User)) {
//            logger.log(logger.WARNING, "Wrong class or null given as parameter.");
//            return false;
//        }
//        User usr = (User)model;
//
//        String WRITE_OBJECT_SQL = "INSERT INTO " + tableName +
//                "(id, " +
//                "name, " +
//                "secondName, " +
//                "email, " +
//                "isEntity" +
//                ") VALUES (?, ?, ?, ?, ?)";
//        try(PreparedStatement pstmt = getPreparedStatement(WRITE_OBJECT_SQL)) {
//            // TODO make check by id before adding
////            PreparedStatement pstmt = getPreparedStatement(WRITE_OBJECT_SQL);
//            pstmt.setInt(1, usr.getId());
//            pstmt.setString(2, usr.getName());
//            pstmt.setString(3, usr.getSecondName());
//            pstmt.setString(4, usr.getUserEmail());
//            pstmt.setBoolean(5, usr.isEntity());
//            logger.log(logger.INFO, "Filled statement.");
//            writeToSQL(pstmt);
//        } catch (SQLException e) {
//            logger.log(logger.WARNING, "Failed to add " +
//                    model.getClass().toString().toLowerCase() + " to database.");
//            logger.log(logger.WARNING, e.getMessage());
//            return false;
//        }
        return false;
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
    public BaseModel find(long id, long userId) throws SQLException {

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
        return new User(1, "Vadim", "Trishin", "myemail@email.com", false);
    }
    @Override
    public List<BaseModel> getAll(long id) throws SQLException {

        String getAllStmnt = "select * from " + tableName + ";";

        reopenConnection();
        ResultSet rs = null;
        Statement statement = null;
        List<BaseModel> users = new ArrayList<BaseModel>();
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(getAllStmnt);
            if (rs.wasNull()) {
                logger.log(logger.WARNING, "Result set was null!");
            }
            logger.log(logger.INFO, "Got result!");
            while ( rs.next() ) {
                int usrId = 1;//rs.getInt("id");
                Object obj = rs.getObject(2);
                logger.log(logger.WARNING, "Object class: " + obj.getClass().getName());
                logger.log(logger.INFO, "Got id! " + usrId);
                String name = rs.getString("name");
                logger.log(logger.INFO, "Got name!" + name);
                String secondName = rs.getString("secondname");
                logger.log(logger.INFO, "Got secondName!" + secondName);
                String email = rs.getString("email");
                logger.log(logger.INFO, "Got email!" + email);
                boolean isEntity  = rs.getBoolean("isentity");
                logger.log(logger.INFO, "Got entity!" + isEntity);
                users.add(new User(usrId, name, secondName, email, isEntity));
            }
        } catch (SQLException e) {
            logger.log(logger.WARNING, "Failed to get list of users!\n" + e.getMessage());
        }
        rs.close();
        statement.close();
        close();
        return users;
//        List<BaseModel> users = new ArrayList<BaseModel>();
//        users.add(new User(1, "Vadim", "Trishin", "myemail@email.com", false));
//        users.add(new User(2, "Maria", "Kenny", "notmyemail@email.ru", true));
//        return users;
    }

    @Override
    public void createTable() throws SQLException {

        // TODO may move it to BaseTable and set commands as strings in classes (will be read from BaseModel)
        //  use reflection for this?
        try {
            String CreateSql = "CREATE TABLE IF NOT EXISTS " +
                    tableName + "(" +
                    "id BIGINT PRIMARY KEY NOT NULL," +
                    "name varchar(255) NOT NULL," +
                    "secondName varchar(255) NOT NULL," +
                    "email varchar(255) NOT NULL," +
                    "isEntity boolean NOT NULL)";
            executeSqlStatement(CreateSql);
        } catch (SQLException e) {
            logger.log(logger.WARNING, "Failed to create table " + tableName + "!");
            logger.log(logger.WARNING, e.getMessage());
        }
    }
}
