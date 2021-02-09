/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.math.BigDecimal;
import trackit.util.FacesUtils;

/**
 *
 * @author Owner
 */
public class AccountBean extends BaseBean {

    private Long accountId;
    private String accountDescription;
    private UserBean user;
    private BigDecimal cashBalance = new BigDecimal(0);

    public AccountBean() {
    }

    public String getAccountDescription() {
        return accountDescription;
    }

    public void setAccountDescription(String accountDescription) {
        this.accountDescription = accountDescription;
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
    
    public String getConfirmEmail() {
        return user.getConfirmEmail();
    }

    public void setConfirmEmail(String emailAddress) {
        this.user.setConfirmEmail(emailAddress);
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

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public BigDecimal getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(BigDecimal cashBalance) {
        this.cashBalance = cashBalance;
    }



    public String createAction() {
        //try {

            // Validate that the user name does not already exist
//            if (this.serviceLocator.getUserService().findUserByEmail(user.getEmail()) != null) {
//                String msg = "User Name already has an account with us";
//                FacesUtils.addErrorMessage(msg + ", please try again.");
//                this.logger.debug(msg);
//
//                return null;
//            }
    	
    	if (!user.getEmail().equals(user.getConfirmEmail())) {
    		String msg = "Email and confirm email must match.";
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


            if (!user.getPassword().equals(user.getConfirmPassword())) {
                String msg = "Passwords did not match";
                FacesUtils.addErrorMessage(msg + ", please try again.");
                this.logger.debug(msg);

                return null;
            }

            /*User us = UserBuilder.createUser(user);
            us = this.serviceLocator.getUserService().saveUser(us);
            FacesUtils.getSessionBean().setCurrentUserId(us.getId().toString());

            Account account = AccountBuilder.createAccount(this);

            account.setPrimaryUserId(us.getId());
            account.setCashBalance(cashBalance);
            
            Account savedAccount = this.serviceLocator.getAccountService().saveAccount(account);
            FacesUtils.getSessionBean().setCurrentAccountId(savedAccount.getId().toString());


            savedAccount = this.serviceLocator.getAccountService().findAccount(
                    FacesUtils.getSessionBean().getCurrentAccountId());

            //Set<User> accountUsers = new HashSet<User>();
            //accountUsers.add(us);
            //account.setUsers(accountUsers);

            us = this.serviceLocator.getUserService().findUser(
                    FacesUtils.getSessionBean().getCurrentUserId());

            if (savedAccount.getUsers() == null) {
                savedAccount.setUsers(new HashSet<User>());
            }

            savedAccount.getUsers().add(us);
            this.serviceLocator.getAccountService().saveAccount(savedAccount);

            us.setAccountId(savedAccount.getId());
            this.serviceLocator.getUserService().saveUser(us);*/

            this.serviceLocator.getAccountTransactionalService().setServiceLocator(this.serviceLocator);

            try {
                this.serviceLocator.getAccountTransactionalService().saveAccount(user, this);
            } catch (Exception ex) {
                String msg = "Account was not created due to a system error";
                FacesUtils.addErrorMessage(msg + ", please try again.");
                this.logger.debug(msg);
                return "";
            }

            return NavigationResults.ACCOUNT_CREATED;

        //} catch (EntityException ex) {
        //    Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        //}

        //return null;
    }

}
