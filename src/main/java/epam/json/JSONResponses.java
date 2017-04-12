package epam.json;

import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

public final class JSONResponses {
    public static final JSONStreamAware ERROR_INCORRECT_REQUEST;
    public static final JSONStreamAware ERROR_INCORRECT_LOG_IN_REQUEST;
    public static final JSONStreamAware ERROR_INCORRECT_SIGN_UP_REQUEST;
    public static final JSONStreamAware ERROR_INVALID_STATIONS;
    public static final JSONStreamAware ERROR_NO_RESULTS_FOR_REQUEST;
    public static final JSONStreamAware ERROR_USER_HAS_BLOCKED;
    public static final JSONStreamAware ERROR_INVALID_EMAIL_OR_PASSWORD;
    public static final JSONStreamAware ERROR_INVALID_REGISTRATION_DATA;
    public static final JSONStreamAware ERROR_EMAIL_ALREADY_EXIST;
    public static final JSONStreamAware ERROR_WHEN_BUYING_TICKET;
    public static final JSONStreamAware ERROR_AUTHENTICATION;

    static {
        JSONObject response = new JSONObject();
        response.put("errorCode", 1);
        response.put("errorDescription", "Incorrect request");
        ERROR_INCORRECT_REQUEST = JSON.prepare(response);

        response = new JSONObject();
        response.put("errorCode", 2);
        response.put("errorDescription", "Invalid email or password");
        ERROR_INVALID_EMAIL_OR_PASSWORD = JSON.prepare(response);

        response = new JSONObject();
        response.put("errorCode", 3);
        response.put("errorDescription", "Invalid registration data");
        ERROR_INVALID_REGISTRATION_DATA = JSON.prepare(response);

        response = new JSONObject();
        response.put("errorCode", 4);
        response.put("errorDescription", "Such email already exists");
        ERROR_EMAIL_ALREADY_EXIST = JSON.prepare(response);

        response = new JSONObject();
        response.put("errorCode", 5);
        response.put("errorDescription", "Incorrect log in request");
        ERROR_INCORRECT_LOG_IN_REQUEST = JSON.prepare(response);

        response = new JSONObject();
        response.put("errorCode", 6);
        response.put("errorDescription", "Incorrect sign ip request");
        ERROR_INCORRECT_SIGN_UP_REQUEST = JSON.prepare(response);

        response = new JSONObject();
        response.put("errorCode", 7);
        response.put("errorDescription", "An invalid value departure or arrival stations");
        ERROR_INVALID_STATIONS = JSON.prepare(response);

        response = new JSONObject();
        response.put("errorCode", 8);
        response.put("errorDescription", "There are no trains on this date and stations");
        ERROR_NO_RESULTS_FOR_REQUEST = JSON.prepare(response);

        response = new JSONObject();
        response.put("errorCode", 9);
        response.put("errorDescription", "This user has been blocked");
        ERROR_USER_HAS_BLOCKED = JSON.prepare(response);

        response = new JSONObject();
        response.put("errorCode", 10);
        response.put("errorDescription", "An error when buying a ticket, may be somebody has bought this ticket");
        ERROR_WHEN_BUYING_TICKET = JSON.prepare(response);

        response = new JSONObject();
        response.put("errorCode", 11);
        response.put("errorDescription", "An authentication error");
        ERROR_AUTHENTICATION = JSON.prepare(response);
    }
}
