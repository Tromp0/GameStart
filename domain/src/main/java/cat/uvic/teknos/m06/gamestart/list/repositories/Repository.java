package cat.uvic.teknos.m06.gamestart.list.repositories;

public interface Repository<TModel, Key> {
    void save(TModel model );
    void delete(TModel model);
    TModel getById(Key id);
    List<TModel> getAll();
}
