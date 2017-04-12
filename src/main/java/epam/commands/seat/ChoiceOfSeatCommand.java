package epam.commands.seat;

import epam.commands.ICommand;
import epam.db.service.CarriageService;
import epam.db.service.UserService;
import epam.db.service.factory.ServiceFactory;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static epam.json.JSONResponses.ERROR_USER_HAS_BLOCKED;

public class ChoiceOfSeatCommand implements ICommand {

    private static final Logger logger = Logger.getLogger(ChoiceOfSeatCommand.class);

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
        int carriage_class = Integer.parseInt(request.getParameter("carriage_class"));

        CarriageService carriageService = ServiceFactory.getInstance().getCarriageService();
        Map<Integer, Integer> map = carriageService.numOfFreeSeatsInCarriages(train_number, dep_date, dep_st_seq, arr_st_seq, carriage_class);

        JSONObject outerJSON = new JSONObject();
        int i = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            JSONObject innerJSON = new JSONObject();
            innerJSON.put("carriage_number", entry.getKey());
            innerJSON.put("free_seats", entry.getValue());
            outerJSON.put(i, innerJSON);
            i++;
        }

        return outerJSON;
    }
}
