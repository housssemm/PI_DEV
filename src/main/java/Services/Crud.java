package Services;

import java.util.List;

public interface Crud<T> {
    int create(T obj) throws Exception;

    void update(T obj) throws Exception;

    void delete(T obj) throws Exception;

    List<T> getAll() throws Exception;

}