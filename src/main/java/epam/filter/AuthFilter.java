package epam.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Map;

import static epam.json.JSONResponses.ERROR_AUTHENTICATION;

public class AuthFilter implements Filter {

    private FilterConfig config = null;
    private boolean active = false;
    private static final Logger logger = Logger.getLogger(AuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
        String act = config.getInitParameter("active");
        if (act != null)
            active = (act.toUpperCase().equals("TRUE"));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        logger.debug("In filter");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        int user_id = 0;
        String requestType = servletRequest.getParameter("requestType");

        if (requestType.equalsIgnoreCase("login")
                || requestType.equalsIgnoreCase("registration")
                || session.getAttribute("user_id") != null) {

            user_id = Integer.parseInt(session.getAttribute("user_id").toString());
            servletRequest.setAttribute("user_id", user_id);
            filterChain.doFilter(servletRequest, servletResponse);

        } else {
            response.setContentType("application/json; charset=UTF-8");
            Writer writer = null;

            try {
                writer = response.getWriter();
                ERROR_AUTHENTICATION.writeJSONString(writer);
            } catch (IOException e) {
                logger.error("Sorry, something wrong!", e);
            }
        }
    }

    @Override
    public void destroy() {
        config = null;
    }
}
