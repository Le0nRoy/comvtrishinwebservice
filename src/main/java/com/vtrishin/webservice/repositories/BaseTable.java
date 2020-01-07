package com.vtrishin.webservice.repositories;

import com.vtrishin.webservice.models.User;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseTable implements Closeable {

    public BaseTable(String tableName) throws SQLException {

        this.tableName = tableName;
        this.connection = getConnection();
    }
    public void close() {

        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            logger.warning("Ошибка закрытия SQL соединения!");
        }
    }
    public void executeSqlStatement(String sql, String description) throws SQLException {

        reopenConnection();
        Statement statement = connection.createStatement();
        statement.execute(sql);
        statement.close();
        if (description != null) {
            logger.log(Level.INFO, description);
        }
    }
    public void executeSqlStatement(String sql) throws SQLException {

        executeSqlStatement(sql, null);
    }

    protected Logger logger = Logger.getLogger(User.class.getName());
    protected String tableName;

    private Connection connection;
    private String pathToDaatabase = "jdbc:postgresql://localhost:5433/EpamCoursework";
    private String usernameDatabase = "postgres";
    private String passwordDatabase = "0852";

    private void reopenConnection() throws SQLException {

        if (connection == null || connection.isClosed()) {
            connection = getConnection();
        }
    }
    private Connection getConnection() throws SQLException {

        return DriverManager.getConnection(pathToDaatabase,
                usernameDatabase, passwordDatabase);
    }

}
