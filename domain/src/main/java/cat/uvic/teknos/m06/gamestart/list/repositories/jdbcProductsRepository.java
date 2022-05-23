package cat.uvic.teknos.m06.gamestart.list.repositories;

import cat.uvic.teknos.m06.gamestart.list.models.PriceList;
import cat.uvic.teknos.m06.gamestart.list.models.Products;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class jdbcProductsRepository implements Repository<Products, Integer>{
    private static final String INSERT = "insert into products (name,) values (?)";
    private static final String UPDATE = "update products set name = ? where id = ?";
    private static final String SELECT_ALL = "select id, name from products";
    private static final String DELETE = "delete from products where productid = ?";
    private final Connection connection;
    public jdbcProductsRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Products products) {
        if (products == null) {
            throw new RepositoryException("The products is null!");
        }
        if (products.getProductId() <= 0) {
            insert(products);
        } else {
            update(products);
        }
    }

    private void update(Products products) {
        try (var preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, products.getName());
            preparedStatement.setInt(1, products.getProductId());
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
            products.setProductId(generatedKeysResultSet.getInt(1));
        } catch (SQLException e) {
            throw new RepositoryException("Exception while inserting: " + products, e);
        }
    }

    public void delete(Products products) {
        try(var preparedStatement = connection.prepareStatement(DELETE)){
            preparedStatement.setInt(1,products.getProductId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception while trying to delete", e);
        }
    }
    @Override
    public Products getById(Integer id){
        Products products = null;

        try (var prepareStatement = connection.prepareStatement(SELECT_ALL + "where priceid = ?")) {
            prepareStatement.setInt(1, id);

            var resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                priceList = new PriceList();

                priceList.setPriceId(resultSet.getInt("priceid"));
                priceList.setPrice(resultSet.getFloat("price"));
            }

            return priceList;
        } catch (SQLException ex) {
            throw new RepositoryException("Exception while excecuting get all");
        }

    }

    @Override
    public List<Products> getAll() {
        var products = new ArrayList<Products>();
        try (var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                var product = new Products();
                product.setProductId(resultSet.getInt("productid"));
                product.setName(resultSet.getString("name"));
                products.add(product);
            }

            return products;
        } catch (SQLException e) {
            throw new RepositoryException("Exception while executing get all");
        }
    }
}
