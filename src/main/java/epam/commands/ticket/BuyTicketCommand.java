package epam.commands.ticket;

import epam.commands.ICommand;
import epam.db.service.UserService;
import epam.db.service.factory.ServiceFactory;
import epam.db.service.TicketService;
import org.apache.log4j.Logger;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static epam.json.JSONResponses.ERROR_NAME_INVALID;
import static epam.json.JSONResponses.ERROR_USER_HAS_BLOCKED;
import static epam.json.JSONResponses.ERROR_WHEN_BUYING_TICKET;

public class BuyTicketCommand implements ICommand {

    private static final Logger logger = Logger.getLogger(BuyTicketCommand.class);

    @Override
    public JSONStreamAware processRequest(HttpServletRequest request) {

        int user_id = Integer.parseInt(request.getAttribute("user_id").toString());
        logger.debug(user_id);
        UserService userService = ServiceFactory.getInstance().getUserService();
        if (userService.isBlocked(user_id)) {
            return ERROR_USER_HAS_BLOCKED;
        }

        Enumeration<String> parameterNames = request.getParameterNames();
        parameterNames.nextElement();
        String train_number = request.getParameter(parameterNames.nextElement());
        String dep_date = request.getParameter(parameterNames.nextElement());
        String dep_st = request.getParameter(parameterNames.nextElement());
        String arr_st = request.getParameter(parameterNames.nextElement());
        int dep_st_seq = Integer.parseInt(request.getParameter(parameterNames.nextElement()));
        int arr_st_seq = Integer.parseInt(request.getParameter(parameterNames.nextElement()));
        double price = Double.parseDouble(request.getParameter(parameterNames.nextElement()));
        List<Integer> carriages = new LinkedList<>();
        List<Integer> seats = new LinkedList<>();
        List<String> names = new LinkedList<>();
        List<String> surnames = new LinkedList<>();

        while (parameterNames.hasMoreElements()) {
            carriages.add(Integer.parseInt(request.getParameter(parameterNames.nextElement())));
            seats.add(Integer.parseInt(request.getParameter(parameterNames.nextElement())));
            surnames.add(request.getParameter(parameterNames.nextElement()));
            names.add(request.getParameter(parameterNames.nextElement()));
        }

        TicketService ticketService = ServiceFactory.getInstance().getTicketService();
        boolean b = false;
        try {
            b = ticketService.buyTickets(train_number, dep_date, dep_st, arr_st, price, dep_st_seq, arr_st_seq, user_id,
                    carriages, seats, names, surnames);
        } catch (IllegalArgumentException e) {
            logger.error(e);
            return ERROR_NAME_INVALID;
        }

        if (!b) {
            logger.info("ERROR_WHEN_BUYING_TICKET");
            return ERROR_WHEN_BUYING_TICKET;
        }

        return new org.json.simple.JSONObject();
    }
}
