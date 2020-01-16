package com.vtrishin.webservice.repositories;

import com.vtrishin.webservice.models.BaseModel;
import com.vtrishin.webservice.models.Advert;

import java.sql.SQLException;
import java.util.List;

public final class DatabaseAdvert extends BaseTable implements TableOperations {

    public DatabaseAdvert() throws SQLException {

        super(ADVERT_TABLE);
    }
    @Override
    public boolean add(BaseModel model) throws SQLException {

        String message;
        if (model == null || model.getClass() == Advert.class) {
            message = "Wrong class or null given as parameter.";
            logger.warning(message);
            return false;
        }

        try {
            // TODO make check by id before adding
            executeSqlStatement();
            message = "";
        } catch (SQLException e) {
            message = "Failed to add " + model.getClass().toString().toLowerCase() + " to database.\n" + e.getMessage();
            logger.warning(message);
            return false;
        }
        logger.info(message);
        return true;
    }
    @Override
    public boolean remove(long id) throws SQLException {

        String message;
        if (id > BaseModel.getMaxId()) {
            message = "No element with id = " + id + " in database.";
            logger.warning(message);
            return false;
        }

        try {
            executeSqlStatement();
            message = "";
        } catch (SQLException e) {
            message = "Failed to remove element with id " + id + " from database.\n" + e.getMessage();
            logger.warning(message);
            return false;
        }
        logger.info(message);
        return true;
    }
    @Override
    public BaseModel find(long id) throws SQLException {

        String message;
        if (id > BaseModel.getMaxId()) {
            message = "No element with id = " + id + " in database.";
            logger.warning(message);
            return null;
        }

        Advert advert;
        try {
            // TODO make check by id before adding
            executeSqlStatement();
            message = "";
        } catch (SQLException e) {
            message = "Failed to find element with id " + id + " in database.\n" + e.getMessage();
            logger.warning(message);
            return null;
        }
        logger.info(message);
        return advert;
    }
    @Override
    public List<BaseModel> getAll(long id) throws SQLException {

        String message;
        if (id > BaseModel.getMaxId()) {
            message = "No person with id = " + id + " in database.";
            logger.warning(message);
            return null;
        }

        List<Advert> adverts;
        try {
            // TODO make check by id before adding
            executeSqlStatement();
            message = "";
        } catch (SQLException e) {
            message = "Failed to find element with id " + id + " in database.\n" + e.getMessage();
            logger.warning(message);
            return null;
        }
        logger.info(message);
        return adverts;
    }
    @Override
    protected void createTable() throws SQLException {

        try {
            super.executeSqlStatement("CREATE TABLE IF NOT EXISTS " +
                            tableName + "(" +
                            "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                            "personId BIGINT " + //TODO add foreign key
                            "header VARCHAR(255) NOT NULL," +
                            "category VARCHAR(255) NOT NULL," +
                            "phoneNumber VARCHAR(255) NOT NULL," +
                            "creationTime TIMESTAMP NOT NULL)",
                    "Создана таблица " + tableName);
        } catch (SQLException e) {
            logger.warning("Failed to create table " + tableName + "!");
        }
    }
    @Override
    protected void createForeignKeys() throws SQLException {

        // FIXME add foreign key on person
        super.executeSqlStatement(" ALTER TABLE" + tableName +
                        " ADD FOREIGN KEY (advert) REFERENCES " + ADVERT_TABLE + "(id)",
                "Cоздан внешний ключ " + tableName + ".advert -> " + ADVERT_TABLE + ".id");
    }

}
