package com.vtrishin.webservice.repositories;

import com.vtrishin.webservice.models.User;

import java.sql.SQLException;

public class DatabaseUser extends BaseTable implements TableOperations {

    public DatabaseUser() throws SQLException {

        super(USER_TABLE);
    }
    @Override
    public void createTable() throws SQLException {

        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS" +
                        tableName + "(" +
                        "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                        "name varchar(255) NOT NULL," +
                        "secondName varchar(255) NOT NULL," +
                        "email varchar(255) NOT NULL," +
                        "isEntity boolean NOT NULL)",
                "Создана таблица " + tableName);
    }
    @Override
    public void createForeignKeys() throws SQLException {

        super.executeSqlStatement(" ALTER TABLE" + tableName +
                        " ADD FOREIGN KEY (advert) REFERENCES " + ADVERT_TABLE + "(id)",
                "Cоздан внешний ключ " + tableName + ".advert -> " + ADVERT_TABLE + ".id");
    }

    public void addUserToDatabase(User user) throws SQLException {

        addUserToDatabase(user.getName(), user.getSecondName(), user.getUserEmail(), user.isEntity());
    }
    public void addUserToDatabase(String name, String secondName, String userEmail, boolean isEntity) throws SQLException {

    }

}
