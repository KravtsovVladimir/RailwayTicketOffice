package epam.commands.route;

import epam.commands.ICommand;
import epam.db.dto.Util;
import epam.db.service.UserService;
import epam.db.service.factory.ServiceFactory;
import epam.db.service.UtilService;
import epam.regexp.RegExp;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static epam.json.JSONResponses.*;

public class SearchRoutesCommand implements ICommand {

    private static final Logger logger = Logger.getLogger(SearchRoutesCommand.class);

    @Override
    public JSONStreamAware processRequest(HttpServletRequest request) {

        int user_id = Integer.parseInt(request.getAttribute("user_id").toString());
        logger.debug(user_id);
        UserService userService = ServiceFactory.getInstance().getUserService();
        if (userService.isBlocked(user_id)) {
            return ERROR_USER_HAS_BLOCKED;
        }

        String dep_st = request.getParameter("dep_st");
        String arr_st = request.getParameter("arr_st");
        String date = request.getParameter("date");
        String time = request.getParameter("time");

        if (!(RegExp.validateForLettersAndDash(dep_st) & RegExp.validateForLettersAndDash(arr_st))) {
            logger.info("ERROR_INVALID_STATIONS");
            return ERROR_INVALID_STATIONS;
        }

        UtilService utilService = ServiceFactory.getInstance().getUtilService();
        List<Util> routes = null;

        try{

            routes = utilService.searchPossibleRoutes(dep_st, arr_st, date, time);
        }catch (IllegalArgumentException e){
            return ERROR_NAME_INVALID;
        }

        if (routes.size() == 0) {
            logger.info("ERROR_NO_RESULTS_FOR_REQUEST");
            return ERROR_NO_RESULTS_FOR_REQUEST;
        }

        JSONObject jsonObject = new JSONObject();
        int i = 0;
        for (Util dto : routes) {
            JSONObject tempJson = new JSONObject();
            tempJson.put("train", dto.getTrain_number());
            tempJson.put("dep_st", dto.getDep_city());
            tempJson.put("arr_st", dto.getArr_city());
            tempJson.put("arr_st_seq", dto.getArr_seq());
            tempJson.put("dep_st_seq", dto.getDep_seq());
            tempJson.put("route_number", dto.getRoute_number());
            tempJson.put("dep_date", date);
            tempJson.put("arr_date", date);
            tempJson.put("dep_time", dto.getDep_time().toString().replace(':', '.'));
            tempJson.put("arr_time", dto.getArr_time().toString().replace(':', '.'));
            tempJson.put("travel_time", dto.getTravel_time().toString().replace(':', '.'));
            tempJson.put("free1st", dto.getFreeSeats_1st_class());
            tempJson.put("free2nd", dto.getFreeSeats_2nd_class());
            tempJson.put("price1st", dto.getPrice_1st_class());
            tempJson.put("price2nd", dto.getPrice_2nd_class());
            tempJson.put("dep_st_id", dto.getDep_st_id());
            tempJson.put("arr_st_id", dto.getArr_st_id());
            jsonObject.put(i, tempJson);
            i++;
        }

        return jsonObject;
    }
}
