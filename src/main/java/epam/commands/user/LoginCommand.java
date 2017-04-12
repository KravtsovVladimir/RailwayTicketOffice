package epam.commands.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import epam.commands.ICommand;
import epam.db.dto.User;
import epam.db.service.factory.ServiceFactory;
import epam.db.service.UserService;
import epam.regexp.RegExp;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.UnsupportedEncodingException;

import static epam.json.JSONResponses.ERROR_INCORRECT_LOG_IN_REQUEST;
import static epam.json.JSONResponses.ERROR_INVALID_EMAIL_OR_PASSWORD;
import static epam.json.JSONResponses.ERROR_USER_HAS_BLOCKED;

/**
 * Created by Мир on 30.03.2017.
 */
public class LoginCommand implements ICommand {

    private static final Logger logger = Logger.getLogger(LoginCommand.class);

    @Override
    public JSONStreamAware processRequest(HttpServletRequest request) {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (!(RegExp.validateEmail(email) & RegExp.validatePassword(password))) {
            logger.info("ERROR_INVALID_EMAIL_OR_PASSWORD");
            return ERROR_INVALID_EMAIL_OR_PASSWORD;
        }

        UserService service = ServiceFactory.getInstance().getUserService();
        User user = service.findUser(email, password);
        if (user == null) {
            logger.info("ERROR_INCORRECT_LOG_IN_REQUEST");
            return ERROR_INCORRECT_LOG_IN_REQUEST;
        }

        if (user.isBlocked()) {
            logger.info("ERROR_USER_HAS_BLOCKED");
            return ERROR_USER_HAS_BLOCKED;
        }

        HttpSession session = request.getSession();
        session.setAttribute("user_id", user.getUser_id());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_id", user.getUser_id());
        jsonObject.put("email", user.getEmail());
        jsonObject.put("name", user.getName());
        jsonObject.put("surname", user.getSurname());
        jsonObject.put("isAdmin", user.isAdmin());
        jsonObject.put("isBlocked", user.isBlocked());

        return jsonObject;
    }
}
