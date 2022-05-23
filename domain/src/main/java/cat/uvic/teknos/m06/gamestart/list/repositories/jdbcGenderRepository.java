package cat.uvic.teknos.m06.gamestart.list.repositories;

import cat.uvic.teknos.m06.gamestart.list.models.Consoles;
import cat.uvic.teknos.m06.gamestart.list.models.Gender;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class jdbcGenderRepository implements Repository<Gender, Integer>{
    private static final String INSERT = "insert into gender (description) values (?)";
    private static final String UPDATE = "update gender set description = ? where genderid = ?";
    private static final String SELECT_ALL = "select genderid, description from gender";
    private static final String DELETE = "delete from gender where genderid = ?";
    private final Connection connection;
    public jdbcGenderRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Gender gender) {
        if (gender == null) {
            throw new RepositoryException("The products is null!");
        }
        if (gender.getGenderId()<= 0) {
            insert(gender);
        } else {
            update(gender);
        }
    }

    private void update(Gender gender) {
        try (var preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, gender.getDescription());
            preparedStatement.setInt(1, gender.getGenderId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception while inserting: " + gender, e);
        }

    }

    private void insert(Gender gender) {
        try (var preparedStatement = connection.prepareStatement(INSERT)) {
            preparedStatement.setString(1, gender.getDescription());
            preparedStatement.executeUpdate();
            var generatedKeysResultSet = preparedStatement.getGeneratedKeys();
            if (!generatedKeysResultSet.next()) {
                throw new RepositoryException("Exception while inserting: id not generated" + gender);
            }
            gender.setGenderId(generatedKeysResultSet.getInt(1));
        } catch (SQLException e) {
            throw new RepositoryException("Exception while inserting: " + gender, e);
        }
    }

    public void delete(Gender gender) {
        try(var preparedStatement = connection.prepareStatement(DELETE)){
            preparedStatement.setInt(1,gender.getGenderId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception while trying to delete", e);
        }
    }

    @Override
    public Gender getById(Integer id) {
        Gender gender = null;

        try (var prepareStatement = connection.prepareStatement(SELECT_ALL + "where genderid = ?")) {
            prepareStatement.setInt(1, id);

            var resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                gender = new Gender();

                gender.setGenderId(resultSet.getInt("genderid"));
                gender.setDescription(resultSet.getString("description"));
            }

            return gender;
        } catch (SQLException ex) {
            throw new RepositoryException("Exception while excecuting get all");
        }

    }

    @Override
    public List<Gender> getAll() {
        var genders = new ArrayList<Gender>();
        try (var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                var gender = new Gender();
                gender.setGenderId(resultSet.getInt("genderid"));
                gender.setDescription(resultSet.getString("description"));
                genders.add(gender);
            }

            return genders;
        } catch (SQLException e) {
            throw new RepositoryException("Exception while executing get all");
        }
    }
}