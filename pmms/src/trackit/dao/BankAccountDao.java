/*
 * JCatalog Project
 */
package trackit.dao;

import java.util.List;
import trackit.businessobjects.BankAccount;


/**
 * The user DAO interface.
 * <p>
 * This class contains user management related data access logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface BankAccountDao {

    public List<BankAccount> getAllBankAccountsForAccount(String accountId);


	/**
	 * Get user by username.
	 *
	 * @param username the username
	 * @return the user associated with the username
	 */
    public BankAccount getBankAccount(String id);

    public BankAccount saveBankAccount(BankAccount account);

    public List findBankAccountByDescription(String description);

	public void deleteBankAccount(String id);
	
	public int getNumberOfExpensesWithBankAccount(String id);
	
	public int getNumberOfLoansWithBankAccount(String id);
	
	public int getNumberOfDepositsWithBankAccount(String id);
	
	public int getNumberOfNonOpeningBalanceTransactionsWithBankAccount(String id);

}
