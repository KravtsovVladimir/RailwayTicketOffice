package epam.commands.exit;

import epam.commands.ICommand;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;

public class ExitCommand implements ICommand {
    private static final Logger logger = Logger.getLogger(ExitCommand.class);

    @Override
    public JSONStreamAware processRequest(HttpServletRequest request) {
        request.getSession().removeAttribute("user_id");
        return new JSONObject();
    }
}
