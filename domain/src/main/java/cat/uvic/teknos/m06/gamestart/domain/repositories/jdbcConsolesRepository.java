package cat.uvic.teknos.m06.gamestart.domain.repositories;
import cat.uvic.teknos.m06.gamestart.domain.models.Consoles;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class jdbcConsolesRepository implements Repository<Consoles, Integer> {
    private static final String INSERT = "insert into consoles (name) values (?)";
    private static final String UPDATE = "update consoles set name = ? where consoleid = (?)";
    private static final String SELECT_ALL = "select * from consoles";
    private static final String DELETE = "delete from consoles where consoleid = ?";
    private final Connection connection;

    public jdbcConsolesRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Consoles consoles) {
        if (consoles == null) {
            throw new RepositoryException("The products is null!");
        }
        if (consoles.getConsoleId() <= 0) {
            insert(consoles);
        } else {
            update(consoles);
        }
    }

    private void update(Consoles consoles) {
        try (var preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, consoles.getName());
            preparedStatement.setInt(2, consoles.getConsoleId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception while inserting: " + consoles, e);
        }

    }

    private void insert(Consoles consoles){
        try(var prepared = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)){
            prepared.setString(1, consoles.getName());
            prepared.executeUpdate();
            var generatedKeysResultSet = prepared.getGeneratedKeys();
            if (!generatedKeysResultSet.next()) {
                throw new RepositoryException("Exception while inserting: id not generated" + consoles);
            }
            consoles.setConsoleId(generatedKeysResultSet.getInt(1));
        } catch (SQLException e) {
            throw new RepositoryException("Exception while inserting: " + consoles, e);
        }
    }

    public void delete(Consoles consoles) {
        try (var preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, consoles.getConsoleId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception while trying to delete", e);
        }
    }

    @Override
    public Consoles getById(Integer id) {
        Consoles console = null;

        try (var prepareStatement = connection.prepareStatement(SELECT_ALL + " where consoleid = ?")) {
            prepareStatement.setInt(1, id);

            var resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                console = new Consoles();

                console.setConsoleId(resultSet.getInt("ConsoleId"));
                console.setName(resultSet.getString("name"));
            }

            return console;
        } catch (SQLException ex) {
            throw new RepositoryException("Exception while excecuting get by id");
        }

    }

    @Override
    public List<Consoles> getAll() {
        var consoles = new ArrayList<Consoles>();
        try (var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                var console = new Consoles();
                console.setConsoleId(resultSet.getInt("consoleid"));
                console.setName(resultSet.getString("name"));
                consoles.add(console);
            }

            return consoles;
        } catch (SQLException e) {
            throw new RepositoryException("Exception while executing get all");
        }
    }
}