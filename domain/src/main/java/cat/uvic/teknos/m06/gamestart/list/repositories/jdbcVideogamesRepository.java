package cat.uvic.teknos.m06.gamestart.list.repositories;

import cat.uvic.teknos.m06.gamestart.list.models.Members;
import cat.uvic.teknos.m06.gamestart.list.models.Products;
import cat.uvic.teknos.m06.gamestart.list.models.Videogames;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class jdbcVideogamesRepository implements Repository<Videogames, Integer>{
    private static final String INSERT = "insert into videogames (title) values (?)";
    private static final String UPDATE = "update videogames set title = ? where gameid = ?";
    private static final String SELECT_ALL = "select gameid, name from videogames";
    private static final String DELETE = "delete from videogames where gameid = ?";
    private final Connection connection;
    public jdbcVideogamesRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Videogames videogames) {
        if (videogames == null) {
            throw new RepositoryException("The products is null!");
        }
        if (videogames.getGameId() <= 0) {
            insert(videogames);
        } else {
            update(videogames);
        }
    }

    private void update(Videogames videogames) {
        try (var preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, videogames.getTitle());
            preparedStatement.setInt(1, videogames.getGameId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception while inserting: " + videogames, e);
        }

    }

    private void insert(Videogames videogames) {
        try (var preparedStatement = connection.prepareStatement(INSERT)) {
            preparedStatement.setString(1, videogames.getTitle());
            preparedStatement.executeUpdate();
            var generatedKeysResultSet = preparedStatement.getGeneratedKeys();
            if (!generatedKeysResultSet.next()) {
                throw new RepositoryException("Exception while inserting: id not generated" + videogames);
            }
            videogames.setGameId(generatedKeysResultSet.getInt(1));
        } catch (SQLException e) {
            throw new RepositoryException("Exception while inserting: " + videogames, e);
        }
    }

    public void delete(Videogames videogames) {
        try(var preparedStatement = connection.prepareStatement(DELETE)){
            preparedStatement.setInt(1,videogames.getConsoleId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception while trying to delete", e);
        }
    }
    @Override
    public Videogames getById(Integer id) {
        return null;
    }
    @Override
    public List<Videogames> getAll() {
        var videogames = new ArrayList<Videogames>();
        try (var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                var videogame = new Videogames();
                videogame.setGameId(resultSet.getInt("gameid"));
                videogame.setTitle(resultSet.getString("title"));
                videogames.add(videogame);
            }

            return videogames;
        } catch (SQLException e) {
            throw new RepositoryException("Exception while executing get all");
        }
    }
}