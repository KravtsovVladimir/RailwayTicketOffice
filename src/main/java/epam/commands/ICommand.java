package epam.commands;

import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Мир on 10.04.2017.
 */
public interface ICommand {
    JSONStreamAware processRequest(HttpServletRequest request);
}
