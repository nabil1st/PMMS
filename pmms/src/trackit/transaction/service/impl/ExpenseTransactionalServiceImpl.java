/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.transaction.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import trackit.backingbeans.ExpenseBean;
import trackit.backingbeans.ExpenseGroupBean;
import trackit.backingbeans.ExpenseItemBean;
import trackit.backingbeans.ExpenseTypeBean;
import trackit.backingbeans.GenericExpenseBean;
import trackit.backingbeans.ItemizedExpenseBean;
import trackit.backingbeans.PayeeBean;
import trackit.builder.ExpenseBuilder;
import trackit.builder.ExpenseGroupBuilder;
import trackit.builder.ExpenseItemBuilder;
import trackit.builder.ExpenseTypeBuilder;
import trackit.builder.PayeeBuilder;
import trackit.businessobjects.Account;
import trackit.businessobjects.BankAccount;
import trackit.businessobjects.CreditCard;
import trackit.businessobjects.CurrencyOnHand;
import trackit.businessobjects.Expense;
import trackit.businessobjects.ExpenseGroup;
import trackit.businessobjects.ExpenseItem;
import trackit.businessobjects.ExpensePurpose;
import trackit.businessobjects.ExpenseType;
import trackit.businessobjects.Loan;
import trackit.businessobjects.Payee;
import trackit.businessobjects.PaymentMethod;
import trackit.businessobjects.TransactionType;
import trackit.businessobjects.User;
import trackit.exception.EntityException;
import trackit.service.impl.DataInUseException;
import trackit.servicelocator.ServiceLocator;
import trackit.transaction.service.ExpenseTransactionalService;
import trackit.util.FacesUtils;
import trackit.util.ListUtils;

/**
 *
 * @author ndaoud
 */
public class ExpenseTransactionalServiceImpl implements ExpenseTransactionalService {

    private HibernateTransactionManager transactionManager;
    private ServiceLocator serviceLocator;

    public void saveExpense(final ExpenseBean expenseBean) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
            	Long currentUserId = Long.parseLong( FacesUtils.getSessionBean().getCurrentUserId());
            	
