package com.vtrishin.webservice.repositories;

import com.vtrishin.webservice.models.Advert;
import com.vtrishin.webservice.models.BaseModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseAdvert extends BaseTable {

    public DatabaseAdvert() throws SQLException {

        super(ADVERT_TABLE);
    }
    @Override
    public boolean add(BaseModel model) throws SQLException {
//
//        String message;
//        if (model == null || model.getClass() == Advert.class) {
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
//            message = "Failed to add " + model.getClass().toString().toLowerCase() + " to database.\n" + e.getMessage();
//            logger.warning(message);
//            return false;
//        }
//        logger.info(message);
        return true;
    }
    @Override
    public boolean remove(long id) throws SQLException {
//
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
//        Advert advert;
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
//        return advert;
//        return null;
        return new Advert(0, 1, "Refrigirator", "Kitchen", "88005553535");
    }
    @Override
    public List<BaseModel> getAll(long id) throws SQLException {

//        String message;
//        if (id > BaseModel.getMaxId()) {
//            message = "No person with id = " + id + " in database.";
//            logger.warning(message);
//            return null;
//        }
//
//        List<Advert> adverts;
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
//        return adverts;
        List<BaseModel> adverts = new ArrayList<BaseModel>();
        adverts.add(new Advert(0, 1, "Refrigirator", "Kitchen", "88005553535"));
        adverts.add(new Advert(1, 1, "Shower", "Bathroom", "88"));

        return adverts;
    }
    @Override
    public void createTable() throws SQLException {

        // FIXME may move it to BaseTable and set commands as strings in classes (will be read from BaseModel)
        //  use reflection for this?
        try {
            String createSql = "CREATE TABLE IF NOT EXISTS " +
                    tableName + "(" +
                    "id BIGINT PRIMARY KEY NOT NULL," +
                    "personId BIGINT NOT NULL," +
                    "header varchar(255) NOT NULL," +
                    "category varchar(255) NOT NULL," +
                    "phoneNumber varchar(255) NOT NULL," +
                    "creationDate timestamp NOT NULL)";
            executeSqlStatement(createSql);

            executeSqlStatement(" ALTER TABLE " + tableName +
                    " ADD FOREIGN KEY (personId) REFERENCES " + USER_TABLE + " (id)");
        } catch (SQLException e) {
            logger.log(logger.WARNING, "Failed to create table " + tableName + "!");
            logger.log(logger.WARNING, e.getMessage());
        }
    }

}
