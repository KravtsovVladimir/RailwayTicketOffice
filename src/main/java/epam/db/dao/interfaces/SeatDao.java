package epam.db.dao.interfaces;

import epam.db.dto.Seat;

import java.util.List;

public interface SeatDao {

    Seat findSeat(int seat_id);

    List<Integer> findBusySeats(String train_number, String date, int dep_st_seq, int arr_st_seq, int carriage_number);

    boolean isFree(String train_number, String dep_date, int dep_st_seq, int arr_st_seq, int carriage, int seat);

}
