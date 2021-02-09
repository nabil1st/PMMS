/*
 * JCatalog Project
 */
package trackit.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import trackit.businessobjects.BankAccountTransaction;
import trackit.businessobjects.CreditCardPayment;
import trackit.businessobjects.CreditCardTransaction;


/**
 * The user DAO interface.
 * <p>
 * This class contains user management related data access logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface CreditCardTransactionDao {

    public List<CreditCardTransaction> getAllCreditCardTransactionsForCreditCard(
            String creditCardId, Date fromDate, Date toDate);


	/**
	 * Get user by username.
	 *
	 * @param username the username
	 * @return the user associated with the username
	 */
    public CreditCardTransaction getCreditCardTransaction(String id);

    public CreditCardTransaction saveCreditCardTransaction(CreditCardTransaction account);


    public CreditCardPayment getCreditCardPayment(String id);

    public CreditCardPayment saveCreditCardPayment(CreditCardPayment payment);
    
    public void deleteTransaction(String id);
	
	public void deleteAllOpeningBalanceTransactions(String creditCardId);

	public BigDecimal getOpeningBalance(String creditCardId);


	public CreditCardTransaction findCreditCardOpeningBalanceTransaction(String creditCardId);

}
