package com.vtrishin.webservice.repositories;

import com.vtrishin.webservice.service.ServletLogger;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class BaseTable implements Closeable, TableOperations {

    /**
     * Closes connection.
     */
    @Override
    public void close() {

        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            logger.log(logger.WARNING, "Ошибка закрытия SQL соединения!");
        }
    }

    // FIXME Maybe must set Serial/Sequent for autoincrement
    /**
     * Sets table name, inits connection and creates table with tableName if such not exists.
     * @param tableName - name of the table
     * @throws SQLException
     */
    protected BaseTable(String tableName) throws SQLException {

        this.tableName = tableName;
        this.connection = getConnection();
        createTable();
    }
    /**
     * Executes manually filled sql statement
     * @param sql - string with manually filled sql statement
     * @throws SQLException
     */
    protected void executeSqlStatement(String sql) throws SQLException {

        reopenConnection();
        Statement statement = connection.createStatement();
        statement.execute(sql);
        statement.close();
        close();
    }
    /**
     * Reopens connection.
     * @throws SQLException
     */
    protected void reopenConnection() throws SQLException {

        if (connection == null || connection.isClosed()) {
            connection = getConnection();
        }
    }
    /**
     * Reads properties from 'config.properties'.
     * Gets driver and inits connection.
     * @return - connection to database if success.
     * @throws SQLException
     */
    protected Connection getConnection() throws SQLException {

        // TODO reading properties and getting class must be in own methods,
        //  that not connected each time by recopenConnection
        Properties properties = new Properties();
        // FIXME here must be return
        try (InputStream inputStream = BaseTable.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.log(logger.WARNING, "Properties file not found {}. " + e);
        }

        final String URL = properties.getProperty("db.url");
        final String USER = properties.getProperty("db.user");
        final String PASSWORD = properties.getProperty("db.password");
        final String DRIVER_CLASS_NAME = properties.getProperty("db.driver");

        // FIXME here must be return
        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            logger.log(logger.WARNING, "Failed to load driver for database.");
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // TODO may move here createTable and just fill command here?
    //  use reflection for generalizing this?

    protected String tableName;
    protected Connection connection;
    protected ServletLogger logger = ServletLogger.getInstance();
}
