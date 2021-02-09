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
import trackit.backingbeans.CashTransferBean;
import trackit.backingbeans.UserBean;
import trackit.builder.AccountBuilder;
import trackit.builder.CashTransferBuilder;
import trackit.builder.UserBuilder;
import trackit.businessobjects.Account;
import trackit.businessobjects.CashTransfer;
import trackit.businessobjects.User;
import trackit.exception.EntityException;
import trackit.servicelocator.ServiceLocator;
import trackit.transaction.service.AccountTransactionalService;
import trackit.transaction.service.CashTransactionalService;
import trackit.util.FacesUtils;
import trackit.util.UserPermissions;

/**
 *
 * @author ndaoud
 */
public class CashTransactionalServiceImpl implements CashTransactionalService {

    private HibernateTransactionManager transactionManager;
    private ServiceLocator serviceLocator;

    public void saveCashTransfer(final CashTransferBean bean) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    CashTransfer transfer = CashTransferBuilder.createCashTransfer(bean);
                    transfer = getServiceLocator().getCashService().saveCashTransfer(transfer);
                    
                    // Reduce the cash amount in the source account
                    if (transfer.getSourceId() == null) { // This is the main account
                    	Account account = getServiceLocator().getAccountService().findAccount(FacesUtils.getSessionBean().getCurrentAccountId());
                    	account.subtractFromBalance(transfer.getAmount());
                    } else {
                    	User user = getServiceLocator().getUserService().findUser(String.valueOf(transfer.getSourceId().longValue()));
                    	user.subtractFromBalance(transfer.getAmount());
                    }
                    
                    if (transfer.getDestinationId() == null) { // This is the main account
                    	Account account = getServiceLocator().getAccountService().findAccount(FacesUtils.getSessionBean().getCurrentAccountId());
                    	account.addToBalance(transfer.getAmount());
                    } else {
                    	User user = getServiceLocator().getUserService().findUser(String.valueOf(transfer.getDestinationId().longValue()));
                    	user.addToBalance(transfer.getAmount());
                    }
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
                    User us = UserBuilder.createUser(accountUser.getUser());
                    us.setPermissions(buildPermissionsStr(accountUser.getSelectedPermissions()));
                    us.setAccountId(Long.parseLong(FacesUtils.getSessionBean().getCurrentAccountId()));
                    us = getServiceLocator().getUserService().saveUser(us);
                    Account savedAccount = getServiceLocator().getAccountService().findAccount(
                            FacesUtils.getSessionBean().getCurrentAccountId());
                    if (savedAccount.getUsers() == null) {
                        savedAccount.setUsers(new HashSet<User>());
                    }
                    savedAccount.getUsers().add(us);
                    getServiceLocator().getAccountService().saveAccount(savedAccount);
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
