/*
 * JCatalog Project
 */
package trackit.util;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
//
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//
import trackit.backingbeans.BeanNames;
import trackit.backingbeans.UserBean;

/**
 * This Filter class handle the security of the application.
 * <p>
 * It should be configured inside the web.xml.
 * 
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public class SecurityFilter implements Filter {
	//the login page uri
	private static final String LOGIN_PAGE_URI = "login.jsf";
	
	//the logger object
	private Log logger = LogFactory.getLog(this.getClass());
	
	//a set of restricted resources
	private Set<String> restrictedResources;
	
	/**
	 * Initializes the Filter.
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		this.restrictedResources = new HashSet<String>();
		this.restrictedResources.add("/createProduct.jsf");
		this.restrictedResources.add("/editProduct.jsf");
		this.restrictedResources.add("/productList.jsf");
	}
	
	/**
	 * Standard doFilter object.
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		this.logger.debug("doFilter");
		
		String contextPath = ((HttpServletRequest)req).getContextPath();
		String requestUri = ((HttpServletRequest)req).getRequestURI();
		
		this.logger.debug("contextPath = " + contextPath);
		this.logger.debug("requestUri = " + requestUri);
		
		if (this.contains(requestUri, contextPath) && !this.authorize((HttpServletRequest)req)) {
			this.logger.debug("authorization failed");
			((HttpServletRequest)req).getRequestDispatcher(LOGIN_PAGE_URI).forward(req, res);
		}
		else {
			this.logger.debug("authorization succeeded");
			chain.doFilter(req, res);
		}
	}
	
	public void destroy() {} 
	
	private boolean contains(String value, String contextPath) {
		Iterator<String> ite = this.restrictedResources.iterator();
		
		while (ite.hasNext()) {
			String restrictedResource = (String)ite.next();
			
			if ((contextPath + restrictedResource).equalsIgnoreCase(value)) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean authorize(HttpServletRequest req) {
		UserBean user = (UserBean)req.getSession().getAttribute(BeanNames.USER_BEAN);
		
		if (user != null && user.getLoggedIn()) {
			//user logged in
			return true;
		}
		else {
			return false;
		}
	}
}
