package cat.uvic.teknos.m06.gamestart.domain;
import cat.uvic.teknos.m06.gamestart.domain.repositories.JpaConsolesRepository;
import org.junit.jupiter.api.Test;
import cat.uvic.teknos.m06.gamestart.domain.models.Consoles;
import org.junit.jupiter.api.BeforeAll;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import static org.junit.jupiter.api.Assertions.*;

public class JPAConsolesTest {
    private static EntityManagerFactory entityManagerFactory;

    @BeforeAll
    static void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("gamestart");
    }

    @Test
    void saveInsert() {
        var ConsolesRepository = new JpaConsolesRepository(entityManagerFactory);
        var console = new Consoles();
        console.setName("PS5");
        assertDoesNotThrow(() -> {
            ConsolesRepository.save(console);
        });
        assertTrue((console.getConsoleId() != 0));
    }

    @Test
    void saveUpdate() {
        var ConsolesRepository = new JpaConsolesRepository(entityManagerFactory);
        var console = new Consoles();
        console.setName("Xbox Series X");
        assertDoesNotThrow(() -> {
            ConsolesRepository.save(console);
        });
        var entityManager = entityManagerFactory.createEntityManager();
        var modifiCountry = entityManager.find(Consoles.class, 7);
        assertEquals("PS5", modifiCountry.getName());
        entityManager.close();
    }

    @Test
    void delete() { /*
        var ConsolesRepository = new JpaConsolesRepository(entityManagerFactory);
        var entityManager = entityManagerFactory.createEntityManager();
        var entityManager1 = entityManagerFactory.createEntityManager();
        var con = entityManager.find(Consoles.class, 7);
        assertNotNull(con);
        assertDoesNotThrow(() -> {
            ConsolesRepository.delete(7);
        });
        con = entityManager1.find(Consoles.class, 7);
        assertNull(con);*/
    }

    @Test
    void getById() {
        var ConsolesRepository = new JpaConsolesRepository(entityManagerFactory);
        var console = ConsolesRepository.getById(1);
        assertNotNull(console);
    }

    @Test
    void getAll() {
        var ConsolesRepository = new JpaConsolesRepository(entityManagerFactory);
        var console = ConsolesRepository.getAll();
        assertNotNull(console);
        assertTrue(console.size() > 0);
    }
}
