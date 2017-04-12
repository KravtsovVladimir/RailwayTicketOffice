package epam.servlet;

import epam.commands.*;
import epam.json.JSON;
import org.apache.log4j.Logger;
import org.json.simple.JSONStreamAware;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

import static epam.json.JSONResponses.ERROR_INCORRECT_REQUEST;

public class APIHandlerServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(epam.servlet.APIHandlerServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doGet");
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doPost");
        process(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doPut");
        process(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doDelete");
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate, private");
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setDateHeader("Expires", 0);
        JSONStreamAware response = JSON.emptyJSON;

        try {

            String requestType = req.getParameter("requestType");
            if (requestType == null) {
                logger.info("ERROR_INCORRECT_REQUEST");
                response = ERROR_INCORRECT_REQUEST;
                return;
            }

            ICommand command = CommandDispatcher.getInstance().getCommand(requestType);
            if (command == null) {
                logger.info("ERROR_INCORRECT_REQUEST");
                response = ERROR_INCORRECT_REQUEST;
                return;
            }

            response = command.processRequest(req);

        } finally {
            resp.setContentType("application/json; charset=UTF-8");
            Writer writer = resp.getWriter();
            response.writeJSONString(writer);
        }

    }
}
