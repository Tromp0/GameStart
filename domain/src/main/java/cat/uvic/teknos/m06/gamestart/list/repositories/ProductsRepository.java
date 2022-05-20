package cat.uvic.teknos.m06.gamestart.list.repositories;

import cat.uvic.teknos.m06.gamestart.list.models.Products;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductsRepository implements Repository<Products, Integer>{
    private static final String INSERT = "insert into products (name) values (?)";
    private static final String UPDATE = "update products set name = ? where id = ?";
    private static final String SELECT_ALL = "select id, name from products";
    private final Connection connection;
    public ProductsRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Products products) {
        if (products == null) {
            throw new RepositoryException("The products is null!");
        }
        if (products.getId() <= 0) {
            insert(products);
        } else {
            update(products);
        }
    }

    private void update(Products products) {
        try (var preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, products.getName());
            preparedStatement.setInt(1, products.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception while inserting: " + products, e);
        }

    }

    private void insert(Products products) {
        try (var preparedStatement = connection.prepareStatement(INSERT)) {
            preparedStatement.setString(1, products.getName());
            preparedStatement.executeUpdate();
            var generatedKeysResultSet = preparedStatement.getGeneratedKeys();
            if (!generatedKeysResultSet.next()) {
                throw new RepositoryException("Exception while inserting: id not generated" + products);
            }
            musicalGenre.setId(generatedKeysResultSet.getInt(1));
        } catch (SQLException e) {
            throw new RepositoryException("Exception while inserting: " + products, e);
        }
    }

    public void delete(Products products) {}

    @Override
    public Products getById(Integer id) {
        return null;
    }

    @Override
    public List<Products> getAll() {
        var products = new ArrayList<Products>();
        try (var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                var product = new Products();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.add(product);
            }

            return products;
        } catch (SQLException e) {
            throw new RepositoryException("Exception while executing get all");
        }
    }
}
}
