/*
 * JCatalog Project
 */
package trackit.service.impl;

import java.util.List;
//
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
//
import trackit.businessobjects.User;
import trackit.dao.UserDao;
import trackit.exception.UsernameNotExistException;
import trackit.service.UserService;


/**
 * The implementation of the <code>UserService</code>.
 * </p>
 * Spring Framework is used to manage this service bean.
 * Since this class is not dependend on Spring API, it can be used outside the Spring IOC container.
 * 
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 * @see UserService
 */

//@Service("samplePlanFlexService")
//@RemotingDestination
public class UserServiceImpl implements UserService {
	//the logger for this class
	private Log logger = LogFactory.getLog(this.getClass());
	
	//the smtp server host address
	//private String smtpHost;
	
	//the default sender email address
	//private String defaultSenderAddress;
	
	//the default sender name
	//private String defaultSenderName;
	
	//the receiver email addresses
	//private List receiverAddresses;
	
	//the UserDao used
	private UserDao userDao;
	
	/*public void setSmtpHost(String host) {
		this.smtpHost = host;
	}
	
	public void setDefaultSenderAddress(String newDefaultSenderAddress) {
		this.defaultSenderAddress = newDefaultSenderAddress;
	}
	
	public void setDefaultSenderName(String newDefaultSenderName) {
		this.defaultSenderName = newDefaultSenderName;
	}
	
	public void setReceiverAddresses(List newDefaultReceiverAddresses) {
		this.receiverAddresses = newDefaultReceiverAddresses;
	}*/
	
	public void setUserDao(UserDao newUserDao) {
		this.userDao = newUserDao;
	}
	
	/**
	 * @see UserService#login(String, String)
	 */
	public User login(String email, String password) throws UsernameNotExistException {
		try {
                        
			List users = this.userDao.findUserByEmail(email);
			
			if (users != null && users.size() > 0) {
                            User user = (User) users.get(0);
                            if (!user.getPassword().equals(password)) {
                                    //user = null;
                                return null;
                            }
			}
			
			return (User) users.get(0);
		} catch (HibernateObjectRetrievalFailureException e) {
			//username does not exist
			throw new UsernameNotExistException(email);
		} catch (Exception e) {
			this.logger.error("Could not login", e);
			throw new UsernameNotExistException("Could not login");
		}
                
                
	}

    public User saveUser(User user) {
        User savedUser = userDao.saveUser(user);
        return savedUser;
    }

    public User findUser(String userId) {
        return userDao.getUser(userId);
    }

    public User findUserByEmail(String email) {
        try {
                List list = this.userDao.findUserByEmail(email);
                if (list.size() == 0) {
                    return null;
                }

                return (User) list.get(0);
        } catch (HibernateObjectRetrievalFailureException e) {
                return null;
        }
    }

    public List<User> getAllUsersForAccount(String currentAccountId) {
        return userDao.getAllUsersForAccount(currentAccountId);
    }
    
    
	
	/**
	 * @see UserService#sendEmail(String, String, String, String)
	 */
	/*public void sendEmail(String senderAddress, String senderName, String sub, String msg) throws CatalogException {
		if (senderAddress == null || senderAddress.trim().equals("")) {
			senderAddress = this.defaultSenderAddress;
		}
		
		if (senderName == null || senderName.trim().equals("")) {
			senderName = this.defaultSenderName;
		}
		
		EmailUtil.sendEmail(this.smtpHost, senderAddress, senderName, this.receiverAddresses, sub, msg);
	}*/
}
