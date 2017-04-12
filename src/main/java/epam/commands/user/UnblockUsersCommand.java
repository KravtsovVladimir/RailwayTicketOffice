package epam.commands.user;

import epam.commands.ICommand;
import epam.db.service.factory.ServiceFactory;
import epam.db.service.UserService;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

import static epam.json.JSONResponses.ERROR_USER_HAS_BLOCKED;

public class UnblockUsersCommand implements ICommand {

    private static final Logger logger = Logger.getLogger(UnblockUsersCommand.class);

    @Override
    public JSONStreamAware processRequest(HttpServletRequest request) {

        int user_id = Integer.parseInt(request.getAttribute("user_id").toString());
        logger.debug(user_id);
        UserService userService = ServiceFactory.getInstance().getUserService();
        if (userService.isBlocked(user_id)) {
            return ERROR_USER_HAS_BLOCKED;
        }

        List<Integer> list = new LinkedList<>();
        String[] array = request.getParameter("ids").split(",");

        for (String str : array) {
            list.add(Integer.parseInt(str));
        }

        int rows = userService.unblockUsers(list);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rows", rows);

        return jsonObject;
    }
}
