/*
 * JCatalog Project
 */
package trackit.backingbeans;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import trackit.builder.UserBuilder;
import trackit.businessobjects.User;
import trackit.exception.EntityException;
import trackit.exception.UsernameNotExistException;
import trackit.util.FacesUtils;

/** 
 * User backing bean.
 * 
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public class UserBean extends BaseBean implements Serializable {

    private Long id;
    private String email;
    private String confirmEmail;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;
    private BigDecimal cashBalance = new BigDecimal(0);
    private Boolean mainUser = false;

    private Long accountId;
    
    //whether or not the user is logged in
    private boolean loggedIn;

    private String permissions;

    /**
     * Default constructor.
     */
    public UserBean() {
        this.clear();
        this.logger.debug("UserBean is created");
    }

    /**
     * Backing bean action to login a user.
     * 
     * @return the navigation result
     */
    public String loginAction() throws IOException, ServletException {
//        try {
//            User user = this.serviceLocator.getUserService().login(
//                    this.email, this.password);
//
//            if (user != null) {
//                this.loggedIn = true;
//
//                FacesUtils.getSessionBean().setCurrentUserId(
//                        String.valueOf(user.getId()));
//
//                FacesUtils.getSessionBean().setCurrentAccountId(
//                        String.valueOf(user.getAccountId()));
//
//                if (user.getEmail().equalsIgnoreCase("admin")) {
//                    return NavigationResults.ADMIN_SUCCESS;
//                }
//
//                return NavigationResults.SUCCESS;
//            } else {
//                this.loggedIn = false;
//
//                String msg = "Incorrect password ";
//                FacesUtils.addErrorMessage(msg + ", please try again.");
//                this.logger.debug(msg);
//
//                return NavigationResults.RETRY;
//            }
//        } catch (UsernameNotExistException ue) {
//            String msg = "Non-existing username ";
//            this.logger.info(msg);
//            FacesUtils.addErrorMessage(msg + ", please try again.");
//
//            return NavigationResults.RETRY;
//        } catch (Exception e) {
//            this.logger.error("Could not log in user.", e);
//            FacesUtils.addInfoMessage("Could not log in user: Internal Error");
//
//            return NavigationResults.FAILURE;
//        }

        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

        //RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
        //         .getRequestDispatcher("/j_spring_security_check");

        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
                 .getRequestDispatcher("/j_spring_security_check?j_username=" +
                this.email + "&j_password=" + this.password);

        dispatcher.forward((ServletRequest) context.getRequest(),
                (ServletResponse) context.getResponse());

        FacesContext.getCurrentInstance().responseComplete();
        // It's OK to return null here because Faces is just going to exit.
        return null;


    }

    public void login(ActionEvent event) {
        //loginAction();

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(
                "/spring-authentication/j_spring_security_check?j_username=" +
                this.email + "&j_password=" + this.password);
        } catch (IOException ex) {
            this.logger.error("Could not log in user.", ex);
            FacesUtils.addInfoMessage("Could not log in user: Internal Error");
        }
    }


    /**
     * The backing bean action to logout a user.
     * 
     * @return the navigation result
     */
    public String logoutAction() {
        this.clear();
        FacesUtils.getSessionBean().setCurrentAccountId(null);
        FacesUtils.getSessionBean().setCurrentUserId(null);
        FacesUtils.getSession().invalidate();
        this.logger.debug("Logout successfully.");

        return NavigationResults.LOGOUT_SUCCESS;
    }

    public boolean getLoggedIn() {
        return this.loggedIn;
    }

    public void setLoggedIn(boolean newLoggedIn) {
        this.loggedIn = newLoggedIn;
    }

    public String getDummyVariable() {
        return null;
    }

    private void clear() {
        this.email = null;
        this.password = null;
        this.loggedIn = false;
    }

    public String createAction() {
        try {
        	
        	if (!email.equals(confirmEmail)) {
        		String msg = "Email and confirm email must match.";
                FacesUtils.addErrorMessage(msg + ", please try again.");
                this.logger.debug(msg);

                return null;
        	}
            
            // Validate that the user name does not already exist
            if (this.serviceLocator.getUserService().findUserByEmail(email) != null) {
                String msg = "User Name already exists";
                FacesUtils.addErrorMessage(msg + ", please try again.");
                this.logger.debug(msg);

                return null;
            }
            
            
            if (!password.equals(confirmPassword)) {
                String msg = "Passwords did not match";
                FacesUtils.addErrorMessage(msg + ", please try again.");
                this.logger.debug(msg);

                return null;
            }
            
            User user = UserBuilder.createUser(this);
            this.serviceLocator.getUserService().saveUser(user);
            FacesUtils.getSessionBean().setCurrentUserId(user.getId().toString());
            FacesUtils.getSessionBean().setCurrentAccountId(user.getAccountId().toString());
            return NavigationResults.USER_CREATED;
            
        } catch (EntityException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return lastName + ", " + firstName;
    }

	public BigDecimal getCashBalance() {
		return cashBalance;
	}

	public void setCashBalance(BigDecimal cashBalance) {
		this.cashBalance = cashBalance;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public Boolean getMainUser() {
		return mainUser;
	}

	public void setMainUser(Boolean mainUser) {
		this.mainUser = mainUser;
	}

	public String getConfirmEmail() {
		return confirmEmail;
	}

	public void setConfirmEmail(String confirmEmail) {
		this.confirmEmail = confirmEmail;
	}    
	
	
    
}
