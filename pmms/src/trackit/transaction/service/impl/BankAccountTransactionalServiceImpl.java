/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.transaction.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import trackit.backingbeans.BankAccountBean;
import trackit.backingbeans.BankAccountTransactionBean;
import trackit.backingbeans.BankAccountWithdrawlBean;
import trackit.builder.BankAccountBuilder;
import trackit.businessobjects.Account;
import trackit.businessobjects.BankAccount;
import trackit.businessobjects.BankAccountTransaction;
import trackit.businessobjects.BankTransactionType;
import trackit.businessobjects.BankTransactionType;
import trackit.businessobjects.Payee;
import trackit.businessobjects.User;
import trackit.exception.EntityException;
import trackit.service.impl.DataInUseException;
import trackit.servicelocator.ServiceLocator;
import trackit.transaction.service.BankAccountTransactionalService;
import trackit.util.FacesUtils;

/**
 *
 * @author ndaoud
 */
public class BankAccountTransactionalServiceImpl implements BankAccountTransactionalService {

    private HibernateTransactionManager transactionManager;
    private ServiceLocator serviceLocator;

    public void saveBankAccount(final BankAccountBean bankAccountBean) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
            	if (bankAccountBean.isEditMode()) {
            		BankAccount original = getServiceLocator().getBankAccountService().findBankAccount(
            				String.valueOf(bankAccountBean.getId()));
            		original.setDescription(bankAccountBean.getDescription());
            		original.setAccountTypeId(bankAccountBean.getAccountTypeId());
            		
            		
            		
            		// What about the opening balance?
            		BankAccountTransaction openingBalanceXAction = getServiceLocator().getBankAccountService().
            			findBankAccountOpeningBalanceTransaction(String.valueOf(bankAccountBean.getId()));
            		
            		if (openingBalanceXAction != null) {
            			if (bankAccountBean.getCurrentBalance().doubleValue() != openingBalanceXAction.getAmount().doubleValue()) {
            				if (bankAccountBean.getCurrentBalance().doubleValue() == 0) {
            					getServiceLocator().getBankAccountService().deleteAllOpeningBalanceTransactions(String.valueOf(bankAccountBean.getId()));
            					original.setBalance(original.getBalance().subtract(openingBalanceXAction.getAmount()));
            				} else {
            					BigDecimal diff = bankAccountBean.getCurrentBalance().subtract(openingBalanceXAction.getAmount());
            					original.setBalance(original.getBalance().add(diff));
            					openingBalanceXAction.setAmount(bankAccountBean.getCurrentBalance());
            					getServiceLocator().getBankAccountService().saveBankAccountTransaction(openingBalanceXAction);
            				}            				
            			}
            		} else {
            			if (bankAccountBean.getCurrentBalance().doubleValue() != 0) {
            				BankAccountTransaction tx = new BankAccountTransaction();
	                        tx.setAmount(bankAccountBean.getCurrentBalance());
	                        tx.setDate(new Date());
	                        tx.setTransactionType(BankTransactionType.OPENING_BALANCE);
	                        tx.setBankAccountId(original.getId());
	                        tx.setDescription("Opening Blance");
	                        Long currentUserId = Long.parseLong(FacesUtils.getSessionBean().getCurrentUserId());
	                        tx.setUserId(currentUserId);
	                        tx.setDate(original.getCreatedDate());
	                        tx = getServiceLocator().getBankAccountService().saveBankAccountTransaction(tx);
	                        	                        
	                        original.setBalance(original.getBalance().add(tx.getAmount()));
            			}
            		}
            		
            		original.setModifiedDate(new Date());
            		original.setModifiedBy(Long.parseLong( FacesUtils.getSessionBean().getCurrentUserId()));
            		
            		getServiceLocator().getBankAccountService().saveBankAccount(original);
            	} else {
	                try {
	                    BankAccount ba = BankAccountBuilder.createBankAccount(bankAccountBean);
	                    ba.setBalance(bankAccountBean.getCurrentBalance());
	                    ba.setCreatedDate(new Date());
	                    ba.setCreatedBy(Long.parseLong( FacesUtils.getSessionBean().getCurrentUserId()));
	                    
	                    ba = getServiceLocator().getBankAccountService().saveBankAccount(ba);
	                    
	                    if (bankAccountBean.getCurrentBalance().doubleValue() > 0) {
	                        BankAccountTransaction tx = new BankAccountTransaction();
	                        tx.setAmount(bankAccountBean.getCurrentBalance());
	                        tx.setDate(new Date());
	                        tx.setTransactionType(BankTransactionType.OPENING_BALANCE);
	                        tx.setBankAccountId(ba.getId());
	                        tx.setDescription("Opening Blance");
	                        Long currentUserId = Long.parseLong(FacesUtils.getSessionBean().getCurrentUserId());
	                        tx.setUserId(currentUserId);
	                        tx = getServiceLocator().getBankAccountService().saveBankAccountTransaction(tx);
	                    }
	                    Account account = getServiceLocator().getAccountService().findAccount(FacesUtils.getSessionBean().getCurrentAccountId());
	                    Set<BankAccount> bankAccounts = account.getBankAccounts();
	                    if (bankAccounts == null) {
	                        bankAccounts = new HashSet<BankAccount>();
	                    }
	                    bankAccounts.add(ba);
	                    account.setBankAccounts(bankAccounts);
	                    getServiceLocator().getAccountService().saveAccount(account);                    
	                } catch (EntityException ex) {
	                    Logger.getLogger(BankAccountTransactionalServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
	                    status.setRollbackOnly();
	                }
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

    public void saveBankTransaction(final BankAccountTransactionBean bean) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
                BankAccount ba = getServiceLocator().getBankAccountService().
                        findBankAccount(bean.getBankAccountId().toString());


                BankAccountTransaction xaction = new BankAccountTransaction();
                xaction.setAmount(bean.getAmount());
                xaction.setBankAccountId(bean.getBankAccountId());
                xaction.setDate(bean.getDate());
                xaction.setTransactionType(bean.getTransactionType());

                Long currentUserId = Long.parseLong( FacesUtils.getSessionBean().getCurrentUserId());
                xaction.setUserId(currentUserId);

                getServiceLocator().getBankAccountService().saveBankAccountTransaction(xaction);

                if (bean.getTransactionType().equals(BankTransactionType.BANK_FEE)) {
                    ba.subtractFromBalance(bean.getAmount());
                    getServiceLocator().getBankAccountService().saveBankAccount(ba);
                } else if (bean.getTransactionType().equals(BankTransactionType.INTEREST)) {
                    ba.addToBalance(bean.getAmount());
                    getServiceLocator().getBankAccountService().saveBankAccount(ba);
                }
            }
        });
    }

    public void saveBankWithdrawl(final BankAccountWithdrawlBean bean) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
                BankAccount ba = getServiceLocator().getBankAccountService().
                        findBankAccount(bean.getBankAccountId().toString());


                BankAccountTransaction xaction = new BankAccountTransaction();
                xaction.setAmount(bean.getAmount());
                xaction.setBankAccountId(bean.getBankAccountId());
                xaction.setDate(bean.getDate());
                xaction.setCheckNumber(bean.getCheckNumber());
                if (bean.getCheckNumber() != null && bean.getCheckNumber().trim().length() > 0) {
                    xaction.setTransactionType(BankTransactionType.CHECK_WITHDRAWL);
                } else {
                    xaction.setTransactionType(BankTransactionType.ATM_WITHDRAWL);
                }

                Long currentUserId = Long.parseLong( FacesUtils.getSessionBean().getCurrentUserId());
                xaction.setUserId(currentUserId);

                getServiceLocator().getBankAccountService().saveBankAccountTransaction(xaction);

                ba.subtractFromBalance(bean.getAmount());
                getServiceLocator().getBankAccountService().saveBankAccount(ba);

                //Account account = getServiceLocator().getAccountService().findAccount(
                //        FacesUtils.getSessionBean().getCurrentAccountId());
                
                User currentUser = getServiceLocator().getUserService().findUser(String.valueOf(currentUserId.longValue()));
                currentUser.addToBalance(bean.getAmount());
                getServiceLocator().getUserService().saveUser(currentUser);
            }
        });
    }

	@Override
	public void deleteBankAccount(final String id) throws DataInUseException {
		
		verifyBankAccountCanBeDeleted(id);
		
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
            	String currentAccountId = FacesUtils.getSessionBean().getCurrentAccountId();
            	Account account = getServiceLocator().getAccountService().findAccount(currentAccountId);
            	
            	Set<BankAccount> set = account.getBankAccounts();
            	for (BankAccount p : set) {
            		if (p.getId() == Long.parseLong(id)) {
            			set.remove(p);
            			getServiceLocator().getAccountService().saveAccount(account);
            			break;
            		}
            	}
            	
            	// Need to delete any opening balance transactions related to this bank account
            	getServiceLocator().getBankAccountService().deleteAllOpeningBalanceTransactions(id);
            	
                getServiceLocator().getBankAccountService().deleteBankAccount(id);
            }
        });
	}
	
	private void verifyBankAccountCanBeDeleted(String id) throws DataInUseException {
		// Must not be used by any expense
		if (getServiceLocator().getBankAccountService().getNumberOfExpensesWithBankAccount(id) > 0) {
			throw new DataInUseException("One or more Expenses are already tied to this bank account.");
		}
		
		// Must not be used by any loan
		if (getServiceLocator().getBankAccountService().getNumberOfLoansWithBankAccount(id) > 0) {
			throw new DataInUseException("One or more Loans are already tied to this bank account.");
		}
		
		// Must not be used by any Deposit
		if (getServiceLocator().getBankAccountService().getNumberOfDepositsWithBankAccount(id) > 0) {
			throw new DataInUseException("One or more Deposits are already tied to this bank account.");
		}
		
		// Must not be used by any BankAccountTransaction except for opening balance transactions
		if (getServiceLocator().getBankAccountService().getNumberOfTransactionsWithBankAccount(id) > 0) {
			throw new DataInUseException("One or more Non-Open Balance Transactions are already tied to this bank account.");
		}
		
	}

}
