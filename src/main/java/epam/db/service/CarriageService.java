package epam.db.service;

import epam.db.dao.DaoFactory;
import epam.db.dao.interfaces.CarriageDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarriageService {
    private final CarriageDao carriageDao;

    public CarriageService() {
        this.carriageDao = DaoFactory.getInstance().getCarriageDao();
    }

    public Map<Integer, Integer> numOfFreeSeatsInCarriages(String train_number, String date,
                                                           int dep_st_seq, int arr_st_seq, int carriage_class) {

        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> list = carriageDao.numOfCarriages(carriage_class, train_number);
        for (int i : list) {
            int freeSeats = carriageDao.numOfFreeSeatsInCarriage(train_number, date, dep_st_seq, arr_st_seq, i);
            map.put(i, freeSeats);
        }
        return map;
    }
}
