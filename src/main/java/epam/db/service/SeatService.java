package epam.db.service;

import epam.db.dao.DaoFactory;
import epam.db.dao.interfaces.SeatDao;

import java.util.List;

public class SeatService {
    private final SeatDao seatDao;

    public SeatService() {
        this.seatDao = DaoFactory.getInstance().getSeatDao();
    }

    public List<Integer> findBusySeats(String train_number, String date, int dep_st_seq, int arr_st_seq, int carriage_number) {
        return seatDao.findBusySeats(train_number, date, dep_st_seq, arr_st_seq, carriage_number);
    }

}
