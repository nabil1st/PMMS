/*
 * JCatalog Project
 */
package trackit.dao;

import java.util.List;
import trackit.businessobjects.CreditCard;


/**
 * The user DAO interface.
 * <p>
 * This class contains user management related data access logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface CreditCardDao {

    public List<CreditCard> getAllCreditCardsForAccount(String accountId);


	/**
	 * Get user by username.
	 *
	 * @param username the username
	 * @return the user associated with the username
	 */
    public CreditCard getCreditCard(String id);

    public CreditCard saveCreditCard(CreditCard card);

    public List findCreditCardByDescription(String description);
    
    public void deleteCreditCard(String id);
	
	public int getNumberOfExpensesWithCreditCard(String id);
	
	public int getNumberOfLoansWithCreditCard(String id);
	
	public int getNumberOfPaymentsOnCreditCard(String id);
	
	public int getNumberOfNonOpeningBalanceTransactionsWithCreditCard(String id);	

}
