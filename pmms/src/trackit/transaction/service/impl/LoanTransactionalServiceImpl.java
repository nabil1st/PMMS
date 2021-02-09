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
import trackit.backingbeans.BorrowerBean;
import trackit.backingbeans.LoanBean;
import trackit.backingbeans.LoanPaymentBean;
import trackit.builder.BorrowerBuilder;
import trackit.businessobjects.Account;
import trackit.businessobjects.BankAccount;
import trackit.businessobjects.BankAccountTransaction;
import trackit.businessobjects.BankTransactionType;
import trackit.businessobjects.Borrower;
import trackit.businessobjects.CashTransaction;
import trackit.businessobjects.CashTransactionType;
import trackit.businessobjects.CreditCard;
import trackit.businessobjects.CreditCardTransaction;
import trackit.businessobjects.CreditCardTransactionType;
import trackit.businessobjects.CurrencyOnHand;
import trackit.businessobjects.CurrencyOnHandSourceType;
import trackit.businessobjects.CurrencyOnHandType;
import trackit.businessobjects.Deposit;
import trackit.businessobjects.Loan;
import trackit.businessobjects.PaymentMethod;
import trackit.businessobjects.TransactionReason;
import trackit.businessobjects.TransactionType;
import trackit.businessobjects.User;
import trackit.exception.EntityException;
import trackit.service.impl.DataInUseException;
import trackit.servicelocator.ServiceLocator;
import trackit.transaction.service.LoanTransactionalService;
import trackit.util.FacesUtils;
import trackit.util.ListUtils;

/**
 *
 * @author ndaoud
 */
public class LoanTransactionalServiceImpl implements LoanTransactionalService {

    private HibernateTransactionManager transactionManager;
    private ServiceLocator serviceLocator;

    public void saveLoan(final LoanBean loanBean) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
                
                CurrencyOnHand coh = null;
                if (loanBean.getPaymentMethod().equals(PaymentMethod.MONEY_ORDER)) {
                    coh = findMoneyOrder(loanBean.getMoneyOrderId());
                    if (coh != null) {
                        loanBean.setAmount(coh.getAmount());
                        coh.setUsed(true);
                        getServiceLocator().getCurrencyOnHandService().saveCurrencyOnHand(coh);
                    }
                }
                Loan loan = new Loan();
                loan.setAmount(loanBean.getAmount());
                loan.setBankAccountId(ListUtils.isNullSelection(loanBean.getBankAccountId())?null:loanBean.getBankAccountId());
                loan.setBorrowerId(ListUtils.isNullSelection(loanBean.getBorrowerId())?null:loanBean.getBorrowerId());
                loan.setCheckNumber(loanBean.getCheckNumber());
                loan.setCreditCardId(ListUtils.isNullSelection(loanBean.getCreditCardId())?null:loanBean.getCreditCardId());
                loan.setDate(loanBean.getDate());
                loan.setDescription(loanBean.getDescription());
                loan.setGroupId(ListUtils.isNullSelection(loanBean.getGroupId())?null:loanBean.getGroupId());
                loan.setMoneyOrderId(ListUtils.isNullSelection(loanBean.getMoneyOrderId())?null:loanBean.getMoneyOrderId());
                loan.setPayeeId(ListUtils.isNullSelection(loanBean.getPayeeId())?null:loanBean.getPayeeId());
                loan.setPaymentMethod(loanBean.getPaymentMethod());
                                
