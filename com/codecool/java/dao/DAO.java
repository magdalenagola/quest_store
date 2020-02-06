package codecool.java.dao;

import java.util.List;

public interface DAO<T> {
    void save(T t);
    List<T> loadAll();
    void update(T t);
    void disable(T t);
    void activate(T t);
}
