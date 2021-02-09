/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.transaction.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import trackit.backingbeans.CreditCardBean;
import trackit.backingbeans.CreditCardPaymentBean;
import trackit.backingbeans.CreditCardTransactionBean;
import trackit.builder.CreditCardBuilder;
import trackit.businessobjects.Account;
import trackit.businessobjects.BankAccount;
import trackit.businessobjects.BankAccountTransaction;
import trackit.businessobjects.BankTransactionType;
import trackit.businessobjects.CashTransaction;
import trackit.businessobjects.CashTransactionType;
import trackit.businessobjects.CreditCard;
import trackit.businessobjects.CreditCardPayment;
import trackit.businessobjects.CreditCardTransaction;
import trackit.businessobjects.CreditCardTransactionType;
import trackit.businessobjects.CurrencyOnHand;
import trackit.businessobjects.PaymentMethod;
import trackit.businessobjects.TransactionReason;
import trackit.exception.EntityException;
import trackit.service.impl.DataInUseException;
import trackit.servicelocator.ServiceLocator;
import trackit.transaction.service.CreditCardTransactionalService;
import trackit.util.FacesUtils;

/**
 *
 * @author ndaoud
 */
public class CreditCardTransactionalServiceImpl implements CreditCardTransactionalService {

    private HibernateTransactionManager transactionManager;
    private ServiceLocator serviceLocator;

