package com.vtrishin.webservice.repositories;

import com.vtrishin.webservice.models.Advert;
import com.vtrishin.webservice.models.BaseModel;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseAdvert extends BaseTable {

    public DatabaseAdvert() throws SQLException {

        super(ADVERT_TABLE);
    }
    @Override
    public void add(BaseModel model) throws SQLException {

        reopenConnection();
        if (!(model instanceof Advert)) {
            logger.log(logger.WARNING, "Wrong class or null given as parameter.");
            throw new SQLException("Wrong instance of object.");
        }
        Advert advert = (Advert) model;

        PreparedStatement pstmt = connection.prepareStatement(WRITE_OBJECT_SQL);
        pstmt.setInt(1, advert.getId());
        pstmt.setInt(2, advert.getPersonId());
        pstmt.setString(3, advert.getHeader());
        pstmt.setString(4, advert.getCategory());
        pstmt.setString(5, advert.getPhoneNumber());
        pstmt.setTimestamp(6, Timestamp.valueOf(advert.getCreationDate()));
        pstmt.executeUpdate();

        // FIXME uncomment when manage how to auto_increment
//        ResultSet rs = pstmt.getGeneratedKeys();
//        int id = -1;
//        if (rs.next()) {
//            id = rs.getInt(1);
//        }
//        rs.close();
        pstmt.close();
        super.close();
    }
    @Override
    public void remove(long id) throws SQLException {
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
    }
    @Override
    public BaseModel find(int id, int userId) throws SQLException {

//        String message;
//        if (id > BaseModel.getMaxId()) {
//            message = "No element with id = " + id + " in database.";
//            logger.warning(message);
//            return null;
//        }
//
//        Advert advert;
//        try {
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
        return new Advert(0, 1, "Refrigirator", "Kitchen",
                "88005553535", LocalDateTime.now());
    }
    @Override
    public List<BaseModel> getAll(long usrId) throws SQLException {

        reopenConnection();
        Statement statement = null;
        ResultSet rs = null;
        List<BaseModel> adverts = new ArrayList<>();
        String getAllStmnt = "select * from " + tableName + " where personId = " + usrId + ";";

        statement = connection.createStatement();
        rs = statement.executeQuery(getAllStmnt);
        if (rs.wasNull()) {
            throw new SQLException("Failed to get " + tableName + " from database.");
        }

        while (rs.next()) {
            // FIXME may be remake with reflection in loop
//            int id = rs.getInt("id");
            int id = 1;
            int personId = rs.getInt("personId");
            String header = rs.getString("header");
            String category = rs.getString("category");
            String phoneNumber = rs.getString("phoneNumber");
            Timestamp creationDate = rs.getTimestamp("creationDate");
            adverts.add(new Advert(id, personId, header, category, phoneNumber, creationDate.toLocalDateTime()));
            logger.log(logger.INFO, creationDate.toString());
        }
        rs.close();
        statement.close();
        close();
        return adverts;
    }
    @Override
    public void createTable() throws SQLException {

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
    }

    // FIXME make static
    private String WRITE_OBJECT_SQL = "INSERT INTO " + tableName +
            "(id, " +
            "personId, " +
            "header, " +
            "category, " +
            "phoneNumber, " +
            "creationDate" +
            ") VALUES (?, ?, ?, ?, ?, ?)";
}
