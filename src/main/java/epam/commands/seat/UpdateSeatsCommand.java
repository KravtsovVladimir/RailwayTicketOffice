package epam.commands.seat;

import epam.commands.ICommand;
import epam.db.service.RouteService;
import epam.db.service.SeatService;
import epam.db.service.UserService;
import epam.db.service.factory.ServiceFactory;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

import static epam.json.JSONResponses.ERROR_USER_HAS_BLOCKED;

public class UpdateSeatsCommand implements ICommand {

    private static final Logger logger = Logger.getLogger(UpdateSeatsCommand.class);

    @Override
    public JSONStreamAware processRequest(HttpServletRequest request) {

        int user_id = Integer.parseInt(request.getAttribute("user_id").toString());
        logger.debug(user_id);
        UserService userService = ServiceFactory.getInstance().getUserService();
        if (userService.isBlocked(user_id)) {
            return ERROR_USER_HAS_BLOCKED;
        }

        String train_number = request.getParameter("train_number");
        String dep_date = request.getParameter("dep_date");
        int dep_st_seq = Integer.parseInt(request.getParameter("dep_st_seq"));
        int arr_st_seq = Integer.parseInt(request.getParameter("arr_st_seq"));
        int carriage = Integer.parseInt(request.getParameter("carriage"));

        SeatService seatService = ServiceFactory.getInstance().getSeatService();
        RouteService routeService = ServiceFactory.getInstance().getRouteService();

        List<Integer> list = seatService.findBusySeats(train_number, dep_date, dep_st_seq, arr_st_seq, carriage);
        double price = routeService.calculateRoutePrice(train_number, dep_st_seq, arr_st_seq);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        jsonArray.addAll(list);
        jsonObject.put("seats", jsonArray);
        jsonObject.put("price", price);

        return jsonObject;
    }
}
