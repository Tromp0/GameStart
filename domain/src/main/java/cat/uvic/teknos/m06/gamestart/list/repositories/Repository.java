package cat.uvic.teknos.m06.gamestart.list.repositories;

import cat.uvic.teknos.m06.gamestart.list.models.Members;
import cat.uvic.teknos.m06.gamestart.list.models.Products;

import java.util.List;

public interface Repository<T, K> {
    void save(T model );
    void delete(T model);
    T getById(K id);

    List<T> getAll();
}
