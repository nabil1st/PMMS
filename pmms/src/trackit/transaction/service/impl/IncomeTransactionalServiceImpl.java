/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.transaction.service.impl;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import trackit.backingbeans.IncomeBean;
import trackit.backingbeans.IncomeSourceBean;
import trackit.builder.IncomeSourceBuilder;
import trackit.businessobjects.Account;
import trackit.businessobjects.BankAccount;
import trackit.businessobjects.CurrencyOnHand;
import trackit.businessobjects.CurrencyOnHandSourceType;
import trackit.businessobjects.CurrencyOnHandType;
import trackit.businessobjects.Deposit;
import trackit.businessobjects.IncomeSource;
import trackit.businessobjects.User;
import trackit.exception.EntityException;
import trackit.service.impl.DataInUseException;
import trackit.servicelocator.ServiceLocator;
import trackit.transaction.service.IncomeTransactionalService;
import trackit.util.FacesUtils;
import trackit.util.ListUtils;


/**
 *
 * @author ndaoud
 */
public class IncomeTransactionalServiceImpl implements IncomeTransactionalService {

    private HibernateTransactionManager transactionManager;
    private ServiceLocator serviceLocator;

    public void saveIncome(final IncomeBean incomeBean) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {

                Long currentUserId = Long.parseLong(
                        FacesUtils.getSessionBean().getCurrentUserId());

                CurrencyOnHand coh = new CurrencyOnHand();
                coh.setUserId(currentUserId);
                coh.setAmount(incomeBean.getAmount());
                coh.setCheckNumber(incomeBean.getCheckNumber());
                coh.setDate(incomeBean.getDate());
                if (!incomeBean.getExpenseGroupId().equals(ListUtils.NULL_CONST)) {
                	coh.setGroupId(incomeBean.getExpenseGroupId());
                }
                
                coh.setSourceId(incomeBean.getIncomeSourceId());
                
//                if (incomeBean.getCurrencyType().equals(CurrencyOnHandType.DIRECT_DEPOSIT)) {
//                	coh.setType(CurrencyOnHandType.CHECK);
//                } else {
                	coh.setType(incomeBean.getCurrencyType());
//                }
                
                coh.setSourceType(CurrencyOnHandSourceType.INCOME);
                coh = getServiceLocator().getCurrencyOnHandService().saveCurrencyOnHand(coh);

                // If income is cash, update cas balance
                if (coh.getType().equals(CurrencyOnHandType.CASH)) {
//                    Account account = getServiceLocator().getAccountService().findAccount(
//                            FacesUtils.getSessionBean().getCurrentAccountId());
//                    account.addToBalance(incomeBean.getAmount());
//                    getServiceLocator().getAccountService().saveAccount(account);
                	User currentUser = getServiceLocator().getUserService().findUser(String.valueOf(currentUserId.longValue()));
                    currentUser.addToBalance(incomeBean.getAmount());
                    getServiceLocator().getUserService().saveUser(currentUser);
                }
                
                if (incomeBean.getCurrencyType().equals(CurrencyOnHandType.DIRECT_DEPOSIT)) {
                	Deposit dep = new Deposit();
                	dep.setBankAccountId(incomeBean.getBankAccountId());
                	dep.setDate(incomeBean.getDate());
                	dep.setUserId(currentUserId);
                	Set<CurrencyOnHand> depositItems = new HashSet<CurrencyOnHand>();
                	depositItems.add(coh);
                	dep.setDepositItems(depositItems);
                	dep.setTotalCash(new BigDecimal(0));
                	
                	getServiceLocator().getDepositService().saveDeposit(dep);
                	
                	BankAccount bankAccount = getServiceLocator().getBankAccountService().findBankAccount(
                			String.valueOf(incomeBean.getBankAccountId()));
                	bankAccount.addToBalance(incomeBean.getAmount());
                	getServiceLocator().getBankAccountService().saveBankAccount(bankAccount);
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

    public void saveIncomeSource(final IncomeSourceBean incomeSource) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    IncomeSource ba = IncomeSourceBuilder.createIncomeSource(incomeSource);
                    ba = getServiceLocator().getIncomeService().saveIncomeSource(ba);
                    Account account = getServiceLocator().getAccountService().findAccount(
                            FacesUtils.getSessionBean().getCurrentAccountId());
                    
                    Set<IncomeSource> incomeSources = account.getIncomeSources();
                    if (incomeSources == null) {
                        incomeSources = new HashSet<IncomeSource>();
                    }
                    incomeSources.add(ba);
                    account.setIncomeSources(incomeSources);
                    getServiceLocator().getAccountService().saveAccount(account);
                } catch (EntityException ex) {
                    Logger.getLogger(IncomeTransactionalServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                    status.setRollbackOnly();
                }
            }
        });
    }
    
    public void deleteIncome(final Long incomeId) throws DataInUseException {
    	final CurrencyOnHand coh = getServiceLocator().getCurrencyOnHandService().findCurrencyOnHand(String.valueOf(incomeId));
    	verifyIncomeCanBeDeleted(coh);
    	
    	TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {

                Long currentUserId = Long.parseLong(
                        FacesUtils.getSessionBean().getCurrentUserId());
                
                if (coh.getType().equals(CurrencyOnHandType.DIRECT_DEPOSIT)) {
                	// Delete the deposit transaction
                	// Find deposit containing this income
                	Deposit d = getServiceLocator().getDepositService().findDepositByCurrencyOnHandId(String.valueOf(coh.getId()));
                	
                	// Take money from bank account
                	// Find the bank account
                	BankAccount bankAccount = getServiceLocator().getBankAccountService().findBankAccount(String.valueOf(d.getBankAccountId()));
                	bankAccount.subtractFromBalance(coh.getAmount());
                	
                	getServiceLocator().getDepositService().deleteDeposit(String.valueOf(d.getId()));
                	
                } else if (coh.getType().equals(CurrencyOnHandType.CASH)) {
                	User currentUser = getServiceLocator().getUserService().findUser(String.valueOf(currentUserId.longValue()));
                    currentUser.subtractFromBalance(coh.getAmount());
                    getServiceLocator().getUserService().saveUser(currentUser); 
                }
                
                getServiceLocator().getCurrencyOnHandService().deleteCurrencyOnHand(String.valueOf(coh.getId()));
                
            }
        });
    	
    }
    
    public void verifyIncomeCanBeDeleted(CurrencyOnHand income) throws DataInUseException {
    	if (income.isUsed() && !income.getType().equals(CurrencyOnHandType.DIRECT_DEPOSIT)) {
    		throw new DataInUseException("The item you're trying to delete is already in use by another transaction");
    	}
    }



}
