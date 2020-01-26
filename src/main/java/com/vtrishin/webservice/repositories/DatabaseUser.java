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
     *
     * @throws SQLException
     */
    public DatabaseUser() throws SQLException {

        super(USER_TABLE);
    }
    @Override
    public void add(BaseModel model) throws SQLException {

        reopenConnection();
        if (!(model instanceof User)) {
            logger.log(logger.WARNING, "Wrong class or null given as parameter.");
            throw new SQLException("Wrong instance of object.");
        }
        User usr = (User) model;

        PreparedStatement pstmt = connection.prepareStatement(WRITE_OBJECT_SQL);
        pstmt.setInt(1, usr.getId());
        pstmt.setString(2, usr.getName());
        pstmt.setString(3, usr.getSecondName());
        pstmt.setString(4, usr.getUserEmail());
        pstmt.setBoolean(5, usr.isEntity());
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

        reopenConnection();
        Statement stmt = connection.createStatement();
        String sql = "DELETE from " + tableName + " where ID = " + id + ";";
        stmt.executeUpdate(sql);
        connection.commit();

        stmt.close();
        super.close();
    }
    @Override
    public BaseModel find(int id, int userId) throws SQLException {

        reopenConnection();
        // FIXME why it doesnt take USERS as parameter?
        String sql = "SELECT * FROM USERS WHERE ID = ?;";
        PreparedStatement pstmt = connection.prepareStatement(sql);
//        pstmt.setString(1, tableName);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();

        rs.next();
        logger.log(logger.INFO, String.valueOf(rs.getFetchSize()));
        logger.log(logger.INFO, rs.getObject(1).getClass().toString());

        User user;
        int usrId = rs.getInt("id");
        String name = rs.getString("name");
        String secondName = rs.getString("secondname");
        String email = rs.getString("email");
        boolean isEntity = rs.getBoolean("isentity");
        User usr = new User(usrId, name, secondName, email, isEntity);
        logger.log(logger.INFO, usr.toString());

        pstmt.close();
        super.close();
        return usr;
    }
    @Override
    public List<BaseModel> getAll(int id) throws SQLException {

        reopenConnection();
        Statement statement = null;
        ResultSet rs = null;
        List<BaseModel> users = new ArrayList<BaseModel>();
        String getAllStmnt = "select * from " + tableName + ";";

        statement = connection.createStatement();
        rs = statement.executeQuery(getAllStmnt);
        if (rs.wasNull()) {
            throw new SQLException("Failed to get " + tableName + " from database.");
        }

        while (rs.next()) {
            // FIXME may be remake with reflection in loop
            int usrId = rs.getInt("id");
            String name = rs.getString("name");
            String secondName = rs.getString("secondname");
            String email = rs.getString("email");
            boolean isEntity = rs.getBoolean("isentity");
            users.add(new User(usrId, name, secondName, email, isEntity));
        }
        rs.close();
        statement.close();
        close();
        return users;
    }
    @Override
    public void createTable() throws SQLException {

        String CreateSql = "CREATE TABLE IF NOT EXISTS " +
                tableName + "(" +
                "id BIGINT PRIMARY KEY NOT NULL," +
                "name varchar(255) NOT NULL," +
                "secondName varchar(255) NOT NULL," +
                "email varchar(255) NOT NULL," +
                "isEntity boolean NOT NULL)";
        executeSqlStatement(CreateSql);
    }

    // FIXME make static
    private String WRITE_OBJECT_SQL = "INSERT INTO " + tableName +
            "(id, " +
            "name, " +
            "secondName, " +
            "email, " +
            "isEntity" +
            ") VALUES (?, ?, ?, ?, ?)";

}
