package Ali_Nauta.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by jan on 3.3.2016.
 */


public interface Dao<T, K> {

    T findOne(K key) throws SQLException;

    List<T> findAll() throws SQLException;

    List<T> findAllin(Integer key) throws SQLException;

    void delete(K key) throws SQLException;

}
