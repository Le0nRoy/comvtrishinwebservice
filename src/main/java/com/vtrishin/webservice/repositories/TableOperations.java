package com.vtrishin.webservice.repositories;

import com.vtrishin.webservice.models.BaseModel;

import java.sql.SQLException;
import java.util.List;

// FIXME make generics to all methods to use reflection and generalize code
public interface TableOperations {

    String ADVERT_TABLE = "adverts";
    String USER_TABLE = "users";

    /**
     * Create table for appropriate model.
     * @throws SQLException
     */
    void createTable() throws SQLException;
    /**
     * Add one element to database
     * @param model - object of appropriate model to serialize
     * @throws SQLException
     */
    void add(BaseModel model) throws SQLException;
    /**
     * Removes element from database.
     * @param id - id of element to remove
     * @throws SQLException
     */
    void remove(long id) throws SQLException;
    /**
     *
     * @param id - id of user or advert that is looked for
     * @param userId - used only for DatabaseAdvert to set user, whose advert to look for
     * @return - null if nothing with such ids exists, Advert or User if requested object is found
     * @throws SQLException - if failed request to database
     */
    BaseModel find(int id, int userId) throws SQLException;
    /**
     * Get all values from table (referenced by userId if adverts)
     * @param id - userId for advert or negative value for user
     * @return - List with all
     * @throws SQLException
     */
    List<BaseModel> getAll(int id) throws SQLException;
}
