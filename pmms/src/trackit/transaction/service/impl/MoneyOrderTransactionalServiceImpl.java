/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.transaction.service.impl;

import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import trackit.backingbeans.MoneyOrderBean;
import trackit.businessobjects.BankAccount;
import trackit.businessobjects.BankAccountTransaction;
import trackit.businessobjects.BankTransactionType;
import trackit.businessobjects.CashTransaction;
import trackit.businessobjects.CashTransactionType;
import trackit.businessobjects.CreditCard;
import trackit.businessobjects.CreditCardTransaction;
import trackit.businessobjects.CreditCardTransactionType;
import trackit.businessobjects.CurrencyOnHand;
import trackit.businessobjects.CurrencyOnHandSourceType;
import trackit.businessobjects.CurrencyOnHandType;
import trackit.businessobjects.MoneyOrderFee;
import trackit.businessobjects.PaymentMethod;
import trackit.businessobjects.TransactionReason;
import trackit.businessobjects.TransactionType;
import trackit.businessobjects.User;
import trackit.service.impl.DataInUseException;
import trackit.servicelocator.ServiceLocator;
import trackit.transaction.service.MoneyOrderTransactionalService;
import trackit.util.FacesUtils;
import trackit.util.ListUtils;

/**
 *
 * @author ndaoud
 */
public class MoneyOrderTransactionalServiceImpl implements MoneyOrderTransactionalService {

    private HibernateTransactionManager transactionManager;
    private ServiceLocator serviceLocator;

