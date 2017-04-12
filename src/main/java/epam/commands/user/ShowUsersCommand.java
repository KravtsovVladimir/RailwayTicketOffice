package epam.commands.user;

import epam.commands.ICommand;
import epam.db.dto.User;
import epam.db.service.factory.ServiceFactory;
import epam.db.service.UserService;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static epam.json.JSONResponses.ERROR_USER_HAS_BLOCKED;

public class ShowUsersCommand implements ICommand {

    private static final Logger logger = Logger.getLogger(ShowUsersCommand.class);

    @Override
    public JSONStreamAware processRequest(HttpServletRequest request) {

        int user_id = Integer.parseInt(request.getAttribute("user_id").toString());
        logger.debug(user_id);
        UserService userService = ServiceFactory.getInstance().getUserService();
        if (userService.isBlocked(user_id)) {
            return ERROR_USER_HAS_BLOCKED;
        }

        List<User> users = userService.findAllUsers();
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < users.size(); i++) {
            JSONObject tempJson = new JSONObject();
            tempJson.put("id", users.get(i).getUser_id());
            tempJson.put("email", users.get(i).getEmail());
            tempJson.put("password", users.get(i).getPassword());
            tempJson.put("name", users.get(i).getName());
            tempJson.put("surname", users.get(i).getSurname());
            tempJson.put("isAdmin", users.get(i).isAdmin());
            tempJson.put("isBlocked", users.get(i).isBlocked());
            jsonObject.put(i, tempJson);
        }

        return jsonObject;
    }
}