    public void saveCreditCard(final CreditCardBean creditCardBean) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
            	if (creditCardBean.isEditMode()) {            		
            		CreditCard original = getServiceLocator().getCreditCardService().findCreditCard(
            				String.valueOf(creditCardBean.getId()));
            		original.setDescription(creditCardBean.getDescription());
            		
            		
            		// What about the opening balance?
            		CreditCardTransaction openingBalanceXAction = getServiceLocator().getCreditCardService().
            			findCreditCardOpeningBalanceTransaction(String.valueOf(creditCardBean.getId()));
            		
            		if (openingBalanceXAction != null) {
            			if (creditCardBean.getCurrentBalance().doubleValue() != openingBalanceXAction.getAmount().doubleValue()) {
            				if (creditCardBean.getCurrentBalance().doubleValue() == 0) {
            					getServiceLocator().getBankAccountService().deleteAllOpeningBalanceTransactions(String.valueOf(creditCardBean.getId()));
            					original.setBalance(original.getBalance().subtract(openingBalanceXAction.getAmount()));
            				} else {
            					BigDecimal diff = creditCardBean.getCurrentBalance().subtract(openingBalanceXAction.getAmount());
            					original.setBalance(original.getBalance().add(diff));
            					openingBalanceXAction.setAmount(creditCardBean.getCurrentBalance());
            					getServiceLocator().getCreditCardService().saveCreditCardTransaction(openingBalanceXAction);
            				}            				
            			}
            		} else {
            			if (creditCardBean.getCurrentBalance().doubleValue() != 0) {
            				CreditCardTransaction tx = new CreditCardTransaction();
	                        tx.setAmount(creditCardBean.getCurrentBalance());
	                        tx.setDate(new Date());
	                        tx.setTransactionType(CreditCardTransactionType.OPENING_BALANCE);
	                        tx.setCreditCardId(original.getId());
	                        tx.setDescription("Opening Blance");
	                        Long currentUserId = Long.parseLong(FacesUtils.getSessionBean().getCurrentUserId());
	                        tx.setUserId(currentUserId);
	                        tx.setDate(original.getCreatedDate());
	                        tx = getServiceLocator().getCreditCardService().saveCreditCardTransaction(tx);
	                        
	                        original.setBalance(original.getBalance().add(tx.getAmount()));
            			}
            		}
            		
            		original.setModifiedDate(new Date());
            		original.setModifiedBy(Long.parseLong( FacesUtils.getSessionBean().getCurrentUserId()));
            		
            		getServiceLocator().getCreditCardService().saveCreditCard(original);
            		
            	} else {
	                try {
	                	Long currentUserId = Long.parseLong( FacesUtils.getSessionBean().getCurrentUserId());
	                    CreditCard ba = CreditCardBuilder.createCreditCard(creditCardBean);
	
	                    BigDecimal currentBalance = creditCardBean.getCurrentBalance();
	                    if (currentBalance == null) {
	                        currentBalance = new BigDecimal(0);
	                    }
	                    ba.setBalance(currentBalance);
	                    ba.setCreatedDate(new Date());
	                    ba.setCreatedBy(currentUserId);
	
	                    ba = getServiceLocator().getCreditCardService().saveCreditCard(ba);
	
	                    if (currentBalance.doubleValue() > 0) {
	                        CreditCardTransaction tx = new CreditCardTransaction();
	                        tx.setAmount(currentBalance);
	                        tx.setDate(new Date());	                        
	                        tx.setTransactionType(CreditCardTransactionType.OPENING_BALANCE);
	                        tx.setCreditCardId(ba.getId());
	                        tx.setUserId(currentUserId);
	
	                        tx.setDescription("Opening Balance");
	
	                        tx = getServiceLocator().getCreditCardService().saveCreditCardTransaction(tx);
	                    }
	
	                    Account account = getServiceLocator().
	                            getAccountService().findAccount(
	                                FacesUtils.getSessionBean().getCurrentAccountId());
	                    Set<CreditCard> creditCards = account.getCreditCards();
	                    if (creditCards == null) {
	                        creditCards = new HashSet<CreditCard>();
	                    }
	                    creditCards.add(ba);
	                    account.setCreditCards(creditCards);
	
	                    getServiceLocator().getAccountService().saveAccount(account);
	                } catch (EntityException ex) {
	                    Logger.getLogger(BankAccountTransactionalServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
	                    status.setRollbackOnly();
	                }
            	}

            }
        });
    }
    
    @Override
	public void deleteCreditCard(final String id) throws DataInUseException {
		
		verifyCreditCardCanBeDeleted(id);
		
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
            	String currentAccountId = FacesUtils.getSessionBean().getCurrentAccountId();
            	Account account = getServiceLocator().getAccountService().findAccount(currentAccountId);
            	
            	Set<CreditCard> set = account.getCreditCards();
            	for (CreditCard p : set) {
            		if (p.getId() == Long.parseLong(id)) {
            			set.remove(p);
            			getServiceLocator().getAccountService().saveAccount(account);
            			break;
            		}
            	}
            	
            	// Need to delete any opening balance transactions related to this bank account
            	getServiceLocator().getCreditCardService().deleteAllOpeningBalanceTransactions(id);
            	
                getServiceLocator().getCreditCardService().deleteCreditCard(id);
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

    public void saveCreditCardPayment(final CreditCardPaymentBean creditCardPayment) {


        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
                CurrencyOnHand coh = null;
                if (creditCardPayment.getPaymentMethod().equals(PaymentMethod.MONEY_ORDER)) {
                    coh = findMoneyOrder(creditCardPayment.getMoneyOrderId());
                    if (coh != null) {
                        creditCardPayment.setAmount(coh.getAmount());
                        coh.setUsed(true);
                        getServiceLocator().getCurrencyOnHandService().saveCurrencyOnHand(coh);
                    }
                }

                Long currentUserId = Long.parseLong( FacesUtils.getSessionBean().getCurrentUserId());

                CreditCardTransaction ccxaction = new CreditCardTransaction();
                ccxaction.setAmount(creditCardPayment.getAmount());
                ccxaction.setCreditCardId(creditCardPayment.getCreditCardToPayId());
                ccxaction.setDate(creditCardPayment.getDate());
                ccxaction.setTransactionReason(TransactionReason.CREDIT_CARD_PAYMENT);
                ccxaction.setTransactionType(CreditCardTransactionType.PAYMENT);
                ccxaction.setUserId(currentUserId);

                ccxaction = getServiceLocator().getCreditCardService().saveCreditCardTransaction(ccxaction);

                CreditCardPayment payment = new CreditCardPayment();

                payment.setCreditCardId(creditCardPayment.getCreditCardToPayId());
                payment.setCreditCardTransactionId(ccxaction.getId());

                payment.setPaymentMethod(creditCardPayment.getPaymentMethod());

                if (creditCardPayment.getPaymentMethod().equals(PaymentMethod.CASH)) { // Cash transaction
                    CashTransaction cash = new CashTransaction();
                    cash.setAmount(creditCardPayment.getAmount());
                    cash.setDate(creditCardPayment.getDate());
                    cash.setInitiatorId(creditCardPayment.getCreditCardToPayId());
                    cash.setTransactionType(CashTransactionType.CASH_OUT);
                    cash.setTransactionReason(TransactionReason.CREDIT_CARD_PAYMENT);
                    cash.setUserId(currentUserId);
                    cash = getServiceLocator().getCashService().saveCashTransaction(cash);

                    payment.setTransactionId(cash.getId());

                } else if (creditCardPayment.getPaymentMethod().equals(PaymentMethod.CREDIT)) { // Credit card transaction
                    CreditCardTransaction credit = new CreditCardTransaction();
                    credit.setAmount(creditCardPayment.getAmount());
                    credit.setCreditCardId(creditCardPayment.getCreditCardId());
                    credit.setDate(creditCardPayment.getDate());
                    credit.setInitiatorId(creditCardPayment.getCreditCardToPayId());
                    credit.setTransactionType(CreditCardTransactionType.BALANCE_TRANSFER);
                    credit.setTransactionReason(TransactionReason.CREDIT_CARD_PAYMENT);
                    credit.setUserId(currentUserId);
                    credit = getServiceLocator().getCreditCardService().saveCreditCardTransaction(credit);

                    payment.setTransactionId(credit.getId());

                } else if (creditCardPayment.getPaymentMethod().equals(PaymentMethod.DEBIT) ||
                        creditCardPayment.getPaymentMethod().equals(PaymentMethod.E_PAYMENT) ||
                        creditCardPayment.getPaymentMethod().equals(PaymentMethod.CHECK)) { // Bank account transaction
                        BankAccountTransaction bank = new BankAccountTransaction();
                        bank.setAmount(creditCardPayment.getAmount());
                        bank.setBankAccountId(creditCardPayment.getBankAccountId());
                        bank.setDate(creditCardPayment.getDate());
                        bank.setInitiatorId(creditCardPayment.getCreditCardToPayId());
                        bank.setUserId(currentUserId);
                        bank.setTransactionReason(TransactionReason.CREDIT_CARD_PAYMENT);

                        if (creditCardPayment.getPaymentMethod().equals(PaymentMethod.CHECK)) {
                            bank.setCheckNumber(creditCardPayment.getCheckNumber());
                            bank.setTransactionType(BankTransactionType.CHECK_WITHDRAWL);
                        } else {
                            bank.setTransactionType(BankTransactionType.DEBIT);
                        }

                        bank = getServiceLocator().getBankAccountService().saveBankAccountTransaction(bank);

                        payment.setTransactionId(bank.getId());

                        // update bank account's balance
                        BankAccount bankAccount =
                                getServiceLocator().getBankAccountService().
                                    findBankAccount(creditCardPayment.getBankAccountId().toString());
                        bankAccount.subtractFromBalance(creditCardPayment.getAmount());
                        getServiceLocator().getBankAccountService().saveBankAccount(bankAccount);
                    }

                    getServiceLocator().getCreditCardService().saveCreditCardPayment(payment);

                    // update credit card balance
                    CreditCard cc = getServiceLocator().getCreditCardService().findCreditCard(
                            creditCardPayment.getCreditCardToPayId().toString());
                    cc.subtractFromBalance(creditCardPayment.getAmount());
                    getServiceLocator().getCreditCardService().saveCreditCard(cc);
                }
            });
    }

    private List<CurrencyOnHand> getAvailableMoneyOrders() {
        return getServiceLocator().getCurrencyOnHandService().
                    getAllUnusedMoneyOrdersOnHandForAccount(
                        FacesUtils.getSessionBean().getCurrentAccountId());
    }

    private CurrencyOnHand findMoneyOrder(Long id) {
        for (CurrencyOnHand coh : getAvailableMoneyOrders()) {
            if (coh.getId().equals(id)) {
                return coh;
            }
        }

        return null;
    }

    public void saveCreditCardTransaction(final CreditCardTransactionBean bean) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
                CreditCard cc = getServiceLocator().getCreditCardService().
                        findCreditCard(bean.getCreditCardId().toString());

                CreditCardTransaction xaction = new CreditCardTransaction();
                xaction.setAmount(bean.getAmount());
                xaction.setCreditCardId(bean.getCreditCardId());
                xaction.setDate(bean.getDate());
                xaction.setTransactionType(bean.getTransactionType());

                Long currentUserId = Long.parseLong( FacesUtils.getSessionBean().getCurrentUserId());
                xaction.setUserId(currentUserId);

                getServiceLocator().getCreditCardService().saveCreditCardTransaction(xaction);

                if (bean.getTransactionType().equals(CreditCardTransactionType.ANNUAL_FEE) ||
                    bean.getTransactionType().equals(CreditCardTransactionType.FINANCE_CHARGE) ||
                    bean.getTransactionType().equals(CreditCardTransactionType.LATE_FEE)) {

                    cc.subtractFromBalance(bean.getAmount());

                    getServiceLocator().getCreditCardService().saveCreditCard(cc);
                }
            }
        });
    }
    
    private void verifyCreditCardCanBeDeleted(String id) throws DataInUseException {
		// Must not be used by any expense
		if (getServiceLocator().getCreditCardService().getNumberOfExpensesWithCreditCard(id) > 0) {
			throw new DataInUseException("One or more Expenses are already tied to this credit card.");
		}
		
		// Must not be used by any loan
		if (getServiceLocator().getCreditCardService().getNumberOfLoansWithCreditCard(id) > 0) {
			throw new DataInUseException("One or more Loans are already tied to this credit card.");
		}
		
		// Must not be used by any BankAccountTransaction except for opening balance transactions
		if (getServiceLocator().getCreditCardService().getNumberOfTransactionsWithCreditCard(id) > 0) {
			throw new DataInUseException("One or more Non-Open Balance Transactions are already tied to this credit card.");
		}
		
		// Must not have any payments
		if (getServiceLocator().getCreditCardService().getNumberOfPaymentsOnCreditCard(id) > 0) {
			throw new DataInUseException("One or more Payments are already tied to this credit card.");
		}
	}

}