                Long currentUserId = Long.parseLong(FacesUtils.getSessionBean().getCurrentUserId());
                loan.setUserId(currentUserId);
                if (loanBean.getPaymentMethod().equals(PaymentMethod.CASH)) {
                    // Cash transaction
                    CashTransaction cash = new CashTransaction();
                    cash.setAmount(loanBean.getAmount());
                    cash.setDate(loanBean.getDate());
                    cash.setInitiatorId(ListUtils.isNullSelection(loan.getPayeeId())?null:loan.getPayeeId());
                    cash.setTransactionType(CashTransactionType.CASH_OUT);
                    cash.setTransactionReason(TransactionReason.LOAN);
                    cash.setUserId(currentUserId);
                    cash = getServiceLocator().getCashService().saveCashTransaction(cash);
                    loan.setTransactionId(cash.getId());
                    loan.setTransactionType(TransactionType.CASH);
//                        Account account = getServiceLocator().getAccountService().findAccount(FacesUtils.getSessionBean().getCurrentAccountId());
//                        account.subtractFromBalance(loanBean.getAmount());
//                        getServiceLocator().getAccountService().saveAccount(account);
                    
                    User currentUser = getServiceLocator().getUserService().findUser(String.valueOf(currentUserId.longValue()));
                    currentUser.subtractFromBalance(loanBean.getAmount());
                    getServiceLocator().getUserService().saveUser(currentUser);
                } else if (loanBean.getPaymentMethod().equals(PaymentMethod.CREDIT)) {
                    // Credit card transaction
                    CreditCardTransaction credit = new CreditCardTransaction();
                    credit.setAmount(loanBean.getAmount());
                    credit.setCreditCardId(loanBean.getCreditCardId());
                    credit.setDate(loanBean.getDate());
                    credit.setInitiatorId(ListUtils.isNullSelection(loan.getPayeeId())?null:loan.getPayeeId());
                    credit.setTransactionType(CreditCardTransactionType.PURCHASE);
                    credit.setTransactionReason(TransactionReason.LOAN);
                    credit.setUserId(currentUserId);
                    credit = getServiceLocator().getCreditCardService().saveCreditCardTransaction(credit);
                    loan.setTransactionId(credit.getId());
                    loan.setTransactionType(TransactionType.CREDIT);
                    CreditCard cc = getServiceLocator().getCreditCardService().findCreditCard(loanBean.getCreditCardId().toString());
                    cc.subtractFromBalance(loanBean.getAmount());
                    getServiceLocator().getCreditCardService().saveCreditCard(cc);
                } else if (loanBean.getPaymentMethod().equals(PaymentMethod.DEBIT) || loanBean.getPaymentMethod().equals(PaymentMethod.CHECK)) {
                    // Bank account transaction
                    BankAccountTransaction bank = new BankAccountTransaction();
                    bank.setAmount(loanBean.getAmount());
                    bank.setBankAccountId(loanBean.getBankAccountId());
                    bank.setDate(loanBean.getDate());
                    bank.setInitiatorId(ListUtils.isNullSelection(loan.getPayeeId())?null:loan.getPayeeId());
                    bank.setUserId(currentUserId);
                    bank.setTransactionReason(TransactionReason.LOAN);
                    if (loanBean.getPaymentMethod().equals(PaymentMethod.CHECK)) {
                        bank.setCheckNumber(loanBean.getCheckNumber());
                        bank.setTransactionType(BankTransactionType.CHECK_WITHDRAWL);
                    } else {
                        bank.setTransactionType(BankTransactionType.DEBIT);
                    }
                    bank = getServiceLocator().getBankAccountService().saveBankAccountTransaction(bank);
                    loan.setTransactionId(bank.getId());
                    loan.setTransactionType(TransactionType.BANK);
                    // Adjust the bank account balanace
                    BankAccount bankAccount = getServiceLocator().getBankAccountService().findBankAccount(loanBean.getBankAccountId().toString());
                    bankAccount.subtractFromBalance(loanBean.getAmount());
                    getServiceLocator().getBankAccountService().saveBankAccount(bankAccount);
                }
                loan = getServiceLocator().getLoanService().saveLoan(loan);
                
            }
        });
    }
    
    
    public void saveLoan(final Loan l) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
                Loan loan = l;                    
                if (ListUtils.isNullSelection(loan.getGroupId())) {
                    loan.setGroupId(null);
                }
                if (loan.getPayeeId() != null && loan.getPayeeId().equals(loan.getBorrowerId())) {
                    loan.setPayeeId(null);
                }
                
                Long currentUserId = Long.parseLong(FacesUtils.getSessionBean().getCurrentUserId());
                loan.setUserId(currentUserId);
                
                loan = getServiceLocator().getLoanService().saveLoan(loan);
                
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

    public void saveLoanPayment(final LoanPaymentBean loanPayment) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
                Long currentUserId = Long.parseLong(
                    FacesUtils.getSessionBean().getCurrentUserId());

                Loan loan = getServiceLocator().getLoanService().getLoan(String.valueOf(
                        loanPayment.getLoanId()));

                CurrencyOnHand coh = new CurrencyOnHand();
                coh.setUserId(currentUserId);
                coh.setAmount(loanPayment.getAmount());
                coh.setCheckNumber(loanPayment.getCheckNumber());
                coh.setDate(loanPayment.getDate());
                coh.setSourceType(CurrencyOnHandSourceType.LOAN_PAYMENT);
                if (loanPayment.getPayerId() != null) {
                    coh.setSourceId(loanPayment.getPayerId());
                } else {
                    coh.setSourceId(loan.getBorrowerId());
                }

                coh.setReferenceId(loan.getId());
                coh.setGroupId(loan.getGroupId());
                
                
                
