/*
 * JCatalog Project
 */
package trackit.builder;

import org.apache.commons.beanutils.BeanUtils;
//
import trackit.backingbeans.UserBean;
import trackit.businessobjects.User;
import trackit.exception.EntityException;

/**
 * The builder class for <code>UserBean</code>.
 * <p>
 * The backing beans are used view objects in the presentation tier,
 * and the business objects are used in the business logic tier.
 * This builder class is used to convert in between the backing beans and business objects.
 * <p>
 * Commons BeanUtils is used.
 * 
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 * @see <a href="http://jakarta.apache.org/commons/beanutils/">Commons BeanUtils</a>
 */
public class UserBuilder {
	/**
	 * Create a new <code>UserBean</code> based on a <code>User</code> business object.
	 * 
	 * @param user the user business object
	 * @return the new user backing bean
	 * @throws CatalogException
	 */
	public static UserBean createUserBean(User user) throws EntityException {
		UserBean userBean = new UserBean();
		
		try {
			BeanUtils.copyProperties(userBean, user);
		} catch (Exception e) {
			throw new EntityException("Could not build UserBean " + e.toString(), e);
		}
		
		return userBean;
	}
        
        public static User createUser(UserBean userBean) throws EntityException {
            User user = new User();
            
            try {
                BeanUtils.copyProperties(user, userBean);
            } catch (Exception e) {
                throw new EntityException("Could not build UserBean " + e.toString(), e);
            }
            
            return user;
        }
}