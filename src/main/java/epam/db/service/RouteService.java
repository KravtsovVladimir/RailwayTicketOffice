package epam.db.service;

import epam.db.dao.DaoFactory;
import epam.db.dao.interfaces.RouteDao;
import org.apache.log4j.Logger;

public class RouteService {
    private final RouteDao routeDao;
    private static final Logger logger = Logger.getLogger(RouteService.class);

    public RouteService() {
        this.routeDao = DaoFactory.getInstance().getRouteDao();
    }

    public double calculateRoutePrice(String train_number, int dep_st_seq, int arr_st_seq) {
        return routeDao.calculateRoutePrice(train_number, dep_st_seq, arr_st_seq);
    }
}
