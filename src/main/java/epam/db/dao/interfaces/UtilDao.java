package epam.db.dao.interfaces;

import epam.db.dto.Util;

import java.util.List;

public interface UtilDao {

    List<Util> findRoutes(String dep_st, String arr_st, String date, String time);

}
