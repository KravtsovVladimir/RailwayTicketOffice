package epam.db.dao.interfaces;

import epam.db.dto.Carriage;

import java.util.List;

public interface CarriageDao {

    Carriage findById(int carriage_id);

    List<Integer> numOfCarriages(int carriage_class, String train_number);

    int numOfFreeSeatsInCarriage(String train_number, String date, int dep_st_seq, int arr_st_seq, int carriage_number);

}
