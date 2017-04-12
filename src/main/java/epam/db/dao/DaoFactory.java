package epam.db.dao;

import epam.db.dao.implementation.*;
import epam.db.dao.interfaces.*;

public class DaoFactory {

    public static DaoFactory instance = new DaoFactory();

    private RouteDao routeDao;
    private CarriageDao carriageDao;
    private SeatDao seatDao;
    private UserDao userDao;
    private TicketDao ticketDao;
    private UtilDao utilDao;

    private DaoFactory() {

    }

    public static DaoFactory getInstance() {
        return DaoFactory.instance;
    }

    public synchronized RouteDao getRouteDao() {
        if (routeDao == null) {
            routeDao = new RouteDaoImpl();
        }
        return routeDao;
    }

    public synchronized CarriageDao getCarriageDao() {
        if (carriageDao == null) {
            carriageDao = new CarriageDaoImpl();
        }
        return carriageDao;
    }

    public synchronized SeatDao getSeatDao() {
        if (seatDao == null) {
            seatDao = new SeatDaoImpl();
        }
        return seatDao;
    }

    public synchronized UserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDaoImpl();
        }
        return userDao;
    }

    public synchronized TicketDao getTicketDao() {
        if (ticketDao == null) {
            ticketDao = new TicketDaoImpl();
        }
        return ticketDao;
    }

    public synchronized UtilDao getUtilDao() {
        if (utilDao == null) {
            utilDao = new UtilDaoImpl();
        }
        return utilDao;
    }
}
