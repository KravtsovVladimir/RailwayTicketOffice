package epam.db.dao.implementation;

import epam.connection.ConnectionProxy;
import epam.db.dao.interfaces.UtilDao;
import epam.db.dto.Util;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.LinkedList;
import java.util.List;

public class UtilDaoImpl implements UtilDao {

    private static final Logger logger = Logger.getLogger(UtilDaoImpl.class);

    private static final String QUERY_FIND_ROUTES_STATIONS_TRAINS = "SELECT rt1.route_number," +
            " st1.city AS dep_city, st2.city AS arr_city, st1.station_id" +
            " AS dep_st_id, st2.station_id AS arr_st_id, rt1.sequence AS dep_st_seq, rt2.sequence" +
            " AS arr_st_seq, rt1.departure_time, rt2.arrival_time, train.train_id," +
            " train.train_number FROM station AS st1 JOIN station AS st2 ON  st1.city = ? AND st2.city = ?" +
            " JOIN route AS rt1 JOIN route AS rt2 ON rt1.route_number = rt2.route_number JOIN train ON" +
            " train.route_num = rt1.route_number JOIN date_route ON date_route.route_num = rt1.route_number" +
            " AND rt1.station_id = st1.station_id AND rt2.station_id = st2.station_id AND" +
            " rt1.sequence < rt2.sequence and date_route.date = ? AND rt1.departure_time >= ?;";

    private static final String QUERY_FIND_NUM_OF_FREE_SEATS_IN_CARRIAGE = "SELECT (total1 - busy1) AS free FROM ((SELECT sum(total_num_of_seats) AS total1" +
            " FROM carriage WHERE carriage_class = 1 AND train_id = ?) AS q3" +
            " JOIN (SELECT count(*) AS busy1 FROM carriage JOIN (SELECT  carriage, seat, train FROM ticket" +
            " WHERE train = ? AND departure_date = ? AND !(arr_st_seq <= ? OR dep_st_seq >= ?)) AS q1" +
            " ON carriage_id = q1.carriage WHERE carriage_class = 1) AS q2) UNION SELECT (total2 - busy2)" +
            " FROM ((SELECT sum(total_num_of_seats) AS total2 FROM carriage WHERE carriage_class = 2" +
            " AND train_id = ?) AS q6 JOIN (SELECT count(*) AS busy2 FROM carriage JOIN" +
            " (SELECT  carriage, seat, train FROM ticket WHERE train = ? AND departure_date = ?" +
            " AND !(arr_st_seq <= ? OR dep_st_seq >= ?)) AS q4 ON carriage_id = q4.carriage" +
            " WHERE carriage_class = 2) AS q5)";

    private static final String QUERY_CALCULATE_ROUTE_PRICE = "SELECT sum(price_to_next_st) AS price" +
            " FROM route WHERE route_number = ? AND route.station_id >= ? AND route.station_id < ?;";

    @Override
    public List<Util> findRoutes(String dep_st, String arr_st, String date, String time) {

        List<Util> utils = new LinkedList<>();

        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnectionProxy();
             PreparedStatement preparedStatement1 = connectionProxy.prepareStatement(QUERY_FIND_ROUTES_STATIONS_TRAINS)) {

            preparedStatement1.setString(1, dep_st);
            preparedStatement1.setString(2, arr_st);
            preparedStatement1.setString(3, date);
            preparedStatement1.setString(4, time);

            ResultSet rs1 = preparedStatement1.executeQuery();

            while (rs1.next()) {
                Util util = new Util();
                util.setRoute_number(rs1.getInt("route_number"));
                util.setDep_city(rs1.getString("dep_city"));
                util.setArr_city(rs1.getString("arr_city"));
                util.setDep_st_id(rs1.getInt("dep_st_id"));
                util.setArr_st_id(rs1.getInt("arr_st_id"));
                util.setDep_seq(rs1.getInt("dep_st_seq"));
                util.setArr_seq(rs1.getInt("arr_st_seq"));
                util.setDep_time((Time) rs1.getObject("departure_time"));
                util.setArr_time((Time) rs1.getObject("arrival_time"));
                util.setTravel_time(new Time(util.getArr_time().getTime() - util.getDep_time().getTime()));
                util.setTrain_id(rs1.getInt("train_id"));
                util.setTrain_number(rs1.getString("train_number"));

                PreparedStatement preparedStatement2 = connectionProxy.prepareStatement(QUERY_FIND_NUM_OF_FREE_SEATS_IN_CARRIAGE);
                preparedStatement2.setInt(1, util.getTrain_id());
                preparedStatement2.setInt(2, util.getTrain_id());
                preparedStatement2.setString(3, date);
                preparedStatement2.setInt(4, util.getDep_seq());
                preparedStatement2.setInt(5, util.getArr_seq());
                preparedStatement2.setInt(6, util.getTrain_id());
                preparedStatement2.setInt(7, util.getTrain_id());
                preparedStatement2.setString(8, date);
                preparedStatement2.setInt(9, util.getDep_seq());
                preparedStatement2.setInt(10, util.getArr_seq());

                ResultSet rs2 = preparedStatement2.executeQuery();
                int i = 0;
                while (rs2.next()) {
                    if (i == 0) {
                        util.setFreeSeats_1st_class(rs2.getInt("free"));
                        i++;
                    } else {
                        util.setFreeSeats_2nd_class(rs2.getInt("free"));
                    }
                }

                PreparedStatement preparedStatement3 = connectionProxy.prepareStatement(QUERY_CALCULATE_ROUTE_PRICE);
                preparedStatement3.setInt(1, util.getRoute_number());
                preparedStatement3.setInt(2, util.getDep_st_id());
                preparedStatement3.setInt(3, util.getArr_st_id());

                ResultSet rs3 = preparedStatement3.executeQuery();
                if (rs3.next()) {
                    util.setPrice_1st_class(rs3.getDouble("price"));
                    util.setPrice_2nd_class(rs3.getDouble("price"));
                }

                rs2.close();
                rs3.close();
                preparedStatement2.close();
                preparedStatement3.close();
                utils.add(util);
            }

            rs1.close();
        } catch (SQLException e) {
            logger.error("Sorry, something wrong!", e);
        }
        return utils;
    }
}
