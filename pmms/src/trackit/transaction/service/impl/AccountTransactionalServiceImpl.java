/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.transaction.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import trackit.backingbeans.AccountBean;
import trackit.backingbeans.AccountUserBean;
import trackit.backingbeans.UserBean;
import trackit.builder.AccountBuilder;
import trackit.builder.UserBuilder;
import trackit.businessobjects.Account;
import trackit.businessobjects.User;
import trackit.exception.EntityException;
import trackit.servicelocator.ServiceLocator;
import trackit.transaction.service.AccountTransactionalService;
import trackit.util.FacesUtils;
import trackit.util.UserPermissions;

/**
 *
 * @author ndaoud
 */
public class AccountTransactionalServiceImpl implements AccountTransactionalService {

    private HibernateTransactionManager transactionManager;
    private ServiceLocator serviceLocator;

    public void saveAccount(final UserBean userBean, final AccountBean accountBean) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    User us = UserBuilder.createUser(userBean);
                    us.setPermissions(UserPermissions.buildDBValue(UserPermissions.getAllPermissions()));
                    us.setMainUser(true);
                    us = getServiceLocator().getUserService().saveUser(us);
                    FacesUtils.getSessionBean().setCurrentUserId(us.getId().toString());
                    Account account = AccountBuilder.createAccount(accountBean);
                    account.setPrimaryUserId(us.getId());
                    account.setCashBalance(accountBean.getCashBalance());
                    Account savedAccount = getServiceLocator().getAccountService().saveAccount(account);
                    FacesUtils.getSessionBean().setCurrentAccountId(savedAccount.getId().toString());
                    savedAccount = getServiceLocator().getAccountService().findAccount(FacesUtils.getSessionBean().getCurrentAccountId());
                    //Set<User> accountUsers = new HashSet<User>();
                    //accountUsers.add(us);
                    //account.setUsers(accountUsers);
                    us = getServiceLocator().getUserService().findUser(FacesUtils.getSessionBean().getCurrentUserId());
                    if (savedAccount.getUsers() == null) {
                        savedAccount.setUsers(new HashSet<User>());
                    }
                    savedAccount.getUsers().add(us);
                    getServiceLocator().getAccountService().saveAccount(savedAccount);
                    us.setAccountId(savedAccount.getId());
                    getServiceLocator().getUserService().saveUser(us);
                } catch (EntityException ex) {
                    Logger.getLogger(AccountTransactionalServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                    status.setRollbackOnly();
                }


                
            }
        });
    }

    public HibernateTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(HibernateTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public ServiceLocator getServiceLocator() {
        return serviceLocator;
    }

    public void setServiceLocator(ServiceLocator locator) {
        this.serviceLocator = locator;
    }

    public void saveAccountUser(final AccountUserBean accountUser) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                	boolean newUser = false;
                	User theUser = null;
                	if (accountUser.getUser().getId() != null && accountUser.getUser().getId() != 0) {
                		theUser = getServiceLocator().getUserService().findUser(String.valueOf(accountUser.getUser().getId()));
                	}
                	
                	if (theUser != null) {
                		theUser.setCashBalance(accountUser.getCashBalance());
                		theUser.setEmail(accountUser.getEmail());
                		theUser.setFirstName(accountUser.getFirstName());
                		theUser.setLastName(accountUser.getLastName());
                		theUser.setPassword(accountUser.getPassword());                		
                	} else {
                		newUser = true;
                		theUser = UserBuilder.createUser(accountUser.getUser());                		
                		theUser.setAccountId(Long.parseLong(FacesUtils.getSessionBean().getCurrentAccountId()));
                	}
                	
                	theUser.setPermissions(buildPermissionsStr(accountUser.getSelectedPermissions()));                	
                	theUser = getServiceLocator().getUserService().saveUser(theUser);
                	
                	if (newUser) {
	                    Account savedAccount = getServiceLocator().getAccountService().findAccount(
	                            FacesUtils.getSessionBean().getCurrentAccountId());
	                    if (savedAccount.getUsers() == null) {
	                        savedAccount.setUsers(new HashSet<User>());
	                    }                    
	                    savedAccount.getUsers().add(theUser);
	                    getServiceLocator().getAccountService().saveAccount(savedAccount);
                	}
                } catch (EntityException ex) {
                    Logger.getLogger(AccountTransactionalServiceImpl.class.getName()).log(
                            Level.SEVERE, null, ex);
                    status.setRollbackOnly();
                }
            }
        });
    }

    private static String buildPermissionsStr(List<String> permissions) {
        return UserPermissions.buildDBValue(permissions);
    }



}
