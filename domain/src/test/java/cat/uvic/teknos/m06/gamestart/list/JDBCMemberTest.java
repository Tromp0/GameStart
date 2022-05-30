package cat.uvic.teknos.m06.gamestart.list;
import cat.uvic.teknos.m06.gamestart.list.models.Consoles;
import cat.uvic.teknos.m06.gamestart.list.repositories.jdbcConsolesRepository;
import com.mysql.cj.jdbc.JdbcConnection;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

class JDBCMemberTest {

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
        console.setConsoleId(2);
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


