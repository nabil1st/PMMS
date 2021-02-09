/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trackit.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

public class SessionExpiredFilter implements Filter {

	private static Logger logger = Logger.getLogger(SessionExpiredFilter.class);

    private String page = "/home.jsp";

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        if (((HttpServletRequest) request).getRequestedSessionId() != null && ((HttpServletRequest) request).isRequestedSessionIdValid() == false) {
//            RequestDispatcher rd = request.getRequestDispatcher(page);
//            rd.forward(request, response);
//        } else {
//            try {
//                chain.doFilter(request, response);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                throw new ServletException(ex);
//            }
//        }
        System.out.println(request.toString());
        logger.debug(request.toString());
        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        if (filterConfig.getInitParameter("page") != null) {
            page = filterConfig.getInitParameter("page");
        }
    }
}
