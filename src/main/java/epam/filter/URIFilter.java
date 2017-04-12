package epam.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by Мир on 12.04.2017.
 */
public class URIFilter implements Filter {

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
        String uri = "/APIHandlerServlet";

        if (request.getRequestURI().equalsIgnoreCase(uri)){
            logger.debug("equal");
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            request.getRequestDispatcher("/WEB-INF/error/error.html").forward(request, response);
            logger.debug("not equal");
            return;
        }
    }

    @Override
    public void destroy() {
        config = null;
    }
}