//                if (loanPayment.getCurrencyType().equals(CurrencyOnHandType.DIRECT_DEPOSIT)) {
//                	coh.setType(CurrencyOnHandType.CHECK);
//                } else {
                	coh.setType(loanPayment.getCurrencyType());                    
//                }
                
                coh = getServiceLocator().getCurrencyOnHandService().saveCurrencyOnHand(coh);
                
                loan.getLoanPayments().add(coh);

                getServiceLocator().getLoanService().saveLoan(loan);

                // If income is cash, update cas balance
                if (coh.getType().equals(CurrencyOnHandType.CASH)) {
                	User currentUser = getServiceLocator().getUserService().findUser(String.valueOf(currentUserId.longValue()));
                    currentUser.addToBalance(loanPayment.getAmount());
                    getServiceLocator().getUserService().saveUser(currentUser);
                }
                
                if (loanPayment.getCurrencyType().equals(CurrencyOnHandType.DIRECT_DEPOSIT)) {
                	
                	Deposit dep = new Deposit();
                	dep.setBankAccountId(loanPayment.getBankAccountId());
                	dep.setDate(loanPayment.getDate());
                	dep.setUserId(currentUserId);
                	Set<CurrencyOnHand> depositItems = new HashSet<CurrencyOnHand>();
                	depositItems.add(coh);
                	dep.setDepositItems(depositItems);
                	dep.setTotalCash(new BigDecimal(0));
                	
                	getServiceLocator().getDepositService().saveDeposit(dep);
                	
                	BankAccount bankAccount = getServiceLocator().getBankAccountService().findBankAccount(
                			String.valueOf(loanPayment.getBankAccountId()));
                	bankAccount.addToBalance(loanPayment.getAmount());
                	getServiceLocator().getBankAccountService().saveBankAccount(bankAccount);
                }                
            }
        });
    }

    private CurrencyOnHand findMoneyOrder(Long id) {
        List<CurrencyOnHand> availableMoneyOrders =
                getServiceLocator().getCurrencyOnHandService().
                    getAllUnusedMoneyOrdersOnHandForAccount(
                        FacesUtils.getSessionBean().getCurrentAccountId());
        for (CurrencyOnHand coh : availableMoneyOrders) {
            if (coh.getId().equals(id)) {
                return coh;
            }
        }

        return null;
    }

    public void saveBorrower(final BorrowerBean borrowerBean) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    Borrower borrower = BorrowerBuilder.createBorrower(borrowerBean);
                    borrower = getServiceLocator().getBorrowerService().saveBorrower(borrower);
                    Account account = getServiceLocator().getAccountService().findAccount(FacesUtils.getSessionBean().getCurrentAccountId());
                    Set<Borrower> borrowers = account.getBorrowers();
                    if (borrower == null) {
                        borrowers = new HashSet<Borrower>();
                    }
                    borrowers.add(borrower);
                    account.setBorrowers(borrowers);
                    getServiceLocator().getAccountService().saveAccount(account);
                } catch (EntityException ex) {
                    Logger.getLogger(ExpenseTransactionalServiceImpl.class.getName()).log(
                            Level.SEVERE, null, ex);
                    status.setRollbackOnly();
                }
            }
        });

    }
    
    public void deleteLoanPayment(final Long incomeId) throws DataInUseException {
    	final CurrencyOnHand coh = getServiceLocator().getCurrencyOnHandService().findCurrencyOnHand(String.valueOf(incomeId));
    	verifyLoanPaymentCanBeDeleted(coh);
    	
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
                
                // Delete the loan payment entry in the loan_currency_on_hand table
                Loan loan = getServiceLocator().getLoanService().findLoanByCurrencyOnHandId(String.valueOf(coh.getId()));
                CurrencyOnHand toDelete = null;
                for (CurrencyOnHand c : loan.getLoanPayments()) {
                	if (c.getId().equals(coh.getId())) {
                		toDelete = c;
                		break;
                	}
                }
                
                if (toDelete != null) {
                	loan.getLoanPayments().remove(toDelete);
                	getServiceLocator().getLoanService().saveLoan(loan);
                }
                
                getServiceLocator().getCurrencyOnHandService().deleteCurrencyOnHand(String.valueOf(coh.getId()));
                
            }
        });
    	
    }
    
    public void verifyLoanPaymentCanBeDeleted(CurrencyOnHand income) throws DataInUseException {
    	if (income.isUsed() && !income.getType().equals(CurrencyOnHandType.DIRECT_DEPOSIT)) {
    		throw new DataInUseException("The item you're trying to delete is already in use by another transaction");
    	}
    }
}
