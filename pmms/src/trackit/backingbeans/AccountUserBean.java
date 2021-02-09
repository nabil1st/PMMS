/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.SelectItem;
import trackit.builder.UserBuilder;
import trackit.businessobjects.User;
import trackit.exception.EntityException;
import trackit.servicelocator.ServiceLocator;
import trackit.util.FacesUtils;
import trackit.util.UserPermissions;

/**
 *
 * @author Owner
 */
public class AccountUserBean extends BaseBean implements Serializable {

    private Long accountId;
    private UserBean user;
    private List<String> selectedPermissions;

    public AccountUserBean() {
        user = new UserBean();        
    }

    @Override
    public void setServiceLocator(ServiceLocator serviceLocator) {
        super.setServiceLocator(serviceLocator);
        String userIdToUse = null;        
        if (FacesUtils.getSessionBean().getSelectedUserId() != null) {
        	userIdToUse = FacesUtils.getSessionBean().getSelectedUserId();
        	FacesUtils.getSessionBean().setSelectedUserId(null);
        } else if (FacesUtils.getSessionBean().isCreateNewUser()) {
        	FacesUtils.getSessionBean().setCreateNewUser(false);
        } else {
        	userIdToUse = FacesUtils.getSessionBean().getCurrentUserId();
        }
        	
        if (userIdToUse != null) {
	        try {
	            User u = this.serviceLocator.getUserService().findUser(
	            		userIdToUse);
	            user = UserBuilder.createUserBean(u);
	            user.setConfirmPassword(user.getPassword());
	            accountId = u.getAccountId();
	            selectedPermissions = UserPermissions.parseDBValue(u.getPermissions());
	            
	        } catch (EntityException ex) {
	            Logger.getLogger(AccountUserBean.class.getName()).log(Level.SEVERE, null, ex);
	        }
        }
    }

	public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getConfirmPassword() {
        return user.getConfirmPassword();
    }

    public void setConfirmPassword(String confirmPassword) {
        this.user.setConfirmPassword(confirmPassword);
    }

    public String getEmail() {
        return user.getEmail();
    }

    public void setEmail(String emailAddress) {
        this.user.setEmail(emailAddress);
    }

    public String getFirstName() {
        return user.getFirstName();
    }

    public void setFirstName(String firstName) {
        this.user.setFirstName(firstName);
    }

    public String getLastName() {
        return user.getLastName();
    }

    public void setLastName(String lastName) {
        this.user.setLastName(lastName);
    }

    public String getPassword() {
        return user.getPassword();
    }

    public void setPassword(String password) {
        this.user.setPassword(password);
    }
    
    public BigDecimal getCashBalance() {
    	return user.getCashBalance();
    }
    
    public void setCashBalance(BigDecimal cashBalance) {
    	this.user.setCashBalance(cashBalance);
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<String> getSelectedPermissions() {
        return selectedPermissions;
    }

    public void setSelectedPermissions(List<String> selected) {
        this.selectedPermissions = selected;
    }

    public List<SelectItem> getAllPermissions() {
        List<SelectItem> dropList = new ArrayList<SelectItem>();
        for (int i=0; i<UserPermissions.PERMISSIONS.length; i++) {
            dropList.add(new SelectItem(UserPermissions.PERMISSIONS[i][0], UserPermissions.PERMISSIONS[i][1]));
        }
        return dropList;
    }


    public String createAction() {        
    	
    	if (this.serviceLocator == null) {
        	this.serviceLocator = FacesUtils.getSessionBean().getServiceLocator();
        }
        if (user.getId() == null || user.getId() == 0) {
            // Validate that the user name does not already exist
            if (this.serviceLocator.getUserService().findUserByEmail(user.getEmail()) != null) {
                String msg = "User Name already has an account with us";
                FacesUtils.addErrorMessage(msg + ", please try again.");
                this.logger.debug(msg);

                return null;
            }

            if (this.serviceLocator.getUserService().findUserByEmail(user.getEmail()) != null) {
                String msg = "Email address already has an account with us";
                FacesUtils.addErrorMessage(msg + ", please try again.");
                this.logger.debug(msg);

                return null;
            }
        }


        if (!user.getPassword().equals(user.getConfirmPassword())) {
            String msg = "Passwords did not match";
            FacesUtils.addErrorMessage(msg + ", please try again.");
            this.logger.debug(msg);

            return null;
        }

        
        
        this.serviceLocator.getAccountTransactionalService().setServiceLocator(serviceLocator);
        try {
            this.serviceLocator.getAccountTransactionalService().saveAccountUser(this);
        } catch (Exception ex) {
            String msg = "Account user was not created due to a system error";
            FacesUtils.addErrorMessage(msg + ", please try again.");
            this.logger.debug(msg);
            return "";
        }

        return NavigationResults.ACCOUNT_USER_CREATED;


    }

    public String cancelAction() {
        //clearAllFields();

        if (FacesUtils.getSessionBean().getFollowOnAction() != null) {
            String followOnAction = FacesUtils.getSessionBean().getFollowOnAction();
            FacesUtils.getSessionBean().setFollowOnAction(null);
            return followOnAction;
        }

        return NavigationResults.CREATE_USER_CANCELLED;
    }
    
    public boolean isNewUser() {
    	return user.getId() == null;
    }
    
    public boolean isAllowedToViewUserPermissions() {
    	
    	// Should be able to set permissions for a new user
    	if (user.getId() == null) {
    		return true;
    	}
    	
    	
    	
    	String currentUserId = FacesUtils.getSessionBean().getCurrentUserId();
    	if (this.serviceLocator == null) {
            this.serviceLocator = FacesUtils.getSessionBean().getServiceLocator();
        }
    	
    	User currentUser = this.serviceLocator.getUserService().findUser(currentUserId);
    	
    	// Users should not be able to modify their own permissions. 
    	if (user.getId()== Long.parseLong(currentUserId)) {
    		return false;
    	}
    	
    	
    	List<String> permissions = UserPermissions.parseDBValue(currentUser.getPermissions());
    	return permissions.contains("ROLE_CAN_VIEW_USER_PERMISSIONS");
    	
    	
    }

}
