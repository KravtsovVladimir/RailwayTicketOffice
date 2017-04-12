package epam.db.dao.implementation;

import epam.connection.ConnectionProxy;
import epam.db.dao.interfaces.CarriageDao;
import epam.db.dto.Carriage;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CarriageDaoImpl implements CarriageDao {

    private static final Logger logger = Logger.getLogger(CarriageDaoImpl.class);
    private static final String QUERY_FIND_BY_ID = "SELECT * FROM carriage WHERE id = ?";
    private static final String QUERY_NUM_OF_CARRIAGES = "SELECT carriage_number FROM carriage WHERE carriage_class = ?" +
            " AND train_id = (SELECT train_id FROM train WHERE train_number = ?)";

    private static final String QUERY_NUM_OF_FREE_SEATS = "SELECT (carriage.total_num_of_seats - q1.busy) as free" +
            " FROM carriage, (SELECT count(*) AS busy FROM ticket JOIN carriage ON ticket.carriage = carriage.carriage_id" +
            " WHERE train = (SELECT train_id FROM train WHERE train_number = ?) AND departure_date = ?" +
            " AND !(arr_st_seq <= ? OR dep_st_seq >= ?) AND carriage.carriage_number = ?) as q1" +
            " WHERE carriage_number = ? AND train_id = (SELECT train_id FROM train WHERE train_number = ?);";

    @Override
    public Carriage findById(int carriage_id) {
        Carriage carriage = null;

        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnectionProxy();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(QUERY_FIND_BY_ID)) {

            preparedStatement.setInt(1, carriage_id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                carriage = new Carriage();
                carriage.setCarriage_id(rs.getInt("carriage_id"));
                carriage.setCarriage_number(rs.getInt("carriage_number"));
                carriage.setCarriage_class(rs.getInt("carriage_class"));
                carriage.setTotal_num_of_seats(rs.getInt("total_num_of_seats"));
                carriage.setTrain_id(rs.getInt("train_id"));
            }

            rs.close();
        } catch (SQLException e) {
            logger.error("Sorry, something wrong!", e);
        }

        return carriage;
    }

    @Override
    public List<Integer> numOfCarriages(int carriage_class, String train_number) {
        List<Integer> list = new LinkedList<>();

        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnectionProxy();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(QUERY_NUM_OF_CARRIAGES)) {

            preparedStatement.setInt(1, carriage_class);
            preparedStatement.setString(2, train_number);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                list.add(rs.getInt("carriage_number"));
            }

            rs.close();
        } catch (SQLException e) {
            logger.error("Sorry, something wrong!", e);
        }

        return list;
    }

    @Override
    public int numOfFreeSeatsInCarriage(String train_number, String date, int dep_st_seq, int arr_st_seq, int carriage_number) {
        int numOfFreeSeats = 0;

        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnectionProxy();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(QUERY_NUM_OF_FREE_SEATS)) {

            preparedStatement.setString(1, train_number);
            preparedStatement.setString(2, date);
            preparedStatement.setInt(3, dep_st_seq);
            preparedStatement.setInt(4, arr_st_seq);
            preparedStatement.setInt(5, carriage_number);
            preparedStatement.setInt(6, carriage_number);
            preparedStatement.setString(7, train_number);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                numOfFreeSeats = rs.getInt("free");
            }

            rs.close();
        } catch (SQLException e) {
            logger.error("Sorry, something wrong!", e);
        }

        return numOfFreeSeats;
    }
}
