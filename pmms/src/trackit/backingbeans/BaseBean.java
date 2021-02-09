/*
 * JCatalog Project
 */
package trackit.backingbeans;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//
import trackit.servicelocator.ServiceLocator;

/**
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public class BaseBean {
	//the logger for this class
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	//the service locator of the business services
	protected ServiceLocator serviceLocator;
	
	public BaseBean() {
	}
	
	public ServiceLocator getServiceLocator() {
		return this.serviceLocator;
	}
	
	public void setServiceLocator(ServiceLocator newServiceLocator) {
		this.logger.debug("service locator is set");
		this.serviceLocator = newServiceLocator;
		
		this.init();
	}
	
	/**
	 * Used to initialize the managed bean.
	 * <p>
	 * Called after the service locator is set.
	 * It is a workaround.
	 * <p>
	 * Once the JSF bean management facility can support init method,
	 * the init method can be configured and called from the JSF implementation directly.
	 */ 
	protected void init() {
	}
}
