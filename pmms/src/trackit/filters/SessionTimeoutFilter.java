package trackit.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionTimeoutFilter implements Filter {

	private String timeoutPage = "welcomeJSF.jsf";

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

		if ((request instanceof HttpServletRequest)
				&& (response instanceof HttpServletResponse)) {

			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;

			if (isSessionControlRequiredForThisResource(httpServletRequest)) {
				if (isSessionInvalid(httpServletRequest)) {
					String timeoutUrl = httpServletRequest.getContextPath()
							+ "/" + getTimeoutPage();

					System.out.println("Session is invalid, redirecting to timeout page::"
									+ timeoutUrl);

					httpServletResponse.sendRedirect(timeoutUrl);
					return;

				}

			}

		}

		filterChain.doFilter(request, response);

	}

	private boolean isSessionControlRequiredForThisResource(
			HttpServletRequest httpServletRequest) {

		String requestPath = httpServletRequest.getRequestURI();

		//boolean controlRequired = !StringUtils.contains(requestPath,
		//		getTimeoutPage());
		
		//return controlRequired;
		
		return requestPath.indexOf(getTimeoutPage()) == -1;

	}

	private boolean isSessionInvalid(HttpServletRequest httpServletRequest) {

		boolean sessionInValid = (httpServletRequest.getRequestedSessionId() != null)
				&& !httpServletRequest.isRequestedSessionIdValid();

		return sessionInValid;

	}

	public void destroy() {

	}

	public String getTimeoutPage() {

		return timeoutPage;

	}

	public void setTimeoutPage(String timeoutPage) {

		this.timeoutPage = timeoutPage;

	}

}
