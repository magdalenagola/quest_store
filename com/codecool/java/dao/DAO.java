package codecool.java.dao;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
    public void save(T t);
    public List<T> loadAll();
    public void update(T t);
    public void disable(T t);
    public void activate(T t);
}
