/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.transaction.service.impl;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import trackit.backingbeans.DepositBean;
import trackit.builder.DepositBuilder;
import trackit.businessobjects.Account;
import trackit.businessobjects.BankAccount;
import trackit.businessobjects.CurrencyOnHand;
import trackit.businessobjects.Deposit;
import trackit.businessobjects.User;
import trackit.exception.EntityException;
import trackit.servicelocator.ServiceLocator;
import trackit.transaction.service.DepositTransactionalService;
import trackit.util.FacesUtils;

/**
 *
 * @author ndaoud
 */
public class DepositTransactionalServiceImpl implements DepositTransactionalService {

    private HibernateTransactionManager transactionManager;
    private ServiceLocator serviceLocator;

    public void saveDeposit(final DepositBean depositBean) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    Deposit deposit = DepositBuilder.createDeposit(depositBean);
                    Long currentUserId = Long.parseLong( FacesUtils.getSessionBean().getCurrentUserId());
                    deposit.setUserId(currentUserId);

                    BigDecimal depositTotal = new BigDecimal(0);

                    deposit = getServiceLocator().getDepositService().saveDeposit(deposit);

                    depositTotal = depositTotal.add(deposit.getTotalCash());

                    Set<CurrencyOnHand> depositItems = new HashSet<CurrencyOnHand>();
                    for (String checkId : depositBean.getDepositCheckIds()) {
                        CurrencyOnHand income = findCurrencyOnHand(Long.parseLong(checkId));
                        if (income != null) {
                            income.setUsed(true);
                            depositItems.add(income);
                            depositTotal = depositTotal.add(income.getAmount());
                            getServiceLocator().getCurrencyOnHandService().saveCurrencyOnHand(income);
                        }
                    }

                    if (depositItems.size() > 0) {
                        deposit.setDepositItems(depositItems);
                        getServiceLocator().getDepositService().saveDeposit(deposit);
                    }

                    BankAccount bankAccount = getServiceLocator().getBankAccountService().findBankAccount(
                            depositBean.getBankAccountId().toString());
                    bankAccount.addToBalance(depositTotal);
                    getServiceLocator().getBankAccountService().saveBankAccount(bankAccount);

                    if (depositBean.getTotalCash() != null && depositBean.getTotalCash().doubleValue() > 0) {
//                        Account account = getServiceLocator().getAccountService().findAccount(
//                                FacesUtils.getSessionBean().getCurrentAccountId());
//                        account.subtractFromBalance(depositBean.getTotalCash());
//                        getServiceLocator().getAccountService().saveAccount(account);
                    	
                    	User currentUser = getServiceLocator().getUserService().findUser(String.valueOf(currentUserId.longValue()));
                        currentUser.subtractFromBalance(depositBean.getTotalCash());
                        getServiceLocator().getUserService().saveUser(currentUser);
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

    private CurrencyOnHand findCurrencyOnHand(Long checkId) {
        List<CurrencyOnHand> undepositedChecksList = getAllUndepositedChecks();
        for (CurrencyOnHand income : undepositedChecksList) {
            if (income.getId().equals(checkId)) {
                return income;
            }
        }

        return null;
    }

    private List<CurrencyOnHand> getAllUndepositedChecks() {
        return this.serviceLocator.getCurrencyOnHandService().getAllUnusedCurrenciesOnHandForAccount(
                    FacesUtils.getSessionBean().getCurrentAccountId());
    }



}
