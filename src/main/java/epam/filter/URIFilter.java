package epam.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

public class URIFilter implements Filter {

    private FilterConfig config = null;
    private boolean active = false;
    private static final Logger logger = Logger.getLogger(URIFilter.class);
    private String resources;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
        String act = config.getInitParameter("active");
        if (act != null)
            active = (act.toUpperCase().equals("TRUE"));

        resources = filterConfig.getInitParameter("resources").replaceAll(" ", "");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.debug("In filter");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = "/APIHandlerServlet";

        logger.debug(uri);
        logger.debug(request.getRequestURI());

        if (isResources(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (request.getRequestURI().equalsIgnoreCase(uri) || request.getRequestURI().equalsIgnoreCase("/")){
            logger.debug("equal");
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            logger.debug("not equal");
            request.getRequestDispatcher("/WEB-INF/error/error.html").forward(request, response);
            return;
        }
    }

    @Override
    public void destroy() {
        config = null;
    }

    private boolean isResources(HttpServletRequest request) {
        String[] extensions = resources.split("[|]");
        String requestURI = request.getRequestURI();

        for (String s : extensions) {
            if (requestURI.endsWith(s)) {
                return true;
            }
        }
        return false;
    }
}
