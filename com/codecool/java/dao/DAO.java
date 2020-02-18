package codecool.java.dao;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
    public void save(T t) throws SQLException;
    public List<T> loadAll() throws SQLException;
    public void update(T t) throws SQLException;
    public void disable(T t) throws SQLException;
    public void activate(T t) throws SQLException;
}
