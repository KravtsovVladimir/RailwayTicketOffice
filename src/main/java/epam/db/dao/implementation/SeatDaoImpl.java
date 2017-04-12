package epam.db.dao.implementation;

import epam.connection.ConnectionProxy;
import epam.db.dao.interfaces.SeatDao;
import epam.db.dto.Seat;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class SeatDaoImpl implements SeatDao {

    private static final Logger logger = Logger.getLogger(SeatDaoImpl.class);
    private static final String QUERY_FIND_BY_ID = "SELECT * FROM seat WHERE seat_id = ?";

    private static final String QUERY_FIND_BUSY_SEATS = "SELECT seat_number FROM seat INNER JOIN ticket" +
            " ON seat.seat_id = ticket.seat WHERE ticket.departure_date = ? AND ticket.train =" +
            " (SELECT train_id FROM train WHERE train_number = ?) AND carriage = (SELECT carriage_id FROM carriage" +
            " WHERE carriage_number = ? AND train_id = (SELECT train_id FROM train WHERE train_number = ?))" +
            " AND ((ticket.dep_st_seq <= ? AND arr_st_seq >= ?) OR (ticket.dep_st_seq <= ? AND arr_st_seq > ?)" +
            " OR (ticket.dep_st_seq >= ? AND ticket.dep_st_seq < ?))";

    private static final String QUERY_IS_FREE = "SELECT * FROM ticket JOIN seat ON seat_id = ticket.seat" +
            " AND seat_number = ?  JOIN carriage ON seat.carriage_id = carriage.carriage_id AND carriage_number = ?" +
            " JOIN train ON carriage.train_id = train.train_id AND train_number = ? AND ticket.departure_date = ?" +
            " AND ((ticket.dep_st_seq <= ? AND ticket.arr_st_seq >= ?) OR (ticket.dep_st_seq <= ?" +
            " AND ticket.arr_st_seq > ?) OR (ticket.dep_st_seq >= ? AND ticket.dep_st_seq < ?))";

    @Override
    public Seat findSeat(int seat_id) {
        Seat seat = null;

        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnectionProxy();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(QUERY_FIND_BY_ID)) {

            preparedStatement.setInt(1, seat_id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                seat = new Seat();
                seat.setSeat_id(rs.getInt("seat_id"));
                seat.setFree(rs.getBoolean("isFree"));
                seat.setSeat_number(rs.getInt("seat_number"));
                seat.setCarriage_id(rs.getInt("carriage_id"));
            }

            rs.close();
        } catch (SQLException e) {
            logger.error("Sorry, something wrong!", e);
        }

        return seat;
    }

    @Override
    public List<Integer> findBusySeats(String train_number, String date, int dep_st_seq, int arr_st_seq, int carriage_number) {
        List<Integer> list = null;

        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnectionProxy();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(QUERY_FIND_BUSY_SEATS)) {

            preparedStatement.setString(1, date);
            preparedStatement.setString(2, train_number);
            preparedStatement.setInt(3, carriage_number);
            preparedStatement.setString(4, train_number);
            preparedStatement.setInt(5, dep_st_seq);
            preparedStatement.setInt(6, arr_st_seq);
            preparedStatement.setInt(7, dep_st_seq);
            preparedStatement.setInt(8, dep_st_seq);
            preparedStatement.setInt(9, dep_st_seq);
            preparedStatement.setInt(10, arr_st_seq);

            ResultSet rs = preparedStatement.executeQuery();

            list = new LinkedList<>();
            while (rs.next()) {
                list.add(rs.getInt("seat_number"));
            }

            rs.close();
        } catch (SQLException e) {
            logger.error("Sorry, something wrong!", e);
        }

        return list;
    }

    @Override
    public boolean isFree(String train_number, String dep_date, int dep_st_seq, int arr_st_seq, int carriage, int seat) {
        boolean isFree = false;

        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnectionProxy();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(QUERY_IS_FREE)) {

            preparedStatement.setInt(1, seat);
            preparedStatement.setInt(2, carriage);
            preparedStatement.setString(3, train_number);
            preparedStatement.setString(4, dep_date);
            preparedStatement.setInt(5, dep_st_seq);
            preparedStatement.setInt(6, arr_st_seq);
            preparedStatement.setInt(7, dep_st_seq);
            preparedStatement.setInt(8, dep_st_seq);
            preparedStatement.setInt(9, dep_st_seq);
            preparedStatement.setInt(10, arr_st_seq);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                isFree = false;
            } else {
                isFree = true;
            }

            rs.close();
        } catch (SQLException e) {
            logger.error("Sorry, something wrong!", e);
        }

        return isFree;
    }
}
