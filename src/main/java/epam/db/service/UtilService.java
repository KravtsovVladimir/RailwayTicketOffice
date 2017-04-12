package epam.db.service;

import epam.db.dao.DaoFactory;
import epam.db.dao.interfaces.UtilDao;
import epam.db.dto.Util;

import java.util.List;

public class UtilService {
    private final UtilDao utilDao;

    public UtilService() {
        this.utilDao = DaoFactory.getInstance().getUtilDao();
    }

    public List<Util> searchPossibleRoutes(String dep_st, String arr_st, String date, String time) {
        return utilDao.findRoutes(dep_st, arr_st, date, time);

    }
}
