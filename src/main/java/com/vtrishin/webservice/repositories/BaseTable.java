package com.vtrishin.webservice.repositories;

import com.vtrishin.webservice.Logger;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class BaseTable  extends Logger implements Closeable {

    @Override
    public void close() {

        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            logger.warning("Ошибка закрытия SQL соединения!");
        }
    }

    protected String tableName;
    private Connection connection;

    // TODO how autoincrement works? should I set it myself?
    protected BaseTable(String tableName) throws SQLException {

        this.tableName = tableName;
        this.connection = getConnection();
    }
    // TODO need to close connection?
    protected void executeSqlStatement(String sql, String description) throws SQLException {

        reopenConnection();
        Statement statement = connection.createStatement();
        statement.execute(sql);
        statement.close();
        if (description != null) {
            logger.log(INFO, description);
        }
    }

    private void reopenConnection() throws SQLException {

        if (connection == null || connection.isClosed()) {
            connection = getConnection();
        }
    }
    private Connection getConnection() throws SQLException {

//        Properties properties = new Properties();
//        try (InputStream inputStream = BaseTable.class.getClassLoader().getResourceAsStream("config.properties")) {
//            properties.load(inputStream);
//        } catch (IOException e) {
//            logger.warning("Properties file not found {}. " + e);
//        }
//
//        final String URL = properties.getProperty("db.url");
//        final String USER = properties.getProperty("db.user");
//        final String PASSWORD = properties.getProperty("db.password");
//        final String DRIVER_CLASS_NAME = properties.getProperty("db.driver");
//
//        try {
//            Class.forName(DRIVER_CLASS_NAME);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return DriverManager.getConnection(URL, USER, PASSWORD);

        String passwordDatabase = "0852";
        String usernameDatabase = "postgres";
        String pathToDaatabase = "jdbc:postgresql://localhost:5433/EpamCoursework";
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return DriverManager.getConnection(pathToDaatabase,
                usernameDatabase, passwordDatabase);
    }
    protected abstract void createTable() throws SQLException;
    protected abstract void createForeignKeys() throws SQLException;
}
