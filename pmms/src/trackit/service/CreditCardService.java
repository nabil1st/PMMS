/*
 * JCatalog Project
 */
package trackit.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import trackit.businessobjects.BankAccountTransaction;
import trackit.businessobjects.CreditCard;
import trackit.businessobjects.CreditCardPayment;
import trackit.businessobjects.CreditCardTransaction;

/**
 * The user business service interface.
 * <p>
 * It contains all user management related business logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface CreditCardService {
    public CreditCard saveCreditCard(CreditCard accnt);
    public CreditCard findCreditCard(String id);
    public Object findCreditCardByDescription(String description);

    public CreditCardTransaction saveCreditCardTransaction(
            CreditCardTransaction accnt);
    public CreditCardTransaction findCreditCardTransaction(String id);
    
    public CreditCardTransaction findCreditCardOpeningBalanceTransaction(
			String id);

    public List<CreditCard> getAllCreditCardsForAccount(String accountId);

    public CreditCardPayment findCreditCardPayment(String id);

    public CreditCardPayment saveCreditCardPayment(CreditCardPayment payment);

    public List<CreditCardTransaction> getAllCreditCardTransactionsForCreditCard(
            String creditCardId, Date fromDate, Date toDate);
    
    public int getNumberOfExpensesWithCreditCard(String id);	
	public int getNumberOfLoansWithCreditCard(String id);	
	public int getNumberOfPaymentsOnCreditCard(String id);	
	public int getNumberOfTransactionsWithCreditCard(String id);
	
	public void deleteAllOpeningBalanceTransactions(String creditCardId);
	public BigDecimal getOpeningBalance(String creditCardId);
	
	public void deleteCreditCardTransaction(String id);
	
	public void deleteCreditCard(String id);
}
