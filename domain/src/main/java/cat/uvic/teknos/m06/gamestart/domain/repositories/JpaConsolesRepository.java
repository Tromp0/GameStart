package cat.uvic.teknos.m06.gamestart.domain.repositories;

import cat.uvic.teknos.m06.gamestart.domain.models.Consoles;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class JpaConsolesRepository implements Repository<Consoles,Integer>{
    private final EntityManagerFactory entityManagerFactory;

    public JpaConsolesRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void save(Consoles consoles) {
        if (consoles.getConsoleId()<= 0) {
            insert(consoles);
        } else {
            update(consoles);
        }
    }

    private void update(Consoles consoles) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(consoles);
        entityManager.getTransaction().commit();
    }

    private void insert(Consoles consoles) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(consoles);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(Consoles console) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        var consoles = entityManager.find(Consoles.class, console.getConsoleId());
        if (consoles != null) {
            entityManager.remove(consoles);
        }
        entityManager.getTransaction().commit();
    }

    @Override
    public Consoles getById(Integer id) {
        var entityManager = entityManagerFactory.createEntityManager();
        var consoles = entityManager.find(Consoles.class, id);
        entityManager.close();

        return consoles;
    }

    @Override
    public List<Consoles> getAll() {
        var entityManager = entityManagerFactory.createEntityManager();
        var query = entityManager.createQuery("SELECT consoles FROM Consoles consoles");
        return query.getResultList();
    }

}