            	if (!expenseBean.isEditMode()) {
            		try {
	                    Expense expense = ExpenseBuilder.createExpense(expenseBean);	                    
	                    expense.setUserId(currentUserId);		
	                    setExpenseAmountFromMoneyOrder(expenseBean, expense);
	                    expense.setExpensePurpose(ExpensePurpose.fromInt(expenseBean.getExpensePurposeId()));
	                    if (ListUtils.isNullSelection(expenseBean.getExpenseGroupId())) {
	                    	expense.setExpenseGroupId(null);
	                    }
	                    expense = getServiceLocator().getExpenseService().saveExpense(expense);		                    
	                    updateExpensePayment(expenseBean, currentUserId);
	                    
	                } catch (EntityException ex) {
	                    Logger.getLogger(AccountTransactionalServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
	                    status.setRollbackOnly();
	                    return;
	                }
            	} else {
            		// Find the unedited expense
            		Expense theExpense = serviceLocator.getExpenseService().getExpense(String.valueOf(expenseBean.getId()));
            		undoExpensePayment(theExpense);
            		
            		theExpense.setAmount(expenseBean.getAmount());
            		theExpense.setBankAccountId(expenseBean.getBankAccountId());
            		theExpense.setCheckNumber(expenseBean.getCheckNumber());
            		theExpense.setCreditCardId(expenseBean.getCreditCardId());
            		theExpense.setDate(expenseBean.getDate());
            		theExpense.setDescription(expenseBean.getDescription());
            		theExpense.setExpenseGroupId(expenseBean.getExpenseGroupId());
            		theExpense.setExpensePurpose(ExpensePurpose.fromInt(expenseBean.getExpensePurposeId()));
            		theExpense.setExpenseTypeId(expenseBean.getExpenseTypeId());
            		theExpense.setMoneyOrderId(expenseBean.getMoneyOrderId());
            		theExpense.setPayeeId(expenseBean.getPayeeId());
            		theExpense.setPaymentMethod(expenseBean.getPaymentMethod());
            		
            		// User ID should not change
            		theExpense = getServiceLocator().getExpenseService().saveExpense(theExpense);
            		setExpenseAmountFromMoneyOrder(expenseBean, theExpense);
                    updateExpensePayment(expenseBean, currentUserId);
            	}
            }
        });
    }
    
    private void setExpenseAmountFromMoneyOrder(ExpenseBean expenseBean, Expense expense) {
    	if (expenseBean.getPaymentMethod().equals(PaymentMethod.MONEY_ORDER)) {
            CurrencyOnHand coh = getServiceLocator().getCurrencyOnHandService().findCurrencyOnHand(
                        expenseBean.getMoneyOrderId().toString());
            if (coh != null) {
                expense.setAmount(coh.getAmount());
            }
        } else {
        	expense.setMoneyOrderId(null);
        }
    }
    
    private void updateExpensePayment(GenericExpenseBean expenseBean, Long currentUserId) {
    	CurrencyOnHand coh = null;
        if (expenseBean.getPaymentMethod().equals(PaymentMethod.MONEY_ORDER)) {
            coh = findMoneyOrder(expenseBean.getMoneyOrderId());
            if (coh != null) {
                expenseBean.setAmount(coh.getAmount());
                coh.setUsed(true);
                getServiceLocator().getCurrencyOnHandService().saveCurrencyOnHand(coh);
            }
        }


        // UPDATE BALANCES
        if (expenseBean.getPaymentMethod().equals(PaymentMethod.CHECK) ||
            expenseBean.getPaymentMethod().equals(PaymentMethod.DEBIT) ||
            expenseBean.getPaymentMethod().equals(PaymentMethod.E_PAYMENT)) {

            BankAccount bankAccount = getServiceLocator().getBankAccountService().findBankAccount(
                    expenseBean.getBankAccountId().toString());
            bankAccount.setBalance(bankAccount.getBalance().subtract(expenseBean.getAmount()));
            getServiceLocator().getBankAccountService().saveBankAccount(bankAccount);
        } else if (expenseBean.getPaymentMethod().equals(PaymentMethod.CASH)) {
        	User currentUser = getServiceLocator().getUserService().findUser(
        			String.valueOf(currentUserId.longValue()));
            currentUser.subtractFromBalance(expenseBean.getAmount());
            getServiceLocator().getUserService().saveUser(currentUser);
        } else if (expenseBean.getPaymentMethod().equals(PaymentMethod.CREDIT)) {
            CreditCard cc = getServiceLocator().getCreditCardService().findCreditCard(
                    expenseBean.getCreditCardId().toString());
            cc.addToBalance(expenseBean.getAmount());
            getServiceLocator().getCreditCardService().saveCreditCard(cc);
        }
    }
    
    public void saveItemizedExpense(final ItemizedExpenseBean expenseBean) throws DataInUseException {
    	
    	final Expense expense = expenseBean.isEditMode()?getServiceLocator().getExpenseService().getExpense(
				String.valueOf(expenseBean.getId())):null;
				
    	if (expenseBean.isEditMode() && expenseBean.isItemListModified()) {
    		verifyItemizedExpenseCanBeModified(expenseBean, expense);
    	}
    	
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
            	
            	if (expenseBean.isEditMode()) {
//            		Expense expense = getServiceLocator().getExpenseService().getExpense(
//            				String.valueOf(expenseBean.getId()));
            		
            		undoExpensePayment(expense);
            		
            		// update the primary expense fields
            		updateExpenseFields(expenseBean, expense);
            		
            		if (expenseBean.isItemListModified()) {
            			processExpenseItems(expenseBean, expense);
            		}
            		
            		updateExpensePayment(expenseBean, expense.getUserId());
            		
            		getServiceLocator().getExpenseService().saveExpense(expense);
            		

            	} else {
	                Expense expense = new Expense();
	                updateExpenseFields(expenseBean, expense);
	                
	                Long currentUserId = Long.parseLong( FacesUtils.getSessionBean().getCurrentUserId());
	                expense.setUserId(currentUserId);
	                
	                Set<ExpenseItem> expenseItems = new HashSet<ExpenseItem>();
	                for (ExpenseItem ei : expenseBean.getExpenseItems()) {
	                	ei = saveExpenseItemAndAddToList(expense,
								currentUserId, expenseItems, ei);
	                }
	                
	                expense.setExpenseItems(expenseItems);
	                expense = getServiceLocator().getExpenseService().saveExpense(expense);
	
	                updateExpensePayment(expenseBean, currentUserId);	                
            	}
            }
			
        });
    }
    
    private ExpenseItem saveExpenseItemAndAddToList(Expense expense,
			Long currentUserId, Set<ExpenseItem> expenseItems,
			ExpenseItem ei) {
		if (ei.getId() < 0) {
			ei.setId(null);
		}
		
		if (ListUtils.isNullSelection(ei.getExpenseGroupId())) {
			ei.setExpenseGroupId(null);
		}
		
		ei = serviceLocator.getExpenseService().saveExpenseItem(ei);
		
		expenseItems.add(ei);
		
		if (ei.getExpensePurpose().equals(ExpensePurpose.Loan)) {
			Loan loan = new Loan();
			BigDecimal loanAmount = ei.getAmount();
			if (ei.getTax() != null) {
				loanAmount = loanAmount.add(ei.getTax());
			}
			loan.setAmount(loanAmount);
			loan.setBorrowerId(ei.getBorrowerId());
			loan.setDate(expense.getDate());
			loan.setDescription(ei.getDescription());
			loan.setGroupId(ei.getExpenseGroupId());
			loan.setPayeeId(expense.getPayeeId());
			loan.setUserId(currentUserId);
			loan.setTransactionId(ei.getId());
			loan.setTransactionType(TransactionType.LOAN);
			
			serviceLocator.getLoanService().saveLoan(loan);
		}
		return ei;
	}
    
    private void updateExpenseFields(
			final ItemizedExpenseBean expenseBean, Expense expense) {
		expense.setAmount(expenseBean.getAmount());
		expense.setBankAccountId(expenseBean.getBankAccountId());
		expense.setCheckNumber(String.valueOf(expenseBean.getCheckNumber()));
		expense.setCreditCardId(expenseBean.getCreditCardId());
		expense.setDate(expenseBean.getDate());
		expense.setDescription("");
		expense.setExpenseGroupId(null);
		expense.setExpenseTypeId(null);
		expense.setMoneyOrderId(expenseBean.getMoneyOrderId());
		expense.setPayeeId(expenseBean.getPayeeId());
		expense.setPaymentMethod(expenseBean.getPaymentMethod());
	}
    
    private void processExpenseItems(ItemizedExpenseBean bean, Expense expense) {
    	List<ExpenseItem> toBeRemovedFromExpense = new ArrayList<ExpenseItem>();
    	
    	for (ExpenseItem ei : expense.getExpenseItems()) {
    		if (!bean.getExpenseItems().contains(ei)) {
    			toBeRemovedFromExpense.add(ei);
    			deleteExpenseItem(ei);
    		}
    	}
    	
    	expense.getExpenseItems().removeAll(toBeRemovedFromExpense);
    	
    	for (ExpenseItem ei : bean.getExpenseItems()) {
    		if (!expense.getExpenseItems().contains(ei)) {
    			saveExpenseItemAndAddToList(expense, expense.getUserId(), expense.getExpenseItems(), ei);
    		}
    	}
    }
    
    private boolean findExpenseItem(ExpenseItem item, Collection<ExpenseItem> items) {
    	for (ExpenseItem ei : items) {
    		if (ei.equals(item)) {
    			return true;
    		}
    	}
    	
    	return false;
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

    private CurrencyOnHand findMoneyOrder(Long id) {
        List<CurrencyOnHand> availableMoneyOrders =
                this.serviceLocator.getCurrencyOnHandService().
                    getAllUnusedMoneyOrdersOnHandForAccount(
                        FacesUtils.getSessionBean().getCurrentAccountId());
        for (CurrencyOnHand coh : availableMoneyOrders) {
            if (coh.getId().equals(id)) {
                return coh;
            }
        }

        return null;
    }

    public void saveExpenseGroup(final ExpenseGroupBean expenseGroup) {

        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
                
            	if (expenseGroup.isEditMode()) {
            		ExpenseGroup oldEG = getServiceLocator().getExpenseGroupService().findExpenseGroup(
            			String.valueOf(expenseGroup.getId()));
            		oldEG.setDescription(expenseGroup.getDescription());
            		getServiceLocator().getExpenseGroupService().saveExpenseGroup(oldEG);
            	} else {
            		try {
                		ExpenseGroup ba = ExpenseGroupBuilder.createExpenseGroup(expenseGroup);
                        ba = getServiceLocator().getExpenseGroupService().saveExpenseGroup(ba);
                        Account account = getServiceLocator().getAccountService().findAccount(FacesUtils.getSessionBean().getCurrentAccountId());
                        Set<ExpenseGroup> expenseGroups = account.getExpenseGroups();
                        if (expenseGroups == null) {
                            expenseGroups = new HashSet<ExpenseGroup>();
                        }
                        expenseGroups.add(ba);
                        account.setExpenseGroups(expenseGroups);
                        getServiceLocator().getAccountService().saveAccount(account);
            		} catch (EntityException ex) {
                        Logger.getLogger(ExpenseTransactionalServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                        status.setRollbackOnly();
                        return;
                    }
            	}
            }
        });
    }

    public void saveItemizedExpense(final List<ExpenseItemBean> list) {

        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
//                Object savePoint = status.createSavepoint();


                ExpenseBean expenseBean = FacesUtils.getSessionBean().getExpenseBean();
                Expense expense = null;

                try {
                    expense = ExpenseBuilder.createExpense(expenseBean);
                } catch (EntityException ex) {
                    Logger.getLogger(ExpenseTransactionalServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }

                Long currentUserId = Long.parseLong( FacesUtils.getSessionBean().getCurrentUserId());
                expense.setUserId(currentUserId);
                expense.setExpenseItems(new HashSet<ExpenseItem>());
                expense = getServiceLocator().getExpenseService().saveExpense(expense);
//                FacesUtils.getSessionBean().setCurrentExpense(expense);

//                Expense expense = FacesUtils.getSessionBean().getCurrentExpense();
                Set<ExpenseItem> expenseItems = expense.getExpenseItems();
                
                for (ExpenseItemBean b : list) {
                    if (b.getAmount().doubleValue() > 0) {
                        try {
                            ExpenseItem ei = ExpenseItemBuilder.createExpenseItem(b);
                            getServiceLocator().getExpenseService().saveExpenseItem(ei);
                            expenseItems.add(ei);                            
                        } catch (EntityException ex) {
                            Logger.getLogger(ExpenseTransactionalServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                            status.setRollbackOnly();
                            return;
                        }
                    }
                }


                getServiceLocator().getExpenseService().saveExpense(expense);
            }
        });
        
    }

    public void saveExpenseType(final ExpenseTypeBean expenseType) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    ExpenseType ba = ExpenseTypeBuilder.createExpenseType(expenseType);
                    ba = getServiceLocator().getExpenseTypeService().saveExpenseType(ba);
                    Account account = getServiceLocator().getAccountService().findAccount(FacesUtils.getSessionBean().getCurrentAccountId());
                    Set<ExpenseType> ExpenseTypes = account.getExpenseTypes();
                    if (ExpenseTypes == null) {
                        ExpenseTypes = new HashSet<ExpenseType>();
                    }                    

                    ExpenseTypes.add(ba);
                    account.setExpenseTypes(ExpenseTypes);
                    getServiceLocator().getAccountService().saveAccount(account);
                } catch (EntityException ex) {
                    Logger.getLogger(ExpenseTransactionalServiceImpl.class.getName()).log(Level.SEVERE, null, ex);

                    // roll back the transaction
                    status.setRollbackOnly();
                }
            }
        });

    }

    public void savePayee(final PayeeBean payeeBean) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
            	if (payeeBean.isEditMode()) {
            		Payee payee = getServiceLocator().getPayeeService().findPayee(String.valueOf(payeeBean.getId()));
            		payee.setDescription(payeeBean.getDescription());
            		getServiceLocator().getPayeeService().savePayee(payee);
            	} else {
	                try {
	                    Payee payee = PayeeBuilder.createPayee(payeeBean);
	                    payee = getServiceLocator().getPayeeService().savePayee(payee);
	                    Account account = getServiceLocator().getAccountService().findAccount(FacesUtils.getSessionBean().getCurrentAccountId());
	                    Set<Payee> payees = account.getPayees();
	                    if (payees == null) {
	                        payees = new HashSet<Payee>();
	                    }
	                    payees.add(payee);
	                    account.setPayees(payees);
	                    getServiceLocator().getAccountService().saveAccount(account);
	                } catch (EntityException ex) {
	                    Logger.getLogger(ExpenseTransactionalServiceImpl.class.getName()).log(
	                            Level.SEVERE, null, ex);
	                    status.setRollbackOnly();
	                }
            	}
            }
        });
    }

    public void deleteExpense(final String expenseId) throws DataInUseException {
    	final Expense expense = getServiceLocator().getExpenseService().getExpense(expenseId);
    	verifyExpenseItemsCanBeDeleted(expense);
    	
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
                undoExpensePayment(expense);
                deleteExpenseItems(expense);
                
//                CurrencyOnHand coh = null;
//                if (expense.getPaymentMethod().equals(PaymentMethod.MONEY_ORDER)) {
//                    coh = getServiceLocator().getCurrencyOnHandService().findCurrencyOnHand(
//                                expense.getMoneyOrderId().toString());
//                    if (coh != null) {
//                        coh.setUsed(false);
//                        getServiceLocator().getCurrencyOnHandService().saveCurrencyOnHand(coh);
//                    }
//                }
//                
//                for (ExpenseItem ie : expense.getExpenseItems()) {
//                	getServiceLocator().getExpenseService().deleteExpenseItem(ie);
//                	
//                	// If the expense item has an associated loan, delete the loan
//                	Loan loan = getServiceLocator().getLoanService().findExpenseLoan(String.valueOf(ie.getId()));
//                	if (loan != null) {
//                		getServiceLocator().getLoanService().deleteLoan(loan);
//                	}
//                }

                getServiceLocator().getExpenseService().deleteExpense(expense.getId().toString());


                // UPDATE BALANCES
//                if (expense.getPaymentMethod().equals(PaymentMethod.CHECK) ||
//                    expense.getPaymentMethod().equals(PaymentMethod.DEBIT) ||
//                    expense.getPaymentMethod().equals(PaymentMethod.E_PAYMENT)) {
//
//                    BankAccount bankAccount = getServiceLocator().getBankAccountService().findBankAccount(
//                            expense.getBankAccountId().toString());
//                    bankAccount.setBalance(bankAccount.getBalance().add(expense.getAmount()));
//                    getServiceLocator().getBankAccountService().saveBankAccount(bankAccount);
//                } else if (expense.getPaymentMethod().equals(PaymentMethod.CASH)) {
////                    Account account = getServiceLocator().getAccountService().findAccount(
////                            FacesUtils.getSessionBean().getCurrentAccountId());
////                    account.addToBalance(expense.getAmount());
////                    getServiceLocator().getAccountService().saveAccount(account);
//                	User user = getServiceLocator().getUserService().findUser(String.valueOf(expense.getUserId().longValue()));
//                    user.addToBalance(expense.getAmount());
//                    getServiceLocator().getUserService().saveUser(user);
//                } else if (expense.getPaymentMethod().equals(PaymentMethod.CREDIT)) {
//                    CreditCard cc = getServiceLocator().getCreditCardService().findCreditCard(
//                            expense.getCreditCardId().toString());
//                    cc.subtractFromBalance(expense.getAmount());
//                    getServiceLocator().getCreditCardService().saveCreditCard(cc);
//                }
                
            }
        });
    }
    
    private void verifyItemizedExpenseCanBeModified(ItemizedExpenseBean bean, Expense expense) throws DataInUseException {
    	for (ExpenseItem ei : expense.getExpenseItems()) {
    		// ExpenseItem is not in the bean indicates it has been removed by the user, therefore it
    		// should be deleted. Verify that it can be deleted.
    		if (!findExpenseItem(ei, bean.getExpenseItems())) {
    			verifyExpenseItemCanBeDeleted(ei);
    		}
    	}
    }
    
    private void verifyExpenseItemsCanBeDeleted(Expense expense) throws DataInUseException {
    	if (expense.getExpenseItems() != null) {
	    	for (ExpenseItem ie : expense.getExpenseItems()) {
	        	// If the expense item has an associated loan, verify that the loan can be deleted.
	        	verifyExpenseItemCanBeDeleted(ie);
	        }
    	}
    }
    
    private void verifyExpenseItemCanBeDeleted(ExpenseItem ie) throws DataInUseException {
    	verifyExpenseItemCanBeDeleted(String.valueOf(ie.getId()));
    }
    
    public void verifyExpenseItemCanBeDeleted(String expenseItemId) throws DataInUseException {
    	Loan loan = getServiceLocator().getLoanService().findExpenseLoan(expenseItemId);
    	if (loan != null) {
    		if (loan.getLoanPayments() != null && loan.getLoanPayments().size() > 0) {
    			throw new DataInUseException(
    					"The selected expense item cannot be deleted because it's tied to a loan that has active payments.");
    		}	        		
    	}
    }
    
    private void deleteExpenseItems(Expense expense) {
    	if (expense.getExpenseItems() != null) {
	    	for (ExpenseItem ei : expense.getExpenseItems()) {
	        	deleteExpenseItem(ei);
	        }
    	}
    }
    
    private void deleteExpenseItem(ExpenseItem ei) {
    	getServiceLocator().getExpenseService().deleteExpenseItem(ei);
    	
    	// If the expense item has an associated loan, delete the loan
    	Loan loan = getServiceLocator().getLoanService().findExpenseLoan(String.valueOf(ei.getId()));
    	if (loan != null) {
    		getServiceLocator().getLoanService().deleteLoan(loan);
    	}
    }
    
    private void undoExpensePayment(Expense expense) {
    	CurrencyOnHand coh = null;
        if (expense.getPaymentMethod().equals(PaymentMethod.MONEY_ORDER)) {
            coh = getServiceLocator().getCurrencyOnHandService().findCurrencyOnHand(
                        expense.getMoneyOrderId().toString());
            if (coh != null) {
                coh.setUsed(false);
                getServiceLocator().getCurrencyOnHandService().saveCurrencyOnHand(coh);
            }
        }

        // UPDATE BALANCES
        if (expense.getPaymentMethod().equals(PaymentMethod.CHECK) ||
            expense.getPaymentMethod().equals(PaymentMethod.DEBIT) ||
            expense.getPaymentMethod().equals(PaymentMethod.E_PAYMENT)) {

            BankAccount bankAccount = getServiceLocator().getBankAccountService().findBankAccount(
                    expense.getBankAccountId().toString());
            bankAccount.setBalance(bankAccount.getBalance().add(expense.getAmount()));
            getServiceLocator().getBankAccountService().saveBankAccount(bankAccount);
        } else if (expense.getPaymentMethod().equals(PaymentMethod.CASH)) {
        	User user = getServiceLocator().getUserService().findUser(
        			String.valueOf(expense.getUserId().longValue()));
            user.addToBalance(expense.getAmount());
            getServiceLocator().getUserService().saveUser(user);
        } else if (expense.getPaymentMethod().equals(PaymentMethod.CREDIT)) {
            CreditCard cc = getServiceLocator().getCreditCardService().findCreditCard(
                    expense.getCreditCardId().toString());
            cc.subtractFromBalance(expense.getAmount());
            getServiceLocator().getCreditCardService().saveCreditCard(cc);
        }
    }
    
    
    public void deleteExpenseGroup(final String expenseGroupId) throws DataInUseException {
    	
    	// Confirm that the expense group can be deleted
    	int numberOfExpensesUsingIt = getServiceLocator().getExpenseGroupService().
    		getNumberOfExpensesWithExpenseGroup(expenseGroupId);
    	
    	if (numberOfExpensesUsingIt > 0) {
    		throw new DataInUseException("One or more Expenses are already tied to this project.");
    	}
    	
    	if (getServiceLocator().getExpenseGroupService().getNumberOfExpenseItemsWithExpenseGroup(expenseGroupId) > 0) {
    		throw new DataInUseException("One or more Expense Items are already tied to this project.");
    	}
    	
    	if (getServiceLocator().getExpenseGroupService().getNumberOfLoansWithExpenseGroup(expenseGroupId) > 0) {
    		throw new DataInUseException("One or more Loans are already tied to this project.");
    	}
    	
    	if (getServiceLocator().getExpenseGroupService().getNumberOfIncomesWithExpenseGroup(expenseGroupId) > 0) {
    		throw new DataInUseException("One or more Incomes are already tied to this project.");
    	}
    	
    	if (getServiceLocator().getExpenseGroupService().getNumberOfCurrencyOnHandWithExpenseGroup(expenseGroupId) > 0) {
    		throw new DataInUseException("One or more Checks/Money Orders are already tied to this project.");
    	}
    	
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
                
            	String currentAccountId = FacesUtils.getSessionBean().getCurrentAccountId();
            	Account account = getServiceLocator().getAccountService().findAccount(currentAccountId);
            	
            	Set<ExpenseGroup> set = account.getExpenseGroups();
            	for (ExpenseGroup eg : set) {
            		if (eg.getId() == Long.parseLong(expenseGroupId)) {
            			set.remove(eg);
            			break;
            		}
            	}
            	
                getServiceLocator().getAccountService().saveAccount(account);
                
                getServiceLocator().getExpenseGroupService().deleteExpenseGroup(expenseGroupId);
                
            }
        });
    }
}
