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

import static epam.json.JSONResponses.ERROR_EMAIL_ALREADY_EXIST;
import static epam.json.JSONResponses.ERROR_INVALID_REGISTRATION_DATA;

public class RegistrationCommand implements ICommand {

    private static final Logger logger = Logger.getLogger(RegistrationCommand.class);

    @Override
    public JSONStreamAware processRequest(HttpServletRequest request) {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");

        if (!(RegExp.validateEmail(email) & RegExp.validatePassword(password)
                & RegExp.validateForLettersAndDash(name) & RegExp.validateForLettersAndDash(surname))) {
            logger.info("ERROR_INVALID_REGISTRATION_DATA");
            return ERROR_INVALID_REGISTRATION_DATA;
        }

        UserService service = ServiceFactory.getInstance().getUserService();
        User user = service.addUser(email, password, name, surname);

        if (user == null) {
            logger.info("ERROR_EMAIL_ALREADY_EXIST");
            return ERROR_EMAIL_ALREADY_EXIST;
        }

        HttpSession session = request.getSession();
        session.setAttribute("user_id", user.getUser_id());

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user_id", user.getUser_id());
        jsonObject.put("email", user.getEmail());
        jsonObject.put("password", user.getPassword());
        jsonObject.put("name", user.getName());
        jsonObject.put("surname", user.getSurname());
        jsonObject.put("ticketCounter", user.getTicketCounter());
        jsonObject.put("isAdmin", user.isAdmin());
        jsonObject.put("isBlocked", user.isBlocked());

        return jsonObject;
    }
}