package medipass.dao.interfaces;

import java.util.List;

public interface GenericDAO<T, ID> {
    void load();
    void save();
    List<T> findAll();
    T findById(ID id);
    void add(T t);
    void update(T t);
    boolean delete(ID id);
    int generateId();
}
 