package cat.uvic.teknos.m06.gamestart.domain.repositories;
import java.util.List;

public interface Repository<T, K> {
    void save(T model );
    void delete(T model);
    T getById(K id);
    List<T> getAll();
}
