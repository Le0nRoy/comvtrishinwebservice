package com.vtrishin.webservice.repositories;

import com.vtrishin.webservice.models.Advert;
import com.vtrishin.webservice.models.BaseModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
        pstmt.setInt(2, advert.getUserId());
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

        reopenConnection();
        // FIXME why it doesnt take ADVERTS as parameter?
        String sql = "SELECT * FROM ADVERTS WHERE ID = ?;";
        PreparedStatement pstmt = connection.prepareStatement(sql);
//        pstmt.setString(1, tableName);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();

        rs.next();
        logger.log(logger.INFO, String.valueOf(rs.getFetchSize()));
        logger.log(logger.INFO, rs.getObject(1).getClass().toString());

        int usrId = rs.getInt("personId");
        String header = rs.getString("header");
        String category = rs.getString("category");
        String phoneNumber = rs.getString("phoneNumber");
        LocalDateTime creationDate = rs.getTimestamp("creationDate").toLocalDateTime();
        Advert advert = new Advert(id, userId, header, category, phoneNumber, creationDate);
        logger.log(logger.INFO, advert.toString());

        pstmt.close();
        super.close();
        return advert;
    }
    @Override
    public List<BaseModel> getAll(int usrId) throws SQLException {

        reopenConnection();
        List<BaseModel> adverts = new ArrayList<>();
        String getAllStmnt = "select * from ADVERTS where personId = ?;";
        PreparedStatement statement = connection.prepareStatement(getAllStmnt);
        statement.setInt(1, usrId);
        ResultSet rs = statement.executeQuery();

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
            "personId, " + // FIXME rewrite as userId
            "header, " +
            "category, " +
            "phoneNumber, " +
            "creationDate" +
            ") VALUES (?, ?, ?, ?, ?, ?)";
}
