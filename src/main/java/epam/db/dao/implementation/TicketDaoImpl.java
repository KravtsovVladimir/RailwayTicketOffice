package epam.db.dao.implementation;

import epam.connection.ConnectionProxy;
import epam.db.dao.interfaces.TicketDao;
import epam.db.dto.Ticket;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private static final String QUERY_GET = "SELECT * FROM ticket WHERE user = ?";

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

    @Override
    public List<Ticket> getTickets(int user_id) {
        List<Ticket> list = new ArrayList<>();

        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnectionProxy();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(QUERY_GET)) {
            preparedStatement.setInt(1, user_id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setTicket_id(rs.getInt("ticket_id"));
                ticket.setCreate_date(rs.getString("create_date"));
                ticket.setDeparture_date(rs.getString("departure_date"));
                ticket.setArrival_date(rs.getString("arrival_date"));
                ticket.setTicketPrice(rs.getDouble("ticketPrice"));
                ticket.setDeparture_station(rs.getInt("departure_station"));
                ticket.setArrival_station(rs.getInt("arrival_station"));
                ticket.setUser(rs.getInt("user"));
                ticket.setTrain(rs.getInt("train"));
                ticket.setCarriage(rs.getInt("carriage"));
                ticket.setSeat(rs.getInt("seat"));
                ticket.setName(rs.getString("name"));
                ticket.setSurname(rs.getString("surname"));
                list.add(ticket);
            }

            return list;
        } catch (SQLException e) {
            logger.error("Sorry, something wrong!", e);
        }
        return list;

    }
}
