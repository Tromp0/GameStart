package cat.uvic.teknos.m06.gamestart.list.repositories;

import cat.uvic.teknos.m06.gamestart.list.models.Members;
import cat.uvic.teknos.m06.gamestart.list.models.PriceList;
import cat.uvic.teknos.m06.gamestart.list.models.Products;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class jdbcPriceListRepository implements Repository<PriceList, Integer>{
    private static final String INSERT = "insert into pricelist (price) values (?)";
    private static final String UPDATE = "update pricelist set price = ? where priceid = ?";
    private static final String SELECT_ALL = "select priceid, price from pricelist";
    private static final String DELETE = "delete from pricelist where priceid = ?";
    private final Connection connection;
    public jdbcPriceListRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(PriceList pricelist) {
        if (pricelist == null) {
            throw new RepositoryException("The products is null!");
        }
        if (pricelist.getPriceId() <= 0) {
            insert(pricelist);
        } else {
            update(pricelist);
        }
    }

    private void update(PriceList pricelist) {
        try (var preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setFloat(1, pricelist.getPrice());
            preparedStatement.setInt(1, pricelist.getPriceId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception while inserting: " + pricelist, e);
        }

    }

    private void insert(PriceList pricelist) {
        try (var preparedStatement = connection.prepareStatement(INSERT)) {
            preparedStatement.setFloat(1, pricelist.getPrice());
            preparedStatement.executeUpdate();
            var generatedKeysResultSet = preparedStatement.getGeneratedKeys();
            if (!generatedKeysResultSet.next()) {
                throw new RepositoryException("Exception while inserting: id not generated" + pricelist);
            }
            pricelist.setPriceId(generatedKeysResultSet.getInt(1));
        } catch (SQLException e) {
            throw new RepositoryException("Exception while inserting: " + pricelist, e);
        }
    }

    public void delete(PriceList pricelist){
        try(var preparedStatement = connection.prepareStatement(DELETE)){
            preparedStatement.setInt(1,pricelist.getPriceId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception while trying to delete", e);
        }
    }

    @Override
    public PriceList getById(Integer id){
        PriceList priceList = null;

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
    public List<PriceList> getAll() {
        var pricelists = new ArrayList<PriceList>();
        try (var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                var pricelist = new PriceList();
                pricelist.setPriceId(resultSet.getInt("priceid"));
                pricelist.setPrice(resultSet.getFloat("price"));
                pricelists.add(pricelist);
            }

            return pricelists;
        } catch (SQLException e) {
            throw new RepositoryException("Exception while executing get all");
        }
    }
}
