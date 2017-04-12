package epam.db.dao.implementation;

import epam.connection.ConnectionProxy;
import epam.db.dao.interfaces.TicketDao;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketDaoImpl implements TicketDao {

    private static final Logger logger = Logger.getLogger(TicketDaoImpl.class);

    private static final String QUERY_FIND_IDS = "SELECT train.train_id, train.route_num, carriage.carriage_id," +
            " seat.seat_id, st1.station_id AS dep_st, st2.station_id AS arr_st FROM train JOIN carriage" +
            " ON train.train_id = carriage.train_id JOIN seat ON carriage.carriage_id = seat.carriage_id" +
            " AND seat_number = ? AND carriage_number = ? AND train_number = ? JOIN station as st1 ON st1.city=?" +
            " JOIN station as st2 ON st2.city = ?";

    private static final String QUERY_INSERT = "INSERT INTO ticket (departure_date, arrival_date, ticketPrice," +
            " route_number, departure_station, arrival_station, dep_st_seq, arr_st_seq, user, train, carriage," +
            " seat, surname, name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    @Override
    public void buyTicket(String train_number, String dep_date, String dep_st, String arr_st, double price,
                          int carriage_number, int seat_number, String name, String surname, int dep_st_seq,
                          int arr_st_seq, int user_id) {


        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnectionProxy();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(QUERY_FIND_IDS)) {

            preparedStatement.setInt(1, seat_number);
            preparedStatement.setInt(2, carriage_number);
            preparedStatement.setString(3, train_number);
            preparedStatement.setString(4, dep_st);
            preparedStatement.setString(5, arr_st);

            ResultSet rs = preparedStatement.executeQuery();
            int train_id = 0;
            int route_num = 0;
            int carriage_id = 0;
            int seat_id = 0;
            int dep_st_id = 0;
            int arr_st_id = 0;

            if (rs.next()) {
                train_id = rs.getInt("train_id");
                route_num = rs.getInt("route_num");
                carriage_id = rs.getInt("carriage_id");
                seat_id = rs.getInt("seat_id");
                dep_st_id = rs.getInt("dep_st");
                arr_st_id = rs.getInt("arr_st");
            }

            PreparedStatement preparedStatement2 = connectionProxy.prepareStatement(QUERY_INSERT);

            preparedStatement2.setString(1, dep_date);
            preparedStatement2.setString(2, dep_date);
            preparedStatement2.setDouble(3, price);
            preparedStatement2.setInt(4, route_num);
            preparedStatement2.setInt(5, dep_st_id);
            preparedStatement2.setInt(6, arr_st_id);
            preparedStatement2.setInt(7, dep_st_seq);
            preparedStatement2.setInt(8, arr_st_seq);
            preparedStatement2.setInt(9, user_id);
            preparedStatement2.setInt(10, train_id);
            preparedStatement2.setInt(11, carriage_id);
            preparedStatement2.setInt(12, seat_id);
            preparedStatement2.setString(13, surname);
            preparedStatement2.setString(14, name);

            preparedStatement2.executeUpdate();

            rs.close();
            preparedStatement2.close();
        } catch (SQLException e) {
            logger.error("Sorry, something wrong!", e);
        }

    }
}
