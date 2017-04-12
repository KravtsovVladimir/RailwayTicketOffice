package epam.db.service.factory;

import epam.db.service.*;

public class ServiceFactory {

    private static ServiceFactory instance = new ServiceFactory();
    private UserService userService;
    private UtilService utilService;
    private CarriageService carriageService;
    private SeatService seatService;
    private RouteService routeService;
    private TicketService ticketService;

    private ServiceFactory() {

    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    public UserService getUserService() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public UtilService getUtilService() {
        if (utilService == null) {
            utilService = new UtilService();
        }
        return utilService;
    }

    public CarriageService getCarriageService() {
        if (carriageService == null) {
            carriageService = new CarriageService();
        }
        return carriageService;
    }

    public SeatService getSeatService() {
        if (seatService == null) {
            seatService = new SeatService();
        }
        return seatService;
    }

    public RouteService getRouteService() {
        if (routeService == null) {
            routeService = new RouteService();
        }
        return routeService;
    }

    public TicketService getTicketService() {
        if (ticketService == null) {
            ticketService = new TicketService();
        }
        return ticketService;
    }
}