    public void saveMoneyOrder(final MoneyOrderBean moneyOrderBean) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {

                Long currentUserId = Long.parseLong(
                        FacesUtils.getSessionBean().getCurrentUserId());

                // Create CurrencyOnHand table entry
                CurrencyOnHand coh = new CurrencyOnHand();
                coh.setUserId(currentUserId);
                coh.setAmount(moneyOrderBean.getAmount());
                coh.setCheckNumber(moneyOrderBean.getNumber());
                coh.setDate(moneyOrderBean.getDate());
                coh.setType(CurrencyOnHandType.MONEY_ORDER);
                coh.setUsed(false);
                coh.setSourceType(CurrencyOnHandSourceType.MONEY_ORDER_PURCHASE);


                coh = getServiceLocator().getCurrencyOnHandService().saveCurrencyOnHand(coh);


                // Create MoneyOrderFee table entry

                if (moneyOrderBean.getFee() != null && moneyOrderBean.getFee().doubleValue() > 0) {
                    MoneyOrderFee moFee = new MoneyOrderFee();
                    moFee.setFee(moneyOrderBean.getFee());
                    moFee.setMoneyOrderId(coh.getId());

                    moFee = getServiceLocator().getMoneyOrderService().saveMoneyOrderFee(moFee);
                }

                // Create a (CashTransaction, CreditCardTransaction or
                // BankTransaction Table entry depending on the payment method selected)

                if (moneyOrderBean.getPaymentMethod().equals(PaymentMethod.CASH)) { // Cash transaction
                    CashTransaction cash = new CashTransaction();
                    cash.setAmount(moneyOrderBean.getAmount().add(moneyOrderBean.getFee()));
                    cash.setDate(moneyOrderBean.getDate());

                    if (ListUtils.isNullSelection(moneyOrderBean.getIssuerId())) {
                        cash.setInitiatorId(null);
                    } else {
                        cash.setInitiatorId(moneyOrderBean.getIssuerId());
                    }
                    
                    cash.setTransactionType(CashTransactionType.CASH_OUT);
                    cash.setTransactionReason(TransactionReason.MONEY_ORDER_PURCHASE);
                    cash.setUserId(currentUserId);
                    cash = getServiceLocator().getCashService().saveCashTransaction(cash);

                    coh.setTransactionId(cash.getId());
                    coh.setTransactionType(TransactionType.CASH);

//                    Account account = getServiceLocator().getAccountService().findAccount(
//                            FacesUtils.getSessionBean().getCurrentAccountId());
//                    account.subtractFromBalance(moneyOrderBean.getAmount().add(moneyOrderBean.getFee()));
//                    getServiceLocator().getAccountService().saveAccount(account);
                    
                    User currentUser = getServiceLocator().getUserService().findUser(String.valueOf(currentUserId.longValue()));
                    currentUser.subtractFromBalance(moneyOrderBean.getAmount().add(moneyOrderBean.getFee()));
                    getServiceLocator().getUserService().saveUser(currentUser);

                } else if (moneyOrderBean.getPaymentMethod().equals(PaymentMethod.CREDIT)) { // Credit card transaction
                    CreditCardTransaction credit = new CreditCardTransaction();
                    credit.setAmount(moneyOrderBean.getAmount().add(moneyOrderBean.getFee()));
                    credit.setCreditCardId(moneyOrderBean.getCreditCardId());
                    credit.setDate(moneyOrderBean.getDate());
                    credit.setInitiatorId(moneyOrderBean.getIssuerId());
                    credit.setTransactionType(CreditCardTransactionType.PURCHASE);
                    credit.setTransactionReason(TransactionReason.MONEY_ORDER_PURCHASE);
                    credit.setUserId(currentUserId);
                    credit = getServiceLocator().getCreditCardService().saveCreditCardTransaction(credit);
                    coh.setTransactionId(credit.getId());
                    coh.setTransactionType(TransactionType.CREDIT);

                    CreditCard cc =
                            getServiceLocator().getCreditCardService().findCreditCard(
                                moneyOrderBean.getCreditCardId().toString());
                    cc.addToBalance(moneyOrderBean.getAmount().add(moneyOrderBean.getFee()));
                    getServiceLocator().getCreditCardService().saveCreditCard(cc);


                } else if (moneyOrderBean.getPaymentMethod().equals(PaymentMethod.DEBIT) ||
                        moneyOrderBean.getPaymentMethod().equals(PaymentMethod.CHECK)) { // Bank account transaction
                    BankAccountTransaction bank = new BankAccountTransaction();
                    bank.setAmount(moneyOrderBean.getAmount().add(moneyOrderBean.getFee()));
                    bank.setBankAccountId(moneyOrderBean.getBankAccountId());
                    bank.setDate(moneyOrderBean.getDate());
                    bank.setInitiatorId(
                            ListUtils.isNullSelection(moneyOrderBean.getIssuerId())?null:moneyOrderBean.getIssuerId());
                    bank.setUserId(currentUserId);
                    bank.setTransactionReason(TransactionReason.MONEY_ORDER_PURCHASE);

                    if (moneyOrderBean.getPaymentMethod().equals(PaymentMethod.CHECK)) {
                        bank.setCheckNumber(moneyOrderBean.getCheckNumber());
                        bank.setTransactionType(BankTransactionType.CHECK_WITHDRAWL);
                    } else {
                        bank.setTransactionType(BankTransactionType.DEBIT);
                    }

                    bank = getServiceLocator().getBankAccountService().saveBankAccountTransaction(bank);

                    coh.setTransactionId(bank.getId());
                    coh.setTransactionType(TransactionType.BANK);


                    BankAccount bankAccount = getServiceLocator().getBankAccountService().findBankAccount(
                            moneyOrderBean.getBankAccountId().toString());
                    bankAccount.subtractFromBalance(moneyOrderBean.getAmount().add(moneyOrderBean.getFee()));
                    getServiceLocator().getBankAccountService().saveBankAccount(bankAccount);
                }


                coh = getServiceLocator().getCurrencyOnHandService().saveCurrencyOnHand(coh);

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
    
    public void deleteMoneyOrder(final Long moneyOrderId) throws DataInUseException {
    	final CurrencyOnHand coh = this.serviceLocator.getCurrencyOnHandService().findCurrencyOnHand(
    			String.valueOf(moneyOrderId));
    	verifyMoneyOrderCanBeDeleted(coh);
    	
    	TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {

                Long currentUserId = Long.parseLong(
                        FacesUtils.getSessionBean().getCurrentUserId());

                // Delete the money order fee
                MoneyOrderFee fee = getServiceLocator().getMoneyOrderService().findMoneyOrderFeeByMoneyOrderId(
                		String.valueOf(moneyOrderId));
                getServiceLocator().getMoneyOrderService().deleteMoneyOrderFee(String.valueOf(fee.getId()));
                
                // Delete a (CashTransaction, CreditCardTransaction or
                // BankTransaction Table entry depending on the payment method selected)
                
                
                if (TransactionType.CASH.equals(coh.getTransactionType())) { // Cash transaction
                	// Delete the cash transaction and add the cash amount to the user's balance
                	CashTransaction cashTransaction = 
                		getServiceLocator().getCashService().findCashTransaction(String.valueOf(coh.getTransactionId()));
                	
                	User currentUser = getServiceLocator().getUserService().findUser(String.valueOf(currentUserId.longValue()));
                	currentUser.addToBalance(cashTransaction.getAmount());
                	getServiceLocator().getUserService().saveUser(currentUser);
                	
                	getServiceLocator().getCashService().deleteCashTransaction(String.valueOf(cashTransaction.getId()));                	
                    

                } else if (TransactionType.CREDIT.equals(coh.getTransactionType())) { // Credit card transaction
                    CreditCardTransaction credit = 
                    	getServiceLocator().getCreditCardService().findCreditCardTransaction(String.valueOf(coh.getTransactionId()));
                    
                    CreditCard cc =
                        getServiceLocator().getCreditCardService().findCreditCard(
                            String.valueOf(credit.getCreditCardId()));
                    cc.subtractFromBalance(credit.getAmount());
                    getServiceLocator().getCreditCardService().saveCreditCard(cc);
                    
                    
                    getServiceLocator().getCreditCardService().deleteCreditCardTransaction(String.valueOf(credit.getId()));


                } else if (TransactionType.BANK.equals(coh.getTransactionType())) { // Bank account transaction
                    BankAccountTransaction bank = 
                    	getServiceLocator().getBankAccountService().findBankAccountTransaction(
                    			String.valueOf(coh.getTransactionId()));
                    
                    BankAccount bankAccount = getServiceLocator().getBankAccountService().findBankAccount(String.valueOf(bank.getBankAccountId()));
                    bankAccount.addToBalance(bank.getAmount());
                    getServiceLocator().getBankAccountService().saveBankAccount(bankAccount);
                    
                    getServiceLocator().getBankAccountService().deleteBankAccountTransaction(
                    		String.valueOf(coh.getTransactionId()));
                }


                getServiceLocator().getCurrencyOnHandService().deleteCurrencyOnHand(String.valueOf(coh.getId()));

            }
        });
    	
    }
    
    private void verifyMoneyOrderCanBeDeleted(CurrencyOnHand coh) throws DataInUseException {
    	if (coh.isUsed()) {
    		throw new DataInUseException("This money order is being used by another transaction");
    	}
    }
}
