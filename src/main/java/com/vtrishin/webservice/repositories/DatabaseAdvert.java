package com.vtrishin.webservice.repositories;

import com.vtrishin.webservice.models.Advert;

import java.sql.SQLException;
import java.time.LocalDate;

public class DatabaseAdvert extends BaseTable implements TableOperations {

    public DatabaseAdvert() throws SQLException {

        super(ADVERT_TABLE);
    }

    @Override
    public void createTable() throws SQLException {

        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS " +
                tableName + "(" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "header VARCHAR(255) NOT NULL," +
                "category VARCHAR(255) NOT NULL," +
                "phoneNumber VARCHAR(255) NOT NULL," +
                "creationTime TIMESTAMP NOT NULL)",
                "Создана таблица " + tableName);
    }

    @Override
    public void createForeignKeys() throws SQLException {

    }

    public void addAdvertToDatabase(Advert advert) throws SQLException {

        addAdvertToDatabase(advert.getHeader(), advert.getCategory(), advert.getPhoneNumber(), advert.getCreationDate());
    }

    public void addAdvertToDatabase(String header, String category, String phoneNumber, LocalDate creationDate) throws SQLException {}

}
