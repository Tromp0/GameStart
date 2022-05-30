package cat.uvic.teknos.m06.gamestart.list.repositories;
import cat.uvic.teknos.m06.gamestart.list.models.Gender;
import cat.uvic.teknos.m06.gamestart.list.models.Members;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class jdbcMembersRepository implements Repository<Members, String>{
    private static final String INSERT = "insert into members (dni,name,mail,phonenumber) values (?,?,?,?)";
    private static final String UPDATE = "update members set name = ? , mail = ? , phonenumber = ? where DNI = ?";
    private static final String SELECT_ALL = "select * from members";
    private static final String DELETE = "delete from members where DNI = ?";
    private final Connection connection;
    public jdbcMembersRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Members members) {
        if (members == null) {
            throw new RepositoryException("The products is null!");
        }
        if (members.getDNI()== null) {
            insert(members);
        } else {
            update(members);
        }
    }

    public void update(Members members) {
        try (var preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, members.getName());
            preparedStatement.setString(1, members.getDNI());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception while inserting: " + members, e);
        }

    }

    private void insert(Members members) {
        try (var preparedStatement = connection.prepareStatement(INSERT)) {
            preparedStatement.setString(1, members.getDNI());
            preparedStatement.setString(2, members.getName());
            preparedStatement.setString(3, members.getMail());
            preparedStatement.setString(4, members.getPhoneNumber());
            preparedStatement.executeUpdate();
            var generatedKeysResultSet = preparedStatement.getGeneratedKeys();
        } catch (SQLException e) {
            throw new RepositoryException("Exception while inserting: " + members, e);
        }
    }

    public void delete(Members members) {
        try(var preparedStatement = connection.prepareStatement(DELETE)){
            preparedStatement.setString(1,members.getDNI());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception while trying to delete", e);
        }
    }

    @Override
    public Members getById(String dni){
        Members member = null;

        try (var prepareStatement = connection.prepareStatement(SELECT_ALL + "where dni = ?")) {
            prepareStatement.setString(1, dni);

            var resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                member = new Members();

                member.setDNI(resultSet.getString("dni"));
                member.setName(resultSet.getString("name"));
            }

            return member;
        } catch (SQLException ex) {
            throw new RepositoryException("Exception while excecuting get all");
        }

    }
    @Override
    public List<Members> getAll() {
        var members = new ArrayList<Members>();
        try (var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                var member = new Members();
                member.setDNI(resultSet.getString("DNI"));
                member.setName(resultSet.getString("name"));
                members.add(member);
            }

            return members;
        } catch (SQLException e) {
            throw new RepositoryException("Exception while executing get all");
        }
    }
}