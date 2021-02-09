/*
 * JCatalog Project
 */
package trackit.backingbeans;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
//
import javax.faces.model.SelectItem;
import javax.faces.FacesException;
//
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//

/**
 * The managed bean with application scope. 
 * <p>
 * It is used as an application scope cache.
 * In JSF, the properties are set by bean management facility.
 * 
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public class ApplicationBean extends BaseBean {
	//the logger for this class
	private Log logger = LogFactory.getLog(this.getClass());
	
	//list of SelectItems for all categories
	private List categorySelectItems;
	
	//the number of products per page
	private int productsPerPage;
	
	//the uri for product image directory
	private static String productImageDirUri;
	
	//the uri for image upload result page
	private static String imageUploadResultPage;

	/**
	 * Default Constructor.
	 * 
	 * @throws FacesException If internal error occurs while retrieves categories
	 */
	public ApplicationBean() {
		this.categorySelectItems = new ArrayList();
		this.logger.debug("ApplicationBean is created");
	}
	
	/**
	 * Initializes the ApplicationBean.
	 * <p>
	 * load all the categories.
	 * 
	 * @see BaseBean#init()
	 */
	protected void init() {
		try {
			this.logger.debug("Initializes ApplicationBean");
						
			//List list = this.serviceLocator.getCatalogService().getAllCategories();
			
			//for (int i=0; i<list.size(); i++) {
			//	Category c = (Category)list.get(i);
			//	this.categorySelectItems.add(new SelectItem(c.getId(), c.getName(), c.getDescription()));
			//}
		} catch (Exception e) {
			String msg = "Could not initialize ApplicationBean " + e.toString();
			this.logger.error(msg);
			throw new FacesException(msg);
		}
		
		this.logger.debug("Application bean is initialized");
	}
	
	public List getCategorySelectItems() {
		return this.categorySelectItems;
	}
	
	public int getProductsPerPage() {
		return this.productsPerPage;
	}
	
	public void setProductsPerPage(int newProductsPerPage) {
		this.productsPerPage = newProductsPerPage;
	}
	
	public static String getProductImageDirUri() {
		return productImageDirUri;
	}
	
	public void setProductImageDirUri(String newProductImageDirUri) {
		productImageDirUri = newProductImageDirUri;
		this.logger.debug("productImageUri is set with value of " + newProductImageDirUri);
	}
	
	public static String getImageUploadResultPage() {
		return imageUploadResultPage;
	}
	
	public void setImageUploadResultPage(String newImageUploadResultPage) {
		imageUploadResultPage = newImageUploadResultPage;
		this.logger.debug("imageUploadResultPage is set with value of " + newImageUploadResultPage);
	}
	
	public String getDummyVariable() {
		return null;
	}
	
	/**
	 * Get the category name by category id.
	 * 
	 * @param id the category id
	 * @return the category name associated with the category id
	 */
	public String getCategoryName(String id) {
		if (id != null && this.categorySelectItems != null) {
			Iterator ite = this.categorySelectItems.iterator();
			
			while(ite.hasNext()) {
				SelectItem i = (SelectItem)ite.next();
				if (id.equals(i.getValue())) {
					return i.getLabel();
				}
			}
		}
		
		return null;
	}
}
