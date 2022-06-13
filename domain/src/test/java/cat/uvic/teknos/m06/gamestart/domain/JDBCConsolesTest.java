package cat.uvic.teknos.m06.gamestart.domain;
import cat.uvic.teknos.m06.gamestart.domain.models.Consoles;
import cat.uvic.teknos.m06.gamestart.domain.repositories.jdbcConsolesRepository;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

class JDBCConsolesTest {

    @Test
    void save() throws SQLException {
        var console = new Consoles();
        console.setName("PS5");
        var connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gamestart", "root", null);
        var jdbcConsolesRepository = new jdbcConsolesRepository(connection);
        jdbcConsolesRepository.save(console);
    }

    @Test
    void saveUp() throws SQLException {
        var connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gamestart", "root", null);
        var jdbcConsolesRepository = new jdbcConsolesRepository(connection);
        Consoles console = new Consoles();
        console.setName("Xbox Series X");
        console.setConsoleId(1);
        jdbcConsolesRepository.save(console);

    }
    @Test
    void Delete()throws SQLException {
        var connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gamestart", "root", null);
        var jdbcConsolesRepository = new jdbcConsolesRepository(connection);
        Consoles console = new Consoles();
        console.setConsoleId(1);
        jdbcConsolesRepository.delete(console);

    }
    @Test
    void getbyid()throws SQLException {
        var connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gamestart", "root", null);
        var jdbcConsolesRepository = new jdbcConsolesRepository(connection);
        Consoles console = jdbcConsolesRepository.getById(1);

    }

    @Test
    void getAll()throws SQLException {
        var connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gamestart", "root", null);
        var jdbcConsolesRepository = new jdbcConsolesRepository(connection);
        List<Consoles> console = jdbcConsolesRepository.getAll();

    }
}


