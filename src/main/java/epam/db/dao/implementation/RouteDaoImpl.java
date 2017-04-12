package epam.db.dao.implementation;

import epam.connection.ConnectionProxy;
import epam.db.dao.interfaces.RouteDao;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RouteDaoImpl implements RouteDao {

    private static final Logger logger = Logger.getLogger(RouteDaoImpl.class);

    private static final String QUERY_CALC_ROUTE_PRICE = "SELECT sum(price_to_next_st) AS price FROM route JOIN train" +
            " ON route.route_number = train.route_num AND train_number = ? AND sequence >= ? AND sequence <= ?";


    @Override
    public double calculateRoutePrice(String train_number, int dep_st_seq, int arr_st_seq) {
        int price = 0;

        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnectionProxy();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(QUERY_CALC_ROUTE_PRICE)) {

            preparedStatement.setString(1, train_number);
            preparedStatement.setInt(2, dep_st_seq);
            preparedStatement.setInt(3, arr_st_seq);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                price = rs.getInt("price");
            }

            rs.close();
        } catch (SQLException e) {
            logger.error("Sorry, something wrong!", e);
        }

        return price;
    }
}
