package com.vtrishin.webservice.repositories;

import com.vtrishin.webservice.models.BaseModel;

import java.sql.SQLException;
import java.util.List;

// Операции с таблицами
public interface TableOperations {

    String ADVERT_TABLE = "adverts";
    String USER_TABLE = "users";

    void createTable() throws SQLException;
    // TODO May move same code to BaseTable?
    boolean add(BaseModel model) throws SQLException;
    boolean remove(long id) throws SQLException;
    /**
     *
     * @param id - id of user or advert that is looked for
     * @param userId - used only for DatabaseAdvert to set user, whose advert to look for
     * @return - null if nothing with such ids exists, Advert or User if requested object is found
     * @throws SQLException - if failed request to database
     */
    BaseModel find(long id, long userId) throws SQLException;
    // TODO decide how to get all users
    List<BaseModel> getAll(long id) throws SQLException;
}
